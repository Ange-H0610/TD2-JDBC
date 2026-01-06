package football.model;

import football.enums.PlayerPositionEnum;

public class Player {

    private Integer id;
    private String name;
    private int age;
    private PlayerPositionEnum position;
    private Team team;

    public Player(Integer id, String name, int age,
                  PlayerPositionEnum position, Team team) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.position = position;
        this.team = team;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public PlayerPositionEnum getPosition() {
        return position;
    }
    public Team getTeam() {
        return team;
    }
}
