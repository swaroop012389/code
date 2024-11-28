/** Program Description: Methods and queries built up for RMSDATA table
 *  Author			   : SMNetserv
 *  Date of Creation   : 22/02/2018
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

public class RMSDATA {
	public String rmsTable;
	public String hvn;
	public String product;
	public String floodZone;
	public String elevationAvg;
	public String elevationMin;
	public String elevationMax;
	public String elevationConfidence;
	public String liquefactionType;
	public String liquefactionDescription;
	public String liquefactionConfidenceMessage;
	public String liquefactionMapResolution;
	public String distanceToCoast;
	public String createDateTime;
	public String locationLongitude;
	public String locationLatitude;
	public String geoRes;


	/* RESIDENTIAL PARAMETERS
	public String elevation;
	public String soilType;
	public String liquefactionValue;
	public String policyBuildingID;
	public String locationLattitude;
	public String locationLongittude;
	Building building;
	Policy policy;
	*/

	public String transactionId;
	public DBFrameworkConnection connection;
	public QueryBuilder build;
	public Map<String, Object> results;

	private static List<Integer> testStatus;

	public RMSDATA(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("RmsData");
		rmsTable = properties.getProperty("db_RMSTable");
		hvn = properties.getProperty("db_Hvn");
		product = properties.getProperty("db_Product");
		floodZone = properties.getProperty("db_FloodZone");
		elevationAvg = properties.getProperty("db_ElevationAverage");
		elevationMin = properties.getProperty("db_ElevationMin");
		elevationMax = properties.getProperty("db_ElevationMax");
		elevationConfidence = properties.getProperty("db_ElevationConfidence");
		liquefactionType = properties.getProperty("db_LiquefactionType");
		liquefactionDescription = properties.getProperty("db_LiquefactionDescription");
		liquefactionConfidenceMessage = properties.getProperty("db_LiquefactionConfidenceMessage");
		liquefactionMapResolution = properties.getProperty("db_LiquefactionMapResolution");
		distanceToCoast = properties.getProperty("db_DistToCoast");
		createDateTime = properties.getProperty("db_CreateDateTime");
		locationLongitude = properties.getProperty("db_LocationLongitude");
		locationLatitude = properties.getProperty("db_LocationLatitude");
		geoRes = properties.getProperty("db_GeoRes");

		/* RESIDENTIAL PARAMETERS
		elevation = properties.getProperty("db_Elevation");
		soilType = properties.getProperty("db_SoilType");
		liquefactionValue = properties.getProperty("db_LiqueficationValue");
		policyBuildingID = properties.getProperty("db_PolicyBuildingId");
		locationLattitude = properties.getProperty("db_LocationLatitude");
		locationLongittude = properties.getProperty("db_LocationLongitude");
				policy = new Policy(dbConfig);
		building = new Building(dbConfig);
		 */

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(rmsTable);

	}

	public List<Map<String, Object>> getRMSDATADetails(int transactionId) {
		List<String> outFields = new ArrayList<>();
		outFields.add(hvn);
		outFields.add(product);
		outFields.add(floodZone);
		outFields.add(elevationAvg);
		outFields.add(elevationMin);
		outFields.add(elevationMax);
		outFields.add(elevationConfidence);
		outFields.add(liquefactionType);
		outFields.add(liquefactionDescription);
		outFields.add(liquefactionConfidenceMessage);
		outFields.add(liquefactionMapResolution);
		outFields.add(distanceToCoast);
		outFields.add(createDateTime);
		outFields.add(locationLongitude);
		outFields.add(locationLatitude);
		outFields.add(geoRes);

		/* RESIDENTIAL PROPERTIES
		outFields.add(lossCount);
		outFields.add(dateOfLoss);
		outFields.add(descriptionOfLoss);
		outFields.add(grossLossAmount);
		outFields.add(isCompleteRepairs);
		outFields.add(sinkholeLosses);

		 */

		build.outFields(outFields);
		build.whereBy("transactionId = " + transactionId);
		List<Map<String, Object>> rmsDataDetails = build.execute(60);

		return rmsDataDetails;
	}

	public List<Map<String, String>> getRMSDataExpected(String tcid) {
		List<Map<String, String>> rmsData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"RMSData").readExcelRowWise();

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : rmsData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);
			}
		}

		return TCData;

	}

	public String verifyRMSData(List<Map<String, String>> binderData, int transactionId) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		List<Map<String, Object>> aftershockValues = getRMSDATADetails(transactionId);

		for (int i = 0; i < binderData.size(); i++) {
			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = aftershockValues.get(i);

			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN"), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Product").toString(), binderDataRow.get("Product"), "Product : " + aftershockRow.get("Product").toString(), "Expected : " + binderDataRow.get("Product"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Flood_Zone").toString(), binderDataRow.get("Flood_Zone"), "Flood_Zone : " + aftershockRow.get("Flood_Zone").toString(), "Expected : " + binderDataRow.get("Flood_Zone"), false, false);
			String elev_averageDBValue = "NULL";
			if (aftershockRow.get("Elev_Average") != null){
				elev_averageDBValue = aftershockRow.get("Elev_Average").toString();
			}
			testStatus = Assertions.verify(elev_averageDBValue, binderDataRow.get("Elev_Average"), "Elev_Average : " + elev_averageDBValue, "Expected : " + binderDataRow.get("Elev_Average"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Elev_Min").toString(), binderDataRow.get("Elev_Min"), "Elev_Min : " + aftershockRow.get("Elev_Min").toString(), "Expected : " + binderDataRow.get("Elev_Min"), false, false);
			String elev_maxDBValue = "NULL";
			if (aftershockRow.get("Elev_Max") != null){
				elev_maxDBValue = aftershockRow.get("Elev_Max").toString();
			}
			testStatus = Assertions.verify(elev_maxDBValue, binderDataRow.get("Elev_Max"), "Elev_Max : " + elev_maxDBValue, "Expected : " + binderDataRow.get("Elev_Max"), false, false);
			String elev_confidenceDBValue = "NULL";
			if (aftershockRow.get("Elev_Confidence") != null){
				elev_confidenceDBValue = aftershockRow.get("Elev_Confidence").toString();
			}
			testStatus = Assertions.verify(elev_confidenceDBValue, binderDataRow.get("Elev_Confidence"), "Elev_Confidence : " + elev_confidenceDBValue, "Expected : " + binderDataRow.get("Elev_Confidence"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Liqu_Type").toString(), binderDataRow.get("Liqu_Type"), "Liqu_Type : " + aftershockRow.get("Liqu_Type").toString(), "Expected : " + binderDataRow.get("Liqu_Type"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Liqu_Description").toString(), binderDataRow.get("Liqu_Description"), "Liqu_Description : " + aftershockRow.get("Liqu_Description").toString(), "Expected : " + binderDataRow.get("Liqu_Description"), false, false);
			String liqu_confidence_msgDBValue = "NULL";
			if (aftershockRow.get("Liqu_Confidence_Msg") != null){
				liqu_confidence_msgDBValue = aftershockRow.get("Liqu_Confidence_Msg").toString();
			}
			testStatus = Assertions.verify(liqu_confidence_msgDBValue, binderDataRow.get("Liqu_Confidence_Msg"), "Liqu_Confidence_Msg : " + liqu_confidence_msgDBValue, "Expected : " + binderDataRow.get("Liqu_Confidence_Msg"), false, false);
			String liqu_map_resolutionDBValue = "NULL";
			if (aftershockRow.get("Liqu_Map_Resolution") != null){
				liqu_map_resolutionDBValue = aftershockRow.get("Liqu_Map_Resolution").toString();
			}
			testStatus = Assertions.verify(liqu_map_resolutionDBValue, binderDataRow.get("Liqu_Map_Resolution"), "Liqu_Map_Resolution : " + liqu_map_resolutionDBValue, "Expected : " + binderDataRow.get("Liqu_Map_Resolution"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Coast_Dist").toString(), binderDataRow.get("Coast_Dist"), "Coast_Dist : " + aftershockRow.get("Coast_Dist").toString(), "Expected : " + binderDataRow.get("Coast_Dist"), false, false);

			LocalDate asCreateDateTime = LocalDate.parse(String.valueOf(aftershockRow.get("CreateDateTime")).substring(0,10));
			String epiProcessDate = ExcelReportUtil.getInstance().getLatestProcessingDateFromMaster(binderDataRow.get("TCID"));
			testStatus = Assertions.verify(asCreateDateTime.format(f), epiProcessDate, "CreateDateTime : " + asCreateDateTime.format(f), "Expected : " + epiProcessDate, false, false);
			testStatus = Assertions.verify(aftershockRow.get("Loc_Lon").toString(), binderDataRow.get("Loc_Lon"), "Loc_Lon : " + aftershockRow.get("Loc_Lon").toString(), "Expected : " + binderDataRow.get("Loc_Lon"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Loc_Lat").toString(), binderDataRow.get("Loc_Lat"), "Loc_Lat : " + aftershockRow.get("Loc_Lat").toString(), "Expected : " + binderDataRow.get("Loc_Lat"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Geo_Res").toString(), binderDataRow.get("Geo_Res"), "GeoRes : " + aftershockRow.get("Geo_Res").toString(), "Expected : " + binderDataRow.get("Geo_Res"), false, false);

		}

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}


	/*RESIDENTIAL METHODS


	public Map<String, Object> getRMSDetails(int policybldgID) {
		List<String> outFields = new ArrayList<>();
		outFields.add(floodZone);
		outFields.add(elevation);
		outFields.add(soilType);
		outFields.add(liquefactionValue);
		outFields.add(locationLattitude);
		outFields.add(locationLongittude);
		outFields.add(distanceToCoast);
		build.outFields(outFields);
		build.whereBy(policyBuildingID + " in (" + policybldgID + ")");
		List<Map<String, Object>> rmsData = build.execute(60);
		return rmsDetails = rmsData.get(0);
	}

	public String verifyRMSData(Map<String, String> binderData, int policybldgID, String productSelection) {
		rmsDetails = getRMSDetails(policybldgID);
		BigDecimal elevation = new BigDecimal((rmsDetails.get("ELEV_MIN").toString()));
		elevation = elevation.setScale(2, RoundingMode.HALF_UP);
		Assertions.addInfo("<span class='group'> RMS Details : " + binderData.get("Address2") + "</span>",
				"<span class='group'> GROUPING </span>");
		Assertions.verify(rmsDetails.get("FLOOD_ZONE") + "", binderData.get("FloodZone"),
				"Flood Zone : " + rmsDetails.get("FLOOD_ZONE"), "Flood Zone : " + binderData.get("FloodZone"), false,
				false);
		Assertions.verify(elevation.toString(), binderData.get("Elevation"), "Elevation : " + elevation,
				"Elevation : " + binderData.get("Elevation"), false, false);
		Assertions.verify(rmsDetails.get("SOIL_DESCRIPTION"), binderData.get("SoilType"),
				"Soil Type : " + rmsDetails.get("SOIL_DESCRIPTION"), "Soil Type : " + binderData.get("SoilType"), false,
				false);
		Assertions.verify(rmsDetails.get("LIQU_DESCRIPTION"), binderData.get("LiquefactionValue"),
				"Liquefaction Value : " + rmsDetails.get("LIQU_DESCRIPTION"),
				"Liquefaction Value : " + binderData.get("LiquefactionValue"), false, false);
		if (!productSelection.equalsIgnoreCase("Commercial")) {
			Assertions.verify(rmsDetails.get("LOC_LAT"), binderData.get("LocationLattitude"),
					"Location Lattitude : " + rmsDetails.get("LOC_LAT"),
					"Location Lattitude : " + binderData.get("LocationLattitude"), false, false);
			Assertions.verify(rmsDetails.get("LOC_LON"), binderData.get("LocationLongitude"),
					"Location Longitude : " + rmsDetails.get("LOC_LON"),
					"Location Longitude : " + binderData.get("LocationLongitude"), false, false);
			testStatus=Assertions.verify(rmsDetails.get("COAST_DIST"), binderData.get("DistanceToCoast"),
					"Distance To Coast : " + rmsDetails.get("COAST_DIST"),
					"Distance To Coast : " + binderData.get("DistanceToCoast"), false, false);
		}
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}

	public void verifyRMSDataPNB(Map<String, String> binderData, int policybldgID, String productSelection,
			String dwellingAddress) {
		rmsDetails = getRMSDetails(policybldgID);
		BigDecimal elevation = new BigDecimal((rmsDetails.get("ELEV_MIN").toString()));
		elevation = elevation.setScale(2, RoundingMode.HALF_UP);
		if (!dwellingAddress.equals("")) {
			Assertions.addInfo("<span class='group'> RMS Details : " + binderData.get("Address2") + "</span>",
					"<span class='group'> GROUPING </span>");
			if (!binderData.get("FloodZone").equals(""))
				Assertions.verify(rmsDetails.get("FLOOD_ZONE") + "", binderData.get("FloodZone"),
						"Flood Zone : " + rmsDetails.get("FLOOD_ZONE"), "Flood Zone : " + binderData.get("FloodZone"),
						false, false);
			Assertions.verify(elevation.toString(), binderData.get("Elevation"), "Elevation : " + elevation,
					"Elevation : " + binderData.get("Elevation"), false, false);
			Assertions.verify(rmsDetails.get("SOIL_DESCRIPTION"), binderData.get("SoilType"),
					"Soil Type : " + rmsDetails.get("SOIL_DESCRIPTION"), "Soil Type : " + binderData.get("SoilType"),
					false, false);
			Assertions.verify(rmsDetails.get("LIQU_DESCRIPTION"), binderData.get("LiquefactionValue"),
					"Liquefaction Value : " + rmsDetails.get("LIQU_DESCRIPTION"),
					"Liquefaction Value : " + binderData.get("LiquefactionValue"), false, false);
			Assertions.verify(rmsDetails.get("LOC_LAT"), binderData.get("LocationLattitude"),
					"Location Lattitude : " + rmsDetails.get("LOC_LAT"),
					"Location Lattitude : " + binderData.get("LocationLattitude"), false, false);
			Assertions.verify(rmsDetails.get("LOC_LON"), binderData.get("LocationLongitude"),
					"Location Longitude : " + rmsDetails.get("LOC_LON"),
					"Location Longitude : " + binderData.get("LocationLongitude"), false, false);
			Assertions.verify(rmsDetails.get("COAST_DIST"), binderData.get("DistanceToCoast"),
					"Distance To Coast : " + rmsDetails.get("COAST_DIST"),
					"Distance To Coast : " + binderData.get("DistanceToCoast"), false, false);
		} else {
			Assertions.addInfo("<span class='group'> RMS Details : " + binderData.get("Address2") + "</span>",
					"<span class='group'> GROUPING </span>");
			if (!binderData.get("FloodZone").equals(""))
				Assertions.addInfo("Flood Zone : " + rmsDetails.get("FLOOD_ZONE"),
						"Flood Zone : " + binderData.get("FloodZone"));
			Assertions.addInfo("Elevation : " + elevation, "Elevation : " + binderData.get("Elevation"));
			Assertions.addInfo("Soil Type : " + rmsDetails.get("SOIL_DESCRIPTION"),
					"Soil Type : " + binderData.get("SoilType"));
			Assertions.addInfo("Liquefaction Value : " + rmsDetails.get("LIQU_DESCRIPTION"),
					"Liquefaction Value : " + binderData.get("LiquefactionValue"));
			Assertions.addInfo("Location Lattitude : " + rmsDetails.get("LOC_LAT"),
					"Location Lattitude : " + binderData.get("LocationLattitude"));
			Assertions.addInfo("Location Longitude : " + rmsDetails.get("LOC_LON"),
					"Location Longitude : " + binderData.get("LocationLongitude"));
			Assertions.addInfo("Distance To Coast : " + rmsDetails.get("COAST_DIST"),
					"Distance To Coast : " + binderData.get("DistanceToCoast"));
		}
	} */
}
