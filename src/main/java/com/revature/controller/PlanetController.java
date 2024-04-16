package com.revature.controller;

import com.revature.models.Planet;
import com.revature.service.PlanetService;

public class PlanetController {
	
	private PlanetService planetService;

	public PlanetController(PlanetService planetService){
		this.planetService = planetService;
	}

	public void getAllPlanets(int currentUserId) {
		// TODO: implement
		for (Planet planet : planetService.getAllPlanets()){
			if (currentUserId == planet.getOwnerId()){
				System.out.println(planet);
			}
		}

	}

	public void getPlanetByName(int currentUserId, String name) {
		// TODO: implement
		planetService.getPlanetByName(currentUserId, name);
	}

	public void getPlanetByID(int currentUserId, int id) {
		// TODO: implement
		planetService.getPlanetById(currentUserId, id);
	}

	public void createPlanet(int currentUserId, Planet planet) {
		// TODO: implement
		planetService.createPlanet(currentUserId, planet);
	}

	public void deletePlanet(int currentUserId, int id) {
		// TODO: implement
		planetService.deletePlanetById(id);
	}
}
