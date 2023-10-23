create table if not exists user_(
    mid varchar(100) primary key  ,
--     name 用户名是否可以重复？
--可以吧
    name varchar(50) not null ,
    sex varchar(50),
    birthday varchar(50),
    level varchar(50),
    sign varchar(150),
    identity varchar(30) not null
);
create table if not exists following(
    id serial primary key ,
    user_mid varchar(100),
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
--     owner mid 视频所有者是只能有一个人吗？
--不知
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
    BV varchar(100),
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
    BV varchar(100),
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
    BV varchar(100),
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
    BV varchar(100),
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
--投币点赞收藏，同一个视频同一个人只能进行一次，（bv,id）唯一
--on UPDATE cascade on DELETE cascade ,作用：
--父表数据更新删除时，子表相应数据也会更新删除
create table if not exists danmu(
    id serial primary key ,
    BV varchar(100),
    mid varchar(100),
    time varchar(20),
    content varchar(500),
    --constraint fk1 foreign key (BV) references videos(BV),
    constraint fk2 foreign key (mid) references user_(mid)
);
