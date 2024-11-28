/** Program Description: Roll forward endorsement  (NPB and PB) on a policy that has a renewal quote generated
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
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseProducerContact;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC021 extends AbstractNAHOTest {

	public PNBTC021() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID21.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		DwellingPage dwellingPage = new DwellingPage();
		LocationPage locationPage = new LocationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseProducerContact endorseProducerContact = new EndorseProducerContact();
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

		// Entering Location 1 Dwelling 1 Details
		Assertions.passTest("Dwelling Page", "Page Navigated");
		dwellingPage.enterDwellingDetails(testData);
		Assertions.passTest("Dwelling Page", "Location 1 Dwelling 1 -1 Values Entered successfully");

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

		accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

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

		// Go to Previous Policy
		accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
		accountOverviewPage.viewPreviousPolicyButton.click();
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		policySummaryPage.renewalCreatedOkBtn.click();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("PolicyEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();

		// Change coverages
		endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
		endorsePolicyPage.changeCoverageOptionsLink.click();
		Assertions.passTest("Building Information Page", "Navigated to Edit Location page Successfully");

		// Add Coverages
		testData = data.get(dataValue2);
		createQuotePage.ordinanceLawArrow_NAHO.scrollToElement();
		createQuotePage.ordinanceLawArrow_NAHO.click();
		createQuotePage.ordLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw")).scrollToElement();
		createQuotePage.ordLawOption.formatDynamicPath(testData.get("OrdinanceOrLaw")).click();
		createQuotePage.enhancedReplacementCostArrow.scrollToElement();
		createQuotePage.enhancedReplacementCostArrow.click();
		createQuotePage.enhancedReplacementCostOption.formatDynamicPath(testData.get("EnhancedReplCost"))
				.scrollToElement();
		createQuotePage.enhancedReplacementCostOption.formatDynamicPath(testData.get("EnhancedReplCost")).click();
		createQuotePage.continueEndorsementButton.scrollToElement();
		createQuotePage.continueEndorsementButton.click();

		// Delete the following code after the issue is fixed
		if (createQuotePage.continueButton.checkIfElementIsPresent()
				&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
		}

		// NPB endorsement- change producer contact details
		testData = data.get(dataValue2);
		endorsePolicyPage.producerContactLink.scrollToElement();
		endorsePolicyPage.producerContactLink.click();
		endorseProducerContact.producerName.setData(testData.get("ProducerName"));
		endorseProducerContact.producerEmail.setData(testData.get("ProducerEmail"));
		endorseProducerContact.okButton.scrollToElement();
		endorseProducerContact.okButton.click();
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		Assertions.passTest("Endorsement Policy Page",
				"Clicked on Complete after modifying the Ordinance or Law and Enhanced Replacement Cost value");

		// Click on ROll forward Endorsement button
		endorsePolicyPage.rollForwardBtn.scrollToElement();
		endorsePolicyPage.rollForwardBtn.click();
		endorsePolicyPage.scrollToBottomPage();
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		policySummaryPage.viewActiveRenewal.scrollToElement();
		policySummaryPage.viewActiveRenewal.click();

		// Validate the Roll forwarded quote
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		testData = data.get(dataValue2);
		Assertions.verify(createQuotePage.ordinanceLawValue.getData(), testData.get("OrdinanceOrLaw"),
				"Roll forwarded renewal Page", "Roll forwarded OrdinanceOrLaw Value is reflected", true, false);
		Assertions.verify(createQuotePage.enhancedRCValue.getData(), testData.get("EnhancedReplCost"),
				"Roll forwarded renewal Page", "Roll forwarded EnhancedReplCost Value is reflected", true, false);
		createQuotePage.previous.scrollToElement();
		createQuotePage.previous.click();

		// Release to Producer
		accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
		accountOverviewPage.releaseRenewalToProducerButton.click();

		// Request Bind
		accountOverviewPage.requestBind.scrollToElement();
		accountOverviewPage.requestBind.click();
		requestBindPage.submit.scrollToElement();
		requestBindPage.submit.click();
		requestBindPage.confirmBind();
		Assertions.passTest("Policy Summary Page", "Renewal Policy Number: " + policySummaryPage.getPolicynumber());
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC021", "Executed Successfully");
	}
}