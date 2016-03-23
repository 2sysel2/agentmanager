/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Jaromir Sys
 * Created: 17.3.2016
 */



CREATE TABLE MISSION (
    "id" BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "code" VARCHAR(255),
    "location" VARCHAR(255),
    "start" TIMESTAMP,
    "end" TIMESTAMP,
    "objective" VARCHAR(255),
    "outcome" VARCHAR(255)
 );

CREATE TABLE AGENT(
    "id" BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "name" VARCHAR(255),
    "born" DATE,
    "died" DATE,
    "level" SMALLINT
);

CREATE TABLE INVOLVEMENT(
    "id" BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "agent" BIGINT REFERENCES AGENT("id"),
    "mission" BIGINT REFERENCES MISSION("id"),
    "start" TIMESTAMP,
    "end" TIMESTAMP
);

