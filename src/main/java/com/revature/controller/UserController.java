package com.revature.controller;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;


public class UserController {
	
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	public void authenticate(UsernamePasswordAuthentication loginRequestData) {
		// TODO: implement
		User user = new User();
		user = userService.authenticate(loginRequestData);
		if (user != null)
			logout();

	}

	public void register(User registerRequestData) {
		// TODO: implement
		//pass user information into the service class for registration (userService.register(User))
		userService.register(registerRequestData);
	}

	public void logout() {
		// TODO: implement
		System.out.println("You have successfully been logged out!\n");
	}
	
	public boolean checkAuthorization(int userId) {	
		// TODO: implement
		return false;
	}
}
