package com.icat.epicenter.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;

public class PriorLoss {
	public String tableName;
	public String hvn;
	public String lossCount;
	public String dateOfLoss;
	public String descriptionOfLoss;
	public String grossLossAmount;
	public String isCompleteRepairs;
	public String sinkholeLoss;

	/* RESIDENTIAL PARAMETERS
	public String policyBuildingId;
	public String typeOfLoss;
	public String policyLossId;
	public String deleteTransactionId;
	ConsolidatedCodeTable cc;
	*/

	public String transactionId;
	public DBFrameworkConnection connection;
	public QueryBuilder build;
	public Map<String, Object> results;

	private static List<Integer> testStatus;


	public PriorLoss(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("PriorLoss");
		tableName = properties.getProperty("db_TableName");
		hvn = properties.getProperty("db_Hvn");
		lossCount = properties.getProperty("db_LossCount");
		dateOfLoss = properties.getProperty("db_DateOfLoss");
		descriptionOfLoss = properties.getProperty("db_DescriptionOfLoss");
		grossLossAmount = properties.getProperty("db_GrossLossAmount");
		isCompleteRepairs = properties.getProperty("db_IsCompleteRepairs");
		sinkholeLoss = properties.getProperty("db_SinkholeLoss");

		/* RESIDENTIAL PARAMETERS
		policyBuildingId = properties.getProperty("db_PolicyBuildingID");
		typeOfLoss = properties.getProperty("db_TypeOfLoss");
		policyLossId = properties.getProperty("db_PolicyLossId");
		deleteTransactionId = properties.getProperty("db_DeleteTransactionId");
		cc=new ConsolidatedCodeTable(dbConfig); */

		transactionId = properties.getProperty("db_TransactionID");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(tableName);
	}

	public Map<String, Object> getPriorLossDetails(int transactionId, int i) {
		List<String> outFields = new ArrayList<>();
		outFields.add(hvn);
		outFields.add(lossCount);
		outFields.add(dateOfLoss);
		outFields.add(descriptionOfLoss);
		outFields.add(grossLossAmount);
		outFields.add(isCompleteRepairs);
		outFields.add(sinkholeLoss);

		build.outFields(outFields);
		build.whereBy("transactionId  = '" + transactionId + "'");
		List<Map<String, Object>> priorLossDetails = build.execute(60);
		return priorLossDetails.get(i);
	}

	public List<Map<String, String>> getPriorLossDataExpected(String tcid) {
		List<Map<String, String>> priorLossData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"PriorLoss").readExcelRowWise();


		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : priorLossData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);
			}
		}

		return TCData;

	}


	public String verifyPriorLossData(List<Map<String, String>> binderData, int transactionId) {

		for (int i = 0; i < binderData.size(); i++) {
			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = getPriorLossDetails(transactionId, i);

			System.out.println("binderDataRow = " + binderDataRow);
			System.out.println("aftershockRow = " + aftershockRow);

			testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN"), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("LossCount").toString(), binderDataRow.get("LossCount"), "LossCount : " + aftershockRow.get("LossCount").toString(), "Expected : " + binderDataRow.get("LossCount"), false, false);

			String dateOfLossExpected = binderDataRow.get("DateOfLoss").split(" ")[0];
			String dateOfLossActual = aftershockRow.get("DateOfLoss").toString().split(" ")[0];
			testStatus = Assertions.verify(dateOfLossActual, dateOfLossExpected, "DateOfLoss : " + dateOfLossActual, "Expected : " + dateOfLossExpected, false, false);
			testStatus = Assertions.verify(aftershockRow.get("DescLoss").toString(), binderDataRow.get("DescLoss"), "DescLoss : " + aftershockRow.get("DescLoss").toString(), "Expected : " + binderDataRow.get("DescLoss"), false, false);
			testStatus = Assertions.verify(aftershockRow.get("GrossLossAmount").toString(), binderDataRow.get("GrossLossAmount"), "GrossLossAmount : " + aftershockRow.get("GrossLossAmount").toString(), "Expected : " + binderDataRow.get("GrossLossAmount"), false, false);
			String isCompleteRepairsExpected = "false";
			if (binderDataRow.get("IsCompleteRepairs").equals("1")) {
				isCompleteRepairsExpected = "true";
			}
			testStatus = Assertions.verify(aftershockRow.get("IsCompleteRepairs").toString(), isCompleteRepairsExpected, "IsCompleteRepairs : " + aftershockRow.get("IsCompleteRepairs").toString(), "Expected : " + isCompleteRepairsExpected, false, false);
			if (!binderDataRow.get("SinkHoleLoss").equals("0")) {
				testStatus = Assertions.verify(aftershockRow.get("SinkHoleLoss").toString(), binderDataRow.get("SinkHoleLoss"), "SinkHoleLoss : " + aftershockRow.get("SinkHoleLoss").toString(), "Expected : " + binderDataRow.get("SinkHoleLoss"), false, false);
			}

		}

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}




	/*RESIDENTIAL METHODS
	public List<Integer> getTypeLossId(int transactionid) {
		List<String> outFields = new ArrayList<>();
		outFields.add(typeOfLoss);
		build.outFields(outFields);
		build.whereBy(transactionId + " = " + transactionid);
		build.orderBy(typeOfLoss);
		List<Map<String, Object>> lossData = build.execute(60);
		List<Integer> lossId = new ArrayList<>();
		for (int i = 0; i < lossData.size(); i++) {
			lossId.add((Integer) lossData.get(i).get("TypeOfLoss"));
		}

		return lossId;
	}

	public List<Map<String, Object>> getPriorLossDetails(int transactionid) {
		List<String> outFields = new ArrayList<>();
		outFields.add(dateOfLoss);
		outFields.add(descriptionOfLoss);
		outFields.add(typeOfLoss);
		outFields.add(grossLossAmount);
		outFields.add(isCompleteRepairs);
		outFields.add(deleteTransactionId);
		build.outFields(outFields);
		build.whereBy(transactionId + " = " + "'" + transactionid + "'");
		build.orderBy(typeOfLoss);
		List<Map<String, Object>> priorLossDetails = build.execute(60);
		return priorLossDetails;
	}

	public String verifyPriorlossDetails(int policyBuildingID,int i,Map<String, String> testData,int transactionId,List<Map<String, Object>> lossType) {
		List<Map<String, Object>> priorLossData = getPriorLossDetails(transactionId);
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");

		Assertions.verify(lossType.get(i).get("Desc1"), testData.get("PriorLossType"+(i+1)), "Prior Loss Type : " + lossType.get(i).get("Desc1"),
				"Prior Loss Type : " + testData.get("PriorLossType"+(i+1)), false, false);

		Assertions.verify(dateformat.format(priorLossData.get(i).get("DateOfLoss")), testData.get("PriorLossDate"+(i+1)), "Prior Loss Date : " + dateformat.format(priorLossData.get(i).get("DateOfLoss")),
				"Prior Loss Date : " + testData.get("PriorLossDate"+(i+1)), false, false);

		Assertions.verify(format.format(priorLossData.get(i).get("GrossLossAmount")), format.format(Integer.valueOf(testData.get("PriorLossAmount"+(i+1)))), "Prior Loss Amount : " + format.format(priorLossData.get(i).get("GrossLossAmount")),
				"Prior Loss Amount : " + format.format(Integer.valueOf(testData.get("PriorLossAmount"+(i+1)))), false, false);

		if(priorLossData.get(i).get("IsCompleteRepairs").toString().equals("true")) {
			testStatus=Assertions.verify(priorLossData.get(i).get("IsCompleteRepairs").toString().replace("true", "Yes"), testData.get("IsPriorLossDamageRepaired?"+(i+1)), "Prior Loss Damage Repaired : " + priorLossData.get(i).get("IsCompleteRepairs").toString().replace("true", "Yes"),
					"Prior Loss Damage Repaired : " + testData.get("IsPriorLossDamageRepaired?"+(i+1)), false, false);
		}else {
			testStatus=Assertions.verify(priorLossData.get(i).get("IsCompleteRepairs").toString().replace("false", "No"), testData.get("IsPriorLossDamageRepaired?"+(i+1)), "Prior Loss Damage Repaired : " + priorLossData.get(i).get("IsCompleteRepairs").toString().replace("false", "No"),
					"Prior Loss Damage Repaired : " + testData.get("IsPriorLossDamageRepaired?"+(i+1)), false, false);
		}

		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}
	public void verifyPriorlossDetailsPNB(int policyBuildingID,int i,List<Map<String, String>> testData,int transactionId,List<Map<String, Object>> lossType,int transactionNumber,int p) {
		List<Map<String, Object>> priorLossData = getPriorLossDetails(transactionId);
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
		boolean typeOfLoss=false;
		boolean dateOfLoss=false;
		boolean lossAmount=false;
		boolean lossRepaired=false;
		Assertions.addInfo("<span class='group'> " + "Prior Loss " + " : "+ (i+1) + "</span>", "<span class='group'> GROUPING </span>");
		if (!priorLossData.get(i).get("DeleteTransactionId").toString().equals("0")) {
			Assertions.passTest(
					"Prior Loss :" + lossType.get(i).get("Desc1"),
					"Deleted Transaction id : " + priorLossData.get(i).get("DeleteTransactionId"));
		}

		else {
			if (!testData.get(p).get("PriorLossType"+(i+1)).equals("") && typeOfLoss == false) {
				if (p == transactionNumber)
					Assertions.verify(lossType.get(i).get("Desc1"), testData.get(p).get("PriorLossType"+(i+1)), "Prior Loss Type : " + lossType.get(i).get("Desc1"),
							"Prior Loss Type : " + testData.get(p).get("PriorLossType"+(i+1)), false, false);
				else {
					if(lossType.get(i).get("Desc1").toString().equals(testData.get(p).get("PriorLossType"+(i+1))))
						Assertions.addInfo("Prior Loss Type : " + lossType.get(i).get("Desc1"), "Prior Loss Type : " + testData.get(p).get("PriorLossType"+(i+1)));

					else
						Assertions.addInfo("Prior Loss Type : " + lossType.get(i).get("Desc1"), "Prior Loss Type : " + testData.get(0).get("PriorLossType"+(i+1)));
				}
				typeOfLoss=true;
			}

			if (!testData.get(p).get("PriorLossDate"+(i+1)).equals("") && dateOfLoss == false) {
				if (p == transactionNumber)
					Assertions.verify(dateformat.format(priorLossData.get(i).get("DateOfLoss")), testData.get(p).get("PriorLossDate"+(i+1)), "Prior Loss Date : " + dateformat.format(priorLossData.get(i).get("DateOfLoss")),
							"Prior Loss Date : " + testData.get(p).get("PriorLossDate"+(i+1)), false, false);
				else {
					if(dateformat.format(priorLossData.get(i).get("DateOfLoss")).equals(testData.get(p).get("PriorLossDate"+(i+1))))
						Assertions.addInfo("Prior Loss Date : " + dateformat.format(priorLossData.get(i).get("DateOfLoss")), "Prior Loss Date : " + testData.get(p).get("PriorLossDate"+(i+1)));

					else
						Assertions.addInfo("Prior Loss Date : " + dateformat.format(priorLossData.get(i).get("DateOfLoss")), "Prior Loss Date : " + testData.get(0).get("PriorLossDate"+(i+1)));

				}
				dateOfLoss=true;
			}

			if (!testData.get(p).get("PriorLossAmount"+(i+1)).equals("") && lossAmount == false) {
				if (p == transactionNumber)
					Assertions.verify(format.format(priorLossData.get(i).get("GrossLossAmount")), format.format(Integer.valueOf(testData.get(p).get("PriorLossAmount"+(i+1)))), "Prior Loss Amount : " + format.format(priorLossData.get(i).get("GrossLossAmount")),
							"Prior Loss Amount : " + format.format(Integer.valueOf(testData.get(p).get("PriorLossAmount"+(i+1)))), false, false);
				else {
					if(format.format(priorLossData.get(i).get("GrossLossAmount")).equals(format.format(Integer.valueOf(testData.get(p).get("PriorLossAmount"+(i+1))))))
						Assertions.addInfo("Prior Loss Amount : " + format.format(priorLossData.get(i).get("GrossLossAmount")), "Prior Loss Amount : " + format.format(Integer.valueOf(testData.get(p).get("PriorLossAmount"+(i+1)))));
					else
						Assertions.addInfo("Prior Loss Amount : " + format.format(priorLossData.get(i).get("GrossLossAmount")), "Prior Loss Amount : " + format.format(Integer.valueOf(testData.get(0).get("PriorLossAmount"+(i+1)))));
				}
				lossAmount=true;
			}
			if (!testData.get(p).get("IsPriorLossDamageRepaired?"+(i+1)).equals("") && lossRepaired == false) {
				if (p == transactionNumber) {
					if(priorLossData.get(i).get("IsCompleteRepairs").toString().equals("true"))
						Assertions.verify(priorLossData.get(i).get("IsCompleteRepairs").toString().replace("true", "Yes"), testData.get(p).get("IsPriorLossDamageRepaired?"+(i+1)), "Prior Loss Damage Repaired : " + priorLossData.get(i).get("IsCompleteRepairs").toString().replace("true", "Yes"),
								"Prior Loss Damage Repaired : " + testData.get(p).get("IsPriorLossDamageRepaired?"+(i+1)), false, false);
					else
						Assertions.verify(priorLossData.get(i).get("IsCompleteRepairs").toString().replace("false", "No"), testData.get(p).get("IsPriorLossDamageRepaired?"+(i+1)), "Prior Loss Damage Repaired : " + priorLossData.get(i).get("IsCompleteRepairs").toString().replace("false", "No"),
								"Prior Loss Damage Repaired : " + testData.get(p).get("IsPriorLossDamageRepaired?"+(i+1)), false, false);
				}else {
					if(priorLossData.get(i).get("IsCompleteRepairs").toString().equals(testData.get(p).get("IsPriorLossDamageRepaired?"+(i+1)).replace("Yes", "true")) || priorLossData.get(i).get("IsCompleteRepairs").toString().equals(testData.get(p).get("IsPriorLossDamageRepaired?"+(i+1)).replace("No", "false"))) {
						if(priorLossData.get(i).get("IsCompleteRepairs").toString().equals("true"))
							Assertions.addInfo("Prior Loss Damage Repaired : " + priorLossData.get(i).get("IsCompleteRepairs").toString().replace("true", "Yes"), "Prior Loss Damage Repaired : " + testData.get(p).get("IsPriorLossDamageRepaired?"+(i+1)));

						else
							Assertions.addInfo("Prior Loss Damage Repaired : " + priorLossData.get(i).get("IsCompleteRepairs").toString().replace("false", "No"), "Prior Loss Damage Repaired : " + testData.get(p).get("IsPriorLossDamageRepaired?"+(i+1)));
					}else {
						if(priorLossData.get(i).get("IsCompleteRepairs").toString().equals("true"))
							Assertions.addInfo("Prior Loss Damage Repaired : " + priorLossData.get(i).get("IsCompleteRepairs").toString().replace("true", "Yes"), "Prior Loss Damage Repaired : " + testData.get(0).get("IsPriorLossDamageRepaired?"+(i+1)));

						else
							Assertions.addInfo("Prior Loss Damage Repaired : " + priorLossData.get(i).get("IsCompleteRepairs").toString().replace("false", "No"), "Prior Loss Damage Repaired : " + testData.get(0).get("IsPriorLossDamageRepaired?"+(i+1)));
					}
				}
				lossRepaired=true;

			}
		}
	} */
}
