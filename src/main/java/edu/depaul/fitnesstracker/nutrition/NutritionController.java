package edu.depaul.fitnesstracker.nutrition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NutritionController {
	
	@Autowired
	private NutritionService nutritionService;
	
	@GetMapping("/nutritions")
	public List<Nutrition> getAllNutritionEntries(@RequestParam(value="id", required=false) List<Integer> idList) {
		if (idList != null) {
			return nutritionService.getNutritionEntries(idList);
		}
		else {
			return nutritionService.getAllNutritionEntries();
		}
	}
	
	@PostMapping("/nutritions")
	public void addNutritionEntries(@RequestBody List<Nutrition> nutritionList) {
		nutritionService.addNutritionEntries(nutritionList);
	}
	
	@PutMapping("/nutritions")
	public void updateNutrition(@RequestBody List<Nutrition> nutritionList) {
		nutritionService.updateNutritionEntries(nutritionList);
	}
	
	@DeleteMapping("/nutritions")
	public void deleteNutrition(@RequestParam(value="id", required=false) List<Integer> idList) {
		if (idList != null) {
			nutritionService.deleteNutritionEntries(idList);
		}
		else {
			nutritionService.deleteAllNutritionEntries();
		}
	}
}
