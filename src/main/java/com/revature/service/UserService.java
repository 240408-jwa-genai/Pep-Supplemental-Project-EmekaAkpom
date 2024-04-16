package com.revature.service;
import java.util.Objects;
import java.util.Scanner;


import com.revature.controller.PlanetController;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.PlanetDao;
import com.revature.repository.UserDao;

public class UserService {

	private UserDao dao;

	public UserService(UserDao dao){
		this.dao = dao;
	}

	public User authenticate(UsernamePasswordAuthentication loginRequestData) {
		// TODO: implement
		User loginUser = new User();
		if (loginRequestData.getUsername().equals(dao.getUserByUsername(loginRequestData.getUsername()).getUsername())){
			loginUser.setUsername(dao.getUserByUsername(loginRequestData.getUsername()).getUsername());
			loginUser.setId(dao.getUserByUsername(loginRequestData.getUsername()).getId());
			if (loginRequestData.getPassword().equals(dao.getUserByUsername(loginRequestData.getUsername()).getPassword())){
				loginUser.setPassword(dao.getUserByUsername(loginRequestData.getUsername()).getPassword());
				System.out.println("Login was successful!\nDisplayed below is your current Planet list:\n");

				Planet planet = new Planet();
				PlanetDao planetDao = new PlanetDao();
				PlanetService planetService = new PlanetService(planetDao);
				PlanetController planetController = new PlanetController(planetService);

				planetController.getAllPlanets(loginUser.getId());

				try{
					Scanner scanner = new Scanner(System.in);
					System.out.println("\nWhat would you like to do with your Planet list? Press '1' to add/remove a planet, or press '2' to add/remove a moon: ");
					String newUserInput = scanner.nextLine();
					while(!Objects.equals(newUserInput, "3")){
						if (Objects.equals(newUserInput, "1")){
							System.out.println("\nWould you like to add or remove a Planet? Press '1' to add a planet, or press '2' to remove a planet: ");
							newUserInput = scanner.nextLine();
							while(!Objects.equals(newUserInput, "3")){
								if (Objects.equals(newUserInput, "1")){
									System.out.println("Please enter the name of the planet that you would like to add: ");
									newUserInput = scanner.nextLine();
									planet.setName(newUserInput);
									planet.setOwnerId(loginUser.getId());
									planetService.createPlanet(loginUser.getId(), planet);
								}

								else if (Objects.equals(newUserInput, "2")){
									System.out.println("Please enter the ID of the planet that you would like to remove: ");
									int numInput = scanner.nextInt();
									scanner.nextLine();
									System.out.print("Are you sure that you want to remove this planet? yes or no:");
									newUserInput = scanner.nextLine();
									if (newUserInput.toUpperCase().equals("YES")){
										planetController.deletePlanet(loginUser.getId(), numInput);
									}

								}


								System.out.println("\nWould you like to add or remove a Planet? Press '1' to add a planet, or press '2' to remove a planet: ");
								newUserInput = scanner.nextLine();
							}
						}

							System.out.println("\nWhat would you like to do with your Planet list? Press '1' to add/remove a planet, or press '2' to add/remove a moon: ");
							newUserInput = scanner.nextLine();
					}
				} catch(Exception e){
					System.out.print("An error has occurred in the system!");
				}
				//planetController

				return loginUser;
			}
			else
				System.out.println("Login was NOT successful.");
		}

		else
			System.out.println("Login was NOT successful.");
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
