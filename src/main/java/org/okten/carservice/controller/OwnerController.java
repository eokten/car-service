package org.okten.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okten.carservice.dto.owner.CreateOwnerRequest;
import org.okten.carservice.dto.owner.OwnerDto;
import org.okten.carservice.dto.owner.UpdateOwnerRequest;
import org.okten.carservice.service.OwnerService;
import org.springframework.http.ResponseEntity;
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
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/owners")
    public ResponseEntity<List<OwnerDto>> getOwners() {
        return ResponseEntity.ok(ownerService.findOwners());
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable Long id) {
        return ResponseEntity.of(ownerService.findOwner(id));
    }

    @PostMapping("/owners")
    public ResponseEntity<OwnerDto> createOwner(@RequestBody @Valid CreateOwnerRequest createOwnerRequest) {
        return ResponseEntity.ok(ownerService.createOwner(createOwnerRequest));
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable(name = "id") Long ownerId, @RequestBody UpdateOwnerRequest updateOwnerRequest) {
        return ResponseEntity.ok(ownerService.updateOwner(ownerId, updateOwnerRequest));
    }

    @DeleteMapping("/owners/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable(name = "id") Long ownerId) {
        ownerService.deleteOwner(ownerId);
        return ResponseEntity.accepted().build();
    }
}
