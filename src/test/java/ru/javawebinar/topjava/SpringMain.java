package ru.javawebinar.topjava;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.Profiles.REPOSITORY_IMPLEMENTATION;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext()) {
            appContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), REPOSITORY_IMPLEMENTATION);
            appContext.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
            appContext.refresh();
            System.out.println("Bean definition names: " + Arrays.toString(appContext.getBeanDefinitionNames()));
            AdminRestController adminUserController = appContext.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            System.out.println();

            MealRestController mealController = appContext.getBean(MealRestController.class);
            List<MealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);
        }
    }
}
