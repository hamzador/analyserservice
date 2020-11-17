package org.ida.entities;

import java.time.Instant;


import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Temperature {
	
	@PrimaryKey
	private String toke;
	private String nameSensor;
	private double valueTemperature;
	private Instant date; 
	private int level;
	

}
