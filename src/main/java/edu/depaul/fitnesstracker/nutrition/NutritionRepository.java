package edu.depaul.fitnesstracker.nutrition;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionRepository extends CrudRepository<Nutrition, Integer> {
	
}
