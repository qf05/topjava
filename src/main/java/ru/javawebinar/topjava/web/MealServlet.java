package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CRUDForMeals;
import ru.javawebinar.topjava.dao.CRUDForMealsFromListIMP;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private CRUDForMeals dataBase = new CRUDForMealsFromListIMP();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealWithExceed> mealWithExceedsList = MealsUtil.getFilteredWithExceeded(dataBase.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("listMeals", mealWithExceedsList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        List<String> parametrNames = Collections.list(req.getParameterNames());

        try {
            if (parametrNames.contains("delete")) {
                dataBase.delete(Integer.parseInt(req.getParameter("del")));
                req.setAttribute("listMeals", MealsUtil.getFilteredWithExceeded(dataBase.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            }
            if (parametrNames.contains("add")) {
                LocalDateTime date = TimeUtil.timeFormatter(req.getParameter("addDate"));
                dataBase.add(new Meal(date,req.getParameter("addDis"),
                        Integer.parseInt(req.getParameter("addColories")),
                        CRUDForMealsFromListIMP.id.incrementAndGet()));

                req.setAttribute("listMeals", MealsUtil.getFilteredWithExceeded(dataBase.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            }
            if (parametrNames.contains("redact")) {
                LocalDateTime date = TimeUtil.timeFormatter(req.getParameter("reDate"));
                dataBase.getAll().forEach(i->{if (i.getDateTime().equals(date))
                dataBase.update(new Meal(date, req.getParameter("reDis"),
                        Integer.parseInt(req.getParameter("reColories")), i.getId())); });
                req.setAttribute("listMeals", MealsUtil.getFilteredWithExceeded(dataBase.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            req.setAttribute("listMeals", MealsUtil.getFilteredWithExceeded(dataBase.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
            req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }
}
