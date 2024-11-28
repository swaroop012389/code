/** Program Description: Methods and queries built up for LineOfCoverage table
 *  Author			   : SMNetserv
 *  Date of Creation   : 21/02/2018
 **/

package com.icat.epicenter.dom;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;

public class LineOfCoverage {

	public String tableName;
	public String policyNumber;

	public String hvn;
	public String policyLocationId;
	public String policySpecialClassId;
	public String covId;
	public String covPerilId;
	public String limit;
	public String value;
	public String monthlyIndemnity;
	public String quotaSharePercent;
	public String isSublimit;
	public String annualTransactionPremium;
	public String carrierId;
	public String transactionPremium;
	public String participation;
	public String deleteTransactionId;


	/*

	public String policyBuildingId;

	public String coverageId;
	public String limit;
	public String coveragePerilId;
	public String transactionPremium;
		public String deleteTransactionid;
		Location location;
	PolicyLocation policyLocation;
	SpecialClass specialClass;
	Coverage cov;
	CovPeril cp;
	Building building;
	AdditionalInterest additionalInterestPage;
	 */


	public DBFrameworkConnection connection;
	public QueryBuilder build;

	public Map<String, Object> results;
	private static List<Integer> testStatus;

	public LineOfCoverage(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("LineOfCoverage");

		tableName = properties.getProperty("db_LineOfCoverageTable");
		hvn = properties.getProperty("db_Hvn");
		policyLocationId = properties.getProperty("db_PolicyLocationId");
		policySpecialClassId = properties.getProperty("db_PolicySpecialClassId");
		covId = properties.getProperty("db_CovId");
		covPerilId = properties.getProperty("db_CovPerilId");
		limit = properties.getProperty("db_Limit");
		value = properties.getProperty("db_Value");
		monthlyIndemnity = properties.getProperty("db_MonthlyIndemnity");
		quotaSharePercent = properties.getProperty("db_QuotaSharePercent");
		isSublimit = properties.getProperty("db_IsSublimit");
		annualTransactionPremium = properties.getProperty("db_AnnualTransactionPremium");
		carrierId = properties.getProperty("db_CarrierID");
		transactionPremium = properties.getProperty("db_TransactionPremium");
		participation = properties.getProperty("db_Participation");
		deleteTransactionId = properties.getProperty("db_DeleteTransactionId");

		/*RESIDENTIAL PARAMETERS
		policyNumber = properties.getProperty("db_PolicyNumber");
		policyLocationId = properties.getProperty("db_PolicyLocationId");
		policyBuildingId = properties.getProperty("db_PolicyBuildingId");
		coverageId = properties.getProperty("db_CoverageId");
		coveragePerilId = properties.getProperty("db_CoveragePerilId");
		deleteTransactionid = properties.getProperty("db_DeleteTransactionId");
		location = new Location(dbConfig);
		policyLocation = new PolicyLocation(dbConfig);
		specialClass = new SpecialClass(dbConfig);
		cov = new Coverage(dbConfig);
		cp = new CovPeril(dbConfig);
		building = new Building(dbConfig);
		additionalInterestPage = new AdditionalInterest(dbConfig);	 */

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);

	}

	public List<Map<String, Object>> getLineOfCoverageDetails(int transactionId) {
		List<String> outFields = new ArrayList<>();
		outFields.add(hvn);
		outFields.add(policyLocationId);
		outFields.add(policySpecialClassId);
		outFields.add(covId);
		outFields.add(covPerilId);
		outFields.add(limit);
		outFields.add(value);
		outFields.add(monthlyIndemnity);
		outFields.add(quotaSharePercent);
		outFields.add(isSublimit);
		outFields.add(annualTransactionPremium);
		outFields.add(carrierId);
		outFields.add(transactionPremium);
		outFields.add(participation);
		outFields.add(deleteTransactionId);

		build.outFields(outFields);
		build.whereBy("transactionId = " + transactionId + " order by PolicyLocationId, PolicySpecialClassId, CovPerilId, CovId");
		List<Map<String, Object>> lineOfCoverageDetails = build.execute(60);
		return lineOfCoverageDetails;
	}

	public List<Map<String, String>> getLineOfCoverageDataExpected(String tcid) {
		List<Map<String, String>> binderLineOfCoverageData = new ArrayList<>();

		//splitting out LineOfCoverage expected values into 6 spreadsheet tabs because the data is so big
		int testNum = Integer.parseInt(tcid.split("_")[0].replace("NBTC", ""));

		if (testNum <= 5) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage1").readExcelRowWise();
		} else if (6 <= testNum && testNum <= 16) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage2").readExcelRowWise();
		} else if (17 <= testNum && testNum <= 26) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage3").readExcelRowWise();
		} else if (27 <= testNum && testNum <= 32) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage4").readExcelRowWise();
		} else if (33 <= testNum && testNum <= 40) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage5").readExcelRowWise();
		} else if (41 <= testNum && testNum <= 68) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage6").readExcelRowWise();
		} else if (69 <= testNum && testNum <= 77) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage7").readExcelRowWise();
		} else if (78 <= testNum && testNum <= 85) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage8").readExcelRowWise();
		} else if (86 <= testNum && testNum <= 97) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage9").readExcelRowWise();
		} else if (98 <= testNum && testNum <= 113) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage10").readExcelRowWise();
		} else if (114 <= testNum && testNum <= 123) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage11").readExcelRowWise();
		} else if (testNum > 123) {
			binderLineOfCoverageData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LineOfCoverage12").readExcelRowWise();
		}

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : binderLineOfCoverageData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);
			}
		}

		return TCData;

	}

	public String verifyLineOfCoverageData(List<Map<String, String>> binderData, int transactionId) {
		DecimalFormat twoDigits = new DecimalFormat("#.##");
		DecimalFormat fourDigits = new DecimalFormat("#.####");
		List<Map<String, Object>> aftershockValues = getLineOfCoverageDetails(transactionId);

		for (int i = 0; i < binderData.size(); i++) {
			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = aftershockValues.get(i);

			System.out.println("LineOfCoverage binderDataRow = " + binderDataRow);
			System.out.println("LineOfCoverage aftershockRow = " + aftershockRow);

			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN"), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("CovId").toString(), binderDataRow.get("CovId"), "CovId : " + aftershockRow.get("CovId").toString(), "Expected : " + binderDataRow.get("CovId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("CovPerilId").toString(), binderDataRow.get("CovPerilId"), "CovPerilId : " + aftershockRow.get("CovPerilId").toString(), "Expected : " + binderDataRow.get("CovPerilId"), false, false);
			String limit = "NULL";
			if (aftershockRow.get("Limit") != null){
				limit = aftershockRow.get("Limit").toString();
			}

			double asLimit = 0.00;
			double expectedLimit = 0.00;
			if (!String.valueOf(binderDataRow.get("Limit")).equals("NULL")) {
				asLimit = Double.parseDouble(String.valueOf(aftershockRow.get("Limit")));
				expectedLimit = Double.parseDouble(String.valueOf(binderDataRow.get("Limit")));
			}
			testStatus = Assertions.verify(twoDigits.format(asLimit), twoDigits.format(expectedLimit), "Limit : " + twoDigits.format(asLimit), "Expected : " + twoDigits.format(expectedLimit), false, false);

			double asValue = 0.00;
			double expectedValue = 0.00;
			if (!String.valueOf(binderDataRow.get("Value")).toUpperCase().equals("NULL")) {
				asValue = Double.parseDouble(String.valueOf(aftershockRow.get("Value")));
				expectedValue = Double.parseDouble(String.valueOf(binderDataRow.get("Value")));
			}
			testStatus = Assertions.verify(twoDigits.format(asValue), twoDigits.format(expectedValue), "Value : " + twoDigits.format(asValue), "Expected : " + twoDigits.format(expectedValue), false, false);
			String monthlyIndemnity = "NULL";
			if (aftershockRow.get("MonthlyIndemnity") != null){
				monthlyIndemnity = aftershockRow.get("MonthlyIndemnity").toString();
			}
			double asMonthlyIndemnity = 0.0000;
			double expectedMonthlyIndemnity = 0.0000;
			if (!String.valueOf(binderDataRow.get("MonthlyIndemnity")).toUpperCase().equals("NULL")) {
				asMonthlyIndemnity = Double.parseDouble(String.valueOf(aftershockRow.get("MonthlyIndemnity")));
				expectedMonthlyIndemnity = Double.parseDouble(String.valueOf(binderDataRow.get("MonthlyIndemnity")));
			}
			testStatus = Assertions.verify(fourDigits.format(asMonthlyIndemnity), fourDigits.format(expectedMonthlyIndemnity), "MonthlyIndemnity : " + fourDigits.format(asMonthlyIndemnity), "Expected : " + fourDigits.format(expectedMonthlyIndemnity), false, false);

			double asQuotaShare = 0.00;
			double expectedQuotaShare = 0.00;
			if (!String.valueOf(binderDataRow.get("QuotaSharePercent")).toUpperCase().equals("NULL")) {
				asQuotaShare = Double.parseDouble(String.valueOf(aftershockRow.get("QuotaSharePercent")));
				expectedQuotaShare = Double.parseDouble(String.valueOf(binderDataRow.get("QuotaSharePercent")));
			}
			testStatus = Assertions.verify(twoDigits.format(asQuotaShare), twoDigits.format(expectedQuotaShare), "QuotaSharePercent : " + twoDigits.format(asQuotaShare), "Expected : " + twoDigits.format(expectedQuotaShare), false, false);

			String expectedIsSublimit = "false";
			if (binderDataRow.get("IsSublimit").equals("1")) {
				expectedIsSublimit = "true";
			}

			testStatus = Assertions.verify(aftershockRow.get("IsSublimit").toString(), expectedIsSublimit, "IsSublimit : " + aftershockRow.get("IsSublimit").toString(), "Expected : " + expectedIsSublimit, false, false);

			double asAnnTxnPrem = 0.00;
			double expectedAnnTxnPrem = 0.00;
			if (!String.valueOf(binderDataRow.get("AnnualTransactionPremium")).toUpperCase().equals("NULL")) {
				asAnnTxnPrem = Double.parseDouble(String.valueOf(aftershockRow.get("AnnualTransactionPremium")));
				expectedAnnTxnPrem = Double.parseDouble(String.valueOf(binderDataRow.get("AnnualTransactionPremium")));
			}
			//TODO - see if there's a way to check these premiums, as they'll change often, they're not currently checked in the ruby framework
			//testStatus = Assertions.verify(twoDigits.format(asAnnTxnPrem), twoDigits.format(expectedAnnTxnPrem), "AnnualTransactionPremium : " + twoDigits.format(asAnnTxnPrem), "Expected : " + twoDigits.format(expectedAnnTxnPrem), false, false);
			//TODO - in Ruby, I get CarrierID from the Epicenter DB - not sure how to get it here
			//testStatus = Assertions.verify(aftershockRow.get("CarrierID").toString(), binderDataRow.get("CarrierID"), "CarrierID : " + aftershockRow.get("CarrierID").toString(), "Expected : " + binderDataRow.get("CarrierID"), false, false);

			double asTxnPrem = 0.00;
			double expectedTxnPrem = 0.00;
			if (!String.valueOf(binderDataRow.get("TransactionPremium")).toUpperCase().equals("NULL")) {
				asTxnPrem = Double.parseDouble(String.valueOf(aftershockRow.get("TransactionPremium")));
				expectedTxnPrem = Double.parseDouble(String.valueOf(binderDataRow.get("TransactionPremium")));
			}
			//TODO - see if there's a way to check these premiums, as they'll change often, they're not currently checked in the ruby framework
			//testStatus = Assertions.verify(twoDigits.format(asTxnPrem), twoDigits.format(expectedTxnPrem), "TransactionPremium : " + twoDigits.format(asTxnPrem), "Expected : " + twoDigits.format(expectedTxnPrem), false, false);

			double asParticipation = 0.00;
			double expectedParticipation = 0.00;
			if (!String.valueOf(binderDataRow.get("Participation")).toUpperCase().equals("NULL")) {
				asParticipation = Double.parseDouble(String.valueOf(aftershockRow.get("Participation")));
				expectedParticipation = Double.parseDouble(String.valueOf(binderDataRow.get("Participation")));
			}
			testStatus = Assertions.verify(twoDigits.format(asParticipation), twoDigits.format(expectedParticipation), "Participation : " + twoDigits.format(asParticipation), "Expected : " + twoDigits.format(expectedParticipation), false, false);

			String expectedDeleteTxnId;
			if (binderDataRow.get("DeleteTransactionId").toString().equals("0")) {
				expectedDeleteTxnId = "0";
			} else {
				expectedDeleteTxnId = String.valueOf(transactionId);
			}
			testStatus = Assertions.verify(aftershockRow.get("DeleteTransactionId").toString(), expectedDeleteTxnId, "DeleteTransactionId : " + aftershockRow.get("DeleteTransactionId").toString(), "Expected : " + expectedDeleteTxnId, false, false);

		}

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}



	/* RESIDENTIAL METHODS
	public Integer getTransactionPremium(Integer specialClassid) {
		return ((BigDecimal) build.fetch(transactionPremium, null, policySpecialClassId + "=" + specialClassid))
				.intValue();
	}

	public String verifyTransactionPremium(String TransactionPremium, Integer specialClassid) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		testStatus=Assertions.verify(getTransactionPremium(specialClassid).toString(), TransactionPremium,
				"Transaction Premium :" + format.format(getTransactionPremium(specialClassid)),
				"Transaction Premium :" + format.format(Integer.valueOf(TransactionPremium)), false, false);
		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}

	public Map<String, String> getTotalAPCPremium(String policyNumberData) {
		List<String> outFields = new ArrayList<>();
		Map<String, String> transactionPremiumData = new HashMap<>();
		outFields.add("sum(" + transactionPremium + ") as TotalBuildingPremium");
		outFields.add(policyLocationId);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = '" + policyNumberData + "' and " + policySpecialClassId + ">0");
		build.groupBy(policyLocationId);
		List<Map<String, Object>> sumdetails = build.execute(60);
		int size = sumdetails.size();
		for (int i = 0; i < size; i++) {
			sumdetails.get(i).put("LocNum",
					location.getLocationNumber((Integer) sumdetails.get(i).get(policyLocationId)));
		}
		for (int i = 0; i < size; i++) {
			transactionPremiumData.put(sumdetails.get(i).get("LocNum").toString(),
					((BigDecimal) sumdetails.get(i).get("TotalBuildingPremium")).intValue() + "");
		}
		return transactionPremiumData;
	}

	public List<Map<String, Object>> getCoverageDetails(String policynumberData) {
		ArrayList<String> outFields = new ArrayList<>();
		outFields.add(policyNumber);
		outFields.add(policyLocationId);
		outFields.add(policyBuildingId);
		outFields.add(policySpecialClassId);
		outFields.add(coverageId);
		outFields.add(coveragePerilId);
		outFields.add(transactionPremium);
		outFields.add(limit);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = " + "'" + policynumberData + "'");
		List<Map<String, Object>> coverageDetails = build.execute(60);
		return coverageDetails;
	}

	public List<Map<String, Object>> getCoverageDetailsforPNB(String policynumberData, int hvn) {
		List<String> outFields = new ArrayList<>();
		outFields.add(policyNumber);
		outFields.add(policyLocationId);
		outFields.add(policyBuildingId);
		outFields.add(policySpecialClassId);
		outFields.add(coverageId);
		outFields.add(coveragePerilId);
		outFields.add(transactionPremium);
		outFields.add(limit);
		outFields.add(deleteTransactionid);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = " + "'" + policynumberData + "'" + " and " + Hvn + " =" + hvn);
		List<Map<String, Object>> coverageDetails = build.execute(60);
		return coverageDetails;
	}

	public List<Map<String, Object>> getDBLocationLevelData(List<Map<String, Object>> testDAta) {
		List<Map<String, Object>> locationLevel = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			Integer policyLocationID = (Integer) testDAta.get(i).get("PolicyLocationId");
			Integer policyBuildingID = (Integer) testDAta.get(i).get("PolicyBuildingId");
			Integer policySpecialClassID = (Integer) testDAta.get(i).get("PolicySpecialClassID");
			if (policyLocationID > 0 && policyBuildingID == 0 && policySpecialClassID == 0) {
				locationLevel.add(testDAta.get(i));
			}
		}
		testDAta = locationLevel;
		List<Map<String, Object>> alteredDate = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			Map<String, Object> data = new HashMap<>();
			data.put("CoverageAppliesBy", "Location");
			data.put("CoverageApplicability",
					"L" + location.getLocationNumber((Integer) testDAta.get(i).get("PolicyLocationId")));
			data.put("CovDsc", cov.getCoverageDesc((Integer) testDAta.get(i).get("Covid")));
			data.put("CovPerilDsc", cp.getPerilDesc((Integer) testDAta.get(i).get("CovPerilId")));
			if (data.get("CovDsc").toString().equalsIgnoreCase("Additional Coverage")) {
				if ((Integer) testDAta.get(i).get("TransactionPremium") == 400)
					data.put("IsCoveragePackage", "Coverage A");
				else if ((Integer) testDAta.get(i).get("TransactionPremium") == 300)
					data.put("IsCoveragePackage", "Coverage B");
				else
					data.put("IsCoveragePackage", "Not a Coverage Package");
			} else {
				data.put("IsCoveragePackage", "Not a Coverage Package");
			}
			Integer limit = ((BigDecimal) testDAta.get(i).get("Limit")).intValue();
			data.put("Limit", limit + "");
			data.put("TransactionPremium", ((BigDecimal) testDAta.get(i).get("TransactionPremium")).intValue() + "");
			alteredDate.add(data);
		}
		Collections.sort(alteredDate, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return o1.get("CoverageApplicability").toString().compareTo(o2.get("CoverageApplicability").toString());
			}
		});
		return alteredDate;
	}

	public List<Map<String, Object>> getDBAPCLevelData(List<Map<String, Object>> testDAta) {
		List<Map<String, Object>> APCLevel = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			Integer policyLocationID = (Integer) testDAta.get(i).get("PolicyLocationId");
			Integer policyBuildingID = (Integer) testDAta.get(i).get("PolicyBuildingId");
			Integer policySpecialClassID = (Integer) testDAta.get(i).get("PolicySpecialClassID");
			if (policyLocationID > 0 && policyBuildingID == 0 && policySpecialClassID > 0) {
				APCLevel.add(testDAta.get(i));
			}
		}
		testDAta = APCLevel;
		List<Map<String, Object>> alteredDate = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			Map<String, Object> data = new HashMap<>();
			data.put("CoverageAppliesBy",
					"APC-L" + location.getLocationNumber((Integer) testDAta.get(i).get("PolicyLocationId")));
			data.put("CoverageApplicability",
					specialClass.getSpecialClassName((Integer) testDAta.get(i).get("PolicySpecialClassID")));
			data.put("CovDsc", cov.getCoverageDesc((Integer) testDAta.get(i).get("Covid")));
			data.put("CovPerilDsc", cp.getPerilDesc((Integer) testDAta.get(i).get("CovPerilId")));
			if (data.get("CovDsc").toString().equalsIgnoreCase("Additional Coverage")) {
				if ((Integer) testDAta.get(i).get("TransactionPremium") == 400)
					data.put("IsCoveragePackage", "Coverage A");
				else if ((Integer) testDAta.get(i).get("TransactionPremium") == 300)
					data.put("IsCoveragePackage", "Coverage B");
				else
					data.put("IsCoveragePackage", "Not a Coverage Package");
			} else {
				data.put("IsCoveragePackage", "Not a Coverage Package");
			}
			Integer limit = ((BigDecimal) testDAta.get(i).get("Limit")).intValue();
			data.put("Limit", limit + "");
			data.put("TransactionPremium", ((BigDecimal) testDAta.get(i).get("TransactionPremium")).intValue() + "");
			alteredDate.add(data);
		}
		Collections.sort(alteredDate, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return o1.get("CoverageApplicability").toString().compareTo(o2.get("CoverageApplicability").toString());
			}
		});
		return alteredDate;
	}

	public List<Map<String, String>> getDBBuildingLevelData(List<Map<String, Object>> testDAta) {
		List<Map<String, Object>> BuildingLevel = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			Integer policyLocationID = (Integer) testDAta.get(i).get("PolicyLocationId");
			Integer policyBuildingID = (Integer) testDAta.get(i).get("PolicyBuildingId");
			Integer policySpecialClassID = (Integer) testDAta.get(i).get("PolicySpecialClassID");
			if (policyLocationID > 0 && policyBuildingID > 0 && policySpecialClassID == 0) {
				BuildingLevel.add(testDAta.get(i));
			}
		}
		testDAta = BuildingLevel;
		List<Map<String, String>> alteredDate = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			Map<String, String> data = new HashMap<>();
			data.put("CoverageAppliesBy", "Building");
			data.put("DeleteTransactionid", testDAta.get(i).get("deleteTransactionid") + "");
			data.put("CoverageApplicability",
					building.getBuildingAddr2((Integer) testDAta.get(i).get("PolicyBuildingId")));
			data.put("CovDsc", cov.getCoverageDesc((Integer) testDAta.get(i).get("Covid")));
			data.put("CovPerilDsc", cp.getPerilDesc((Integer) testDAta.get(i).get("CovPerilId")));
			if (data.get("CovDsc").toString().equalsIgnoreCase("Additional Coverage")) {
				if ((Integer) testDAta.get(i).get("TransactionPremium") == 400)
					data.put("IsCoveragePackage", "Coverage A");
				else if ((Integer) testDAta.get(i).get("TransactionPremium") == 300)
					data.put("IsCoveragePackage", "Coverage B");
				else
					data.put("IsCoveragePackage", "Not a Coverage Package");
			} else {
				data.put("IsCoveragePackage", "Not a Coverage Package");
			}
			Integer limit = ((BigDecimal) testDAta.get(i).get("Limit")).intValue();
			data.put("Limit", limit + "");
			data.put("TransactionPremium", ((BigDecimal) testDAta.get(i).get("TransactionPremium")).intValue() + "");
			alteredDate.add(data);
		}
		Collections.sort(alteredDate, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return ComparisonChain.start().compare(o1.get("CoverageApplicability"), o2.get("CoverageApplicability"))
						.compare(o1.get("CovDsc"), o2.get("CovDsc"))
						.compare(o1.get("CovPerilDsc"), o2.get("CovPerilDsc")).result();
			}
		});
		return alteredDate;
	}

	public List<Map<String, String>> getLocationLevelBinderData(List<Map<String, String>> testDAta) {
		List<Map<String, String>> alteredDate = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			if (testDAta.get(i).get("CoverageAppliesBy").equalsIgnoreCase("Location")) {
				alteredDate.add(testDAta.get(i));
			}
		}
		Collections.sort(alteredDate, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return o1.get("CoverageApplicability").toString().compareTo(o2.get("CoverageApplicability").toString());
			}
		});
		return alteredDate;
	}

	public List<Map<String, String>> getAPCLevelBinderData(List<Map<String, String>> testDAta) {
		List<Map<String, String>> alteredDate = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			if (testDAta.get(i).get("CoverageAppliesBy").contains("APC")) {
				alteredDate.add(testDAta.get(i));
			}
		}
		Collections.sort(alteredDate, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return o1.get("CoverageApplicability").toString().compareTo(o2.get("CoverageApplicability").toString());
			}
		});
		return alteredDate;
	}

	public List<Map<String, String>> getBuildingLevelBinderData(List<Map<String, String>> testDAta) {
		List<Map<String, String>> alteredDate = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			if (testDAta.get(i).get("CoverageAppliesBy").contains("Building")) {
				alteredDate.add(testDAta.get(i));
			}
		}
		Collections.sort(alteredDate, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return ComparisonChain.start().compare(o1.get("CoverageApplicability"), o2.get("CoverageApplicability"))
						.compare(o1.get("CovDsc"), o2.get("CovDsc"))
						.compare(o1.get("CovPerilDsc"), o2.get("CovPerilDsc")).result();
			}
		});
		return alteredDate;
	}

	public String verifyCoverageDetails(List<Map<String, String>> binderDetails, String policyNumber) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		List<Map<String, Object>> deductibleData = getCoverageDetails(policyNumber);
		List<Map<String, String>> binderLocationLevelDate = getLocationLevelBinderData(binderDetails);
		List<Map<String, String>> binderAPCLevelDate = getAPCLevelBinderData(binderDetails);
		List<Map<String, String>> binderBuildingLevelDAta = getBuildingLevelBinderData(binderDetails);
		if (binderLocationLevelDate.size() > 0) {
			List<Map<String, Object>> dbLocationLevelDate = getDBLocationLevelData(deductibleData);
			Map<String, String> totalLocationPremium = getTotalLocationPremium(policyNumber);
			for (int i = 0; i < dbLocationLevelDate.size(); i++) {
				Assertions
						.addInfo(
								"<span class='group'> Dwelling :"
										+ binderLocationLevelDate.get(i).get("CoverageApplicability") + " </span>",
								"<span class='group'> GROUPING</span>");
				Assertions.verify(dbLocationLevelDate.get(i).get("CovPerilDsc"),
						binderLocationLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + dbLocationLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + binderLocationLevelDate.get(i).get("CovPerilDsc"), false,
						false);
				Assertions.verify(dbLocationLevelDate.get(i).get("Limit"), binderLocationLevelDate.get(i).get("Limit"),
						"Limit :" + format.format(Integer.parseInt(dbLocationLevelDate.get(i).get("Limit").toString())),
						"Limit :" + format.format(Integer.parseInt(binderLocationLevelDate.get(i).get("Limit"))), false,
						false);
				Assertions.verify(dbLocationLevelDate.get(i).get("TransactionPremium"),
						binderLocationLevelDate.get(i).get("TransactionPremium"),
						"Transaction Premium :" + format.format(
								Integer.parseInt(dbLocationLevelDate.get(i).get("TransactionPremium").toString())),
						"Transaction Premium :" + format
								.format(Integer.valueOf(binderLocationLevelDate.get(i).get("TransactionPremium"))),
						false, false);
				Assertions.verify(totalLocationPremium.get(binderLocationLevelDate.get(i).get("CoverageApplicability")),
						binderLocationLevelDate.get(i).get("TotalLocationPremium"),
						"Total Location Premium :" + format.format(Integer.parseInt(totalLocationPremium
								.get(binderLocationLevelDate.get(i).get("CoverageApplicability")).toString())),
						"Total Location Premium :" + format
								.format(Integer.valueOf(binderLocationLevelDate.get(i).get("TotalLocationPremium"))),
						false, false);
				Assertions.verify(dbLocationLevelDate.get(i).get("CoverageAppliesBy"),
						binderLocationLevelDate.get(i).get("CoverageAppliesBy"),
						"Coverage Applies By :" + dbLocationLevelDate.get(i).get("CoverageAppliesBy"),
						"Coverage Applies By :" + binderLocationLevelDate.get(i).get("CoverageAppliesBy"), false,
						false);
				Assertions.verify(dbLocationLevelDate.get(i).get("CoverageApplicability"),
						binderLocationLevelDate.get(i).get("CoverageApplicability"),
						"Coverage Applicability :" + dbLocationLevelDate.get(i).get("CoverageApplicability"),
						"Coverage Applicability :" + binderLocationLevelDate.get(i).get("CoverageApplicability"), false,
						false);
				testStatus=Assertions.verify(dbLocationLevelDate.get(i).get("CovDsc"),
						binderLocationLevelDate.get(i).get("CovDsc"),
						"Coverage Description :" + dbLocationLevelDate.get(i).get("CovDsc"),
						"Coverage Description :" + binderLocationLevelDate.get(i).get("CovDsc"), false, false);
			}
		}
		if (binderAPCLevelDate.size() > 0) {
			List<Map<String, Object>> dbAPCLevelDate = getDBAPCLevelData(deductibleData);
			for (int i = 0; i < dbAPCLevelDate.size(); i++) {
				Assertions
						.addInfo(
								"<span class='group'> Dwelling :"
										+ binderAPCLevelDate.get(i).get("CoverageApplicability") + " </span>",
								"<span class='group'> GROUPING</span>");
				Assertions.verify(dbAPCLevelDate.get(i).get("CovPerilDsc"),
						binderAPCLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + dbAPCLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + binderAPCLevelDate.get(i).get("CovPerilDsc"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("Limit"), binderAPCLevelDate.get(i).get("Limit"),
						"Limit :" + format.format(Integer.parseInt(dbAPCLevelDate.get(i).get("Limit").toString())),
						"Limit :" + format.format(Integer.parseInt(binderAPCLevelDate.get(i).get("Limit"))), false,
						false);
				Assertions.verify(dbAPCLevelDate.get(i).get("TransactionPremium"),
						binderAPCLevelDate.get(i).get("TransactionPremium"),
						"Transaction Premium :" + format
								.format(Integer.parseInt(dbAPCLevelDate.get(i).get("TransactionPremium").toString())),
						"Transaction Premium :"
								+ format.format(Integer.valueOf(binderAPCLevelDate.get(i).get("TransactionPremium"))),
						false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CoverageAppliesBy"),
						binderAPCLevelDate.get(i).get("CoverageAppliesBy"),
						"Coverage Applies By :" + dbAPCLevelDate.get(i).get("CoverageAppliesBy"),
						"Coverage Applies By :" + binderAPCLevelDate.get(i).get("CoverageAppliesBy"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CoverageApplicability"),
						binderAPCLevelDate.get(i).get("CoverageApplicability"),
						"Coverage Applicability :" + dbAPCLevelDate.get(i).get("CoverageApplicability"),
						"Coverage Applicability :" + binderAPCLevelDate.get(i).get("CoverageApplicability"), false,
						false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CovDsc"), binderAPCLevelDate.get(i).get("CovDsc"),
						"Coverage Description :" + dbAPCLevelDate.get(i).get("CovDsc"),
						"Coverage Description :" + binderAPCLevelDate.get(i).get("CovDsc"), false, false);
			}
		}
		if (binderBuildingLevelDAta.size() > 0) {
			List<Map<String, String>> dbBuildingLevelDate = getDBBuildingLevelData(deductibleData);
			for (int i = 0; i < dbBuildingLevelDate.size(); i++) {
				try {
					Assertions.addInfo(
							"<span class='group'> Dwelling :"
									+ binderBuildingLevelDAta.get(i).get("CoverageApplicability") + " </span>",
							"<span class='group'> GROUPING</span>");
					Assertions.verify(dbBuildingLevelDate.get(i).get("CovPerilDsc"),
							binderBuildingLevelDAta.get(i).get("CovPerilDsc"),
							"Coverage Peril Description :" + dbBuildingLevelDate.get(i).get("CovPerilDsc"),
							"Coverage Peril Description :" + binderBuildingLevelDAta.get(i).get("CovPerilDsc"), false,
							false);
					Assertions.verify(dbBuildingLevelDate.get(i).get("Limit"),
							binderBuildingLevelDAta.get(i).get("Limit"),
							"Limit :" + format
									.format(Integer.parseInt(dbBuildingLevelDate.get(i).get("Limit").toString())),
							"Limit :" + format.format(Integer.parseInt(binderBuildingLevelDAta.get(i).get("Limit"))),
							false, false);
					Assertions.verify(dbBuildingLevelDate.get(i).get("TransactionPremium"),
							binderBuildingLevelDAta.get(i).get("TransactionPremium"),
							"Transaction Premium :" + format.format(
									Integer.parseInt(dbBuildingLevelDate.get(i).get("TransactionPremium").toString())),
							"Transaction Premium :" + format
									.format(Integer.valueOf(binderBuildingLevelDAta.get(i).get("TransactionPremium"))),
							false, false);
					Assertions.verify(dbBuildingLevelDate.get(i).get("CoverageAppliesBy"),
							binderBuildingLevelDAta.get(i).get("CoverageAppliesBy"),
							"Coverage Applies By :" + dbBuildingLevelDate.get(i).get("CoverageAppliesBy"),
							"Coverage Applies By :" + binderBuildingLevelDAta.get(i).get("CoverageAppliesBy"), false,
							false);
					Assertions.verify(dbBuildingLevelDate.get(i).get("CoverageApplicability"),
							binderBuildingLevelDAta.get(i).get("CoverageApplicability"),
							"Coverage Applicability :" + dbBuildingLevelDate.get(i).get("CoverageApplicability"),
							"Coverage Applicability :" + binderBuildingLevelDAta.get(i).get("CoverageApplicability"),
							false, false);
					testStatus=Assertions.verify(dbBuildingLevelDate.get(i).get("CovDsc"),
							binderBuildingLevelDAta.get(i).get("CovDsc"),
							"Coverage Description :" + dbBuildingLevelDate.get(i).get("CovDsc"),
							"Coverage Description :" + binderBuildingLevelDAta.get(i).get("CovDsc"), false, false);
				} catch (Exception e) {
					Assertions
							.addInfo(
									"<span class='group'> Dwelling :"
											+ dbBuildingLevelDate.get(i).get("CoverageApplicability") + " </span>",
									"<span class='group'> GROUPING</span>");
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Coverage Peril Description :" + dbBuildingLevelDate.get(i).get("CovPerilDsc"), "", false,
							false);
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Limit :" + format
									.format(Integer.parseInt(dbBuildingLevelDate.get(i).get("Limit").toString())),
							"", false, false);
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Transaction Premium :" + format.format(
									Integer.parseInt(dbBuildingLevelDate.get(i).get("TransactionPremium").toString())),
							"", false, false);
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Coverage Applies By :" + dbBuildingLevelDate.get(i).get("CoverageAppliesBy"), "", false,
							false);
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Coverage Applicability :" + dbBuildingLevelDate.get(i).get("CoverageApplicability"), "",
							false, false);
					testStatus=Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Coverage Description :" + dbBuildingLevelDate.get(i).get("CovDsc"), "", false, false);
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

	public void verifyCoverageDetailsforPNB(List<Map<String, String>> binderDetails, String policyNumber,
			int hvn, int transactionNumber, Map<String, String> pnbdataTest) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		List<Map<String, Object>> deductibleData = getCoverageDetailsforPNB(policyNumber, hvn);
		List<Map<String, String>> binderLocationLevelDate = getLocationLevelBinderData(binderDetails);
		List<Map<String, String>> binderAPCLevelDate = getAPCLevelBinderData(binderDetails);
		List<Map<String, String>> binderBuildingLevelDAta = getBuildingLevelBinderData(binderDetails);
		if (binderLocationLevelDate.size() > 0) {
			List<Map<String, Object>> dbLocationLevelDate = getDBLocationLevelData(deductibleData);
			Map<String, String> totalLocationPremium = getTotalLocationPremiumforPNB(policyNumber, hvn);
			for (int i = 0; i < dbLocationLevelDate.size(); i++) {
				Assertions
						.addInfo(
								"<span class='group'> Dwelling :"
										+ binderLocationLevelDate.get(i).get("CoverageApplicability") + " </span>",
								"<span class='group'> GROUPING</span>");
				Assertions.verify(dbLocationLevelDate.get(i).get("CovPerilDsc"),
						binderLocationLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + dbLocationLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + binderLocationLevelDate.get(i).get("CovPerilDsc"), false,
						false);
				Assertions.verify(dbLocationLevelDate.get(i).get("Limit"), binderLocationLevelDate.get(i).get("Limit"),
						"Limit :" + format.format(Integer.parseInt(dbLocationLevelDate.get(i).get("Limit").toString())),
						"Limit :" + format.format(Integer.parseInt(binderLocationLevelDate.get(i).get("Limit"))), false,
						false);
				Assertions.verify(dbLocationLevelDate.get(i).get("TransactionPremium"),
						binderLocationLevelDate.get(i).get("TransactionPremium"),
						"Transaction Premium :" + format.format(
								Integer.parseInt(dbLocationLevelDate.get(i).get("TransactionPremium").toString())),
						"Transaction Premium :" + format
								.format(Integer.valueOf(binderLocationLevelDate.get(i).get("TransactionPremium"))),
						false, false);
				Assertions.verify(totalLocationPremium.get(binderLocationLevelDate.get(i).get("CoverageApplicability")),
						binderLocationLevelDate.get(i).get("TotalLocationPremium"),
						"Total Location Premium :" + format.format(Integer.parseInt(totalLocationPremium
								.get(binderLocationLevelDate.get(i).get("CoverageApplicability")).toString())),
						"Total Location Premium :" + format
								.format(Integer.valueOf(binderLocationLevelDate.get(i).get("TotalLocationPremium"))),
						false, false);
				Assertions.verify(dbLocationLevelDate.get(i).get("CoverageAppliesBy"),
						binderLocationLevelDate.get(i).get("CoverageAppliesBy"),
						"Coverage Applies By :" + dbLocationLevelDate.get(i).get("CoverageAppliesBy"),
						"Coverage Applies By :" + binderLocationLevelDate.get(i).get("CoverageAppliesBy"), false,
						false);
				Assertions.verify(dbLocationLevelDate.get(i).get("CoverageApplicability"),
						binderLocationLevelDate.get(i).get("CoverageApplicability"),
						"Coverage Applicability :" + dbLocationLevelDate.get(i).get("CoverageApplicability"),
						"Coverage Applicability :" + binderLocationLevelDate.get(i).get("CoverageApplicability"), false,
						false);
				Assertions.verify(dbLocationLevelDate.get(i).get("CovDsc"),
						binderLocationLevelDate.get(i).get("CovDsc"),
						"Coverage Description :" + dbLocationLevelDate.get(i).get("CovDsc"),
						"Coverage Description :" + binderLocationLevelDate.get(i).get("CovDsc"), false, false);
			}
		}
		if (binderAPCLevelDate.size() > 0) {
			List<Map<String, Object>> dbAPCLevelDate = getDBAPCLevelData(deductibleData);
			for (int i = 0; i < dbAPCLevelDate.size(); i++) {
				Assertions
						.addInfo(
								"<span class='group'> Dwelling :"
										+ binderAPCLevelDate.get(i).get("CoverageApplicability") + " </span>",
								"<span class='group'> GROUPING</span>");
				Assertions.verify(dbAPCLevelDate.get(i).get("CovPerilDsc"),
						binderAPCLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + dbAPCLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + binderAPCLevelDate.get(i).get("CovPerilDsc"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("Limit"), binderAPCLevelDate.get(i).get("Limit"),
						"Limit :" + format.format(Integer.parseInt(dbAPCLevelDate.get(i).get("Limit").toString())),
						"Limit :" + format.format(Integer.parseInt(binderAPCLevelDate.get(i).get("Limit"))), false,
						false);
				Assertions.verify(dbAPCLevelDate.get(i).get("TransactionPremium"),
						binderAPCLevelDate.get(i).get("TransactionPremium"),
						"Transaction Premium :" + format
								.format(Integer.parseInt(dbAPCLevelDate.get(i).get("TransactionPremium").toString())),
						"Transaction Premium :"
								+ format.format(Integer.valueOf(binderAPCLevelDate.get(i).get("TransactionPremium"))),
						false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CoverageAppliesBy"),
						binderAPCLevelDate.get(i).get("CoverageAppliesBy"),
						"Coverage Applies By :" + dbAPCLevelDate.get(i).get("CoverageAppliesBy"),
						"Coverage Applies By :" + binderAPCLevelDate.get(i).get("CoverageAppliesBy"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CoverageApplicability"),
						binderAPCLevelDate.get(i).get("CoverageApplicability"),
						"Coverage Applicability :" + dbAPCLevelDate.get(i).get("CoverageApplicability"),
						"Coverage Applicability :" + binderAPCLevelDate.get(i).get("CoverageApplicability"), false,
						false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CovDsc"), binderAPCLevelDate.get(i).get("CovDsc"),
						"Coverage Description :" + dbAPCLevelDate.get(i).get("CovDsc"),
						"Coverage Description :" + binderAPCLevelDate.get(i).get("CovDsc"), false, false);
			}
		}
		List<Map<String, String>> dbBuildingLevelDate = getDBBuildingLevelData(deductibleData);
		if (dbBuildingLevelDate.size() > 0) {
			for (int i = 0; i < dbBuildingLevelDate.size(); i++) {
				if (!dbBuildingLevelDate.get(i).get("DeleteTransactionid").equals("0")) {
					Assertions.addInfo("Coverage Description :" + dbBuildingLevelDate.get(i).get("CovPerilDsc"),
							"Deleted Transaction id : " + dbBuildingLevelDate.get(i).get("DeleteTransactionid"));
				} else {
					if ((binderBuildingLevelDAta.get(i).get("CovDsc").contains("Building/Structure") && !pnbdataTest
							.get(binderBuildingLevelDAta.get(i).get("CoverageApplicability") + "-DwellingCovA")
							.equals(""))
							|| (binderBuildingLevelDAta.get(i).get("CovDsc").contains("Homeowners Other Structures")
									&& !pnbdataTest.get(binderBuildingLevelDAta.get(i).get("CoverageApplicability")
											+ "-DwellingCovB").equals(""))
							|| (binderBuildingLevelDAta.get(i).get("CovDsc")
									.contains("Business Personal Property/Contents")
									&& !pnbdataTest
											.get(binderBuildingLevelDAta.get(i).get("CoverageApplicability")
													+ "-DwellingCovC")
											.equals(""))
							|| (binderBuildingLevelDAta.get(i).get("CovDsc")
									.contains("Business Income/Extra Expense/Business Interruption")
									&& !pnbdataTest.get(binderBuildingLevelDAta.get(i).get("CoverageApplicability")
											+ "-DwellingCovD").equals(""))) {
						Assertions.addInfo(
								"<span class='group'> Dwelling :"
										+ binderBuildingLevelDAta.get(i).get("CoverageApplicability") + " </span>",
								"<span class='group'> GROUPING</span>");
						Assertions.verify(dbBuildingLevelDate.get(i).get("CovPerilDsc"),
								binderBuildingLevelDAta.get(i).get("CovPerilDsc"),
								"Coverage Peril Description :" + dbBuildingLevelDate.get(i).get("CovPerilDsc"),
								"Coverage Peril Description :" + binderBuildingLevelDAta.get(i).get("CovPerilDsc"),
								false, false);
						Assertions.verify(dbBuildingLevelDate.get(i).get("Limit"),
								binderBuildingLevelDAta.get(i).get("Limit"),
								"Limit :" + format
										.format(Integer.parseInt(dbBuildingLevelDate.get(i).get("Limit").toString())),
								"Limit :"
										+ format.format(Integer.parseInt(binderBuildingLevelDAta.get(i).get("Limit"))),
								false, false);
						Assertions.verify(dbBuildingLevelDate.get(i).get("TransactionPremium"),
								binderBuildingLevelDAta.get(i).get("TransactionPremium"),
								"Transaction Premium :" + format.format(Integer
										.parseInt(dbBuildingLevelDate.get(i).get("TransactionPremium").toString())),
								"Transaction Premium :" + format.format(
										Integer.valueOf(binderBuildingLevelDAta.get(i).get("TransactionPremium"))),
								false, false);
						Assertions.verify(dbBuildingLevelDate.get(i).get("CoverageAppliesBy"),
								binderBuildingLevelDAta.get(i).get("CoverageAppliesBy"),
								"Coverage Applies By :" + dbBuildingLevelDate.get(i).get("CoverageAppliesBy"),
								"Coverage Applies By :" + binderBuildingLevelDAta.get(i).get("CoverageAppliesBy"),
								false, false);
						Assertions.verify(dbBuildingLevelDate.get(i).get("CoverageApplicability"),
								binderBuildingLevelDAta.get(i).get("CoverageApplicability"),
								"Coverage Applicability :" + dbBuildingLevelDate.get(i).get("CoverageApplicability"),
								"Coverage Applicability :"
										+ binderBuildingLevelDAta.get(i).get("CoverageApplicability"),
								false, false);
						Assertions.verify(dbBuildingLevelDate.get(i).get("CovDsc"),
								binderBuildingLevelDAta.get(i).get("CovDsc"),
								"Coverage Description :" + dbBuildingLevelDate.get(i).get("CovDsc"),
								"Coverage Description :" + binderBuildingLevelDAta.get(i).get("CovDsc"), false, false);
					}
					if ((binderBuildingLevelDAta.get(i).get("CovDsc").contains("Building/Structure") && pnbdataTest
							.get(binderBuildingLevelDAta.get(i).get("CoverageApplicability") + "-DwellingCovA")
							.equals(""))
							|| (binderBuildingLevelDAta.get(i).get("CovDsc").contains("Homeowners Other Structures")
									&& pnbdataTest.get(binderBuildingLevelDAta.get(i).get("CoverageApplicability")
											+ "-DwellingCovB").equals(""))
							|| (binderBuildingLevelDAta.get(i).get("CovDsc")
									.contains("Business Personal Property/Contents")
									&& pnbdataTest.get(binderBuildingLevelDAta.get(i).get("CoverageApplicability")
											+ "-DwellingCovC").equals(""))
							|| (binderBuildingLevelDAta.get(i).get("CovDsc")
									.contains("Business Income/Extra Expense/Business Interruption")
									&& pnbdataTest.get(binderBuildingLevelDAta.get(i).get("CoverageApplicability")
											+ "-DwellingCovD").equals(""))) {
						Assertions.addInfo(
								"<span class='group'> Dwelling :"
										+ binderBuildingLevelDAta.get(i).get("CoverageApplicability") + " </span>",
								"<span class='group'> GROUPING</span>");
						Assertions.addInfo(
								"Coverage Peril Description :" + dbBuildingLevelDate.get(i).get("CovPerilDsc"),
								"Coverage Peril Description :" + binderBuildingLevelDAta.get(i).get("CovPerilDsc"));
						Assertions.addInfo(
								"Limit :" + format
										.format(Integer.parseInt(dbBuildingLevelDate.get(i).get("Limit").toString())),
								"Limit :"
										+ format.format(Integer.parseInt(binderBuildingLevelDAta.get(i).get("Limit"))));
						Assertions.addInfo(
								"Transaction Premium :" + format.format(Integer
										.parseInt(dbBuildingLevelDate.get(i).get("TransactionPremium").toString())),
								"Transaction Premium :" + format.format(
										Integer.valueOf(binderBuildingLevelDAta.get(i).get("TransactionPremium"))));
						Assertions.addInfo(
								"Coverage Applies By :" + dbBuildingLevelDate.get(i).get("CoverageAppliesBy"),
								"Coverage Applies By :" + binderBuildingLevelDAta.get(i).get("CoverageAppliesBy"));
						Assertions.addInfo(
								"Coverage Applicability :" + dbBuildingLevelDate.get(i).get("CoverageApplicability"),
								"Coverage Applicability :"
										+ binderBuildingLevelDAta.get(i).get("CoverageApplicability"));
						Assertions.addInfo("Coverage Description :" + dbBuildingLevelDate.get(i).get("CovDsc"),
								"Coverage Description :" + binderBuildingLevelDAta.get(i).get("CovDsc"));
					}
				}
			}
		}
	}

	public Map<String, String> getBuildingTotalBuildingPremium(String policyNumberData) {
		List<String> outFields = new ArrayList<>();
		Map<String, String> transactionPremiumData = new HashMap<>();
		outFields.add("sum(" + transactionPremium + ") as TotalBuildingPremium");
		outFields.add(policyBuildingId);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = '" + policyNumberData + "' and " + policyBuildingId + ">0");
		build.groupBy(policyBuildingId);
		List<Map<String, Object>> sumdetails = build.execute(60);
		int size = sumdetails.size();
		for (int i = 0; i < size; i++) {
			sumdetails.get(i).put("CoverageApplicability",
					building.getBuildingAddr2((Integer) sumdetails.get(i).get(policyBuildingId)));
		}
		for (int i = 0; i < size; i++) {
			transactionPremiumData.put(sumdetails.get(i).get("CoverageApplicability").toString(),
					((BigDecimal) sumdetails.get(i).get("TotalBuildingPremium")).intValue() + "");
		}
		return transactionPremiumData;
	}

	public Map<String, String> getBuildingTotalBuildingPremiumforPNB(String policyNumberData, int hvn) {
		List<String> outFields = new ArrayList<>();
		Map<String, String> transactionPremiumData = new HashMap<>();
		outFields.add("sum(" + transactionPremium + ") as TotalBuildingPremium");
		outFields.add(policyBuildingId);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = '" + policyNumberData + "' and " + policyBuildingId + ">0" + " and " + Hvn
				+ " =" + hvn);
		build.groupBy(policyBuildingId);
		List<Map<String, Object>> sumdetails = build.execute(60);
		int size = sumdetails.size();
		for (int i = 0; i < size; i++) {
			sumdetails.get(i).put("CoverageApplicability",
					building.getBuildingAddr2((Integer) sumdetails.get(i).get(policyBuildingId)));
		}
		for (int i = 0; i < size; i++) {
			transactionPremiumData.put(sumdetails.get(i).get("CoverageApplicability").toString(),
					((BigDecimal) sumdetails.get(i).get("TotalBuildingPremium")).intValue() + "");
		}
		return transactionPremiumData;
	}

	public Map<String, String> getTotalLocationPremium(String policyNumberData) {
		List<String> outFields = new ArrayList<>();
		Map<String, String> transactionPremiumData = new HashMap<>();
		outFields.add("sum(" + transactionPremium + ") as TotalLocationPremium");
		outFields.add(policyLocationId);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = '" + policyNumberData + "'");
		build.groupBy(policyLocationId);
		List<Map<String, Object>> sumdetails = build.execute(60);
		int size = sumdetails.size();
		for (int i = 0; i < size; i++) {
			sumdetails.get(i).put("CoverageApplicability",
					"L" + location.getLocationNumber((Integer) sumdetails.get(i).get(policyLocationId)));
		}
		for (int i = 0; i < size; i++) {
			transactionPremiumData.put(sumdetails.get(i).get("CoverageApplicability").toString(),
					((BigDecimal) sumdetails.get(i).get("TotalLocationPremium")).intValue() + "");
		}
		return transactionPremiumData;
	}

	public Map<String, String> getTotalLocationPremiumforPNB(String policyNumberData, int hvn) {
		List<String> outFields = new ArrayList<>();
		Map<String, String> transactionPremiumData = new HashMap<>();
		outFields.add("sum(" + transactionPremium + ") as TotalLocationPremium");
		outFields.add(policyLocationId);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = '" + policyNumberData + "'" + " and " + Hvn + " =" + hvn);
		build.groupBy(policyLocationId);
		List<Map<String, Object>> sumdetails = build.execute(60);
		int size = sumdetails.size();
		for (int i = 0; i < size; i++) {
			sumdetails.get(i).put("CoverageApplicability",
					"L" + location.getLocationNumber((Integer) sumdetails.get(i).get(policyLocationId)));
		}
		for (int i = 0; i < size; i++) {
			transactionPremiumData.put(sumdetails.get(i).get("CoverageApplicability").toString(),
					((BigDecimal) sumdetails.get(i).get("TotalLocationPremium")).intValue() + "");
		}
		return transactionPremiumData;
	} */
}
