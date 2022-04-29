CREATE TABLE IF NOT EXISTS  maestro_comparendo (
 id_comparendo int(11) not null auto_increment,
 numero_comparendo varchar(100) not null,
 tipo_infraccion int not null,
 identificacion_infractor varchar(100) not null,
 fecha_comparendo datetime not null,
 valor_comparendo float(9,2) UNSIGNED NOT NULL,
 primary key (id_comparendo)
);