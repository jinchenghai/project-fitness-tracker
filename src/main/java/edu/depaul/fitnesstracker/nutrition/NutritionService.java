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
	
	
	public List<Nutrition> getAllNutritionEntries(){
		List<Nutrition> nutritionEntries = new ArrayList<>(); 
		
		nutritionRepository.findAll().forEach(nutritionEntries::add);
		
		return nutritionEntries;
	}
	
	public Nutrition getNutritionEntry(int nutritionID) {
		return nutritionRepository.findOne(nutritionID);
	}
	
	public void addNutritionEntries(List<Nutrition> nutritionList) {
		nutritionRepository.save(nutritionList);
	}
	
	public void updateNutrition(List<Nutrition> nutritionList) {
		for (Nutrition nutrition : nutritionList) {
			nutritionRepository.save(nutrition);
		}
	}

	public void deleteNutrition(int id) {
		nutritionRepository.delete(id);
		
	}
}
