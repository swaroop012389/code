/** Program Description: Methods and queries built up for Carrier table
 *  Author			   : SMNetserv
 *  Date of Creation   : 13/02/2018
**/

package com.icat.epicenter.dom;

import java.util.List;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class Carrier {
	public String carrierTable;
	public String carrierName;
	public String policyCarrierID;
	public String carrierDetail;
	DBFrameworkConnection connection;
	QueryBuilder build;
	private static List<Integer> testStatus;

	public Carrier(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("Carrier");
		carrierTable = properties.getProperty("db_CarrierTableName");
		carrierName = properties.getProperty("db_CarrierName");
		policyCarrierID = properties.getProperty("db_CarrierID");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
	}

	public String getCarrierName(int carrierID) {
		return (String) build.fetch(carrierName, carrierTable, policyCarrierID + " = " + carrierID);
	}

	public String verifyCarrierName(int carrierID, String carrier) {
		String carriername = getCarrierName(carrierID);
		testStatus=Assertions.verify(carriername, carrier, "Carrier : " + carriername, "Carrier : " + carrier, false, false);
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public void verifyCarrierNamePNB(int carrierID, String carrier) {
		String carriername = getCarrierName(carrierID);
		Assertions.addInfo("Carrier : " + carriername, "Carrier : " + carrier);
	}
}