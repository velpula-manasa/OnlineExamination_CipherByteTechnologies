package com.exam;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ExamSystem {
    private static HashMap<String, User> users = new HashMap<>();
    private static User currentUser = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample users
        users.put("user1", new User("user1", "pass1"));
        users.put("user2", new User("user2", "pass2"));

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.exit(0);
            }
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            currentUser = users.get(username);
            System.out.println("Login successful!");
            showMenu();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static void showMenu() {
        while (currentUser != null) {
            System.out.println("1. Update Profile");
            System.out.println("2. Update Password");
            System.out.println("3. Take Exam");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    updatePassword();
                    break;
                case 3:
                    takeExam();
                    break;
                case 4:
                    currentUser = null;
                    System.out.println("Logged out successfully!");
            }
        }
    }

    private static void updateProfile() {
        System.out.print("Enter new profile info: ");
        scanner.nextLine(); // Consume newline
        String profileInfo = scanner.nextLine();
        currentUser.setProfileInfo(profileInfo);
        System.out.println("Profile updated!");
    }

    private static void updatePassword() {
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();
        currentUser.setPassword(newPassword);
        System.out.println("Password updated!");
    }

    private static void takeExam() {
        String[] questions = {
                "What is the capital of France?\n1. Berlin\n2. Madrid\n3. Paris\n4. Rome",
                "What is 2 + 2?\n1. 3\n2. 4\n3. 5\n4. 6"
        };

        int[] answers = {3, 2};
        int[] userAnswers = new int[questions.length];
        int timeLimit = 60; // Time limit in seconds

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up!");
                autoSubmit(userAnswers);
            }
        }, timeLimit * 1000);

        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            userAnswers[i] = scanner.nextInt();
        }

        timer.cancel(); // Cancel the timer if the user finishes before the time limit
        autoSubmit(userAnswers);
    }

    private static void autoSubmit(int[] userAnswers) {
        System.out.println("Exam submitted!");
        // Evaluate the answers or perform any other required actions
        for (int answer : userAnswers) {
            System.out.print(answer + " ");
        }
        System.out.println();
    }
}

