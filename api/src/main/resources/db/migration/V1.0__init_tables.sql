create table user_profile (
    id int primary key,
    username varchar(128) not null unique,
    first_name varchar(128),
    last_name varchar(128),
    department varchar(64),
    office_name varchar(128),
    team_name varchar(64),
    floor_number smallint,
    profile_pic_location varchar(128),
    profile_pic_name varchar(40),
    external_uuid uuid
);

create sequence user_id_sequence;

create table user_timeline (
    id int primary key,
    user_id int not null,
    start_date timestamp without time zone not null,
    end_date timestamp without time zone not null,
    external_uuid uuid
);

create sequence user_timeline_id_sequence;

create table break_request (
    id int primary key,
    initiator int,
    place varchar(128),
    comment text,
    external_uuid uuid
);

create sequence break_request_id_sequence;
