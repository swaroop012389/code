/** Program Description: Methods and queries built up for LocDeductible table
 *  Author			   : SMNetserv
 *  Date of Creation   : 12/02/2018
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

public class LOCDeductible {
	public String locDeductible;
	public String transactionId;
	public String covId;
	public String covPerilId;
	public String dedId;
	public String dedTypeId;
	public String deductible;
	public String policySpecialClassId;
	public String deleteTransactionId;
	public String dedOccurrenceId;
	public String calculatedDeductible;
	public String policyLocationId;

	/*RESIDENTIAL PARAMETERS
	public String policyNumber;
	public String policyLocationId;
	public String policyBuildingId;
	public String coverageId;
	public String coveragePerilId;
	public String deductibleId;
	public String deductibleTypeId;
	public String deductibleOccurrenceId;
	public String hvn;
	Location loc;
	Coverage cov;
	CovPeril cp;
	Deductible ded;
	DedType dt;
	ConsolidatedCodeTable cc;
	SpecialClass sc;

	 */

	public DBFrameworkConnection connection;
	public QueryBuilder build;
	public Map<String, Object> results;

	private static List<Integer> testStatus;

	public LOCDeductible(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("LOCDeductible");
		locDeductible = properties.getProperty("db_LocDeductibleTable");
		covId = properties.getProperty("db_CovId");
		covPerilId = properties.getProperty("db_CovPerilId");
		dedId = properties.getProperty("db_DedId");
		dedTypeId = properties.getProperty("db_DedTypeId");
		deductible = properties.getProperty("db_Deductible");
		policySpecialClassId = properties.getProperty("db_PolicySpecialClassId");
		deleteTransactionId = properties.getProperty("db_DeleteTransactionId");
		dedOccurrenceId = properties.getProperty("db_DedOccurrenceId");
		calculatedDeductible = properties.getProperty("db_CalculatedDeductible");
		policyLocationId = properties.getProperty("db_PolicyLocationId");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(locDeductible);

	}

	public List<Map<String, Object>> getLOCDeductibleDetails(int transactionId) {
		List<String> outFields = new ArrayList<>();
		outFields.add(covId);
		outFields.add(covPerilId);
		outFields.add(dedId);
		outFields.add(dedTypeId);
		outFields.add(deductible);
		outFields.add(policySpecialClassId);
		outFields.add(deleteTransactionId);
		outFields.add(dedOccurrenceId);
		outFields.add(calculatedDeductible);
		outFields.add(policyLocationId);

		build.outFields(outFields);
		build.whereBy("transactionId = " + transactionId + " order by PolicyLocationId, CovId, CovPerilId, DedId, DedTypeId, CalculatedDeductible");
		List<Map<String, Object>> locDeductibleDetails = build.execute(60);
		return locDeductibleDetails;
	}


	public List<Map<String, String>> getLOCDeductibleDataExpected(String tcid) {
		List<Map<String, String>> binderLOCDeductibleData = new ArrayList<>();

		//splitting out LOCDeductible expected values into 12 spreadsheet tabs because the data is so big
		int testNum = Integer.parseInt(tcid.split("_")[0].replace("NBTC", ""));

		if (testNum <= 12) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible1").readExcelRowWise();
		} else if (12 <= testNum && testNum <= 22) {
				binderLOCDeductibleData = new SheetMatchedAccessManager(
						"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
						"LOCDeductible2").readExcelRowWise();
		} else if (23 <= testNum && testNum <= 30) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible3").readExcelRowWise();
		} else if (31 <= testNum && testNum <= 33) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible4").readExcelRowWise();
		} else if (34 <= testNum && testNum <= 40) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible5").readExcelRowWise();
		} else if (41 <= testNum && testNum <= 66) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible6").readExcelRowWise();
		} else if (67 <= testNum && testNum <= 75) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible7").readExcelRowWise();
		} else if (76 <= testNum && testNum <= 89) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible8").readExcelRowWise();
		} else if (90 <= testNum && testNum <= 110) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible9").readExcelRowWise();
		} else if (111 <= testNum && testNum <= 116) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible10").readExcelRowWise();
		} else if (117 <= testNum && testNum <= 123) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible11").readExcelRowWise();
		} else if (testNum > 123) {
			binderLOCDeductibleData = new SheetMatchedAccessManager(
					"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
					"LOCDeductible12").readExcelRowWise();
		}

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : binderLOCDeductibleData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);
			}
		}

		return TCData;

	}

	public String verifyLOCDeductibleData(List<Map<String, String>> binderData, int transactionId) {
		DecimalFormat threeDigits = new DecimalFormat("#.###");

		List<Map<String, Object>> aftershockValues = getLOCDeductibleDetails(transactionId);
		for (int i = 0; i < binderData.size(); i++) {
			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = aftershockValues.get(i);

			//System.out.println("LocDeductible binderDataRow = " + binderDataRow);
			//System.out.println("LocDeductible aftershockRow = " + aftershockRow);

			testStatus = Assertions.verify(aftershockRow.get("CovId").toString(), binderDataRow.get("CovId"), "CovId : " + aftershockRow.get("CovId").toString(), "Expected : " + binderDataRow.get("CovId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("CovPerilId").toString(), binderDataRow.get("CovPerilId"), "CovPerilId : " + aftershockRow.get("CovPerilId").toString(), "Expected : " + binderDataRow.get("CovPerilId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("DedId").toString(), binderDataRow.get("DedId"), "DedId : " + aftershockRow.get("DedId").toString(), "Expected : " + binderDataRow.get("DedId"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("DedTypeId").toString(), binderDataRow.get("DedTypeId"), "DedTypeId : " + aftershockRow.get("DedTypeId").toString(), "Expected : " + binderDataRow.get("DedTypeId"), false, false);

			double asDed = Double.parseDouble(String.valueOf(aftershockRow.get("Deductible")));
			double expectedDed = Double.parseDouble(String.valueOf(binderDataRow.get("Deductible")));
			testStatus = Assertions.verify(threeDigits.format(asDed), threeDigits.format(expectedDed), "Deductible : " + threeDigits.format(asDed), "Expected : " + threeDigits.format(expectedDed), false, false);
			String expectedDeleteTxnId;
			if (binderDataRow.get("DeleteTransactionId").toString().equals("0")) {
				expectedDeleteTxnId = "0";
			} else {
				expectedDeleteTxnId = String.valueOf(transactionId);
			}
			testStatus = Assertions.verify(aftershockRow.get("DeleteTransactionId").toString(), expectedDeleteTxnId, "DeleteTransactionId : " + aftershockRow.get("DeleteTransactionId").toString(), "Expected : " + expectedDeleteTxnId, false, false);
			testStatus = Assertions.verify(aftershockRow.get("DedOccurrenceId").toString(), binderDataRow.get("DedOccurrenceId"), "DedOccurrenceId : " + aftershockRow.get("DedOccurrenceId").toString(), "Expected : " + binderDataRow.get("DedOccurrenceId"), false, false);

			double asCalcDed = 0.000;
			double expectedCalcDed = 0.000;
			if (!binderDataRow.get("CalculatedDeductible").equals("NULL")) {
				asCalcDed = Double.parseDouble(String.valueOf(aftershockRow.get("CalculatedDeductible")));
				expectedCalcDed = Double.parseDouble(String.valueOf(binderDataRow.get("CalculatedDeductible")));
			}

			testStatus = Assertions.verify(threeDigits.format(asCalcDed), threeDigits.format(expectedCalcDed), "CalculatedDeductible : " + threeDigits.format(asCalcDed), "Expected : " + threeDigits.format(expectedCalcDed), false, false);

		}

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}


	/* RESIDENTIAL METHODS
		public LocDeductible(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("LocationDeductible");
		tableName = properties.getProperty("db_TableName");
		policyNumber = properties.getProperty("db_PolicyNumber");
		policyLocationId = properties.getProperty("db_PolicyLocationId");
		policyBuildingId = properties.getProperty("db_PolicyBuildingId");
		policySpecialClassId = properties.getProperty("db_PolicySpecialClassId");
		coverageId = properties.getProperty("db_CoverageId");
		coveragePerilId = properties.getProperty("db_CoveragePerilId");
		deductibleId = properties.getProperty("db_DeductibleId");
		deductibleTypeId = properties.getProperty("db_DeductibleTypeId");
		deductible = properties.getProperty("db_Deductible");
		deductibleOccurrenceId = properties.getProperty("db_DeductibleOccurrenceId");
		calculatedDeductible = properties.getProperty("db_CalculatedDeductible");
		deleteTransactionId = properties.getProperty("db_DeleteTransactionId");
		hvn = properties.getProperty("db_Hvn");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
		loc = new Location(dbConfig);
		cov = new Coverage(dbConfig);
		cp = new CovPeril(dbConfig);
		ded = new Deductible(dbConfig);
		dt = new DedType(dbConfig);
		cc = new ConsolidatedCodeTable(dbConfig);
		sc = new SpecialClass(dbConfig);
	}

	public List<Map<String, Object>> getDeductibleDetails(String policynumber) {
		List<String> outFields = new ArrayList<>();
		outFields.add(policyNumber);
		outFields.add(policyLocationId);
		outFields.add(policyBuildingId);
		outFields.add(policySpecialClassId);
		outFields.add(coverageId);
		outFields.add(coveragePerilId);
		outFields.add(deductibleId);
		outFields.add(deductibleTypeId);
		outFields.add(deductible);
		outFields.add(deductibleOccurrenceId);
		outFields.add(calculatedDeductible);
		outFields.add(deleteTransactionId);
		build.outFields(outFields);
		build.whereBy(policyNumber + " = " + "'" + policynumber + "'");
		List<Map<String, Object>> deductibleDetails = build.execute(60);
		return deductibleDetails;
	}

	public List<Map<String, Object>> getDeductibleDetailsPNB(String policynumber, int hvnNumber) {
		List<String> outFields = new ArrayList<>();
		outFields.add(policyNumber);
		outFields.add(policyLocationId);
		outFields.add(policyBuildingId);
		outFields.add(policySpecialClassId);
		outFields.add(coverageId);
		outFields.add(coveragePerilId);
		outFields.add(deductibleId);
		outFields.add(deductibleTypeId);
		outFields.add(deductible);
		outFields.add(deductibleOccurrenceId);
		outFields.add(calculatedDeductible);
		outFields.add(hvn);
		outFields.add(deleteTransactionId);
		build.outFields(outFields);
		build.whereBy(hvn + " = " + hvnNumber + " and " + policyNumber + " = " + "'" + policynumber + "'");
		List<Map<String, Object>> deductibleDetails = build.execute(60);
		return deductibleDetails;
	}

	public List<Map<String, String>> getDBLocationLevelData(List<Map<String, Object>> testDAta) {
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
		List<Map<String, String>> alteredDate = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			Map<String, String> data = new HashMap<>();
			data.put("DeductibleAppliesBy", "Location");
			data.put("DeleteTransactionid", testDAta.get(i).get("deleteTransactionid") + "");
			data.put("DeductibleApplicability",
					"L" + loc.getLocationNumber((Integer) testDAta.get(i).get("PolicyLocationId")));
			data.put("CoverageDesc", cov.getCoverageDesc((Integer) testDAta.get(i).get("Covid")));
			data.put("CovPerilDsc", cp.getPerilDesc((Integer) testDAta.get(i).get("CovPerilId")));
			data.put("DedDsc", ded.getDeductibleDesc((Integer) testDAta.get(i).get("DedId")));
			data.put("CalculateDeductible", testDAta.get(i).get(calculatedDeductible) + "");
			data.put("DedTypeDsc", dt.getDeductibleTypeDsc(((Short) testDAta.get(i).get("DedTypeId")).intValue()));
			double deductible = ((BigDecimal) testDAta.get(i).get("Deductible")).doubleValue();
			if (deductible < 1) {
				data.put("Deductible", (int) (deductible * 100) + "%");
			} else {
				data.put("Deductible", (int) (deductible) + "");
			}
			data.put("DeductibleOccurence",
					cc.getConsolidatedCodeDesc((Integer) testDAta.get(i).get("DedOccurrenceId")));
			alteredDate.add(data);
		}
		Collections.sort(alteredDate, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return ComparisonChain.start()
						.compare(o1.get("DeductibleApplicability"), o2.get("DeductibleApplicability"))
						.compare(o1.get("DedDsc"), o2.get("DedDsc")).compare(o1.get("DedTypeDsc"), o2.get("DedTypeDsc"))
						.result();
			}
		});
		return alteredDate;
	}*/

	/*public List<Map<String, Object>> getDBAPCLevelData(List<Map<String, Object>> testDAta) {
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
			data.put("DeductibleAppliesBy",
					"APC-L" + loc.getLocationNumber((Integer) testDAta.get(i).get("PolicyLocationId")));
			data.put("DeductibleApplicability",
					sc.getSpecialClassName((Integer) testDAta.get(i).get("PolicySpecialClassID")));
			data.put("CoverageDesc", cov.getCoverageDesc((Integer) testDAta.get(i).get("Covid")));
			data.put("CovPerilDsc", cp.getPerilDesc((Integer) testDAta.get(i).get("CovPerilId")));
			data.put("DedDsc", ded.getDeductibleDesc((Integer) testDAta.get(i).get("DedId")));
			data.put("DedTypeDsc", dt.getDeductibleTypeDsc(((Short) testDAta.get(i).get("DedTypeId")).intValue()));
			double deductible = ((BigDecimal) testDAta.get(i).get("Deductible")).doubleValue();
			if (deductible < 1) {
				data.put("Deductible", (int) (deductible * 100) + "%");
			} else {
				data.put("Deductible", (int) (deductible) + "");
			}
			data.put("DeductibleOccurence",
					cc.getConsolidatedCodeDesc((Integer) testDAta.get(i).get("DedOccurrenceId")));
			alteredDate.add(data);
		}
		return alteredDate;
	}*/

	/*public List<Map<String, String>> getLocationLevelBinderData(List<Map<String, String>> testDAta) {
		List<Map<String, String>> alteredDate = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			if (testDAta.get(i).get("DeductibleAppliesBy").equalsIgnoreCase("Location")) {
				alteredDate.add(testDAta.get(i));
			}
		}
		Collections.sort(alteredDate, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return ComparisonChain.start()
						.compare(o1.get("DeductibleApplicability"), o2.get("DeductibleApplicability"))
						.compare(o1.get("DedDsc"), o2.get("DedDsc")).compare(o1.get("DedTypeDsc"), o2.get("DedTypeDsc"))
						.result();
			}
		});
		return alteredDate;
	}*/

	/*public List<Map<String, String>> getAPCLevelBinderData(List<Map<String, String>> testDAta) {
		List<Map<String, String>> alteredDate = new ArrayList<>();
		for (int i = 0; i < testDAta.size(); i++) {
			if (testDAta.get(i).get("DeductibleAppliesBy").contains("APC")) {
				alteredDate.add(testDAta.get(i));
			}
		}
		return alteredDate;
	}*/

	/*public String verifyDeductibleDetails(List<Map<String, String>> binderDetails, String policyNumber) {
		List<Map<String, Object>> deductibleData = getDeductibleDetails(policyNumber);
		List<Map<String, String>> binderLocationLevelDate = getLocationLevelBinderData(binderDetails);
		List<Map<String, String>> binderAPCLevelDate = getAPCLevelBinderData(binderDetails);
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		if (binderLocationLevelDate.size() > 0) {
			List<Map<String, String>> dbLocationLevelDate = getDBLocationLevelData(deductibleData);
			for (int i = 0; i < dbLocationLevelDate.size(); i++) {
				try {
					Assertions
							.addInfo(
									"<span class='group'> "
											+ dbLocationLevelDate.get(i).get("DeductibleAppliesBy").toString() + " : "
											+ binderLocationLevelDate.get(i).get("DeductibleApplicability")
													.substring(binderLocationLevelDate.get(i)
															.get("DeductibleApplicability").length() - 1)
											+ "</span>",
									"<span class='group'> GROUPING</span>");
					Assertions.verify(dbLocationLevelDate.get(i).get("DedDsc"),
							binderLocationLevelDate.get(i).get("DedDsc"),
							"Deductible Description :" + dbLocationLevelDate.get(i).get("DedDsc"),
							"Deductible Description :" + binderLocationLevelDate.get(i).get("DedDsc"), false, false);
					Assertions.verify(dbLocationLevelDate.get(i).get("Deductible"),
							binderLocationLevelDate.get(i).get("Deductible"),
							"Deductible :" + dbLocationLevelDate.get(i).get("DedTypeDsc") + " : "
									+ (dbLocationLevelDate.get(i).get("Deductible").contains("%")
											? dbLocationLevelDate.get(i).get("Deductible")
											: format.format(
													Integer.parseInt(dbLocationLevelDate.get(i).get("Deductible")))),
							"Deductible :" + binderLocationLevelDate.get(i).get("DedTypeDsc") + " : "
									+ (binderLocationLevelDate.get(i).get("Deductible").contains("%")
											? binderLocationLevelDate.get(i).get("Deductible")
											: format.format(Integer
													.parseInt(binderLocationLevelDate.get(i).get("Deductible")))),
							false, false);
					Assertions.verify(dbLocationLevelDate.get(i).get("DeductibleOccurence"),
							binderLocationLevelDate.get(i).get("DeductibleOccurence"),
							"Deductible Occurrence :" + dbLocationLevelDate.get(i).get("DeductibleOccurence"),
							"Deductible Occurrence :" + binderLocationLevelDate.get(i).get("DeductibleOccurence"),
							false, false);
					Assertions.verify(dbLocationLevelDate.get(i).get("DeductibleAppliesBy"),
							binderLocationLevelDate.get(i).get("DeductibleAppliesBy"),
							"Deductible Applies By : " + dbLocationLevelDate.get(i).get("DeductibleAppliesBy"),
							"Deductible Applies By: " + binderLocationLevelDate.get(i).get("DeductibleAppliesBy"),
							false, false);
					Assertions.verify(dbLocationLevelDate.get(i).get("DeductibleApplicability"),
							binderLocationLevelDate.get(i).get("DeductibleApplicability"),
							"Deductible Applicability :" + dbLocationLevelDate.get(i).get("DeductibleApplicability"),
							"Deductible Applicability :"
									+ binderLocationLevelDate.get(i).get("DeductibleApplicability"),
							false, false);
					Assertions.verify(dbLocationLevelDate.get(i).get("CoverageDesc"),
							binderLocationLevelDate.get(i).get("CoverageDesc"),
							"Coverage Description :" + dbLocationLevelDate.get(i).get("CoverageDesc"),
							"Coverage Description :" + binderLocationLevelDate.get(i).get("CoverageDesc"), false,
							false);
					Assertions.verify(dbLocationLevelDate.get(i).get("CovPerilDsc"),
							binderLocationLevelDate.get(i).get("CovPerilDsc"),
							"Coverage Peril Description :" + dbLocationLevelDate.get(i).get("CovPerilDsc"),
							"Coverage Peril Description :" + binderLocationLevelDate.get(i).get("CovPerilDsc"), false,
							false);
					if (deductibleData.get(i).get("CalculatedDeductible") != null)
						Assertions.passTest("Calculated Deductible",
								format.format(deductibleData.get(i).get("CalculatedDeductible")) + "");
					testStatus=Assertions.verify(deductibleData.get(i).get("DedOccurrenceId").toString(), "16521",
							"Deductible Occurrence Id ", deductibleData.get(i).get("DedOccurrenceId") + "", false,
							false);
				} catch (Exception e) {
					Assertions.addInfo("<span class='group'> "
							+ dbLocationLevelDate.get(i).get("DeductibleAppliesBy").toString() + " : " + "</span>",
							"<span class='group'> GROUPING</span>");
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Deductible Description :" + dbLocationLevelDate.get(i).get("DedDsc"), "", false, false);
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB", "Deductible :"
							+ dbLocationLevelDate.get(i).get("DedTypeDsc") + " : "
							+ (dbLocationLevelDate.get(i).get("Deductible").contains("%")
									? dbLocationLevelDate.get(i).get("Deductible")
									: format.format(Integer.parseInt(dbLocationLevelDate.get(i).get("Deductible")))),
							"", false, false);
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Deductible Occurrence :" + dbLocationLevelDate.get(i).get("DeductibleOccurence"), "",
							false, false);
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Deductible Applies By : " + dbLocationLevelDate.get(i).get("DeductibleAppliesBy"), "",
							false, false);
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Deductible Applicability :" + dbLocationLevelDate.get(i).get("DeductibleApplicability"),
							"", false, false);
					Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Coverage Description :" + dbLocationLevelDate.get(i).get("CoverageDesc"), "", false,
							false);
					testStatus=Assertions.verify("Unexpected data in DB", "to not have this coverage in DB",
							"Coverage Peril Description :" + dbLocationLevelDate.get(i).get("CovPerilDsc"), "", false,
							false);
				}
			}
		}
		if (binderAPCLevelDate.size() > 0) {
			List<Map<String, Object>> dbAPCLevelDate = getDBAPCLevelData(deductibleData);
			for (int i = 0; i < dbAPCLevelDate.size(); i++) {
				Assertions.addInfo(
						"<span class='group'> " + dbAPCLevelDate.get(i).get("DeductibleAppliesBy").toString() + " : "
								+ binderAPCLevelDate.get(i).get("DeductibleAppliesBy").substring(
										binderAPCLevelDate.get(i).get("DeductibleAppliesBy").length() - 1)
								+ "</span>",
						"<span class='group'> GROUPING</span>");
				Assertions.verify(dbAPCLevelDate.get(i).get("DedDsc"), binderAPCLevelDate.get(i).get("DedDsc"),
						"Deductible Description :" + dbAPCLevelDate.get(i).get("DedDsc"),
						"Deductible Description :" + binderAPCLevelDate.get(i).get("DedDsc"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("Deductible"), binderAPCLevelDate.get(i).get("Deductible"),
						"Deductible :" + dbAPCLevelDate.get(i).get("DedTypeDsc") + " : "
								+ (((String) dbAPCLevelDate.get(i).get("Deductible")).contains("%")
										? dbAPCLevelDate.get(i).get("Deductible")
										: format.format(
												Integer.parseInt((String) dbAPCLevelDate.get(i).get("Deductible")))),
						"Deductible :" + binderAPCLevelDate.get(i).get("DedTypeDsc") + " : "
								+ (binderAPCLevelDate.get(i).get("Deductible").contains("%")
										? binderAPCLevelDate.get(i).get("Deductible")
										: format.format(Integer.parseInt(binderAPCLevelDate.get(i).get("Deductible")))),
						false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("DeductibleOccurence"),
						binderAPCLevelDate.get(i).get("DeductibleOccurence"),
						"Deductible Occurrence :" + dbAPCLevelDate.get(i).get("DeductibleOccurence"),
						"Deductible Occurrence :" + binderAPCLevelDate.get(i).get("DeductibleOccurence"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("DeductibleAppliesBy"),
						binderAPCLevelDate.get(i).get("DeductibleAppliesBy"),
						"Deductible Applies By :" + dbAPCLevelDate.get(i).get("DeductibleAppliesBy"),
						"Deductible Applies By :" + binderAPCLevelDate.get(i).get("DeductibleAppliesBy"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("DeductibleApplicability"),
						binderAPCLevelDate.get(i).get("DeductibleApplicability"),
						"Deductible Applicability :" + dbAPCLevelDate.get(i).get("DeductibleApplicability"),
						"Deductible Applicability :" + binderAPCLevelDate.get(i).get("DeductibleApplicability"), false,
						false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CoverageDesc"),
						binderAPCLevelDate.get(i).get("CoverageDesc"),
						"Coverage Description :" + dbAPCLevelDate.get(i).get("CoverageDesc"),
						"Coverage Description :" + binderAPCLevelDate.get(i).get("CoverageDesc"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CovPerilDsc"),
						binderAPCLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + dbAPCLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :", false, false);
				if (deductibleData.get(i).get("CalculatedDeductible") != null)
					Assertions.passTest("Calculated Deductible",
							format.format(deductibleData.get(i).get("CalculatedDeductible")) + "");
				Assertions.verify(deductibleData.get(i).get("DedOccurrenceId").toString(), "16521",
						"Deductible Occurrence Id ", deductibleData.get(i).get("DedOccurrenceId") + "", false, false);
			}
		}
		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}*/

	/*public void verifyDeductibleDetailsPNB(List<Map<String, String>> binderDetails, String policyNumber,
			int hvnNumber, int transactionNumber, Map<String, String> pnbdataTest) {
		List<Map<String, Object>> deductibleData = getDeductibleDetailsPNB(policyNumber, hvnNumber);
		List<Map<String, String>> binderLocationLevelDate = getLocationLevelBinderData(binderDetails);
		List<Map<String, String>> binderAPCLevelDate = getAPCLevelBinderData(binderDetails);
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		if (binderLocationLevelDate.size() > 0) {
			List<Map<String, String>> dbLocationLevelDate = getDBLocationLevelData(deductibleData);
			for (int i = 0; i < dbLocationLevelDate.size(); i++) {
				if (!dbLocationLevelDate.get(i).get("DeleteTransactionid").equals("0")) {
					Assertions.passTest(
							"Deductible Description :" + dbLocationLevelDate.get(i).get("DedDsc") + " "
									+ dbLocationLevelDate.get(i).get("DedTypeDsc"),
							"Deleted Transaction id : " + dbLocationLevelDate.get(i).get("DeleteTransactionid"));
				} else {
					try {
						if ((binderLocationLevelDate.get(i).get("CovPerilDsc").contains("Wind") && !pnbdataTest.get(
								binderLocationLevelDate.get(i).get("DeductibleApplicability") + "-NamedHurricaneDed")
								.equals(""))
								|| (binderLocationLevelDate.get(i).get("CovPerilDsc").contains("Wind")
										&& !pnbdataTest.get("NamedHurricaneDedValue").equals(""))
								|| (binderLocationLevelDate.get(i).get("CovPerilDsc").contains("Flood")
										&& !pnbdataTest.get("FloodDeductible").equals(""))
								|| (binderLocationLevelDate.get(i).get("CovPerilDsc").contains("Earthquake"))) {
							Assertions.addInfo(
									"<span class='group'> "
											+ dbLocationLevelDate.get(i).get("DeductibleAppliesBy").toString() + " : "
											+ binderLocationLevelDate.get(i).get("DeductibleApplicability")
													.substring(binderLocationLevelDate.get(i)
															.get("DeductibleApplicability").length() - 1)
											+ "</span>",
									"<span class='group'> GROUPING</span>");
							Assertions.verify(dbLocationLevelDate.get(i).get("DedDsc"),
									binderLocationLevelDate.get(i).get("DedDsc"),
									"Deductible Description :" + dbLocationLevelDate.get(i).get("DedDsc"),
									"Deductible Description :" + binderLocationLevelDate.get(i).get("DedDsc"), false,
									false);
							Assertions.verify(dbLocationLevelDate.get(i).get("Deductible"),
									binderLocationLevelDate.get(i).get("Deductible"),
									"Deductible :" + dbLocationLevelDate.get(i).get("DedTypeDsc") + " : "
											+ (dbLocationLevelDate.get(i).get("Deductible").contains("%")
													? dbLocationLevelDate.get(i).get("Deductible")
													: format.format(Integer
															.parseInt(dbLocationLevelDate.get(i).get("Deductible")))),
									"Deductible :" + binderLocationLevelDate.get(i).get("DedTypeDsc") + " : "
											+ (binderLocationLevelDate.get(i).get("Deductible").contains("%")
													? binderLocationLevelDate.get(i).get("Deductible")
													: format.format(Integer.parseInt(
															binderLocationLevelDate.get(i).get("Deductible")))),
									false, false);
							Assertions.verify(dbLocationLevelDate.get(i).get("DeductibleOccurence"),
									binderLocationLevelDate.get(i).get("DeductibleOccurence"),
									"Deductible Occurrence :" + dbLocationLevelDate.get(i).get("DeductibleOccurence"),
									"Deductible Occurrence :"
											+ binderLocationLevelDate.get(i).get("DeductibleOccurence"),
									false, false);
							Assertions.verify(dbLocationLevelDate.get(i).get("DeductibleAppliesBy"),
									binderLocationLevelDate.get(i).get("DeductibleAppliesBy"),
									"Deductible Applies By : " + dbLocationLevelDate.get(i).get("DeductibleAppliesBy"),
									"Deductible Applies By: "
											+ binderLocationLevelDate.get(i).get("DeductibleAppliesBy"),
									false, false);
							Assertions.verify(dbLocationLevelDate.get(i).get("DeductibleApplicability"),
									binderLocationLevelDate.get(i).get("DeductibleApplicability"),
									"Deductible Applicability :"
											+ dbLocationLevelDate.get(i).get("DeductibleApplicability"),
									"Deductible Applicability :"
											+ binderLocationLevelDate.get(i).get("DeductibleApplicability"),
									false, false);
							Assertions.verify(dbLocationLevelDate.get(i).get("CoverageDesc"),
									binderLocationLevelDate.get(i).get("CoverageDesc"),
									"Coverage Description :" + dbLocationLevelDate.get(i).get("CoverageDesc"),
									"Coverage Description :" + binderLocationLevelDate.get(i).get("CoverageDesc"),
									false, false);
							Assertions.verify(dbLocationLevelDate.get(i).get("CovPerilDsc"),
									binderLocationLevelDate.get(i).get("CovPerilDsc"),
									"Coverage Peril Description :" + dbLocationLevelDate.get(i).get("CovPerilDsc"),
									"Coverage Peril Description :" + binderLocationLevelDate.get(i).get("CovPerilDsc"),
									false, false);
							if (!dbLocationLevelDate.get(i).get("CalculateDeductible").equals("null")) {
								Assertions.passTest("Calculated Deductible",
										dbLocationLevelDate.get(i).get("CalculateDeductible") + "");
								Assertions.verify(deductibleData.get(i).get("DedOccurrenceId").toString(), "16521",
										"Deductible Occurrence Id ", deductibleData.get(i).get("DedOccurrenceId") + "",
										false, false);
							}
						}
						if ((binderLocationLevelDate.get(i).get("CovPerilDsc").contains("Wind")
								&& pnbdataTest.get("NamedHurricaneDedValue").equals(""))
								|| (binderLocationLevelDate.get(i).get("CovPerilDsc").contains("Flood")
										&& pnbdataTest.get("FloodDeductible").equals(""))
								|| (binderLocationLevelDate.get(i).get("CovPerilDsc").contains("Earthquake")
										&& pnbdataTest.get("EQDeductible").equals("None")
										&& pnbdataTest.get("EQDeductible").equals(""))) {
							Assertions.addInfo(
									"<span class='group'> "
											+ dbLocationLevelDate.get(i).get("DeductibleAppliesBy").toString() + " : "
											+ binderLocationLevelDate.get(i).get("DeductibleApplicability")
													.substring(binderLocationLevelDate.get(i)
															.get("DeductibleApplicability").length() - 1)
											+ "</span>",
									"<span class='group'> GROUPING</span>");
							Assertions.addInfo("Deductible Description :" + dbLocationLevelDate.get(i).get("DedDsc"),
									"Deductible Description :" + binderLocationLevelDate.get(i).get("DedDsc"));
							Assertions
									.addInfo(
											"Deductible :"
													+ dbLocationLevelDate.get(i).get("DedTypeDsc") + " : "
													+ (dbLocationLevelDate.get(i).get("Deductible")
															.contains("%")
																	? dbLocationLevelDate.get(i).get("Deductible")
																	: format.format(
																			Integer.parseInt(dbLocationLevelDate.get(i)
																					.get("Deductible")))),
											"Deductible :" + binderLocationLevelDate.get(i).get("DedTypeDsc") + " : "
													+ (binderLocationLevelDate.get(i).get("Deductible").contains("%")
															? binderLocationLevelDate.get(i).get("Deductible")
															: format.format(Integer.parseInt(binderLocationLevelDate
																	.get(i).get("Deductible")))));
							Assertions.addInfo(
									"Deductible Occurrence :" + dbLocationLevelDate.get(i).get("DeductibleOccurence"),
									"Deductible Occurrence :"
											+ binderLocationLevelDate.get(i).get("DeductibleOccurence"));
							Assertions.addInfo(
									"Deductible Applies By : " + dbLocationLevelDate.get(i).get("DeductibleAppliesBy"),
									"Deductible Applies By: "
											+ binderLocationLevelDate.get(i).get("DeductibleAppliesBy"));
							Assertions.addInfo(
									"Deductible Applicability :"
											+ dbLocationLevelDate.get(i).get("DeductibleApplicability"),
									"Deductible Applicability :"
											+ binderLocationLevelDate.get(i).get("DeductibleApplicability"));
							Assertions.addInfo(
									"Coverage Description :" + dbLocationLevelDate.get(i).get("CoverageDesc"),
									"Coverage Description :" + binderLocationLevelDate.get(i).get("CoverageDesc"));
							Assertions.addInfo(
									"Coverage Peril Description :" + dbLocationLevelDate.get(i).get("CovPerilDsc"),
									"Coverage Peril Description :" + binderLocationLevelDate.get(i).get("CovPerilDsc"));
							if (!dbLocationLevelDate.get(i).get("CalculateDeductible").equals("null"))
								Assertions.addInfo("Calculated Deductible",
										dbLocationLevelDate.get(i).get("CalculateDeductible") + "");
							Assertions.addInfo("Deductible Occurrence Id ",
									deductibleData.get(i).get("DedOccurrenceId") + "");
						}
					} catch (Exception e) {
					}
				}
			}
		}
		if (binderAPCLevelDate.size() > 0) {
			List<Map<String, Object>> dbAPCLevelDate = getDBAPCLevelData(deductibleData);
			for (int i = 0; i < dbAPCLevelDate.size(); i++) {
				Assertions.addInfo(
						"<span class='group'> " + dbAPCLevelDate.get(i).get("DeductibleAppliesBy").toString() + " : "
								+ binderAPCLevelDate.get(i).get("DeductibleAppliesBy").substring(
										binderAPCLevelDate.get(i).get("DeductibleAppliesBy").length() - 1)
								+ "</span>",
						"<span class='group'> GROUPING</span>");
				Assertions.verify(dbAPCLevelDate.get(i).get("DedDsc"), binderAPCLevelDate.get(i).get("DedDsc"),
						"Deductible Description :" + dbAPCLevelDate.get(i).get("DedDsc"),
						"Deductible Description :" + binderAPCLevelDate.get(i).get("DedDsc"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("Deductible"), binderAPCLevelDate.get(i).get("Deductible"),
						"Deductible :" + dbAPCLevelDate.get(i).get("DedTypeDsc") + " : "
								+ (((String) dbAPCLevelDate.get(i).get("Deductible")).contains("%")
										? dbAPCLevelDate.get(i).get("Deductible")
										: format.format(
												Integer.parseInt((String) dbAPCLevelDate.get(i).get("Deductible")))),
						"Deductible :" + binderAPCLevelDate.get(i).get("DedTypeDsc") + " : "
								+ (binderAPCLevelDate.get(i).get("Deductible").contains("%")
										? binderAPCLevelDate.get(i).get("Deductible")
										: format.format(Integer.parseInt(binderAPCLevelDate.get(i).get("Deductible")))),
						false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("DeductibleOccurence"),
						binderAPCLevelDate.get(i).get("DeductibleOccurence"),
						"Deductible Occurrence :" + dbAPCLevelDate.get(i).get("DeductibleOccurence"),
						"Deductible Occurrence :" + binderAPCLevelDate.get(i).get("DeductibleOccurence"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("DeductibleAppliesBy"),
						binderAPCLevelDate.get(i).get("DeductibleAppliesBy"),
						"Deductible Applies By :" + dbAPCLevelDate.get(i).get("DeductibleAppliesBy"),
						"Deductible Applies By :" + binderAPCLevelDate.get(i).get("DeductibleAppliesBy"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("DeductibleApplicability"),
						binderAPCLevelDate.get(i).get("DeductibleApplicability"),
						"Deductible Applicability :" + dbAPCLevelDate.get(i).get("DeductibleApplicability"),
						"Deductible Applicability :" + binderAPCLevelDate.get(i).get("DeductibleApplicability"), false,
						false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CoverageDesc"),
						binderAPCLevelDate.get(i).get("CoverageDesc"),
						"Coverage Description :" + dbAPCLevelDate.get(i).get("CoverageDesc"),
						"Coverage Description :" + binderAPCLevelDate.get(i).get("CoverageDesc"), false, false);
				Assertions.verify(dbAPCLevelDate.get(i).get("CovPerilDsc"),
						binderAPCLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :" + dbAPCLevelDate.get(i).get("CovPerilDsc"),
						"Coverage Peril Description :", false, false);
				if (deductibleData.get(i).get("CalculatedDeductible") != null)
					Assertions.passTest("Calculated Deductible",
							format.format(deductibleData.get(i).get("CalculatedDeductible")) + "");
				Assertions.verify(deductibleData.get(i).get("DedOccurrenceId").toString(), "16521",
						"Deductible Occurrence Id ", deductibleData.get(i).get("DedOccurrenceId") + "", false, false);
			}
		}
	}*/
}
