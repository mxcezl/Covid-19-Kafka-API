package cs2pr3.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class PostgresUtils {
	
	// Parametres
	public static final String USERNAME = "postgres";
	public static final String PASSWORD = "admin";
	
	// Requetes pour les commandes
	public static final String QUERY_GET_GLOBAL = "SELECT data->'Global' FROM public.covid WHERE id=1;";
	public static final String QUERY_COUNTRY = "SELECT elem FROM ( SELECT jsonb_array_elements(data->'Countries') elem FROM public.covid WHERE id=1 ) sub WHERE elem->>'CountryCode'='$COUNTRY_CODE$';";
	public static final String QUERY_ALL = "SELECT data FROM public.covid WHERE id=1;";
	

	private static final Logger LOGGER = Logger.getLogger(PostgresUtils.class.getName());
	
	public static Connection createConnection(String username, String password) {
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

	public static String executeQuery(Connection connection, String query) {
		String data = "NO_RESULT";
		
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) data = rs.getString(1);
			rs.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
