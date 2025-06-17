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
-- VEHICULOS - STOCK - TIPO VEHICULO
-- =====================
INSERT INTO tipo_vehiculo (descripcion, garantia_anios, garantia_kilometros)
VALUES 
  ('Sedan', 3, 150000),
  ('SUV', 5, 180000),
  ('Pickup', 7, 200000);


INSERT INTO vehiculo (marca, modelo, anio, tipo_vehiculo_id, precio_unidad) VALUES 
('Toyota', 'Corolla', 2023, 1, 25000.00),
('Ford', 'Ranger', 2024, 3, 38000.00);

-- Stock en concesionaria (por ejemplo, con concesionaria_id = 1)
INSERT INTO stock (vehiculo_id, concesionaria_id, cantidad, tiempo_entrega_dias, tiempo_desde_central_dias) 
VALUES (1, 1, 5, 2, 10);

-- Stock central (concesionaria_id = NULL)
INSERT INTO stock (vehiculo_id, concesionaria_id, cantidad, tiempo_entrega_dias, tiempo_desde_central_dias) 
VALUES (2, NULL, 20, 0, 7);

-- =====================
-- CLIENTES
-- =====================
INSERT INTO cliente (nombre, apellido, dni, email, telefono) VALUES
('María', 'Gómez', '30566789', 'maria.gomez@example.com', '1145678932'),
('Carlos', 'Pérez', '27890123', 'carlos.perez@example.com', '1167890123'),
('Luciana', 'Martínez', '33123456', 'luciana.martinez@example.com', '1154321987');

-- =====================
-- SERVICIOS-MECANICOS
-- =====================
INSERT INTO tipo_servicio_mecanico (descripcion, precio)
VALUES 
  ('Cambio de aceite y filtros', 30000),
  ('Revisión y ajuste de frenos', 80000),
  ('Inspección y rotación de neumáticos', 20000);


INSERT INTO servicio_mecanico (cliente_id, vehiculo_id, fecha_entrega, kilometros, en_garantia)
VALUES 
(1, 101, '2025-06-20', 45000, true),
(2, 102, '2025-06-15', 89000, false);

-- =====================
-- VENTAS
-- =====================
-- Venta de un vehículo (sin servicio mecánico)
INSERT INTO venta (cliente_id, empleado_id, vehiculo_id, cantidad, monto, fecha_operacion)
VALUES (1, 2, 3, 1, 28000000.00, '2025-06-15');

-- Venta de un servicio mecánico (con servicioMecanicoId)
INSERT INTO venta (cliente_id, empleado_id, vehiculo_id, cantidad, monto, fecha_operacion, servicio_mecanico_id)
VALUES (1, 2, 3, 1, 95000.00, '2025-06-15', 5);