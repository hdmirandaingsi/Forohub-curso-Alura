CREATE DATABASE IF NOT EXISTS foro_api;
USE foro_api; 
-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(100) NOT NULL,
    clave VARCHAR(300) NOT NULL,
    nombre VARCHAR(100),  -- Agregar este campo
    PRIMARY KEY (id)
); 
ALTER TABLE usuarios ADD UNIQUE (login); 
INSERT INTO usuarios (login, clave, nombre) VALUES ('henry', '$2a$10$lRMsrnkxeF6Wyp0WiOQjUesQ0.HCPTq16EHcniJYouqYxbCzELMLq', 'fff');

-- Tabla de tópicos
CREATE TABLE IF NOT EXISTS topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensaje VARCHAR(200) NOT NULL,
    fechaCreacion TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    id_autor BIGINT, -- Cambiar de idAutor a id_autor
    nombreCurso VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_autor) REFERENCES usuarios(id) -- Cambiar de idAutor a id_autor también aquí
); 
INSERT INTO topicos (titulo, mensaje, id_autor, nombreCurso)
VALUES
  ('Tema 1', 'Mensaje del tema 1', 1, 'Curso de Java'), -- id_autor 1
  ('Tema 2', 'Mensaje del tema 2', 1, 'Curso de Java'), -- id_autor 1
  ('Tema 3', 'Mensaje del tema 3', 1, 'Curso de PHP');  -- id_autor 1 
INSERT INTO topicos (titulo, mensaje, id_autor, nombreCurso)
VALUES
  ('Tgfda 1', 'M 1', 1, 'Curso gdfgdfgdf Java'), -- id_autor 1
  ('Thh 2', 'Mhhhhh', 1, 'gggggg'), -- id_autor 1
  ('Tefd', 'Mensajftema 3', 1, 'Cufffff PHP');  -- id_autor 1


INSERT INTO topicos (titulo, mensaje, id_autor, nombreCurso)
VALUES
  ('Tema 3', 'Mensaje del tema 3', 1,'fdg' );  -- id_autor 1 

 
  ALTER TABLE topicos MODIFY nombreCurso VARCHAR(100) NOT NULL DEFAULT 'Valor por defecto';
  
    
SELECT * FROM usuarios; 
SELECT * FROM topicos;
    
    
    
USE foro_api;

DELETE FROM topicos;
DELETE FROM usuarios;


DROP TABLE topicos;
DROP TABLE usuarios;