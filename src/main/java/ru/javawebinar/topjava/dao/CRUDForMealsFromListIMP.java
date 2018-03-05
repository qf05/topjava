package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CRUDForMealsFromListIMP implements CRUDForMeals {

    public static AtomicInteger id = new AtomicInteger(0);

    private static Map<Integer, Meal> mealMap = new ConcurrentHashMap();

    static {
        mealMap.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, id.get()));
        mealMap.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, id.get()));
        mealMap.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, id.get()));
        mealMap.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, id.get()));
        mealMap.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, id.get()));
        mealMap.put(id.incrementAndGet(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, id.get()));
    }

    @Override
    public void update(Meal meal) {
        mealMap.remove(meal.getId());
        mealMap.put(meal.getId(),meal);
    }

    @Override
    public Meal add(Meal meal) {
        Meal newMeal = new Meal(meal.getDateTime(),meal.getDescription(),meal.getCalories(),id.incrementAndGet());
        mealMap.put(newMeal.getId(),newMeal);
        return newMeal;
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal getId(int id) {
        return mealMap.get(id);
    }
}
