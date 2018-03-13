package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(i->save(i,i.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        } else {
            // treat case: update, but absent in storage
            if (get(meal.getId(), userId) != null) {
                meal.setUserId(userId);
                return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
            } else
                return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = get(id, userId);
        return meal != null && repository.remove(id, meal);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return meal == null || meal.getUserId() != userId ? null : meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFiltred(int userId, LocalDate localDateFrom, LocalDate localDateTo) {
        return getAll(userId).stream().filter(meal -> DateTimeUtil.isBetween(meal.getDate(), localDateFrom, localDateTo))
//                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

