package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.DataBase;
import ru.javawebinar.topjava.DAO.DataBaseListIMP;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

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
    private DataBase dataBase = new DataBaseListIMP();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealWithExceed> mealWithExceedsList = MealsUtil.getFilteredWithExceeded(dataBase.getListMeal(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("listMeals", mealWithExceedsList);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> parametrNames = Collections.list(req.getParameterNames());
        try {
            if (parametrNames.contains("del")) {
                dataBase.delete(Integer.parseInt(req.getParameter("del")));
                doGet(req, resp);
            } else {
                if (parametrNames.contains("addDate") && parametrNames.contains("addDis") && parametrNames.contains("addColories")) {
                    DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    LocalDateTime date = LocalDateTime.parse(req.getParameter("addDate"), dateTimeFormat);
                    dataBase.add(date, req.getParameter("addDis"), Integer.parseInt(req.getParameter("addColories")));
                    doGet(req, resp);
                } else {
                    if (parametrNames.contains("reDate") && parametrNames.contains("reDis") && parametrNames.contains("reColories") && parametrNames.contains("id")) {
                        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                        LocalDateTime date = LocalDateTime.parse(req.getParameter("reDate"), dateTimeFormat);
                        dataBase.update(date, req.getParameter("reDis"), Integer.parseInt(req.getParameter("reColories")), Integer.parseInt(req.getParameter("id")));
                        doGet(req, resp);
                    } else {
                        doGet(req, resp);
                    }
                }
            }
        } catch (NumberFormatException e) {
            doGet(req, resp);
        }
    }
}
