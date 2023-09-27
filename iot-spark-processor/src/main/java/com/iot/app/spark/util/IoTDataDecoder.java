package com.iot.app.spark.util;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.app.spark.vo.IoTData;


/**
 * Class to deserialize JSON string to IoTData java object
 * 
 * @author abaghel
 *
 */
public class IoTDataDecoder implements Deserializer<IoTData> {
	
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IoTData deserialize(String arg0, byte[] arg1) {
        try {
            return objectMapper.readValue(new String(arg1, "UTF-8"), IoTData.class);
        } catch (Exception e) {
            return null;
        }
	}

	
	
}
