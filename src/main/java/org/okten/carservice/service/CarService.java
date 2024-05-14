package org.okten.carservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.okten.carservice.dto.car.CarDto;
import org.okten.carservice.dto.car.CreateCarRequest;
import org.okten.carservice.dto.car.UpdateCarRequest;
import org.okten.carservice.entity.Car;
import org.okten.carservice.entity.User;
import org.okten.carservice.exception.CarOwnerDoesNotExistException;
import org.okten.carservice.mapper.CarMapper;
import org.okten.carservice.repository.CarRepository;
import org.okten.carservice.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    private final UserRepository userRepository;

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

    public List<Car> findCarsWithLastMaintenanceDateLowerThan(LocalDate targetDate) {
        return carRepository
                .findAllByLastMaintenanceTimestampLessThan(targetDate)
                .stream()
                .toList();
    }

    public Optional<CarDto> findCar(Long id) {
        return carRepository
                .findById(id)
                .map(carMapper::mapToCarDto);
    }

    @Transactional
    public CarDto createCar(CreateCarRequest createCarRequest) {
        Optional<User> owner = userRepository.findByUsername(createCarRequest.getOwner());

        if (owner.isEmpty()) {
            throw new CarOwnerDoesNotExistException("Owner '%s' does not exist".formatted(createCarRequest.getOwner()));
        }

        Car car = carMapper.mapToCarEntity(createCarRequest);
        car.setOwner(owner.get());
        Car savedCar = carRepository.save(car);
        return carMapper.mapToCarDto(savedCar);
    }

    @Transactional
    public CarDto updateCar(Long carId, UpdateCarRequest updateCarRequest) {
        return carRepository
                .findById(carId)
                .map(car -> {
                    Car updatedCar = carMapper.updateCar(car, updateCarRequest);

                    if (updateCarRequest.getOwner() != null) {
                        Optional<User> owner = userRepository.findByUsername(updateCarRequest.getOwner());

                        if (owner.isEmpty()) {
                            throw new CarOwnerDoesNotExistException("User '%s' does not exist".formatted(updateCarRequest.getOwner()));
                        }

                        updatedCar.setOwner(owner.get());
                    }

                    return updatedCar;
                })
                .map(carMapper::mapToCarDto)
                .orElseThrow(() -> new NoSuchElementException("Car '%s' does not exist".formatted(carId)));
    }

    public void deleteCar(Long id) {
        carRepository
                .findById(id)
                .ifPresent(car -> {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                    boolean isOwner = StringUtils.equalsIgnoreCase(String.valueOf(authentication.getPrincipal()), car.getOwner().getUsername());
                    boolean isAdmin = authentication.getAuthorities().stream().anyMatch(authority -> StringUtils.equalsIgnoreCase(authority.getAuthority(), "admin"));

                    if (!isAdmin && !isOwner) {
                        throw new AccessDeniedException("Your can not delete this car");
                    }

                    carRepository.deleteById(id);
                });
    }
}
