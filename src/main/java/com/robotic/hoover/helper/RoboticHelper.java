package com.robotic.hoover.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.robotic.hoover.model.Hoover;
import com.robotic.hoover.service.HooverService;

@Component
public class RoboticHelper {

	@Autowired
	HooverService hooverService;

	public String roboticHoover(String jsonInput) {
		Hoover hoover = hooverService.JsonParser(jsonInput);
		return hooverService.roboticHoover(hoover.getRoomSize(), hoover.getCoords(), hoover.getInstructions(),
				hoover.getPatches());
	}

}
