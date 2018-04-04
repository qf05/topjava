package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    int deleteByIdAndUser_Id(int id, int userId);

    @Override
    Meal save(Meal meal);

    @Override
    Optional<Meal> findById(Integer id);

    List<Meal> findByUser_IdOrderByDateTimeDesc(int userId);

    List<Meal> findByDateTimeBetweenAndUser_IdOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, Integer userId);

    @Query("SELECT m FROM Meal m inner join fetch m.user u WHERE m.id=:id AND m.user.id=:user_id")
    Meal getMealWidthUser(@Param("id") int id,@Param("user_id") int userId);
}
