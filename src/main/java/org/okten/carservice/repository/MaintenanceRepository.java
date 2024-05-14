package org.okten.carservice.repository;

import org.bson.types.ObjectId;
import org.okten.carservice.entity.Maintenance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends MongoRepository<Maintenance, ObjectId> {
}
