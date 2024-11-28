/** Program Description: Methods and queries built up for BuildingAdditionalInterest table
 *  Author			   : SMNetserv
 *  Date of Creation   : 10/02/2018
**/

package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class BuildingAdditionalInterest {
	public String policyAdditionalInterestId;
	public String buildingAdditionalInterestTable;
	public String policyBuildingId;
	DBFrameworkConnection connection;
	QueryBuilder build;

	public BuildingAdditionalInterest(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("BuildingAdditionalInterest");
		policyAdditionalInterestId = properties.getProperty("db_PolicyAdditionalInterestId");
		buildingAdditionalInterestTable = properties.getProperty("db_BuildingAdditionalInterestTable");
		policyBuildingId = properties.getProperty("db_PolicyBuildingId");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(buildingAdditionalInterestTable);
	}

	public List<Integer> getPolicyAddtlInterstId(int policyBldgId) {
		List<String> outFields = new ArrayList<>();
		outFields.add(policyAdditionalInterestId);
		build.outFields(outFields);
		build.whereBy(policyBuildingId + " = " + policyBldgId);
		List<Map<String, Object>> aiData = build.execute(60);
		List<Integer> policyAIId = new ArrayList<>();
		for (int i = 0; i < aiData.size(); i++) {
			policyAIId.add((Integer) aiData.get(i).get("policyadditionalinterestid"));
		}

		return policyAIId;
	}
}
