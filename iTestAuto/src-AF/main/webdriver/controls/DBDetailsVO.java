/* Description : This class works with DBDetails class.  The class file have different functions
 * relating to DBDetailscontrol.  There are different set and get functions like getServerName, SetServerName
 * getDatabaseName, SetDatabaseName, getUsername, SetUsername, getPwd, SetPwd.  This class would build the DB object
 * to make connection 
 * Author :
 * Comments Author: Raghothama
 * Date created : 03-Jun-2014
 * Modification comments:
 * Modified by :
 * Modified on :   
 */
package controls;

public class DBDetailsVO {

	private String serverName = "";

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	private String databaseName = "";

	private String username = "";

	private String pwd = "";

}
