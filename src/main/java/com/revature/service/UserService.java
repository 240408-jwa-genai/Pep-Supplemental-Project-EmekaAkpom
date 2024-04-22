package com.revature.service;
import java.util.Objects;
import java.util.Scanner;


import com.revature.controller.MoonController;
import com.revature.controller.PlanetController;
import com.revature.controller.UserController;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.MoonDao;
import com.revature.repository.PlanetDao;
import com.revature.repository.UserDao;

public class UserService {

	private UserDao dao;

	public UserService(UserDao dao){
		this.dao = dao;
	}

	public User authenticate(UsernamePasswordAuthentication loginRequestData) {
		// TODO: implement
		//Check if user exists in the database
		User loginUser = new User();
		//Check if username exists
		if (loginRequestData.getUsername().equals(dao.getUserByUsername(loginRequestData.getUsername()).getUsername())){
			loginUser.setUsername(dao.getUserByUsername(loginRequestData.getUsername()).getUsername());
			loginUser.setId(dao.getUserByUsername(loginRequestData.getUsername()).getId());
			//check if password exists
			if (loginRequestData.getPassword().equals(dao.getUserByUsername(loginRequestData.getUsername()).getPassword())){
				loginUser.setPassword(dao.getUserByUsername(loginRequestData.getUsername()).getPassword());
				System.out.println("Hello " + loginUser.getUsername() + "!\n");
				System.out.println("Login was successful!\n\nDisplayed below is your current Planet list:\n");

				//Instantiate classes for objects
				Planet planet = new Planet();
				Moon moon = new Moon();
				MoonDao moonDao = new MoonDao();
				MoonService moonService = new MoonService(moonDao);
				MoonController moonController = new MoonController(moonService);
				PlanetDao planetDao = new PlanetDao();
				PlanetService planetService = new PlanetService(planetDao);
				PlanetController planetController = new PlanetController(planetService);

				try{
					Scanner scanner = new Scanner(System.in);
					planetController.getAllPlanets(loginUser.getId());

					System.out.println("\nWhat would you like to do with your Planet list? Press '1' to add/remove a planet, or press '2' to add/remove a moon. Press '3' to logout: ");

					String newUserInput = scanner.nextLine();
					while(!Objects.equals(newUserInput, "3")){
						//Add or remove planet
						if (Objects.equals(newUserInput, "1")){
							//Display Planet list to user
							planetController.getAllPlanets(loginUser.getId());

							System.out.println("\nDisplayed above is your planet list.\n\nWould you like to add or remove a Planet? Press '1' to add a planet, or press '2' to remove a planet. Press '3' to go back: ");
							newUserInput = scanner.nextLine();
							while(!Objects.equals(newUserInput, "3")){
								//Add planet
								if (Objects.equals(newUserInput, "1")){
									System.out.println("Please enter the name of the planet that you would like to add: ");
									newUserInput = scanner.nextLine();
									planet.setName(newUserInput);
									planet.setOwnerId(loginUser.getId());
									//planetService.createPlanet(loginUser.getId(), planet);
									planetController.createPlanet(loginUser.getId(), planet);
								}

								//remove planet
								else if (Objects.equals(newUserInput, "2")){

									System.out.println("Please enter the ID of the planet that you would like to remove: ");
									try{
										int numInput = scanner.nextInt();
										scanner.nextLine();


										if (planetService.getPlanetById(loginUser.getId(), numInput) != null) {
											System.out.print("Are you sure that you want to remove this planet? \n\nCAUTION: Removing this planet will remove all associated moons. yes or no:");
											newUserInput = scanner.nextLine();
											if (newUserInput.toUpperCase().equals("YES")) {
												System.out.println(planetService.getPlanetById(loginUser.getId(), numInput).getName() + " has been removed from the planet list.\nAll associated moons have also been removed.");
												planetController.deletePlanet(loginUser.getId(), numInput);
												for (Moon currMoon : moonService.getMoonsFromPlanet(numInput)) {
													moonController.deleteMoon(currMoon.getId());
												}
											}
										} else
											System.out.println("Planets with this ID do not exist in your account.\n");
									} catch(Exception e){
										System.out.println("Invalid input!\n");
									}
								}

								else
									System.out.println("Invalid input!\n");

								planetController.getAllPlanets(loginUser.getId());
								System.out.println("\nWould you like to add or remove a Planet? Press '1' to add a planet, or press '2' to remove a planet. Press '3' to go back: ");
								newUserInput = scanner.nextLine();
							}

						}

						//Provide ID of the planet that you wish to interact with
						else if (Objects.equals(newUserInput, "2")){
							planetController.getAllPlanets(loginUser.getId());

							System.out.println("\nDisplayed above is your Planet list.\n\nPlease select the ID of the Planet that you would like to interact with:");

							try {
								int numInput = scanner.nextInt();
								scanner.nextLine();

								if (planetService.getPlanetById(loginUser.getId(), numInput) != null) {

									moonController.getPlanetMoons(numInput);

									System.out.println("Displayed above is the list of moons for this planet.\n");

									System.out.println("Would you like to add or remove a Moon? Press '1' to add a moon, or press '2' to remove a moon. Press '3' to go back: ");
									newUserInput = scanner.nextLine();

									while (!Objects.equals(newUserInput, "3")) {
										if (Objects.equals(newUserInput, "1")) {
											//Add a moon
											System.out.println("Please enter the name of the moon that you would like to add: ");
											newUserInput = scanner.nextLine();
											moon.setName(newUserInput);
											moon.setMyPlanetId(numInput);
											moonController.createMoon(loginUser.getId(), moon);

										} else if (Objects.equals(newUserInput, "2")) {

											//remove a moon
											moonController.getPlanetMoons(numInput);

											System.out.println("Please enter the ID of the moon that you would like to remove: ");
											int moonIDInput = scanner.nextInt();
											scanner.nextLine();
											if (moonService.getMoonById(numInput, moonIDInput) != null) {

												System.out.print("Are you sure that you want to remove this moon? yes or no:");
												newUserInput = scanner.nextLine();
												if (newUserInput.toUpperCase().equals("YES")) {
													System.out.println(moonService.getMoonById(numInput, moonIDInput).getName() + " has been removed from the moon list.\n");
													moonController.deleteMoon(moonIDInput);
												}
											} else
												System.out.println("The moon of ID " + moonIDInput + " does not exist within this planet.\n");

										} else
											System.out.println("Invalid input!\n");


										moonController.getPlanetMoons(numInput);

										System.out.println("\nWould you like to add or remove a moon? Press '1' to add a moon, or press '2' to remove a moon. Press '3' to go back: ");
										newUserInput = scanner.nextLine();
									}
								}

								else
									System.out.println("Planets with this ID does not exist in your account.\n");

							} catch (Exception e){
								System.out.println("Invalid input!\n");
							}
						}

						else
							System.out.println("Invalid input!\n");

						planetController.getAllPlanets(loginUser.getId());

						System.out.println("\nWhat would you like to do with your Planet list? Press '1' to add/remove a planet, or press '2' to add/remove a moon. Press '3' to logout: ");
						newUserInput = scanner.nextLine();
					}
				} catch(Exception e){
					System.out.print("An error has occurred in the system!\n");
				}


				return loginUser;
			}
			else
				System.out.println("Login was NOT successful.\n");
		}

		else
			System.out.println("Login was NOT successful.\n");
		return null;
	}

	public User register(User registerRequestData) {
		// TODO: implement
		// Ensure that both the username and password are below 30 characters.
		// If they are above 30 characters, inform the user.
		if (registerRequestData.getUsername().length() <= 30 && registerRequestData.getPassword().length() <= 30) {
			UsernamePasswordAuthentication newUser = new UsernamePasswordAuthentication();
			newUser.setUsername(registerRequestData.getUsername());
			if (!newUser.getUsername().equals(dao.getUserByUsername(registerRequestData.getUsername()).getUsername())){
				newUser.setPassword(registerRequestData.getPassword());
				dao.createUser(newUser);
				System.out.println("User Account has been created!");
				return registerRequestData;
			}
			else {
				System.out.println("This username already exists within the database!");
			}
		}

		else
			System.out.println("The username and/or password entered exceeds the 30-character limit. The account was NOT created.");

		return null;


	}
}
