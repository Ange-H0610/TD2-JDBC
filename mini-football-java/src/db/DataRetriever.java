package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Player;

public class DataRetriever {

    private Connection connection;

    public DataRetriever() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    // ============================================================
    // 1) FIND TEAM BY ID (Team + Players)
    // ============================================================

    public model.Team findTeamById(Integer id) throws SQLException {

        model.Team team = null;

        String sql = """
            SELECT t.id AS t_id,
                   t.name AS t_name,
                   t.continent,
                   p.id AS p_id,
                   p.name AS p_name,
                   p.age,
                   p.position
            FROM team t
            LEFT JOIN player p ON p.id_team = t.id
            WHERE t.id = ?
        """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            if (team == null) {
                team = new model.Team();
                team.setId(rs.getInt("t_id"));
                team.setName(rs.getString("t_name"));
                team.setContinent(
                        model.ContinentEnum.valueOf(rs.getString("continent"))
                );
            }

            if (rs.getObject("p_id") != null) {
                model.Player p = new model.Player();
                p.setId(rs.getInt("p_id"));
                p.setName(rs.getString("p_name"));
                p.setAge(rs.getInt("age"));
                p.setPosition(
                        model.PlayerPositionEnum.valueOf(rs.getString("position"))
                );
                p.setTeam(team);
                team.getPlayers().add(p);
            }
        }

        return team;
    }

    // ============================================================
    // 2) FIND ALL TEAMS
    // ============================================================

    public List<model.Team> findAllTeams() throws SQLException {

        List<model.Team> list = new ArrayList<>();

        String sql = "SELECT id, name, continent FROM team";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.Team t = new model.Team();
            t.setId(rs.getInt("id"));
            t.setName(rs.getString("name"));
            t.setContinent(
                    model.ContinentEnum.valueOf(rs.getString("continent"))
            );
            list.add(t);
        }

        return list;
    }

    // ============================================================
    // 3) FIND PLAYERS BY TEAM ID
    // ============================================================

    public List<model.Player> findPlayersByTeamId(Integer idTeam) throws SQLException {

        List<model.Player> list = new ArrayList<>();

        String sql = """
            SELECT id, name, age, position
            FROM player
            WHERE id_team = ?
        """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, idTeam);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.Player p = new model.Player();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setAge(rs.getInt("age"));
            p.setPosition(
                    model.PlayerPositionEnum.valueOf(rs.getString("position"))
            );
            list.add(p);
        }

        return list;
    }

    // ============================================================
    // 4) INSERT TEAM
    // ============================================================

    public boolean insertTeam(model.Team t) throws SQLException {

        String sql = """
            INSERT INTO team(name, continent)
            VALUES(?, ?)
        """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, t.getName());
        ps.setString(2, t.getContinent().toString());

        return ps.executeUpdate() > 0;
    }

    // ============================================================
    // 5) INSERT PLAYER
    // ============================================================

    public boolean insertPlayer(model.Player p) throws SQLException {

        String sql = """
            INSERT INTO player(name, age, position, id_team)
            VALUES(?, ?, ?, ?)
        """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, p.getName());
        ps.setInt(2, p.getAge());
        ps.setString(3, p.getPosition().toString());
        ps.setInt(4, p.getTeam().getId());

        return ps.executeUpdate() > 0;
    }

    // ============================================================
    // 6) UPDATE TEAM
    // ============================================================

    public boolean updateTeam(model.Team t) throws SQLException {

        String sql = """
            UPDATE team
            SET name = ?, continent = ?
            WHERE id = ?
        """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, t.getName());
        ps.setString(2, t.getContinent().toString());
        ps.setInt(3, t.getId());

        return ps.executeUpdate() > 0;
    }

    // ============================================================
    // 7) UPDATE PLAYER
    // ============================================================

    public boolean updatePlayer(model.Player p) throws SQLException {

        String sql = """
            UPDATE player
            SET name = ?, age = ?, position = ?, id_team = ?
            WHERE id = ?
        """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, p.getName());
        ps.setInt(2, p.getAge());
        ps.setString(3, p.getPosition().toString());
        ps.setInt(4, p.getTeam().getId());
        ps.setInt(5, p.getId());

        return ps.executeUpdate() > 0;
    }

    // ============================================================
    // 8) DELETE TEAM (Cascade delete)
    // ============================================================

    public boolean deleteTeam(Integer id) throws SQLException {

        String sql1 = "DELETE FROM player WHERE id_team = ?";
        String sql2 = "DELETE FROM team WHERE id = ?";

        PreparedStatement ps1 = connection.prepareStatement(sql1);
        ps1.setInt(1, id);
        ps1.executeUpdate();

        PreparedStatement ps2 = connection.prepareStatement(sql2);
        ps2.setInt(1, id);

        return ps2.executeUpdate() > 0;
    }

    // ============================================================
    // 9) DELETE PLAYER
    // ============================================================

    public boolean deletePlayer(Integer id) throws SQLException {

        String sql = "DELETE FROM player WHERE id = ?";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);

        return ps.executeUpdate() > 0;
    }
private Player mapPlayer(ResultSet rs) throws SQLException {
    Player p = new Player();
    p.setId(rs.getInt("id"));
    p.setName(rs.getString("name"));
    p.setGoalNb((Integer) rs.getObject("goal_nb")); 
    return p;
}
public void savePlayer(Player p) throws SQLException {
    String sql = "UPDATE Player SET name = ?, goal_nb = ? WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, p.getName());
        if (p.getGoalNb() != null) {
            ps.setInt(2, p.getGoalNb());
        } else {
            ps.setNull(2, Types.INTEGER);
        }
        ps.setInt(3, p.getId());
        ps.executeUpdate();
    }
}
}
