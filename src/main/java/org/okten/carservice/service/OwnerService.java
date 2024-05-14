package org.okten.carservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okten.carservice.dto.owner.CreateOwnerRequest;
import org.okten.carservice.dto.owner.OwnerDto;
import org.okten.carservice.dto.owner.UpdateOwnerRequest;
import org.okten.carservice.entity.Owner;
import org.okten.carservice.exception.OwnerCannotBeDeletedException;
import org.okten.carservice.mapper.OwnerMapper;
import org.okten.carservice.repository.CarRepository;
import org.okten.carservice.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    private final CarRepository carRepository;

    private final OwnerMapper ownerMapper;

    public List<OwnerDto> findOwners() {
        return ownerRepository
                .findAll()
                .stream()
                .map(ownerMapper::mapToOwnerDto)
                .toList();
    }

    public Optional<OwnerDto> findOwner(Long id) {
        return ownerRepository
                .findById(id)
                .map(ownerMapper::mapToOwnerDto);
    }

    @Transactional
    public OwnerDto createOwner(CreateOwnerRequest createOwnerRequest) {
        Owner owner = ownerMapper.mapToOwnerEntity(createOwnerRequest);
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.mapToOwnerDto(savedOwner);
    }

    @Transactional
    public OwnerDto updateOwner(Long ownerId, UpdateOwnerRequest updateOwnerRequest) {
        return ownerRepository
                .findById(ownerId)
                .map(owner -> ownerMapper.updateOwner(owner, updateOwnerRequest))
                .map(ownerMapper::mapToOwnerDto)
                .orElseThrow(() -> new NoSuchElementException("Owner '%s' does not exist".formatted(ownerId)));
    }

    public void deleteOwner(Long id) {
        if (carRepository.existsByOwnerId(id)) {
            throw new OwnerCannotBeDeletedException("Owner '%s' has cars and can not be deleted".formatted(id));
        }

        ownerRepository.deleteById(id);
    }
}
