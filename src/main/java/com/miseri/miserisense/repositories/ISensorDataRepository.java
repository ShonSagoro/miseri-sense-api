package com.miseri.miserisense.repositories;

import com.miseri.miserisense.models.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISensorDataRepository extends MongoRepository<SensorData,Long> {
}
