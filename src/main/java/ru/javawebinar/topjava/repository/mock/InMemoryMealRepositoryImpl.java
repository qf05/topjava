package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.get(id).getUserId() == userId && repository.remove(id, repository.get(id));
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.get(id).getUserId() == userId) {
            return repository.get(id);
        } else return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().filter(meal -> meal.getUserId() == userId)
                .sorted(
                        (o1, o2) -> {
                            int t;
                            if (o1.getDateTime().isBefore(o2.getDateTime())) {
                                t = 1;
                            } else {
                                if (o1.getDateTime().isAfter(o2.getDateTime())) {
                                    t = -1;
                                } else t = 0;
                            }
                            return t;
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFiltred(int userId, LocalDate localDateFrom, LocalDate localDateTo) {
        return getAll(userId).stream().filter(meal -> DateTimeUtil.isBetween(meal.getDate(), localDateFrom, localDateTo)).collect(Collectors.toList());
    }
}

