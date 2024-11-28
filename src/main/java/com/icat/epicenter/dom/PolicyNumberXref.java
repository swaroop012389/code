/** Program Description: Methods and queries built up for PolicyNumberXref table
 *  Author			   : SMNetserv
 *  Date of Creation   : 24/02/2018
**/

package com.icat.epicenter.dom;

import java.util.List;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class PolicyNumberXref {
	public String tableName;
	public String externalPolicyNumber;
	public String internalPolicyNumber;
	public String policyNumber;
	DBFrameworkConnection connection;
	QueryBuilder build;
	private static List<Integer> testStatus;

	public PolicyNumberXref(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("PolicyNumberXref");
		tableName = properties.getProperty("db_TableName");
		externalPolicyNumber = properties.getProperty("db_ExternalPolicyNumber");
		internalPolicyNumber = properties.getProperty("db_InternalPolicyNumber");
		policyNumber = properties.getProperty("db_PolicyNumber");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
	}

	public String getExternalPolicyNumber(String policyNum) {
		return (String) build.fetch(externalPolicyNumber, tableName,
				internalPolicyNumber + " = " + "'" + policyNum + "'");
	}

	public String verifyExternalPolicyNum(String PolicyNumber) {
		testStatus=Assertions.verify(getExternalPolicyNumber(PolicyNumber), PolicyNumber,
				"External Policy Number:" + getExternalPolicyNumber(PolicyNumber),
				"Internal Policy Number:" + PolicyNumber, false, false);
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}
}