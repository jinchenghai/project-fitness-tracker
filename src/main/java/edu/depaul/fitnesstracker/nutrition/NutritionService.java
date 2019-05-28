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
		//ERROR: Should not be empty list, dont compute and return empty list
		if (nutritionIDList.size() == 0)
			return nutritionEntries;
		else
			nutritionRepository.findAll(nutritionIDList).forEach(nutritionEntries::add);
		
		return nutritionEntries;	
	}
	
	public List<Nutrition> addNutritionEntries(List<Nutrition> nutritionList) {
		List<Nutrition> nutritionEntries = new ArrayList<>(); 
		nutritionRepository.save(nutritionList);
		
		for (Nutrition nutrition : nutritionList)
			nutritionEntries.add(nutritionRepository.findOne(nutrition.getNutritionID()));
		
		return nutritionEntries;
	}
	
	public List<Nutrition> updateNutritionEntries(List<Nutrition> nutritionList) {
		List<Nutrition> nutritionEntries = new ArrayList<>(); 
		
		for (Nutrition nutrition : nutritionList) {
			if (nutritionRepository.exists(nutrition.getNutritionID())) {
				nutritionRepository.save(nutrition);
				nutritionEntries.add(nutritionRepository.findOne(nutrition.getNutritionID()));
			}
		}
		
		return nutritionEntries;
	}
	
	public List<Nutrition> deleteNutritionEntries(List<Integer> nutritionIDList) {
		List<Nutrition> nutritionEntries = new ArrayList<>();
		Nutrition nutrition = new Nutrition();
		
		for (int nutritionId : nutritionIDList) {
			//CHECK: If it exists in repo, then find and delete it
			if (nutritionRepository.exists(nutritionId)) {
				nutrition = nutritionRepository.findOne(nutritionId);
				nutritionRepository.delete(nutritionId);
				
				//VALIDATE: That entry was actually deleted
				if (!nutritionRepository.exists(nutritionId)) {
					nutritionEntries.add(nutrition);
				}
			}
		}	
		
		return nutritionEntries;
	}
	
	public boolean deleteAllNutritionEntries() {
		nutritionRepository.deleteAll();
		if (nutritionRepository.count() > 0) {
			return false;
		}
		return true;
	}
}
