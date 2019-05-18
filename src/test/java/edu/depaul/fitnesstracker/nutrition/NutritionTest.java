package edu.depaul.fitnesstracker.nutrition;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NutritionTest {
	
	Nutrition testNutrition;
	
	@Before
	public void setup() {
		testNutrition = new Nutrition(1, "Test Nutrition Entry", 23,45,67,89);
	}
	
	@After
	public void tearDown() {
		testNutrition = null;
	}
	
	//Constructor Tests
	@Test(expected=IllegalArgumentException.class)
	public void Nutrition_NegativeNumberAsID_ExceptionThrown() {
		testNutrition = new Nutrition(-1, "Test Nutrition Entry", 23,45,67,89);
	}
	@Test(expected=IllegalArgumentException.class)
	public void Nutrition_NegativeNumberAsCalorieValue_ExceptionThrown() {
		testNutrition = new Nutrition(1, "Test Nutrition Entry", -23,45,67,89);
	}
	@Test(expected=IllegalArgumentException.class)
	public void Nutrition_NegativeNumberAsProteinValue_ExceptionThrown() {
		testNutrition = new Nutrition(1, "Test Nutrition Entry", 23,-45,67,89);
	}
	@Test(expected=IllegalArgumentException.class)
	public void Nutrition_NegativeNumberAsCarbohydrateValue_ExceptionThrown() {
		testNutrition = new Nutrition(1, "Test Nutrition Entry", 23,45,-67,89);
	}
	@Test(expected=IllegalArgumentException.class)
	public void Nutrition_NegativeNumberAsFatValue_ExceptionThrown() {
		testNutrition = new Nutrition(1, "Test Nutrition Entry", 23,45,67,-89);
	}
	@Test
	public void Nutrition_InstantiatedWithProperParameters_Instantiated() {
		assertEquals(true,testNutrition instanceof Nutrition);
	}
	
	//Getter Tests
	@Test
	public void getNutritionID_InstantiatedWithProperParameters_GetID() {
		int target = testNutrition.getNutritionID();
		assertEquals(target, testNutrition.getNutritionID());
	}
	@Test
	public void getNutritionName_InstantiatedWithProperParameters_GetName() {
		String target = testNutrition.getNutritionName();
		assertEquals(target, testNutrition.getNutritionName());
	}
	@Test
	public void getCalorieValue_InstantiatedWithProperParameters_GetCalories() {
		int target = testNutrition.getCalorieValue();
		assertEquals(target, testNutrition.getCalorieValue());
	}
	@Test
	public void getProteinValue_InstantiatedWithProperParameters_GetProtiein() {
		int target = testNutrition.getProteinValue();
		assertEquals(target, testNutrition.getProteinValue());
	}
	@Test
	public void getFatValue_InstantiatedWithProperParameters_GetFats() {
		int target = testNutrition.getFatValue();
		assertEquals(target, testNutrition.getFatValue());
	}
	
	//Setter Tests
	@Test
	public void setNutritionID_SetWithProperIDValue_SetID() {
		int oldValue = testNutrition.getNutritionID();
		testNutrition.setNutritionID(99999);
		int newValue = testNutrition.getNutritionID();

		assertEquals(true, ((oldValue != newValue) && newValue == testNutrition.getNutritionID()));
	}
	@Test(expected=IllegalArgumentException.class)
	public void setNutritionID_SetWithNegativeIDValue_ExceptionThrown() {
		testNutrition.setNutritionID(-1);
	}
	@Test
	public void setNutritionName_SetWithProperNameValue_SetName() {
		String oldValue = testNutrition.getNutritionName();
		testNutrition.setNutritionName("New Nutrition Test Name");
		String newValue = testNutrition.getNutritionName();

		assertEquals(true, (!(oldValue.equals(newValue)) && newValue.equals(testNutrition.getNutritionName())));
	}
	@Test(expected=IllegalArgumentException.class)
	public void setNutritionName_SetWithEmptyNameValue_ExceptionThrown() {
		testNutrition.setNutritionName("");
	}
	@Test(expected=IllegalArgumentException.class)
	public void setNutritionName_SetWithNullNameValue_ExceptionThrown() {
		testNutrition.setNutritionName(null);
	}
	@Test
	public void setCalorieValue_SetWithProperCalorieValue_SetCalories() {
		int oldValue = testNutrition.getCalorieValue();
		testNutrition.setCalorieValue(99999);
		int newValue = testNutrition.getCalorieValue();

		assertEquals(true, ((oldValue != newValue) && newValue == testNutrition.getCalorieValue()));
	}
	@Test(expected=IllegalArgumentException.class)
	public void setCalorieValue_SetWithNegativeCalorieValue_ExceptionThrown() {
		testNutrition.setCalorieValue(-23);
	}
	@Test
	public void setProteinValue_SetWithProperProteinValue_SetProtein() {
		int oldValue = testNutrition.getProteinValue();
		testNutrition.setProteinValue(99999);
		int newValue = testNutrition.getProteinValue();

		assertEquals(true, ((oldValue != newValue) && newValue == testNutrition.getProteinValue()));
	}
	@Test(expected=IllegalArgumentException.class)
	public void setProteinValue_SetWithNegativeCalorieValue_ExceptionThrown() {
		testNutrition.setProteinValue(-45);
	}
	@Test
	public void setCarbohydrateValue_SetWithProperCarbohydrateValue_SetCarbohydrate() {
		int oldValue = testNutrition.getCarbohydrateValue();
		testNutrition.setCarbohydrateValue(99999);
		int newValue = testNutrition.getCarbohydrateValue();

		assertEquals(true, ((oldValue != newValue) && newValue == testNutrition.getCarbohydrateValue()));
	}
	@Test(expected=IllegalArgumentException.class)
	public void setCarbohydrateValue_SetWithNegativeCalorieValue_ExceptionThrown() {
		testNutrition.setCarbohydrateValue(-67);
	}
	@Test
	public void setFatValue_SetWithProperFatValue_SetFat() {
		int oldValue = testNutrition.getFatValue();
		testNutrition.setFatValue(99999);
		int newValue = testNutrition.getFatValue();

		assertEquals(true, ((oldValue != newValue) && newValue == testNutrition.getFatValue()));
	}
	@Test(expected=IllegalArgumentException.class)
	public void setFatValue_SetWithNegativeFatValue_ExceptionThrown() {
		testNutrition.setCarbohydrateValue(-89);
	}
	
}
