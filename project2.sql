create table if not exists videos(
    bv varchar(100) primary key,
    title varchar(100),
    ownerMid  bigint,
--     constraint fk1 foreign key (ownerMid) references user_(mid),
    ownerName varchar(100),
    commitTime timestamp(0),
    reviewTime timestamp(0),
    publicTime timestamp(0),
    duration double precision,
    description varchar(10000),
    reviewer varchar(100),
    isReview boolean

--     constraint fk2 foreign key (reviewer) references user_(mid)

);
create table if not exists view(
    id serial primary key ,
    bv varchar(100) not null ,
    mid bigint,
    watchTime double precision
--     constraint fk1 foreign key (bv) references videos(bv)
--          on UPDATE cascade
--          on DELETE cascade ,
--     constraint fk2 foreign key (user_mid) references user_(mid)
--         on UPDATE cascade
--         on DELETE cascade ,
--         constraint uq4 unique (BV,userMid)

);


create table if not exists danmu(
    id serial primary key ,
    bv varchar(100) not null ,
    mid bigint,
    time float,
    content varchar(500),
    postTime timestamp
--     constraint fk1 foreign key (BV) references videos(BV),
--     constraint fk2 foreign key (mid) references user_(mid)
);
create table if not exists danmuLikeBy(
    id serial primary key ,
    danmuID integer,
    likeMid bigint
--     constraint fk1 foreign key (BV) references videos(BV),
--     constraint fk2 foreign key (mid) references user_(mid)
);

create table if not exists like_(
    id serial primary key ,
    BV varchar(100) not null ,
    mid bigint
--    constraint fk1 foreign key (BV) references videos(BV)
--         on DELETE cascade
--        on UPDATE cascade ,
--     constraint fk2 foreign key (user_mid) references user_(mid)
--         on DELETE cascade
--         on UPDATE cascade ,
--         constraint uq1 unique (BV,user_mid)
);
create table if not exists coin(
    id serial primary key ,
    BV varchar(100) not null ,
    mid bigint
--     constraint fk1 foreign key (BV) references videos(BV)
--         on DELETE cascade
--         on UPDATE cascade ,
--     constraint fk2 foreign key (user_mid) references user_(mid)
--         on DELETE cascade
--         on UPDATE cascade,
--         constraint uq2 unique (BV,user_mid)
);
create table if not exists favorite(
    id serial primary key ,
    BV varchar(100) not null ,
    mid bigint
--     constraint fk1 foreign key (BV) references videos(BV)
--         on DELETE cascade
--         on UPDATE cascade ,
--     constraint fk2 foreign key (user_mid) references user_(mid)
--         on DELETE cascade
--         on UPDATE cascade,
--         constraint uq3 unique (BV,user_mid)
);
