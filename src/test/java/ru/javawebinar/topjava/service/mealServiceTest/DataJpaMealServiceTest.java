package ru.javawebinar.topjava.service.mealServiceTest;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getMealWidthUser() throws Exception {
        Meal actual = service.getMealAndUser(ADMIN_MEAL_ID, ADMIN_ID);
        ADMIN_MEAL1.setUser(ADMIN);
        assertMatchWidthUser(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFoundMealWidthUser() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getMealAndUser(MEAL1_ID, ADMIN_ID);
    }
}
