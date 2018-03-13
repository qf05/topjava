package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal update(Meal meal, int userId) {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public Meal create(Meal meal, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFound(repository.delete(id, userId), String.valueOf(id));
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public Collection<Meal> getAllFiltred(int userId, LocalDate localDateFrom, LocalDate localDateTo) {
        return repository.getAllFiltred(userId, localDateFrom, localDateTo);
    }
}