package org.okten.carservice.job;

import lombok.RequiredArgsConstructor;
import org.okten.carservice.dto.mail.MailDto;
import org.okten.carservice.service.CarService;
import org.okten.carservice.service.MailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class SendMaintenanceReminder {

    private static final String CAR_MAINTENANCE_ITEM = "%s was last maintained on %s";

    private final CarService carService;

    private final MailService mailService;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    public void sendReminder() {
        LocalDate monthAgo = LocalDate.now().minusMonths(1);

        carService
                .findCarsWithLastMaintenanceDateLowerThan(monthAgo)
                .stream()
                .collect(groupingBy(car -> car.getOwner().getEmail(), toList()))
                .forEach((owner, cars) -> {
                    String body = cars
                            .stream()
                            .map(car -> CAR_MAINTENANCE_ITEM.formatted(car.getModel(), car.getLastMaintenanceTimestamp()))
                            .collect(Collectors.joining("\n", "Your car(s) were on maintenance more than 1 month ago:\n\n", ""));

                    mailService.sendMail(MailDto.builder()
                            .subject("Maintenance reminder")
                            .body(body)
                            .recipients(List.of(owner))
                            .build());
                });
    }
}
