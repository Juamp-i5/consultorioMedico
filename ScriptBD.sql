CREATE DATABASE IF NOT EXISTS consultas_medicas;
use consultas_medicas;

CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
    apellido_materno VARCHAR(50) NULL,
    contrasenia VARCHAR(200) NOT NULL 
);

CREATE TABLE Paciente (
    id_paciente INT PRIMARY KEY,
    fecha_nacimiento DATE NOT NULL,
    telefono VARCHAR(15) unique NOT NULL,
    correo_electronico VARCHAR(100) UNIQUE NOT NULL,
    FOREIGN KEY (id_paciente) REFERENCES Usuario(id_usuario)
);

CREATE TABLE Direccion_Paciente (
	id_direccion_paciente INT PRIMARY KEY,
    calle VARCHAR(30) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    colonia VARCHAR(60) NOT NULL,
    codigo_postal VARCHAR(5) NOT NULL,
    FOREIGN KEY (id_direccion_paciente) REFERENCES Paciente(id_paciente)
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
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    detalles VARCHAR(200) null,
    FOREIGN KEY (id_cita) REFERENCES Cita(id_cita)
);

CREATE TABLE Horario_Atencion (
    id_horario_atencion INT AUTO_INCREMENT PRIMARY KEY,
    id_medico INT NOT NULL,
    dia_semana ENUM('Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado') NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (id_medico)
        REFERENCES Medico (id_medico)
);

DELIMITER //

CREATE PROCEDURE FiltrarMedicosPorEspecialidad(IN especialidad_busqueda VARCHAR(100))
BEGIN
    SELECT 
        M.id_medico, 
        U.nombre, 
        U.apellido_paterno, 
        U.apellido_materno, 
        M.especialidad, 
        M.cedula_profesional, 
        M.estado
    FROM Medico M
    JOIN Usuario U ON M.id_medico = U.id_usuario
    WHERE M.especialidad = especialidad_busqueda;
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE ObtenerHorariosMedico(IN idDoctor INT)
BEGIN
    SELECT 
        H.dia_semana, 
        H.hora_inicio, 
        H.hora_fin
    FROM Horario_Atencion H
    WHERE H.id_medico = idDoctor
    ORDER BY FIELD(H.dia_semana, 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'), 
             H.hora_inicio;
END //

DELIMITER ;

DELIMITER //



DELIMITER //

CREATE FUNCTION EstaEnHorario(idMedico INT, fechaHora DATETIME) 
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    DECLARE horario_valido BOOLEAN;

    SELECT 
        IF(
            EXISTS (
                SELECT 1
                FROM Horario_Atencion H
                WHERE H.id_medico = idMedico
                AND DAYOFWEEK(fechaHora) = 
                    CASE 
                        WHEN H.dia_semana = 'Domingo' THEN 1
                        WHEN H.dia_semana = 'Lunes' THEN 2
                        WHEN H.dia_semana = 'Martes' THEN 3
                        WHEN H.dia_semana = 'Miércoles' THEN 4
                        WHEN H.dia_semana = 'Jueves' THEN 5
                        WHEN H.dia_semana = 'Viernes' THEN 6
                        WHEN H.dia_semana = 'Sábado' THEN 7
                    END
                AND TIME(H.hora_inicio) <= TIME(fechaHora)
                AND TIME(H.hora_fin) >= TIME(fechaHora)
            ), TRUE, FALSE
        ) INTO horario_valido;

    RETURN horario_valido;
END //

DELIMITER ;



DELIMITER //

CREATE FUNCTION VerificarCitaConflicto(idMedico INT, fechaHora DATETIME) 
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    DECLARE cita_conflicto BOOLEAN;

    SELECT 
        IF(
            EXISTS (
                SELECT 1
                FROM Cita C
                WHERE C.id_medico = idMedico
                AND ABS(TIMESTAMPDIFF(MINUTE, C.fecha_hora, fechaHora)) < 30
            ), FALSE, TRUE
        ) INTO cita_conflicto;

    RETURN cita_conflicto;
END //

DELIMITER ;



DELIMITER //

CREATE PROCEDURE InsertarCita(
    IN idPaciente INT, 
    IN idMedico INT, 
    IN fechaHora DATETIME, 
    IN tipoCita VARCHAR(50),
    OUT resultado BOOLEAN
)
BEGIN
    DECLARE horario_valido BOOLEAN;
    DECLARE cita_conflicto BOOLEAN;

    SET horario_valido = EstaEnHorario(idMedico, fechaHora);
    SET cita_conflicto = VerificarCitaConflicto(idMedico, fechaHora);

    IF horario_valido = FALSE OR cita_conflicto = FALSE THEN
        SET resultado = FALSE;
    ELSE
        INSERT INTO Cita (id_paciente, id_medico, tipo, fecha_hora, estado) 
        VALUES (idPaciente, idMedico, tipoCita, fechaHora, 'Programado');
        
        SET resultado = TRUE;
    END IF;
END //

DELIMITER ;


CREATE VIEW VistaInicioSesion AS
SELECT 
    u.id_usuario,
    CASE 
        WHEN p.id_paciente IS NOT NULL THEN p.correo_electronico
        ELSE NULL 
    END AS correo,
    CASE 
        WHEN m.id_medico IS NOT NULL THEN m.cedula_profesional
        ELSE NULL 
    END AS cedula,
    u.contrasenia
FROM Usuario u
LEFT JOIN Paciente p ON u.id_usuario = p.id_paciente
LEFT JOIN Medico m ON u.id_usuario = m.id_medico;

CREATE VIEW VistaEspecialidades AS
SELECT DISTINCT especialidad
FROM medico;

DELIMITER //

CREATE PROCEDURE FiltrarMedicosPorEspecialidadYQueEsteActivo(IN especialidad_busqueda VARCHAR(100))
BEGIN
    SELECT 
        M.id_medico, 
        U.nombre, 
        U.apellido_paterno, 
        U.apellido_materno, 
        M.especialidad, 
        M.cedula_profesional, 
        M.estado
    FROM Medico M
    JOIN Usuario U ON M.id_medico = U.id_usuario
    WHERE M.especialidad = especialidad_busqueda AND M.estado = "Activo";
END //

DELIMITER ;

/*TRIGGER DE CITA PROGRAMADA*/
DELIMITER //
CREATE TRIGGER triggerAuditoriaCitaProgramada
AFTER INSERT ON cita FOR EACH ROW
BEGIN
	INSERT INTO auditoria (tipo_operacion, id_cita, fecha_hora, detalles) 
    VALUES ("Se agendo una Cita", NEW.id_cita,NOW(),"Se ha registrado una cita");
END //
DELIMITER ;

/*TRIGGER DE CITA CANCELADA*/
DELIMITER //
CREATE TRIGGER triggerAuditoriaConsultaCancelada
AFTER UPDATE ON cita FOR EACH ROW
BEGIN
	IF NEW.estado = "Cancelada" THEN
		INSERT INTO auditoria (tipo_operacion, id_cita, fecha_hora, detalles) 
		VALUES ("Se cancelo una cita", OLD.id_cita,NOW(),"Se ha cancelado una cita");
	END IF;
END //
DELIMITER ;

/*TRIGGER DE CONSULTA REALIZADAS*/
DELIMITER //
CREATE TRIGGER triggerAuditoriaConsultaRealizada
AFTER INSERT ON consulta FOR EACH ROW
BEGIN
		INSERT INTO auditoria (tipo_operacion, id_cita, fecha_hora, detalles) 
		VALUES ("Se atendio una cita", NEW.id_cita,NOW(),"La consulta se ha atendido");
END //
DELIMITER ;



DELIMITER //

CREATE PROCEDURE ObtenerConsultasMedico(IN p_id_medico INT)
BEGIN
    SELECT 
        C.id_cita,
        C.fecha_hora,
        C.tipo,
        P.id_paciente,
        U.nombre AS nombre_paciente,
        U.apellido_paterno,
        U.apellido_materno,
        CO.diagnostico,
        CO.tratamiento,
        CO.estado
    FROM Cita C
    JOIN Paciente P ON C.id_paciente = P.id_paciente
    JOIN Usuario U ON P.id_paciente = U.id_usuario
    LEFT JOIN Consulta CO ON C.id_cita = CO.id_cita
    WHERE C.id_medico = p_id_medico
    ORDER BY C.fecha_hora DESC;
END //

DELIMITER ;

INSERT INTO Cita (id_paciente, id_medico, tipo, folio, fecha_hora, estado)
VALUES 
    (2, 10, 'Consulta General', 'A1234567', '2025-02-25 09:00:00', 'Programado'),
    (2, 10, 'Revisión Cardiológica', 'B7654321', '2025-02-26 10:30:00', 'Programado'),
    (2, 10, 'Consulta Dermatológica', 'C1122334', '2025-02-27 11:00:00', 'No atendida');