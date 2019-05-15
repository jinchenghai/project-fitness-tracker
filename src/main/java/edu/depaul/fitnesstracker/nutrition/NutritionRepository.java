package edu.depaul.fitnesstracker.nutrition;

import org.springframework.data.repository.CrudRepository;

public interface NutritionRepository extends CrudRepository<Nutrition, Integer> {
	
}
