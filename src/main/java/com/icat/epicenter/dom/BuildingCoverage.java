/** Program Description: Methods and queries built up for BuildingCoverage table
 *  Author			   : SMNetserv
 *  Date of Creation   : 13/02/2018
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

public class BuildingCoverage {
	public String bldgCoverageTable;

	public String hvn;
	public String product;
	public String bldValue;
	public String bppValue;
	public String ordLawLimit;
	public String createDateTime;
	public String deleteDateTime;
	DBFrameworkConnection connection;
	QueryBuilder build;
	private static List<Integer> testStatus;
	public Map<String, Object> bldgCoverage;

	//RESIDENTIAL PARAMETERS
	public String tiv;
	public String policyNumber;
	public String policyBuildingId;
	public String covBValue;
	public String bIValue;
	public String enhancedReplacementCost;


	public BuildingCoverage(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("BuildingCoverage");
		bldgCoverageTable = properties.getProperty("db_BldgCoverageTable");
		hvn = properties.getProperty("db_Hvn");
		product = properties.getProperty("db_Product");
		bldValue = properties.getProperty("db_BldValue");
		bppValue = properties.getProperty("db_BppValue");
		ordLawLimit = properties.getProperty("db_OrdLawLimit");
		createDateTime = properties.getProperty("db_CreateDateTime");
		deleteDateTime = properties.getProperty("db_DeleteDateTime");

		//RESIDENTIAL PARAMETERS
		/*
		tiv = properties.getProperty("db_Tiv");
		policyNumber = properties.getProperty("db_PolicyNumber");
		policyBuildingId = properties.getProperty("db_PolicyBuildingID");
		covBValue = properties.getProperty("db_CovBValue");
		bIValue = properties.getProperty("db_BIValue");
		enhancedReplacementCost = properties.getProperty("db_EnhancedReplacementCost");*/

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(bldgCoverageTable);
	}

	public Map<String, Object> getBuildingCoverageDetails(int transactionId, int i) {
		List<String> outFields = new ArrayList<>();
		outFields.add(hvn);
		outFields.add(product);
		outFields.add(bldValue);
		outFields.add(bppValue);
		outFields.add(ordLawLimit);
		outFields.add(createDateTime);
		outFields.add(deleteDateTime);

		//RESIDENTIAL PARAMETERS
		/*
		outFields.add(tiv);
		outFields.add(covBValue);
		outFields.add(bIValue);*/

		build.outFields(outFields);
		build.whereBy("transactionId = " + transactionId + "order by PolicyBuildingId");
		List<Map<String, Object>> bldgCoverageData = build.execute(60);
		return bldgCoverage = bldgCoverageData.get(i);
	}

	public List<Map<String, String>> getBuildingCoverageDataExpected(String tcid) {
		List<Map<String, String>> binderBuildingCoverageData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"BuildingCoverage").readExcelRowWise();


		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : binderBuildingCoverageData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);
			}
		}

		return TCData;

	}


	public String verifyBuildingCoverageData(List<Map<String, String>> binderData, int transactionId) {
		for (int i = 0; i < binderData.size(); i++) {
			DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = getBuildingCoverageDetails(transactionId, i);

			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN"), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("Product").toString(), binderDataRow.get("Product"), "Product : " + aftershockRow.get("Product").toString(), "Expected : " + binderDataRow.get("Product"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("BLDValue").toString().split("\\.")[0], binderDataRow.get("BLDValue"), "BLDValue : " + aftershockRow.get("BLDValue").toString().split("\\.")[0], "Expected : " + binderDataRow.get("BLDValue"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("BPPValue").toString().split("\\.")[0], binderDataRow.get("BPPValue"), "BPPValue : " + aftershockRow.get("BPPValue").toString().split("\\.")[0], "Expected : " + binderDataRow.get("BPPValue"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("OrdLawLimit").toString().split("\\.")[0], binderDataRow.get("OrdLawLimit"), "OrdLawLimit : " + aftershockRow.get("OrdLawLimit").toString().split("\\.")[0], "Expected : " + binderDataRow.get("OrdLawLimit"), false, false);

			LocalDate asCreateDateTime = LocalDate.parse(String.valueOf(aftershockRow.get("CreateDateTime")).substring(0,10));
			String epiProcessDate = ExcelReportUtil.getInstance().getLatestProcessingDateFromMaster(binderDataRow.get("TCID"));
			testStatus = Assertions.verify(asCreateDateTime.format(f), epiProcessDate, "CreateDateTime : " + asCreateDateTime.format(f), "Expected : " + epiProcessDate, false, false);

			String delDateTimeExpected = "01/01/1900";
			if (!binderDataRow.get("DeleteDateTime").contains("1900-01-01")) {
				delDateTimeExpected = ExcelReportUtil.getInstance().getLatestTransactionDateFromMaster(binderDataRow.get("TCID"));
			}
			LocalDate asDeleteDate = LocalDate.parse(String.valueOf(aftershockRow.get("DeleteDateTime")).replace(" 00:00:00.0", ""));
			testStatus = Assertions.verify(f.format(asDeleteDate), delDateTimeExpected, "DeleteDateTime : " + f.format(asDeleteDate), "Expected : " + delDateTimeExpected, false, false);

		}

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}





	//RESIDENTIAL METHODS
	/*
		public Map<String, Object> getDwellingCoverageDetails(int policyBldgId, int hvnnum) {
		List<String> outFields = new ArrayList<>();
		outFields.add(bldValue);
		outFields.add(bppValue);
		outFields.add(tiv);
		outFields.add(covBValue);
		outFields.add(bIValue);
		outFields.add(bIValue);
		build.outFields(outFields);
		build.whereBy(policyBuildingId + " = " + policyBldgId + " and " + hvn + " =" + hvnnum);
		List<Map<String, Object>> bldgCoverageData = build.execute(60);
		return bldgCoverage = bldgCoverageData.get(0);
	}

		public String verifyDwellingCoverageValues(int policyBldgId, Map<String, String> testData,
			Map<String, String> binderData, String testdatarenewaldb) {
		Assertions.addInfo("<span class='group'> " + binderData.get("BldgAddr2") + "</span>",
				"<span class='group'> GROUPING</span>");
		bldgCoverage = getBuildingCoverageDetais(policyBldgId);
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0");
		if (!binderData.get("DwellingCoverageA").equals("")) {
			testStatus = Assertions.verify(df.format(bldgCoverage.get("BLDValue")).toString().trim(),
					binderData.get("DwellingCoverageA"),
					"Coverage A : " + format.format((bldgCoverage.get("BLDValue"))),
					"Coverage A : " + format.format(Integer.valueOf(binderData.get("DwellingCoverageA").trim())), false,
					false);
		} else {
			if (!testData.get(binderData.get("BldgAddr2") + "-DwellingCovA").equals("")) {
				testStatus = Assertions.verify(df.format(bldgCoverage.get("BLDValue")).toString().trim(),
						testData.get(binderData.get("BldgAddr2") + "-DwellingCovA").trim(),
						"Coverage A : " + format.format((bldgCoverage.get("BLDValue"))),
						"Coverage A : " + format.format(
								Integer.valueOf((testData.get(binderData.get("BldgAddr2") + "-DwellingCovA").trim()))),
						false, false);
			} else {
				Assertions.addInfo("Coverage A", "N/A");
			}
		}
		if (testData.get("TCID").equalsIgnoreCase("TC_006") && testdatarenewaldb.equalsIgnoreCase("yes")) {
			Assertions.addInfo("Coverage B", "N/A");
		} else {
			if (!testData.get(binderData.get("BldgAddr2") + "-DwellingCovB").equals("")) {
				testStatus = Assertions.verify(df.format(bldgCoverage.get("CovBValue")).toString().trim(),
						testData.get(binderData.get("BldgAddr2") + "-DwellingCovB").trim(),
						"Coverage B : " + format.format((bldgCoverage.get("CovBValue"))),
						"Coverage B : " + format
								.format(Integer.valueOf(testData.get(binderData.get("BldgAddr2") + "-DwellingCovB"))),
						false, false);
			} else {
				Assertions.addInfo("Coverage B", "N/A");
			}
		}
		if (!testData.get(binderData.get("BldgAddr2") + "-DwellingCovC").equals("")) {
			testStatus = Assertions.verify(df.format(bldgCoverage.get("BPPValue")).toString().trim(),
					testData.get(binderData.get("BldgAddr2") + "-DwellingCovC").trim(),
					"Coverage C : " + format.format((bldgCoverage.get("BPPValue"))),
					"Coverage C : " + format.format(
							Integer.valueOf((testData.get(binderData.get("BldgAddr2") + "-DwellingCovC").trim()))),
					false, false);
		} else {
			Assertions.addInfo("Coverage C", "N/A");
		}
		if (!testData.get(binderData.get("BldgAddr2") + "-DwellingCovD").equals("")) {
			testStatus = Assertions.verify(df.format(bldgCoverage.get("BIValue")).toString().trim(),
					testData.get(binderData.get("BldgAddr2") + "-DwellingCovD").trim(),
					"Coverage D : " + format.format((bldgCoverage.get("BIValue"))),
					"Coverage D : " + format
							.format(Integer.valueOf(testData.get(binderData.get("BldgAddr2") + "-DwellingCovD"))),
					false, false);
		} else {
			Assertions.addInfo("Coverage D", "N/A");
		}
		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}

		public void verifyDwellingCoverageValuesforPNB(int policyBldgId, List<Map<String, String>> pnbtestData,
			Map<String, String> binderData, int hvn, int transactionNumber, Map<String, String> nbTestData) {
		bldgCoverage = getDwellingCoverageDetails(policyBldgId, hvn);
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0");
		boolean dwellCovA = false;
		boolean dwellCovB = false;
		boolean dwellCovC = false;
		boolean dwellCovD = false;
		for (int p = transactionNumber; p >= 0; p--) {
			if (!binderData.get("DwellingCoverageA").equals("")) {
				Assertions.verify(df.format(bldgCoverage.get("BLDValue")).toString().trim(),
						binderData.get("DwellingCoverageA"),
						"Coverage A : " + format.format((bldgCoverage.get("BLDValue"))),
						"Coverage A : " + format.format(Integer.valueOf(binderData.get("DwellingCoverageA").trim())),
						false, false);
			} else {
				if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovA").equals("")
						&& dwellCovA == false) {
					if (p == transactionNumber)
						Assertions
								.verify(df.format(bldgCoverage.get("BLDValue")).toString().trim(),
										pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovA").trim(),
										"Coverage A : " + format.format((bldgCoverage.get("BLDValue"))),
										"Coverage A : " + format.format(Integer.valueOf((pnbtestData.get(p)
												.get(binderData.get("BldgAddr2") + "-DwellingCovA").trim()))),
										false, false);
					else
						Assertions.addInfo("Coverage A : " + format.format((bldgCoverage.get("BLDValue"))),
								"Coverage A : " + format.format(Integer.valueOf((pnbtestData.get(p)
										.get(binderData.get("BldgAddr2") + "-DwellingCovA").trim()))));
					dwellCovA = true;
				}
			}
			if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovB").equals("")
					&& dwellCovB == false) {
				if (p == transactionNumber)
					Assertions.verify(df.format(bldgCoverage.get("CovBValue")).toString().trim(),
							pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovB").trim(),
							"Coverage B : " + format.format((bldgCoverage.get("CovBValue"))),
							"Coverage B : " + format.format(Integer
									.valueOf(pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovB"))),
							false, false);
				else
					Assertions.addInfo("Coverage B : " + format.format((bldgCoverage.get("CovBValue"))),
							"Coverage B : " + format.format(Integer
									.valueOf(pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovB"))));
				dwellCovB = true;
			}
			if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovC").equals("")
					&& dwellCovC == false) {
				if (p == transactionNumber)
					Assertions.verify(df.format(bldgCoverage.get("BPPValue")).toString().trim(),
							pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovC").trim(),
							"Coverage C : " + format.format((bldgCoverage.get("BPPValue"))),
							"Coverage C : " + format.format(Integer.valueOf(
									(pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovC").trim()))),
							false, false);
				else
					Assertions.addInfo("Coverage C : " + format.format((bldgCoverage.get("BPPValue"))),
							"Coverage C : " + format.format(Integer.valueOf(
									(pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovC").trim()))));
				dwellCovC = true;
			}
			if (!pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovD").equals("")
					&& dwellCovD == false) {
				if (p == transactionNumber)
					Assertions.verify(df.format(bldgCoverage.get("BIValue")).toString().trim(),
							pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovD").trim(),
							"Coverage D : " + format.format((bldgCoverage.get("BIValue"))),
							"Coverage D : " + format.format(Integer
									.valueOf(pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovD"))),
							false, false);
					Assertions.addInfo("Coverage D : " + format.format((bldgCoverage.get("BIValue"))),
							"Coverage D : " + format.format(Integer
									.valueOf(pnbtestData.get(p).get(binderData.get("BldgAddr2") + "-DwellingCovD"))));
				dwellCovD = true;
			}
			if (p == 0 && dwellCovD == false) {
				Assertions.addInfo("Coverage D", "Coverage D: Loss of Use not included as a coverage");

			}
		}
	}
	 */
}
