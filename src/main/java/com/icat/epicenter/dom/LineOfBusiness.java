/** Program Description: Methods and queries built up for LineOfBusiness table
 *  Author			   : SMNetserv
 *  Date of Creation   : 19/02/2018
**/

package com.icat.epicenter.dom;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class LineOfBusiness {
	public String lineOfBusinessTable;
	public String lOB;
	public String lOBID;
	DBFrameworkConnection connection;
	QueryBuilder build;
	private static List<Integer> testStatus;

	public LineOfBusiness(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("LineOfBusiness");
		lineOfBusinessTable = properties.getProperty("db_LineOfBusinessTable");
		lOB = properties.getProperty("db_LineOfBusiness");
		lOBID = properties.getProperty("db_LOBID");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
	}

	public String getLineOfBusiness(int id) {
		return (String) build.fetch(lOB, lineOfBusinessTable, lOBID + " = " + id);
	}

	public String verifylineOfBusiness(int id, String lineOfBsns, String productselection) {
		String lineOfBusiness = getLineOfBusiness(id);
		testStatus=Assertions.verify(lineOfBusiness, lineOfBsns,
				"Line Of Business : " + lineOfBusiness + " <span class='header'>(" + productselection + ")</span>",
				"Line Of Business : " + lineOfBsns + " <span class='header'>(" + productselection + ")</span>", false,
				false);
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public void verifylineOfBusinessPNB(int id, String lineOfBsns, List<Map<String, String>> pnbtestData,
			int transactionNumber) {
		String lineOfBusiness = getLineOfBusiness(id);
		boolean lineOfBus = false;
		for (int p = transactionNumber; p >= 0; p--) {
			if (!pnbtestData.get(p).get("ProductSelection").equals("") && !lineOfBus) {
				if (p == transactionNumber)
					Assertions.verify(lineOfBusiness, lineOfBsns,
							"Line Of Business : " + lineOfBusiness + " <span class='header'>("
									+ pnbtestData.get(p).get("ProductSelection") + ")</span>",
							"Line Of Business : " + lineOfBsns + " <span class='header'>("
									+ pnbtestData.get(p).get("ProductSelection") + ")</span>",
							false, false);
				else
					Assertions.addInfo(
							"Line Of Business : " + lineOfBusiness + " <span class='header'>("
									+ pnbtestData.get(p).get("ProductSelection") + ")</span>",
							"Line Of Business : " + lineOfBsns + " <span class='header'>("
									+ pnbtestData.get(p).get("ProductSelection") + ")</span>");
				lineOfBus = true;
			}
		}
	}
}
