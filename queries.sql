drop table link_data_info;

create table link_data_info
(link varchar(500) not null unique,
title varchar (200) not null unique,
hashset varchar (1000),
 day date;
primary key(link));

DELETE FROM link_data_info
WHERE (day - now() > 10);

