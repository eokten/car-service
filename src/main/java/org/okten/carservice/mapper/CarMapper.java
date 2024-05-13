package org.okten.carservice.mapper;

import org.okten.carservice.dto.CarDto;
import org.okten.carservice.dto.request.CreateCarRequest;
import org.okten.carservice.dto.request.UpdateCarRequest;
import org.okten.carservice.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car mapToCarEntity(CreateCarRequest createCarRequest) {
        return Car.builder()
                .model(createCarRequest.getModel())
                .enginePower(createCarRequest.getEnginePower())
                .torque(createCarRequest.getTorque())
                .fuelType(createCarRequest.getFuelType())
                .build();
    }

    public CarDto mapToCarDto(Car car) {
        return CarDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .enginePower(car.getEnginePower())
                .torque(car.getTorque())
                .fuelType(car.getFuelType())
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

        return car;
    }
}
