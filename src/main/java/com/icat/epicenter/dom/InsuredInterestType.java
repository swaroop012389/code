package com.icat.epicenter.dom;

import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class InsuredInterestType {
	public String tableName;
	public String insuredInterestTypeId;
	public String insuredInterestTypeDescription;

	public Map<String, Object> results;
	DBFrameworkConnection connection;
	QueryBuilder build;

	public InsuredInterestType(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("InsuredInterestType");
		tableName = properties.getProperty("db_TableName");
		insuredInterestTypeId = properties.getProperty("db_InsuredInterestTypeId");
		insuredInterestTypeDescription = properties.getProperty("db_InsuredInterestTypeDescription");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
	}

	public short getInsuredInterestID(String insuredInterestTypeDesc) {
		return (short) build.fetch(insuredInterestTypeId, tableName, insuredInterestTypeDescription + " = " + "'"+ insuredInterestTypeDesc + "'");
	}
}
