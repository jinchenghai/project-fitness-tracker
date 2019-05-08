package edu.depaul.fitnesstracker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FitnessTrackerAppController {

	@RequestMapping("/")
	public String home() {
		return "Home...";
	}
}
