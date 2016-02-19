drop table if exists art_user;
create table art_user (
  ph_key int primary key auto_increment,
  id BIGINT not null unique,
  email VARCHAR(512) not null,
  pwd VARCHAR(512) not null,
  state INTEGER not null,
  authCode INTEGER not null,
  created BIGINT not null,
  modified BIGINT not null
)default charset=utf8;
INSERT INTO art_user(id,email,pwd,state,authCode,created,modified) VALUES(1000,'admin','admin',0,3,UNIX_TIMESTAMP(now()),UNIX_TIMESTAMP(now()));