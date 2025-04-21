package com.alxsshv.controller;

import com.alxsshv.model.Goal;
import com.alxsshv.model.Sex;
import com.alxsshv.model.*;
import com.alxsshv.repository.DishRepository;
import com.alxsshv.repository.FoodIntakeRepository;
import com.alxsshv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class Initializer {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private FoodIntakeRepository foodIntakeRepository;

   // @PostConstruct
    public void init() {
        System.out.println("Заполнение БД...");

        User user1 = new User();
        user1.setName("Иван");
        user1.setEmail("ivan@world.com");
        user1.setAge(20);
        user1.setWeight(75);
        user1.setHeight(185);
        user1.setSex(Sex.MAN);
        user1.setGoal(Goal.KEEPING_FIT);
        user1.setCalorieNorm(50);
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("Сергей");
        user2.setEmail("sergey@world.com");
        user2.setAge(20);
        user2.setWeight(75);
        user2.setHeight(185);
        user2.setGoal(Goal.WEIGHT_LOSS);
        user2.setSex(Sex.MAN);
        user2.setCalorieNorm(1680);
        userRepository.save(user2);

        Dish dish1 = new Dish();
        dish1.setTitle("Блин");
        dish1.setCalorieContent(200);
        dish1.setProteinsAmount(1);
        dish1.setFatsAmount(1);
        dish1.setCarbohydratesAmount(1);
        dishRepository.save(dish1);

        Dish dish2 = new Dish();
        dish2.setTitle("Суп");
        dish2.setCalorieContent(100);
        dish2.setProteinsAmount(2);
        dish2.setFatsAmount(2);
        dish2.setCarbohydratesAmount(2);
        dishRepository.save(dish2);

        Dish dish3 = new Dish();
        dish3.setTitle("Каша гречневая");
        dish3.setCalorieContent(100);
        dish3.setProteinsAmount(3);
        dish3.setFatsAmount(3);
        dish3.setCarbohydratesAmount(3);
        dishRepository.save(dish3);

        ServingSize food1 = new ServingSize();
        food1.setDish(dish2);
        food1.setAmount(100);
        ServingSize food2 = new ServingSize();
        food2.setDish(dish2);
        food2.setAmount(200);

        FoodIntake foodIntake1 = new FoodIntake();
        foodIntake1.addServingSize(food1);
        foodIntake1.addServingSize(food2);
        foodIntake1.setDate(LocalDate.now());
        foodIntake1.setUser(user1);
        foodIntakeRepository.save(foodIntake1);

        ServingSize food3 = new ServingSize();
        food3.setDish(dish2);
        food3.setAmount(300);
        ServingSize food4 = new ServingSize();
        food4.setDish(dish3);
        food4.setAmount(400);
        FoodIntake foodIntake2 = new FoodIntake();
        foodIntake2.addServingSize(food3);
        foodIntake2.addServingSize(food4);
        foodIntake2.setDate(LocalDate.now().minusDays(2));
        foodIntake2.setUser(user1);
        foodIntakeRepository.save(foodIntake2);

        ServingSize food5 = new ServingSize();
        food5.setDish(dish1);
        food5.setAmount(800);
        ServingSize food6 = new ServingSize();
        food6.setDish(dish2);
        food6.setAmount(600);
        FoodIntake foodIntake3 = new FoodIntake();
        foodIntake3.addServingSize(food5);
        foodIntake3.addServingSize(food6);
        foodIntake3.setDate(LocalDate.now());
        foodIntake3.setUser(user2);
        foodIntakeRepository.save(foodIntake3);

        ServingSize food7 = new ServingSize();
        food7.setDish(dish1);
        food7.setAmount(8);
        ServingSize food8 = new ServingSize();
        food8.setDish(dish2);
        food8.setAmount(8);
        FoodIntake foodIntake4 = new FoodIntake();
        foodIntake4.addServingSize(food7);
        foodIntake4.addServingSize(food8);
        foodIntake4.setDate(LocalDate.now().minusDays(1));
        foodIntake4.setUser(user1);
        foodIntakeRepository.save(foodIntake4);


        ServingSize food9 = new ServingSize();
        food9.setDish(dish1);
        food9.setAmount(8);
        FoodIntake foodIntake5 = new FoodIntake();
        foodIntake5.addServingSize(food9);
        foodIntake5.setDate(LocalDate.now().minusDays(1));
        foodIntake5.setUser(user1);
        foodIntakeRepository.save(foodIntake5);
    }
}
