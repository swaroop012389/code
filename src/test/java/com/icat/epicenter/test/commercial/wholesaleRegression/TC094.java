/** Program Description: Create an AOP Policy and Open the Policy as Producer and Click on All the available links without entering Endorsement Effective date and assert the Error message
 *  Author			   : Sowndarya
 *  Date of Modified   : 09/29/2021
**/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseAdditionalInterestsPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseProducerContact;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC094 extends AbstractCommercialTest {

	public TC094() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID094.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing Page objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		GLInformationPage gLInfoPage = new GLInformationPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		EndorseAdditionalInterestsPage endorseAdditionalInterestsPage = new EndorseAdditionalInterestsPage();
		EndorseProducerContact endorseProducerContact = new EndorseProducerContact();

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

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering GL Information
			gLInfoPage.enterGLInformation(testData);

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

			// entering details in request bind page
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");
			requestBindPage = accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Approval page loaded successfully", false, false);
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Signing out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Login as USM
			loginPage.refreshPage();
			Assertions.addInfo("Home Page", "Opening the Policy as Producer");
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login Page", "Logged in as Producer successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer Home page loaded successfully", false, false);
			homePage.searchPolicyByProducer(policyNumber);
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);

			// Click on Endorse Policy link
			policySummaryPage.producerEndorsePolicyLink.scrollToElement();
			policySummaryPage.producerEndorsePolicyLink.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse Policy Link");

			// Click on All other Changes link
			endorsePolicyPage.allOtherChanges.waitTillPresenceOfElement(60);
			endorsePolicyPage.allOtherChanges.waitTillVisibilityOfElement(60);
			endorsePolicyPage.allOtherChanges.scrollToElement();
			endorsePolicyPage.allOtherChanges.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on All other changes link");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);

			// Click on Change named insured link
			Assertions.addInfo("Endorse Policy Page",
					"Click on Change Named Insueed link and Asserting the endorsement effective date error message");
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change named insured link");
			Assertions
					.verify(endorsePolicyPage.endorsementEffDateErrorMsg.checkIfElementIsDisplayed(), true,
							"Endorse Policy Page", "The error message "
									+ endorsePolicyPage.endorsementEffDateErrorMsg.getData() + " displayed is verified",
							false, false);

			// click on Inspection contact link
			Assertions.addInfo("Endorse Policy Page",
					"Click on Inspection contact link and Asserting the endorsement effective date error message");
			endorsePolicyPage.prodInspectionContactLink.scrollToElement();
			endorsePolicyPage.prodInspectionContactLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change inspection Contact Link");
			Assertions
					.verify(endorsePolicyPage.endorsementEffDateErrorMsg.checkIfElementIsDisplayed(), true,
							"Endorse Policy Page", "The error message "
									+ endorsePolicyPage.endorsementEffDateErrorMsg.getData() + " displayed is verified",
							false, false);

			// click on change AI information link
			Assertions.addInfo("Endorse Policy Page",
					"Click on Change AI Information link and Asserting the endorsement effective date error message");
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Additional Information link");
			Assertions
					.verify(endorsePolicyPage.endorsementEffDateErrorMsg.checkIfElementIsDisplayed(), true,
							"Endorse Policy Page", "The error message "
									+ endorsePolicyPage.endorsementEffDateErrorMsg.getData() + " displayed is verified",
							false, false);

			// click on Producer contact link
			Assertions.addInfo("Endorse Policy Page",
					"Click on producer contact link and Asserting the endorsement effective date error message");
			endorsePolicyPage.producerContactLink.scrollToElement();
			endorsePolicyPage.producerContactLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Producer Contact link");
			Assertions
					.verify(endorsePolicyPage.endorsementEffDateErrorMsg.checkIfElementIsDisplayed(), true,
							"Endorse Policy Page", "The error message "
									+ endorsePolicyPage.endorsementEffDateErrorMsg.getData() + " displayed is verified",
							false, false);

			// click on edit location or building details link
			Assertions.addInfo("Endorse Policy Page",
					"Click on edit location or building information link and Asserting the endorsement effective date error message");
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location/Building Details link");
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.iNeedToChangeRiskButton.scrollToElement();
			endorsePolicyPage.iNeedToChangeRiskButton.click();
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillInVisibilityOfElement(60);
			Assertions
					.verify(endorsePolicyPage.endorsementEffDateErrorMsg.checkIfElementIsDisplayed(), true,
							"Endorse Policy Page", "The error message "
									+ endorsePolicyPage.endorsementEffDateErrorMsg.getData() + " displayed is verified",
							false, false);

			// click on deductibles and coverages link
			Assertions.addInfo("Endorse Policy Page",
					"Click on deductibles and coverages link and Asserting the endorsement effective date error message");
			endorsePolicyPage.prodDeductiblesAndCoverageLink.scrollToElement();
			endorsePolicyPage.prodDeductiblesAndCoverageLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Deductibles and Coverage link");
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.iNeedToChangeRiskButton.scrollToElement();
			endorsePolicyPage.iNeedToChangeRiskButton.click();
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillInVisibilityOfElement(60);
			Assertions
					.verify(endorsePolicyPage.endorsementEffDateErrorMsg.checkIfElementIsDisplayed(), true,
							"Endorse Policy Page", "The error message "
									+ endorsePolicyPage.endorsementEffDateErrorMsg.getData() + " displayed is verified",
							false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered the Endorsement Effective Date");

			// click on change named insured link
			testData = data.get(data_Value2);
			Assertions.addInfo("Endorse Policy Page", "Initiate Endorsement to change Insured name");
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Named Insured link");
			Assertions.verify(changeNamedInsuredPage.okButton.checkIfElementIsDisplayed(), true,
					"Change Named Insured Page", "Change Named Insured Page Loaded successfully", false, false);
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);

			// click on inspection contact link
			Assertions.addInfo("Endorse Policy Page", "Initiate Endorsement to change Inspection contact details");
			endorsePolicyPage.prodInspectionContactLink.scrollToElement();
			endorsePolicyPage.prodInspectionContactLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Inspection contact link");
			Assertions.verify(endorseInspectionContactPage.okButton.checkIfElementIsDisplayed(), true,
					"Endorse Inspection Contact Page", "Endorse Inspection Contact Page loaded successfully", false,
					false);
			endorseInspectionContactPage.enterInspectionContactPB(testData);

			// click on change AI information link
			Assertions.addInfo("Endorse Policy Page", "Initiate Endorsement to change Additional Interest details");
			endorsePolicyPage.changeAIInformationLink.scrollToElement();
			endorsePolicyPage.changeAIInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Additional Information link");
			Assertions.verify(endorseAdditionalInterestsPage.okButton.checkIfElementIsDisplayed(), true,
					"Endorse Additional Interests Page", "Endorse Additional Interests Page Loaded successfully", false,
					false);
			endorseAdditionalInterestsPage.enterEndorsementAdditionalInterestDetails(testData);

			// click on producer contact link
			Assertions.addInfo("Endorse Policy Page", "Initiate Endorsement to change producer contact details");
			endorsePolicyPage.producerContactLink.scrollToElement();
			endorsePolicyPage.producerContactLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Producer Contact Link");
			Assertions.verify(endorseProducerContact.okButton.checkIfElementIsDisplayed(), true,
					"Endorse Producer Contact Page", "Endorse Producer Contact Page loaded successfully", false, false);
			endorseProducerContact.enterEndorseProducerContactDetails(testData);

			// click on Edit Location or building link
			Assertions.addInfo("Endorse Policy Page", "Initiate Endorsement to change location and building details");
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Edit Location or Building Details link");
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.iNeedToChangeRiskButton.scrollToElement();
			endorsePolicyPage.iNeedToChangeRiskButton.click();
			endorsePolicyPage.iNeedToChangeRiskButton.waitTillInVisibilityOfElement(60);

			locationPage.buildingLink.formatDynamicPath(1, 1).scrollToElement();
			locationPage.buildingLink.formatDynamicPath(1, 1).click();
			Assertions.verify(buildingPage.reviewBuilding.checkIfElementIsDisplayed(), true, "Building Page",
					"Building Page loaded successfully", false, false);

			// buildingPage.modifyBuildingDetailsPNB(testData);
			buildingPage.waitTime(2);// if the waittime is removed test case will fail here
			buildingPage.editRoofDetailsPNB(testData, data_Value2, data_Value2);
			Assertions.passTest("Building Page", "Modified the Building Details successfully");
			buildingPage.reviewBuilding();
			buildingPage.continueButton.scrollToElement();
			buildingPage.continueButton.click();

			// click on continue endorsement button
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// click on cancel button
			endorsePolicyPage.cancelButton.scrollToElement();
			endorsePolicyPage.cancelButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Cancel Button");

			// sign out and close browser
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 94", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 94", "Executed Successfully");
			}
		}
	}
}
