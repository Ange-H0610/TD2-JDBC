package model;

import java.util.List;

public class Team {
    private int id;
    private String name;
    private ContinentEnum continent;
    private List<Player> players;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ContinentEnum getContinent() { return continent; }
    public void setContinent(ContinentEnum continent) { this.continent = continent; }

    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }
}
