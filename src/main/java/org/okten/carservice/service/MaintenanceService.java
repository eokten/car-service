package org.okten.carservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.okten.carservice.dto.mail.MailDto;
import org.okten.carservice.dto.maintenance.CreateMaintenanceRequest;
import org.okten.carservice.dto.maintenance.MaintenanceDto;
import org.okten.carservice.dto.maintenance.UpdateMaintenanceRequest;
import org.okten.carservice.dto.user.UserDto;
import org.okten.carservice.entity.Maintenance;
import org.okten.carservice.mapper.MaintenanceMapper;
import org.okten.carservice.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    private final MaintenanceMapper maintenanceMapper;

    private final UserService userService;

    private final MailService mailService;

    public List<MaintenanceDto> findMaintenances() {
        return maintenanceRepository
                .findAll()
                .stream()
                .map(maintenanceMapper::mapToMaintenanceDto)
                .toList();
    }

    public Optional<MaintenanceDto> findMaintenance(String hexId) {
        ObjectId id = new ObjectId(hexId);
        return maintenanceRepository
                .findById(id)
                .map(maintenanceMapper::mapToMaintenanceDto);
    }

    @Transactional
    public MaintenanceDto createMaintenance(CreateMaintenanceRequest createMaintenanceRequest) {
        Maintenance maintenance = maintenanceMapper.mapToMaintenanceEntity(createMaintenanceRequest);
        Maintenance savedMaintenance = maintenanceRepository.save(maintenance);

        List<String> allOwners = userService
                .findUsers()
                .stream()
                .map(UserDto::getEmail)
                .distinct()
                .toList();

        mailService.sendMail(MailDto.builder()
                .subject("New maintenance: %s".formatted(savedMaintenance.getName()))
                .body("Description: %s".formatted(savedMaintenance.getDescription()))
                .recipients(allOwners)
                .build());

        return maintenanceMapper.mapToMaintenanceDto(savedMaintenance);
    }

    @Transactional
    public MaintenanceDto updateMaintenance(String hexId, UpdateMaintenanceRequest updateMaintenanceRequest) {
        ObjectId id = new ObjectId(hexId);
        return maintenanceRepository
                .findById(id)
                .map(maintenance -> maintenanceMapper.updateMaintenance(maintenance, updateMaintenanceRequest))
                .map(maintenanceMapper::mapToMaintenanceDto)
                .orElseThrow(() -> new NoSuchElementException("Maintenance '%s' does not exist".formatted(hexId)));
    }

    public void deleteMaintenance(String hexId) {
        ObjectId id = new ObjectId(hexId);
        maintenanceRepository.deleteById(id);
    }
}
