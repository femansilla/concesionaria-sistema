package com.vehiculos.controller;
import com.vehiculos.client.ConcesionariaFeignClient;
import com.vehiculos.model.ConcesionariaDTO;
import com.vehiculos.model.Stock;
import com.vehiculos.model.Vehiculo;
import com.vehiculos.repository.StockRepository;
import com.vehiculos.repository.VehiculoRepository;
import com.vehiculos.service.StockService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehiculos/stock")
public class StockController {

    private final StockService service;
    private final VehiculoRepository vehiculoRepository;
    private final ConcesionariaFeignClient concesionariaClient;

    public StockController(StockService service,
                           VehiculoRepository vehiculoRepository,
                           ConcesionariaFeignClient concesionariaClient) {
        this.service = service;
        this.vehiculoRepository = vehiculoRepository;
        this.concesionariaClient = concesionariaClient;
    }

    // 1. Stock general (tal cual tabla)
    @GetMapping
    public List<Stock> findAll() {
        return service.findAll();
    }

    // 2. Stock de una concesionaria
    @GetMapping("/concesionaria/{id}")
    public List<Stock> findByConcesionaria(@PathVariable Long id) {
        return service.findByConcesionariaId(id);
    }

    // 3. Stock de un vehículo general (sin filtro)
    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<?> getStockVehiculo(
            @PathVariable("vehiculoId") Long vehiculoId,
            @RequestParam(value = "concesionariaId", required = false) Long concesionariaId) {

        // Validación de existencia
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findById(vehiculoId);
        if (optionalVehiculo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Si viene la concesionaria
        if (concesionariaId != null) {
            List<Stock> stockList = service.findByVehiculoIdAndConcesionariaId(vehiculoId, concesionariaId);

            if (!stockList.isEmpty() && stockList.get(0).getCantidad() > 0) {
                Stock stock = stockList.get(0);
                return ResponseEntity.ok(Map.of(
                        "disponibleEn", "concesionaria",
                        "stock", stock.getCantidad(),
                        "tiempoEntregaDias", stock.getTiempoEntregaDias()
                ));
            }

            // Si no hay stock local, se consulta el stock central
            List<Stock> stockCentral = service.findByVehiculoIdAndConcesionariaIdIsNull(vehiculoId);

            if (!stockCentral.isEmpty() && stockCentral.get(0).getCantidad() > 0) {
                Stock central = stockCentral.get(0);

                ConcesionariaDTO dto = concesionariaClient.obtenerConcesionaria(concesionariaId);

                int demoraExtra;
                switch (dto.getLocalidad().getNombre().toLowerCase()) {
                    case "san fernando":
                        demoraExtra = 7;
                        break;
                    case "san isidro":
                        demoraExtra = 10;
                        break;
                    case "vicente lopez":
                        demoraExtra = 14;
                        break;
                    default:
                        demoraExtra = 20;
                        break;
                }

                int demoraTotal = central.getTiempoDesdeCentralDias() + demoraExtra;

                return ResponseEntity.ok(Map.of(
                        "disponibleEn", "stock central",
                        "stock", central.getCantidad(),
                        "tiempoEntregaTotal", demoraTotal
                ));
            }

            return ResponseEntity.ok(Map.of(
                    "disponibleEn", "sin stock",
                    "stock", 0
            ));
        }

        // Si no se pasa concesionariaId, mostrar stock total por vehículo
        Optional<Stock> todosStock = service.findByVehiculoId(vehiculoId);
        return ResponseEntity.ok(todosStock);
    }

    // 4. Crear stock
    @PostMapping
    public Stock save(@RequestBody @Valid Stock stock) {
        return service.save(stock);
    }

    @PutMapping("/descontar")
    public ResponseEntity<Boolean> descontarStock(
            @RequestParam(name = "vehiculoId") Long vehiculoId,
            @RequestParam(name = "cantidad") Integer cantidad) {
        boolean actualizado = service.descontarStock(vehiculoId, cantidad);
        return ResponseEntity.ok(actualizado);
    }
}
