/** Program Description: Methods and queries built up for building table
 *  Author			   : SMNetserv
 *  Date of Creation   : 12/02/2018
 **/

package com.icat.epicenter.dom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
import com.icat.epicenter.utils.ExcelReportUtil;

public class Building {
	public String buildingTable;
	public String policyBuildingId;
	public String transactionId;
	public String buildingId;
	public String hvn;
	public String product;
	public String address1;
	public String address2;
	public String city;
	public String state;
	public String zip5;
	public String zip4;
	public String county;
	public String yearBuilt;
	public String numberOfStories;
	public String squareFeet;
	public String constructionClass;
	public String constructionClassId;
	public String parkingId;
	public String parking;
	public String factorOccupancyId;
	public String natureOfBusiness;
	public String inspectionContact;
	public String inspectionPhone;
	public String createDateTime;
	public String deleteDateTime;
	public String deleteTransactionId;
	public String bldCnt;
	public String buildingShapeId;
	public String setbacksId;
	public String tenancy;
	public String windResistive;
	public String roofYearBuilt;
	public String roofCoveringMaterial;

	/* RESIDENTIAL PARAMETERS
        public String distanceToCoast;
        public String policyLocationID;
        public String bldgWindResistive;

        public String roofWallAttached;
        public String wallFoundation;
        public String openingProtection;
        public String dwellingUserId;
        public String livingSquareFeet;
        public String nonLivingSquareFeet;
        public String inspectionOrder;
        public String foundationTypeId;
        public String roofTypeId;


     */
	Map<String, Object> buildingData;
	DBFrameworkConnection connection;
	QueryBuilder build;
	private static List<Integer> testStatus;

	public Building(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("DbBuilding");
		buildingTable = properties.getProperty("db_BuildingTable");
		policyBuildingId = properties.getProperty("db_PolicyBuildingID");
		transactionId = properties.getProperty("db_TransactionID");

		buildingId = properties.getProperty("db_BuildingId");
		hvn = properties.getProperty("db_HVN");
		product = properties.getProperty("db_Product");
		address1 = properties.getProperty("db_Address1");
		address2 = properties.getProperty("db_Address2");
		city = properties.getProperty("db_City");
		state = properties.getProperty("db_State");
		zip5 = properties.getProperty("db_Zip5");
		zip4 = properties.getProperty("db_Zip4");
		county = properties.getProperty("db_County");
		yearBuilt = properties.getProperty("db_YearBuilt");
		numberOfStories = properties.getProperty("db_NumberOfStories");
		squareFeet = properties.getProperty("db_SquareFeet");
		constructionClass = properties.getProperty("db_ConstructionClass");
		constructionClassId = properties.getProperty("db_ConstructionClassId");
		parkingId = properties.getProperty("db_ParkingId");
		parking = properties.getProperty("db_Parking");
		factorOccupancyId = properties.getProperty("db_FactorOccupancyId");
		natureOfBusiness = properties.getProperty("db_NatureOfBusiness");
		inspectionContact = properties.getProperty("db_InspectionContact");
		inspectionPhone = properties.getProperty("db_InspectionPhone");
		createDateTime = properties.getProperty("db_CreateDateTime");
		deleteDateTime = properties.getProperty("db_DeleteDateTime");
		deleteTransactionId = properties.getProperty("db_DeleteTransactionId");
		bldCnt = properties.getProperty("db_BldCnt");
		buildingShapeId = properties.getProperty("db_BuildingShapeId");
		setbacksId = properties.getProperty("db_SetbacksId");
		tenancy = properties.getProperty("db_Tenancy");
		windResistive = properties.getProperty("db_WindResistive");
		roofYearBuilt = properties.getProperty("db_RoofYearBuilt");
		roofCoveringMaterial = properties.getProperty("db_RoofCoveringMaterial");

		/* RESIDENTIAL PARAMETERS
		constructionType = properties.getProperty("db_ConstructionClass");
		bldgSqFeet = properties.getProperty("db_BldgSqFeet");
		distanceToCoast = properties.getProperty("db_DistanceToCoast");
		policyLocationID = properties.getProperty("db_PolicyLocationId");
		bldgWindResistive = properties.getProperty("db_BldgWindResisitive");
		factoryOccupancyID = properties.getProperty("db_FactoryID");
		roofWallAttached = properties.getProperty("db_RoofToWall");
		wallFoundation = properties.getProperty("db_WallToFoundation");
		openingProtection = properties.getProperty("db_OpeningProtection");
		dwellingUserId = properties.getProperty("db_DwellingUseId");
		livingSquareFeet = properties.getProperty("db_LivingSquareFeet");
		nonLivingSquareFeet = properties.getProperty("db_NonLivingSquareFeet");
		inspectionOrder = properties.getProperty("db_InspectionOrder");
		foundationTypeId = properties.getProperty("db_FoundationTypeId");
		roofTypeId = properties.getProperty("db_RoofTypeId");
		*/

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(buildingTable);
	}



	public Map<String, Object> getBuildingDetails(int transactionId, int i) {
		List<String> outFields = new ArrayList<>();
		outFields.add(buildingId);
		outFields.add(hvn);
		outFields.add(product);
		outFields.add(address1);
		outFields.add(address2);
		outFields.add(city);
		outFields.add(state);
		outFields.add(zip5);
		outFields.add(zip4);
		outFields.add(county);
		outFields.add(yearBuilt);
		outFields.add(numberOfStories);
		outFields.add(squareFeet);
		outFields.add(constructionClass);
		outFields.add(constructionClassId);
		outFields.add(parkingId);
		outFields.add(parking);
		outFields.add(factorOccupancyId);
		outFields.add(natureOfBusiness);
		outFields.add(inspectionContact);
		outFields.add(inspectionPhone);
		outFields.add(createDateTime);
		outFields.add(deleteDateTime);
		outFields.add(deleteTransactionId);
		outFields.add(bldCnt);
		outFields.add(buildingShapeId);
		outFields.add(setbacksId);
		outFields.add(tenancy);
		outFields.add(windResistive);
		outFields.add(roofYearBuilt);
		outFields.add(roofCoveringMaterial);

		build.outFields(outFields);
		build.whereBy("transactionId = " + transactionId + " order by BuildingId");
		List<Map<String, Object>> buildingDetails = build.execute(60);
		return buildingData = buildingDetails.get(i);
	}

	public List<Map<String, String>> getBuildingDataExpected(String tcid) {
		List<Map<String, String>> binderBuildingData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"Building").readExcelRowWise();

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : binderBuildingData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);
			}
		}
		return TCData;

	}

	public String verifyBuildingData(List<Map<String, String>> binderData, int transactionId) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		for (int i = 0; i < binderData.size(); i++) {

			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = getBuildingDetails(transactionId, i);

			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN"), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Product").toString(), binderDataRow.get("Product"), "Product : " + aftershockRow.get("Product").toString(), "Expected : " + binderDataRow.get("Product"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Address1").toString().toLowerCase(), binderDataRow.get("Address1").toLowerCase(), "Address1 : " + aftershockRow.get("Address1").toString().toLowerCase(), "Expected : " + binderDataRow.get("Address1").toLowerCase(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Address2").toString().toLowerCase(), binderDataRow.get("Address2").toLowerCase(), "Address2 : " + aftershockRow.get("Address2").toString().toLowerCase(), "Expected : " + binderDataRow.get("Address2").toLowerCase(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("City").toString().toLowerCase(), binderDataRow.get("City").toLowerCase(), "City : " + aftershockRow.get("City").toString().toLowerCase(), "Expected : " + binderDataRow.get("City").toLowerCase(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("State").toString(), binderDataRow.get("State"), "State : " + aftershockRow.get("State").toString(), "Expected : " + binderDataRow.get("State"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Zip5").toString(), binderDataRow.get("Zip5"), "Zip5 : " + aftershockRow.get("Zip5").toString(), "Expected : " + binderDataRow.get("Zip5"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Zip4").toString().trim(), binderDataRow.get("Zip4").trim(), "Zip4 : " + aftershockRow.get("Zip4").toString().trim(), "Expected : " + binderDataRow.get("Zip4").trim(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("County").toString().toLowerCase(), binderDataRow.get("County").toLowerCase(), "County : " + aftershockRow.get("County").toString().toLowerCase(), "Expected : " + binderDataRow.get("County").toLowerCase(), false, false);
			testStatus = Assertions.verify(aftershockRow.get("YearBuilt").toString(), binderDataRow.get("YearBuilt"), "YearBuilt : " + aftershockRow.get("YearBuilt").toString(), "Expected : " + binderDataRow.get("YearBuilt"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("NumberOfStories").toString(), binderDataRow.get("NumberOfStories"), "NumberOfStories : " + aftershockRow.get("NumberOfStories").toString(), "Expected : " + binderDataRow.get("NumberOfStories"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("SquareFeet").toString(), binderDataRow.get("SquareFeet"), "SquareFeet : " + aftershockRow.get("SquareFeet").toString(), "Expected : " + binderDataRow.get("SquareFeet"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("ConstructionClass").toString(), binderDataRow.get("ConstructionClass"), "ConstructionClass : " + aftershockRow.get("ConstructionClass").toString(), "Expected : " + binderDataRow.get("ConstructionClass"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("ConstructionClassId").toString(), binderDataRow.get("ConstructionClassId"), "ConstructionClassId : " + aftershockRow.get("ConstructionClassId").toString(), "Expected : " + binderDataRow.get("ConstructionClassId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("ParkingId").toString(), binderDataRow.get("ParkingId"), "ParkingId : " + aftershockRow.get("ParkingId").toString(), "Expected : " + binderDataRow.get("ParkingId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Parking").toString(), binderDataRow.get("Parking"), "Parking : " + aftershockRow.get("Parking").toString(), "Expected : " + binderDataRow.get("Parking"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("FactorOccupancyId").toString(), binderDataRow.get("FactorOccupancyId"), "FactorOccupancyId : " + aftershockRow.get("FactorOccupancyId").toString(), "Expected : " + binderDataRow.get("FactorOccupancyId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("NatureOfBusiness").toString(), binderDataRow.get("NatureOfBusiness"), "NatureOfBusiness : " + aftershockRow.get("NatureOfBusiness").toString(), "Expected : " + binderDataRow.get("NatureOfBusiness"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("InspectionContact").toString(), binderDataRow.get("InspectionContact"), "InspectionContact : " + aftershockRow.get("InspectionContact").toString(), "Expected : " + binderDataRow.get("InspectionContact"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("InspectionPhone").toString(), binderDataRow.get("InspectionPhone"), "InspectionPhone : " + aftershockRow.get("InspectionPhone").toString(), "Expected : " + binderDataRow.get("InspectionPhone"), false, false);

			LocalDate asCreateDateTime = LocalDate.parse(String.valueOf(aftershockRow.get("CreateDateTime")).substring(0,10));
			String epiProcessDate = ExcelReportUtil.getInstance().getLatestProcessingDateFromMaster(binderDataRow.get("TCID"));
			testStatus = Assertions.verify(asCreateDateTime.format(f), epiProcessDate, "CreateDateTime : " + asCreateDateTime.format(f), "Expected : " + epiProcessDate, false, false);

			String delDateTimeExpected = "01/01/1900";
			int delTxnIdExpected = 0;
			if (!binderDataRow.get("DeleteTransactionId").equals("0")) {
				delDateTimeExpected = ExcelReportUtil.getInstance().getLatestTransactionDateFromMaster(binderDataRow.get("TCID"));
				delTxnIdExpected = transactionId;
			}
			LocalDate asDeleteDate = LocalDate.parse(String.valueOf(aftershockRow.get("DeleteDateTime")).replace(" 00:00:00.0", ""));
			testStatus = Assertions.verify(f.format(asDeleteDate), delDateTimeExpected, "DeleteDateTime : " + f.format(asDeleteDate), "Expected : " + delDateTimeExpected, false, false);
			testStatus = Assertions.verify(Integer.valueOf(aftershockRow.get("DeleteTransactionId").toString()), delTxnIdExpected, "DeleteTransactionId : " + Integer.valueOf(aftershockRow.get("DeleteTransactionId").toString()), "Expected : " + delTxnIdExpected, false, false);
			testStatus = Assertions.verify(aftershockRow.get("BldCnt").toString(), binderDataRow.get("BldCnt"), "BldCnt : " + aftershockRow.get("BldCnt").toString(), "Expected : " + binderDataRow.get("BldCnt"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("BuildingShapeId").toString(), binderDataRow.get("BuildingShapeId"), "BuildingShape : " + aftershockRow.get("BuildingShapeId").toString(), "Expected : " + binderDataRow.get("BuildingShapeId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("SetbacksId").toString(), binderDataRow.get("SetbacksId"), "SetbacksId : " + aftershockRow.get("SetbacksId").toString(), "Expected : " + binderDataRow.get("SetbacksId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Tenancy").toString(), binderDataRow.get("Tenancy"), "Tenancy : " + aftershockRow.get("Tenancy").toString(), "Expected : " + binderDataRow.get("Tenancy"), false, false);

			String windResistiveExpected = "false";
			if (binderDataRow.get("WindResistive").toString().equals("1")) {
				windResistiveExpected = "true";
			}
			testStatus = Assertions.verify(aftershockRow.get("WindResistive").toString(), windResistiveExpected, "WindResistive : " + aftershockRow.get("WindResistive").toString(), "Expected : " + windResistiveExpected, false, false);
			testStatus = Assertions.verify(aftershockRow.get("RoofYearBuilt").toString(), binderDataRow.get("RoofYearBuilt"), "RoofYearBuilt : " + aftershockRow.get("RoofYearBuilt").toString(), "Expected : " + binderDataRow.get("RoofYearBuilt"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("RoofCoveringMaterial").toString(), binderDataRow.get("RoofCoveringMaterial"), "RoofCoveringMaterial : " + aftershockRow.get("RoofCoveringMaterial").toString(), "Expected : " + binderDataRow.get("RoofCoveringMaterial"), false, false);

		}

		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public String getBuildingAddr2(Integer policyBuildingIdNum) {
		return build.fetch(address2, null, policyBuildingId + " = " + policyBuildingIdNum).toString();
	}

	/* RESIDENTIAL METHODS

		public Map<String, Object> getBuildingDetails(int transid, String address) {
		List<String> outFields = new ArrayList<>();
		outFields.add(address1);
		outFields.add(address2);
		outFields.add(city);
		outFields.add(state);
		outFields.add(zip);
		outFields.add(constructionType);
		outFields.add(numberOfStories);
		outFields.add(yearBuilt);
		outFields.add(bldgSqFeet);
		outFields.add(inspectionContact);
		outFields.add(inspectionPhone);
		outFields.add(distanceToCoast);
		outFields.add(bldgWindResistive);
		outFields.add(tenancy);
		outFields.add(roofWallAttached);
		outFields.add(wallFoundation);
		outFields.add(openingProtection);
		outFields.add(natureOfBusiness);
		outFields.add(livingSquareFeet);
		outFields.add(nonLivingSquareFeet);
		build.outFields(outFields);
		build.whereBy(address2 + " = '" + address + "'" + " and " + transactionId + " = " + transid);
		List<Map<String, Object>> buildingDetails = build.execute(60);
		return buildingData = buildingDetails.get(0);
	}

	public Map<String, Object> getBuildingDetailsforPNB(int transid, String address) {
		List<String> outFields = new ArrayList<>();
		outFields.add(address1);
		outFields.add(address2);
		outFields.add(city);
		outFields.add(state);
		outFields.add(zip);
		outFields.add(constructionType);
		outFields.add(numberOfStories);
		outFields.add(yearBuilt);
		outFields.add(bldgSqFeet);
		outFields.add(inspectionContact);
		outFields.add(inspectionPhone);
		outFields.add(distanceToCoast);
		outFields.add(bldgWindResistive);
		outFields.add(tenancy);
		outFields.add(roofWallAttached);
		outFields.add(wallFoundation);
		outFields.add(openingProtection);
		outFields.add(natureOfBusiness);
		outFields.add(livingSquareFeet);
		outFields.add(nonLivingSquareFeet);
		outFields.add(inspectionOrder);
		outFields.add(deleteTransactionId);
		build.outFields(outFields);
		build.whereBy(address2 + " = '" + address + "'" + " and " + transactionId + " = " + transid);
		List<Map<String, Object>> buildingDetails = build.execute(60);
		return buildingData = buildingDetails.get(0);
	}


		public int getBuildinID(int transid, String address) {
		return (int) build.fetch(policyBuildingId, buildingTable,
				address2 + " = '" + address + "'" + " and " + transactionId + " = " + transid);

	}

	public Integer getBuildinIDPNB(int transid, String address) {
		List<String> outFields = new ArrayList<>();
		outFields.add(policyBuildingId);
		build.outFields(outFields);
		build.whereBy(address2 + " = '" + address + "'" + " and " + transactionId + " = " + transid);
		List<Map<String, Object>> buildingID = build.execute(60);
		if (buildingID.isEmpty())
			return null;
		else
			return (Integer) buildingID.get(0).get(policyBuildingId);
	}

	public String getBuildingTenancyId(int transid, String address) {
		return (String) build.fetch(tenancy, buildingTable,
				address2 + " = '" + address + "'" + " and " + transactionId + " = " + transid);
	}

	public int getFactoryOccupancyId(int transid, String address) {
		return (int) build.fetch(factoryOccupancyID, buildingTable,
				address2 + " = '" + address + "'" + " and " + transactionId + " = " + transid);
	}

	public int getDwellingUseId(int transid, String address) {
		return (int) build.fetch(dwellingUserId, buildingTable,
				address2 + " = '" + address + "'" + " and " + transactionId + " = " + transid);
	}

	public int getfoundationTypeId(int transid, String address) {
		return ((BigDecimal) build.fetch(foundationTypeId, buildingTable,
				address2 + " = '" + address + "'" + " and " + transactionId + " = " + transid)).intValueExact();
	}

	public int getRoofTypeId(String addres2, int transid) {
		return (int) build.fetch(roofTypeId, buildingTable,
				address2 + " = '" + addres2 + "'" + " and " + transactionId + " = " + transid);
	}

	public String getNatureofBusiness(String addres2, int transid) {
		return (String) build.fetch(natureOfBusiness, buildingTable,
				address2 + " = '" + addres2 + "'" + " and " + transactionId + " = " + transid);
	}

	public Object getInspectionOrder(int transid, String addres2) {
		List<String> outFields = new ArrayList<>();
		outFields.add(inspectionOrder);
		build.outFields(outFields);
		build.whereBy(transactionId + " = " + transid + " and " + address2 + " = '" + addres2 + "'");
		List<Map<String, Object>> inspectionOrders = build.execute(60);
		if (inspectionOrders.isEmpty()) {
			return null;
		} else
			return inspectionOrders.get(0).get("InspectionOrder");
	}

	public int deleteTransactionidForDwellingOrder(int transid, String address2) {
		return (int) build.fetch(deleteTransactionId, null, this.deleteTransactionId + "=" + transid);
	}

	public String verifyDwellingData(Map<String, String> testData, Map<String, String> binderData, int transid,
			List<Map<String, String>> dwellingData) {
		buildingData = getBuildingDetails(transid, binderData.get("BldgAddr2"));
		BigDecimal distanceToCoast = new BigDecimal((buildingData.get("DistanceToCoast").toString()));
		distanceToCoast = distanceToCoast.setScale(10, RoundingMode.HALF_UP);
		Assertions.verify(buildingData.get("Address1").toString().trim(),
				testData.get(binderData.get("BldgAddr2") + "-DwellingAddress").trim(),
				"Dwelling address1 : " + buildingData.get("Address1"),
				"Dwelling address1 : " + testData.get(binderData.get("BldgAddr2") + "-DwellingAddress"), false, false);
		Assertions.verify(buildingData.get("City").toString().trim(),
				testData.get(binderData.get("BldgAddr2") + "-DwellingCity").trim(),
				"City : " + buildingData.get("City"),
				"City : " + testData.get(binderData.get("BldgAddr2") + "-DwellingCity"), false, false);
		Assertions.verify(buildingData.get("State").toString().trim(), binderData.get("State").trim(),
				"State : " + buildingData.get("State"), "State : " + binderData.get("State"), false, false);
		Assertions.verify(buildingData.get("Zip5").toString().trim(),
				testData.get(binderData.get("BldgAddr2") + "-DwellingZIP").trim(), "Zip : " + buildingData.get("Zip5"),
				"Zip : " + testData.get(binderData.get("BldgAddr2") + "-DwellingZIP"), false, false);
		Assertions.verify(buildingData.get("YearBuilt").toString().trim(),
				testData.get(binderData.get("BldgAddr2") + "-DwellingYearBuilt").trim(),
				"Dwelling Year Built : " + buildingData.get("YearBuilt"),
				"Dwelling Year Built : " + testData.get(binderData.get("BldgAddr2") + "-DwellingYearBuilt"), false,
				false);
		Assertions.verify(buildingData.get("NumberOfStories").toString().trim(),
				testData.get(binderData.get("BldgAddr2") + "-DwellingFloors").trim(),
				"Dwelling Stories : " + buildingData.get("NumberOfStories"),
				"Dwelling Stories : " + testData.get(binderData.get("BldgAddr2") + "-DwellingFloors"), false, false);
		Assertions.verify(buildingData.get("LivingSquareFeet").toString(),
				testData.get(binderData.get("BldgAddr2") + "-DwellingSqFoot"),
				"Dwelling Living Sq Feet : " + buildingData.get("LivingSquareFeet"),
				"Dwelling Living Sq Feet : " + testData.get(binderData.get("BldgAddr2") + "-DwellingSqFoot"), false,
				false);
		Assertions.verify(buildingData.get("NonLivingSquareFeet").toString(),
				testData.get(binderData.get("BldgAddr2") + "-DwellingNonLivingSqFoot"),
				"Dwelling Non Sq Feet : " + buildingData.get("NonLivingSquareFeet"),
				"Dwelling Non Sq Feet : " + testData.get(binderData.get("BldgAddr2") + "-DwellingNonLivingSqFoot"),
				false, false);
		if (buildingData.get("ConstructionClass")
				.equals(testData.get(binderData.get("BldgAddr2") + "-DwellingConstType"))) {
			Assertions.verify(buildingData.get("ConstructionClass"),
					testData.get(binderData.get("BldgAddr2") + "-DwellingConstType"),
					"Dwelling Construction Type : " + buildingData.get("ConstructionClass"),
					"Dwelling Construction Type : " + testData.get(binderData.get("BldgAddr2") + "-DwellingConstType"),
					false, false);
		} else {
			for (int i = 0; i < dwellingData.size(); i++) {
				if (testData.get(binderData.get("BldgAddr2") + "-DwellingConstType")
						.matches(dwellingData.get(i).get("TestData"))
						&& dwellingData.get(i).get("FieldName").equals("ConstructionType"))
					Assertions
							.verify(buildingData.get("ConstructionClass"), dwellingData.get(i).get("DBData"),
									"Dwelling Construction Type : " + buildingData.get("ConstructionClass"),
									"Dwelling Construction Type : "
											+ testData.get(binderData.get("BldgAddr2") + "-DwellingConstType"),
									false, false);
			}
		}
		if (!testData.get(binderData.get("BldgAddr2") + "-DwellingRoofWallAttach").equals("")) {
			if (buildingData.get("RooftoWall").equals("N"))
				Assertions.passTest("Dwelling Roof to Wall Attachment : " + "No",
						"Dwelling Roof to Wall Attachment : " + "No");

			if (buildingData.get("RooftoWall").equals("Y"))
				Assertions.passTest("Dwelling Roof to Wall Attachment : " + "Yes",
						"Dwelling Roof to Wall Attachment : " + "Yes");

			if (buildingData.get("WallToFoundation").equals("SUP")) {
				Assertions.passTest("Dwelling Wall Foundation Attachment : " + "Superior",
						"Dwelling Wall Foundation Attachment : " + "Superior");
			} else if (buildingData.get("WallToFoundation").equals("STD")) {
				Assertions.passTest("Dwelling Wall Foundation Attachment : " + "Standard",
						"Dwelling Wall Foundation Attachment : " + "Standard");
			} else {
				Assertions.verify(buildingData.get("WallToFoundation"),
						testData.get(binderData.get("BldgAddr2") + "-DwellingWallFoundationAttach"),
						"Dwelling Wall Foundation Attachment : " + buildingData.get("WallToFoundation"),
						"Dwelling Wall Foundation Attachment : "
								+ testData.get(binderData.get("BldgAddr2") + "-DwellingWallFoundationAttach"),
						false, false);
			}
			if (buildingData.get("OpeningProtection").equals("SUP")) {
				Assertions.passTest("Dwelling Opening Protection : " + "Superior",
						"Dwelling Opening Protection : " + "Superior");
			} else if (buildingData.get("OpeningProtection").equals("STD")) {
				Assertions.passTest("Dwelling Opening Protection : " + "Standard",
						"Dwelling Opening Protection : " + "Standard");
			} else {
				Assertions.verify(buildingData.get("OpeningProtection"),
						testData.get(binderData.get("BldgAddr2") + "-DwellingOpeningProtection"),
						"Dwelling Opening Protection : " + buildingData.get("OpeningProtection"),
						"Dwelling Opening Protection : "
								+ testData.get(binderData.get("BldgAddr2") + "-DwellingOpeningProtection"),
						false, false);
			}
		}
		Assertions.verify(buildingData.get("InspectionContact"), testData.get("InspectionContact"),
				"Inspection Contact : " + buildingData.get("InspectionContact"),
				"Inspection Contact : " + testData.get("InspectionContact"), false, false);
		Assertions.verify(
				buildingData.get("InspectionPhone").toString().substring(0, 3) + "."
						+ buildingData.get("InspectionPhone").toString().substring(4, 7) + "."
						+ buildingData.get("InspectionPhone").toString().substring(8),
				testData.get("InspectionAreaCode") + "." + testData.get("InspectionPrefix") + "."
						+ testData.get("InspectionNumber"),
				"Inspection Area Code : " + buildingData.get("InspectionPhone").toString().substring(0, 3) + "."
						+ buildingData.get("InspectionPhone").toString().substring(4, 7) + "."
						+ buildingData.get("InspectionPhone").toString().substring(8),
				"Inspection Area Code : " + testData.get("InspectionAreaCode") + "." + testData.get("InspectionPrefix")
						+ "." + testData.get("InspectionNumber"),
				false, false);
		testStatus=Assertions.verify(distanceToCoast.toString(), binderData.get("DistanceToCoast").trim(),
				"Distance To Coast : " + distanceToCoast, "Distance To Coast : " + binderData.get("DistanceToCoast"),
				false, false);
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public void verifyDwellingDataforPNB(List<Map<String, String>> pnbtestData, Map<String, String> binderData,
			int transid, List<Map<String, String>> dwellingData, int transactionNumber) {
		NumberFormat format = NumberFormat.getIntegerInstance(Locale.US);
		buildingData = getBuildingDetailsforPNB(transid, binderData.get("BldgAddr2"));
		boolean dwellingAddrs = false;
		boolean dwellingAddrs2 = false;
		boolean dwellingCity = false;
		boolean dwellingZip = false;
		boolean dwellingYearBuilt = false;
		boolean dwellingFloors = false;
		boolean dwellingSqFeet = false;
		boolean dwellingNonLivingSqFeet = false;
		boolean dwellingConstType = false;
		boolean dwellingRoofWallAttach = false;
		boolean dwellingInspectionContact = false;
		boolean dwellingInspectionAreaCode = false;
		for (int p = transactionNumber; p >= 0; p--) {
			if (buildingData.get("deleteTransactionid").equals(0)) {
				BigDecimal distanceToCoast = new BigDecimal((buildingData.get("DistanceToCoast").toString()));
				distanceToCoast = distanceToCoast.setScale(10, RoundingMode.HALF_UP);
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingAddress").equals("")
						&& dwellingAddrs == false) {
					if (p == transactionNumber)
						Assertions.verify(buildingData.get("Address1").toString().trim(),
								pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingAddress").trim(),
								"Dwelling address1 : " + buildingData.get("Address1"),
								"Dwelling address1 : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingAddress"),
								false, false);
					else
						Assertions.addInfo("Dwelling address1 : " + buildingData.get("Address1"), "Dwelling address1 : "
								+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingAddress"));
					dwellingAddrs = true;
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingDesc").equals("")
						&& dwellingAddrs2 == false) {
					if (p == transactionNumber)
						Assertions.verify(buildingData.get("Address2").toString().trim(),
								pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingDesc").trim(),
								"Dwelling address2 : " + buildingData.get("Address2"),
								"Dwelling address2 : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingDesc"),
								false, false);
					else
						Assertions.addInfo("Dwelling address2 : " + buildingData.get("Address2"), "Dwelling address2 : "
								+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingDesc"));
					dwellingAddrs2 = true;
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCity").equals("")
						&& dwellingCity == false) {
					if (p == transactionNumber) {
						Assertions.verify(buildingData.get("City").toString().trim(),
								pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCity").trim(),
								"City : " + buildingData.get("City"),
								"City : " + pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCity"),
								false, false);
						Assertions.verify(buildingData.get("State").toString().trim(), binderData.get("State").trim(),
								"State : " + buildingData.get("State"), "State : " + binderData.get("State"), false,
								false);
					} else {
						Assertions.addInfo("City : " + buildingData.get("City"),
								"City : " + pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCity"));
						Assertions.addInfo("State : " + buildingData.get("State"),
								"State : " + binderData.get("State"));
					}
					dwellingCity = true;
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingZIP").equals("")
						&& dwellingZip == false) {
					if (p == transactionNumber)
						Assertions.verify(buildingData.get("Zip5").toString().trim(),
								pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingZIP").trim(),
								"Zip : " + buildingData.get("Zip5"),
								"Zip : " + pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingZIP"), false,
								false);

					else
						Assertions.addInfo("Zip : " + buildingData.get("Zip5"),
								"Zip : " + pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingZIP"));
					dwellingZip = true;
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingYearBuilt").equals("")
						&& dwellingYearBuilt == false) {
					if (p == transactionNumber)
						Assertions.verify(buildingData.get("YearBuilt").toString().trim(),
								pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingYearBuilt").trim(),
								"Dwelling Year Built : " + buildingData.get("YearBuilt"),
								"Dwelling Year Built : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingYearBuilt"),
								false, false);
					else
						Assertions.addInfo("Dwelling Year Built : " + buildingData.get("YearBuilt"),
								"Dwelling Year Built : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingYearBuilt"));
					dwellingYearBuilt = true;
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingFloors").equals("")
						&& dwellingFloors == false) {
					if (p == transactionNumber)
						Assertions.verify(buildingData.get("NumberOfStories").toString().trim(),
								pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingFloors").trim(),
								"Dwelling Stories : " + buildingData.get("NumberOfStories"),
								"Dwelling Stories : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingFloors"),
								false, false);
					else
						Assertions.addInfo("Dwelling Stories : " + buildingData.get("NumberOfStories"),
								"Dwelling Stories : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingFloors"));
					dwellingFloors = true;
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingSqFoot").equals("")
						&& dwellingSqFeet == false) {
					if (p == transactionNumber)
						Assertions.verify(buildingData.get("LivingSquareFeet").toString(),
								pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingSqFoot"),
								"Dwelling Living Sq Feet : " + buildingData.get("LivingSquareFeet"),
								"Dwelling Living Sq Feet : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingSqFoot"),
								false, false);
					else
						Assertions.addInfo("Dwelling Living Sq Feet : " + buildingData.get("LivingSquareFeet"),
								"Dwelling Living Sq Feet : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingSqFoot"));
					dwellingSqFeet = true;
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingNonLivingSqFoot").equals("")
						&& dwellingNonLivingSqFeet == false) {
					if (p == transactionNumber)
						Assertions
								.verify(buildingData.get("NonLivingSquareFeet").toString(),
										pnbtestData.get(p)
												.get(binderData.get("BldgAddr2") + "-DwellingNonLivingSqFoot"),
										"Dwelling Non Sq Feet : " + buildingData.get("NonLivingSquareFeet"),
										"Dwelling Non Sq Feet : " + pnbtestData.get(p)
												.get(binderData.get("BldgAddr2") + "-DwellingNonLivingSqFoot"),
										false, false);
					else
						Assertions.addInfo("Dwelling Non Sq Feet : " + buildingData.get("NonLivingSquareFeet"),
								"Dwelling Non Sq Feet : " + pnbtestData.get(p)
										.get(binderData.get("BldgAddr2") + "-DwellingNonLivingSqFoot"));
					dwellingNonLivingSqFeet = true;
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingSqFoot").equals("")
						&& dwellingSqFeet == false
						|| !pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingNonLivingSqFoot")
								.equals("")) {
					if (p == transactionNumber)
						Assertions.verify(buildingData.get("SquareFeet").toString().trim(),
								buildingData.get("SquareFeet").toString().trim(),
								"Total Building Sq Feet : " + format.format(buildingData.get("SquareFeet")),
								"Total Building Sq Feet : " + format.format(buildingData.get("SquareFeet")), false,
								false);
					else
						Assertions.addInfo("Total Building Sq Feet : " + format.format(buildingData.get("SquareFeet")),
								"Total Building Sq Feet : " + format.format(buildingData.get("SquareFeet")));
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingConstType").equals("")
						&& dwellingConstType == false) {

					if (buildingData.get("ConstructionClass")
							.equals(pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingConstType"))) {
						Assertions.addInfo("Dwelling Construction Type : " + buildingData.get("ConstructionClass"),
								"Dwelling Construction Type : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingConstType"));
					} else {
						Assertions.addInfo("Dwelling Construction Type : " + buildingData.get("ConstructionClass"),
								"Dwelling Construction Type : "
										+ pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingConstType"));
					}
					dwellingConstType = true;
				}
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingRoofWallAttach").equals("")
						&& dwellingRoofWallAttach == false) {
					if (buildingData.get("RooftoWall").equals("N")) {
						if (p == transactionNumber)
							Assertions.passTest("Dwelling Roof to Wall Attachment : " + "No",
									"Dwelling Roof to Wall Attachment : " + "No");
						else
							Assertions.addInfo("Dwelling Roof to Wall Attachment : " + "No",
									"Dwelling Roof to Wall Attachment : " + "No");
					}
					if (buildingData.get("RooftoWall").equals("Y")) {
						if (p == transactionNumber)
							Assertions.passTest("Dwelling Roof to Wall Attachment : " + "Yes",
									"Dwelling Roof to Wall Attachment : " + "Yes");
						else
							Assertions.addInfo("Dwelling Roof to Wall Attachment : " + "Yes",
									"Dwelling Roof to Wall Attachment : " + "Yes");
					}
					if (buildingData.get("WallToFoundation").equals("SUP")) {
						if (p == transactionNumber)
							Assertions.passTest("Dwelling Wall Foundation Attachment : " + "Superior",
									"Dwelling Wall Foundation Attachment : " + "Superior");
						else
							Assertions.addInfo("Dwelling Wall Foundation Attachment : " + "Superior",
									"Dwelling Wall Foundation Attachment : " + "Superior");
					} else if (buildingData.get("WallToFoundation").equals("STD")) {
						if (p == transactionNumber)
							Assertions.passTest("Dwelling Wall Foundation Attachment : " + "Standard",
									"Dwelling Wall Foundation Attachment : " + "Standard");
						else
							Assertions.addInfo("Dwelling Wall Foundation Attachment : " + "Standard",
									"Dwelling Wall Foundation Attachment : " + "Standard");
					} else {
						if (p == transactionNumber)
							Assertions.verify(buildingData.get("WallToFoundation"),
									pnbtestData.get(p)
											.get(binderData.get("BldgAddr2") + "-DwellingWallFoundationAttach"),
									"Dwelling Wall Foundation Attachment : " + buildingData.get("WallToFoundation"),
									"Dwelling Wall Foundation Attachment : " + pnbtestData.get(p)
											.get(binderData.get("BldgAddr2") + "-DwellingWallFoundationAttach"),
									false, false);
						else
							Assertions.addInfo(
									"Dwelling Wall Foundation Attachment : " + buildingData.get("WallToFoundation"),
									"Dwelling Wall Foundation Attachment : " + pnbtestData.get(p)
											.get(binderData.get("BldgAddr2") + "-DwellingWallFoundationAttach"));
					}
					if (buildingData.get("OpeningProtection").equals("SUP")) {
						if (p == transactionNumber)
							Assertions.passTest("Dwelling Opening Protection : " + "Superior",
									"Dwelling Opening Protection : " + "Superior");
						else
							Assertions.addInfo("Dwelling Opening Protection : " + "Superior",
									"Dwelling Opening Protection : " + "Superior");
					} else if (buildingData.get("OpeningProtection").equals("STD")) {
						if (p == transactionNumber)
							Assertions.passTest("Dwelling Opening Protection : " + "Standard",
									"Dwelling Opening Protection : " + "Standard");
						else
							Assertions.addInfo("Dwelling Opening Protection : " + "Standard",
									"Dwelling Opening Protection : " + "Standard");
					} else {
						if (p == transactionNumber)
							Assertions.verify(buildingData.get("OpeningProtection"),
									pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingOpeningProtection"),
									"Dwelling Opening Protection : " + buildingData.get("OpeningProtection"),
									"Dwelling Opening Protection : " + pnbtestData.get(p)
											.get(binderData.get("BldgAddr2") + "-DwellingOpeningProtection"),
									false, false);
						else
							Assertions.addInfo("Dwelling Opening Protection : " + buildingData.get("OpeningProtection"),
									"Dwelling Opening Protection : " + pnbtestData.get(p)
											.get(binderData.get("BldgAddr2") + "-DwellingOpeningProtection"));
					}
					dwellingRoofWallAttach = true;
				}
				if (!pnbtestData.get(p).get("InspectionContact").equals("") && dwellingInspectionContact == false) {
					if (p == transactionNumber)
						Assertions.verify(buildingData.get("InspectionContact"),
								pnbtestData.get(p).get("InspectionContact"),
								"Inspection Contact : " + buildingData.get("InspectionContact"),
								"Inspection Contact : " + pnbtestData.get(p).get("InspectionContact"), false, false);
					else
						Assertions.addInfo("Inspection Contact : " + buildingData.get("InspectionContact"),
								"Inspection Contact : " + pnbtestData.get(p).get("InspectionContact"));
					dwellingInspectionContact = true;
				}
				if (!pnbtestData.get(p).get("InspectionAreaCode").equals("") && dwellingInspectionAreaCode == false) {
					if (p == transactionNumber)
						Assertions.verify(
								buildingData.get("InspectionPhone").toString().substring(0, 3) + "."
										+ buildingData.get("InspectionPhone").toString().substring(4, 7) + "."
										+ buildingData.get("InspectionPhone").toString().substring(8),
								pnbtestData.get(p).get("InspectionAreaCode") + "."
										+ pnbtestData.get(p).get("InspectionPrefix") + "."
										+ pnbtestData.get(p).get("InspectionNumber"),
								"Inspection Area Code : "
										+ buildingData.get("InspectionPhone").toString().substring(0, 3) + "."
										+ buildingData.get("InspectionPhone").toString().substring(4, 7) + "."
										+ buildingData.get("InspectionPhone").toString().substring(8),
								"Inspection Area Code : " + pnbtestData.get(p).get("InspectionAreaCode") + "."
										+ pnbtestData.get(p).get("InspectionPrefix") + "."
										+ pnbtestData.get(p).get("InspectionNumber"),
								false, false);
					else
						Assertions.addInfo(
								"Inspection Area Code : "
										+ buildingData.get("InspectionPhone").toString().substring(0, 3) + "."
										+ buildingData.get("InspectionPhone").toString().substring(4, 7) + "."
										+ buildingData.get("InspectionPhone").toString().substring(8),
								"Inspection Area Code : " + pnbtestData.get(p).get("InspectionAreaCode") + "."
										+ pnbtestData.get(p).get("InspectionPrefix") + "."
										+ pnbtestData.get(p).get("InspectionNumber"));
					dwellingInspectionAreaCode = true;
				}
			}
		}
		Assertions.addInfo("Nature of Business :" + buildingData.get("natureOfbusiness"),
				"Nature of Business :" + buildingData.get("natureOfbusiness"));
	}

	public void verifyInspectionOrderforPNB(Map<String, String> pnbtestData, int transactionId, int hvn) {
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 5; j++) {
				Object inspectionOrders = getInspectionOrder(transactionId, "L" + i + "D" + j);
				if (inspectionOrders == null) {
					break;
				}
				inspectionOrders = inspectionOrders.toString();

				if (inspectionOrders.equals("1")) {
					Assertions.passTest("<span class='group'>Inspection order Yes : " + "L" + i + "D" + j + "</span>",
							"");
				}
			}
		}
	}

	public void verifyDeleteTransactionIdforPNB(Map<String, String> pnbtestData, int transactionId, int hvn) {
		for (int i = 1; i <= 3; i++) {
			for (int j = 1; j <= 5; j++) {
				if (pnbtestData.get("L" + i + "D" + j + "-DeleteDwelling").equalsIgnoreCase("yes")) {
					Assertions.passTest("Deleted Dwelling : " + "L" + i + "D" + j, "Deleted Dwelling Transaction Id "
							+ deleteTransactionidForDwellingOrder(transactionId, "L" + i + "D" + j));
				}
			}
		}
	}



	public String getBuildingAddr2(Integer policyBuildingIdNum, int transid) {
		return build
				.fetch(address2, null,
						policyBuildingId + " = " + policyBuildingIdNum + " and " + transactionId + " = " + transid)
				.toString();
	}


	 */
}
