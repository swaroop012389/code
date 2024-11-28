/** Program Description: Methods and queries built up for Deductible table
 *  Author			   : SMNetserv
 *  Date of Creation   : 11/02/2018
**/

package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class Deductible {
	public String tableName;
	public String deductibleId;
	public String deductibleDescription;
	public DBFrameworkConnection connection;
	public QueryBuilder build;

	public Deductible(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("Deductible");
		tableName = properties.getProperty("db_TableName");
		deductibleId = properties.getProperty("db_DeductibleId");
		deductibleDescription = properties.getProperty("db_DeductibleDescription");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
	}

	public List<Map<String, Object>> getDeductible(List<Integer> dedID) {
		List<String> outFields = new ArrayList<>();
		outFields.add(deductibleId);
		outFields.add(deductibleDescription);
		build.outFields(outFields);
		String ccCodes = "";
		for (int i = 0; i < dedID.size() - 1; i++) {
			ccCodes += dedID.get(i) + ",";
		}
		ccCodes += dedID.get(dedID.size() - 1);
		build.whereBy(deductibleId + " in (" + ccCodes + ")");
		return build.execute(60);
	}

	public String getDeductibleDesc(Integer dedID) {
		return build.fetch(deductibleDescription, null, deductibleId + "=" + dedID).toString();
	}
}
