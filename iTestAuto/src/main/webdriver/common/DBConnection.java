package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	public void getData() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://192.168.185.167/his_qa_aug1", "1aug_his",
					"aug_his_new");

			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM his_fb_mst_city");

			while (rs.next()) {
				int numColumns = rs.getMetaData().getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					// Column numbers start at 1.
					// Also there are many methods on the result set to return
					// the column as a particular type. Refer to the Sun
					// documentation
					// for the list of valid conversions.
					System.out.println("COLUMN " + i + " = " + rs.getObject(i));
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (Throwable ignore) { /*
										 * Propagate the original exception
										 * instead of this one that you may want
										 * just logged
										 */
			}
			try {
				stmt.close();
			} catch (Throwable ignore) { /*
										 * Propagate the original exception
										 * instead of this one that you may want
										 * just logged
										 */
			}
			try {
				conn.close();
			} catch (Throwable ignore) { /*
										 * Propagate the original exception
										 * instead of this one that you may want
										 * just logged
										 */
			}
		}
	}
}
