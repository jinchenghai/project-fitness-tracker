package edu.depaul.fitnesstracker.nutrition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NutritionService {
	
	@Autowired
	private NutritionRepository nutritionRepository;
	
	private List<Nutrition> nutritionEntries = new ArrayList<>(Arrays.asList(
			new Nutrition(1,"Nutrition 1", 2, 3, 4, 5),
			new Nutrition(2,"Nutrition 2", 6, 7, 8, 9)
			));
	
	public List<Nutrition> getAllNutritionEntries(){
		List<Nutrition> nutritionEntries = new ArrayList<>(); 
		
		nutritionRepository.findAll().forEach(nutritionEntries::add);
		
		return nutritionEntries;
	}
	
	public Nutrition getNutritionEntry(int nutritionID) {
		return nutritionEntries.stream().filter(n -> n.getNutritionID() == nutritionID).findFirst().get();
	}

	public void addNutrition(Nutrition nutrition) {
		nutritionRepository.save(nutrition);
	}
	
	public void updateNutrition(int id, Nutrition nutrition) {
		for (Nutrition nutritionEntry  : nutritionEntries) {
			if (nutritionEntry.getNutritionID() == id) {
				nutritionEntries.set(nutritionEntries.indexOf(nutritionEntry), nutrition);
			}
		}
	}

	public void deleteNutrition(int id) {
		nutritionEntries.removeIf( n -> n.getNutritionID() == id);
		
	}
}
