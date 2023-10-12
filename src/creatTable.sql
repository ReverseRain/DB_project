create table if not exists user(
    mid integer primary key unique ,
--     name 用户名是否可以重复？
    name varchar(20) not null ,
    sex varchar(2),
--     用户的生日应该是什么数据类型
    birthday varchar(6),
    level integer,
    sign varchar(80),
--     follwing 当中是不可以有多个mid值的，否则将影响第一范式
-- identity 尚不清楚数据是啥样，先空着dsjfsndjfnskdfsdfs
    identity varchar(9)
);
create table if not exists follwing(
    id integer primary key ,
    user_mid integer,
    constraint fk foreign key (user_mid) references user(mid),
    follwing_mid integer,
    constraint uq unique (user_mid,follwing_mid)
);
create table if not exists videos(
    BV varchar(30) primary key unique ,
    title varchar(30),
--     owner mid 视频所有者是只能有一个人吗？
    owner_mid integer ,
    constraint fk foreign key (owner_mid) references user(mid),
    commite_time varchar(25),
    review_time varchar(25),
    public_time varchar(25),
    duration integer,
    description varchar(80),
--     这里的reviewer要与什么表进行外键的联系吗
    reviewer integer,
--     给这个视频点赞的用户要新创建一个表
-- view：包含观看此视频的用户及其上次观看的时长的列表。
-- 这是什么鬼
);
-- 点赞，投币和收藏是否可以多次？
create table if not exists like(
    id integer primary key ,
    BV varchar(30),
    constraint fk foreign key (BV) references videos(BV),
    user_mid integer unique
);
create table if not exists coin(
    id integer primary key ,
    BV varchar(30),
    constraint fk foreign key (BV) references videos(BV),
    user_mid integer unique
);
create table if not exists favorite(
    id integer primary key ,
    BV varchar(30),
    constraint fk foreign key (BV) references videos(BV),
    user_mid integer unique
);
-- view：包含观看此视频的用户及其上次观看的时长的列表。
-- 这是什么鬼
create table if not exists danmu(
    id integer primary key ,
    BV varchar(10),
    constraint fk1 foreign key (BV) references videos(BV),
    mid integer,
    constraint fk2 foreign key (mid) references user(mid),
    time integer,
    content varchar(50)
);
-- question 这里要不要设置两个外键？
create table if not exists view(
    id integer primary key ,
    BV varchar(10),
    constraint fk foreign key (BV)references videos(BV),
    mid integer,
    timeLong integer
);
