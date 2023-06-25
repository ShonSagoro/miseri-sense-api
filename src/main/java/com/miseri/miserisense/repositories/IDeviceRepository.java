package com.miseri.miserisense.repositories;

import com.miseri.miserisense.models.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeviceRepository extends MongoRepository<Device, Long> {
}
