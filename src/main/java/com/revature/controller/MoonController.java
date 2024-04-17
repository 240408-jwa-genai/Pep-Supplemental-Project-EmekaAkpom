package com.revature.controller;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.service.MoonService;

public class MoonController {
	
	private MoonService moonService;

	public MoonController(MoonService moonService) {
		this.moonService = moonService;
	}

	public void getAllMoons(int currentUserId) {
		// TODO: implement
		for (Moon moon : moonService.getAllMoons()) {
			if (currentUserId == moon.getMyPlanetId()) {
				System.out.println(moon);
			}
		}
	}

	public void getMoonByName(int currentUserId, String name) {
		// TODO: implement
		moonService.getMoonByName(currentUserId, name);
	}

	public void getMoonById(int currentUserId, int id) {
		// TODO: implement
		moonService.getMoonById(currentUserId, id);
	}

	public void createMoon(int currentUserId, Moon moon) {
		// TODO: implement
		moonService.createMoon(moon);
	}

	public void deleteMoon(int id) {
		// TODO: implement
		moonService.deleteMoonById(id);
	}
	
	public void getPlanetMoons(int myPlanetId) {
		// TODO: implement
		for (Moon moon : moonService.getAllMoons()) {
			if (myPlanetId == moon.getMyPlanetId()) {
				System.out.println(moon);
			}
		}
	}
}
