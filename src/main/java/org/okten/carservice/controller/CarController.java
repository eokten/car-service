package org.okten.carservice.controller;

import lombok.RequiredArgsConstructor;
import org.okten.carservice.api.controller.CarsApi;
import org.okten.carservice.api.model.CarDto;
import org.okten.carservice.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController implements CarsApi {

    private final CarService carService;

    @Override
    public ResponseEntity<CarDto> createCar(CarDto carDto) {
        return ResponseEntity.ok(carService.createCar(carDto));
    }

    @Override
    public ResponseEntity<CarDto> getCar(Long id) {
        return ResponseEntity.of(carService.findCar(id));
    }

    @Override
    public ResponseEntity<List<CarDto>> getCars(Integer minEnginePower, Integer maxEnginePower) {
        return ResponseEntity.ok(carService.findCars(minEnginePower, maxEnginePower));
    }

    @Override
    public ResponseEntity<CarDto> modifyCar(Long id, CarDto carDto) {
        return ResponseEntity.ok(carService.updateCar(id, carDto));
    }
}
