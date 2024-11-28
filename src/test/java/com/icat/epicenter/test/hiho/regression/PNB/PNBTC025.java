/** Program Description: Cancelling policy that has endorsements and renewal quote generated, but not released
 *  Author			   : SM Netserv
 *  Date of Creation   : Sep 2018
 **/
package com.icat.epicenter.test.hiho.regression.PNB;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBTC025 extends AbstractNAHOTest {

	public PNBTC025() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/hiho/regression/HIHOPNB/PNBTCID25.xls";
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
		ChangeNamedInsuredPage changeNamedInsured = new ChangeNamedInsuredPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		int dataValue1 = 0;
		int dataValue2 = 1;
		String quoteNumber;
		Map<String, String> testData = data.get(dataValue1);

		// New account
		Assertions.passTest("Home Page", "Page Navigated");
		homePage.createNewAccountWithNamedInsured(testData, setUpData);
		Assertions.passTest("Home Page", "Values Entered successfully");

		// Entering Location Details
		locationPage = new LocationPage();
		Assertions.passTest("Location Page", "Location Page loaded successfully");
		locationPage.enterLocationDetailsHIHO(testData);
		Assertions.passTest("Location Page", "Location details entered successfully");

		// Entering Dwelling Details
		Assertions.passTest("Dwelling Page", "Page Navigated");
		dwellingPage.enterDwellingDetails(testData);

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
		String policyNumber = policySummaryPage.getPolicynumber();
		Assertions.passTest("Policy Summary Page", "Policy Number: " + policySummaryPage.getPolicynumber());

		// Go to Home Page
		homePage.goToHomepage.scrollToElement();
		homePage.goToHomepage.click();
		homePage.refreshPage();
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

		// Code for Renewal already started page and renewal created page
		policySummaryPage.endorsePB.scrollToElement();
		policySummaryPage.endorsePB.click();
		policySummaryPage.renewalCreatedOkBtn.scrollToElement();
		policySummaryPage.renewalCreatedOkBtn.click();
		endorsePolicyPage.endorsementEffDate.setData(testData.get("PolicyEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();

		// Change Coverages
		endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
		endorsePolicyPage.changeCoverageOptionsLink.click();
		Assertions.passTest("Create Quote Page", "Navigated to Create Quote page Successfully");

		// Modify NH and EQ Deductibles
		testData = data.get(dataValue2);
		createQuotePage.namedHurricaneDeductibleArrow.scrollToElement();
		createQuotePage.namedHurricaneDeductibleArrow.click();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.scrollToElement();
		createQuotePage.namedHurricaneDeductibleOption.formatDynamicPath(testData.get("NamedHurricaneDedValue"))
				.click();
		createQuotePage.earthquakeDeductibleArrow.scrollToElement();
		createQuotePage.earthquakeDeductibleArrow.click();
		createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get("EQDeductible")).scrollToElement();
		createQuotePage.earthquakeDeductibleOption.formatDynamicPath(testData.get("EQDeductible")).click();
		createQuotePage.earthquakeDeductibleArrow.waitTillVisibilityOfElement(60);
		createQuotePage.continueEndorsementButton.scrollToElement();
		createQuotePage.continueEndorsementButton.click();
		endorsePolicyPage.nextButton.scrollToElement();
		endorsePolicyPage.nextButton.click();
		ButtonControl completeButton = new ButtonControl(By.xpath("//button[contains(text(),'Complete')]"));
		completeButton.scrollToElement();
		completeButton.click();
		Assertions.passTest("Endorsement Policy Page", "Clicked on Complete after modifying the NH and EQ value");
		endorsePolicyPage.rollForwardBtn.scrollToElement();
		endorsePolicyPage.rollForwardBtn.click();
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		policySummaryPage.pbEndt800Link.waitTillVisibilityOfElement(60);
		Assertions.verify(policySummaryPage.pbEndt800Link.checkIfElementIsEnabled(), true, "Policy Summary Page",
				"PCF form is generated for Endorsement", true, true);
		Assertions.verify(policySummaryPage.pbEndtAmmDecLink.checkIfElementIsEnabled(), true, "Policy Summary Page",
				"Ammended declaration form is generated for Endorsement", true, true);
		policySummaryPage.viewActiveRenewal.scrollToElement();
		policySummaryPage.viewActiveRenewal.click();

		// Validate the Roll forwarded quote
		accountOverviewPage.editDeductibleAndLimits.scrollToElement();
		accountOverviewPage.editDeductibleAndLimits.click();
		Assertions.verify(createQuotePage.namedHurricaneValue.getData(), testData.get("NamedHurricaneDedValue"),
				"Roll forwarded renewal Page", "Roll forwarded NH deductible Value is reflected", true, false);
		Assertions.verify(createQuotePage.earthQuakeValue.getData(), testData.get("EQDeductible"),
				"Roll forwarded renewal Page", "Roll forwarded Earthquake Value is reflected", true, false);
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
		policySummaryPage.endorseNPB.scrollToElement();
		policySummaryPage.endorseNPB.click();

		// Code for Renewal already started page and renewal created page
		// Select effective date
		endorsePolicyPage.endorsementEffDate.setData(testData.get("PolicyEffectiveDate"));
		endorsePolicyPage.endorsementEffDate.tab();

		// Change Mailing Address
		endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
		endorsePolicyPage.changeNamedInsuredLink.click();
		testData = data.get(dataValue2);
		if (changeNamedInsured.enterAddressManuallLink.checkIfElementIsDisplayed()) {
			changeNamedInsured.enterAddressManuallLink.scrollToElement();
			changeNamedInsured.enterAddressManuallLink.click();
		}
		if (!testData.get("InsuredAddr1").equalsIgnoreCase("")) {
			changeNamedInsured.addressLine1.waitTillVisibilityOfElement(60);
			changeNamedInsured.addressLine1.scrollToElement();
			changeNamedInsured.addressLine1.setData(testData.get("InsuredAddr1"));
		}
		if (changeNamedInsured.insuredCity.checkIfElementIsDisplayed()
				&& !testData.get("InsuredCity").equalsIgnoreCase("")) {
			changeNamedInsured.insuredCity.scrollToElement();
			changeNamedInsured.insuredCity.setData(testData.get("InsuredCity"));
		}
		if (changeNamedInsured.insuredState.checkIfElementIsPresent()
				&& changeNamedInsured.insuredState.checkIfElementIsDisplayed()
				&& !testData.get("InsuredState").equalsIgnoreCase("")) {
			changeNamedInsured.insuredState.scrollToElement();
			changeNamedInsured.insuredState.setData(testData.get("InsuredState"));
			changeNamedInsured.zipCode.setData(testData.get("InsuredZIP"));
		}
		changeNamedInsured.okButton.scrollToElement();
		changeNamedInsured.okButton.click();
		endorsePolicyPage.completeButton.scrollToElement();
		endorsePolicyPage.completeButton.click();
		endorsePolicyPage.closeButton.scrollToElement();
		endorsePolicyPage.closeButton.click();
		policySummaryPage.pbEndt800Link.waitTillVisibilityOfElement(60);
		Assertions.verify(policySummaryPage.pbEndt800Link.checkIfElementIsEnabled(), true, "Policy Summary Page",
				"PCF form is generated for Endorsement", true, true);
		Assertions.verify(policySummaryPage.pbEndtAmmDecLink.checkIfElementIsEnabled(), true, "Policy Summary Page",
				"Ammended declaration form is generated for Endorsement", true, true);
		policySummaryPage.renewPolicy.scrollToElement();
		policySummaryPage.renewPolicy.click();
		Assertions.verify(accountOverviewPage.insuredAcctInfo.getData().contains(testData.get("InsuredAddr1")), true,
				"Roll forwarded renewal Page", "Roll forwarded Insured Address Value is reflected", true, false);
		Assertions.verify(accountOverviewPage.insuredAcctInfo.getData().contains(testData.get("InsuredCity")), true,
				"Roll forwarded renewal Page", "Roll forwarded Insured City Value is reflected", true, false);
		Assertions.verify(accountOverviewPage.insuredAcctInfo.getData().contains(testData.get("InsuredState")), true,
				"Roll forwarded renewal Page", "Roll forwarded Insured State Value is reflected", true, false);
		Assertions.verify(accountOverviewPage.insuredAcctInfo.getData().contains(testData.get("InsuredZIP")), true,
				"Roll forwarded renewal Page", "Roll forwarded Insured Zip Value is reflected", true, false);
		accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
		accountOverviewPage.viewPreviousPolicyButton.click();

		// Cancellation
		policySummaryPage.cancelPolicy.scrollToElement();
		policySummaryPage.cancelPolicy.click();
		cancelPolicyPage.continueButton.scrollToElement();
		cancelPolicyPage.continueButton.click();

		// code for Renewal process already started page.
		cancelPolicyPage.cancelReasonArrow.scrollToElement();
		cancelPolicyPage.cancelReasonArrow.click();
		cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
		cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
		cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
		testData = data.get(dataValue2);
		cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("PolicyEffectiveDate"));
		Assertions.passTest("Cancel Policy Page", "Details entered successfully");
		cancelPolicyPage.nextButton.scrollToElement();
		cancelPolicyPage.nextButton.click();
		testData = data.get(dataValue2);

		// Enter UW comments
		cancelPolicyPage.underwriterComment.setData("Flat Cancellation");
		cancelPolicyPage.completeTransactionButton.scrollToElement();
		cancelPolicyPage.completeTransactionButton.click();
		Assertions.passTest("Cancel Policy Page", cancelPolicyPage.cancellationSuccess.getData());
		cancelPolicyPage.closeButton.click();
		String cancellationStatus = cancelPolicyPage.cancellationStatus.getData();
		Assertions.passTest("Policy Summary Page", "Validation of Policy Cancellation Status is " + cancellationStatus);
		homePage.signOutButton.scrollToElement();
		homePage.signOutButton.click();
		homePage.signOutButton.waitTillInVisibilityOfElement(60);
		Assertions.passTest("PNB_Regression_TC025", "Executed Successfully");
	}
}