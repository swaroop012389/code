package com.icat.epicenter.dom;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class PolicyStateFees {
	public String tableName;
	public String policyNumber;
	public String transactionId;
	public String feeTypeId;
	public String feeValue;
	public String policyStateFeeId;
	public DBFrameworkConnection connection;
	public QueryBuilder build;

	public PolicyStateFees(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("PolicyStateFees");
		tableName = properties.getProperty("db_TableName");
		policyNumber = properties.getProperty("db_PolicyNumber");
		transactionId = properties.getProperty("db_TransactionId");
		feeTypeId = properties.getProperty("db_FeeTypeId");
		feeValue = properties.getProperty("db_FeeValue");
		policyStateFeeId = properties.getProperty("db_PolicyStateFeeId");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
	}
}
