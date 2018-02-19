package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }


    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> map = new HashMap<>();
        final LocalDate[] tld = new LocalDate[1];
        mealList.forEach(i->{tld[0] = toLocalDate(i.getDateTime());
            if (map.containsKey(tld[0])) {
                map.put(tld[0], map.get(tld[0]) + i.getCalories());
            } else {
                map.put(tld[0], i.getCalories());
            }
        });
        return mealList.stream().filter(i->toLocalTime(i.getDateTime()).isAfter(startTime)&&toLocalTime(i.getDateTime()).isBefore(endTime))
                .map(i->new UserMealWithExceed(i.getDateTime(),i.getDescription(),i.getCalories(),
                        (map.get(toLocalDate(i.getDateTime()))<=caloriesPerDay)))
                .collect(Collectors.toList());
    }

    private static LocalDate toLocalDate(LocalDateTime ldt){
        return ldt.toLocalDate();
    }

    private static LocalTime toLocalTime(LocalDateTime ldt){
        return ldt.toLocalTime();
    }
}
