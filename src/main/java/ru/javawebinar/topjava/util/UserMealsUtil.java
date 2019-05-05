package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Return filtered list with correctly exceeded field by cycle
        Map<LocalDate, Integer> totalCaloriesPerDayMap = new HashMap<>();
        for (UserMeal userMeal: mealList) {
                LocalDate date = userMeal.getDateTime().toLocalDate();
                totalCaloriesPerDayMap.put(date, totalCaloriesPerDayMap.getOrDefault(date, 0) + userMeal.getCalories());
        }

        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal userMeal: mealList) {
            LocalDateTime dateTime = userMeal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(dateTime, userMeal.getDescription(), userMeal.getCalories(), totalCaloriesPerDayMap.get(dateTime.toLocalDate()) > caloriesPerDay));

            }
        }
        return userMealWithExceedList;
    }
}
