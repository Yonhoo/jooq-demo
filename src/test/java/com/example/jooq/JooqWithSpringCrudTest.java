package com.example.jooq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import com.example.jooq.flyway.tables.daos.AuthorDao;
import com.example.jooq.flyway.tables.pojos.Author;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JooqWithSpringCrudTest extends BaseUnitTest {
    @Autowired
    AuthorDao authorDao;

    @Test
    void test_spring_jooq_dao_query() {

        List<Author> authors = authorDao.findAll();
        assertTrue(authors.size() > 0);
    }

    @Test
    void test_jooq_dao_insert() {
        Author author = new Author(123423,"firstName","lastName",LocalDate.now(),2022,"github.com");

        authorDao.insert(author);
        assertEquals(1, authorDao.fetchById(123423).size());
        assertEquals("firstName",authorDao.fetchById(123423).get(0).getFirstName());

    }

}
