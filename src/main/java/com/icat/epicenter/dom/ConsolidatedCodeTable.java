/** Program Description: Methods and queries built up for ConsolidatedCodeTable table
 *  Author			   : SMNetserv
 *  Date of Creation   : 14/02/2018
**/

package com.icat.epicenter.dom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;

public class ConsolidatedCodeTable {
	private static final String RENEWALTESTDATAFILEFOLDER = "./TestDataArtifacts/TestData/PNB_DataTest/RenewalTestDataFile/";
	private static final String RENEWALTESTDATASHEETNAME = "TestData";

	public String consolidatedTable;
	public String consolidatedCode;
	public String description1;
	public String codeTypeId;
	public String description3;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public List<Map<String, Object>> tenancy;
	private static List<Integer> testStatus;

	public ConsolidatedCodeTable(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("ConsolidatedCode");
		consolidatedTable = properties.getProperty("db_TableNameConsolidated");
		description1 = properties.getProperty("db_Desc1");
		consolidatedCode = properties.getProperty("db_ConsolidatedCode");
		codeTypeId = properties.getProperty("db_CodeTypeId");
		description3 = properties.getProperty("db_Description3");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(consolidatedTable);
	}

	public String getAIType(List<String> typeid, String type) {
		String typeIDStr = "";
		for (int i = 0; i < typeid.size() - 1; i++) {
			typeIDStr += typeid.get(i) + ",";
		}
		typeIDStr += typeid.get(typeid.size() - 1);
		return (String) build.fetch(description1, consolidatedTable,
				description1 + " = '" + type + "' and " + consolidatedCode + " in " + "(" + typeIDStr + ")");
	}

	public BigDecimal getAITypeid(List<String> typeid, String type) {
		String typeIDStr = "";
		for (int i = 0; i < typeid.size() - 1; i++) {
			typeIDStr += typeid.get(i) + ",";
		}
		typeIDStr += typeid.get(typeid.size() - 1);
		return (BigDecimal) build.fetch(consolidatedCode, consolidatedTable,
				description1 + " = '" + type + "' and " + consolidatedCode + " in " + "(" + typeIDStr + ")");
	}

	public String getBldgTenancy(String tenancyid, String tenancy, int id) {
		String buildingTenancy = (String) build.fetch(description1, consolidatedTable,
				codeTypeId + " = " + id + " and " + consolidatedCode + " in (" + tenancyid + ")");
		testStatus=Assertions.verify(buildingTenancy, tenancy, "Building Tenancy : " + buildingTenancy,
				"Building Tenancy : " + tenancy, false, false);
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public String verifyBldgOccupancy(int factoryOccID, String Occupancy) {
		String buildingOccupanyDesc1 = (String) build.fetch(description1, consolidatedTable,
				consolidatedCode + " = " + factoryOccID);
		String buildingOccupancyDesc3 = (String) build.fetch(description3, consolidatedTable,
				consolidatedCode + " = " + factoryOccID);
		if (buildingOccupanyDesc1.equals(buildingOccupancyDesc3)) {
			testStatus=Assertions.verify(buildingOccupancyDesc3, Occupancy, "Building Occupancy : " + buildingOccupancyDesc3,
					"Building Occupancy : " + Occupancy, false, false);
		} else {
			testStatus=Assertions.verify(buildingOccupancyDesc3 + " - " + buildingOccupanyDesc1, Occupancy,
					"Building Occupancy : " + buildingOccupancyDesc3 + " - " + buildingOccupanyDesc1,
					"Building Occupancy : " + Occupancy, false, false);
		}
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public String getDwellingType(int dwellingUseId) {
		return (String) build.fetch(description1, consolidatedTable, consolidatedCode + " = " + dwellingUseId);
	}

	public String verifyDwellingType(int dwellingid, String dwellingtype, List<Map<String, String>> dwellingData,
			String tcid, List<Map<String, String>> testdatasetupsheet) {
		String dwellingType = getDwellingType(dwellingid);
		if (tcid.equalsIgnoreCase("TC_031") && testdatasetupsheet.get(2).get("TC_031").equalsIgnoreCase("Yes")) {
			SheetMatchedAccessManager testDataSheet = new SheetMatchedAccessManager(
					RENEWALTESTDATAFILEFOLDER + testdatasetupsheet.get(3).get("TC_031"),
					RENEWALTESTDATASHEETNAME);
			List<Map<String, String>> renewaltestData = testDataSheet.readExcelColumnWise();
			testStatus=Assertions.verify(dwellingType, renewaltestData.get(0).get("L1D1-DwellingType"),
					"Dwelling Type : " + dwellingType,
					"Dwelling Type : " + renewaltestData.get(0).get("L1D1-DwellingType"), false, false);
		} else {
			if (dwellingType.equalsIgnoreCase(dwellingtype))
				testStatus=Assertions.verify(dwellingType, dwellingtype, "Dwelling Type : " + dwellingType,
						"Dwelling Type : " + dwellingtype, false, false);
			else {
				for (Map<String, String> element : dwellingData) {
					if (dwellingtype.matches(element.get("TestData"))
							&& element.get("FieldName").equals("DwellingType"))
						testStatus=Assertions.verify(dwellingType, element.get("DBData"),
								"Dwelling Type : " + dwellingType, "Dwelling Type : " + dwellingtype, false, false);
				}
			}
		}
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public void verifyDwellingTypePNB(int dwellingid, String dwellingtype, List<Map<String, String>> dwellingData,
			String tcid, List<Map<String, String>> testdatasetupsheet, boolean dwellType, int p,
			int transactionNumber) {
		String dwellingType = getDwellingType(dwellingid);
		if (tcid.equalsIgnoreCase("TC_031") && testdatasetupsheet.get(2).get("TC_031").equalsIgnoreCase("Yes")) {
			SheetMatchedAccessManager testDataSheet = new SheetMatchedAccessManager(
					RENEWALTESTDATAFILEFOLDER + testdatasetupsheet.get(3).get("TC_031"),
					RENEWALTESTDATASHEETNAME);
			List<Map<String, String>> renewaltestData = testDataSheet.readExcelColumnWise();
			Assertions.verify(dwellingType, renewaltestData.get(0).get("L1D1-DwellingType"),
					"Dwelling Type : " + dwellingType,
					"Dwelling Type : " + renewaltestData.get(0).get("L1D1-DwellingType"), false, false);
		} else {
			if (dwellingType.equalsIgnoreCase(dwellingtype)) {
				if (p == transactionNumber)
					Assertions.verify(dwellingType, dwellingtype, "Dwelling Type : " + dwellingType,
							"Dwelling Type : " + dwellingtype, false, false);
				else
					Assertions.addInfo("Dwelling Type : " + dwellingType, "Dwelling Type : " + dwellingtype);
			} else {
				for (Map<String, String> element : dwellingData) {
					if (dwellingtype.matches(element.get("TestData"))
							&& element.get("FieldName").equals("DwellingType")) {
						if (p == transactionNumber)
							Assertions.verify(dwellingType, element.get("DBData"),
									"Dwelling Type : " + dwellingType, "Dwelling Type : " + dwellingtype, false, false);
						else
							Assertions.addInfo("Dwelling Type : " + dwellingType, "Dwelling Type : " + dwellingtype);
					}
				}
			}
		}
	}

	public String getfoundationType(int foundationTypeId) {
		return (String) build.fetch(description1, consolidatedTable, consolidatedCode + " = " + foundationTypeId);
	}

	public void verifyFoundationType(Map<String, String> buildingBinderData,
			List<Map<String, String>> pnbTestData, int foundationTypeid, int transactionNumber) {
		String foundationType = getfoundationType(foundationTypeid);
		boolean foundatType = false;
		for (int p = transactionNumber; p >= 0; p--) {
			if (!pnbTestData.get(p).get(buildingBinderData.get("BldgAddr2") + "-FoundationType").equals("")
					&& !foundatType) {
				if (p == transactionNumber)
					Assertions.verify(foundationType,
							pnbTestData.get(p).get(buildingBinderData.get("BldgAddr2") + "-FoundationType"),
							"Foundation Type : " + foundationType,
							"Foundation Type : "
									+ pnbTestData.get(p).get(buildingBinderData.get("BldgAddr2") + "-FoundationType"),
							false, false);
				else
					Assertions.addInfo("Foundation Type : " + foundationType, "Foundation Type : "
							+ pnbTestData.get(p).get(buildingBinderData.get("BldgAddr2") + "-FoundationType"));
				foundatType = true;
			}
		}
	}

	public String getOccupiedType(int factoryOccpancyId) {
		return (String) build.fetch(description1, consolidatedTable,
				consolidatedCode + " = " + factoryOccpancyId + " and " + codeTypeId + " = 1");
	}

	public String verifyOccupiedBy(int factoryOccpancyId, String occupiedBy, List<Map<String, String>> dwellingData) {
		String dbOccupiedBy = getOccupiedType(factoryOccpancyId);
		if (dbOccupiedBy.equals(occupiedBy))
			testStatus=Assertions.verify(dbOccupiedBy, occupiedBy, "Occupied By : " + dbOccupiedBy, "Occupied By : " + occupiedBy,
					false, false);
		else {
			for (Map<String, String> element : dwellingData) {
				if (occupiedBy.matches(element.get("TestData"))
						&& element.get("FieldName").equals("OccupiedBy"))
					testStatus=Assertions.verify(dbOccupiedBy, element.get("DBData"), "Occupied By : " + dbOccupiedBy,
							"Occupied By : " + occupiedBy, false, false);
			}
		}
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public void verifyOccupiedByforPNB(int factoryOccpancyId, String occupiedBy,
			List<Map<String, String>> dwellingData, int transactionNumber,
			boolean occupyBy, int p) {
		String dbOccupiedBy = getOccupiedType(factoryOccpancyId);
		if (dbOccupiedBy.equals(occupiedBy)) {
			if (p == transactionNumber)
				Assertions.verify(dbOccupiedBy, occupiedBy, "Occupied By : " + dbOccupiedBy,
						"Occupied By : " + occupiedBy, false, false);
			else
				Assertions.addInfo("Occupied By : " + dbOccupiedBy, "Occupied By : " + occupiedBy);
		} else {
			for (Map<String, String> element : dwellingData) {

				if (occupiedBy.matches(element.get("TestData"))
						&& element.get("FieldName").equals("OccupiedBy")) {
					if (p == transactionNumber)
						testStatus=Assertions.verify(dbOccupiedBy, element.get("DBData"),
								"Occupied By : " + dbOccupiedBy, "Occupied By : " + occupiedBy, false, false);
					else
						Assertions.addInfo("Occupied By : " + dbOccupiedBy, "Occupied By : " + occupiedBy);
				}
			}
		}
	}

	public List<Map<String, Object>> getConsolidatedCodes(List<Integer> consolidatedCodeData) {
		List<String> outFields = new ArrayList<>();
		outFields.add(consolidatedCode);
		outFields.add(codeTypeId);
		outFields.add(description1);
		build.outFields(outFields);
		String ccCodes = "";
		for (int i = 0; i < consolidatedCodeData.size() - 1; i++) {
			ccCodes += consolidatedCodeData.get(i) + ",";
		}
		ccCodes += consolidatedCodeData.get(consolidatedCodeData.size() - 1);
		build.whereBy(consolidatedCode + " in (" + ccCodes + ")");
		return build.execute(60);
	}

	public String getConsolidatedCodeDesc(Integer consolidatedCodeData) {
		List<String> outFields = new ArrayList<>();
		outFields.add(description1);
		build.outFields(outFields);
		build.whereBy(consolidatedCode + "=" + consolidatedCodeData);
		List<Map<String, Object>> desc1 = build.execute(60);
		if (desc1.isEmpty())
			return null;
		else
			return (String) desc1.get(0).get("Desc1");
	}

	public String getConsolidatedCodeDesc(String consolidatedCodeData) {
		if (consolidatedCodeData.equals("OpeningProtection")
				|| consolidatedCodeData.equals("RoofToWallAndOpeningProtection")
				|| consolidatedCodeData.equals("RoofToWall") || consolidatedCodeData.equals("NA"))
			return "Data inserted wrongly";
		else {
			List<String> outFields = new ArrayList<>();
			outFields.add(description1);
			build.outFields(outFields);
			build.whereBy(consolidatedCode + "=" + consolidatedCodeData);
			List<Map<String, Object>> desc1 = build.execute(60);
			if (desc1.isEmpty())
				return null;
			else
				return (String) desc1.get(0).get("Desc1");
		}
	}

	public String QuestionandAnswersVerification(String consolidatedcode, Map<String, String> testData,
			String dbQuestion, List<Map<String, String>> setupSheet) {
		String answer = getConsolidatedCodeDesc(consolidatedcode);
		if (testData.get("TCID").equalsIgnoreCase("TC_012")
				&& setupSheet.get(2).get(testData.get("TCID")).equalsIgnoreCase("yes")) {
			SheetMatchedAccessManager testDataSheet = new SheetMatchedAccessManager(
					RENEWALTESTDATAFILEFOLDER + setupSheet.get(3).get("TC_012"),
					RENEWALTESTDATASHEETNAME);
			List<Map<String, String>> renewalTestdata = testDataSheet.readExcelColumnWise();
			if (dbQuestion.equals("Flood Warranty Form"))
				Assertions.verify("Flood Warranty Form should not be transferred",
						"Flood Warranty Form should be transferred", "Flood Warranty Form", "", false, false);
			if (dbQuestion.equals("BASEMENT"))
				Assertions.verify(answer == null ? "" : answer, renewalTestdata.get(0).get("L1D1-Basement"),
						dbQuestion + " - " + answer,
						" L1D1-Basement" + " - " + renewalTestdata.get(0).get("L1D1-Basement"), false, false);
			if (dbQuestion.equals("Prior Flood Loss"))
				Assertions.verify(answer == null ? "" : answer, renewalTestdata.get(0).get("L1D1-PriorFloodLoss"),
						dbQuestion + " - " + answer,
						" L1D1-PriorFloodLoss" + " - " + renewalTestdata.get(0).get("L1D1-PriorFloodLoss"), false,
						false);
			if (dbQuestion.equals("Elevation Certificate"))
				Assertions.verify(answer == null ? "" : answer, renewalTestdata.get(0).get("L1D1-ElevationCertificate"),
						dbQuestion + " - " + answer,
						" L1D1-ElevationCertificate" + " - " + renewalTestdata.get(0).get("L1D1-ElevationCertificate"),
						false, false);
			if (dbQuestion.equals("Original Construction Year")) {
				testStatus=Assertions.verify(consolidatedcode == null ? "" : consolidatedcode,
						renewalTestdata.get(0).get("L1D1-YearofConstruction"), dbQuestion + " - " + consolidatedcode,
						"L1D1-YearofConstruction" + " - " + renewalTestdata.get(0).get("L1D1-YearofConstruction"),
						false, false);
			}
			if (dbQuestion.equals("55 and Retired"))
				Assertions.verify(answer == null ? "" : answer, testData.get("Discount55Years"),
						dbQuestion + " - " + answer, "Discount55Years" + " - " + testData.get("Discount55Years"), false,
						false);
			if (dbQuestion.equals("Base Flood Elevation"))
				Assertions.verify(consolidatedcode == null ? "" : consolidatedcode.replaceAll("\\.0*", ""),
						renewalTestdata.get(0).get("L1D1-BaseFloodElevation"),
						dbQuestion + " - " + consolidatedcode.replaceAll("\\.0*", ""),
						"L1D1-BaseFloodElevation" + " - " + testData.get("L1D1-BaseFloodElevation"), false, false);
			if (dbQuestion.equals("Lowest Floor Elevation"))
				Assertions.verify(consolidatedcode == null ? "" : consolidatedcode.replaceAll("\\.0*", ""),
						renewalTestdata.get(0).get("L1D1-LowestfloorElevation"),
						dbQuestion + " - " + consolidatedcode.replaceAll("\\.0*", ""),
						"L1D1-LowestfloorElevation" + " - " + testData.get("L1D1-LowestfloorElevation"), false, false);
		} else {
			if (dbQuestion.equals("Flood Warranty Form"))
				Assertions.verify("Flood Warranty Form should not be transferred",
						"Flood Warranty Form should be transferred", "Flood Warranty Form", "", false, false);
			if (dbQuestion.equals("BASEMENT"))
				Assertions.verify(answer == null ? "" : answer, testData.get("L1D1-Basement"),
						dbQuestion + " - " + answer, " L1D1-Basement" + " - " + testData.get("L1D1-Basement"), false,
						false);
			if (dbQuestion.equals("Prior Flood Loss"))
				Assertions.verify(answer == null ? "" : answer, testData.get("L1D1-PriorFloodLoss"),
						dbQuestion + " - " + answer,
						" L1D1-PriorFloodLoss" + " - " + testData.get("L1D1-PriorFloodLoss"), false, false);
			if (dbQuestion.equals("Elevation Certificate"))
				Assertions.verify(answer == null ? "" : answer, testData.get("L1D1-ElevationCertificate"),
						dbQuestion + " - " + answer,
						" L1D1-ElevationCertificate" + " - " + testData.get("L1D1-ElevationCertificate"), false, false);
			if (dbQuestion.equals("Original Construction Year")) {
				Assertions.verify(consolidatedcode == null ? "" : consolidatedcode,
						testData.get("L1D1-YearofConstruction"), dbQuestion + " - " + consolidatedcode,
						"L1D1-YearofConstruction" + " - " + testData.get("L1D1-YearofConstruction"), false, false);
			}
			if (dbQuestion.equals("55 and Retired"))
				testStatus=Assertions.verify(answer == null ? "" : answer, testData.get("Discount55Years"),
						dbQuestion + " - " + answer, "Discount55Years" + " - " + testData.get("Discount55Years"), false,
						false);
			if (dbQuestion.equals("Base Flood Elevation"))
				Assertions.verify(consolidatedcode == null ? "" : consolidatedcode.replaceAll("\\.0*", ""),
						testData.get("L1D1-BaseFloodElevation"),
						dbQuestion + " - " + consolidatedcode.replaceAll("\\.0*", ""),
						"L1D1-BaseFloodElevation" + " - " + testData.get("L1D1-BaseFloodElevation"), false, false);
			if (dbQuestion.equals("Lowest Floor Elevation"))
				Assertions.verify(consolidatedcode == null ? "" : consolidatedcode.replaceAll("\\.0*", ""),
						testData.get("L1D1-LowestfloorElevation"),
						dbQuestion + " - " + consolidatedcode.replaceAll("\\.0*", ""),
						"L1D1-LowestfloorElevation" + " - " + testData.get("L1D1-LowestfloorElevation"), false, false);
		}
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public void verifyReverseEndorsement(int transactionreasonid) {
		String desc1ReverseEndo = (String) build.fetch(description1, consolidatedTable,
				consolidatedCode + " = " + transactionreasonid + " and " + codeTypeId + " = 18");
		Assertions.passTest("Reversed Previous Endorsement : " + desc1ReverseEndo, "");
	}
}
