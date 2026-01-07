package enum

import java.sql.DriverManager
import java.sql.SQLException


class DBConnection {
    private static
    var connection: Connection? = null

    private fun DBConnection() {
        // Constructeur privé pour empêcher l'instanciation
    }

    fun getDBConnection(): Connection? {
        if (connection == null) {
            try {
                // Récupération des variables d'environnement
                val jdbcUrl = System.getenv("JDBC_URL")
                val username = System.getenv("USERNAME")
                val password = System.getenv("PASSWORD")

                // Vérification que les variables sont définies
                if (jdbcUrl == null || username == null || password == null) {
                    throw RuntimeException(
                        "Variables d'environnement non définies. " +
                                "Veuillez définir JDBC_URL, USERNAME et PASSWORD."
                    )
                }

                // Établir la connexion
                connection = DriverManager.getConnection(jdbcUrl, username, password)
                println("Connexion à la base de données établie avec succès !")
            } catch (e: SQLException) {
                System.err.println("Erreur lors de la connexion à la base de données : " + e.message)
                e.printStackTrace()
            }
        }
        return connection
    }

    // Méthode pour fermer la connexion
    fun closeConnection() {
        if (connection != null) {
            try {
                connection.close()
                connection = null
                println("Connexion fermée.")
            } catch (e: SQLException) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.message)
            }
        }
    }
}