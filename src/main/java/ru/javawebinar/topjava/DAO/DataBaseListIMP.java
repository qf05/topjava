package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DataBaseListIMP implements DataBase {

    private static AtomicInteger id = new AtomicInteger(0);

    private static synchronized void generateId() {
        id.set(id.intValue() + 1);
    }

    private static Map<Integer, Meal> mealMap = new ConcurrentHashMap();

    static {
        generateId();
        mealMap.put(id.get(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, id.get()));
        generateId();
        mealMap.put(id.get(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, id.get()));
        generateId();
        mealMap.put(id.get(), new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, id.get()));
        generateId();
        mealMap.put(id.get(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, id.get()));
        generateId();
        mealMap.put(id.get(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, id.get()));
        generateId();
        mealMap.put(id.get(), new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, id.get()));
    }

    @Override
    public void update(LocalDateTime dateTime, String description, int calories, int id) {
        mealMap.put(id, new Meal(dateTime, description, calories, id));
    }

    @Override
    public void add(LocalDateTime dateTime, String description, int calories) {
        generateId();
        mealMap.put(id.get(), new Meal(dateTime, description, calories, id.get()));
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public List<Meal> getListMeal() {
        return new ArrayList<>(mealMap.values());
    }
}
