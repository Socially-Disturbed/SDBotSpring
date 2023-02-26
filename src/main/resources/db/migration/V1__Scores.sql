create table sd_guest_score
(
    id serial not null primary key ,
    player_name text not null unique,
    score numeric,
    wins integer,
    adr integer,
    rank text
);

create table sd_score
(
    id serial not null primary key ,
    player_name text not null unique,
    score numeric,
    wins integer,
    adr integer,
    rank text
);