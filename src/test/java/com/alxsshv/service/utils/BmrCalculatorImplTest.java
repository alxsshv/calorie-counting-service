package com.alxsshv.service.utils;


import com.alxsshv.model.Sex;
import com.alxsshv.model.User;
import com.alxsshv.service.validation.UserViolationsConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class BmrCalculatorImplTest {
    private final BmrCalculator bmrCalculator = new BmrCalculatorImpl();
    private static final double EPSILON = 0.000001d;

    @Test
    @DisplayName("Test calculate when user is man with normal parameters then get valid result")
    public void testCalculate_whenUserIsManWithNormalParameters_thenGetValidResult() {
        double expectedCalorieNorm = 1855.0d;
        User user = new User();
        user.setSex(Sex.MAN);
        user.setHeight(180);
        user.setWeight(85);
        user.setAge(25);
        double actualCalorieNorm = bmrCalculator.calculate(user);
        Assertions.assertEquals(expectedCalorieNorm, actualCalorieNorm, EPSILON);
    }

    @Test
    @DisplayName("Test calculate when user is man with low limit parameters then get valid result")
    public void testCalculate_whenUserIsManWithLowLimitParameters_thenGetValidResult() {
        double expectedCalorieNorm = 322.5d;
        User user = new User();
        user.setSex(Sex.MAN);
        user.setHeight(UserViolationsConstants.MIN_USER_HEIGHT);
        user.setWeight(UserViolationsConstants.MIN_USER_WEIGHT);
        user.setAge(UserViolationsConstants.MIN_USER_AGE);
        double actualCalorieNorm = bmrCalculator.calculate(user);
        Assertions.assertEquals(expectedCalorieNorm, actualCalorieNorm, EPSILON);
    }

    @Test
    @DisplayName("Test calculate when user is man with upper limits parameters then get valid result")
    public void testCalculate_whenUserIsManWithUpperLimitParameters_thenGetValidResult() {
        double expectedCalorieNorm = 3155.0d;
        User user = new User();
        user.setSex(Sex.MAN);
        user.setHeight(UserViolationsConstants.MAX_USER_HEIGHT);
        user.setWeight(UserViolationsConstants.MAX_USER_WEIGHT);
        user.setAge(UserViolationsConstants.MAX_USER_AGE);
        double actualCalorieNorm = bmrCalculator.calculate(user);
        Assertions.assertEquals(expectedCalorieNorm, actualCalorieNorm, EPSILON);
    }

    @Test
    @DisplayName("Test calculate when user is woman with normal parameters then get valid result")
    public void testCalculate_whenUserIsWomanWithNormalParameters_thenGetValidResult() {
        double expectedCalorieNorm = 1689.0d;
        User user = new User();
        user.setSex(Sex.WOMAN);
        user.setHeight(180);
        user.setWeight(85);
        user.setAge(25);
        double actualCalorieNorm = bmrCalculator.calculate(user);
        Assertions.assertEquals(expectedCalorieNorm, actualCalorieNorm, EPSILON);
    }

    @Test
    @DisplayName("Test calculate when user is woman with low limit parameters then get valid result")
    public void testCalculate_whenUserIsWomanWithLowLimitParameters_thenGetValidResult() {
        double expectedCalorieNorm = 156.5d;
        User user = new User();
        user.setSex(Sex.WOMAN);
        user.setHeight(UserViolationsConstants.MIN_USER_HEIGHT);
        user.setWeight(UserViolationsConstants.MIN_USER_WEIGHT);
        user.setAge(UserViolationsConstants.MIN_USER_AGE);
        double actualCalorieNorm = bmrCalculator.calculate(user);
        Assertions.assertEquals(expectedCalorieNorm, actualCalorieNorm, EPSILON);
    }

    @Test
    @DisplayName("Test calculate when user is woman with upper limits parameters then get valid result")
    public void testCalculate_whenUserIsWomanWithUpperLimitParameters_thenGetValidResult() {
        double expectedCalorieNorm = 2989.0d;
        User user = new User();
        user.setSex(Sex.WOMAN);
        user.setHeight(UserViolationsConstants.MAX_USER_HEIGHT);
        user.setWeight(UserViolationsConstants.MAX_USER_WEIGHT);
        user.setAge(UserViolationsConstants.MAX_USER_AGE);
        double actualCalorieNorm = bmrCalculator.calculate(user);
        Assertions.assertEquals(expectedCalorieNorm, actualCalorieNorm, EPSILON);
    }

    @Test
    @DisplayName("Test calculate user man and all parameters are zero then get valid result")
    public void testCalculate_whenUserIsManAndAllParametersAreZero_thenGetValidResult() {
        double expectedCalorieNorm = 5.0d;
        User user = new User();
        user.setSex(Sex.MAN);
        user.setHeight(0);
        user.setWeight(0);
        user.setAge(0);
        double actualCalorieNorm = bmrCalculator.calculate(user);
        Assertions.assertEquals(expectedCalorieNorm, actualCalorieNorm, EPSILON);
    }

    @Test
    @DisplayName("Test calculate when user woman and all parameters are zero then get valid result")
    public void testCalculate_whenUserIsWomanAndAllParametersAreZero_thenGetValidResult() {
        double expectedCalorieNorm = -161.0d;
        User user = new User();
        user.setSex(Sex.WOMAN);
        user.setHeight(0);
        user.setWeight(0);
        user.setAge(0);
        double actualCalorieNorm = bmrCalculator.calculate(user);
        Assertions.assertEquals(expectedCalorieNorm, actualCalorieNorm, EPSILON);
    }

}
