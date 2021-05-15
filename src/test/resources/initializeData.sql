DROP TABLE SECTION;
DROP TABLE LINE;
DROP TABLE STATION;

CREATE TABLE IF NOT EXISTS STATION
(
    id
    BIGINT
    AUTO_INCREMENT
    NOT
    NULL,
    name
    VARCHAR
(
    255
) NOT NULL UNIQUE,
    PRIMARY KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS SECTION
(
    id
    BIGINT
    AUTO_INCREMENT
    NOT
    NULL,
    line_id
    BIGINT
    NOT
    NULL,
    up_station_id
    BIGINT
    NOT
    NULL,
    down_station_id
    BIGINT
    NOT
    NULL,
    distance
    INT,

    PRIMARY
    KEY
(
    id
)
    );

CREATE TABLE IF NOT EXISTS LINE
(
    id
    BIGINT
    AUTO_INCREMENT
    NOT
    NULL,
    name
    VARCHAR
(
    255
) NOT NULL UNIQUE,
    color VARCHAR
(
    20
) NOT NULL UNIQUE,
    PRIMARY KEY
(
    id
)
    );

INSERT INTO LINE(name, color)
VALUES ('OneLine', 'BLUE');
INSERT INTO STATION(name)
VALUES ('신설동역');
INSERT INTO STATION(name)
VALUES ('동묘앞역');
INSERT INTO STATION(name)
VALUES ('동대문역');