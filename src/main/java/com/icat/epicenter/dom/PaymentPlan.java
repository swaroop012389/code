/** Program Description: Methods and queries built up for PaymentPlan table
 *  Author			   : SMNetserv
 *  Date of Creation   : 21/02/2018
**/

package com.icat.epicenter.dom;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.DBFrameworkConnection;
import com.NetServAutomationFramework.generic.QueryBuilder;
import com.NetServAutomationFramework.generic.TableProperties;

public class PaymentPlan {
	public String paymentPlanTable;
	public String paymentPlanId;
	public String description;
	DBFrameworkConnection connection;
	QueryBuilder build;

	public PaymentPlan(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("PaymentPlan");
		paymentPlanTable = properties.getProperty("db_TableNamePaymentPlan");
		paymentPlanId = properties.getProperty("db_PaymentPlanId");
		description = properties.getProperty("db_Description");
		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(paymentPlanTable);
	}

	public Object getPaymentPlanDescription(short paymentPlanIdData) {
		return build.fetch(description, paymentPlanTable, paymentPlanId + " = " + paymentPlanIdData);
	}

	public void verifyPaymentPlanDetails(short paymentPlanId, List<Map<String, String>> pnbTestData,
			int transactionNumber) {
		String paymentDescription = (String) getPaymentPlanDescription(paymentPlanId);
		String paymentKey = null;
		boolean paymentPlan_info = false;
		for (int p = transactionNumber; p >= 0; p--) {
			if (pnbTestData.get(p).get("SinglePay").equalsIgnoreCase("Yes")) {
				paymentKey = "SinglePay";
			} else if (pnbTestData.get(p).get("MortgageePay").equalsIgnoreCase("Yes")) {
				paymentKey = "MortgageePay";
			} else if (pnbTestData.get(p).get("4Pay").equalsIgnoreCase("Yes")) {
				paymentKey = "4Pay";
			}
			if (paymentKey == null) {
				continue;
			}
			if (p == transactionNumber && !paymentPlan_info) {
				if (paymentKey.contains("SinglePay")) {
					Assertions.verify(paymentDescription.replace("Retail: Full-", "Single"), paymentKey,
							"Payment Plan : " + paymentDescription.replace("Retail: Full-", "Single"),
							"Payment Plan : " + paymentKey, false, false);
				} else if (paymentDescription.contains("MortgageePay")) {
					Assertions.verify(paymentDescription.replace("Retail: Mortgagee-", "Mortgagee"), paymentKey,
							"Payment Plan : " + paymentDescription.replace("Retail: Mortgagee-", "Mortgagee"),
							"Payment Plan : " + paymentKey, false, false);
				} else {
					Assertions.verify(paymentDescription.replace("Retail: Optional-", "4"), paymentKey,
							"Payment Plan : " + paymentDescription.replace("Retail: Optional-", "4"),
							"Payment Plan : " + paymentKey, false, false);
				}
				paymentPlan_info = true;
			} else if (!paymentPlan_info) {
				if (paymentKey.contains("SinglePay")) {
					Assertions.addInfo("Payment Plan : " + paymentDescription.replace("Retail: Full-", "Single"),
							"Payment Plan : " + paymentKey);
				} else if (paymentDescription.contains("MortgageePay")) {
					Assertions.addInfo(
							"Payment Plan : " + paymentDescription.replace("Retail: Mortgagee-", "Mortgagee"),
							"Payment Plan : " + paymentKey);
				} else {
					Assertions.addInfo("Payment Plan : " + paymentDescription.replace("Retail: Optional-", "4"),
							"Payment Plan : " + paymentKey);
				}
				paymentPlan_info = true;
			}
		}
	}
}
