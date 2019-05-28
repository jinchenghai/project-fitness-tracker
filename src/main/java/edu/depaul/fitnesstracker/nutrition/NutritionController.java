package edu.depaul.fitnesstracker.nutrition;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Nutrition>> getAllNutritionEntries(@RequestParam(value="id", required=false)
	List<Integer> nutritionIdList) {	
		List<Nutrition> nutritionEntryResults;
		HttpStatus status = HttpStatus.OK;
		
		//CASE: Request Parameter has a list of integers
		if (nutritionIdList != null) {
			//OPERATE: Find SPECIFIC Nutrition Entries
			nutritionEntryResults = nutritionService.getNutritionEntries(nutritionIdList);
			
			//VALIDATE: Found SOME of the specific Nutrition Entries
			if (nutritionEntryResults.size() > 0 && nutritionEntryResults.size() < nutritionIdList.size() )
				status = HttpStatus.PARTIAL_CONTENT;
		}
		//CASE: Request Parameter is empty/null
		else
			//OPERATE: Find SPECIFIC Nutrition Entries
			nutritionEntryResults = nutritionService.getAllNutritionEntries();
			
		//VALIDATE: Found NONE of the Nutrition entries
		if (nutritionEntryResults.isEmpty())
			status = HttpStatus.NO_CONTENT;
		
		return ResponseEntity.status(status).body(nutritionEntryResults);
	}
	
	@PutMapping("/nutritions")
	public ResponseEntity<List<Nutrition>> addNutritionEntries(@RequestBody List<Nutrition> nutritionList) {
		List<Nutrition> nutritionEntryResults;
		HttpStatus status = HttpStatus.OK;
		
		//CHECK (BASE): Request body is empty, nothing to add, return NO CONTENT
		if (nutritionList.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(nutritionList);
		
		//OPERATE: Add Nutrition entries and get back all entries that were added
		nutritionEntryResults = nutritionService.addNutritionEntries(nutritionList);
		
		//VALIDATE: Something went wrong with adding the Nutrition entries
		if (nutritionEntryResults.isEmpty()) 
			status = HttpStatus.CONFLICT;
		
		//VALIDATE: Check to see if ALL entries were added, change status to CONFLICT if not all were added
		for (int i = 0; i < nutritionList.size(); i++) {
			if (!nutritionEntryResults.get(i).toString().equals(nutritionList.get(i).toString())) {
				status = HttpStatus.CONFLICT;
				break;
			}
		}
		
		return ResponseEntity.status(status).body(nutritionEntryResults);
	}
	
	@PostMapping("/nutritions")
	public ResponseEntity<List<Nutrition>> updateNutrition(@RequestBody List<Nutrition> nutritionList) {
		List<Nutrition> nutritionEntryResults;
		HttpStatus status = HttpStatus.OK;
		
		//CHECK (BASE): Request body is empty, nothing to add, return NO CONTENT
		if (nutritionList.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(nutritionList);
		
		//OPERATE: Add Nutrition entries and get back all entries that were added
		nutritionEntryResults = nutritionService.updateNutritionEntries(nutritionList);
		
		//VALIDATE:  Check to see if ANY entries exist, send no content if non exist
		if (nutritionEntryResults.isEmpty()) {
			status = HttpStatus.NO_CONTENT;
		}
		//VALIDATE:  Check to see if ALL entries were updated, change status to BAD_REUEST if not all were updated
		else {
			for (int i = 0; i < nutritionList.size(); i++) {
				if (!nutritionEntryResults.get(i).toString().equals(nutritionList.get(i).toString())) {
					status = HttpStatus.BAD_REQUEST;
					break;
				}
			}
		}
		
		return ResponseEntity.status(status).body(nutritionEntryResults);
	}
	
	@DeleteMapping("/nutritions")
	public ResponseEntity<List<Nutrition>> deleteNutrition(@RequestParam(value="id", required=false) 
	List<Integer> nutritionIdList) {
		List<Nutrition> nutritionEntryResults;
		HttpStatus status = HttpStatus.OK;
		
		//CASE: Request Parameter has a list of integers
		if (nutritionIdList != null) {
			//OPERATE: Delete Nutrition entries and get back all entries that were deleted
			nutritionEntryResults = nutritionService.deleteNutritionEntries(nutritionIdList);
			
			//VALIDATE: Check to see empty list was returned, showing that nothing was deleted or not found.
			if (nutritionEntryResults.isEmpty()) {
				status = HttpStatus.NO_CONTENT;
			}
			//VALIDATE: Check to see if ALL entries were deleted, change status to CONFLICT if not all were deleted
			else {	
				for (int i = 0; i < nutritionIdList.size(); i++) {
					if (nutritionIdList.get(i) != nutritionEntryResults.get(i).getNutritionID()) {
						status = HttpStatus.CONFLICT;
						break;
					}
				}
			}
		}
		//CASE: Request Parameter is empty/null
		else {
			//OPERATE: Delete ALL Nutrition entries and return nothing
			//VALIDATE: Check if all entries of nutrition were deleted, change status to CONFLICT otherwise.
			if (!nutritionService.deleteAllNutritionEntries())
				status = HttpStatus.CONFLICT;
			nutritionEntryResults = null;
		}
		
		return ResponseEntity.status(status).body(nutritionEntryResults);
	}
}
