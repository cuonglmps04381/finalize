create table role (
                      ROLE_ID                             bigint NOT NULL AUTO_INCREMENT,
                      ROLE_NAME                           varchar(256) NOT NULL,
                      CREATED_DATE                        DATE NOT NULL,
                      CREATED_BY                          varchar(64) NOT NULL,
                      UPDATED_DATE                        DATE NOT NULL,
                      UPDATED_BY                          varchar(64) NOT NULL,
                      REMOVAL_FLAG                        bigint(1) DEFAULT 0 NOT NULL,
                     primary key (ROLE_ID))
    ENGINE=InnoDB
AUTO_INCREMENT = 1;

create table user (
    USER_ID                             bigint NOT NULL AUTO_INCREMENT,
    EMAIL                               varchar(50) NOT NULL,
    PASSWORD                            varchar(250) NOT NULL,
    FIRST_NAME                          varchar(50) NOT NULL,
    LAST_NAME                           varchar(50) NOT NULL,
    CREATED_DATE                        DATE NOT NULL ,
    CREATED_BY                          varchar(50)NOT NULL,
    UPDATED_DATE                        DATE NOT NULL ,
    UPDATED_BY                          varchar(50) NOT NULL,
    REMOVAL_FLAG                        bigint(1) DEFAULT 0 NOT NULL,
     primary key (USER_ID))

    ENGINE=InnoDB
AUTO_INCREMENT = 1;


create table role_user (id_user bigint not null, id_role bigint not null, primary key (id_user, id_role));
alter table role_user add constraint FK_role foreign key (id_role) references role (ROLE_ID);
alter table role_user add constraint FK_user foreign key (id_user) references user (USER_ID);
