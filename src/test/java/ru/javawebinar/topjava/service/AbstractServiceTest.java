package ru.javawebinar.topjava.service;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.Profiles;

import java.lang.reflect.Method;

import static ru.javawebinar.topjava.TimingRules.*;
import static ru.javawebinar.topjava.util.ValidationUtil.getRootCause;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract public class AbstractServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    @BeforeClass
    public static void infoStart(){
        start();
    }

    @BeforeMethod
    public static void startTime(Method method){
        beginTest(method.getName());
    }

    @AfterMethod
    public static void infoadd(){
        finishTest();
    }

    @AfterClass
    public static void infoFinish(){
        end();
    }

    @Autowired
    public Environment env;

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    public boolean isJpaBased() {
        return env.acceptsProfiles(Profiles.JPA, Profiles.DATAJPA);
    }

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    public <T extends Throwable> void validateRootCause(Runnable runnable, Class<T> exceptionClass) {
        try {
            runnable.run();
            Assert.fail("Expected " + exceptionClass.getName());
        } catch (Exception e) {
            Assert.assertEquals(getRootCause(e).getClass(),exceptionClass);
        }
    }
}