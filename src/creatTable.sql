create table if not exists user_(
    mid varchar(100) primary key  ,
    name varchar(50) not null ,
    sex varchar(50),
    birthday varchar(50),
    level varchar(50),
    sign varchar(150),
    identity varchar(30) not null
);
create table if not exists following(
    id serial primary key ,
    user_mid varchar(100) not null ,
    constraint fk1 foreign key (user_mid) references user_(mid)
        on UPDATE cascade
        on DELETE cascade ,
    following_mid varchar(100),
    constraint fk2 foreign key (following_mid)references user_(mid)
        on UPDATE cascade
        on DELETE cascade ,
    constraint uq unique (user_mid,following_mid)
);

create table if not exists videos(
    BV varchar(100) primary key,
    title varchar(100),
    owner_mid varchar(100) ,
    constraint fk1 foreign key (owner_mid) references user_(mid),
    owner_name varchar(100),
    commit_time timestamp(0),
    review_time timestamp(0),
    public_time timestamp(0),
    duration integer,
    description varchar(10000),
    reviewer varchar(100),
    constraint fk2 foreign key (reviewer) references user_(mid)
);

create table if not exists like_(
    id serial primary key ,
    BV varchar(100) not null ,
    user_mid varchar(100) ,
   constraint fk1 foreign key (BV) references videos(BV)
        on DELETE cascade
       on UPDATE cascade ,
    constraint fk2 foreign key (user_mid) references user_(mid)
        on DELETE cascade
        on UPDATE cascade ,
        constraint uq1 unique (BV,user_mid)
);
create table if not exists coin(
    id serial primary key ,
    BV varchar(100) not null ,
    user_mid varchar(100) ,
    constraint fk1 foreign key (BV) references videos(BV)
        on DELETE cascade
        on UPDATE cascade ,
    constraint fk2 foreign key (user_mid) references user_(mid)
        on DELETE cascade
        on UPDATE cascade,
        constraint uq2 unique (BV,user_mid)
);
create table if not exists favorite(
    id serial primary key ,
    BV varchar(100) not null ,
    user_mid varchar(100) ,
    constraint fk1 foreign key (BV) references videos(BV)
        on DELETE cascade
        on UPDATE cascade ,
    constraint fk2 foreign key (user_mid) references user_(mid)
        on DELETE cascade
        on UPDATE cascade,
        constraint uq3 unique (BV,user_mid)
);
create table if not exists view_time(
    id serial primary key ,
    BV varchar(100) not null ,
    user_mid varchar(100),
    watch_time integer,
    constraint fk1 foreign key (BV) references videos(BV)
         on UPDATE cascade
         on DELETE cascade ,
    constraint fk2 foreign key (user_mid) references user_(mid)
        on UPDATE cascade
        on DELETE cascade ,
        constraint uq4 unique (BV,user_mid)

);

create table if not exists danmu(
    id serial primary key ,
    BV varchar(100) not null ,
    mid varchar(100),
    time varchar(20),
    content varchar(500),
    constraint fk1 foreign key (BV) references videos(BV),
    constraint fk2 foreign key (mid) references user_(mid)
);

