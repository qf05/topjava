package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
//    int delete(@Param("id") int id,@Param("userId") int userId);

    @Transactional
    @Modifying
    int deleteByIdAndUser_Id(int id, int userId);

    @Override
    @Transactional
    Meal save(Meal meal);

    @Override
    Optional<Meal> findById(Integer id);

    // @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> findByUser_Id(int userId, Sort sort);

    List<Meal> findByDateTimeBetweenAndUser_Id(LocalDateTime startDate, LocalDateTime endDate, Integer userId, Sort sort);

//    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate " +
//            "AND :endDate ORDER BY m.dateTime DESC")
//    List<Meal> findAllBeetwen(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

}
