package com.swe206.group_two.backend.team;

import java.util.List;

public class TeamDTO {
    private String name;

    private List<Integer> usersIds;

    public TeamDTO(String name, List<Integer> usersIds) {
        this.name = name;
        this.usersIds = usersIds;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getUsersIds() {
        return usersIds;
    }

    public Team toEntity(Integer tournamtnId, Integer rankId) {
        return new Team(name, tournamtnId, rankId);
    }

    @Override
    public String toString() {
        return "TeamDTO{"
                + "name='" + name + "', "
                + "users=" + usersIds + ""
                + "}";
    }
}
