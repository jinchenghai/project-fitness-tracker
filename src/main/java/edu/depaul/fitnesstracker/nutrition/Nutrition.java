package edu.depaul.fitnesstracker.nutrition;

public class Nutrition {

	private int nutritionID;
	private String nutritionName;
	private int calorieValue;
	private int proteinValue;
	private int carbohydrateValue;
	private int fatValue;
	
	public Nutrition() {
		
	}

	public Nutrition(int nutritionID,String nutritionName, int calorieValue,
			int proteinValue,int carbohydrateValue,int fatValue) {
		
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
		this.nutritionID = nutritionID;
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
		this.nutritionName = nutritionName;
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
		this.calorieValue = calorieValue;
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
		this.proteinValue = proteinValue;
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
		this.carbohydrateValue = carbohydrateValue;
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
		this.fatValue = fatValue;
	}
	
	
	
}
