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
    'ID' BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    'CODE' VARCHAR(255),
    'LOCATION' VARCHAR(255),
    'START' DATET,
    'END' DATE,
    'OBJECTIVE' VARCHAR(255),
    'OUTCOME' VARCHAR(255)
 );

CREATE TABLE AGENT(
    'ID' BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    'NAME' VARCHAR(255),
    'BORN' DATE,
    'DIED' DATE,
    'LEVEL' SMALLINT
);

CREATE TABLE INVOLVEMENT(
    'ID' BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    'AGENT' BIGINT REFERENCES AGENT(ID)
    'MISSION' BIGINT REFERENCES MISSION(ID)
    'START' DATE,
    'END' DATE
);

