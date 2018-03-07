package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.CrudForMeals;
import ru.javawebinar.topjava.dao.CrudForMealsFromMapImp;
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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/mealEdit.jsp";
    private static final String LIST_MEALS = "/meals.jsp";

    private CrudForMeals dataBase;

    @Override
    public void init() throws ServletException {
        super.init();
        dataBase = new CrudForMealsFromMapImp();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String forward = LIST_MEALS;
        String action = request.getParameter("action");

        if (action == null || action.equalsIgnoreCase("listMeals")) {
            request.setAttribute("listMeals", getAllMeals());
        } else {
            if (action.equalsIgnoreCase("delete")) {
                dataBase.delete(Integer.parseInt(request.getParameter("Id")));
                response.sendRedirect("/meals");
                return;
            } else {
                if (action.equalsIgnoreCase("edit")) {
                    forward = INSERT_OR_EDIT;
                    request.setAttribute("meal", dataBase.getId(Integer.parseInt(request.getParameter("Id"))));
                } else {
                    forward = INSERT_OR_EDIT;
                }
            }
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime date = TimeUtil.timeFormatter(req.getParameter("addDate"));
        Meal meal = new Meal(0, date, req.getParameter("addDis"), Integer.parseInt(req.getParameter("addCalories")));

        if (req.getParameter("id").isEmpty()) {
            dataBase.add(meal);
        } else {
            meal.setId(Integer.parseInt(req.getParameter("id")));
            dataBase.update(meal);
        }
        req.setAttribute("listMeals", getAllMeals());
        req.getRequestDispatcher(LIST_MEALS).forward(req, resp);
    }

    private List<MealWithExceed> getAllMeals() {
        return MealsUtil.getFilteredWithExceeded(dataBase.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
