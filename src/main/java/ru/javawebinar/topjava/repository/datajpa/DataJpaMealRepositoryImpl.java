package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Sort SORT = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private CrudUserRepository userRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew()) {
            Meal m = get(meal.getId(), userId);
            if (m != null) {
                meal.setUser(m.getUser());
                return crudRepository.save(meal);
            } else return null;
        }
        meal.setUser(userRepository.getOne(userId));
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUser_Id(id, userId) != 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.findById(id).orElse(null);
        if (meal != null && meal.getUser().getId() == userId) {
            User user = new User(meal.getUser());
            meal.setUser(user);
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findByUser_Id(userId, SORT);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findByDateTimeBetweenAndUser_Id(startDate, endDate, userId, SORT);
    }
}
