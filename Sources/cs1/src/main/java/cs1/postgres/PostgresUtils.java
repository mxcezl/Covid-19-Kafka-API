package cs1.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Logger;

public class PostgresUtils {
	
	private static final Logger LOGGER = Logger.getLogger(PostgresUtils.class.getName());
	private static final String QUERY_UPDATE = "UPDATE public.covid SET data='$JSON$', date_update='$UPDATE_DATE$' WHERE id=1;";
	
	public static Connection createPostgresConnection(String username, String password) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/HOPSIIA", username, password);
			if (connection != null) {
				return connection;
			} else {
				LOGGER.severe("Connection to PostgreSQL failed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void sendJsonToPostgres(Connection connection, String json) {
		try {
			String query = QUERY_UPDATE;
			
			query = query.replace("$JSON$", json.replace("\'", "\'\'"));
			query = query.replace("$UPDATE_DATE$", new Date().toString());
			
			if (connection.createStatement().executeUpdate(query) > 0) {
				LOGGER.info("La base de donnée a été mise à jour le : " + new Date());
			} else {
				LOGGER.severe("Erreur lors de l'execution de la requete.");
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
