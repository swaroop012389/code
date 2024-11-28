/** Program Description: Methods and queries built up for Policy table
 *  Author			   : SMNetserv
 *  Date of Creation   : 23/02/2018
**/

package com.icat.epicenter.dom;

import java.text.DecimalFormat;
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

public class Policy {
	public String policy;
	public String policyNumber;

	public String hvn;
	public String quoteId;
	public String product;
	public String policyState;
	public String transactionCode;
	public String transactionDesc;
	public String transactionReasonTypeID;
	public String transactionReasonID;
	public String activeStatusCode;
	public String agentId;
	public String carrierId;
	public String policyMasterKey;
	public String isRewrite;
	public String previousPolicyNumber;
	public String policyInsuredId;
	public String policyEffectiveDate;
	public String transactionEffectiveDate;
	public String policyExpirationDate;
	public String binderInsuredName;
	public String binderDate;
	public String approvedBy;
	public String mocReceivedDate;
	public String paymentPlanId;
	public String firstPayment;
	public String tiv;
	public String inspectionFee;
	public String policyFee;
	public String feesTotal;
	public String premiumTotal;
	public String feeAdjustment;
	public String policyTotal;
	public String transactionPolicyFee;
	public String transactionInspectionFee;
	public String transactionPremium;
	public String transactionSurplusContribution;
	public String prorataFactor;
	public String annualTransactionPremium;
	public String annualPolicyPremium;
	public String applicationEmail;
	public String accountingEffectiveDate;
	public String ratingEffectiveDate;
	public String productVersion;
	public String isLegacy;
	public String createDateTime;
	public String esLicenseNumber;
	public String applicationComments;
	public Float epiAnnualTransactionPremium;
	public String epiAccountingEffectiveDate;

	//FWProperties property;
	public Map<String, String> TCData;
	public Map<String, Object> results;
	private static List<Integer> testStatus;
	DBFrameworkConnection connection;
	QueryBuilder build;
	public String transactionId;
	public Map<String, Object> policyResults;

	//maybe don't need these
	//public String surplusLicenceNumber;

	public Map<String, Object> cancellationResults;

	public Policy(DatabaseConfiguration dbConfig) {
		TableProperties properties = new TableProperties("Policy");
		policy = properties.getProperty("db_TableNamePolicy");
		policyNumber = properties.getProperty("db_PolicyNumber");
		transactionId = properties.getProperty("db_TransactionId");

		hvn = properties.getProperty("db_HVN");
		quoteId = properties.getProperty("db_QuoteId");
		product = properties.getProperty("db_Product");
		policyState = properties.getProperty("db_PolicyState");
		transactionCode = properties.getProperty("db_TransactionCode");
		transactionDesc = properties.getProperty("db_TransactionDesc");
		transactionReasonTypeID = properties.getProperty("db_TransactionReasonTypeID");
		transactionReasonID = properties.getProperty("db_TransactionReasonID");
		activeStatusCode = properties.getProperty("db_ActiveStatusCode");
		agentId = properties.getProperty("db_AgentId");
		carrierId = properties.getProperty("db_CarrierId");
		policyMasterKey = properties.getProperty("db_PolicyMasterKey");
		isRewrite = properties.getProperty("db_IsRewrite");
		previousPolicyNumber = properties.getProperty("db_PreviousPolicyNumber");
		policyInsuredId = properties.getProperty("db_PolicyInsuredId");
		policyEffectiveDate = properties.getProperty("db_PolicyEffectiveDate");
		transactionEffectiveDate = properties.getProperty("db_TransactionEffectiveDate");
		policyExpirationDate = properties.getProperty("db_PolicyExpirationDate");
		binderInsuredName = properties.getProperty("db_BinderInsuredName");
		binderDate = properties.getProperty("db_BinderDate");
		approvedBy = properties.getProperty("db_ApprovedBy");
		mocReceivedDate = properties.getProperty("db_MOCReceivedDate");
		paymentPlanId = properties.getProperty("db_PaymentPlanId");
		firstPayment = properties.getProperty("db_FirstPayment");
		tiv = properties.getProperty("db_TIV");
		inspectionFee = properties.getProperty("db_InspectionFee");
		policyFee = properties.getProperty("db_PolicyFee");
		feesTotal = properties.getProperty("db_FeesTotal");
		premiumTotal = properties.getProperty("db_PremiumTotal");
		feeAdjustment = properties.getProperty("db_FeeAdjustment");
		policyTotal = properties.getProperty("db_PolicyTotal");
		transactionPolicyFee = properties.getProperty("db_TransactionPolicyFee");
		transactionInspectionFee = properties.getProperty("db_TransactionInspectionFee");
		transactionPremium = properties.getProperty("db_TransactionPremium");
		prorataFactor = properties.getProperty("db_ProRataFactor");
		annualTransactionPremium = properties.getProperty("db_AnnualTransactionPremium");
		annualPolicyPremium = properties.getProperty("db_AnnualPolicyPremium");
		applicationEmail = properties.getProperty("db_ApplicationEmail");
		accountingEffectiveDate = properties.getProperty("db_AccountingEffectiveDate");
		ratingEffectiveDate = properties.getProperty("db_RatingEffectiveDate");
		productVersion = properties.getProperty("db_ProductVersion");
		isLegacy = properties.getProperty("db_IsLegacy");
		createDateTime = properties.getProperty("db_CreateDateTime");
		esLicenseNumber = properties.getProperty("db_ESLicenceNumber");
		applicationComments = properties.getProperty("db_ApplicationComments");

		connection = new DBFrameworkConnection(dbConfig);
		build = new QueryBuilder(connection);
		build.tableName(policy);
	}


	public int getTransactionId(String policyNum, int hvn) {
		System.out.println("getTransactionId hvn = " + hvn);
		System.out.println("policyNum = " + policyNum);
		return (int) build.fetch(transactionId, policy,
				policyNumber + " = '" + policyNum + "'" + " and hvn = '" + hvn + "'");
	}

	public double getTransactionPremium(String policyNum, int hvn) {
		return ((int) (double) build.fetch(transactionPremium, policy,
				policyNumber + " = '" + policyNum + "'" + " and hvn = '" + hvn + "'"));
	}

	public Map<String, Object> getPolicyDetails(String policyNum, int hvn) {
		ArrayList<String> outfield = new ArrayList<>();

		outfield.add(quoteId);
		outfield.add(product);
		outfield.add(policyState);
		outfield.add(transactionCode);
		outfield.add(transactionDesc);
		outfield.add(transactionReasonTypeID);
		outfield.add(transactionReasonID);
		outfield.add(activeStatusCode);
		outfield.add(agentId);
		outfield.add(carrierId);
		outfield.add(policyMasterKey);
		outfield.add(isRewrite);
		outfield.add(previousPolicyNumber);
		outfield.add(policyInsuredId);
		outfield.add(policyEffectiveDate);
		outfield.add(transactionEffectiveDate);
		outfield.add(policyExpirationDate);
		outfield.add(binderInsuredName);
		outfield.add(binderDate);
		outfield.add(approvedBy);
		outfield.add(mocReceivedDate);
		outfield.add(paymentPlanId);
		outfield.add(firstPayment);
		outfield.add(tiv);
		outfield.add(inspectionFee);
		outfield.add(policyFee);
		outfield.add(feesTotal);
		outfield.add(premiumTotal);
		outfield.add(feeAdjustment);
		outfield.add(policyTotal);
		outfield.add(transactionPolicyFee);
		outfield.add(transactionInspectionFee);
		outfield.add(transactionPremium);
		outfield.add(prorataFactor);
		outfield.add(annualTransactionPremium);
		outfield.add(annualPolicyPremium);
		outfield.add(applicationEmail);
		outfield.add(accountingEffectiveDate);
		outfield.add(ratingEffectiveDate);
		outfield.add(productVersion);
		outfield.add(isLegacy);
		outfield.add(createDateTime);
		outfield.add(esLicenseNumber);
		outfield.add(applicationComments);

		build.outFields(outfield);
		build.whereBy("policyNumber = " + "'" + policyNum + "'" + " and hvn" + " =" + hvn);

		List<Map<String, Object>> policyDetails = build.execute(60);
		if (policyDetails.isEmpty())
			return null;
		else
			return policyResults = policyDetails.get(0);
	}



	public Map<String, String> getPolicyDataExpected(String tcid) {
		List<Map<String, String>> binderPolicyData = new SheetMatchedAccessManager(
				"./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls",
				"Policy").readExcelRowWise();

		for (Map<String, String> element : binderPolicyData) {
			if (element.get("TCID").equalsIgnoreCase(tcid)) {
				TCData = element;
				break;
			}
		}
		return TCData;

	}

	public String verifyPolicyDetails(Map<String, String> binderData, String policyNumber) {
		DecimalFormat df = new DecimalFormat("#.##");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		//ExcelReport excelReport = new ExcelReport();

		//get the aftershock values
		policyResults = getPolicyDetails(policyNumber, Integer.parseInt(binderData.get("HVN")));
		System.out.println(policyResults.get("TransactionDescription"));


		//aftershock doesn't use the full quote number for QuoteId, just the digits, remove the first three characters
		String expectedQuoteId = ExcelReportUtil.getInstance().getLatestQuoteFromMaster(binderData.get("TCID")).substring(3);
		testStatus = Assertions.verify(policyResults.get("QuoteId").toString(), expectedQuoteId, "Quote Number : " + policyResults.get("QuoteId"), "Expected : " + expectedQuoteId, false, false);
		testStatus = Assertions.verify(policyResults.get("Product"), binderData.get("Product"), "Product : " + policyResults.get("Product"), "Expected : " + binderData.get("Product"), false, false);
		testStatus = Assertions.verify(policyResults.get("PolicyState"), binderData.get("PolicyState"), "PolicyState : " + policyResults.get("PolicyState"), "Expected : " + binderData.get("PolicyState"), false, false);
		testStatus = Assertions.verify(policyResults.get("TransactionCode").toString().trim(), binderData.get("TransactionCode").trim(), "TransactionCode : " + policyResults.get("TransactionCode"), "Expected : " + binderData.get("TransactionCode"), false, false);
		testStatus = Assertions.verify(policyResults.get("TransactionDescription"), binderData.get("TransactionDescription"), "TransactionDescription : " + policyResults.get("TransactionDescription"), "Expected : " + binderData.get("TransactionDescription"), false, false);
		testStatus = Assertions.verify(policyResults.get("TransactionReasonTypeID").toString(), binderData.get("TransactionReasonTypeId"), "TransactionReasonTypeID: " + policyResults.get("TransactionReasonTypeID"), "Expected : " + binderData.get("TransactionReasonTypeId"), false, false);
		testStatus = Assertions.verify(policyResults.get("TransactionReasonID").toString(), binderData.get("TransactionReasonId"), "TransactionReasonId: " + policyResults.get("TransactionReasonID"), "Expected : " + binderData.get("TransactionReasonId"), false, false);
		testStatus = Assertions.verify(policyResults.get("ActiveStatusCode"), binderData.get("ActiveStatusCode"), "ActiveStatusCode : " + policyResults.get("ActiveStatusCode"), "Expected : " + binderData.get("ActiveStatusCode"), false, false);

		//the masterproducer value will change with qa refreshes, make sure that the producer number is in the string
		Boolean agentIdResult = policyResults.get("AgentId").toString().contains(binderData.get("AgentId").toString());
		testStatus = Assertions.verify(agentIdResult, true, "AgentId : " + policyResults.get("AgentId"), "Expected : " + binderData.get("AgentId"), false, false);

		//TODO - can't get expected from front end, maybe there will be a default after reciprocal...  maybe query epicenter database.
		//testStatus = Assertions.verify(policyResults.get("CarrierId"), binderData.get("CarrierId"), "CarrierId : " + policyResults.get("CarrierId"), "Expected : " + binderData.get("CarrierId"), false, false);
		testStatus = Assertions.verify(policyResults.get("PolicyMasterKey").toString(), binderData.get("PolicyMasterKey").toString(), "PolicyMasterKey : " + policyResults.get("PolicyMasterKey"), "Expected : " + binderData.get("PolicyMasterKey"), false, false);
		testStatus = Assertions.verify(policyResults.get("IsRewrite").toString(), binderData.get("IsRewrite").toString(), "IsRewrite : " + policyResults.get("IsRewrite"), "Expected : " + binderData.get("IsRewrite"), false, false);

		//in the old framework, i just get this insuredId for subsequent queries, Heidi confirmed we don't need to verify
		//testStatus = Assertions.verify(policyResults.get("PolicyInsuredId"), binderData.get("PolicyInsuredId"), "PolicyInsuredId : " + policyResults.get("PolicyInsuredId"), "Expected : " + binderData.get("PolicyInsuredId"), false, false);

		String epiPreviousPolicyNumber = ExcelReportUtil.getInstance().getLatestPreviousPolicyNumberFromMaster(binderData.get("TCID"));
		testStatus = Assertions.verify(policyResults.get("PreviousPolicyNumber"), epiPreviousPolicyNumber, "PreviousPolicyNumber : " + policyResults.get("PreviousPolicyNumber"), "Expected : " + epiPreviousPolicyNumber, false, false);

		String epiPolicyEffectiveDate = ExcelReportUtil.getInstance().getLatestPolicyEffectiveDateFromMaster(binderData.get("TCID"));
		LocalDate asEffDate = LocalDate.parse(String.valueOf(policyResults.get("PolicyEffectiveDate")).replace(" 00:00:00.0", ""));
		testStatus = Assertions.verify(asEffDate.format(f), epiPolicyEffectiveDate, "PolicyEffectiveDate : " + asEffDate.format(f), "Expected : " + epiPolicyEffectiveDate, false, false);

		String epiTransactionEffectiveDate = ExcelReportUtil.getInstance().getLatestTransactionDateFromMaster(binderData.get("TCID"));
		LocalDate asTxnDate = LocalDate.parse(String.valueOf(policyResults.get("TransactionEffectiveDate")).replace(" 00:00:00.0", ""));
		testStatus = Assertions.verify(asTxnDate.format(f), epiTransactionEffectiveDate, "TransactionEffectiveDate : " + asTxnDate.format(f), "Expected : " + epiTransactionEffectiveDate, false, false);

		String epiPolicyExpirationDate = ExcelReportUtil.getInstance().getLatestPolicyExpirationDateFromMaster(binderData.get("TCID"));
		LocalDate asExpDate = LocalDate.parse(String.valueOf(policyResults.get("PolicyExpirationDate")).replace(" 00:00:00.0", ""));
		if (!binderData.get("TransactionReasonTypeId").equals("10095") && !binderData.get("TransactionReasonTypeId").equals("10100")) {
			testStatus = Assertions.verify(asExpDate.format(f), epiPolicyExpirationDate, "PolicyExpirationDate : " + asExpDate.format(f), "Expected : " + epiPolicyExpirationDate, false, false);
		}
		String epiBinderInsuredName = ExcelReportUtil.getInstance().getLatestInsuredNameFromMaster(binderData.get("TCID"));
		testStatus = Assertions.verify(policyResults.get("BinderInsuredName"), epiBinderInsuredName, "BinderInsuredName : " + policyResults.get("BinderInsuredName"), "Expected : " + epiBinderInsuredName, false, false);

		String epiBinderDate = ExcelReportUtil.getInstance().getLatestBindRequestDateFromMaster(binderData.get("TCID"));
		LocalDate asBinderDate = LocalDate.parse(String.valueOf(policyResults.get("BinderDate")).substring(0,10));
		testStatus = Assertions.verify(asBinderDate.format(f), epiBinderDate, "BinderDate : " + asBinderDate.format(f), "Expected : " + epiBinderDate, false, false);

		testStatus = Assertions.verify(policyResults.get("ApprovedBy"), binderData.get("ApprovedBy"), "ApprovedBy : " + policyResults.get("ApprovedBy"), "Expected : " + binderData.get("ApprovedBy"), false, false);

		String epiProcessDate = ExcelReportUtil.getInstance().getLatestProcessingDateFromMaster(binderData.get("TCID"));
		LocalDate asMOCDate = LocalDate.parse(String.valueOf(policyResults.get("MOCReceivedDate")).substring(0,10));
		testStatus = Assertions.verify(asMOCDate.format(f), epiProcessDate, "MOCReceivedDate : " + asMOCDate.format(f), "Expected : " + epiProcessDate, false, false);

		testStatus = Assertions.verify(policyResults.get("PaymentPlanId").toString(), binderData.get("PaymentPlanId"), "PaymentPlanId : " + policyResults.get("PaymentPlanId"), "Expected : " + binderData.get("PaymentPlanId"), false, false);

		//getting object back, but it's really a double, casting doesn't work
		//get rid of scientific notation and decimal value without BigDecimal or Double methods
		System.out.println("TIV string = " + policyResults.get("TIV"));
		String tivString = policyResults.get("TIV").toString();
		String fixedString = "";
		if (policyResults.get("TIV").toString().contains("E")) {
			fixedString = tivString.split("E")[0].replace(".", "");
		} else {
			fixedString = tivString.replace(".0", "");
		}
		System.out.println("fixedString = " + fixedString);
		testStatus = Assertions.verify(fixedString, binderData.get("TIV"), "TIV : " + fixedString, "Expected : " + binderData.get("TIV"), false, false);

		String epiInspFee = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Inspection Fee").replace(".00", "");
		testStatus = Assertions.verify(policyResults.get("InspectionFee").toString().replace(".0", ""), epiInspFee, "InspectionFee : " + policyResults.get("InspectionFee").toString().replace(".0", ""), "Expected : " + epiInspFee, false, false);

		String epiPolFee = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Policy Fee").replace(".00", "");
		testStatus = Assertions.verify(policyResults.get("PolicyFee").toString().replace(".0", ""), epiPolFee, "PolicyFee : " + policyResults.get("PolicyFee").toString().replace(".0", ""), "Expected : " + epiPolFee, false, false);

		String epiFeesTotal = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Fees Total");
		Double feesTotalExpected = Double.valueOf(epiFeesTotal);
		Double asFeesTotal = Double.valueOf(policyResults.get("FeesTotal").toString());
		testStatus = Assertions.verify(df.format(asFeesTotal), df.format(feesTotalExpected), "FeesTotal : " + asFeesTotal, "Expected : " + feesTotalExpected, false, false);

		String epiPremTotal = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Premium Total");
		testStatus = Assertions.verify(policyResults.get("PremiumTotal").toString().replace(".0", ""), epiPremTotal.replace(".0", ""), "PremiumTotal : " + policyResults.get("PremiumTotal").toString().replace(".0", ""), "Expected : " + epiPremTotal.replace(".0", ""), false, false);

		testStatus = Assertions.verify(policyResults.get("FeeAdjustment").toString().replace(".0", ""), binderData.get("FeeAdjustment"), "FeeAdjustment : " + policyResults.get("FeeAdjustment").toString().replace(".0", ""), "Expected : " + binderData.get("FeeAdjustment"), false, false);

		String epiPolTotal = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Policy Total");
		testStatus = Assertions.verify(policyResults.get("PolicyTotal").toString().replace(".0", ""), epiPolTotal.replace(".0", ""), "PolicyTotal : " + policyResults.get("PolicyTotal").toString().replace(".0", ""), "Expected : " + epiPolTotal.replace(".0", ""), false, false);

		String epiTxnPolFee = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Transaction Policy Fee");
		if (!epiTxnPolFee.equals("N/A")) {
			testStatus = Assertions.verify(policyResults.get("TransactionPolicyFee").toString().split("\\.")[0], epiTxnPolFee.split("\\.")[0], "TransactionPolicyFee : " + policyResults.get("TransactionPolicyFee").toString().split("\\.")[0], "Expected : " + epiTxnPolFee.split("\\.")[0], false, false);
		}

		String epiTxnInspFee = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Transaction Inspection Fee");
		if (!epiTxnInspFee.equals("N/A")) {
			testStatus = Assertions.verify(policyResults.get("TransactionInspectionFee").toString().split("\\.")[0], epiTxnInspFee.split("\\.")[0], "TransactionInspectionFee : " + policyResults.get("TransactionInspectionFee").toString().split("\\.")[0], "Expected : " + epiTxnInspFee.split("\\.")[0], false, false);
		}

		String epiTxnPrem = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Transaction Premium");
		testStatus = Assertions.verify(policyResults.get("TransactionPremium").toString().split("\\.")[0], epiTxnPrem.split("\\.")[0], "TransactionPremium : " + policyResults.get("TransactionPremium").toString().split("\\.")[0], "Expected : " + epiTxnPrem.split("\\.")[0], false, false);

		String epiProRataFactor = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "ProRata Factor");
		//don't check for N/A, saved as double 9.999
		System.out.println("epiProRataFactor = " + epiProRataFactor);
		System.out.println("policyResults.get(ProRataFactor) = " + policyResults.get("ProRataFactor"));
		if (!epiProRataFactor.equals("9.999")) {
			testStatus = Assertions.verify(policyResults.get("ProRataFactor").toString().substring(0,4), epiProRataFactor.substring(0,4), "ProRataFactor : " + policyResults.get("ProRataFactor").toString().substring(0,4), "Expected : " + epiProRataFactor.substring(0,4), false, false);
		}

		String epiAnnualTxnPrem = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Annual Transaction Premium");
		String asAnnualTxnPrem = policyResults.get("AnnualTransactionPremium").toString().replace(".0", "");
		if (!epiAnnualTxnPrem.equals("0") && !asAnnualTxnPrem.equals("0")) {
			Float percentDifference = Math.abs(((Float.valueOf(asAnnualTxnPrem) - Float.valueOf(epiAnnualTxnPrem)) / Float.valueOf(epiAnnualTxnPrem)) * 100);
			Boolean noBigDiff = Boolean.valueOf(percentDifference < 10.000);
			testStatus = Assertions.verify(noBigDiff, true, "Percent Difference : " + percentDifference, "Expected Annual Txn Premium : " + epiAnnualTxnPrem, false, false);
		}
		String epiAnnualPolPrem;
		if (!binderData.get("TransactionCode").contains("Z") && binderData.get("TransactionReasonTypeId").equals("10095")) {
			epiAnnualPolPrem = "0";
		} else {
			epiAnnualPolPrem = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Annual Policy Premium");
		}

		testStatus = Assertions.verify(policyResults.get("AnnualPolicyPremium").toString().split("\\.")[0], epiAnnualPolPrem.split("\\.")[0], "AnnualPolicyPremium : " + policyResults.get("AnnualPolicyPremium").toString().split("\\.")[0], "Expected : " + epiAnnualPolPrem.split("\\.")[0], false, false);
		testStatus = Assertions.verify(policyResults.get("ApplicationEmail"), binderData.get("ApplicationEmail"), "ApplicationEmail : " + policyResults.get("ApplicationEmail"), "Expected : " + binderData.get("ApplicationEmail"), false, false);

		//this is the greater of the processing date (epiProcessDate string above) and transaction date (epiTransactionEffectiveDate string above)
		LocalDate epiProcessDateCheck = LocalDate.parse(epiProcessDate, f);
		LocalDate epiTransactionDateCheck = LocalDate.parse(epiTransactionEffectiveDate, f);
		if (epiProcessDateCheck.isBefore(epiTransactionDateCheck)) {
			epiAccountingEffectiveDate = epiTransactionEffectiveDate;
		} else {
			epiAccountingEffectiveDate = epiProcessDate;
		}
		LocalDate asAccEffDate = LocalDate.parse(String.valueOf(policyResults.get("AccountingEffectiveDate")).substring(0,10));
		testStatus = Assertions.verify(asAccEffDate.format(f), epiAccountingEffectiveDate, "AccountingEffectiveDate : " + asAccEffDate.format(f), "Expected : " + epiAccountingEffectiveDate, false, false);

		//got the rating effective date from the admin and saved to the excel report
		String epiRatingEffDate = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Rating Effective Date");
		LocalDate asRatingEffDate = LocalDate.parse(String.valueOf(policyResults.get("RatingEffectiveDate")).substring(0,10));
		testStatus = Assertions.verify(asRatingEffDate.format(f), epiRatingEffDate, "RatingEffectiveDate : " + asRatingEffDate.format(f), "Expected : " + epiRatingEffDate, false, false);

		testStatus = Assertions.verify(policyResults.get("ProductVersion").toString(), binderData.get("ProductVersion").toString(), "ProductVersion : " + policyResults.get("ProductVersion"), "Expected : " + binderData.get("ProductVersion"), false, false);
		testStatus = Assertions.verify(policyResults.get("IsLegacy"), binderData.get("IsLegacy"), "IsLegacy : " + policyResults.get("IsLegacy"), "Expected : " + binderData.get("IsLegacy"), false, false);

		//using epicenter processing date
		LocalDate asCreateDateTime = LocalDate.parse(String.valueOf(policyResults.get("CreateDateTime")).substring(0,10));
		testStatus = Assertions.verify(asCreateDateTime.format(f), epiProcessDate, "CreateDateTime : " + asCreateDateTime.format(f), "Expected : " + epiProcessDate, false, false);

		String product = ExcelReportUtil.getInstance().getLatestValueFromMaster(binderData.get("TCID"), "Product");
		if (!product.contains("Retail")) {
			testStatus = Assertions.verify(policyResults.get("ESLicenseNumber"), binderData.get("ESLicenseNumber"), "ESLicenseNumber : " + policyResults.get("ESLicenseNumber"), "Expected : " + binderData.get("ESLicenseNumber"), false, false);
		}
		testStatus = Assertions.verify(policyResults.get("ApplicationComments").toString().replace("\n", " "), binderData.get("ApplicationComments"), "ApplicationComments : " + policyResults.get("ApplicationComments"), "Expected : " + binderData.get("ApplicationComments"), false, false);


		if(testStatus.get(1)>0)
		{
			return "FAIL";
		}
		else {
			return "PASS";
		}
	}


}
