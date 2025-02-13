package com.iot.app.springboot.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.iot.app.springboot.dao.entity.POITrafficData;
import com.iot.app.springboot.dao.entity.POITrafficDataKey;

/**
 * DAO class for poi_traffic 
 * 
 * @author abaghel
 *
 */
@Repository
public interface POITrafficDataRepository extends CassandraRepository<POITrafficData, POITrafficDataKey>{
	 
}
