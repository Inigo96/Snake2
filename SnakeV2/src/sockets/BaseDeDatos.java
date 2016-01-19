package sockets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Base de datos.
 * 
 * @author ander.areizagab
 */
public class BaseDeDatos {

	private static Connection connection = null;
	private static Statement statement = null;

	/**
	 * Inicializa una BD SQLITE y devuelve una conexión con ella. Debe llamarse
	 * a este método antes que ningún otro, y debe devolver no null para poder
	 * seguir trabajando con la BD.
	 * 
	 * @param nombreBD
	 *            Nombre de fichero de la base de datos
	 * @return Conexión con la base de datos indicada. Si hay algún error, se
	 *         devuelve null
	 */
	public static Connection initBD(String nombreBD) {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + nombreBD);
			statement = connection.createStatement();
			statement.setQueryTimeout(30); // poner timeout 30 msg
			return connection;
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

	/**
	 * Cierra la conexión con la Base de Datos
	 */
	public static void close() {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve la conexión si ha sido establecida previamente (#initBD()).
	 * 
	 * @return Conexión con la BD, null si no se ha establecido correctamente.
	 */
	public static Connection getConnection() {
		return connection;
	}

	/**
	 * Devuelve una sentencia para trabajar con la BD, si la conexión si ha sido
	 * establecida previamente (#initBD()).
	 * 
	 * @return Sentencia de trabajo con la BD, null si no se ha establecido
	 *         correctamente.
	 */
	public static Statement getStatement() {
		return statement;
	}

	/**
	 * Crea una tabla de puntuacion en una base de datos, si no existía ya. Debe
	 * haberse inicializado la conexión correctamente.
	 */
	public static void crearTablaBD() {
		if (statement == null)
			return;
		try {
			statement.executeUpdate("create table usuarios "
					+ "(usuario string, puntuacion int)");
		} catch (SQLException e) {
		}
	}

	public static void mostrarUsuario(String usuario) {
		String sentencia = ("select usuario, puntuacion from usuarios");
		try {
			boolean n = true;
			ResultSet rs = statement.executeQuery(sentencia);
			while (rs.next() && n == true) {
				if (usuario == rs.getString(1)) {
					System.out.println(rs.getString(1) + ": "
							+ rs.getInt("puntuacion"));
					n = false;
				}
			}
		} catch (SQLException e) {
		}
	}

	public static void crearUsuario(String usuario) {
		String sentencia = ("insert into usuarios values('" + usuario + "',0)");
		try {
			statement.executeUpdate(sentencia);
		} catch (SQLException e) {
		}
	}

	public static void modificarPuntuacion(String usuario) {
		String sentencia = ("select usuario from usuarios");
		try {
			boolean n = true;
			ResultSet rs = statement.executeQuery(sentencia);
			while (rs.next() && n == true) {
				if (usuario == rs.getString(1)) {
					statement
							.executeUpdate("update puntuacion set puntuacion= puntuacion+1 where usuario='"
									+ usuario + "'");
					n = false;
				}
			}
		} catch (SQLException e) {
		}
	}

}
