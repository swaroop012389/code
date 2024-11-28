/** Program Description: Methods and queries built up for UwQuestion table
 *  Author			   : SMNetserv
 *  Date of Creation   : 17/02/2018
 **/

package com.icat.epicenter.dom;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;

public class UwQuestion {
	public String policyNumber;
	public String transactionId;

	public String uwQuestion;
	public String hvn;
	public String questionId;
	public String answer;

	//public List<Map<String, String>> expected;
	public List<Map<String, String>> expectedResults;
	public List<Map<String, Object>> results;
	private static List<Integer> testStatus;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public List<Map<String, Object>> uwQuestionResults;


	public UwQuestion(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("UwQuestion");
		uwQuestion = properties.getProperty("db_TableNameUWQuestion");

		hvn = properties.getProperty("db_Hvn");
		questionId = properties.getProperty("db_QuestionId");
		answer = properties.getProperty("db_Answer");
		questionId = properties.getProperty("db_QuestionId");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(uwQuestion);

	}

	public List<Map<String, Object>> getUWQuestionDetails(int transactionId) {
		List<String> outFields = new ArrayList<>();

		outFields.add(hvn);
		outFields.add(questionId);
		outFields.add(answer);

		build.outFields(outFields);
		build.whereBy("transactionId = " + transactionId + "order by questionId, answer");
		List<Map<String, Object>> uwQuestionDetails = build.execute(60);

		results = uwQuestionDetails;
		//skipping 225 (MSB Valuation) and 226 (Epicenter account number), as they will be different for every run - maybe save them in the NB_DT_Exe file?
		results.remove(questionId="225");
		results.remove(questionId="226");
		return results;
	}

	public List<Map<String, String>> getUWQuestionDataExpected(String tcid) {
		List<Map<String, String>> binderUWQuestionData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"UWQuestion").readExcelRowWise();

		ArrayList<Map<String, String>> TCData = new ArrayList<>();
		for (Map<String, String> element : binderUWQuestionData) {
			//may need to ignore question 225 - MSBValuation
			//may need to ignore question 226 - accoundId, as this will change with each run
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData.add(element);
			}
		}
		return TCData;

	}

	public String verifyUWQuestionDetails(List<Map<String, String>> binderData, int transactionId) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = new DecimalFormat("0");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		List<Map<String, Object>> aftershockValues = getUWQuestionDetails(transactionId);

		for (int i = 0; i < binderData.size(); i++) {

			Map<String, String> binderDataRow = binderData.get(i);
			Map<String, Object> aftershockRow = aftershockValues.get(i);

			//skipping 225 (MSB Valuation) and 226 (Epicenter account number), as they will be different for every run - maybe save them in the CommDataExeReport file?
			//currently deleting from the binder details, can add back if i eventually add them to the CommDataExeReport
			if (!(binderDataRow.get("QuestionId").toString().equals("226")) && !(binderDataRow.get("QuestionId").toString().equals("225"))) {
				testStatus = Assertions.verify(aftershockRow.get("Hvn").toString(), binderDataRow.get("HVN").toString(), "HVN : " + aftershockRow.get("Hvn"), "Expected : " + binderDataRow.get("HVN").toString(), false, false);
				testStatus = Assertions.verify(aftershockRow.get("QuestionId").toString(), binderDataRow.get("QuestionId").toString(), "QuestionId : " + aftershockRow.get("QuestionId"), "Expected : " + binderDataRow.get("QuestionId").toString(), false, false);
				String answer = aftershockRow.get("Answer").toString().split("\\.")[0];
				testStatus = Assertions.verify(aftershockRow.get("Answer").toString().split("\\.")[0], binderDataRow.get("Answer").toString().split("\\.")[0], "Answer : " + aftershockRow.get("Answer").toString().split("\\.")[0], "Expected : " + binderDataRow.get("Answer").toString().split("\\.")[0], false, false);
			}

		}

		if (testStatus.get(1) > 0) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}

}
