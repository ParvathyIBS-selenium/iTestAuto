/* Description : This class works with DB connection.  The class file have different functions
 * relating to DB Connection.  The different functions are like GetDBConnection, dbDetails()
 * Author :
 * Comment Author : Raghothama
 * Date created : 03-Jun-2014
 * Modified by :
 * Modified on :   
 */
package controls;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Map.Entry;


public class DBConnection {
	
	/**
	 * Method for database connection
	 *
	 */
	public Connection getDBConnection() throws SQLException
	{
		Connection conn = null;
		DBDetailsVO dbDetailsVO = null;
		try
		{
			dbDetailsVO = dbDetails();	


			String oraclURLDetails = "", databaseName = "", username = "", pwd = "";


			if (dbDetailsVO != null) {
				oraclURLDetails = dbDetailsVO.getServerName();
				username = dbDetailsVO.getUsername();
				pwd = dbDetailsVO.getPwd();
			}


			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = oraclURLDetails;
			conn = DriverManager.getConnection(url, username, pwd);

		} catch (SQLException sql) {
			sql.printStackTrace();
		} catch (Exception _ex) {
			_ex.printStackTrace();
		}
		return conn;

	}

	public  DBDetailsVO dbDetails() {
		DBDetailsVO dbDetailsVO = new DBDetailsVO();
		try {

			Properties p = new Properties();

			String path = System.getProperty("user.dir");
			String resourcePath = path+"\\src\\resources\\";
			String file = resourcePath+"DBDetails.properties";
			System.out.println("file ::::::::::::"+file);
			p.load(new FileInputStream(file));

			String dburl = "";
			String dBUser = "";
			String dBPassword = "";
			String dBObjectOwner = "";
			for (Entry e : p.entrySet()) {

				if (p.containsKey("DATASOURCE1.dBUrl")) {
					dburl = (String) p.get("DATASOURCE1.dBUrl");
					if (dburl != null && dburl != "") {
						dbDetailsVO.setServerName(dburl);
					}
				}

				if (p.containsKey("DATASOURCE1.dBUser")) {
					dBUser = (String) p.get("DATASOURCE1.dBUser");
					if (dBUser != null && dBUser != "") {
						dbDetailsVO.setUsername(dBUser);
					}
				}

				if (p.containsKey("DATASOURCE1.dBPassword")) {
					dBPassword = (String) p.get("DATASOURCE1.dBPassword");
					if (dBPassword != null && dBPassword != "") {
						dbDetailsVO.setPwd(dBPassword);
					}
				}
				if (p.containsKey("DATASOURCE1.dBObjectOwner")) {
					dBObjectOwner = (String) p.get("DATASOURCE1.dBObjectOwner");
					if (dburl != null && dburl != "") {
						dbDetailsVO.setServerName(dburl);
					}
				}
			}


		}catch(Throwable t) {
			t.printStackTrace();
		}

		return dbDetailsVO;


	}
}