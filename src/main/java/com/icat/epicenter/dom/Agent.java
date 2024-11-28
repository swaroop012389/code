/** Program Description: Methods and queries built up for Agent table
 *  Author			   : SMNetserv
 *  Date of Creation   : 13/02/2018
**/

package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class Agent {
	public String agentTable;
	public String agentPhoneNumber;
	public String licenseNum;
	public String agentAddress1;
	public String agentAddress2;
	public String agentCity;
	public String agentState;
	public String agentZip;
	public String policyAgentID;
	public String masterProducer;
	public String transactionID;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public Map<String, Object> agentResults;
	private static List<Integer> testStatus;

	public Agent(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("Agent");
		agentTable = properties.getProperty("db_TableNameAgent");
		agentPhoneNumber = properties.getProperty("db_AgentPhoneNumber");
		licenseNum = properties.getProperty("db_LicenseNum");
		agentAddress1 = properties.getProperty("db_AgentAddr1");
		agentAddress2 = properties.getProperty("db_AgentAddr2");
		agentCity = properties.getProperty("db_AgentCity");
		agentState = properties.getProperty("db_AgentState");
		agentZip = properties.getProperty("db_AgentZip");
		policyAgentID = properties.getProperty("db_AgentID");
		masterProducer = properties.getProperty("db_MasterProducer");
		transactionID = properties.getProperty("db_TransactionId");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(agentTable);
	}

	public Map<String, Object> getAgentDetails(long agentID) {
		List<String> outfield = new ArrayList<>();
		outfield.add(agentPhoneNumber);
		outfield.add(licenseNum);
		outfield.add(agentAddress1);
		outfield.add(agentAddress2);
		outfield.add(agentCity);
		outfield.add(agentState);
		outfield.add(agentZip);
		outfield.add(masterProducer);
		outfield.add(policyAgentID);
		build.outFields(outfield);
		build.whereBy(policyAgentID + " = " + agentID);
		List<Map<String, Object>> agentDetails = build.execute(60);
		return agentResults = agentDetails.get(0);
	}

	public String verifyAgentDetails(Map<String, String> binderData, Map<String, String> testData, long agentID) {
		agentResults = getAgentDetails(agentID);
		Assertions.verify(
				agentResults.get("masterProducer") + "."
						+ agentResults.get("AgentId").toString()
								.substring(agentResults.get("AgentId").toString().length() - 1),
				testData.get("ProducerNumber"),
				"Producer Number : " + agentResults.get("masterProducer") + "."
						+ agentResults.get("AgentId").toString()
								.substring(agentResults.get("AgentId").toString().length() - 1),
				"Producer Number : " + testData.get("ProducerNumber"), false, false);
		if (!agentResults.get("phoneNum").equals(""))
			Assertions.verify(agentResults.get("phoneNum"), binderData.get("AgentPhoneNumber"),
					"Agent Phone Number : " + agentResults.get("phoneNum"),
					"Agent Phone Number : " + binderData.get("AgentPhoneNumber"), false, false);
		Assertions.verify(agentResults.get("licenseNum"), binderData.get("LicenseNum"),
				"License Number : " + agentResults.get("licenseNum"),
				"License Number : " + binderData.get("LicenseNum"), false, false);
		if (!agentResults.get("PhyAdd2").equals(""))
			Assertions.verify(
					agentResults.get("PhyAdd1") + "," + agentResults.get("PhyAdd2") + "," + agentResults.get("phyCity")
							+ "," + agentResults.get("PhyState") + "," + agentResults.get("PhyZip"),
					binderData.get("Agentaddress"),
					"Agent Address : " + agentResults.get("PhyAdd1") + "," + agentResults.get("PhyAdd2") + ","
							+ agentResults.get("phyCity") + "," + agentResults.get("PhyState") + ","
							+ agentResults.get("PhyZip"),
					"Agent Address : " + binderData.get("Agentaddress"), false, false);
		else
			testStatus=Assertions.verify(
					agentResults.get("PhyAdd1") + " " + agentResults.get("phyCity") + "," + agentResults.get("PhyState")
							+ "," + agentResults.get("PhyZip"),
					binderData.get("Agentaddress"),
					"Agent Address : " + agentResults.get("PhyAdd1") + " " + agentResults.get("phyCity") + ","
							+ agentResults.get("PhyState") + "," + agentResults.get("PhyZip"),
					"Agent Address : " + binderData.get("Agentaddress"), false, false);
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public void verifyAgentDetailsPNB(Map<String, String> binderData, List<Map<String, String>> testData,
			long agentID, int transactionNumber) {
		agentResults = getAgentDetails(agentID);
		boolean prodNumber = false;
		for (int p = transactionNumber; p >= 0; p--) {
			if (!testData.get(p).get("ProducerNumber").equals("") && !prodNumber) {
				if (p == transactionNumber)
					Assertions.verify(
							agentResults.get("masterProducer") + "."
									+ agentResults.get("AgentId").toString()
											.substring(agentResults.get("AgentId").toString().length() - 1),
							testData.get(p).get("ProducerNumber"),
							"Producer Number : " + agentResults.get("masterProducer") + "."
									+ agentResults.get("AgentId").toString()
											.substring(agentResults.get("AgentId").toString().length() - 1),
							"Producer Number : " + testData.get(p).get("ProducerNumber"), false, false);
				else
					Assertions.addInfo(
							"Producer Number : " + agentResults.get("masterProducer") + "."
									+ agentResults.get("AgentId").toString()
											.substring(agentResults.get("AgentId").toString().length() - 1),
							"Producer Number : " + testData.get(p).get("ProducerNumber"));
				prodNumber = true;
			}
		}
		if (!agentResults.get("phoneNum").equals(""))
			Assertions.addInfo("Agent Phone Number : " + agentResults.get("phoneNum"),
					"Agent Phone Number : " + binderData.get("AgentPhoneNumber"));
		Assertions.addInfo("License Number : " + agentResults.get("licenseNum"),
				"License Number : " + binderData.get("LicenseNum"));
		if (!agentResults.get("PhyAdd2").equals(""))
			Assertions.addInfo("Agent Address : " + agentResults.get("PhyAdd1") + "," + agentResults.get("PhyAdd2")
					+ "," + agentResults.get("phyCity") + "," + agentResults.get("PhyState") + ","
					+ agentResults.get("PhyZip"), "Agent Address : " + binderData.get("Agentaddress"));
		else
			Assertions.addInfo(
					"Agent Address : " + agentResults.get("PhyAdd1") + " " + agentResults.get("phyCity") + ","
							+ agentResults.get("PhyState") + "," + agentResults.get("PhyZip"),
					"Agent Address : " + binderData.get("Agentaddress"));
	}
}
