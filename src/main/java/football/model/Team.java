package football.model;


import football.enums.ContinentEnum;
import java.util.List;

public class Team {

    private Integer id;
    private String name;
    private ContinentEnum continent;
    private List<Player> players;

    public Team(Integer id, String name, ContinentEnum continent, List<Player> players) {
        this.id = id;
        this.name = name;
        this.continent = continent;
        this.players = players;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public ContinentEnum getContinent() {
        return continent;
    }
    public List<Player> getPlayers() {
        return players;
    }
}
