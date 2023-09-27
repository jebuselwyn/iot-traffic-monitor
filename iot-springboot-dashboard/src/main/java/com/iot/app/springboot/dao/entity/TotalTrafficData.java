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
 * Entity class for total_traffic db table
 * 
 * @author abaghel
 *
 */
@Table("total_traffic")
public class TotalTrafficData implements Serializable{

	@PrimaryKey
	TotalTrafficDataKey pk;
	@Column(value = "totalcount")
	private long totalCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="MST")
	@Column(value = "timestamp")
	private Date timeStamp;
	
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public TotalTrafficDataKey getPk() {
		return pk;
	}
	public void setPk(TotalTrafficDataKey pk) {
		this.pk = pk;
	}
	@Override
	public String toString() {
		return "TotalTrafficData [pk=" + pk + ", totalCount=" + totalCount + ", timeStamp=" + timeStamp + "]";
	}
	
	
}
