package com.iot.app.springboot.dao.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Entity class for poi_traffic db table
 * 
 * @author abaghel
 *
 */
@Table("poi_traffic")
public class POITrafficData implements Serializable{

	@PrimaryKey
	POITrafficDataKey pk;
	
	@Column(value = "vehicleId")
	private String vehicleId;
	@Column(value = "distance")
	private double distance;
	@Column(value = "vehicleType")
	private String vehicleType;
	
	public String getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public POITrafficDataKey getPk() {
		return pk;
	}
	public void setPk(POITrafficDataKey pk) {
		this.pk = pk;
	}
	
}
