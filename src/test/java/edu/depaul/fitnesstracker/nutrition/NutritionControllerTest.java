package edu.depaul.fitnesstracker.nutrition;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import edu.depaul.fitnesstracker.health.HealthController;

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
	
	
	@Test
	public void getAllNutritionEntries_GivenNutritionURINoParameters_RespondWithAllNutritionEntries()
			throws Exception {
		mockMvc.perform(get("/nutritions").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	public void getAllNutritionEntries_GivenNutritionURIOneParameter_ResponseWithNutritionEntry() throws Exception {
		mockMvc.perform(get("/nutritions?id={id}", 1).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nutritionID").value(1));
	}
	
	@Test
	public void getAllNutritionEntries_GivenNutritionURIMoreThanOneParameter_RespondWithNutritionEntries()
			throws Exception {
		mockMvc.perform(get("/nutritions?id={id}", "1,3").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].nutritionID").value(1))
		.andExpect(jsonPath("$[1].nutritionID").value(3));
	}
	
	@Test
	public void getAllNutritionEntries_GivenNotExistingNutritionURIOneParameter_RespondWithEmptyNutritionEntry()
			throws Exception {
		mockMvc.perform(get("/nutritions?id={id}", 4).contentType(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0]").doesNotExist());
	}
	
	@Test
	public void addNutritionEntries_GivenNotExistingNutritionBody_RespondEmptyNutritionEntry()
			throws Exception {
		mockMvc.perform(post("/nutritions").contentType(MediaType.APPLICATION_JSON_UTF8))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0]").doesNotExist());
	}
	

	//addNutritionEntries
	//updateNutrition
	//deleteNutrition

}
