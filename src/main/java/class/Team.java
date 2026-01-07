import org.ContinentEnum;

import java.util.List;
import java.util.ArrayList;

public class Team {
    private int id;
    private String name;
    private ContinentEnum continent;
    private List<Player> players;

    // Constructeurs
    public Team() {
        this.players = new ArrayList<>();
    }

    public Team(int id, String name, ContinentEnum continent) {
        this();
        this.id = id;
        this.name = name;
        this.continent = continent;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ContinentEnum getContinent() { return continent; }
    public void setContinent(ContinentEnum continent) { this.continent = continent; }

    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }

    // Méthode pour ajouter un joueur
    public void addPlayer(Player player) {
        this.players.add(player);
        player.setTeam(this);
    }

    // Méthode demandée
    public Integer getPlayersCount() {
        return this.players.size();
    }
}

public Integer getPlayersGoals() {
    int total = 0;

    for (Player player : players) {
        if (player.getGoalNb() == null) {
            throw new RuntimeException(
                    "Le nombre de buts du joueur " + player.getName() + " est encore inconnu. Calcul impossible."
            );
        }
        total += player.getGoalNb();
    }

    return total;
}
