package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class InsuredInterest {

	public String tableName;
	public String insuredInterestTypeId;
	public String interest;
	public String description;
	public String beneficiary;
	public String transactionId;
	public String insuredRelationshipId;

	public Map<String, Object> results;
	DBFrameworkConnection connection;
	QueryBuilder build;
	private static List<Integer> testStatus;

	public InsuredInterest(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("InsuredInterest");
		tableName = properties.getProperty("db_TableName");
		insuredInterestTypeId = properties.getProperty("db_InterestTypeId");
		interest = properties.getProperty("db_Interest");
		description = properties.getProperty("db_Description");
		beneficiary = properties.getProperty("db_Beneficiary");
		transactionId = properties.getProperty("db_TransactionId");
		insuredRelationshipId = properties.getProperty("db_InsuredRelationshipId");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
	}

	public Map<String, Object> getInsuredInterestDetails(int transactionID, int interestTypeId) {
		List<String> outFields = new ArrayList<>();
		outFields.add(insuredInterestTypeId);
		outFields.add(insuredRelationshipId);
		outFields.add(description);
		outFields.add(beneficiary);
		build.outFields(outFields);
		build.whereBy(transactionId + " = " + transactionID + " and " + insuredInterestTypeId + " = '" + interestTypeId + "'");
		List<Map<String, Object>> insuredInterestDetails = build.execute(60);
		return results = insuredInterestDetails.get(0);
	}


	public Integer getRelationshipId(int transactionID, int interestTypeId) {
		List<String> outFields = new ArrayList<>();
		outFields.add(insuredRelationshipId);
		build.outFields(outFields);
		return (int) build.fetch(insuredRelationshipId, null, transactionId + " = " + transactionID + " and " + insuredInterestTypeId + " = '" + interestTypeId + "'");

	}

	public String verifyInsuredInterestDetailsNB(Map<String, String> testData, int interestTypeId,int transactionID,String entityType) {
		results = getInsuredInterestDetails(transactionID, interestTypeId);
		if (!testData.get("Entity").equals(""))
			testStatus=Assertions.verify(entityType,testData.get("Entity"),"Entity : " + entityType,
					"Entity : " + testData.get("Entity"),false,false);

		if (!testData.get("Grantor/BeneficiaryName").equals(""))
			testStatus=Assertions.verify(results.get("Beneficiary"), testData.get("Grantor/BeneficiaryName"), "Beneficiary : " + results.get("Beneficiary"),
					"Beneficiary : " + testData.get("Grantor/BeneficiaryName"), false, false);

		else
			Assertions.addInfo("Beneficiary : ", "N/A");

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}

	public void verifyInsuredInterestDetailsPNB(List<Map<String, String>> pnbTestData, int interestTypeId,int transactionID,String entityType,int transactionNumber) {
		results = getInsuredInterestDetails(transactionID, interestTypeId);
		boolean entity=false;
		boolean beneficiary=false;
		for (int p = transactionNumber; p >= 0; p--) {
		if (!pnbTestData.get(p).get("Entity").equals("") && !entity) {
			if(p==transactionNumber)
			Assertions.verify(entityType,pnbTestData.get(p).get("Entity"),"Entity : " + entityType,
					"Entity : " + pnbTestData.get(p).get("Entity"),false,false);

			else
				Assertions.addInfo("Entity : " + entityType,
						"Entity : " + pnbTestData.get(p).get("Entity"));
			entity=true;
		}

		if (!pnbTestData.get(0).get("Grantor/BeneficiaryName").equals("") && !beneficiary) {
			if(p==transactionNumber) {
			if (!pnbTestData.get(p).get("Grantor/BeneficiaryName").equals(""))
			Assertions.verify(results.get("Beneficiary"), pnbTestData.get(p).get("Grantor/BeneficiaryName"), "Beneficiary : " + results.get("Beneficiary"),
					"Beneficiary : " + pnbTestData.get(p).get("Grantor/BeneficiaryName"), false, false);

			else
				Assertions.addInfo("Beneficiary : ", "N/A");
			}
		else {
			if (!pnbTestData.get(p).get("Grantor/BeneficiaryName").equals(""))
			Assertions.addInfo("Beneficiary : "+results.get("Beneficiary").toString(),"Beneficiary : " + pnbTestData.get(p).get("Grantor/BeneficiaryName"));

			else
				Assertions.addInfo("Beneficiary : ", "N/A");
		}
			beneficiary=true;
		}

		if (pnbTestData.get(0).get("Grantor/BeneficiaryName").equals("") && !beneficiary) {
			Assertions.addInfo("Beneficiary : ", "N/A");
			beneficiary=true;
		}
}
}
}

