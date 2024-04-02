create table usuarios (
id serial not null,
login varchar(100) not null,
senha varchar(255) not null,
role varchar(100) not null,

primary key(id)
)