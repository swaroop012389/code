package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.google.common.collect.ComparisonChain;

public class IOPremTaxFee {
	public String tableName;
	public String policyNumber;
	public String transactionId;
	public String feeTypeId;
	public String feeValue;
	public String ioPremTaxId;
	public String ioPremCompId;
	ConsolidatedCodeTable consolidatedCode;
	public DBFrameworkConnection connection;
	public QueryBuilder build;

	public IOPremTaxFee(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("IOPremTaxFee");
		tableName = properties.getProperty("db_TableName");
		policyNumber = properties.getProperty("db_PolicyNumber");
		transactionId = properties.getProperty("db_TransactionId");
		feeTypeId = properties.getProperty("db_FeeTypeId");
		feeValue = properties.getProperty("db_FeeValue");
		ioPremTaxId = properties.getProperty("db_IOPremTaxId");
		ioPremCompId = properties.getProperty("db_IOPremCompId");
		consolidatedCode=new ConsolidatedCodeTable(dbConfig);
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
	}
	public List<Integer> getfeeTypeId(int transactionid) {
		List<String> outFields = new ArrayList<>();
		outFields.add(feeTypeId);
		build.outFields(outFields);
		build.whereBy(transactionId + " = " + transactionid);
		build.orderBy(ioPremTaxId);
		List<Map<String, Object>> feeData = build.execute(60);
		List<Integer> feeId = new ArrayList<>();
		for (int i = 0; i < feeData.size(); i++) {
			feeId.add((Integer) feeData.get(i).get(feeTypeId));
		}

		return feeId;
	}

	public List<Integer> getIOPremCompId(int transactionid) {
		List<String> outFields = new ArrayList<>();
		outFields.add(ioPremCompId);
		build.outFields(outFields);
		build.whereBy(transactionId + " = " + transactionid);
		build.orderBy(ioPremTaxId);
		List<Map<String, Object>> premData = build.execute(60);
		List<Integer> premId = new ArrayList<>();
		for (int i = 0; i < premData.size(); i++) {
			premId.add((Integer) premData.get(i).get("IOPremCompId"));
		}

		return premId;
	}
	public List<Map<String, Object>> getPremiumDetails(int transactionid) {
		List<String> outFields = new ArrayList<>();
		outFields.add(feeTypeId);
		outFields.add(ioPremCompId);
		outFields.add(ioPremTaxId);
		outFields.add(feeValue);
		build.outFields(outFields);
		build.whereBy(transactionId + " = " + "'" + transactionid + "'");
	    build.orderBy(ioPremTaxId);
		List<Map<String, Object>> premiumDetails = build.execute(60);
		return premiumDetails;
	}

	public List<Map<String, String>> getDBData(List<Map<String, Object>> dbData) {
		List<Map<String, String>> alteredData = new ArrayList<>();
		for (int i = 0; i < dbData.size(); i++) {
			Map<String, String> data = new HashMap<>();
			data.put("FeeType", consolidatedCode.getConsolidatedCodeDesc((Integer) dbData.get(i).get("FeeTypeId")));
			data.put("PremiumType", consolidatedCode.getConsolidatedCodeDesc((Integer) dbData.get(i).get("IOPremCompId")));
			data.put("FeeValue", dbData.get(i).get("FeeValue").toString());
			alteredData.add(data);
		}
		Collections.sort(alteredData, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				/*return ComparisonChain.start().compare(o1.get("FeeType"), o2.get("FeeType"))
						.compare(o1.get("PremiumType"), o2.get("PremiumType")).result();*/
				return ComparisonChain.start().compare(o1.get("PremiumType"), o2.get("PremiumType"))
						.compare(o1.get("FeeType"), o2.get("FeeType")).result();
			}
		});
		return alteredData;
	}

	public List<Map<String, String>> getBinderData(List<Map<String, String>> binderData) {
		List<Map<String, String>> alteredDate = new ArrayList<>();
		for (int i = 0; i < binderData.size(); i++) {
				alteredDate.add(binderData.get(i));
		}
		Collections.sort(alteredDate, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return ComparisonChain.start().compare(o1.get("PremType"), o2.get("PremType"))
						.compare(o1.get("FeeType"), o2.get("FeeType")).result();
			}
		});
		return alteredDate;
	}
	public void verifyPremiumFeesDetails(int transactionid,List<Map<String, String>> binderData) {
		List<Map<String, Object>> premiumData = getPremiumDetails(transactionid);
		List<Map<String, String>> premiumDBData= getDBData(premiumData);
		List<Map<String, String>> premiumBinderData= getBinderData(binderData);
		if (premiumBinderData.size() > 0) {
			for (int i = 0; i < premiumDBData.size(); i++) {
				Assertions.addInfo(
						"<span class='group'> " + premiumDBData.get(i).get("PremiumType").toString() + "</span>",
						"<span class='group'> GROUPING</span>");

				Assertions.verify(premiumDBData.get(i).get("PremiumType"),
						premiumBinderData.get(i).get("PremType"),
						"Premium Type :" + premiumDBData.get(i).get("PremiumType"),
						"Premium Type :" + premiumBinderData.get(i).get("PremType"), false, false);

				Assertions.verify(premiumDBData.get(i).get("FeeType"),
						premiumBinderData.get(i).get("FeeType"),
						"Fee Type :" + premiumDBData.get(i).get("FeeType"),
						"Fee Type :" + premiumBinderData.get(i).get("FeeType"), false, false);

				Assertions.verify(premiumDBData.get(i).get("FeeValue"),
						premiumBinderData.get(i).get("FeeValue"),
						"Fee Value :" + premiumDBData.get(i).get("FeeValue"),
						"Fee Value :" + premiumBinderData.get(i).get("FeeValue"), false, false);
			}
		}

}
}
