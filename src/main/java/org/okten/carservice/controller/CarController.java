package org.okten.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okten.carservice.dto.car.CarDto;
import org.okten.carservice.dto.car.CreateCarRequest;
import org.okten.carservice.dto.car.UpdateCarRequest;
import org.okten.carservice.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getCars(
            @RequestParam(required = false) Integer minEnginePower,
            @RequestParam(required = false) Integer maxEnginePower
    ) {
        return ResponseEntity.ok(carService.findCars(minEnginePower, maxEnginePower));
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long id) {
        return ResponseEntity.of(carService.findCar(id));
    }

    @PostMapping("/cars")
    public ResponseEntity<CarDto> createCar(@RequestBody @Valid CreateCarRequest createCarRequest) {
        return ResponseEntity.ok(carService.createCar(createCarRequest));
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable(name = "id") Long carId, @RequestBody @Valid UpdateCarRequest updateCarRequest) {
        return ResponseEntity.ok(carService.updateCar(carId, updateCarRequest));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable(name = "id") Long carId) {
        carService.deleteCar(carId);
        return ResponseEntity.accepted().build();
    }
}
