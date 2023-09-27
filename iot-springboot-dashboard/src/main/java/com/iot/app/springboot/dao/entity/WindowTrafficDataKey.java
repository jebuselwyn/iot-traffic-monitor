package com.iot.app.springboot.dao.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
public class WindowTrafficDataKey {

    @PrimaryKeyColumn(name = "routeid",ordinal = 0,type = PrimaryKeyType.PARTITIONED)
	private String routeId;
	@PrimaryKeyColumn(name = "recordDate",ordinal = 1,type = PrimaryKeyType.CLUSTERED)
	private String recordDate;
	@PrimaryKeyColumn(name = "vehicletype",ordinal = 2,type = PrimaryKeyType.CLUSTERED)
	private String vehicleType;
    public String getRouteId() {
        return routeId;
    }
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
    public String getRecordDate() {
        return recordDate;
    }
    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
    public String getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    
}
