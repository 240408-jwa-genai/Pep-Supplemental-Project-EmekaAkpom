package com.revature.service;

import java.util.ArrayList;
import java.util.List;

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
		planet.setOwnerId(ownerId);
		dao.createPlanet(planet);
		return planet;
	}

	public boolean deletePlanetById(int planetId) {
		// TODO Auto-generated method stub
		return dao.deletePlanetById(planetId);
	}
}
