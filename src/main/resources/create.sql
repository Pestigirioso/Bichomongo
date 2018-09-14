
-- ***********************************
-- *** Script para crear el schema ***
-- ***********************************

-- Crear base de datos
CREATE DATABASE bichomon;

-- Crear tabla especie
CREATE TABLE especie
(
  id             int auto_increment primary key,
  nombre         varchar(50) charset utf8  null,
  altura         int                       null,
  peso           int                       null,
  tipo           varchar(50)               null,
  urlFoto        varchar(100) charset utf8 null,
  cantidadBichos int                       null,
  energiaInicial int                       null,
  constraint especie_nombre_uindex
  unique (nombre)
);

-- Consultar tabla especie
-- select * from especie;
