create table especie
(
  id             int auto_increment
    primary key,
  nombre         varchar(50) charset utf8  null,
  altura         int                       null,
  peso           int                       null,
  tipo           varchar(50)               null,
  urlFoto        varchar(100) charset utf8 null,
  cantidadBichos int                       null,
  energiaInicial int                       null
);


