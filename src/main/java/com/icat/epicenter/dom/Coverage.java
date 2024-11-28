/** Program Description: Methods and queries built up for Coverage table
 *  Author			   : SMNetserv
 *  Date of Creation   : 14/02/2018
**/

package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class Coverage {
	public String tableName;
	public String coverageId;
	public String coverageDescription;
	public DBFrameworkConnection connection;
	public QueryBuilder build;

	public Coverage(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("Coverage");
		tableName = properties.getProperty("db_TableName");
		coverageId = properties.getProperty("db_CoverageId");
		coverageDescription = properties.getProperty("db_CoverageDescription");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
	}

	public List<Map<String, Object>> getCoverage(List<Integer> covID) {
		List<String> outFields = new ArrayList<>();
		outFields.add(coverageId);
		outFields.add(coverageDescription);
		build.outFields(outFields);
		String ccCodes = "";
		for (int i = 0; i < covID.size() - 1; i++) {
			ccCodes += covID.get(i) + ",";
		}
		ccCodes += covID.get(covID.size() - 1);
		build.whereBy(coverageId + " in (" + ccCodes + ")");
		return build.execute(60);
	}

	public String getCoverageDesc(Integer covID) {
		return build.fetch(coverageDescription, null, coverageId + "=" + covID).toString();
	}
}
