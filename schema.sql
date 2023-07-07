
    create table `dataprovider_person` (
        age integer not null,
        id bigint not null,
        family varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table dataprovider_person_seq (
        next_val bigint
    ) engine=InnoDB;

    insert into dataprovider_person_seq values ( 1 );
