package com.flexi.lease.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.flexi.lease.model.Vehicle;
import com.flexi.lease.service.VehiclesService;

import net.minidev.json.JSONObject;

@RunWith(MockitoJUnitRunner.class)
public class VehiclesControllerTest {

	@InjectMocks
	private VehiclesController vehiclesController;

	@Mock
	private VehiclesService vehiclesService;

	private MockMvc mockMvc;

	private String vehicleRequestBodyCreate;

	private String vehicleRequestBodyUpdate;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(vehiclesController).build();

		JSONObject vehicleObject = new JSONObject();
		vehicleObject.put("make", "Toyota");
		vehicleObject.put("model", "Camry");
		vehicleObject.put("year", 2020);

		vehicleRequestBodyCreate = vehicleObject.toString();

		vehicleObject = new JSONObject();
		vehicleObject.put("make", "Honda");
		vehicleObject.put("model", "Accord");
		vehicleObject.put("year", 2021);

		vehicleRequestBodyUpdate = vehicleObject.toString();
	}

	@Test
	public void testGetAllVehicles() throws Exception {
		List<Vehicle> vehiclesList = new ArrayList<>();
		vehiclesList.add(new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null));
		vehiclesList.add(new Vehicle(2, "Honda", "Accord", 2021, "1234567891", 15000.0, null));

		when(vehiclesService.findAll()).thenReturn(vehiclesList);

		mockMvc.perform(get("/vehicles").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].vehicleId", is(1))).andExpect(jsonPath("$[0].brand", is("Toyota")))
				.andExpect(jsonPath("$[0].model", is("Camry"))).andExpect(jsonPath("$[0].modelYear", is(2020)))
				.andExpect(jsonPath("$[1].vehicleId", is(2))).andExpect(jsonPath("$[1].brand", is("Honda")))
				.andExpect(jsonPath("$[1].model", is("Accord"))).andExpect(jsonPath("$[1].modelYear", is(2021)));

		verify(vehiclesService, times(1)).findAll();
	}

	@Test
	public void testCreateVehicle() throws Exception {
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);

		when(vehiclesService.saveVehicle(any(Vehicle.class))).thenReturn(vehicle);

		mockMvc.perform(post("/vehicles").contentType(MediaType.APPLICATION_JSON).content(vehicleRequestBodyCreate)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.vehicleId", is(1))).andExpect(jsonPath("$.brand", is("Toyota")))
				.andExpect(jsonPath("$.model", is("Camry"))).andExpect(jsonPath("$.modelYear", is(2020)));

		verify(vehiclesService, times(1)).saveVehicle(any(Vehicle.class));
	}

	@Test
	public void testGetVehicle() throws Exception {
		Vehicle vehicle = new Vehicle(1, "Toyota", "Camry", 2020, "1234567890", 20000.0, null);

		when(vehiclesService.findByVehicleId(anyInt())).thenReturn(vehicle);

		mockMvc.perform(get("/vehicles/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.vehicleId", is(1))).andExpect(jsonPath("$.brand", is("Toyota")))
				.andExpect(jsonPath("$.model", is("Camry"))).andExpect(jsonPath("$.modelYear", is(2020)));

		verify(vehiclesService, times(1)).findByVehicleId(anyInt());
	}

	@Test
	public void testUpdateVehicle() throws Exception {
		Vehicle existingVehicle = new Vehicle(1, "Honda", "Civic", 2021, "1234567890", 20000.0, null);
		Vehicle updatedVehicle = new Vehicle(1, "Honda", "Civic", 2023, "1234567890", 20000.0, null);

		when(vehiclesService.findByVehicleId(anyInt())).thenReturn(existingVehicle);
		when(vehiclesService.saveVehicle(any(Vehicle.class))).thenReturn(updatedVehicle);

		mockMvc.perform(put("/vehicles/1").contentType(MediaType.APPLICATION_JSON).content(vehicleRequestBodyUpdate)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.vehicleId", is(1))).andExpect(jsonPath("$.brand", is("Honda")))
				.andExpect(jsonPath("$.model", is("Civic"))).andExpect(jsonPath("$.modelYear", is(2023)));

		verify(vehiclesService, times(1)).findByVehicleId(anyInt());
		verify(vehiclesService, times(1)).saveVehicle(any(Vehicle.class));
	}

}
