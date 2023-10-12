create table if not exists user_(
    mid integer primary key  ,
--     name 用户名是否可以重复？
    name varchar(20) not null ,
    sex varchar(2),
--     用户的生日应该是什么数据类型
    birthday varchar(6),
    level integer,
    sign varchar(80),
--     follwing 当中是不可以有多个mid值的，否则将影响第一范式
-- identity 尚不清楚数据是啥样，先空着
    identity varchar(9) not null
);
create table if not exists following(
    id serial primary key ,
    user_mid integer,
    constraint fk1 foreign key (user_mid) references user_(mid),
    following_mid integer,
--     constraint fk2 foreign key (following_mid)references user_(mid)
    constraint uq unique (user_mid,following_mid)
);
create table if not exists videos(
    BV varchar(30) primary key  ,
    title varchar(30),
--     owner mid 视频所有者是只能有一个人吗？
    owner_mid integer ,
    constraint fk foreign key (owner_mid) references user_(mid),
    commit_time varchar(25),
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
create table if not exists like_(
    id serial primary key ,
    BV varchar(30),
    constraint fk1 foreign key (BV) references videos(BV),
    user_mid integer unique,
    constraint fk2 foreign key (user_mid) references user_(mid)
);
create table if not exists coin(
    id serial primary key ,
    BV varchar(30),
    constraint fk1 foreign key (BV) references videos(BV),
    user_mid integer unique,
    constraint fk2 foreign key (user_mid) references user_(mid)
);
create table if not exists favorite(
    id serial primary key ,
    BV varchar(30),
    constraint fk foreign key (BV) references videos(BV),
    user_mid integer unique,
    constraint fk2 foreign key (user_mid) references user_(mid)
);
-- view：包含观看此视频的用户及其上次观看的时长的列表。
-- 这是什么鬼
create table if not exists danmu(
    id serial primary key ,
    BV varchar(10),
    constraint fk1 foreign key (BV) references videos(BV),
    mid integer,
    constraint fk2 foreign key (mid) references user_(mid),
    time integer,
    content varchar(50)
);
-- question 这里要不要设置两个外键？
create table if not exists view(
    id serial primary key ,
    BV varchar(10),
    constraint fk1 foreign key (BV)references videos(BV),
    mid integer,
    constraint fk2 foreign key (mid) references user_(mid),
    timeLong integer
);
