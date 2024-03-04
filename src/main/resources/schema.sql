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
    uuid      VARCHAR(36) NOT NULL UNIQUE,
    title     VARCHAR(55) NOT NULL,
    isprivate BIT         NOT NULL,
    flashyuserid   INT         NOT NULL,
    CONSTRAINT fk_flashyuserid FOREIGN KEY (flashyuserid) REFERENCES flashyuser (id)
)


CREATE TABLE flashcard
(
    id          INT          NOT NULL PRIMARY KEY IDENTITY(1,1),
    uuid        VARCHAR(36)  NOT NULL UNIQUE,
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
    CONSTRAINT fk_carddeckid1 FOREIGN KEY (carddeckid) REFERENCES carddeck (id),

    flashyuserid     INT,
    CONSTRAINT fk_flashyuserid1 FOREIGN KEY (flashyuserid) REFERENCES flashyuser (id),

    createdat datetime DEFAULT(getdate()),
    uuid       VARCHAR(36) NOT NULL UNIQUE,
)

CREATE VIEW extendedcarddeckview AS
SELECT *,
       (SELECT username FROM flashyuser WHERE flashyuser.id = carddeck.flashyuserid) as username,
       (SELECT COUNT(*) FROM flashcard  WHERE carddeckid = carddeck.id) AS cardcount,
       (SELECT COUNT(*) FROM userhaslike  WHERE carddeckid = carddeck.id) AS likecount,
       (SELECT COUNT(*) FROM userhasfavorite  WHERE carddeckid = carddeck.id) AS favoritecount
FROM carddeck;

CREATE VIEW extendedcommentview AS
SELECT *,
       (SELECT username FROM flashyuser WHERE flashyuser.id = comment.flashyuserid) as username,
         (SELECT uuid FROM carddeck WHERE carddeck.id = comment.carddeckid) as carddeckuuid
    FROM comment;