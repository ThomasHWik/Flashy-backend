CREATE TABLE flashyuser
(
    id       INT         NOT NULL PRIMARY KEY IDENTITY(1,1),
    username varchar(55) NOT NULL UNIQUE,
    isadmin  BIT,
    password varchar(255)
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
    flashyuserid   INT         NOT NULL,
    CONSTRAINT fk_flashyuserid FOREIGN KEY (flashyuserid) REFERENCES flashyuser (id)
)


CREATE TABLE flashcard
(
    id          INT          NOT NULL PRIMARY KEY IDENTITY(1,1),
    uuid        VARCHAR(36)  NOT NULL,
    question    VARCHAR(255) NOT NULL,
    answer      VARCHAR(255) NOT NULL,
    carddeckid INT          NOT NULL,
    CONSTRAINT fk_carddeckid FOREIGN KEY (carddeckid) REFERENCES carddeck (id)

)

CREATE TABLE userhaslike
 (
     id          INT NOT NULL PRIMARY KEY IDENTITY(1,1),
     flashyuserid     INT NOT NULL,
     CONSTRAiNT fk_flashyuserid0 FOREIGN KEY (flashyuserid) REFERENCES flashyuser (id),
     carddeckid INT NOT NULL,
     CONSTRAINT fk_carddeckid0 FOREIGN KEY (carddeckid) REFERENCES carddeck (id),

    CONSTRAINT userhaslikeunique UNIQUE(flashyuserid, carddeckid)
 )

CREATE TABLE carddeckHasCategory
(
    id          INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    categoryid INT NOT NULL,
    CONSTRAINT fk_categoryid FOREIGN KEY (categoryid) REFERENCES category (id),
    carddeckid INT NOT NULL,
    CONSTRAINT fk_carddeckid FOREiGN KEY (carddeckid) REFERENCES carddeck (id),

)


CREATE TABLE userhasfavorite
(
    id             INT NOT NULL PRIMARY KEY IDENTITY(1,1),

    flashyuserid     iNT NOT NULL,
    CONSTRAINT fk_flashyuserid FOREIGN KEY (flashyuserid) REFERENCES flashyuser (id),
    carddeckid INT NOT NULL,
    CONSTRAINT fk_carddeckid FOREIGN KEY (carddeckid) REFERENCES carddeck (id),

    CONSTRAINT userhasfavoriteunique UNIQUE(flashyuserid, carddeckid)
)

CREATE TABLE comment
(
    id          INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    comment     VARCHAR(300),
    carddeckid INT NOT NULL,
    CONSTRAINT fk_carddeckId FOREIGN KEY (carddeckid) REFERENCES carddeck (id),
    userid     INT,
    CONSTRAINT fk_flashyuserid FOREIGN KEY (userid) REFERENCES flashyuser (id),
)