package org.okten.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okten.carservice.dto.maintenance.CreateMaintenanceRequest;
import org.okten.carservice.dto.maintenance.MaintenanceDto;
import org.okten.carservice.dto.maintenance.UpdateMaintenanceRequest;
import org.okten.carservice.service.MaintenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping("/maintenances")
    public ResponseEntity<List<MaintenanceDto>> getMaintenances() {
        return ResponseEntity.ok(maintenanceService.findMaintenances());
    }

    @GetMapping("/maintenances/{id}")
    public ResponseEntity<MaintenanceDto> getMaintenance(@PathVariable String id) {
        return ResponseEntity.of(maintenanceService.findMaintenance(id));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/maintenances")
    public ResponseEntity<MaintenanceDto> createMaintenance(@RequestBody @Valid CreateMaintenanceRequest createMaintenanceRequest) {
        return ResponseEntity.ok(maintenanceService.createMaintenance(createMaintenanceRequest));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/maintenances/{id}")
    public ResponseEntity<MaintenanceDto> updateMaintenance(@PathVariable String id, @RequestBody UpdateMaintenanceRequest updateMaintenanceRequest) {
        return ResponseEntity.ok(maintenanceService.updateMaintenance(id, updateMaintenanceRequest));
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/maintenances/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable String id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.accepted().build();
    }
}
