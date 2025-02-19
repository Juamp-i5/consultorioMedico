CREATE DATABASE IF NOT EXISTS consultas_medicas;
use consultas_medicas;

CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NULL,
    contrasenia VARCHAR(50) NOT NULL 
);

CREATE TABLE Paciente (
    id_paciente INT PRIMARY KEY,
    fecha_nacimiento DATE NOT NULL,
    telefono VARCHAR(15) unique NOT NULL,
    correo_electronico VARCHAR(100) UNIQUE NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Direccion_Paciente (
	id_direccion_paciente INT AUTO_INCREMENT PRIMARY KEY,
	id_paciente INT,
    calle VARCHAR(30) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    colonia VARCHAR(60) NOT NULL,
    codigo_postal INT NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Medico (
    id_medico INT PRIMARY KEY,
    especialidad VARCHAR(100) NOT NULL,
    cedula_profesional VARCHAR(20) UNIQUE NOT NULL,
    estado enum("Activo", "Inactivo") not null,
    FOREIGN KEY (id_medico) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Cita (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT not null,
    id_medico INT not null,
    tipo VARCHAR(50) not null,
    folio VARCHAR(8) unique,
    fecha_hora DATETIME NOT NULL,
    estado enum("Programado", "No atendida", "Cancelada") not null,
    FOREIGN KEY (id_paciente) REFERENCES Paciente(id_paciente),
    FOREIGN KEY (id_medico) REFERENCES Medico(id_medico)
);

CREATE TABLE Consulta (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY,
    id_cita INT not null,
    diagnostico VARCHAR(100) not null,
    tratamiento VARCHAR(50) not null,
    notas_medicas VARCHAR(100) not null,
    estado enum("Atendida", "No Atendida"),
    FOREIGN KEY (id_cita) REFERENCES Cita(id_cita)
);


CREATE TABLE Auditoria (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    tipo_operacion enum("Se agendo una Cita", "Se atendio una cita", "Se cancelo una cita") NOT NULL,
    id_cita int not null,
    fecha_hora DATETIME NOT NULL,
    detalles VARCHAR(200) null,
    FOREIGN KEY (id_cita) REFERENCES Cita(id_cita)
);

CREATE TABLE Horario_Atencion(
	id_horario_atencion int AUTO_INCREMENT primary key,
    id_medico int not null,
    dia_semana VARCHAR(50) not null,
    hora_inicio datetime not null,
    hora_fin datetime not null,
    FOREIGN KEY (id_medico) REFERENCES Medico(id_medico)
);