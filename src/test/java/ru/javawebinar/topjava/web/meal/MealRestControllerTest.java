package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService mealService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertMatch(mealService.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExceeded(MEALS, DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    public void created() throws Exception {
        Meal meal = new Meal(LocalDateTime.of(2018, 4, 20, 11, 0), "Шашлычок", 300);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isCreated());
        Meal returned = TestUtil.readFromJson(action, Meal.class);
        meal.setId(returned.getId());

        assertMatch(returned, meal);
        assertMatch(mealService.getAll(USER_ID), meal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        updated.setDescription("Шашлычок");
        updated.setCalories(200);
        mockMvc.perform(put(REST_URL + MEAL1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void getBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?start=2015-05-30T01:15&end=2015-05-30T16:15"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExceeded(Arrays.asList(MEAL2, MEAL1), DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    public void getBetween2() throws Exception {
        mockMvc.perform(get(REST_URL + "filter2?startDate=2015-05-30&startTime=01:15&endDate=2015-05-30&endTime=16:15"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExceeded(Arrays.asList(MEAL2, MEAL1), DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    public void getBetween2Null() throws Exception {
        mockMvc.perform(get(REST_URL + "filter2?startDate=&startTime="))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExceeded(MEALS, DEFAULT_CALORIES_PER_DAY)));
    }
}