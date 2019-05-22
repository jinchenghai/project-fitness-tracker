package edu.depaul.fitnesstracker.nutrition;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NutritionServiceTest {
	List<Nutrition> dummyNutritionList;
	List<Nutrition> testNutritionList;
	
	@Autowired
	NutritionService testNutritionService;
	
	@Autowired
	NutritionRepository testNutritionRepository; 
	
	@Before
	public void setup() {
		dummyNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1", 23,45,67,89),
				new Nutrition(2, "Test Nutrition Entry 2", 32,54,76,98),
				new Nutrition(3, "Test Nutrition Entry 3", 89,67,45,23)));
		
		for (Nutrition nutritionEntry : dummyNutritionList) {
			testNutritionRepository.save(nutritionEntry);
		}
	}
	
	@After
	public void tearDown() {
		dummyNutritionList = null;
		testNutritionList = null;
		testNutritionRepository.deleteAll();
	}
	
	@Test
	public void getAllNutritionEntries_WithNutritionEntriesExisting_GetAllNutritionEntries() {
		boolean assertValue = true;
		
		testNutritionList = testNutritionService.getAllNutritionEntries();
		
		for (int i = 0; i < testNutritionList.size(); i++) {
			if(!dummyNutritionList.get(i).toString().equals(testNutritionList.get(i).toString())) {
				assertValue = false;
			}
		}
		assertTrue(assertValue);
	}
	
	@Test
	public void getAllNutritionEntries_WithNotAllNutritionEntriesExisting_False() {
		boolean assertValue = false;
		
		//Deleting one entry
		testNutritionRepository.delete(dummyNutritionList.get(0).getNutritionID());
		
		testNutritionList = testNutritionService.getAllNutritionEntries();
		
		for (int i = 0; i < testNutritionList.size(); i++) {
			if(dummyNutritionList.get(i).toString().equals(testNutritionList.get(i).toString())) {
				assertValue = true;
			}
		}
		assertFalse(assertValue);
	}
	
	@Test
	public void getNutritionEntries_WithOneIdInParameters_GetNutritionEntry() {
		List<Integer> nutritionIdList = new ArrayList<Integer>(
				Arrays.asList(dummyNutritionList.get(0).getNutritionID()));
		
		testNutritionList = testNutritionService.getNutritionEntries(nutritionIdList);
		
		assertTrue("ERROR:More than one entry recovered. Only one should be retrieved",testNutritionList.size() == 1);
		assertEquals(dummyNutritionList.get(0).toString(), testNutritionList.get(0).toString());
	}
	
	//Might Need improvement
	@Test
	public void getNutritionEntries_WithMoreThanOneIdInParameters_GetNutritionEntry() {
		boolean assertValue = true;
		int indexOfTestNutritionEntry = 0;
		List<Integer> nutritionIdList = new ArrayList<Integer>(
				Arrays.asList(
						dummyNutritionList.get(0).getNutritionID(),
						dummyNutritionList.get(2).getNutritionID()
						));
		
		testNutritionList = testNutritionService.getNutritionEntries(nutritionIdList);
		
		for (int i = 0; i < testNutritionList.size(); i++) {
			//PROBLEM: Only works if index = NutritionId -1
			indexOfTestNutritionEntry = (testNutritionList.get(i).getNutritionID() - 1);
			
			if(!dummyNutritionList.get(indexOfTestNutritionEntry).toString().equals(testNutritionList.get(i).toString())) {
				assertValue = false;
			}
		}
		assertTrue("ERROR: Entry requested != entry recovered",testNutritionList.size() == 2);
		assertTrue(assertValue);
	}
	
	@Test
	public void getNutritionEntries_WithNoNutritionEntryExisting_GetListWithNull() {
		List<Integer> nutritionIdList = new ArrayList<Integer>(Arrays.asList(4));
		
		testNutritionList = testNutritionService.getNutritionEntries(nutritionIdList);
		
		assertTrue("ERROR:Retrieved entry list with non-null entry",testNutritionList.get(0) == null);
	}
	
	@Test
	public void addNutritionEntries_AddingOneNutritionEntry_AddNutritionEntry() {
		List<Nutrition> additionNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(4, "Test Nutrition Entry 4", 44,44,44,4)));
		
		List<Integer> nutritionIdList = new ArrayList<Integer>(Arrays.asList(4));
		
		testNutritionService.addNutritionEntries(additionNutritionList);
		
		testNutritionList = testNutritionService.getNutritionEntries(nutritionIdList);
		
		
		assertEquals(testNutritionList.get(0).toString(), additionNutritionList.get(0).toString());
	}
	
	@Test
	public void addNutritionEntries_AddingMoreThanOneNutritionEntry_AddNutritionEntries() {
		boolean assertValue = true;
		List<Nutrition> additionNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(4, "Test Nutrition Entry 4", 44,44,44,4),
				new Nutrition(5, "Test Nutrition Entry 5", 55,55,55,55)
				));
		
		List<Integer> nutritionIdList = new ArrayList<Integer>(Arrays.asList(4,5));
		
		testNutritionService.addNutritionEntries(additionNutritionList);
		
		testNutritionList = testNutritionService.getNutritionEntries(nutritionIdList);
		
		for (int i = 0; i < testNutritionList.size(); i++) {
			if(!additionNutritionList.get(i).toString().equals(testNutritionList.get(i).toString())) {
				assertValue = false;
			}
		}
		assertTrue(assertValue);
	}
	
	@Test
	public void updateNutritionEntries_UpdatingOneNutritionEntry_UpdateNutritionEntry() {
		boolean assertValue = true;
		List<Nutrition> additionNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1 UPDATED", 11,11,11,11)));
		
		List<Integer> nutritionIdList = new ArrayList<Integer>(Arrays.asList(1));
		
		testNutritionService.addNutritionEntries(additionNutritionList);
		
		testNutritionList = testNutritionService.getNutritionEntries(nutritionIdList);
		
		for (int i = 0; i < testNutritionList.size(); i++) {
			if(!additionNutritionList.get(i).toString().equals(testNutritionList.get(i).toString())) {
				assertValue = false;
			}
		}
		assertTrue(assertValue);
	}
	@Test
	public void updateNutritionEntries_UpdatingMoreThanOneNutritionEntry_UpdateNutritionEntries() {
		boolean assertValue = true;
		List<Nutrition> additionNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1 UPDATED", 11,11,11,11),
				new Nutrition(3, "Test Nutrition Entry 3 UPDATED", 33,33,33,33)				
				));
		
		List<Integer> nutritionIdList = new ArrayList<Integer>(Arrays.asList(1,3));
		
		testNutritionService.addNutritionEntries(additionNutritionList);
		
		testNutritionList = testNutritionService.getNutritionEntries(nutritionIdList);
		
		for (int i = 0; i < testNutritionList.size(); i++) {
			if(!additionNutritionList.get(i).toString().equals(testNutritionList.get(i).toString())) {
				assertValue = false;
			}
		}
		assertTrue(assertValue);
	}
	
	@Test
	public void deleteNutritionEntries_DeleteOneNutritionEntry_DeleteNutritionEntry() {
		List<Integer> nutritionIdList = new ArrayList<Integer>(Arrays.asList(1));
		
		testNutritionService.deleteNutritionEntries(nutritionIdList);
		
		testNutritionList = testNutritionService.getNutritionEntries(nutritionIdList);
		
		assertTrue("ERROR:Retrieved entry list with non-null entry",testNutritionList.get(0) == null);
	}
	
	@Test
	public void deleteNutritionEntries_DeleteMoreThanOneNutritionEntry_DeleteNutritionEntries() {
		List<Integer> nutritionIdList = new ArrayList<Integer>(Arrays.asList(1,3));
		
		testNutritionService.deleteNutritionEntries(nutritionIdList);
		
		testNutritionList = testNutritionService.getNutritionEntries(nutritionIdList);
		
		assertTrue("ERROR:Retrieved entry list with non-null entries",testNutritionList.size() == 0);
	}
	
	@Test
	public void deleteNutritionEntries_DeleteNutritionEntryThatDoesntExist_DeleteNothing() {
		boolean assertValue = true;
		List<Integer> nutritionIdList = new ArrayList<Integer>(Arrays.asList(4));
		
		testNutritionService.deleteNutritionEntries(nutritionIdList);
		
		testNutritionList = testNutritionService.getAllNutritionEntries();
		
		for (int i = 0; i < testNutritionList.size(); i++) {
			if(!dummyNutritionList.get(i).toString().equals(testNutritionList.get(i).toString())) {
				assertValue = false;
			}
		}
		assertTrue(assertValue);
	}
	
	@Test
	public void deleteAllNutritionEntries_WithAllNutritionEntryExisting_DeleteAllNutritionEntries() {
		testNutritionService.deleteAllNutritionEntries();
		
		testNutritionList = testNutritionService.getAllNutritionEntries();
		
		assertTrue("ERROR:Retrieved entry list with non-null entries",testNutritionList.size() == 0);
	}
}
