package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.Profiles.HSQL_DB;

@Repository
@Profile(HSQL_DB)
public class HsqldbJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {

    @Override
    public String getSuitableDateTime(LocalDateTime t) {
        return Timestamp.valueOf(t).toString();
    }
}
