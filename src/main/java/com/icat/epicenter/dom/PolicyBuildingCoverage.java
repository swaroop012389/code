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

public class PolicyBuildingCoverage {
    public String policyBuildingCoverageTable;

    public String hvn;
    public String policyNumber;
    public String createDateTime;
    public String deleteDateTime;
    DBFrameworkConnection connection;
    QueryBuilder build;
    private static List<Integer> testStatus;
    public Map<String, Object> policyBuildingCoverage;


    public PolicyBuildingCoverage(DatabaseConfiguration dbConfig) {
        TableProperties properties = new TableProperties("PolicyBuildingCoverage");
        policyBuildingCoverageTable = properties.getProperty("db_PolicyBuildingCoverageTable");
        hvn = properties.getProperty("db_Hvn");
        policyNumber = properties.getProperty("db_PolicyNumber");
        createDateTime = properties.getProperty("db_CreateDateTime");
        deleteDateTime = properties.getProperty("db_DeleteDateTime");

        connection = new DBFrameworkConnection(dbConfig);
        build = new QueryBuilder(connection);
        build.tableName(policyBuildingCoverageTable);
    }

    public Map<String, Object> getPolicyBuildingCoverageDetails(int transactionId, int i) {
        List<String> outFields = new ArrayList<>();
        outFields.add(hvn);
        outFields.add(policyNumber);
        outFields.add(createDateTime);
        outFields.add(deleteDateTime);


        build.outFields(outFields);
        build.whereBy("transactionId = " + transactionId);
        List<Map<String, Object>> policyBuildingCoverageData = build.execute(60);
        return policyBuildingCoverage = policyBuildingCoverageData.get(i);
    }

    public List<Map<String, String>> getPolicyBuildingCoverageDataExpected(String tcid) {
        List<Map<String, String>> binderPolicyBuildingCoverageData = new SheetMatchedAccessManager(
                "./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
                "PolicyBuildingCoverage").readExcelRowWise();

        ArrayList<Map<String, String>> TCData = new ArrayList<>();
        for (Map<String, String> element : binderPolicyBuildingCoverageData) {
            if (element.get("TCID").equalsIgnoreCase(tcid)) {
                TCData.add(element);
            }
        }

        return TCData;

    }


    public String verifyPolicyBuildingCoverageData(List<Map<String, String>> binderData, int transactionId, String policyNumber) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        for (int i = 0; i < binderData.size(); i++) {

            Map<String, String> binderDataRow = binderData.get(i);
            Map<String, Object> aftershockRow = getPolicyBuildingCoverageDetails(transactionId, i);

            testStatus = Assertions.verify(aftershockRow.get("HVN").toString(), binderDataRow.get("HVN"), "HVN : " + aftershockRow.get("HVN").toString(), "Expected : " + binderDataRow.get("HVN"), false, false);
            testStatus = Assertions.verify(aftershockRow.get("PolicyNumber").toString(), policyNumber, "PolicyNumber : " + aftershockRow.get("PolicyNumber").toString(), "Expected : " + policyNumber, false, false);
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

}
