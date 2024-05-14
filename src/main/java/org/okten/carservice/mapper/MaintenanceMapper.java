package org.okten.carservice.mapper;

import org.okten.carservice.dto.maintenance.CreateMaintenanceRequest;
import org.okten.carservice.dto.maintenance.MaintenanceDto;
import org.okten.carservice.dto.maintenance.UpdateMaintenanceRequest;
import org.okten.carservice.entity.Maintenance;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceMapper {

    public Maintenance mapToMaintenanceEntity(CreateMaintenanceRequest createMaintenanceRequest) {
        return Maintenance.builder()
                .name(createMaintenanceRequest.getName())
                .description(createMaintenanceRequest.getDescription())
                .price(createMaintenanceRequest.getPrice())
                .build();
    }

    public MaintenanceDto mapToMaintenanceDto(Maintenance maintenance) {
        return MaintenanceDto.builder()
                .id(maintenance.getId().toHexString())
                .name(maintenance.getName())
                .description(maintenance.getDescription())
                .price(maintenance.getPrice())
                .build();
    }

    public Maintenance updateMaintenance(Maintenance maintenance, UpdateMaintenanceRequest updateMaintenanceRequest) {
        if (updateMaintenanceRequest.getName() != null) {
            maintenance.setName(updateMaintenanceRequest.getName());
        }

        if (updateMaintenanceRequest.getDescription() != null) {
            maintenance.setDescription(updateMaintenanceRequest.getDescription());
        }

        if (updateMaintenanceRequest.getPrice() != null) {
            maintenance.setPrice(updateMaintenanceRequest.getPrice());
        }

        return maintenance;
    }
}
