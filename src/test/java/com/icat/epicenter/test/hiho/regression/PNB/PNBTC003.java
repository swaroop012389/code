/** Program Description: To check that the USM is able to add the inspection and policy fee to the previous inspection
 * 						 and policy fee when PB endorsement is initiated
 *  Author			   : Aishwarya Rangasamy
 *  Date of Creation   : 05/09/2018
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC003 extends AbstractNAHOTest {

	public PNBTC003() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID03.xls";
	}

	HomePage homePage;
	DwellingPage dwellingPage;
	LocationPage locationPage;
	CreateQuotePage createQuotePage;
	AccountOverviewPage accountOverviewPage;
	RequestBindPage requestBindPage;
	PolicySummaryPage policySummaryPage;
	EndorsePolicyPage endorsePolicyPage;
	EndorseInspectionContactPage endorseInspectContact;
	OverridePremiumAndFeesPage overridePremiumAndFeesPage;
	Map<String, String> testData;
	BasePageControl basePage = new BasePageControl();
	String quoteNumber;
	String policyNumber;
	static final String PAGE_NAVIGATED = "Page Navigated";
	static final String VALUES_ENTERED = "Values Entered";
	static int dataValue1 = 0;

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		homePage = new HomePage();
		locationPage = new LocationPage();
		dwellingPage = new DwellingPage();
		createQuotePage = new CreateQuotePage();
		accountOverviewPage = new AccountOverviewPage();
		requestBindPage = new RequestBindPage();
		policySummaryPage = new PolicySummaryPage();
		endorsePolicyPage = new EndorsePolicyPage();
		endorseInspectContact = new EndorseInspectionContactPage();
		overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		testData = data.get(dataValue1);

		// New account
		Assertions.passTest("Home Page", PAGE_NAVIGATED);
		basePage = homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", VALUES_ENTERED);

		// Entering Location Details
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetails(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", PAGE_NAVIGATED);
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 " + VALUES_ENTERED);

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();

		// Find the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findPolicyButton.scrollToElement();
		homePage.findPolicyButton.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Endorse PB page
		policySummaryPage.endorsePB.waitTillButtonIsClickable(60);
		policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("PolicyEffDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		endorsePolicyPage.feeOnlyEndorsement.scrollToElement();
		endorsePolicyPage.feeOnlyEndorsement.click();
		Assertions.passTest("Override Premium and Fees page", "Successfully Navigated");

		// Get previous InspectionFee and Policy Fee values
		String previousInspectionFee = overridePremiumAndFeesPage.inspectionFeePrevious.getData().substring(1,
				overridePremiumAndFeesPage.inspectionFeePrevious.getData().length());
		String policyInspectionFee = overridePremiumAndFeesPage.policyFeePrevious.getData().substring(1,
				overridePremiumAndFeesPage.policyFeePrevious.getData().length());
		int intPreviousInspectionFee = Integer.parseInt(previousInspectionFee);
		int intPreviousPolicyFee = Integer.parseInt(policyInspectionFee);

		// Set InspectionFee and Policy Fee values in UI
		overridePremiumAndFeesPage.transactionInspectionFee.setData(testData.get("TransactionInspectionFee"));
		overridePremiumAndFeesPage.transactionPolicyFee.setData(testData.get("TransactionPolicyFee"));
		overridePremiumAndFeesPage.transactionPolicyFee.tab();

		// Get New InspectionFee and Policy Fee values
		String newInspectionFee = overridePremiumAndFeesPage.newInspectionFee.getData().substring(1,
				overridePremiumAndFeesPage.newInspectionFee.getData().length());
		String newPolicyFee = overridePremiumAndFeesPage.newPolicyFee.getData().substring(1,
				overridePremiumAndFeesPage.newPolicyFee.getData().length());
		int intNewInspectionFee = Integer.parseInt(newInspectionFee);
		int intNewPolicyFee = Integer.parseInt(newPolicyFee);
		int modifiedInspectionFee = Integer.parseInt(testData.get("TransactionInspectionFee"));
		int modifiedPolicyFee = Integer.parseInt(testData.get("TransactionPolicyFee"));

		// Ensure correctness of New InspectionFee and Policy Fee values
		if (intPreviousInspectionFee + modifiedInspectionFee == intNewInspectionFee) {
			Assertions.passTest("Override Premium and Fees page", "New Total for Inspection Fee is validated");
		} else {
			Assertions.failTest("Override Premium and Fees page", "Expected New Inspection Fee is "
					+ intNewInspectionFee + " but Actual value is " + intPreviousInspectionFee + modifiedInspectionFee);
		}
		Assertions.verify(overridePremiumAndFeesPage.newInspectionFee.checkIfElementIsPresent(), true,
				"Override Premium and Fees page", "New Total for Policy Fee is displayed", false, false);
		if (intPreviousPolicyFee + modifiedPolicyFee == intNewPolicyFee) {
			Assertions.passTest("Override Premium and Fees page", "New Total for Policy Fee is validated");
		} else {
			Assertions.failTest("Override Premium and Fees page", "Expected New Policy Fee is " + intNewPolicyFee
					+ " but Actual value is " + intPreviousPolicyFee + modifiedPolicyFee);
		}
		Assertions.passTest("PNB_Regression_TC003", "Executed Successfully");
		System.out.println("PNB Regression TC003 Ended");
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);

	}
}