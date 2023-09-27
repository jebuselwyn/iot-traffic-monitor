package com.iot.app.springboot.dao.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import com.fasterxml.jackson.annotation.JsonFormat;

@PrimaryKeyClass
public class POITrafficDataKey implements Serializable{
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="MST")
	@PrimaryKeyColumn(name = "timeStamp",ordinal = 0,type = PrimaryKeyType.PARTITIONED)
	private Date timeStamp;
	@PrimaryKeyColumn(name = "recordDate",ordinal = 1,type = PrimaryKeyType.CLUSTERED)
	private String recordDate;
    public Date getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getRecordDate() {
        return recordDate;
    }
    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    
}
