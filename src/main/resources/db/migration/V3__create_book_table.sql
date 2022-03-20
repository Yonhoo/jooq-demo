
CREATE TABLE book (
  id INT NOT NULL,
  author_id INT NOT NULL,
  title VARCHAR(400) NOT NULL,

  CONSTRAINT pk_t_book PRIMARY KEY (id)
);