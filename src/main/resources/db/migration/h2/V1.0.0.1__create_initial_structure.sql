drop table if exists travel;
drop table if exists driver;
drop table if exists passenger;

create table driver
(
    drv_id            uuid         not null default random_uuid(),
    drv_name          varchar(255) not null,
    drv_birthdate     date         not null,
    constraint pk_driver primary key (drv_id)
);

create table passenger
(
    psg_id            uuid         not null default random_uuid(),
    psg_name          varchar(255) not null,
    constraint pk_passenger primary key (psg_id)
);

create table travel
(
    trv_id            uuid         not null default random_uuid(),
    trv_origin        varchar(255) not null,
    trv_destination   varchar(255) not null,
    trv_status        varchar(50)  not null,
    trv_created_at    timestamp    not null,
    trv_psg_id        uuid         not null,
    trv_drv_id        uuid         null,
    constraint pk_travel primary key (trv_id),
    constraint fk_trv_psg_id foreign key (trv_psg_id) references passenger(psg_id) on update cascade,
    constraint fk_trv_drv_id foreign key (trv_drv_id) references driver(drv_id) on update cascade
);