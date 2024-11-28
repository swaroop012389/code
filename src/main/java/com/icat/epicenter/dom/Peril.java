/** Program Description: Methods and queries built up for Peril table
 *  Author			   : SMNetserv
 *  Date of Creation   : 22/02/2018
**/

package com.icat.epicenter.dom;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class Peril {
	public String perilTable;
	public String perilDesc;
	public String perilID;
	DBFrameworkConnection connection;
	QueryBuilder build;
	private static List<Integer> testStatus;

	public Peril(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("Peril");
		perilTable = properties.getProperty("db_TableNamePeril");
		perilDesc = properties.getProperty("db_PerilDsc");
		perilID = properties.getProperty("db_PerilId");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
	}

	public String getPeril(Short id) {
		return (String) build.fetch(perilDesc, perilTable, perilID + " = " + id);
	}

	public String verifyPeril(Short id, String perilName) {
		String peril = getPeril(id);
		testStatus=Assertions.verify(peril, perilName, "Peril : " + peril, "Peril : " + perilName, false, false);
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public void verifyPerilPNB(Short id, List<Map<String, String>> pnbtestData, int transactionNumber) {
		String peril = getPeril(id);
		boolean peri = false;
		for (int p = transactionNumber; p >= 0; p--) {
			if (!pnbtestData.get(p).get("Peril").equals("") && !peri) {
				if (p == transactionNumber)
					Assertions.verify(peril, pnbtestData.get(p).get("Peril"), "Peril : " + peril,
							"Peril : " + pnbtestData.get(p).get("Peril"), false, false);
				else
					Assertions.addInfo("Peril : " + peril, "Peril : " + pnbtestData.get(p).get("Peril"));
				peri = true;
			}
		}
	}
}
