/** Program Description: Methods and queries built up for DBError table
 *  Author			   : SMNetserv
 *  Date of Creation   : 15/02/2018
**/

package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.ReportsManager;
import com.NetServAutomationFramework.generic.TableProperties;

public class DBError {
	public String dbErrorTableName;
	public String errorNumber;
	public String errorDescription;
	public String policyNumber;
	public String transactionID;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public List<Map<String, Object>> dberrorData;

	public DBError(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("DBError");
		dbErrorTableName = properties.getProperty("db_DbErrorTableName");
		errorNumber = properties.getProperty("db_ErrorNumber");
		errorDescription = properties.getProperty("db_ErrorDescription");
		policyNumber = properties.getProperty("db_PolicyNumber");
		transactionID = properties.getProperty("db_TransactionId");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(dbErrorTableName);
	}

	public List<Map<String, Object>> getdberrordetails(String policyNumberData) {
		List<String> outFields = new ArrayList<>();
		outFields.add(errorNumber);
		outFields.add(errorDescription);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = '" + policyNumberData + "'");
		dberrorData = build.execute(60);
		if (dberrorData.isEmpty())
			return null;
		else
			return dberrorData;
	}

	public void verifyDBError(String policyNumber, int transactionid) {
		dberrorData = getdberrordetails(policyNumber);
		if (dberrorData != null) {
			ReportsManager.startTest(null, "DB Errors",
					"Error Count : " + dberrorData.size() + " - Transactionid :" + transactionid);
			for (int i = 0; i < dberrorData.size(); i++) {
				Assertions.verify(dberrorData.get(i).get("ErrorDescription"), "No Error",
						dberrorData.get(i).get("ErrorNumber") + " - " + dberrorData.get(i).get("ErrorDescription"), "",
						false, false);
			}
		}
		else {
			ReportsManager.startTest(null, "DB Errors", "");
			Assertions.addInfo("DB Errors", "No DB Errors for Transactionid : " + transactionid);
		}
		ReportsManager.endTest(this.getClass().getName());
	}
	public List<Map<String, Object>> getdberrordetailsPNB(String policyNumberData, int transactionid) {
		List<String> outFields = new ArrayList<>();
		outFields.add(errorNumber);
		outFields.add(errorDescription);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = '" + policyNumberData + "' and " + transactionID + " = " + transactionid);
		dberrorData = build.execute(60);
		if (dberrorData.isEmpty())
			return null;
		else
			return dberrorData;
	}

	public void verifyDBErrorPNB(String policyNumber, int transactionid) {
		dberrorData = getdberrordetailsPNB(policyNumber, transactionid);
		if (dberrorData != null) {
			for (Map<String, Object> element : dberrorData) {
				Assertions.verify(element.get("ErrorDescription"), "No Error",
						element.get("ErrorNumber") + " - " + element.get("ErrorDescription"), "",
						false, false);
			}
		} else {
			Assertions.addInfo("DB Errors", "No DB Errors for Transactionid : " + transactionid);
		}
	}

}
