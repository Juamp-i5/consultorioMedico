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
    estado enum("Programado", "Atendida", "No atendida", "Cancelada") not null,
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


    
DELIMITER //

CREATE PROCEDURE AgendarCitaEmergencia(
    IN especialidad_param VARCHAR(100),
    IN id_paciente_param INT,
    OUT folio_resultado VARCHAR(8),
    OUT medico_nombre VARCHAR(255)
)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE doctor_id INT;
    DECLARE min_datetime DATETIME;
    DECLARE current_datetime DATETIME;
    DECLARE candidate_datetime DATETIME;
    DECLARE best_doctor INT;
    DECLARE best_time DATETIME;
    DECLARE new_folio VARCHAR(8);
    
    DECLARE cur_doctors CURSOR FOR 
        SELECT M.id_medico 
        FROM Medico M 
        WHERE M.especialidad = especialidad_param 
        AND M.estado = 'Activo';
        
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    SET current_datetime = NOW();
    SET best_time = NULL;
    SET best_doctor = NULL;

    -- Buscar el mejor horario entre todos los médicos
    OPEN cur_doctors;
    
    read_loop: LOOP
        FETCH cur_doctors INTO doctor_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        -- Encontrar el próximo horario disponible para este médico
        SELECT MIN(hora_disponible) INTO candidate_datetime
        FROM (
            -- Horarios futuros del médico
            SELECT 
                CASE 
                    WHEN next_date > CURRENT_DATE() 
                        THEN CONCAT(next_date, ' ', H.hora_inicio)
                    WHEN next_date = CURRENT_DATE() AND H.hora_inicio > CURRENT_TIME 
                        THEN CONCAT(next_date, ' ', H.hora_inicio)
                    ELSE CONCAT(next_date, ' ', H.hora_inicio)
                END AS hora_disponible
            FROM (
                -- Calcular próxima fecha para cada día de atención
                SELECT 
                    H.id_medico,
                    H.dia_semana,
                    H.hora_inicio,
                    H.hora_fin,
                    CASE DAYOFWEEK(current_datetime)
                        WHEN 1 THEN 'Domingo'
                        WHEN 2 THEN 'Lunes' 
                        WHEN 3 THEN 'Martes'
                        WHEN 4 THEN 'Miércoles'
                        WHEN 5 THEN 'Jueves'
                        WHEN 6 THEN 'Viernes'
                        WHEN 7 THEN 'Sábado'
                    END AS hoy,
                    CASE 
                        WHEN FIELD(H.dia_semana, 'Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado') 
                            >= DAYOFWEEK(current_datetime)
                        THEN current_datetime + INTERVAL (FIELD(H.dia_semana, 'Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado') 
                            - DAYOFWEEK(current_datetime)) DAY
                        ELSE current_datetime + INTERVAL (7 - DAYOFWEEK(current_datetime) + 
                            FIELD(H.dia_semana, 'Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado')) DAY
                    END AS next_date
                FROM Horario_Atencion H
                WHERE H.id_medico = doctor_id
            ) AS proximas_fechas
            WHERE HOUR(hora_inicio) * 60 + MINUTE(hora_inicio) <= HOUR(hora_fin) * 60 + MINUTE(hora_fin)
        ) AS horarios_posibles
        WHERE hora_disponible >= current_datetime
        AND VerificarCitaConflicto(doctor_id, hora_disponible) = TRUE
        LIMIT 1;
        
        -- Comparar con el mejor tiempo actual
        IF candidate_datetime IS NOT NULL THEN
            IF best_time IS NULL OR candidate_datetime < best_time THEN
                SET best_time = candidate_datetime;
                SET best_doctor = doctor_id;
            END IF;
        END IF;
    END LOOP;
    
    CLOSE cur_doctors;

    -- Verificar que se encontró un médico disponible
    IF best_doctor IS NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No hay médicos disponibles para la especialidad solicitada';
    END IF;

    -- Generar folio único
    REPEAT
        SET new_folio = LPAD(FLOOR(RAND() * 100000000), 8, '0');
    UNTIL NOT EXISTS(SELECT 1 FROM Cita WHERE folio = new_folio) END REPEAT;

    -- Insertar la cita
    INSERT INTO Cita (id_paciente, id_medico, tipo, folio, fecha_hora, estado)
    VALUES (id_paciente_param, best_doctor, 'Emergencia', new_folio, best_time, 'Programado');

    -- Obtener nombre del médico
    SELECT CONCAT(nombre, ' ', apellido_paterno, ' ', apellido_materno) 
    INTO medico_nombre
    FROM Usuario 
    WHERE id_usuario = best_doctor;

    SET folio_resultado = new_folio;
END //

DELIMITER ;    

CALL AgendarCitaEmergencia('Pediatría', 1, @folio, @medico);