import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLInjectionCompliant {
	String hashPassword(char[] password) {
		// create hash of password
		return null;
	}

	public void doPrivilegedAction(String username, char[] password) throws SQLException {
		Connection connection = getConnection();
		if (connection == null) {
			// Handle error
		}
		try {
			String pwd = hashPassword(password);
			// Ensure that the length of user name is legitimate
			if (username.length() > 8) {
				// Handle error
			}
			String sqlString = "select * from db_user where username=? and password=?";
			PreparedStatement stmt = connection.prepareStatement(sqlString);
			//Use the set*() methods of the PreparedStatement class to enforce strong type checking.
			//This mitigates the SQL injection vulnerability because the input is properly escaped by
			//automatic entrapment within double quotes.
			stmt.setString(1, username);
			stmt.setString(2, pwd);
			ResultSet rs = stmt.executeQuery();

			if (!rs.next()) {
				throw new SecurityException("User name or password incorrect");
			}
			// Authenticated, proceed
		} finally {
			try {
				connection.close();
			} catch (SQLException x) {
				// forward to handler
			}
		}
	}

	private Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}
}
