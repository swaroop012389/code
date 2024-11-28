/** Program Description: Methods and queries built up for CovPeril table
 *  Author			   : SMNetserv
 *  Date of Creation   : 15/02/2018
**/

package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class CovPeril {
	public String tableName;
	public String coveragePerilId;
	public String coveragePerilDescription;
	public DBFrameworkConnection connection;
	public QueryBuilder build;

	public CovPeril(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("CoveragePeril");
		tableName = properties.getProperty("db_TableName");
		coveragePerilId = properties.getProperty("db_CoveragePerilId");
		coveragePerilDescription = properties.getProperty("db_CoveragePerilDescription");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
	}

	public List<Map<String, Object>> getPerils(List<Integer> covPerilID) {
		List<String> outFields = new ArrayList<>();
		outFields.add(coveragePerilId);
		outFields.add(coveragePerilDescription);
		build.outFields(outFields);
		String ccCodes = "";
		for (int i = 0; i < covPerilID.size() - 1; i++) {
			ccCodes += covPerilID.get(i) + ",";
		}
		ccCodes += covPerilID.get(covPerilID.size() - 1);
		build.whereBy(coveragePerilId + " in (" + ccCodes + ")");
		return build.execute(60);
	}

	public String getPerilDesc(Integer covPerilID) {
		return build.fetch(coveragePerilDescription, null, coveragePerilId + " = " + covPerilID).toString();
	}
}
