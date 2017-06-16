create table pets (id  bigserial not null, created_at timestamp not null, version int4 not null, owner_id int8 not null, primary key (id));
create table users (id  bigserial not null, created_at timestamp not null, version int4 not null, email varchar(255), first_name varchar(255), last_name varchar(255), user_type varchar(255), username varchar(255) not null, primary key (id));
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);
alter table users add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);
alter table pets add constraint FKoygstexeo9ivoylgrdrv2tc39 foreign key (owner_id) references users;

