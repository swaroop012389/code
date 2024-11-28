/** Program Description: Perform Fee only Endorsement and USM and and Perform Endorsement as Producer and Assert require review message
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/27/2019
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC074 extends AbstractCommercialTest {

	public TC074() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID074.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		GLInformationPage glInformationPage = new GLInformationPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// creating New account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account created successfully");

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// entering general liability details
			if (!testData.get("L1-GLLocationClass").equals("")) {
				Assertions.verify(glInformationPage.locationClassArrow.checkIfElementIsDisplayed(), true,
						"GL Information Page", "GL Information Page loaded successfully", false, false);
				glInformationPage.enterGLInformation(testData);
				Assertions.passTest("GL Information Page", "GL Information details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// endorse policy
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement to Perform Fee only Endorsement");
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on edit prior loss hyperlink
			endorsePolicyPage.feeOnlyEndorsement.waitTillVisibilityOfElement(60);
			endorsePolicyPage.feeOnlyEndorsement.scrollToElement();
			endorsePolicyPage.feeOnlyEndorsement.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);
			Assertions.passTest("Endorse Policy Page", "Clicked on Fee only endorsement link");

			// Modifying prior loss Details
			testData = data.get(data_Value2);
			Assertions.verify(overridePremiumAndFeesPage.saveAndClose.checkIfElementIsDisplayed(), true,
					"Override Premium And Fees Page", "Override Premium And Fees page loaded successfully", false,
					false);
			overridePremiumAndFeesPage.enterOverrideFeesCommercial(testData);
			Assertions.passTest("Override Premium And Fees Page", "Entered override data");

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// verifying PB Endorsement record in policy summary page
			Assertions.addInfo("Policy Summary Page", "Verifying PB Endorsement record in policy summary page");
			testData = data.get(data_Value1);
			Assertions.verify(
					policySummarypage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData(),
					testData.get("TransactionType"), "Policy Summary Page", "PB Endorsement Record Verified", false,
					false);

			// Validating the transaction values for 1st endorsement from policy summary
			// page
			Assertions.addInfo("Policy Summary Page",
					"Verifying the transaction values for endorsement on policy summary page");
			policySummarypage.refreshPage();
			policySummarypage.waitTime(3);// need wait time to load the page
			policySummarypage.transRevReason.formatDynamicPath("Endorsement").waitTillVisibilityOfElement(60);
			policySummarypage.transRevReason.formatDynamicPath("Endorsement").scrollToElement();
			policySummarypage.transRevReason.formatDynamicPath("Endorsement").click();
			String transctionPremium = policySummarypage.transactionPremium.getData();
			String policyFee = policySummarypage.policyFee.getData();
			String inspectionFee = policySummarypage.inspectionFee.getData();
			String totalTransactionPremium = policySummarypage.policyTotalPremium.getData();
			Assertions.verify(policySummarypage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Transaction Premium after first endorsement : " + transctionPremium, false,
					false);
			Assertions.verify(policySummarypage.policyFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Fee after first endorsement : " + policyFee, false, false);
			Assertions.verify(policySummarypage.inspectionFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Inspection Fee after first endorsement : " + inspectionFee, false, false);
			Assertions.verify(policySummarypage.policyTotalPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"Total Transaction Premium after first endorsement : " + totalTransactionPremium, false, false);

			// Logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));

			// Search the policy
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.producerPolicySearchButton.scrollToElement();
			homePage.producerPolicySearchButton.click();
			homePage.producerPolicyNumberSearchTextbox.scrollToElement();
			homePage.producerPolicyNumberSearchTextbox.setData(policyNumber);
			homePage.producerPolicyFindButton.scrollToElement();
			homePage.producerPolicyFindButton.click();
			homePage.producerPolicyNumberLink.formatDynamicPath(policyNumber).scrollToElement();
			homePage.producerPolicyNumberLink.formatDynamicPath(policyNumber).click();

			// Clicking on Endorse Policy link
			Assertions.addInfo("Policy Summary Page", "Initiating Endorsement as producer");
			policySummarypage.producerEndorsePolicyLink.waitTillVisibilityOfElement(60);
			policySummarypage.producerEndorsePolicyLink.scrollToElement();
			policySummarypage.producerEndorsePolicyLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse Policy link");

			// Clicking on all other changes link
			endorsePolicyPage.allOtherChanges.waitTillVisibilityOfElement(60);
			endorsePolicyPage.allOtherChanges.scrollToElement();
			endorsePolicyPage.allOtherChanges.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on All other changes link");

			// setting Endorsement Effective Date
			testData = data.get(data_Value2);
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Validating the Out of sequence transaction message
			Assertions.verify(endorsePolicyPage.oosInfoMessage.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Out of sequence transaction message : " + endorsePolicyPage.oosInfoMessage.getData()
							+ " is verified",
					false, false);

			// Clicking on Continue button
			endorsePolicyPage.continueButton.scrollToElement();
			endorsePolicyPage.continueButton.click();

			// Clicking on deductibles and coverage link
			endorsePolicyPage.prodDeductiblesAndCoverageLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.prodDeductiblesAndCoverageLink.scrollToElement();
			endorsePolicyPage.prodDeductiblesAndCoverageLink.click();

			// Asserting changing endorsement to require review message
			Assertions.addInfo("Endorse Policy Page", "Assert changing endorsement to require review message");
			endorsePolicyPage.changingEndorsementToRequireReviewMessage.waitTillVisibilityOfElement(60);
			Assertions.verify(endorsePolicyPage.changingEndorsementToRequireReviewMessage.checkIfElementIsDisplayed(),
					true, "Endorse Policy Page",
					"Changing Endorsement To Require Review Message : "
							+ endorsePolicyPage.changingEndorsementToRequireReviewMessage.getData() + " is verified",
					false, false);

			// Clicking on I Need To Change Risk Button
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.iNeedToChangeRiskButton.scrollToElement();
			endorsePolicyPage.iNeedToChangeRiskButton.click();

			// modifying deductibles and coverages in create quote page
			Assertions.addInfo("Create Quote Page", "Modifying Deductibles and coverages details");
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			Assertions.passTest("Create Quote Page", "Deductibles and coverages details modified successfully");
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			if (createQuotePage.continueEndorsementButton.checkIfElementIsPresent()
					&& createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed()) {
				createQuotePage.scrollToBottomPage();
				createQuotePage.continueEndorsementButton.scrollToElement();
				createQuotePage.continueEndorsementButton.click();
			}

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// Entering refer quote details
			referQuotePage.contactName.waitTillVisibilityOfElement(60);
			referQuotePage.contactName.clearData();
			referQuotePage.contactName.scrollToElement();
			testData = data.get(data_Value1);
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.clearData();
			referQuotePage.contactEmail.scrollToElement();
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.waitTillVisibilityOfElement(60);
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// Logout as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");

			// approving referral
			policySummarypage.openCurrentReferral.scrollToElement();
			policySummarypage.openCurrentReferral.click();
			referralPage.clickOnApprove();
			// approve_DeclineQuotePage.clickOnApprove();
			// Added this new code as these steps are newly included.(IO-21580)
			if (approveDeclineQuotePage.internalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.internalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.internalUnderwriterComments.setData("Test");
			}
			if (approveDeclineQuotePage.externalUnderwriterComments.checkIfElementIsPresent()) {
				approveDeclineQuotePage.externalUnderwriterComments.scrollToElement();
				approveDeclineQuotePage.externalUnderwriterComments.setData("Test");
			}
			if (endorsePolicyPage.editEndtQuoteButton.checkIfElementIsPresent()) {
				endorsePolicyPage.editEndtQuoteButton.scrollToElement();
				endorsePolicyPage.editEndtQuoteButton.click();
				endorsePolicyPage.nextButton.scrollToElement();
				endorsePolicyPage.nextButton.click();
				endorsePolicyPage.submitButton.scrollToElement();
				endorsePolicyPage.submitButton.click();
				approveDeclineQuotePage.clickOnApprove();
			}
			// New code ended

			// Logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));

			// Search the policy
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.producerPolicySearchButton.scrollToElement();
			homePage.producerPolicySearchButton.click();
			homePage.producerPolicyNumberSearchTextbox.scrollToElement();
			homePage.producerPolicyNumberSearchTextbox.setData(policyNumber);
			homePage.producerPolicyFindButton.scrollToElement();
			homePage.producerPolicyFindButton.click();
			homePage.producerPolicyNumberLink.formatDynamicPath(policyNumber).scrollToElement();
			homePage.producerPolicyNumberLink.formatDynamicPath(policyNumber).click();

			// Clicking on Endorse Policy link
			policySummarypage.producerEndorsePolicyLink.waitTillVisibilityOfElement(60);
			policySummarypage.producerEndorsePolicyLink.scrollToElement();
			policySummarypage.producerEndorsePolicyLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse Policy link");

			// Click on submit button
			endorsePolicyPage.submitButton.scrollToElement();
			endorsePolicyPage.submitButton.click();

			// Entering refer quote details
			referQuotePage.contactName.waitTillVisibilityOfElement(60);
			referQuotePage.contactName.clearData();
			referQuotePage.contactName.scrollToElement();
			testData = data.get(data_Value1);
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.clearData();
			referQuotePage.contactEmail.scrollToElement();
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.waitTillVisibilityOfElement(60);
			referQuotePage.referQuote.scrollToElement();
			referQuotePage.referQuote.click();

			// Logout as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));

			// Finding the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfullly");
			policySummarypage.openCurrentReferral.scrollToElement();
			policySummarypage.openCurrentReferral.click();
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.scrollToBottomPage();
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			// approving referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			approveDeclineQuotePage.clickOnApprove();
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the account
			homePage.searchPolicy(policyNumber);

			// Validating reversal of last endorsement
			Assertions.verify(policySummarypage.transHistReason.formatDynamicPath(4).getData().contains("Reversal"),
					true, "Policy Summary Page", "First endorsement is reversed is verified", false, false);

			// Validating the transaction values for Reversal endorsement from policy
			// summary
			// page
			policySummarypage.waitTime(2);
			policySummarypage.transRevReason.formatDynamicPath("Reversal - Endorsement")
					.waitTillVisibilityOfElement(60);
			policySummarypage.transRevReason.formatDynamicPath("Reversal - Endorsement").scrollToElement();
			policySummarypage.transRevReason.formatDynamicPath("Reversal - Endorsement").click();

			Assertions.verify(policySummarypage.transactionPremium.getData().equals(transctionPremium), true,
					"Policy Summary Page",
					"Transaction Premium for Reversal endorsement : " + policySummarypage.transactionPremium.getData(),
					false, false);

			Assertions.verify(policySummarypage.policyFee.getData().contains(policyFee), true, "Policy Summary Page",
					"Policy Fee for Reversal endorsement : " + policySummarypage.policyFee.getData().replace("-", ""),
					false, false);
			Assertions.verify(policySummarypage.inspectionFee.getData().contains(inspectionFee), true,
					"Policy Summary Page", "Inspection Fee for Reversal endorsement : "
							+ policySummarypage.inspectionFee.getData().replace("-", ""),
					false, false);
			Assertions.verify(policySummarypage.policyTotalPremium.getData().contains(totalTransactionPremium), true,
					"Policy Summary Page", "Total Transaction Premium for Reversal endorsement : "
							+ policySummarypage.policyTotalPremium.getData().replace("-", ""),
					false, false);

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 74", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 74", "Executed Successfully");
			}
		}
	}
}
