package org.ida.entities;


import lombok.AllArgsConstructor;
import lombok.Data;




@Data
@AllArgsConstructor
public class LevelTemp {
	
	
	private final int level2 = -1;
	private final int level1 = 1;
	private final int level0 = 0;
	
	private final int measure2 = 0;
	private final int measure1 = 10;
	private final int measure0 = 30;
	
	
	
	
}
