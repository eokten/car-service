package org.okten.carservice.repository;

import org.okten.carservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByEnginePowerBetween(Integer min, Integer max);

    List<Car> findAllByEnginePowerGreaterThanEqual(Integer value);

    List<Car> findAllByEnginePowerLessThanEqual(Integer value);
}
