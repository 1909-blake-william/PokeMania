CREATE SEQUENCE trainer_id_seq;

CREATE TABLE trainers (
    trainer_id NUMBER PRIMARY KEY,
    trainer_name VARCHAR2(50) UNIQUE NOT NULL,
    trainer_password VARCHAR2(50) NOT NULL,
    first_name VARCHAR2(50),
    last_name VARCHAR2(50),
    badges NUMBER NOT NULL,
    wins NUMBER,
    losses NUMBER
);

CREATE SEQUENCE pokemon_id_seq;
CREATE TABLE pokemon (
    pokemon_id NUMBER PRIMARY KEY,
    trainer_id NUMBER NOT NULL REFERENCES trainers(trainer_id),
    pokedex_id NUMBER NOT NULL,
    pokemon_level NUMBER DEFAULT 1,
    pokemon_hp NUMBER NOT NULL,
    pokemon_att NUMBER NOT NULL,
    pokemon_def NUMBER NOT NULL,
    pokemon_speed NUMBER NOT NULL,
    pokemon_type1 VARCHAR2(20) NOT NULL,
    pokemon_type2 VARCHAR2(20),
    front_image VARCHAR2(100) NOT NULL,
    back_image VARCHAR(100) NOT NULL
);

CREATE TABLE pokemon_team (
    trainer_id NUMBER NOT NULL REFERENCES trainers(trainer_id),
    pokemon_id NUMBER NOT NULL REFERENCES pokemon(pokemon_id),
    constraint pokemon_team_pk PRIMARY KEY(trainer_id, pokemon_id)
);

CREATE TABLE friends (
    trainer_id1 NUMBER NOT NULL REFERENCES trainers(trainer_id),
    trainer_id2 NUMBER NOT NULL REFERENCES trainers(trainer_id),
    constraint friends_pk PRIMARY KEY(trainer_id1, trainer_id2)
);


CREATE SEQUENCE trade_id_seq;
CREATE TABLE trade_requests(
    trade_id NUMBER PRIMARY KEY,
    trainer_id1 NUMBER NOT NULL REFERENCES trainers(trainer_id),
    pokemon_id1 NUMBER NOT NULL REFERENCES pokemon(pokemon_id),
    trainer_id2 NUMBER NOT NULL REFERENCES trainers(trainer_id),
    pokemon_id2 NUMBER NOT NULL REFERENCES pokemon(pokemon_id)
);



CREATE OR REPLACE TRIGGER trainer_id_trig
BEFORE INSERT ON trainers
FOR EACH ROW
BEGIN
    SELECT trainer_id_seq.nextval INTO :new.trainer_id FROM dual;
END;


CREATE OR REPLACE TRIGGER pokemon_id_trig
BEFORE INSERT ON pokemon
FOR EACH ROW
BEGIN
    SELECT pokemon_id_seq.nextval INTO :new.pokemon_id FROM dual;
END;


CREATE OR REPLACE TRIGGER trade_id_trig
BEFORE INSERT ON trade_requests
FOR EACH ROW
BEGIN
    SELECT trade_id_seq.nextval INTO :new.trade_id FROM dual;
END;






