# --- !Ups

create table UserProfile (
    userID VARCHAR(255) NOT NULL PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    fullName VARCHAR(255),
    email VARCHAR(255),
    avatarURL VARCHAR(255)
) DEFAULT CHARSET=utf8;

create table LoginInfo (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    providerID VARCHAR(255) NOT NULL,
    providerKey VARCHAR(255) NOT NULL
) DEFAULT CHARSET=utf8;

create table UserLoginInfo (
    userID VARCHAR(255) NOT NULL,
    loginInfoId BIGINT NOT NULL
) DEFAULT CHARSET=utf8;

create table PasswordInfo (
    hasher VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255),
    loginInfoId BIGINT NOT NULL
) DEFAULT CHARSET=utf8;

create table OAuth2Info (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    accesstoken VARCHAR(255) NOT NULL,
    tokentype VARCHAR(255),
    expiresin INTEGER,
    refreshtoken VARCHAR(255),
    logininfoid BIGINT NOT NULL
) DEFAULT CHARSET=utf8;


# --- !Downs

drop table if exists OAuth2Info;
drop table if exists PasswordInfo;
drop table if exists UserLoginInfo;
drop table if exists LoginInfo;
drop table if exists UserProfile;
