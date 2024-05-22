package org.okten.carservice.mapper;

import org.okten.carservice.api.model.CarDto;
import org.okten.carservice.api.model.CarProductDto;
import org.okten.carservice.entity.Car;
import org.okten.productservice.api.ProductDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CarMapper {

    public Car mapToCarEntity(CarDto createCarRequest) {
        return Car.builder()
                .model(createCarRequest.getModel())
                .enginePower(createCarRequest.getEnginePower())
                .torque(createCarRequest.getTorque())
                .fuelType(createCarRequest.getFuelType())
                .lastMaintenanceTimestamp(LocalDate.now())
                .build();
    }

    public CarDto mapToCarDto(Car car) {
        return new CarDto()
                .id(car.getId())
                .model(car.getModel())
                .enginePower(car.getEnginePower())
                .torque(car.getTorque())
                .fuelType(car.getFuelType())
                .owner(car.getOwner().getUsername())
                .lastMaintenanceTimestamp(car.getLastMaintenanceTimestamp());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #car.owner.username == authentication.principal")
    public Car updateCar(Car car, CarDto updateCarRequest) {
        if (updateCarRequest.getModel() != null) {
            car.setModel(updateCarRequest.getModel());
        }

        if (updateCarRequest.getEnginePower() != null) {
            car.setEnginePower(updateCarRequest.getEnginePower());
        }

        if (updateCarRequest.getTorque() != null) {
            car.setTorque(updateCarRequest.getTorque());
        }

        if (updateCarRequest.getLastMaintenanceTimestamp() != null) {
            car.setLastMaintenanceTimestamp(LocalDate.now());
        }

        return car;
    }

    public CarProductDto mapToCarProduct(ProductDto productDto) {
        return new CarProductDto()
                .name(productDto.getName())
                .price(productDto.getPrice());
    }
}
