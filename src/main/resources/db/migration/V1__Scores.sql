create table warrior
(
    id serial not null primary key,
    player_name text not null unique,
    adr integer,
    rank text
);

create table guest_score
(
    id serial primary key,
    score numeric,
    wins integer,
    warriorid integer not null unique,
    constraint warrior_fk
        foreign key (warriorid)
        references warrior (id)
);

create table sd_score
(
    id serial primary key,
    score numeric,
    wins integer,
    warriorid integer not null unique ,
    constraint warrior_fk
        foreign key (warriorid)
        references warrior (id)
);
