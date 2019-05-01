package edu.depaul.fitnesstracker.health;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	
	@RequestMapping("/hello")
	public String sayHi() {
		return "This is the initial local running of the Fitness Tracker Application. Hello World!";
	}
}
