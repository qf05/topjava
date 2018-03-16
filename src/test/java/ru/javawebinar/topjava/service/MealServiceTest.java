package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_USER_ID, USER_ID);
        MealTestData.assertMatch(meal, MEAL_USER_2);
    }

    @Test
    public void delete() {
        service.delete(MEAL_USER_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_USER_3, MEAL_USER_1);
    }

    @Test
    public void getBetweenDates() {
        LocalDate startDate = LocalDate.of(2015, Month.MAY, 30);
        LocalDate endDate = LocalDate.of(2015, Month.MAY, 31);
        List<Meal> meals = service.getBetweenDates(startDate, endDate, USER_ID);
        assertMatch(meals, MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime startDateTime = LocalDateTime.of(2015, Month.MAY, 30, 2, 40);
        LocalDateTime endDateTime = LocalDateTime.of(2015, Month.MAY, 30, 18, 40);
        List<Meal> meals = service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID);
        assertMatch(meals, MEAL_USER_2, MEAL_USER_1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }

    @Test
    public void update() {
        Meal meal = new Meal(MEAL_USER_1);
        meal.setDateTime(LocalDateTime.of(2015, Month.MAY, 31, 2, 40));
        meal.setDescription("Ночной Дожор");
        meal.setCalories(52000);
        service.update(meal, USER_ID);
        assertMatch(service.get(meal.getId(), USER_ID), meal);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(MEAL_USER_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(MEAL_USER_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() throws Exception {
        Meal meal = new Meal(MEAL_USER_1);
        meal.setDateTime(LocalDateTime.of(2015, Month.MAY, 31, 2, 40));
        meal.setDescription("Ночной Дожор");
        meal.setCalories(52000);
        service.update(meal, ADMIN_ID);
    }
}