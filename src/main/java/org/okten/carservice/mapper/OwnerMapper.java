package org.okten.carservice.mapper;

import org.okten.carservice.dto.owner.CreateOwnerRequest;
import org.okten.carservice.dto.owner.OwnerDto;
import org.okten.carservice.dto.owner.UpdateOwnerRequest;
import org.okten.carservice.entity.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

    public Owner mapToOwnerEntity(CreateOwnerRequest createOwnerRequest) {
        return Owner.builder()
                .username(createOwnerRequest.getUsername())
                .email(createOwnerRequest.getEmail())
                .build();
    }

    public OwnerDto mapToOwnerDto(Owner owner) {
        return OwnerDto.builder()
                .id(owner.getId())
                .username(owner.getUsername())
                .email(owner.getEmail())
                .build();
    }

    public Owner updateOwner(Owner owner, UpdateOwnerRequest updateOwnerRequest) {
        if (updateOwnerRequest.getUsername() != null) {
            owner.setUsername(updateOwnerRequest.getUsername());
        }

        if (updateOwnerRequest.getEmail() != null) {
            owner.setEmail(updateOwnerRequest.getEmail());
        }

        return owner;
    }
}
