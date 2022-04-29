CREATE TABLE IF NOT EXISTS asistencia_curso (
	id int(11) not null,
	identificacion_infractor varchar(100) not null,
	numero_comparendo varchar(100) not null,
	fecha_asistencia datetime not null,
	primary key (id)
);

