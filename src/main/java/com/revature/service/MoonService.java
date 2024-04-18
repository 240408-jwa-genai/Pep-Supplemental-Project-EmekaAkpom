package com.revature.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.revature.models.Moon;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.MoonDao;

public class MoonService {

	private MoonDao dao;

	public MoonService(MoonDao dao) {
		this.dao = dao;
	}

	public List<Moon> getAllMoons() {
		// TODO implement
		return dao.getAllMoons();
	}

	public Moon getMoonByName(int myPlanetId, String moonName) {
		// TODO implement
		if (myPlanetId == dao.getMoonByName(moonName).getMyPlanetId() && moonName.equals(dao.getMoonByName(moonName).getName()))
			return dao.getMoonByName(moonName);
		else
			return null;
	}

	public Moon getMoonById(int myPlanetId, int moonId) {
		// TODO implement
		if (myPlanetId == dao.getMoonById(moonId).getMyPlanetId())
			return dao.getMoonById(moonId);
		else
			return null;
	}

	public Moon createMoon(Moon m) {
		// TODO implement
		List<Moon> moonList = getAllMoons();
		if (m.getName().length() <= 30) {
			for (Moon moon : moonList){
				if (m.getName().equals(moon.getName())){
					System.out.println("The moon, " + moon.getName() + ", already exists within your list. This moon was NOT added to your list.");
					return null;
				}
			}

		}

		else {
			System.out.println("The moon name entered exceeds the 30-character limit. This moon was NOT added to your list.");
			return null;
		}
		System.out.println(m.getName() + " has been added to the moon list for this planet!\n");
		return dao.createMoon(m);
	}

	public boolean deleteMoonById(int moonId) {
		return dao.deleteMoonById(moonId);
	}

	public List<Moon> getMoonsFromPlanet(int myPlanetId) {
		// TODO Auto-generated method stub
		return dao.getMoonsFromPlanet(myPlanetId);
	}
}
