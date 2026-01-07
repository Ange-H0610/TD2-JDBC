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


    public class UnknownGoalsException extends RuntimeException {
    public UnknownGoalsException(String message) {
        super(message);
    }
}

    public int getPlayersGoals() throws Exception {
        int total = 0;

        for (Player p : players) {
            Integer goals = p.getGoalNb();
            if (goals == null) {
                throw new Exception("Le nombre de buts est inconnu pour le joueur " + p.getName());
            }
            total += goals;
        }

        return total;
    }

}
