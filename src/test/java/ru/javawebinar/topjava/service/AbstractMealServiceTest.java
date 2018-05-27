package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public abstract class AbstractMealServiceTest extends AbstractServiceTest {

    @Autowired
    protected MealService service;

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test (expectedExceptions = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void create() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test (expectedExceptions = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

//    Так как нельзя добавить в проверку в expectedExceptionsMessageRegExp ErrorType.DATA_NOT_FOUND,
//    потому что ему не нравится не финальность сообщения, он не считает его финальным.
//      По этому использую try/catch
//      http://qaru.site/questions/427089/testng-how-to-test-for-mandatory-exceptions
//
//    private static final String ERROR = ".*"+ ErrorType.DATA_NOT_FOUND.name() +".*" +
//              NotFoundException.NOT_FOUND_EXCEPTION + ".*" + MEAL1_ID + ".*";
//
//    @Test (expectedExceptions = NotFoundException.class,
//            expectedExceptionsMessageRegExp = ERROR + "")
    @Test
    public void updateNotFound() throws Exception {
//        thrown.expect(NotFoundException.class);
//        thrown.expectMessage(containsString(ErrorType.DATA_NOT_FOUND.name()));
//        thrown.expectMessage(containsString(NotFoundException.NOT_FOUND_EXCEPTION));
//        thrown.expectMessage(containsString(String.valueOf(MEAL1_ID)));
        try {
            service.update(MEAL1, ADMIN_ID);
            Assert.fail();
        }catch (Exception e){
            if ((e instanceof NotFoundException) &&
                    e.getMessage().contains(ErrorType.DATA_NOT_FOUND.name())&&
                    e.getMessage().contains(NotFoundException.NOT_FOUND_EXCEPTION)&&
                    e.getMessage().contains(String.valueOf(MEAL1_ID))) {
            }else Assert.fail();
        }
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);
    }

    @Test(groups = "not_jdbc")
    @Transactional(propagation = Propagation.NEVER)
    public void testValidation() throws Exception {
        Assert.assertTrue(isJpaBased());
        validateRootCause(() -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Meal(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 9), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "Description", 5001), USER_ID), ConstraintViolationException.class);
    }
}