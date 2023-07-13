package com.miseri.miserisense.repositories;

import com.miseri.miserisense.models.SensorData;
import com.miseri.miserisense.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISensorDataRepository extends MongoRepository<SensorData,Long> {

    List<SensorData> findBySession(String session);

    List<SensorData> findByDate(String Date);
}
