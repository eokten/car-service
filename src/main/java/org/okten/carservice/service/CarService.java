package org.okten.carservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okten.carservice.dto.CarDto;
import org.okten.carservice.dto.request.CreateCarRequest;
import org.okten.carservice.dto.request.UpdateCarRequest;
import org.okten.carservice.entity.Car;
import org.okten.carservice.mapper.CarMapper;
import org.okten.carservice.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    private final CarMapper carMapper;

    public List<CarDto> findCars(Integer minEnginePower, Integer maxEnginePower) {
        List<Car> cars;

        if (minEnginePower != null && maxEnginePower != null) {
            cars = carRepository.findAllByEnginePowerBetween(minEnginePower, maxEnginePower);
        } else if (minEnginePower != null) {
            cars = carRepository.findAllByEnginePowerGreaterThanEqual(minEnginePower);
        } else if (maxEnginePower != null) {
            cars = carRepository.findAllByEnginePowerLessThanEqual(maxEnginePower);
        } else {
            cars = carRepository.findAll();
        }

        return cars
                .stream()
                .map(carMapper::mapToCarDto)
                .toList();
    }

    public Optional<CarDto> findCar(Long id) {
        return carRepository
                .findById(id)
                .map(carMapper::mapToCarDto);
    }

    @Transactional
    public CarDto createCar(CreateCarRequest createCarRequest) {
        Car car = carMapper.mapToCarEntity(createCarRequest);
        Car savedCar = carRepository.save(car);
        return carMapper.mapToCarDto(savedCar);
    }

    @Transactional
    public CarDto updateCar(Long carId, UpdateCarRequest updateCarRequest) {
        return carRepository
                .findById(carId)
                .map(car -> carMapper.updateCar(car, updateCarRequest))
                .map(carMapper::mapToCarDto)
                .orElseThrow(() -> new NoSuchElementException("Car '%s' does not exist".formatted(carId)));
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}
