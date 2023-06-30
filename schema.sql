
    create table `dataprovider_product` (
        price integer not null,
        id bigint not null,
        company varchar(255),
        description varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table dataprovider_product_seq (
        next_val bigint
    ) engine=InnoDB;

    insert into dataprovider_product_seq values ( 1 );
