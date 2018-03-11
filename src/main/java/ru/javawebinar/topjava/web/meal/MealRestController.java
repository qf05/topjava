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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    @Autowired
    private MealService service;

    public Meal save(String idStr, String dateTime, String description, String calories) {
        int id = idStr.isEmpty() ? 0 : Integer.valueOf(idStr);
        Meal meal = new Meal(id == 0 ? null : id, AuthorizedUser.id(),
                LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        if (meal.isNew()) {
            meal.setUserId(AuthorizedUser.id());
            return this.create(meal);
        } else {
            return this.update(meal, id);
        }
    }

    public Meal update(Meal meal, int id) throws NotFoundException {
        if (meal.getUserId()!= AuthorizedUser.id()){
            throw new NotFoundException("id " + id);
        }
        assureIdConsistent(meal, id);
        return service.update(meal);
    }

    public Meal create(Meal meal) throws NotFoundException {
        return service.create(meal);
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