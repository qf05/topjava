package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface DataBase {
    void update(LocalDateTime dateTime, String description, int calories, int id);
    void add(LocalDateTime dateTime, String description, int calories);
    void delete(int id);
    List<Meal> getListMeal();
}
