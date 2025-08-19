-- tipos de datos enumerados
create type fase_torneo as enum ('grupos', 'octavos', 'cuartos', 'semifinal', 'tercer_puesto', 'final');
create type estado_partido as enum ('programado', 'en_curso', 'finalizado', 'suspendido', 'cancelado');
create type resultado_partido as enum ('local', 'visitante', 'empate', 'pendiente');
create type posicion_jugador as enum ('portero', 'defensa', 'mediocampo', 'delantero');

-- tabla de equipos
create table equipos (
    id_equipo serial primary key,
    nombre_equipo varchar(100) not null,
    pais varchar(100) not null,
    codigo_fifa char(3) not null unique,
    entrenador varchar(100),
    grupo_asignado char(1),
    activo boolean default true,
);

-- tabla de grupos
create table grupos (
    id_grupo serial primary key,
    nombre_grupo char(1) not null unique,
    fase fase_torneo default 'grupos',
    activo boolean default true
);

-- tabla de partidos
create table partidos (
    id_partido serial primary key,
    id_equipo_local integer not null,
    id_equipo_visitante integer not null,
    id_grupo integer null,
    fase fase_torneo not null,
    fecha_partido timestamp not null,
    estadio varchar(100),
    ciudad varchar(100),
    estado estado_partido default 'programado',
    goles_local integer default 0,
    goles_visitante integer default 0,
    penales_local integer null,
    penales_visitante integer null,
    resultado resultado_partido default 'pendiente',
    tiempo_extra boolean default false,
    definido_penales boolean default false,
    observaciones text,
    
    foreign key (id_equipo_local) references equipos(id_equipo),
    foreign key (id_equipo_visitante) references equipos(id_equipo),
    foreign key (id_grupo) references grupos(id_grupo),
    
    constraint chk_equipos_diferentes check (id_equipo_local != id_equipo_visitante)
);

-- tabla de jugadores
create table jugadores (
    id_jugador serial primary key,
    id_equipo integer not null,
    nombre_completo varchar(100) not null,
    numero_camiseta integer not null,
    posicion posicion_jugador not null,
    edad integer,
    activo boolean default true,
    
    foreign key (id_equipo) references equipos(id_equipo) on delete cascade,
    unique (id_equipo, numero_camiseta)
);

-- tabla de estadísticas por partido
create table estadisticas_partido (
    id_estadistica serial primary key,
    id_partido integer not null,
    id_jugador integer not null,
    goles integer default 0,
    asistencias integer default 0,
    tarjetas_amarillas integer default 0,
    tarjetas_rojas integer default 0,
    minutos_jugados integer default 0,
    titular boolean default false,
    
    foreign key (id_partido) references partidos(id_partido) on delete cascade,
    foreign key (id_jugador) references jugadores(id_jugador) on delete cascade,
    unique (id_partido, id_jugador)
);

-- tabla de posiciones por grupo
create table posiciones_grupo (
    id_posicion serial primary key,
    id_grupo integer not null,
    id_equipo integer not null,
    partidos_jugados integer default 0,
    partidos_ganados integer default 0,
    partidos_empatados integer default 0,
    partidos_perdidos integer default 0,
    goles_favor integer default 0,
    goles_contra integer default 0,
    diferencia_goles integer default 0,
    puntos integer default 0,
    posicion integer default 0,
    
    foreign key (id_grupo) references grupos(id_grupo) on delete cascade,
    foreign key (id_equipo) references equipos(id_equipo) on delete cascade,
    unique (id_grupo, id_equipo)
);

-- tabla de estadísticas generales
create table estadisticas_generales (
    id_estadistica_general serial primary key,
    id_jugador integer not null,
    goles_totales integer default 0,
    asistencias_totales integer default 0,
    partidos_jugados integer default 0,
    minutos_totales integer default 0,
    tarjetas_amarillas_totales integer default 0,
    tarjetas_rojas_totales integer default 0,
    
    foreign key (id_jugador) references jugadores(id_jugador) on delete cascade,
    unique (id_jugador)
);
