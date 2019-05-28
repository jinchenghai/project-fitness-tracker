package edu.depaul.fitnesstracker.nutrition;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NutritionControllerTest {

	List<Nutrition> dummyNutritionList;
	List<Nutrition> testNutritionList;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	NutritionService testNutritionService;
	
	@Autowired
	NutritionRepository testNutritionRepository;
	
	@Autowired
	NutritionController testNutritionController;
	
	@Before
	public void setup() {
		dummyNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1", 23,45,67,89),
				new Nutrition(2, "Test Nutrition Entry 2", 32,54,76,98),
				new Nutrition(3, "Test Nutrition Entry 3", 89,67,45,23)));
		
		mockMvc = MockMvcBuilders.standaloneSetup(testNutritionController).build();
		
		testNutritionService.addNutritionEntries(dummyNutritionList);
	}
	
	@After
	public void tearDown() {
		dummyNutritionList = null;
		testNutritionList = null;
		
		mockMvc = null;
		
		testNutritionService.deleteAllNutritionEntries();
	}
	
	//GET REQUESTS
	@Test
	public void getNutrition_GivenNutritionURINoParameters_RespondWithAllNutritionEntries()
			throws Exception {
		mockMvc.perform(get("/nutritions").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	public void getNutrition_GivenNutritionURIOneParameter_ResponseWithNutritionEntry() throws Exception {
		mockMvc.perform(get("/nutritions?id={id}", 1).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nutritionID").value(1));
	}
	
	@Test
	public void getNutrition_GivenNutritionURIMoreThanOneParameter_RespondWithNutritionEntries()
			throws Exception {
		mockMvc.perform(get("/nutritions?id={id}", "1,3").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(dummyNutritionList.size()-1))
		.andExpect(jsonPath("$[0].nutritionID").value(dummyNutritionList.get(0).getNutritionID()))
		.andExpect(jsonPath("$[0].nutritionName").value(dummyNutritionList.get(0).getNutritionName()))
		.andExpect(jsonPath("$[0].calorieValue").value(dummyNutritionList.get(0).getCalorieValue()))
		.andExpect(jsonPath("$[0].proteinValue").value(dummyNutritionList.get(0).getProteinValue()))
		.andExpect(jsonPath("$[0].carbohydrateValue").value(dummyNutritionList.get(0).getCarbohydrateValue()))
		.andExpect(jsonPath("$[0].fatValue").value(dummyNutritionList.get(0).getFatValue()))
		.andExpect(jsonPath("$[1].nutritionID").value(dummyNutritionList.get(2).getNutritionID()))
		.andExpect(jsonPath("$[1].nutritionName").value(dummyNutritionList.get(2).getNutritionName()))
		.andExpect(jsonPath("$[1].calorieValue").value(dummyNutritionList.get(2).getCalorieValue()))
		.andExpect(jsonPath("$[1].proteinValue").value(dummyNutritionList.get(2).getProteinValue()))
		.andExpect(jsonPath("$[1].carbohydrateValue").value(dummyNutritionList.get(2).getCarbohydrateValue()))
		.andExpect(jsonPath("$[1].fatValue").value(dummyNutritionList.get(2).getFatValue()));
	}
	
	@Test
	public void getNutrition_GivenNotExistingNutritionURIOneParameter_RespondWithEmptyNutritionEntry()
			throws Exception {
		mockMvc.perform(get("/nutritions?id={id}", 4).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent())
		.andExpect(jsonPath("$").isEmpty());
	}
	
	//PUT REQUESTS
	@Test
	public void putNutrition_GivenOneNutritionContentAndNoNutritionExists_RespondWithNutritionEntry()
			throws Exception {
		List<Nutrition> newNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(4, "Test Nutrition Entry 4", 44,44,44,44)));
		
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(newNutritionList);
	
		mockMvc.perform(put("/nutritions").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(newNutritionList.size()))
			.andExpect(jsonPath("$[0].nutritionID").value(newNutritionList.get(0).getNutritionID()))
			.andExpect(jsonPath("$[0].nutritionName").value(newNutritionList.get(0).getNutritionName()))
			.andExpect(jsonPath("$[0].calorieValue").value(newNutritionList.get(0).getCalorieValue()))
			.andExpect(jsonPath("$[0].proteinValue").value(newNutritionList.get(0).getProteinValue()))
			.andExpect(jsonPath("$[0].carbohydrateValue").value(newNutritionList.get(0).getCarbohydrateValue()))
			.andExpect(jsonPath("$[0].fatValue").value(newNutritionList.get(0).getFatValue()));
	}
	
	@Test
	public void putNutrition_GivenMoreThanOneNutritionContentAndNoNutritionExists_RespondWithNutritionEntries()
			throws Exception {
		List<Nutrition> newNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(5, "Test Nutrition Entry 5", 55,55,55,55),
				new Nutrition(6, "Test Nutrition Entry 6", 66,66,66,66)
				));
		
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(newNutritionList);
	
		mockMvc.perform(put("/nutritions").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(newNutritionList.size()))
			.andExpect(jsonPath("$[0].nutritionID").value(newNutritionList.get(0).getNutritionID()))
			.andExpect(jsonPath("$[0].nutritionName").value(newNutritionList.get(0).getNutritionName()))
			.andExpect(jsonPath("$[0].calorieValue").value(newNutritionList.get(0).getCalorieValue()))
			.andExpect(jsonPath("$[0].proteinValue").value(newNutritionList.get(0).getProteinValue()))
			.andExpect(jsonPath("$[0].carbohydrateValue").value(newNutritionList.get(0).getCarbohydrateValue()))
			.andExpect(jsonPath("$[0].fatValue").value(newNutritionList.get(0).getFatValue()))
			.andExpect(jsonPath("$[1].nutritionID").value(newNutritionList.get(1).getNutritionID()))
			.andExpect(jsonPath("$[1].nutritionName").value(newNutritionList.get(1).getNutritionName()))
			.andExpect(jsonPath("$[1].calorieValue").value(newNutritionList.get(1).getCalorieValue()))
			.andExpect(jsonPath("$[1].proteinValue").value(newNutritionList.get(1).getProteinValue()))
			.andExpect(jsonPath("$[1].carbohydrateValue").value(newNutritionList.get(1).getCarbohydrateValue()))
			.andExpect(jsonPath("$[1].fatValue").value(newNutritionList.get(1).getFatValue()));
	}
	
	@Test
	public void putNutrition_GivenOneNutritionContentAndAllNutritionExisting_RespondWithNutritionEntry()
			throws Exception {
		List<Nutrition> newNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1", 23,45,67,89)));
		
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(newNutritionList);
	
		mockMvc.perform(put("/nutritions").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(newNutritionList.size()))
			.andExpect(jsonPath("$[0].nutritionID").value(newNutritionList.get(0).getNutritionID()))
			.andExpect(jsonPath("$[0].nutritionName").value(newNutritionList.get(0).getNutritionName()))
			.andExpect(jsonPath("$[0].calorieValue").value(newNutritionList.get(0).getCalorieValue()))
			.andExpect(jsonPath("$[0].proteinValue").value(newNutritionList.get(0).getProteinValue()))
			.andExpect(jsonPath("$[0].carbohydrateValue").value(newNutritionList.get(0).getCarbohydrateValue()))
			.andExpect(jsonPath("$[0].fatValue").value(newNutritionList.get(0).getFatValue()));
	}
	
	@Test
	public void putNutrition_GivenMoreThanOneNutritionContentAndNutritionExists_RespondWithNutritionEntries()
			throws Exception {
		List<Nutrition> newNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1", 23,45,67,89),
				new Nutrition(3, "Test Nutrition Entry 3", 89,67,45,23)));
		
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(newNutritionList);
	
		mockMvc.perform(put("/nutritions").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(newNutritionList.size()))
			.andExpect(jsonPath("$[0].nutritionID").value(newNutritionList.get(0).getNutritionID()))
			.andExpect(jsonPath("$[0].nutritionName").value(newNutritionList.get(0).getNutritionName()))
			.andExpect(jsonPath("$[0].calorieValue").value(newNutritionList.get(0).getCalorieValue()))
			.andExpect(jsonPath("$[0].proteinValue").value(newNutritionList.get(0).getProteinValue()))
			.andExpect(jsonPath("$[0].carbohydrateValue").value(newNutritionList.get(0).getCarbohydrateValue()))
			.andExpect(jsonPath("$[0].fatValue").value(newNutritionList.get(0).getFatValue()))
			.andExpect(jsonPath("$[1].nutritionID").value(newNutritionList.get(1).getNutritionID()))
			.andExpect(jsonPath("$[1].nutritionName").value(newNutritionList.get(1).getNutritionName()))
			.andExpect(jsonPath("$[1].calorieValue").value(newNutritionList.get(1).getCalorieValue()))
			.andExpect(jsonPath("$[1].proteinValue").value(newNutritionList.get(1).getProteinValue()))
			.andExpect(jsonPath("$[1].carbohydrateValue").value(newNutritionList.get(1).getCarbohydrateValue()))
			.andExpect(jsonPath("$[1].fatValue").value(newNutritionList.get(1).getFatValue()));
	}
	
	
	@Test
	public void putNutrition_GivenEmptyNutritionContent_RespondHttpStatus204NoContent()
			throws Exception {
		List<Nutrition> newNutritionList = new ArrayList<Nutrition>();
		
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(newNutritionList);
		
		mockMvc.perform(put("/nutritions").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent())
		.andExpect(jsonPath("$").isEmpty());
	}
	
	
	@Test
	public void putNutrition_GivenNoNutritionContent_RespondHttpStatus400BadRequest()
			throws Exception {
		mockMvc.perform(put("/nutritions").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$[0]").doesNotExist());
	}
	
	//POST REQUESTS
	@Test
	public void postNutrition_GivenOneNutritionContentAndNutritionExists_RespondWithNutritionEntry()
			throws Exception {
		List<Nutrition> newNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1 UPDATED", 11,11,11,11)));
		
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(newNutritionList);
	
		mockMvc.perform(post("/nutritions").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(newNutritionList.size()))
		.andExpect(jsonPath("$[0].nutritionID").value(newNutritionList.get(0).getNutritionID()))
		.andExpect(jsonPath("$[0].nutritionName").value(newNutritionList.get(0).getNutritionName()))
		.andExpect(jsonPath("$[0].calorieValue").value(newNutritionList.get(0).getCalorieValue()))
		.andExpect(jsonPath("$[0].proteinValue").value(newNutritionList.get(0).getProteinValue()))
		.andExpect(jsonPath("$[0].carbohydrateValue").value(newNutritionList.get(0).getCarbohydrateValue()))
		.andExpect(jsonPath("$[0].fatValue").value(newNutritionList.get(0).getFatValue()));
	}
	
	@Test
	public void postNutrition_GivenMoreThanOneNutritionContentAndNutritionExists_RespondWithNutritionEntries()
			throws Exception {
		List<Nutrition> newNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(1, "Test Nutrition Entry 1 UPDATED", 11,11,11,11),
				new Nutrition(3, "Test Nutrition Entry 3 UPDATED", 33,33,33,33)));
		
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(newNutritionList);
	
		mockMvc.perform(post("/nutritions").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(newNutritionList.size()))
		.andExpect(jsonPath("$[0].nutritionID").value(newNutritionList.get(0).getNutritionID()))
		.andExpect(jsonPath("$[0].nutritionName").value(newNutritionList.get(0).getNutritionName()))
		.andExpect(jsonPath("$[0].calorieValue").value(newNutritionList.get(0).getCalorieValue()))
		.andExpect(jsonPath("$[0].proteinValue").value(newNutritionList.get(0).getProteinValue()))
		.andExpect(jsonPath("$[0].carbohydrateValue").value(newNutritionList.get(0).getCarbohydrateValue()))
		.andExpect(jsonPath("$[0].fatValue").value(newNutritionList.get(0).getFatValue()))
		.andExpect(jsonPath("$[1].nutritionID").value(newNutritionList.get(1).getNutritionID()))
		.andExpect(jsonPath("$[1].nutritionName").value(newNutritionList.get(1).getNutritionName()))
		.andExpect(jsonPath("$[1].calorieValue").value(newNutritionList.get(1).getCalorieValue()))
		.andExpect(jsonPath("$[1].proteinValue").value(newNutritionList.get(1).getProteinValue()))
		.andExpect(jsonPath("$[1].carbohydrateValue").value(newNutritionList.get(1).getCarbohydrateValue()))
		.andExpect(jsonPath("$[1].fatValue").value(newNutritionList.get(1).getFatValue()));
	}

	@Test
	public void postNutrition_GivenNutritionContentAndNutritionDoesNotExists_RespondWithEmptyList()
			throws Exception {
		List<Nutrition> newNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(4, "Test Nutrition Entry 4 UPDATED", 44,44,44,44)));
		
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(newNutritionList);
	
		mockMvc.perform(post("/nutritions").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent())
		.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	public void postNutrition_GivenMoreThanOneNutritionContentAndSomeNutritionExists_RespondWith206PatrialContent()
			throws Exception {
		List<Nutrition> newNutritionList = new ArrayList<Nutrition>(Arrays.asList(
				new Nutrition(2, "Test Nutrition Entry 2 UPDATED", 22,22,22,22),
				new Nutrition(4, "Test Nutrition Entry 4 UPDATED", 44,44,44,44)));
		
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(newNutritionList);
	
		mockMvc.perform(post("/nutritions").content(json).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isPartialContent());
	}
	
	@Test
	public void postNutrition_GivenNoNutritionContent_RespondHttpStatus400BadRequest()
			throws Exception {
		mockMvc.perform(post("/nutritions").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$[0]").doesNotExist());
	}
	
	
	
	//DELETE REQUESTS
	@Test
	public void deleteNutrition_GivenOneNutritionParameterAndNutritionExists_RespondWithNutritionEntry()
			throws Exception {
		
		mockMvc.perform(delete("/nutritions?id={id}", 1).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(1))
		.andExpect(jsonPath("$[0].nutritionID").value(dummyNutritionList.get(0).getNutritionID()))
		.andExpect(jsonPath("$[0].nutritionName").value(dummyNutritionList.get(0).getNutritionName()))
		.andExpect(jsonPath("$[0].calorieValue").value(dummyNutritionList.get(0).getCalorieValue()))
		.andExpect(jsonPath("$[0].proteinValue").value(dummyNutritionList.get(0).getProteinValue()))
		.andExpect(jsonPath("$[0].carbohydrateValue").value(dummyNutritionList.get(0).getCarbohydrateValue()))
		.andExpect(jsonPath("$[0].fatValue").value(dummyNutritionList.get(0).getFatValue()));
		
		
	}
	
	@Test
	public void deleteNutrition_GivenMoreThanOneNutritionParameterAndNutritionExists_RespondWithNutritionEntries()
			throws Exception {
		
		mockMvc.perform(delete("/nutritions?id={id}", "1,3").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(dummyNutritionList.size()-1))
		.andExpect(jsonPath("$[0].nutritionID").value(dummyNutritionList.get(0).getNutritionID()))
		.andExpect(jsonPath("$[0].nutritionName").value(dummyNutritionList.get(0).getNutritionName()))
		.andExpect(jsonPath("$[0].calorieValue").value(dummyNutritionList.get(0).getCalorieValue()))
		.andExpect(jsonPath("$[0].proteinValue").value(dummyNutritionList.get(0).getProteinValue()))
		.andExpect(jsonPath("$[0].carbohydrateValue").value(dummyNutritionList.get(0).getCarbohydrateValue()))
		.andExpect(jsonPath("$[0].fatValue").value(dummyNutritionList.get(0).getFatValue()))
		.andExpect(jsonPath("$[1].nutritionID").value(dummyNutritionList.get(2).getNutritionID()))
		.andExpect(jsonPath("$[1].nutritionName").value(dummyNutritionList.get(2).getNutritionName()))
		.andExpect(jsonPath("$[1].calorieValue").value(dummyNutritionList.get(2).getCalorieValue()))
		.andExpect(jsonPath("$[1].proteinValue").value(dummyNutritionList.get(2).getProteinValue()))
		.andExpect(jsonPath("$[1].carbohydrateValue").value(dummyNutritionList.get(2).getCarbohydrateValue()))
		.andExpect(jsonPath("$[1].fatValue").value(dummyNutritionList.get(2).getFatValue()));
	}

	@Test
	public void deleteNutrition_GivenNutritionParameterAndNutritionDoesNotExists_RespondWithEmptyList()
			throws Exception {
		
		mockMvc.perform(delete("/nutritions?id={id}", 4).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isNoContent())
		.andExpect(jsonPath("$").isEmpty());
	}

	@Test
	public void 
	deleteNutrition_GivenMoreThanOneNutritionParameterAndSomeNutritionExists_RespondWithExistinghNutritionEntries()
			throws Exception {
		
		mockMvc.perform(delete("/nutritions?id={id}", "1,4").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isPartialContent())
		.andExpect(jsonPath("$.size()").value(1))
		.andExpect(jsonPath("$[0].nutritionID").value(dummyNutritionList.get(0).getNutritionID()))
		.andExpect(jsonPath("$[0].nutritionName").value(dummyNutritionList.get(0).getNutritionName()))
		.andExpect(jsonPath("$[0].calorieValue").value(dummyNutritionList.get(0).getCalorieValue()))
		.andExpect(jsonPath("$[0].proteinValue").value(dummyNutritionList.get(0).getProteinValue()))
		.andExpect(jsonPath("$[0].carbohydrateValue").value(dummyNutritionList.get(0).getCarbohydrateValue()))
		.andExpect(jsonPath("$[0].fatValue").value(dummyNutritionList.get(0).getFatValue()));
	}
	
	@Test
	public void deleteNutrition_GivenNoNutritionParameter_RespondWithEmptyNutritionEntry()
			throws Exception {
		mockMvc.perform(delete("/nutritions").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());
	}

}
