package edu.depaul.fitnesstracker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FitnessTrackerAppController {

	@RequestMapping("/")
	public String home(ModelAndView mav) {
		return "index";
	}
}
