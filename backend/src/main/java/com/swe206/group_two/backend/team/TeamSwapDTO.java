package com.swe206.group_two.backend.team;

public class TeamSwapDTO {
    private int oldUser;
    private int newUser;

    public TeamSwapDTO(int oldUser, int newUser) {
        this.oldUser = oldUser;
        this.newUser = newUser;
    }

    public int getOldUser() {
        return oldUser;
    }

    public int getNewUser() {
        return newUser;
    }

}
