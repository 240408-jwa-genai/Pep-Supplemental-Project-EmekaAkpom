package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.repository.PlanetDao;
import com.revature.utilities.ConnectionUtil;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(PlanetDao dao){
		this.dao = dao;
	}

	public List<Planet> getAllPlanets() {
		// TODO Auto-generated method stub
		//call the DAO to receive a list of planets for the user
		return dao.getAllPlanets();
	}

	public Planet getPlanetByName(int ownerId, String planetName) {
		// TODO Auto-generated method stub
		Planet planet = new Planet();
		planet.setName(planetName);
		planet.setOwnerId(ownerId);
		if (planet.getOwnerId() == ownerId && planet.getName().equals(planetName)){
			return planet;
		}
		return null;
	}

	public Planet getPlanetById(int ownerId, int planetId) {
		// TODO Auto-generated method stub
		if (ownerId == dao.getPlanetById(planetId).getOwnerId()){
			return dao.getPlanetById(planetId);
		}
		return null;
	}

	public Planet createPlanet(int ownerId, Planet planet) {
		// TODO Auto-generated method stub
		//create a planet object to iterate through all of your planets with
		List<Planet> planetList = getAllPlanets();
		//ensure that the names of the planets added are BELOW 30 characters
		if (planet.getName().length() <= 30) {
			//Iterate planet list to ensure that the planet you will add is not already there
			for (Planet planet2 : planetList) {
				if (planet.getName().equals(planet2.getName()) && planet.getOwnerId() == planet2.getOwnerId()) {
					System.out.println("The planet, " + planet2.getName() + ", already exists within your list. This planet was NOT added to your list.");
					return null;
				}
			}

		} else{
			System.out.println("The planet name entered exceeds the 30-character limit. This planet was NOT added to your list.");
			return null;
		}
		System.out.println(planet.getName() + " has been added to the planet list for this user!\n");
		return dao.createPlanet(planet);
	}

	public boolean deletePlanetById(int planetId) {
		// TODO Auto-generated method stub
		//call the DAO to delete the planet
		return dao.deletePlanetById(planetId);
	}
}
