package ru.javawebinar.topjava.service.userServiceTest;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {


    @Test
    public void getUserAndMeals() throws Exception {
        User actual = service.getUserAndMeals(ADMIN_ID);
        assertMatch(actual, ADMIN);
        assertMatch(actual.getMeals(), ADMIN_MEAL2, ADMIN_MEAL1);
    }
}
