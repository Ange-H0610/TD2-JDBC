public class Main {
    public static void main(String[] args) {
        DataRetriever retriever = new DataRetriever();

        // Test 1: Récupérer une équipe par ID
        Team team = retriever.findTeamById(1);
        if (team != null) {
            System.out.println("Équipe : " + team.getName());
            System.out.println("Nombre de joueurs : " + team.getPlayersCount());
        }

        // Test 2: Récupérer des joueurs avec pagination
        List<Player> players = retriever.findPlayers(0, 10);
        System.out.println("Nombre de joueurs récupérés : " + players.size());

        // Fermer la connexion
        DBConnection.closeConnection();
    }
}


