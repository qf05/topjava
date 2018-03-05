package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.List;

public interface CRUDForMeals {
    void update(Meal meal);
    Meal add(Meal meal);
    void delete(int id);
    List<Meal> getAll();
    Meal getId(int id);
}
