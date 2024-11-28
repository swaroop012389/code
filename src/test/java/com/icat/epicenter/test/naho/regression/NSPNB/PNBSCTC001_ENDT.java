package com.icat.epicenter.test.naho.regression.NSPNB;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBSCTC001_ENDT extends AbstractNAHOTest {

	public PNBSCTC001_ENDT() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSPNB/SC001.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initialize the pages
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ReferralPage referralPage = new ReferralPage();
		ViewOrPrintFullQuotePage viewPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int data_Value4 = 3;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			// Creating New Account
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling  Page",
					"Dwelling Page Loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);

			// Assert Eq Deductible value and Eq loss assessment value in NB Quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewPrintFullQuotePage.discountsValue.formatDynamicPath("Earthquake").getData(),
					"Not Selected", "View/Print Full Quote Page", "EQ Deductible Not Selected is displayed", false,
					false);
			Assertions.verify(viewPrintFullQuotePage.discountsValue.formatDynamicPath("EQ Loss Assessment").getData(),
					"Not Available", "View/Print Full Quote Page", "EQ Loss Assessment Not Included is displayed",
					false, false);
			viewPrintFullQuotePage.scrollToTopPage();
			viewPrintFullQuotePage.waitTime(2);
			viewPrintFullQuotePage.backButton.click();

			testData = data.get(data_Value1);
			// Binding the quote and creating the policy
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Adding the ticket IO-21999
			// Logout as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page",
					"Logged in as producer " + setUpData.get("NahoProducer") + " successfully");
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Producer home page loaded successfully", false, false);

			// Search the quote
			homePage.searchQuoteByProducer(quoteNumber);
			Assertions.passTest("Home Page", "Searched the quote " + quoteNumber + " successfully");
			Assertions.verify(accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on upload prebind documents button
			accountOverviewPage.uploadPreBindDocuments.scrollToElement();
			accountOverviewPage.uploadPreBindDocuments.click();
			Assertions.passTest("Account Overview Page", "Clicked on upload prebind documents button");
			Assertions.verify(policyDocumentsPage.addDocumentButton.checkIfElementIsDisplayed(), true,
					"Quote Documents Page", "Quote Documents Page loaded successfully", false, false);

			// Click on add document button
			policyDocumentsPage.addDocumentButton.scrollToElement();
			policyDocumentsPage.addDocumentButton.click();
			Assertions.passTest("Policy Documents Page", "Clicked on add document button");

			String fileName = testData.get("FileNameToUpload");
			if (StringUtils.isBlank(fileName)) {
				Assertions.failTest("Upload File", "Filename is blank");
			}
			String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
			waitTime(8);
			if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
					&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
				policyDocumentsPage.chooseDocument.setData(new File(uploadFileDir + fileName).getAbsolutePath());
				waitTime(5);// Adding wait time to load the element
				System.out.println("Choose document");
			} else {
				policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
				waitTime(5);// Adding wait time to load the element

			}

			policyDocumentsPage.waitTime(4);// wait time is needed to load the element

			// Click on document category arrow
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).waitTillPresenceOfElement(60);
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
			policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
			policyDocumentsPage.waitTime(3);// wait time is needed to load the element

			// Verify the options present in drop down as producer
			Assertions.verify(
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Due Diligence", 2)
							.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "The Option Due Diligence displayed is verified", false, false);
			Assertions.verify(
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
							.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "The Option Subscription Agreement displayed is verified", false,
					false);
			Assertions.verify(
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Signed Quote Application", 2)
							.checkIfElementIsDisplayed(),
					true, "Policy Documents Page", "The Option Signed Quote Application displayed is verified", false,
					false);

			// Click on cancel
			policyDocumentsPage.cancelButtonUAT.scrollToElement();
			policyDocumentsPage.cancelButtonUAT.click();
			// End of the ticket IO-21999

			// Sign out as producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM successfully");
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Search the quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the quote " + quoteNumber + " successfully");
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(60);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();
			requestBindPage.refreshPage();

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Values Entered Successfully");

			// Approve bind request
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Bind Request successfully");

			// approving referral
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Validating the premium amount
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Endorse EQ deductible on to policy through PB endorsement
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Click on change coverage options
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			Assertions.addInfo("Scenario 01",
					"Verifying the absence of Policy expiration date field and Asserting EQ Loss Assessment is only available on endorsement if EQ Deductible is selected");
			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.verify(endorsePolicyPage.policyExpirationDate.checkIfElementIsPresent(), false,
					"Endorse Policy Page", "Policy Expiration Date field not present is verified for SC state", false,
					false);
			endorsePolicyPage.waitTime(2);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();

			Assertions.verify(
					createQuotePage.earthquakeDeductibleArrow.checkIfElementIsPresent()
							&& createQuotePage.earthquakeDeductibleArrow.checkIfElementIsDisplayed(),
					true, "Create Quote Page", "EQ Deductible Dropdown is displayed and default value is "
							+ createQuotePage.earthquakeData.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.eqLossAssessmentArrow.checkIfElementIsPresent()
							&& createQuotePage.eqLossAssessmentArrow.checkIfElementIsDisplayed(),
					false, "Create Quote Page",
					"EQ Loss Assessent Dropdown is not displayed when EQ Deductible value is "
							+ createQuotePage.earthquakeData.getData(),
					false, false);
			createQuotePage.earthquakeDeductibleArrow.scrollToElement();
			createQuotePage.earthquakeDeductibleArrow.click();
			createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath(testData.get("EQDeductibleValue"))
					.scrollToElement();
			createQuotePage.earthquakeDeductibleOptionEQHO.formatDynamicPath(testData.get("EQDeductibleValue")).click();
			Assertions.verify(createQuotePage.eqLossAssessmentArrow.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "EQ Loss Assessent Dropdown is displayed when EQ Deductible value is Selected",
					false, false);
			createQuotePage.refreshPage();

			// Add EQ deductible and EQ loss assessment values
			createQuotePage.enterQuoteDetailsNAHO(testData);

			// continue with endorsement the endorsement
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on view policy snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			// Assert EQ deductible values on Policy snapshot
			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath(22).getData(), "Included",
					"View Policy Snapshot Page", viewPolicySnapShot.endorsementValues.formatDynamicPath(21).getData()
							+ " is " + viewPolicySnapShot.endorsementValues.formatDynamicPath(22).getData(),
					false, false);
			Assertions
					.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath(24).getData(),
							testData.get("EQLossAssessment"), "View Policy Snapshot Page",
							viewPolicySnapShot.endorsementValues.formatDynamicPath(23).getData() + " is "
									+ viewPolicySnapShot.endorsementValues.formatDynamicPath(24).getData(),
							false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("1").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Premium   displayed is "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("1").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("2").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Policy Fee displayed is "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("2").getData(),
					false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("3").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Inspection Fee displayed is  "
							+ viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("3").getData(),
					false, false);

			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			Assertions.addInfo("Scenario 02", "Asserting PB/NPB endorsements can be processed in the same transaction");
			// Endorse EQ deductible on to policy through PB endorsement
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Click on change named insured link
			testData = data.get(data_Value2);
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(2);
			endorsePolicyPage.changeNamedInsuredLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeNamedInsuredLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeNamedInsuredLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeNamedInsuredLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();

			// Endorse Insured name
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);

			// Click on change coverage options
			testData = data.get(data_Value3);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillElementisEnabled(60);
			endorsePolicyPage.changeCoverageOptionsLink.waitTillButtonIsClickable(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();

			Assertions.verify(
					createQuotePage.earthquakeDeductibleArrow.checkIfElementIsPresent()
							&& createQuotePage.earthquakeDeductibleArrow.checkIfElementIsDisplayed(),
					true, "Create Quote Page", "EQ Deductible Dropdown is displayed and default value is "
							+ createQuotePage.earthquakeData.getData(),
					false, false);
			Assertions.verify(
					createQuotePage.eqLossAssessmentArrow.checkIfElementIsPresent()
							&& createQuotePage.eqLossAssessmentArrow.checkIfElementIsDisplayed(),
					true, "Create Quote Page", "EQ Loss Assessent Dropdown is displayed and default value is "
							+ createQuotePage.eqLossAssessmentData.getData(),
					false, false);

			// Update coverage valuess
			createQuotePage.enterQuoteDetailsNAHO(testData);

			// continue with endorsement the endorsement
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Click on view policy snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();

			// Assert EQ deductible values on Policy snapshot
			Assertions.verify(viewPolicySnapShot.insuredDetails.getData().contains(testData.get("InsuredName")), true,
					"View Policy Snapshot Page", "Insured Name is Updated", false, false);
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath(10).getData().replace(",", "")
							.replace("$", "").contains(testData.get("L1D1-DwellingCovA")),
					true, "View Policy Snapshot Page", "Coverage A value is Updated", false, false);
			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath(22).getData(), "Not Selected",
					"View Policy Snapshot Page", viewPolicySnapShot.endorsementValues.formatDynamicPath(21).getData()
							+ " is " + viewPolicySnapShot.endorsementValues.formatDynamicPath(22).getData(),
					false, false);
			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath(24).getData(), "Not Available",
					"View Policy Snapshot Page", viewPolicySnapShot.endorsementValues.formatDynamicPath(23).getData()
							+ " is " + viewPolicySnapShot.endorsementValues.formatDynamicPath(24).getData(),
					false, false);
			viewPolicySnapShot.scrollToTopPage();
			viewPolicySnapShot.waitTime(2);
			viewPolicySnapShot.goBackButton.click();
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Adding code for 2 or more prior lossess for Water Damage Not Due to
			// Weather
			// Click on PB endorsement
			testData = data.get(data_Value4);
			Assertions.addInfo("Scenario 03",
					"Asserting warning message when 2 or more prior lossess for Water Damage Not Due to Weather are added");
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(2);

			// Click on Edit location/ Building link
			endorsePolicyPage.editPriorLoss.scrollToElement();
			endorsePolicyPage.editPriorLoss.click();

			// Entering prior loss details
			if (!testData.get("PriorLoss2").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossesPage.selectPriorLossesInformation(testData);
			}

			// Click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next button");

			// Asserting Endorsement warning Message
			Assertions.verify(
					endorsePolicyPage.endorsementWarningMsg.formatDynamicPath("Water").checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"Warning message is " + endorsePolicyPage.endorsementWarningMsg.formatDynamicPath("Water").getData()
							+ " is verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Click on continue
			endorsePolicyPage.oKContinueButton.scrollToElement();
			endorsePolicyPage.oKContinueButton.click();

			// Click on Complete
			Assertions.verify(endorsePolicyPage.completeButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Complete Button");

			// Click on Complete
			Assertions.verify(endorseSummaryDetailsPage.closeBtn.checkIfElementIsDisplayed(), true,
					"Endorse Summary Page", "Endorse Policy Page Loaded successfully", false, false);
			// click on close button
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.waitTillElementisEnabled(60);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Summary Page", "Clicked on Close Button");

			// Adding code for validation for decline when Number of stories = 0 or
			// less,
			// Number of units = 0 or less, Total Square feet = 0 or less and Future
			// dated year built
			// Click on PB endorsement
			testData = data.get(data_Value2);
			Assertions.addInfo("Scenario 04",
					"Asserting warning message when Number of Stories = 0 or less, Number of Units = 0 or less, Total Square footage = 0, Year built = 2023");
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			endorsePolicyPage.waitTime(2);

			// Click on Edit location/ Building link
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();

			// Updating Number of units = 0, Number of stories = 0, Year built =
			// 2023, Total
			// Square Footage = 0,
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.scrollToBottomPage();
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.verify(dwellingPage.protectionClassWarMsg.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Warning message is " + dwellingPage.protectionClassWarMsg.getData() + " is verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBSCTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBSCTC001 ", "Executed Successfully");
			}
		}
	}
}
