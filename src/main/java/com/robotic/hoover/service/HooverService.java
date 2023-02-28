package com.robotic.hoover.service;

import java.util.List;

import com.robotic.hoover.model.Hoover;

public interface HooverService {
	
	public String roboticHoover(int[] roomDimensions, int[] hooverPosition, List<Character> drivingInstructions,
			List<int[]> dirtLocations);	
	public Hoover JsonParser(String jsonData);

}
