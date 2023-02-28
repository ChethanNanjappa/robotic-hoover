package com.robotic.hoover.controlelr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.robotic.hoover.helper.RoboticHelper;

@RestController
public class RoboticController {
	
	@Autowired
	RoboticHelper roboticHelper;

	@PostMapping("/robotic/hoover")
    public String roboticHoover(@RequestBody String jsonInput) {
		return roboticHelper.roboticHoover(jsonInput);
    }

}
