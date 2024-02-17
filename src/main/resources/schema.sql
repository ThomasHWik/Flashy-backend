CREATE TABLE flashyuser
(
    id       INT         NOT NULL PRIMARY KEY IDENTITY(1,1),
    username varchar(55) NOT NULL UNIQUE,
    isadmin  BIT,
    password varchar(55)
)

CREATE TABLE category
(
    id   INT         NOT NULL PRIMARY KEY IDENTITY(1,1),
    name varchar(55) NOT NULL UNIQUE
)

CREATE TABLE carddeck
(
    id        INT         NOT NULL PRIMARY KEY IDENTITY(1,1),
    uuid      VARCHAR(36) NOT NULL,
    title     VARCHAR(55) NOT NULL,
    isprivate BIT         NOT NULL,
    flashyuser_id   INT         NOT NULL,
    CONSTRAINT fk_flashyuser_id FOREIGN KEY (flashyuser_id) REFERENCES flashyuser (id)
)


CREATE TABLE flashcard
(
    id          INT          NOT NULL PRIMARY KEY IDENTITY(1,1),
    uuid        VARCHAR(36)  NOT NULL,
    question    VARCHAR(255) NOT NULL,
    answer      VARCHAR(255) NOT NULL,
    carddeck_id INT          NOT NULL,
    CONSTRAINT fk_carddeck_id FOREIGN KEY (carddeck_id) REFERENCES carddeck (id)

)

CREATE TABLE user_has_like
(
    flashyuser_id     INT NOT NULL,
    CONSTRAINT fk_flashyuser_id FOREIGN KEY (flashyuser_id) REFERENCES flashyuser (id),
    carddeck_id INT NOT NULL,
    CONSTRAINT fk_carddeck_id FOREIGN KEY (carddeck_id) REFERENCES carddeck (id)
)

CREATE TABLE carddeck_has_category
(
    category_id INT NOT NULL,
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category (id),
    carddeck_id INT NOT NULL,
    CONSTRAINT fk_carddeck_id FOREIGN KEY (carddeck_id) REFERENCES carddeck (id),
)


CREATE TABLE user_has_favourite
(
    flashyuser_id     INT NOT NULL,
    CONSTRAINT fk_flashyuser_id FOREIGN KEY (flashyuser_id) REFERENCES flashyuser (id),
    carddeck_id INT NOT NULL,
    CONSTRAINT fk_carddeck_id FOREIGN KEY (carddeck_id) REFERENCES carddeck (id),
)

CREATE TABLE comment
(
    id          INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    comment     VARCHAR(300),
    carddeck_id INT NOT NULL,
    CONSTRAINT fk_carddeck_id FOREIGN KEY (carddeck_id) REFERENCES carddeck (id),
    user_id     INT,
    CONSTRAINT fk_flashyuser_id FOREIGN KEY (user_id) REFERENCES flashyuser (id),
)