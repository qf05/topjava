package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal update(Meal meal, int id) throws NotFoundException {
        assureIdConsistent(meal, id);
        return service.update(meal, AuthorizedUser.id());
    }

    public Meal create(Meal meal) throws NotFoundException {
        checkNew(meal);
        return service.create(meal, AuthorizedUser.id());
    }

    public void delete(int id) throws NotFoundException {
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) throws NotFoundException {
        return service.get(id, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAllFiltred(String localDateFromStr, String localDateToStr, String localTimeFromStr, String localTimeToStr) {
        LocalDate localDateFrom = localDateFromStr == null || localDateFromStr.isEmpty() ? LocalDate.MIN : LocalDate.parse(localDateFromStr);
        LocalDate localDateTo = localDateToStr == null || localDateToStr.isEmpty() ? LocalDate.MAX : LocalDate.parse(localDateToStr);
        LocalTime localTimeFrom = localTimeFromStr == null || localTimeFromStr.isEmpty() ? LocalTime.MIN : LocalTime.parse(localTimeFromStr);
        LocalTime localTimeTo = localTimeToStr == null || localTimeToStr.isEmpty() ? LocalTime.MAX : LocalTime.parse(localTimeToStr);
        return MealsUtil.getFilteredWithExceeded((service.getAllFiltred(AuthorizedUser.id(), localDateFrom, localDateTo)), AuthorizedUser.getCaloriesPerDay(), localTimeFrom, localTimeTo);
    }

    public List<MealWithExceed> getAll() {
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }
}