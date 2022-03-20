package com.example.jooq;

import static com.example.jooq.flyway.Tables.AUTHOR;
import static com.example.jooq.flyway.Tables.BOOK;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JooqApplicationTests {

	private DSLContext dslContext;
	private Connection connection;

	@BeforeEach
	private void setUp() throws Exception {
		connection = DriverManager.getConnection("jdbc:h2:~/flyway-test", "sa", "");
		connection.setAutoCommit(false);
		dslContext = DSL.using(connection);
	}

	@AfterEach
	public void rollbackTransaction() throws SQLException {
		connection.rollback();
	}

	@Test
	public void testQueryingAfterMigration() throws Exception {
		Result<?> result = dslContext
				.select(
						AUTHOR.FIRST_NAME,
						AUTHOR.LAST_NAME,
						BOOK.ID,
						BOOK.TITLE)
				.from(AUTHOR)
				.join(BOOK)
				.on(AUTHOR.ID.eq(BOOK.AUTHOR_ID))
				.orderBy(BOOK.ID.asc())
				.fetch();
		assertEquals(4, result.size());
		assertEquals(asList(1, 2, 3, 4), result.getValues(BOOK.ID));

	}

	@Test
	void test_insert_data_by_column_name() throws Exception {

		dslContext
				.insertInto(AUTHOR, AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, AUTHOR.DATE_OF_BIRTH,
						AUTHOR.YEAR_OF_BIRTH)
				.values(10, "f1", "l1", LocalDate.now(), 2022)
				.values(11, "f2", "l2", LocalDate.now(), 2022)
				.execute();
		Result<?> result = dslContext
				.select(AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
				.from(AUTHOR)
				.where(AUTHOR.ID.in(10, 11))
				.orderBy(AUTHOR.ID.desc())
				.fetch();

		assertEquals(asList("f2", "f1"), result.getValues(AUTHOR.FIRST_NAME));

	}

}
