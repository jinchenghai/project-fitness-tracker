package edu.depaul.fitnesstracker.nutrition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NutritionController {
	
	@Autowired
	private NutritionService nutritionService;
	
	@GetMapping("/nutritions")
	public List<Nutrition> getNutritionEntries() {
		return nutritionService.getAllNutritionEntries();
	}
	
	@GetMapping("/nutritions/{id}")
	public Nutrition getNutritionEntries(@PathVariable int id) {
		return nutritionService.getNutritionEntry(id);
	}
	
	@PostMapping("/nutritions")
	public void addTopic(@RequestBody Nutrition nutrition) {
		nutritionService.addNutrition(nutrition);
	}
	
	@PutMapping("/nutritions/{id}")
	public void updateNutrition( @PathVariable int id, @RequestBody Nutrition nutrition) {
		nutritionService.updateNutrition(id, nutrition);
	}
	
	@DeleteMapping("/nutritions/{id}")
	public void deleteNutrition( @PathVariable int id) {
		nutritionService.deleteNutrition(id);
	}
}
