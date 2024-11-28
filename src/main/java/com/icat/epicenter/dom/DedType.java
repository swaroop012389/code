/** Program Description: Methods and queries built up for DedType table
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

public class DedType {
	public String tableName;
	public String deductibleTypeId;
	public String deductibleTypeDescription;
	public DBFrameworkConnection connection;
	public QueryBuilder build;

	public DedType(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("DeductibleType");
		tableName = properties.getProperty("db_TableName");
		deductibleTypeId = properties.getProperty("db_DeductibleTypeId");
		deductibleTypeDescription = properties.getProperty("db_DeductibleTypeDescription");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
	}

	public List<Map<String, Object>> getDedTypes(List<Integer> DedId) {
		List<String> outFields = new ArrayList<>();
		outFields.add(deductibleTypeId);
		outFields.add(deductibleTypeDescription);
		build.outFields(outFields);
		String ccCodes = "";
		for (int i = 0; i < DedId.size() - 1; i++) {
			ccCodes += DedId.get(i) + ",";
		}
		ccCodes += DedId.get(DedId.size() - 1);
		build.whereBy(deductibleTypeId + " in (" + ccCodes + ")");
		return build.execute(60);
	}

	public String getDeductibleTypeDsc(Integer dedTypeID) {
		return build.fetch(deductibleTypeDescription, null, deductibleTypeId + "=" + dedTypeID).toString();
	}
}
