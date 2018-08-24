create table especie
(
  id             int                      not null
    primary key,
  tipo           varchar(50) charset utf8 null,
  cantidadBichos int                      null,
  altura         int                      null,
  nombre         varchar(50) charset utf8 null,
  peso           int                      null
);


