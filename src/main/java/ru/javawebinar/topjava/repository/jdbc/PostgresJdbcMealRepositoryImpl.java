package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;

@Repository
@Profile(POSTGRES_DB)
public class PostgresJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {

}
