package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

public abstract class AbstractUserJpaDatajpaTest extends  AbstractUserServiceTest{
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() throws  Exception{
        super.setUp();
        jpaUtil.clear2ndLevelHibernateCache();
    }
}
