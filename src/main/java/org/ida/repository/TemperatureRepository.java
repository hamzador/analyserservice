package org.ida.repository;

import org.ida.entities.Temperature;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TemperatureRepository extends CassandraRepository<Temperature,String>{

}
