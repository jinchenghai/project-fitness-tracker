package edu.depaul.fitnesstracker.nutrition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NutritionController {

	
	@RequestMapping("/nutritions")
	public List<Nutrition> getNutritionEntries() {
		return Arrays.asList(
				new Nutrition(1,"Nutrition 1", 2, 3, 4, 5),
				new Nutrition(2,"Nutrition 2", 6, 7, 8, 9)
				);
	}
}
