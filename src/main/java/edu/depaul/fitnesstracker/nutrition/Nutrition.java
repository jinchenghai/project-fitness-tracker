package edu.depaul.fitnesstracker.nutrition;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Nutrition {
	
	@Id
	private int nutritionID;
	private String nutritionName;
	private int calorieValue;
	private int proteinValue;
	private int carbohydrateValue;
	private int fatValue;
	
	public Nutrition() {
		
	}

	public Nutrition(int nutritionID,String nutritionName, int calorieValue,int proteinValue,int carbohydrateValue,
		int fatValue) {
		//Check for valid Nutrition Parameters before constructing
		if (nutritionID <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition ID (" + this.nutritionID + ") must be greater than 0"); 
		}
		else if (nutritionName == null || nutritionName.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: Nutrition Name (\"" + this.nutritionName + "\") cannot be empty "
					+ "or null"); 
		}
		else if (calorieValue <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition Calorie (" + this.calorieValue + ") must be greater "
					+ "than 0");
		}
		else if (proteinValue <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition Protein (" + this.proteinValue + ") must be greater "
					+ "than 0");
		}
		else if (carbohydrateValue <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition Carbohydrate (" + this.carbohydrateValue + ") must be "
					+ "greater than 0");
		}
		else if (fatValue <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition Fat (" + this.fatValue + ") must be "
					+ "greater than 0");
		}
		
		this.nutritionID = nutritionID;
		this.nutritionName = nutritionName;
		this.calorieValue = calorieValue;
		this.proteinValue = proteinValue;
		this.carbohydrateValue = carbohydrateValue;
		this.fatValue = fatValue;
	}

	/**
	 * @return the nutritionID
	 */
	public int getNutritionID() {
		return nutritionID;
	}

	/**
	 * @param nutritionID the nutritionID to set
	 */
	public void setNutritionID(int nutritionID) {
		if (nutritionID <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition ID (" + this.nutritionID + ") must be greater than 0");
		}
		else {
			this.nutritionID = nutritionID;
		}
		
	}

	/**
	 * @return the nutritionName
	 */
	public String getNutritionName() {
		return nutritionName;
	}

	/**
	 * @param nutritionName the nutritionName to set
	 */
	public void setNutritionName(String nutritionName) {
		if (nutritionName == null || nutritionName.trim().equals("")) {
			throw new IllegalArgumentException("ERROR: Nutrition Name (\"" + this.nutritionName + "\") cannot be empty "
					+ "or null"); 
		}
		else{
			this.nutritionName = nutritionName;
		}
	}

	/**
	 * @return the calorieValue
	 */
	public int getCalorieValue() {
		return calorieValue;
	}

	/**
	 * @param calorieValue the calorieValue to set
	 */
	public void setCalorieValue(int calorieValue) {
		if (calorieValue <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition Calorie (" + this.calorieValue + ") must be greater "
					+ "than 0");
		}
		else {
			this.calorieValue = calorieValue;
		}
	}

	/**
	 * @return the proteinValue
	 */
	public int getProteinValue() {
		return proteinValue;
	}

	/**
	 * @param proteinValue the proteinValue to set
	 */
	public void setProteinValue(int proteinValue) {
		if (proteinValue <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition Protein (" + this.proteinValue + ") must be greater "
					+ "than 0");
		}
		else {
			this.proteinValue = proteinValue;
		}
	}

	/**
	 * @return the carbohydrateValue
	 */
	public int getCarbohydrateValue() {
		return carbohydrateValue;
	}

	/**
	 * @param carbohydrateValue the carbohydrateValue to set
	 */
	public void setCarbohydrateValue(int carbohydrateValue) {
		if (carbohydrateValue <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition Carbohydrate (" + this.carbohydrateValue + ") must be "
					+ "greater than 0");
		}
		else {
			this.carbohydrateValue = carbohydrateValue;
		}
	}

	/**
	 * @return the fatValue
	 */
	public int getFatValue() {
		return fatValue;
	}

	/**
	 * @param fatValue the fatValue to set
	 */
	public void setFatValue(int fatValue) {
		if (fatValue <= 0) {
			throw new IllegalArgumentException("ERROR: Nutrition Fat (" + this.fatValue + ") must be "
					+ "greater than 0");
		}
		else {
			this.fatValue = fatValue;
		}
	}
	
	@Override
	public String toString() {
		String toString;
		
		toString = "{Nutrition ID: " + this.nutritionID + ", Nutrition Name: " + this.nutritionName + 
				", Calorie Value: " + this.calorieValue + ", Protein Value: " + this.proteinValue +
				", Carbohydrate Value: " + this.carbohydrateValue + ", Fat Value: " + this.fatValue + "}";
		
		return toString;
	}
	
	
}
