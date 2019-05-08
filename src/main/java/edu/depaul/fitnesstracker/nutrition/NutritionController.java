package edu.depaul.fitnesstracker.nutrition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NutritionController {
	
	@Autowired
	private NutritionService nutritionService;
	
	@RequestMapping("/nutritions")
	public List<Nutrition> getNutritionEntries() {
		return nutritionService.getAllNutritionEntries();
	}
	
	@RequestMapping("/nutritions/{nutritionID}")
	public Nutrition getNutritionEntries(@PathVariable int nutritionID) {
		return nutritionService.getNutritionEntry(nutritionID);
	}
}
