/*INSERT INTO Usuario(email, password, rol, activo) VALUES('test@unlam.edu.ar', 'test', 'ADMIN', true);*/

/*-- Insertar preguntas para el tipo de proyecto "Piso"
INSERT INTO Pregunta (texto, tipoProyecto) VALUES
                                               ('¿Qué tipo de piso deseas instalar?', 'PISO'),
                                               ('¿Cuál es el área total del piso?', 'PISO'),
                                               ('¿Qué material prefieres para el piso?', 'PISO'),
                                               ('¿Deseas instalar calefacción bajo el piso?', 'PISO'),
                                               ('¿Necesitas nivelar el suelo antes de instalar el piso?', 'PISO');

-- Insertar opciones para las preguntas de "Piso"
INSERT INTO Opcion (texto, pregunta_id) VALUES
                                            ('Cerámico', 1),
                                            ('Madera', 1),
                                            ('Vinílico', 1),
                                            ('Otro', 1),
                                            ('Menos de 20 m2', 2),
                                            ('20-50 m2', 2),
                                            ('Más de 50 m2', 2),
                                            ('Cemento', 3),
                                            ('Madera Laminada', 3),
                                            ('No', 4),
                                            ('Sí', 4),
                                            ('Sí', 5),
                                            ('No', 5);

-- Insertar preguntas para el tipo de proyecto "Pared"
INSERT INTO Pregunta (texto, tipoProyecto) VALUES
                                               ('¿Qué tipo de material deseas usar para la pared?', 'PARED'),
                                               ('¿Cuál es la altura de la pared que deseas construir?', 'PARED'),
                                               ('¿Necesitas agregar aislamiento a la pared?', 'PARED'),
                                               ('¿Qué tipo de acabado deseas para la pared?', 'PARED'),
                                               ('¿Cuántas ventanas deseas incluir en la pared?', 'PARED');

-- Insertar opciones para las preguntas de "Pared"
INSERT INTO Opcion (texto, pregunta_id) VALUES
                                            ('Ladrillo', 6),
                                            ('Bloque', 6),
                                            ('Drywall', 6),
                                            ('Otro', 6),
                                            ('Menos de 2.5 m', 7),
                                            ('2.5 - 3 m', 7),
                                            ('Más de 3 m', 7),
                                            ('Pintura', 8),
                                            ('Empapelado', 8),
                                            ('Ninguno', 9),
                                            ('Una', 10),
                                            ('Dos', 10);

-- Insertar preguntas para el tipo de proyecto "Techo"
INSERT INTO Pregunta (texto, tipoProyecto) VALUES
                                               ('¿Qué tipo de material deseas para el techo?', 'TECHO'),
                                               ('¿Cuál es la inclinación del techo que prefieres?', 'TECHO'),
                                               ('¿Deseas instalar aislamiento en el techo?', 'TECHO'),
                                               ('¿Qué tipo de acabado deseas para el techo?', 'TECHO'),
                                               ('¿Cuál es el área total del techo?', 'TECHO');

-- Insertar opciones para las preguntas de "Techo"
INSERT INTO Opcion (texto, pregunta_id) VALUES
                                            ('Teja', 11),
                                            ('Metal', 11),
                                            ('Lámina asfáltica', 11),
                                            ('Otro', 11),
                                            ('Plano', 12),
                                            ('Inclinación media', 12),
                                            ('Alta inclinación', 12),
                                            ('Sí', 13),
                                            ('No', 13),
                                            ('Pintura', 14),
                                            ('Paneles decorativos', 14),
                                            ('Menos de 50 m2', 15),
                                            ('50-100 m2', 15),
                                            ('Más de 100 m2', 15);*/