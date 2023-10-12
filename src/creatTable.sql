create table if not exists user_(
    mid integer primary key  ,
--     name 用户名是否可以重复？
--可以吧
    name varchar(20) not null ,
    sex varchar(2),
    birthday varchar(6),
    level integer,
    sign varchar(80),
    identity varchar(9) not null
);
create table if not exists following(
    id serial primary key ,
    user_mid integer,
    constraint fk1 foreign key (user_mid) references user_(mid)
        on UPDATE cascade 
        on DELETE cascade ,
    following_mid integer,         
    constraint fk2 foreign key (following_mid)references user_(mid)
        on UPDATE cascade 
        on DELETE cascade ,
    constraint uq unique (user_mid,following_mid)
);
create table if not exists videos(
    BV varchar(30) primary key,
    title varchar(30),
--     owner mid 视频所有者是只能有一个人吗？
--不知
    owner_mid integer ,
    constraint fk1 foreign key (owner_mid) references user_(mid),
    commit_time varchar(25),
    review_time varchar(25),
    public_time varchar(25),
    duration integer,
    description varchar(80),
    reviewer integer,
    constraint fk2 foreign key (reviewer) references user_(mid)
);

create table if not exists like_(
    id serial primary key ,
    BV varchar(30),
    constraint fk1 foreign key (BV) references videos(BV)
        on DELETE cascade 
        on UPDATE cascade ,
    user_mid integer ,
    constraint fk2 foreign key (user_mid) references user_(mid)
        on DELETE cascade 
        on UPDATE cascade ,
        constraint uq unique (BV,user_mid)                        
);
create table if not exists coin(
    id serial primary key ,
    BV varchar(30),
    constraint fk1 foreign key (BV) references videos(BV)
        on DELETE cascade 
        on UPDATE cascade ,
    user_mid integer ,
    constraint fk2 foreign key (user_mid) references user_(mid)
        on DELETE cascade 
        on UPDATE cascade,
        constraint uq unique (BV,user_mid)                        
);
create table if not exists favorite(
    id serial primary key ,
    BV varchar(30),
    constraint fk1 foreign key (BV) references videos(BV)
        on DELETE cascade 
        on UPDATE cascade ,
    user_mid integer ,
    constraint fk2 foreign key (user_mid) references user_(mid)
        on DELETE cascade 
        on UPDATE cascade,
        constraint uq unique (BV,user_mid)                            
);
create table if not exists view_time(
    id serial primary key ,
    BV varchar(10),
    user_mid integer,
    watch_time integer,
    constraint fk1 foreign key (BV) references videos(BV)
         on UPDATE cascade 
         on DELETE cascade ,
    constraint fk2 foreign key (user_mid) references user_(mid)
        on UPDATE cascade 
        on DELETE cascade ,
        constraint uq unique (BV,user_mid) 
                                    
);
--投币点赞收藏，同一个视频同一个人只能进行一次，（bv,id）唯一
--on UPDATE cascade on DELETE cascade ,作用：
--父表数据更新删除时，子表相应数据也会更新删除
create table if not exists danmu(
    id serial primary key ,
    BV varchar(10),
    constraint fk1 foreign key (BV) references videos(BV),
    mid integer,
    constraint fk2 foreign key (mid) references user_(mid),
    time integer,
    content varchar(50)
);
