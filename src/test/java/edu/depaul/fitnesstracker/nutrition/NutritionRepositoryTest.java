package edu.depaul.fitnesstracker.nutrition;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NutritionRepositoryTest  {
	List<Nutrition> dummyNutritionEntries;
	List<Nutrition> testList = new ArrayList<>();
	
	@Autowired
	private NutritionRepository testNutritionRepository;
	
	@Before
	public void setup() {
		dummyNutritionEntries = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1", 23,45,67,89),
				new Nutrition(2, "Test Nutrition Entry 2", 32,54,76,98),
				new Nutrition(3, "Test Nutrition Entry 3", 89,67,45,23)));
		
		for (Nutrition nutritionEntry : dummyNutritionEntries) {
			testNutritionRepository.save(nutritionEntry);
		}
		
	}
	
	@After
	public void tearDown() {
		dummyNutritionEntries = null;
		testNutritionRepository.deleteAll();
	}
	
	@Test
	public void findAll_WithAllNutritionEntriesExisting_FindAllNutritionEntries() {
		boolean assertionValue = true;
		List<Nutrition> target = new ArrayList<>(); 
		
		testNutritionRepository.findAll().forEach(target::add);
		
		for (int i = 0; i < target.size(); i ++) {
			if (!target.get(i).toString().equals(dummyNutritionEntries.get(i).toString())) {
				assertionValue = false;
			}
		}
		
		assertEquals(true,assertionValue);
	}
	@Test
	public void findAll_WithNotAllNutritionEntriesExisting_FindAllExistingNutritionEntries() {
		boolean assertionValue = true;
		
		List<Integer> testIntList = new ArrayList<Integer>(Arrays.asList(1,3,4));
		List<Nutrition> target = new ArrayList<>(); 
		List<Nutrition>testNutritionEntries = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1", 23,45,67,89),
				new Nutrition(3, "Test Nutrition Entry 3", 89,67,45,23)));
		
		testNutritionRepository.findAll(testIntList).forEach(target::add);
		
		for (int i = 0; i < target.size(); i ++) {
			if (!target.get(i).toString().equals(testNutritionEntries.get(i).toString())) {
				assertionValue = false;
			}
		}
		
		assertEquals(true,assertionValue);
	}
	
	@Test
	public void findOne_WithNutritionEntryExisting_FindNutritionEntry() {
		Nutrition targetNutritionEntry  = new Nutrition(1, "Test Nutrition Entry 1", 23,45,67,89);
		
		assertEquals(targetNutritionEntry.toString(), testNutritionRepository.findOne(1).toString());
	}
	
	@Test
	public void findOne_WithNutritionEntryNotExisting_FindNoNutritionEntry() {	
		assertTrue(testNutritionRepository.findOne(4) == null);
	
	}
	
	@Test
	public void save_WithNutritionEntryNotExisting_SaveNewNutritionEntry() {
		Nutrition targetNutritionEntry  = new Nutrition(4, "Test Nutrition Entry 4", 44,44,44,44);
		
		testNutritionRepository.save(targetNutritionEntry);
		
		assertEquals(targetNutritionEntry.toString(), testNutritionRepository.findOne(4).toString());
	}
	
	@Test
	public void save_WithNutritionEntryExisting_SaveUpdatedNutritionEntry() {
		Nutrition targetNutritionEntry  = new Nutrition(3, "Test Nutrition Entry 3 UPDATED", 33,33,33,33);
		
		testNutritionRepository.save(targetNutritionEntry);
		
		assertEquals(targetNutritionEntry.toString(), testNutritionRepository.findOne(3).toString());
	}
	
	@Test
	public void delete_WithNutritionEntryExisting_DeleteNutritionEntry() {
		Long repositoryCount  = testNutritionRepository.count();
		
		testNutritionRepository.delete(1);
		
		assertTrue(testNutritionRepository.findOne(1) == null);
		assertEquals(repositoryCount - 1 ,testNutritionRepository.count());
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void delete_WithNutritionEntryNotExisting_DeleteNoNutritionEntry() {		
		testNutritionRepository.delete(4);
	}
	
	@Test
	public void delete_WithNutritionEntryExisting_DeleteMultipleNutritionEntry() {
		List<Integer> entriesList = new ArrayList<Integer>(Arrays.asList(1,3)) ;
		Long repositoryCount  = testNutritionRepository.count();
		
		for (int nutritionEntryIds : entriesList) {
			testNutritionRepository.delete(nutritionEntryIds);
		}
		
		assertTrue(testNutritionRepository.findOne(1) == null);
		assertTrue(testNutritionRepository.findOne(3) == null);
		assertEquals(repositoryCount - 2 ,testNutritionRepository.count());
	}
	
	@Test
	public void deleteAll_WithNutritionEntryExisting_DeleteAllNutritionEntries() {
		testNutritionRepository.deleteAll();
		
		assertEquals(0, testNutritionRepository.count());
	}
	
	@Test
	public void count_WithNutritionEntryExisting_countNutritionEntries() {
		assertEquals(dummyNutritionEntries.size(), testNutritionRepository.count());
	}
	
	@Test
	public void exists_WithNutritionEntryExisting_True() {
		int existingNutritionId = dummyNutritionEntries.get(0).getNutritionID();
		
		assertTrue(testNutritionRepository.exists(existingNutritionId));
	}
	
	@Test
	public void exists_WithNutritionEntryNotExisting_False() {
		assertFalse(testNutritionRepository.exists(4));
	}

}
