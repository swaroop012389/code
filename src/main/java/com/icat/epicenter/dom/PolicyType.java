/** Program Description: Methods and queries built up for PolicyType table
 *  Author			   : SMNetserv
 *  Date of Creation   : 24/02/2018
**/

package com.icat.epicenter.dom;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class PolicyType {
	public String policyTypeTable;
	public String policyStructure;
	public String policyTypeID;
	DBFrameworkConnection connection;
	QueryBuilder build;

	public PolicyType(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("PolicyType");
		policyTypeTable = properties.getProperty("db_PolicyTypeTable");
		policyStructure = properties.getProperty("db_PolicyStructure");
		policyTypeID = properties.getProperty("db_PolicyTypeID");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
	}

	public String getPolicyStructure(int polTypeID) {
		return (String) build.fetch(policyStructure, policyTypeTable, policyTypeID + " = " + polTypeID);
	}

	public void verifyPolicyStructure(int polTypeID, String PolicyStructure) {
		String policyStruct = getPolicyStructure(polTypeID);
		Assertions.verify(policyStruct, PolicyStructure, "Policy Structure : " + policyStruct,
				"Policy Structure : " + PolicyStructure, false, false);
	}

	public void verifyPolicyStructurePNB(int polTypeID, List<Map<String, String>> pnbtestData,
			int transactionNumber) {
		String policyStruct = getPolicyStructure(polTypeID);
		boolean polStructure = false;
		for (int p = transactionNumber; p >= 0; p--) {
			if (!pnbtestData.get(p).get("PolicyStructure").equals("") && !polStructure) {
				if (p == transactionNumber)
					Assertions.verify(policyStruct, pnbtestData.get(p).get("PolicyStructure"),
							"Policy Structure : " + policyStruct,
							"Policy Structure : " + pnbtestData.get(p).get("PolicyStructure"), false, false);
				else
					Assertions.addInfo("Policy Structure : " + policyStruct,
							"Policy Structure : " + pnbtestData.get(p).get("PolicyStructure"));
				polStructure = true;
			}
		}
	}
}
