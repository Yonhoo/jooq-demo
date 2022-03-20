package com.example.jooq;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(JooqApplication.class)
@Transactional
@Rollback
public class BaseUnitTest {
    @Autowired
    DSLContext dslContext;
}