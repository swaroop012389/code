/** Program Description: Commercial Aftershock Verification
 *  Author			   : Tiffany Fodor
 *  Date of Creation   : 12/18/2023
 **/

package com.icat.epicenter.test.commercial.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.database.DatabaseConfiguration;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.config.TestType;
import com.icat.epicenter.dom.AdditionalInterest;
import com.icat.epicenter.dom.AddressIntl;
import com.icat.epicenter.dom.Building;
import com.icat.epicenter.dom.BuildingCoverage;
import com.icat.epicenter.dom.ConsolidatedCodeTable;
import com.icat.epicenter.dom.Insured;
import com.icat.epicenter.dom.LOCDeductible;
import com.icat.epicenter.dom.LOCExtension;
import com.icat.epicenter.dom.LineOfCoverage;
import com.icat.epicenter.dom.Location;
import com.icat.epicenter.dom.Policy;
import com.icat.epicenter.dom.PolicyBuildingCoverage;
import com.icat.epicenter.dom.PolicyCommission;
import com.icat.epicenter.dom.PolicyLineOfBusiness;
import com.icat.epicenter.dom.PolicyLocation;
import com.icat.epicenter.dom.PriorLoss;
import com.icat.epicenter.dom.RMSDATA;
import com.icat.epicenter.dom.SpecialClass;
import com.icat.epicenter.dom.UwQuestion;

//LiabilityRatingAttribute is GL only
//import com.icat.epicenter.dom.LiabilityRatingAttribute;

//import com.icat.epicenter.dom.PolicyBuildingCoverage;


import com.icat.epicenter.test.AbstractDataTest;
import com.icat.epicenter.utils.ExcelReportUtil;

public class CommercialDatabaseValidationTest extends AbstractDataTest {
	public static final String PNBCOMMERCIALBINDERTESTFILEPATH = "./src/test/resources/testdata/commercial/data/Commercial-SecondaryTransactionsBinderDetails.xls";
	//private static final String RRLOCATIONBINDERSHEETNAME = "Location";
	//private static final String RRBUILDINGBINDERTESTDATASHEETNAME = "BuildingData";
	//private static final String RRCOVERAGEBINDERSHEETNAME = "Coverages";

	private PolicyLineOfBusiness policyLineOfBusiness;
	private Policy policyData;
	private Insured insured;
	private PolicyCommission policyCommission;
	private Location location;
	private PolicyLocation policyLocation;
	private UwQuestion uwQuestion;
	private LOCExtension locExtension;
	private Building building;
	private BuildingCoverage buildingCoverage;
	private PolicyBuildingCoverage policyBuildingCoverage;
	private AdditionalInterest additionalInterest;
	private PriorLoss priorLoss;
	private RMSDATA rmsData;
	private LOCDeductible locDeductible;
	private LineOfCoverage lineOfCoverage;
	private SpecialClass specialClass;
	private AddressIntl addressIntl;
	//TODO Add LOCMultiState
	//private LOCMultiState locMultiState;
	//private HVN hvn;

	private ConsolidatedCodeTable consolidated;

	private String testStatus;
	private List<String> testStatusList;
	List<Integer> testStatusListTemp;
	Double test_prem;

	public CommercialDatabaseValidationTest() {
		super(TestType.COMMERCIAL, LoginType.USM, "NBTC");
	}

	@Override
	protected int getSetupColumnNumber() {
		return 2;
	}

	/**
	 * Sheet name is on the first page of the data spreadsheet.
	 */
	@Override
	protected String getSheetName(String testName, List<Map<String, String>> testSetup) {
		return testSetup.get(3).get(testName);
	}

	@Override
	protected String getReportName() {
		return "Commercial Database Validation Tests";
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/data/CommercialDataTestMaster.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> testData) {
		String tcid = testData.get(0).get("TCID");
		String policyNumber = ExcelReportUtil.getInstance().getLatestPolicyFromMaster(tcid + "_Trx1");
		pnbDbExecute(testData, policyNumber, true, tcid, loadTestSetup(), EnvironmentDetails.getEnvironmentDetails().getAftershockDetails());
	}

	public String pnbDbExecute(List<Map<String, String>> pnbtTCData, String policyNumber, boolean master,
							   String tcid, List<Map<String, String>> testDataSetup, DatabaseConfiguration dbConfig) {
		policyLineOfBusiness = new PolicyLineOfBusiness(dbConfig);
		policyData = new Policy(dbConfig);
		insured = new Insured(dbConfig);
		policyCommission = new PolicyCommission(dbConfig);
		location = new Location(dbConfig);
		policyLocation = new PolicyLocation(dbConfig);
		uwQuestion = new UwQuestion(dbConfig);
		locExtension = new LOCExtension(dbConfig);
		building = new Building(dbConfig);
		buildingCoverage = new BuildingCoverage(dbConfig);
		policyBuildingCoverage = new PolicyBuildingCoverage(dbConfig);
		additionalInterest = new AdditionalInterest(dbConfig);
		priorLoss = new PriorLoss(dbConfig);
		rmsData = new RMSDATA(dbConfig);
		locDeductible = new LOCDeductible(dbConfig);
		lineOfCoverage = new LineOfCoverage(dbConfig);
		specialClass = new SpecialClass(dbConfig);
		addressIntl = new AddressIntl(dbConfig);

		//carrier = new Carrier(dbConfig);
		//lineOfBusines = new LineOfBusiness(dbConfig);
		//peril = new Peril(dbConfig);
		//policyTyp = new PolicyType(dbConfig);
		//agentDetails = new Agent(dbConfig);
		consolidated = new ConsolidatedCodeTable(dbConfig);


		//locDed = new LocDeductible(dbConfig);
		//payment_plan = new PaymentPlan(dbConfig);
		//ques = new Question(dbConfig);
		//ioPremTaxFee=new IOPremTaxFee(dbConfig);
		testStatusList = new ArrayList<>();

		for (int i = 1; i < pnbtTCData.size() + 1; i++) {

			String transactionType = pnbtTCData.get(i-1).get("TransactionType");
			//skip OOS reversals - we don't currently check them, but could later.  the tricky part will be checking buildings that were deleted previously
			if (!transactionType.contains("OOS Reversal")) {
				String spreadsheet_txn = pnbtTCData.get(i - 1).get("TransactionNum");
				policyNumber = ExcelReportUtil.getInstance().getLatestPolicyFromMaster(pnbtTCData.get(i - 1).get("TCID") + "_Trx" + (spreadsheet_txn));
				System.out.println("policyNumber = " + policyNumber);

				List<Map<String, String>> hvnBinderData = gethvnBinderDataforPNB(pnbtTCData.get(i - 1).get("TCID") + "_Trx" + (spreadsheet_txn));

				int transactionId = policyData.getTransactionId(policyNumber, Integer.valueOf(hvnBinderData.get(0).get("HVN")));
				System.out.println("transactionId = " + transactionId);

				testStatus = dbValidation(pnbtTCData, policyNumber, master, tcid, testDataSetup, Integer.parseInt(spreadsheet_txn), transactionId);
			}
		}
		return testStatus;
	}

	public String dbValidation(List<Map<String, String>> pnbtTCData, String policyNumber, boolean master,
							   String tcid, List<Map<String, String>> testDataSetup, int i, int txnId) {
		//try {
		/*if (pnbtTCData.get(i-1).get("TransactionType").contains("Revers")) {
			verifyOOSTransaction(i, policyNumber, pnbtTCData.get(i-1).get("TCID"));
		}*/
		Assertions.addInfo("***POLICY #" + policyNumber + ", Transaction " + i, "");
		System.out.println(" TransactionType = " + pnbtTCData.get(i-1).get("TransactionType"));
		System.out.println(tcid+" Transaction " + (i) + " Started");


		//***POLICYLINEOFBUSINESS TABLE***  only test for New Business
		if (pnbtTCData.get(i-1).get("TransactionType").equalsIgnoreCase("New Business")) {
			verifyPolicyLineOfBusinessData(pnbtTCData.get(i-1), testDataSetup, policyNumber);
		}

		//***POLICY TABLE***
		verifyPolicyDetails(pnbtTCData.get(i-1), policyNumber, i);

		//***INSURED TABLE***
		verifyInsuredDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***POLICYCOMMISSION TABLE***
		verifyPolicyCommissionDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***LOCATION TABLE***
		System.out.println("pnbtTCData.get(i-1) = " + pnbtTCData.get(i-1));
		verifyLocationDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***POLICYLOCATION TABLE***
		verifyPolicyLocationDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***UWQUESTION TABLE***
		//only on Wind/AOP
		if (!pnbtTCData.get(i-1).get("Peril").equals("EQ")) {
			verifyUWQuestionDetails(pnbtTCData.get(i - 1), policyNumber, i, txnId);
		}

		//***LOCEXTENSION TABLE***
		verifyLocExtensionDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***BUILDING TABLE***
		verifyBuildingDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***BUILDINGCOVERAGE TABLE***
		verifyBuildingCoverageDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***POLICYBUILDINGCOVERAGE TABLE***
		verifyPolicyBuildingCoverageDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***ADDITIONALINTEREST TABLE***
		verifyAdditionalInterestDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***PRIORLOSS TABLE***
		verifyPriorLossDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***RMSDATA TABLE***
		verifyRMSDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***LOCDEDUCTIBLE TABLE***
		verifyLOCDeductibleDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***LINEOFCOVERAGE TABLE***
		verifyLineOfCoverageDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***SPECIALCLASS TABLE***
		verifySpecialClassDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);

		//***ADDRESSINTL TABLE***
		verifyAddressIntlDetails(pnbtTCData.get(i-1), policyNumber, i, txnId);


		testStatusList.add(testStatus);
		Assertions.resetTestStepCount();

		//List<Map<String, String>> hvnBinderData = gethvnBinderDataforPNB(pnbtTCData.get(i-1).get("TCID") + "_Trx" + (i));


		/*} catch (Exception e) {
			Assertions.exceptionErrorWithoutScreenshot("Error in pnbDBExecute method", e.toString());
		}*/
		if (testStatusList.contains("FAIL")) {
			return "FAIL";
		} else {
			return "PASS";
		}
	}



	//***POLICYLINEOFBUSINESS TABLE***  only check new business so, just transaction 1
	public void verifyPolicyLineOfBusinessData(Map<String, String> pnbtTCData, List<Map<String, String>> testDataSetup, String policyNumber) {
		Assertions.addInfo("***POLICYLINEOFBUSINESS TABLE DB VALIDATION*** - New Business Txn Only", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" ***", "");
		Map<String, String> binderData = policyLineOfBusiness.getPolicyLineOfBusinessDataExpected(pnbtTCData.get("TCID") + "_Trx1");
		policyLineOfBusiness.verifyPolicyLineOfBusinessDetails(binderData, testDataSetup, policyNumber);
	}

	//***POLICY TABLE***
	public void verifyPolicyDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum) {
		Assertions.addInfo("***POLICY TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		Map<String, String> binderData = policyData.getPolicyDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		policyData.verifyPolicyDetails(binderData, policyNumber);
	}

	//***INSURED TABLE***
	public void verifyInsuredDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		Assertions.addInfo("***INSURED TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		Map<String, String> binderData = insured.getInsuredDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		insured.verifyInsuredDetails(binderData, policyNumber, txnId);
	}

	//***POLICYCOMMISSION TABLE***
	public void verifyPolicyCommissionDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		Assertions.addInfo("***POLICY COMMISSION TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		Map<String, String> binderData = policyCommission.getPolicyCommissionDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		policyCommission.verifyPolicyCommissionDetails(binderData, policyNumber, txnId);
	}

	//***LOCATION TABLE***
	public void verifyLocationDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		Assertions.addInfo("***LOCATION TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		List<Map<String, String>> binderData = location.getLocationDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		location.verifyLocationDetails(binderData, txnId);
	}

	//***POLICYLOCATION TABLE***
	public void verifyPolicyLocationDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		Assertions.addInfo("***POLICYLOCATION TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		List<Map<String, String>> binderData = policyLocation.getPolicyLocationDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		policyLocation.verifyPolicyLocationDetails(binderData, txnId);
	}

	//***UWQUESTION TABLE***
	public void verifyUWQuestionDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		//we've only been checking new business, need to figure out how ObjectID corresponds to the building numbers when buildings have been added/removed
		List<Map<String, String>> binderData = uwQuestion.getUWQuestionDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		if (txnNum == 1){
			Assertions.addInfo("***UWQUESTION TABLE DB VALIDATION***", "");
			Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
			uwQuestion.verifyUWQuestionDetails(binderData, txnId);
		}
	}

	//***LOCEXTENSION TABLE***
	public void verifyLocExtensionDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = locExtension.getLocExtensionDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***LOCEXTENSION TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		//some eq policies won't have this
		if (binderData.size() != 0) {
			locExtension.verifyLocExtensionDetails(binderData, txnId);
		}
	}

	//***BUILDING TABLE***
	public void verifyBuildingDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = building.getBuildingDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***BUILDING TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		building.verifyBuildingData(binderData, txnId);
	}

	//***BUILDINGCOVERAGE TABLE***
	public void verifyBuildingCoverageDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = buildingCoverage.getBuildingCoverageDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***BUILDINGCOVERAGE TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		buildingCoverage.verifyBuildingCoverageData(binderData, txnId);
	}

	//***POLICYBUILDINGCOVERAGE TABLE***
	public void verifyPolicyBuildingCoverageDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = policyBuildingCoverage.getPolicyBuildingCoverageDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***POLICYBUILDINGCOVERAGE TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		policyBuildingCoverage.verifyPolicyBuildingCoverageData(binderData, txnId, policyNumber);
	}

	//***ADDITIONALINTEREST TABLE***
	public void verifyAdditionalInterestDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = additionalInterest.getAdditionalInterestDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***ADDITIONALINTEREST TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		if (binderData.size() > 0) {
			additionalInterest.verifyAdditionalInterestData(binderData, txnId);
		}
	}

	//***PRIORLOSS TABLE***
	public void verifyPriorLossDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = priorLoss.getPriorLossDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***PRIORLOSS TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		if (binderData.size() > 0) {
			priorLoss.verifyPriorLossData(binderData, txnId);
		}
	}

	//***RMSDATA TABLE***
	public void verifyRMSDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = rmsData.getRMSDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***RMSDATA TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		if (binderData.size() > 0) {
			rmsData.verifyRMSData(binderData, txnId);
		}
	}

	//***LOCDEDUCTIBLE TABLE***
	public void verifyLOCDeductibleDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = locDeductible.getLOCDeductibleDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***LOCDEDUCTIBLE TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		if (binderData.size() > 0) {
			locDeductible.verifyLOCDeductibleData(binderData, txnId);
		}
	}

	//***LINEOFCOVERAGE TABLE***
	public void verifyLineOfCoverageDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = lineOfCoverage.getLineOfCoverageDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***LINEOFCOVERAGE TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		if (binderData.size() > 0) {
			lineOfCoverage.verifyLineOfCoverageData(binderData, txnId);
		}
	}

	//***SPECIALCLASS TABLE***
	public void verifySpecialClassDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		List<Map<String, String>> binderData = specialClass.getSpecialClassDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***SPECIALCLASS TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		if (binderData.size() > 0) {
			specialClass.verifySpecialClassData(binderData, txnId);
		}
	}

	//***ADDRESSINTL TABLE***
	public void verifyAddressIntlDetails(Map<String, String> pnbtTCData, String policyNumber, int txnNum, int txnId) {
		Map<String, String> binderData;
		binderData = addressIntl.getAddressIntlDataExpected(pnbtTCData.get("TCID") + "_Trx" + txnNum);
		Assertions.addInfo("***ADDRESSINTL TABLE DB VALIDATION***", "");
		Assertions.addInfo("*** POLICY# " + policyNumber +" txn # " + txnNum + " ***", "");
		if (binderData != null && binderData.size() > 0) {
			addressIntl.verifyAddressIntlDetails(binderData, policyNumber, txnId);
		}
	}






	public List<Map<String, String>> gethvnBinderDataforPNB(String TCID) {
		List<Map<String, String>> hvnbinderData = new SheetMatchedAccessManager(PNBCOMMERCIALBINDERTESTFILEPATH,
				"HVN").readExcelRowWise();
		List<Map<String, String>> hvnTCbinderData = new ArrayList<>();
		for (int i = 1; i < hvnbinderData.size(); i++) {
			if (hvnbinderData.get(i-1).get("TCID").equalsIgnoreCase(TCID))
				hvnTCbinderData.add(hvnbinderData.get(i-1));
		}
		Collections.sort(hvnTCbinderData, new Comparator<Map<String, String>>() {
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				return 0;
			}

		});
		return hvnTCbinderData;
	}








	//may not need this since we have rollback transactions in spreadsheet
	/*public void verifyOOSTransaction(int transactionNumber, String policyNum, String tcid) {
		int transactionreasonid = policyData.getTransactionReasonID(policyNum,
				Integer.valueOf(gethvnBinderDataforPNB(tcid + "_Trx" + (transactionNumber)).get(0).get("HVN")));
		consolidated.verifyReverseEndorsement(transactionreasonid);
	}*/



}
