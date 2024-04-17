package com.revature;
import java.util.Objects;
import java.util.Scanner;
import java.lang.System;

import com.revature.controller.UserController;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;
import com.revature.service.UserService;

import java.io.IOException;
import java.sql.SQLException;

import com.revature.service.UserService;
import com.revature.utilities.ConnectionUtil;

public class MainDriver {

    public static void main(String[] args) {
        // TODO: implement main method to initialize layers and run the application

        //Set up the user classes in preparation for code
        User newUser = new User();
        UserDao userDao = new UserDao();
        UserService userService = new UserService(userDao);
        UserController userController = new UserController(userService);
        UsernamePasswordAuthentication returnUser = new UsernamePasswordAuthentication();

        // Allow the user to create an account for the Planetarium with user input
        //Create Username
        try (Scanner userScanner = new Scanner(System.in)){
            System.out.println("\nWelcome to the Planetarium!\nPlease press '1' to create a new account, press '2' to login, or press '3' to quit:");
            String userInput = userScanner.nextLine();
            while(!Objects.equals(userInput, "3")) {
                if (Objects.equals(userInput, "1")) {
                    System.out.print("\nPlease enter a username for registration (Must be 30 characters or below): ");
                    userInput = userScanner.nextLine();
                    newUser.setUsername(userInput);

                    //Create Password
                    System.out.print("\nPlease create a password for registry (Must be 30 characters or below): ");
                    userInput = userScanner.nextLine();
                    newUser.setPassword(userInput);
                    userController.register(newUser);
                }

                else if (Objects.equals(userInput, "2")){
                    System.out.print("\nPlease enter your account username: ");
                    userInput = userScanner.nextLine();
                    returnUser.setUsername(userInput);

                    System.out.print("\nPlease enter your account password: ");
                    userInput = userScanner.nextLine();
                    returnUser.setPassword(userInput);

                    userController.authenticate(returnUser);

                }

                System.out.println("\nWelcome to the Planetarium!\nPlease press '1' to create a new account, press '2' to login, or press '3' to quit:");
                userInput = userScanner.nextLine();
            }

        } catch(Exception e){
            System.out.print("An error has occurred in the system!");
        }
    }

}
