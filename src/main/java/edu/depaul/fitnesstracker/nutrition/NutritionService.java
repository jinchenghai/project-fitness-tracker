package edu.depaul.fitnesstracker.nutrition;

import java.util.ArrayList;
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
	
	public List<Nutrition> getNutritionEntries(List<Integer> nutritionIDList) {
		List<Nutrition> nutritionEntries = new ArrayList<>(); 
		if (nutritionIDList.size() < 2) {
			nutritionEntries.add(nutritionRepository.findOne(nutritionIDList.get(0)));
		}
		else {
			nutritionRepository.findAll(nutritionIDList).forEach(nutritionEntries::add);
		}
		
		return nutritionEntries;	
	}
	
	public void addNutritionEntries(List<Nutrition> nutritionList) {
		nutritionRepository.save(nutritionList);
	}
	
	public void updateNutritionEntries(List<Nutrition> nutritionList) {
		for (Nutrition nutrition : nutritionList) {
			nutritionRepository.save(nutrition);
		}
	}
	
	public void deleteNutritionEntries(List<Integer> nutritionIDList) {
		for (int nutritonID : nutritionIDList) {
			if (nutritionRepository.exists(nutritonID))
				nutritionRepository.delete(nutritonID);
		}	
	}
	
	public void deleteAllNutritionEntries() {
		nutritionRepository.deleteAll();
	}
}
