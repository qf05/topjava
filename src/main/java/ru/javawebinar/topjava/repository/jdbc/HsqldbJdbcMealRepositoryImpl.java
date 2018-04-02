package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.Profiles.HSQL_DB;

@Repository
@Profile(HSQL_DB)
public class HsqldbJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {

    @Autowired
    public HsqldbJdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(dataSource, jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        String s = DateTimeUtil.toString(meal.getDateTime()) + ":00";
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("date_time", s);
        return super.saveImpl(meal, userId, map);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        String begin = DateTimeUtil.toString(startDate) + ":00";
        String end = DateTimeUtil.toString(endDate) + ":00";
        return super.jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, begin, end);
    }

}
