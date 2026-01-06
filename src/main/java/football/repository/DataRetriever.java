package football.repository;


import football.db.DBConnection;
import football.enums.*;
import football.model.*;

import java.sql.*;
import java.util.*;

public class DataRetriever {

    private DBConnection dbConnection;

    public DataRetriever(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    public Team findTeamById(Integer id) throws SQLException {

        Connection conn = dbConnection.getConnection();

        PreparedStatement teamStmt =
                conn.prepareStatement("SELECT * FROM team WHERE id = ?");
        teamStmt.setInt(1, id);
        ResultSet rsTeam = teamStmt.executeQuery();

        if (!rsTeam.next()) return null;

        Team team = new Team(
                rsTeam.getInt("id"),
                rsTeam.getString("name"),
                ContinentEnum.valueOf(rsTeam.getString("continent")),
                new ArrayList<>()
        );

        PreparedStatement playerStmt =
                conn.prepareStatement("SELECT * FROM player WHERE id_team = ?");
        playerStmt.setInt(1, id);
        ResultSet rsPlayers = playerStmt.executeQuery();

        while (rsPlayers.next()) {
            team.getPlayers().add(
                    new Player(
                            rsPlayers.getInt("id"),
                            rsPlayers.getString("name"),
                            rsPlayers.getInt("age"),
                            PlayerPositionEnum.valueOf(rsPlayers.getString("position")),
                            team
                    )
            );
        }

        return team;
    }

    public List<Player> findPlayers(int page, int size) throws SQLException {

        List<Player> players = new ArrayList<>();
        int offset = (page - 1) * size;

        Connection conn = dbConnection.getConnection();
        PreparedStatement stmt =
                conn.prepareStatement(
                        "SELECT * FROM player ORDER BY id LIMIT ? OFFSET ?");

        stmt.setInt(1, size);
        stmt.setInt(2, offset);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            players.add(new Player(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    PlayerPositionEnum.valueOf(rs.getString("position")),
                    null
            ));
        }

        return players;
    }

    public void createPlayers(List<Player> players) throws SQLException {

        Connection conn = dbConnection.getConnection();
        conn.setAutoCommit(false);

        try {
            PreparedStatement insert =
                    conn.prepareStatement(
                            "INSERT INTO player(name, age, position, id_team) VALUES (?, ?, ?, ?)");

            for (Player p : players) {
                insert.setString(1, p.getName());
                insert.setInt(2, p.getAge());
                insert.setString(3, p.getPosition().name());
                insert.setObject(4, null);
                insert.executeUpdate();
            }

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
    }
}

