package org.okten.carservice.mapper;

import org.okten.carservice.dto.car.CarDto;
import org.okten.carservice.dto.car.CreateCarRequest;
import org.okten.carservice.dto.car.UpdateCarRequest;
import org.okten.carservice.entity.Car;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CarMapper {

    public Car mapToCarEntity(CreateCarRequest createCarRequest) {
        return Car.builder()
                .model(createCarRequest.getModel())
                .enginePower(createCarRequest.getEnginePower())
                .torque(createCarRequest.getTorque())
                .fuelType(createCarRequest.getFuelType())
                .lastMaintenanceTimestamp(LocalDate.now())
                .build();
    }

    public CarDto mapToCarDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .enginePower(car.getEnginePower())
                .torque(car.getTorque())
                .fuelType(car.getFuelType())
                .owner(car.getOwner().getUsername())
                .lastMaintenanceTimestamp(car.getLastMaintenanceTimestamp())
                .build();
    }

    public Car updateCar(Car car, UpdateCarRequest updateCarRequest) {
        if (updateCarRequest.getModel() != null) {
            car.setModel(updateCarRequest.getModel());
        }

        if (updateCarRequest.getEnginePower() != null) {
            car.setEnginePower(updateCarRequest.getEnginePower());
        }

        if (updateCarRequest.getTorque() != null) {
            car.setTorque(updateCarRequest.getTorque());
        }

        if (updateCarRequest.isWasMaintained()) {
            car.setLastMaintenanceTimestamp(LocalDate.now());
        }

        return car;
    }
}
