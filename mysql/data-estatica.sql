-- =====================
-- PAISES
-- =====================
INSERT INTO pais (id, nombre) VALUES (1, 'Argentina');
INSERT INTO pais (id, nombre) VALUES (2, 'Brasil');
INSERT INTO pais (id, nombre) VALUES (3, 'Uruguay');
-- INSERT INTO pais (id, nombre) VALUES (4, 'Paraguay');
-- INSERT INTO pais (id, nombre) VALUES (5, 'Chile');
-- INSERT INTO pais (id, nombre) VALUES (6, 'Bolivia');

-- =====================
-- PROVINCIAS DE ARGENTINA
-- =====================
INSERT INTO provincia (id, nombre, pais_id) VALUES (1, 'Buenos Aires', 1);
INSERT INTO provincia (id, nombre, pais_id) VALUES (2, 'Córdoba', 1);
INSERT INTO provincia (id, nombre, pais_id) VALUES (3, 'Santa Fe', 1);
INSERT INTO provincia (id, nombre, pais_id) VALUES (4, 'Mendoza', 1);
INSERT INTO provincia (id, nombre, pais_id) VALUES (5, 'Tucumán', 1);

-- =====================
-- LOCALIDADES
-- =====================
INSERT INTO localidad (id, nombre, provincia_id) VALUES (1, 'San Fernando', 1);
INSERT INTO localidad (id, nombre, provincia_id) VALUES (2, 'San Isidro', 1);
INSERT INTO localidad (id, nombre, provincia_id) VALUES (3, 'Tigre', 1);
INSERT INTO localidad (id, nombre, provincia_id) VALUES (4, 'Vicente Lopez', 1);

-- =====================
-- CONCESIONARIAS
-- =====================
INSERT INTO concesionaria (id, nombre, direccion, fecha_apertura, pais_id, localidad_id)
VALUES (1, 'AutoCity San Fernando', 'Av. Libertador 1234', '2015-05-10', 1, 1);

INSERT INTO concesionaria (id, nombre, direccion, fecha_apertura, pais_id, localidad_id)
VALUES (2, 'MotorHaus San Fernando', 'Ruta 202 Km 15.5', '2018-09-22', 1, 1);

INSERT INTO concesionaria (id, nombre, direccion, fecha_apertura, pais_id, localidad_id)
VALUES (3, 'Tigre Motors', 'Av. Liniers 987', '2020-03-15', 1, 3);

INSERT INTO concesionaria (id, nombre, direccion, fecha_apertura, pais_id, localidad_id)
VALUES (4, 'Premium Cars San Isidro', 'Av. Centenario 456', '2017-11-01', 1, 2);

-- =====================
-- VEHICULOS - stock
-- =====================
INSERT INTO vehiculo (marca, modelo, anio, tipo, garantia_anios, precio_unidad) VALUES 
('Toyota', 'Corolla', 2023, 'Sedan', 3, 25000.00),
('Ford', 'Ranger', 2024, 'Pickup', 5, 38000.00);

-- Stock en concesionaria (por ejemplo, con concesionaria_id = 1)
INSERT INTO stock (vehiculo_id, concesionaria_id, cantidad, tiempo_entrega_dias, tiempo_desde_central_dias) 
VALUES (1, 1, 5, 2, 10);

-- Stock central (concesionaria_id = NULL)
INSERT INTO stock (vehiculo_id, concesionaria_id, cantidad, tiempo_entrega_dias, tiempo_desde_central_dias) 
VALUES (2, NULL, 20, 0, 7);
