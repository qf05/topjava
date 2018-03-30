package ru.javawebinar.topjava.service.userServiceTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void getUserAndMeals() throws Exception {
        User actual = service.getUserAndMeals(ADMIN_ID);
        ADMIN.setMeals(Arrays.asList(ADMIN_MEAL1, ADMIN_MEAL2));
        assertMatchWidthMeals(actual, ADMIN);
    }
}
