import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    // a) Récupérer une équipe par son ID avec ses joueurs
    public Team findTeamById(Integer id) {
        Team team = null;
        String teamQuery = "SELECT * FROM team WHERE id = ?";
        String playersQuery = "SELECT * FROM player WHERE team_id = ?";
        player.setGoalNb((Integer) playersRs.getObject("goal_nb"));

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement teamStmt = conn.prepareStatement(teamQuery)) {

            // Récupération de l'équipe
            teamStmt.setInt(1, id);
            ResultSet teamRs = teamStmt.executeQuery();

            if (teamRs.next()) {
                team = new Team();
                team.setId(teamRs.getInt("id"));
                team.setName(teamRs.getString("name"));
                team.setContinent(ContinentEnum.valueOf(teamRs.getString("continent")));

                // Récupération des joueurs de l'équipe
                try (PreparedStatement playersStmt = conn.prepareStatement(playersQuery)) {
                    playersStmt.setInt(1, id);
                    ResultSet playersRs = playersStmt.executeQuery();

                    List<Player> players = new ArrayList<>();
                    while (playersRs.next()) {
                        Player player = new Player();
                        player.setId(playersRs.getInt("id"));
                        player.setName(playersRs.getString("name"));
                        player.setAge(playersRs.getInt("age"));
                        player.setPosition(PlayerPositionEnum.valueOf(playersRs.getString("position")));
                        player.setTeam(team); // Définir la référence à l'équipe

                        players.add(player);
                    }
                    team.setPlayers(players);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'équipe : " + e.getMessage());
            e.printStackTrace();
        }

        return team;
    }

    // b) Récupérer la liste des joueurs avec pagination
    public List<Player> findPlayers(int page, int size) {
        List<Player> players = new ArrayList<>();
        String query = "SELECT * FROM player ORDER BY id LIMIT ? OFFSET ?";

        // Calcul de l'offset
        int offset = page * size;

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player();
                player.setId(rs.getInt("id"));
                player.setName(rs.getString("name"));
                player.setAge(rs.getInt("age"));
                player.setPosition(PlayerPositionEnum.valueOf(rs.getString("position")));

                // Note: Pour récupérer l'équipe complète, vous pourriez faire une jointure
                // ou appeler findTeamById si nécessaire

                players.add(player);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des joueurs : " + e.getMessage());
            e.printStackTrace();
        }

        return players;
    }
}