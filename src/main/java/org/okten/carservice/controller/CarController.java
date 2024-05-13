package org.okten.carservice.controller;

import lombok.RequiredArgsConstructor;
import org.okten.carservice.entity.Car;
import org.okten.carservice.repository.CarRepository;
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

    private final CarRepository carRepository;

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCars(
            @RequestParam(required = false) Integer minEnginePower,
            @RequestParam(required = false) Integer maxEnginePower
    ) {

        if (minEnginePower != null && maxEnginePower != null) {
            return ResponseEntity.ok(carRepository.findAllByEnginePowerBetween(minEnginePower, maxEnginePower));
        } else if (minEnginePower != null) {
            return ResponseEntity.ok(carRepository.findAllByEnginePowerGreaterThanEqual(minEnginePower));
        } else if (maxEnginePower != null) {
            return ResponseEntity.ok(carRepository.findAllByEnginePowerLessThanEqual(maxEnginePower));
        } else {
            return ResponseEntity.ok(carRepository.findAll());
        }
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCar(@PathVariable Long id) {
        return ResponseEntity.of(carRepository.findById(id));
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(carRepository.save(car));
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable(name = "id") Long carId, @RequestBody Car car) {
        return ResponseEntity.of(
                carRepository
                        .findById(carId)
                        .map(existingCar -> {
                            existingCar.setModel(car.getModel());
                            existingCar.setEnginePower(car.getEnginePower());
                            existingCar.setTorque(car.getTorque());
                            return carRepository.save(existingCar);
                        }));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Car> deleteCar(@PathVariable(name = "id") Long carId) {
        carRepository.deleteById(carId);
        return ResponseEntity.accepted().build();
    }
}
