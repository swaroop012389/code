/** Program Description: NPB roll forward on policy with a bound renewal
 *  Author			   : SM Netserv
 *  Date of Creation   : Sep 2018
**/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorseAdditionalInterestsPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC022 extends AbstractNAHOTest {

	public PNBTC022() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID22.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {
		HomePage homePage = new HomePage();
		DwellingPage dwellingPage = new DwellingPage();
		LocationPage locationPage = new LocationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseAdditionalInterestsPage endorseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();
		int dataValue1 = 0;
		int dataValue2 = 1;
		String quoteNumber;
		Map<String, String> testData = data.get(dataValue1);

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered");

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Enter Dwelling details
		Assertions.passTest("Dwelling Page", "Page Navigated");
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 - 1 Values Entered successfully");

		// Entering Create quote page Details
		Assertions.passTest("Create Quote Page", "Create Quote page loaded successfully");
		createQuotePage.enterDeductibles(testData);
		Assertions.passTest("Create Quote Page", "Quote details entered successfully");
		createQuotePage.getAQuote.scrollToElement();
		createQuotePage.getAQuote.click();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}
		quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
		Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

		accountOverviewPage.clickOnRequestBind(testData, null);
		Assertions.passTest("Request Bind Page", "Request Bind page loaded successfully");
		requestBindPage.enterBindDetailsNAHO(testData);
		Assertions.passTest("Request Bind Page", "Bind details entered successfully");
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policyNumber);

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", "Page Navigated");

		// Find the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Home Page", "Created Policy is selected");

		// Initiate the renewal
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		Assertions.passTest("Renew Policy Page", "Renewal initiated Successfully");

		// Release to Producer
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();

		// Request Bind
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.confirmBind();
		String renewalPolicyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number: " + renewalPolicyNumber);

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", "Page Navigated");

		// Find the policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(policyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Policy Summary Page", "Navigated to NB policy summary page");

		// NPB endorsement for adding AI
		policySummaryPage.endorseNPB.scrollToElement();
		policySummaryPage.endorseNPB.click();
		policySummaryPage.renewalCreatedOkBtn.scrollToElement();
		policySummaryPage.renewalCreatedOkBtn.click();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("PolicyEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();
		endorsePolicyPage.changeAIInformationLink.scrollToElement();
		endorsePolicyPage.changeAIInformationLink.click();
		testData = data.get(dataValue2);
		endorseAdditionalInterestsPage.enterEndorsementAdditionalInterestDetails(testData);

		// Roll forward
		endorseAdditionalInterestsPage.completeWithRollForwarButton.scrollToElement();
		endorseAdditionalInterestsPage.completeWithRollForwarButton.click();
		Assertions.passTest("Endorsement Policy Page",
				"Clicked on Complete with Roll Forward Button after Adding Additional Interests");

		// Click on Close button
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		Assertions.passTest("Endorse Policy Page", "Clicked on Close button successfully");

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		Assertions.passTest("Home Page", "Page Navigated");

		// Find the renewal policy by entering policy Number
		homePage.policyNumber.scrollToElement();
		homePage.findFilterArrow.click();
		homePage.findFilterPolicyOption.scrollToElement();
		homePage.findFilterPolicyOption.click();
		homePage.policyNumber.setData(renewalPolicyNumber);
		homePage.findBtnPolicy.scrollToElement();
		homePage.findBtnPolicy.click();
		Assertions.passTest("Policy Summary Page", "Navigated to Renewal policy summary page");

		// Code to check the endorsement record created in transaction history
		Assertions.verify(
				policySummaryPage.endorsementRecordQ3.formatDynamicPath("Endorsement").checkIfElementIsDisplayed(),
				true, "Policy Summary Page", "Endorsement record created in transaction history", true, false);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC022", "Executed Successfully");
	}
}