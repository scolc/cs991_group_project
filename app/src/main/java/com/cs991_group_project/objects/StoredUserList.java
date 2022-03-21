package com.cs991_group_project.objects;


import java.util.ArrayList;


public class StoredUserList {

    private ArrayList<User> usersList;

    public StoredUserList() {

        usersList = new ArrayList<>();

    }

    // Getters and Setters

    public ArrayList<User> getStoredUsersList() {
        return usersList;
    }

    public void setUsersList(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

    // Methods

    /**
     * Takes a User object and adds it to the list
     * @param newUser the new storedUser object
     */
    public void addUser(User newUser) {
        usersList.add(newUser);
    }

    /**
     * Compares the entered login details with the stored users list
     * @param email The users entered Email
     * @param password The users entered Password
     * @return true if details are correct with the index of the user in the list, "false" otherwise
     */
    public User checkLoginDetails(String email, String password){

        for (User user : usersList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Compares the entered email with the stored users list
     * @param email The users entered email
     * @return true if details are found within the list, false otherwise
     */
    public boolean checkEmailExists(String email){

        for (User user : usersList) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }

        return false;
    }
}
