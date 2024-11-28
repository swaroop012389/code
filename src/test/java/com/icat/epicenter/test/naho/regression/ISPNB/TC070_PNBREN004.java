/** Program Description: Verifying the following
 * 1) Checking if the PB Endorsement is roll forwarded on unbound renewal quote.
 * 2) As USM, Check the Green Upgrades coverage is being updated on Renewals
 *
 *  Author			   : Sowndarya NH
 *  Date of Creation   :31/8/2023
 **/

package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC070_PNBREN004 extends AbstractNAHOTest {

	public TC070_PNBREN004() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN004.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the pages
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewpriFullQuotePage = new ViewOrPrintFullQuotePage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		PolicyRenewalPage policyrenewalPage = new PolicyRenewalPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		String quoteNumber;
		String policyNumber;
		String renewalQuoteNumber;
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

			if (createQuotePage.greenUpgradesArrow.checkIfElementIsPresent()
					&& createQuotePage.greenUpgradesArrow.checkIfElementIsDisplayed()) {
				if (testData.get("GreenUpgrades") != null && !testData.get("GreenUpgrades").equals("")) {
					if (testData.get("GreenUpgrades").equals("Yes")) {
						createQuotePage.greenUpgradesArrow.scrollToElement();
						createQuotePage.greenUpgradesArrow.click();
						createQuotePage.greenUpgradesYesOption.scrollToElement();
						createQuotePage.greenUpgradesYesOption.click();
						Assertions.passTest("Create Quote Page",
								"Green Upgrades Value Selected is  : " + createQuotePage.greenUpgradesData.getData());
					}

				}
			}
			testData = data.get(data_Value1);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions Details Entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			Assertions.passTest("Request Bind Page", "Bind Details Entered Successfully");
			requestBindPage.enterBindDetailsNAHO(testData);

			// Getting Policy Number from policy summary Page
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on view policy snapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy snapshot link");

			// Verifying green upgrades value is included when it is selected as Yes
			Assertions.addInfo("Scenario 01", "Verifying green upgrades value is included when it is selected as Yes");
			Assertions.verify(viewPolicySnapShot.greenUpgradesValue.getData().contains("Included"), true,
					"View PolicySnapShot Page",
					"The Green Upgrades value  " + viewPolicySnapShot.greenUpgradesValue.getData() + " is displayed",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on back button
			viewPolicySnapShot.goBackButton.scrollToElement();
			viewPolicySnapShot.goBackButton.click();
			Assertions.passTest("View policy snapshot page", "Clicked on back button");

			// Click on Renewal Link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal link successfully ");

			// Verifying Expacc message
			Assertions.addInfo("Scenario 02", "Verifying Expacc message");
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				Assertions.verify(policySummaryPage.expaacMessage.checkIfElementIsDisplayed(), true,
						"Policy Renewal Page", policySummaryPage.expaacMessage.getData() + " Message verified", false,
						false);
				Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// clicking on expaac link in home page
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				Assertions.passTest("Home Page", "Clicked on Expaac Link");

				// entering expaac data
				Assertions.verify(expaccInfoPage.policyNumber.checkIfElementIsDisplayed(), true, "Expacc Info Page",
						"Update Expaac Data page loaded successfully", false, false);
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("Expacc Info Page", "Expaac Data updated successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// Find the policy by entering policy Number
				homePage.searchPolicy(policyNumber);
				Assertions.passTest("Home Page", "Policy Number : " + policyNumber + " searched successfully");

				// Click on Renew Policy Hyperlink
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

				// click on continue button in Renewal building review page
				if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
						&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
					policyRenewalPage.continueRenewal.scrollToElement();
					policyRenewalPage.continueRenewal.click();
					Assertions.passTest("Renewal Building Review Page", "Clicked on Continue");
				}
			}

			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overvew Page", "Account Overview Page loaded successfully", false, false);

			// Getting Renewal Quote Number
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is 1 : " + renewalQuoteNumber);
			Assertions.passTest("Create Quote Page", "NamedStorm value is:" + testData.get("NamedStormValue"));
			Assertions.passTest("Create Quote Page", "AOPDeductible Value is:" + testData.get("AOPDeductibleValue"));
			Assertions.passTest("Create Quote Page", "OrdinanceOrLaw is:" + testData.get("OrdinanceOrLaw"));

			// click on release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer button");

			// Click on View Previous Policy link
			accountOverviewPage.viewPreviousPolicyButton.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on View Previous Policy link successfully and Navigated to Policy Summary Page");

			// Click on Endorse Pb link
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked EndorsePB link successfully");

			// Verifying Renewal warning message
			Assertions.addInfo("Scenario 03", "Verifying Renewal warning message");
			Assertions.verify(policyrenewalPage.rnlWarningMSG.checkIfElementIsDisplayed(), true, "Renewal Created Page",
					"Renewal Warning message is verified, Renewal Message is:"
							+ policyrenewalPage.rnlWarningMSG.getData(),
					false, false);
			policyrenewalPage.rnlOkButton.click();
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			Assertions.passTest("Renewal Created Page", "Clicked on OK Button and Navigated to Endorse Policy Page");

			// Enter endorsement effective date
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Entered Endorsement Effective Date successfully,Endorsement Effective Date: "
							+ testData.get("TransactionEffectiveDate"));

			// Click on Change Coverage Options Link on Endorse Policy Page
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on ChangeCoverage Option link successfully and Navigated to Create Quote Page");

			// Assertions
			testData = data.get(data_Value2);
			Assertions.addInfo("Scenario 04", "Verifying the Endorsement changes");
			Assertions.passTest("Create Quote Page", "NamedStorm value changed from: "
					+ createQuotePage.namedStormData.getData() + " to :" + testData.get("NamedStormValue"));
			Assertions.passTest("Create Quote Page", "AOPDeductible value changed from: "
					+ createQuotePage.aopDeductibleData.getData() + " to: " + testData.get("AOPDeductibleValue"));
			Assertions.passTest("Create Quote Page", "OrdinanceOrLaw changed from: "
					+ createQuotePage.ordinanceOrLawDedValue.getData() + " to: " + testData.get("OrdinanceOrLaw"));
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");



			// Changing Deductibles and Optional Coverages
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);

			// Review and continue create quote page
			createQuotePage.scrollToBottomPage();
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.waitTillButtonIsClickable(60);
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Create Quote Page", "Clicked on Continue button successfully");

			// continue with endorsement the endorsement
			Assertions.verify(endorsePolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Endorse Policy Page",
					"Endorse policy page loaded", false, false);

			// Asserting Changes from endorse summary page
			Assertions.addInfo("Scenario 05", "Verifying Green upgrades change in endorse policy page");
			String greenUpgradesFrom = endorsePolicyPage.endorsementSummary.formatDynamicPath(6).getData();
			String greenUpgradesTo = endorsePolicyPage.endorsementSummary.formatDynamicPath(7).getData();
			Assertions.verify(endorsePolicyPage.endorsementSummary.formatDynamicPath(5).checkIfElementIsDisplayed(),
					true, "Endorse Policy Page",
					"Green Upgrades " + greenUpgradesFrom + " changed to : " + greenUpgradesTo + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// click on next button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next button successfully");

			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on view endt quote
			endorsePolicyPage.viewEndorsementQuote.scrollToElement();
			endorsePolicyPage.viewEndorsementQuote.click();
			Assertions.passTest("Endorse Summary Page", "Clicked on view endorsement quote");

			// Verifying Green upgrades change in view endt quote page
			Assertions.verify(viewpriFullQuotePage.closeButton.checkIfElementIsDisplayed(), true,
					"View Endorsement Quote Page", "View Endorsement Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 06", "Verifying Green upgrades is Not selected when it is selected as No");
			Assertions.verify(viewpriFullQuotePage.greenUpgradesValue.getData().contains("Not Selected"), true,
					"View Endorsement Quote Page",
					"Green Upgrades " + viewpriFullQuotePage.greenUpgradesValue.getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// click on close
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Complete the endorsement
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on complete button successfully");

			// Click on Roll Forward option
			Assertions.addInfo("Scenario 07", "Verifying the presence of Roll forward button");
			Assertions.verify(endorsePolicyPage.rollForwardBtn.checkIfElementIsDisplayed(), true,
					"Roll Forward Endorsement Page", "Roll Forward Endorsement Page loaded", false, false);
			Assertions.verify(endorsePolicyPage.rollForwardBtn.checkIfElementIsDisplayed(), true,
					"Roll Forward Endorsement Page", "Roll Forward Button present is verified", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");
			endorsePolicyPage.rollForwardBtn.waitTillVisibilityOfElement(60);
			endorsePolicyPage.rollForwardBtn.scrollToElement();
			endorsePolicyPage.rollForwardBtn.click();
			Assertions.passTest("Roll Forward Endorsement Page",
					"Clicked on Roll Forward Option successfully and Navigated to Endorse Summary Page");

			// Click on close button
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on Close Button successfully and Navigated to Policy Summary Page");

			// Click on View Active Renewal link
			policySummaryPage.viewActiveRenewal.scrollToElement();
			policySummaryPage.viewActiveRenewal.click();
			Assertions.passTest("Policy Summary Page",
					"Clicked on View Active Renewal link successfully and Navigated to Account Overview Page");

			// Getting Renewal Quote Number
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is 2: " + renewalQuoteNumber);

			// Approve referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.approveRenewalReferralUSM(renewalQuoteNumber);
			}

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");

			// Verify Green upgrades is Not selected when it is selected as No
			Assertions.verify(viewpriFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "View Print Full Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 08",
					"Verifying Green upgrades is Not selected when it is selected as No in renewal quote document");
			Assertions.verify(viewpriFullQuotePage.greenUpgradesValue.getData().contains("Not Selected"), true,
					"View Print Full Quote Page",
					"Green Upgrades " + viewpriFullQuotePage.greenUpgradesValue.getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// click on back
			viewpriFullQuotePage.backButton.scrollToElement();
			viewpriFullQuotePage.backButton.click();

			// Click on Edit Deductibles link
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page",
					"Clicked on EditDeductible And Limits link successfully and Navigated to Create Quote Page");
			Assertions.addInfo("Scenario 09", "Verifying deductibles vaues on create quote page");
			String namedstorm = testData.get("NamedStormValue");
			String actualNamedStorm = createQuotePage.namedStormData.getData();
			if (actualNamedStorm.equalsIgnoreCase(namedstorm)) {
				Assertions.verify(actualNamedStorm, namedstorm, "Create Quote Page",
						"NamedStorm value Selected During PB Endorsement is Reflected on Renewal Quote is verified, NamedStorm value is: "
								+ actualNamedStorm,
						false, false);
			} else {
				Assertions.verify(actualNamedStorm, namedstorm, "Create Quote Page",
						"NamedStorm value Selected During PB Endorsement is Not Reflected on Renewal Quote, NamedStorm value is: "
								+ actualNamedStorm,
						false, false);
			}
			String ExpOrdLaw = testData.get("OrdinanceOrLaw");
			String actulaOrdLaw = createQuotePage.ordinanceOrLawDedValue.getData();
			if (!actulaOrdLaw.equalsIgnoreCase(ExpOrdLaw) )  {
			Assertions.verify(!actulaOrdLaw.equalsIgnoreCase(ExpOrdLaw),true, "Create Quote Page","OrdinanceOrLaw value Selected During PB Endorsement is Reflected on Renewal Quote is verified, OrdinanceOrLaw value is: "
								+ actulaOrdLaw,
						false, false);
			} else {
				Assertions.verify(actulaOrdLaw, ExpOrdLaw, "Create Quote Page",
						"OrdinanceOrLaw value Selected During PB Endorsement is Not Reflected on Renewal Quote OrdinanceOrLaw value is: "
								+ actulaOrdLaw,
						false, false);

			}
			String aopDeductiblevalue = testData.get("AOPDeductibleValue");
			String actualAopDeductiblevalue = createQuotePage.aopDedValue.getData();
			if (actualAopDeductiblevalue.equalsIgnoreCase(aopDeductiblevalue)) {
				Assertions.verify(actualAopDeductiblevalue, aopDeductiblevalue, "Create Quote Page",
						"AOP Deductible value Selected During PB Endorsement is Reflected on Renewal Quote is verified, AOP Deductible value is: "
								+ actualAopDeductiblevalue,
						false, false);
			} else {
				Assertions.verify(actulaOrdLaw, aopDeductiblevalue, "Create Quote Page",
						"AOP Deductible value Selected During PB Endorsement is Not Reflected on Renewal Quote,AOP Deductible value is: "
								+ actualAopDeductiblevalue,
						false, false);

			}
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Verify Green upgrades remain No in create quote page
			Assertions.addInfo("Scenario 10", "Verify Green upgrades remains No in create quote page");
			Assertions.verify(createQuotePage.greenUpgradesData.getData(), testData.get("GreenUpgrades"),
					"Create Quote Page",
					"Green Upgrades option " + createQuotePage.greenUpgradesData.getData() + " retained is verified",
					false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Update green upgrades to Yes
			testData = data.get(data_Value1);
			createQuotePage.greenUpgradesArrow.scrollToElement();
			createQuotePage.greenUpgradesArrow.click();
			createQuotePage.greenUpgradesYesOption.scrollToElement();
			createQuotePage.greenUpgradesYesOption.click();
			Assertions.passTest("Create Quote Page",
					"Green Upgrades Value Selected is  : " + createQuotePage.greenUpgradesData.getData());

			// Click on get a quote Link
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page",
					"Clicked on Get a quote successfully and Navigated to Account Overview Page");

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.waitTillButtonIsClickable(60);
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue quote button");

			// Getting Renewal Quote Number
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is 2: " + renewalQuoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");

			Assertions.verify(viewpriFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View Print Full Quote Page", "View Print Full Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 11",
					"Verifying Green upgrades is Not selected when it is selected as No in renewal quote document");
			Assertions.verify(viewpriFullQuotePage.greenUpgradesValue.getData().contains("Included"), true,
					"View Print Full Quote Page",
					"Green Upgrades " + viewpriFullQuotePage.greenUpgradesValue.getData() + " is displayed", false,
					false);
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// click on back
			viewpriFullQuotePage.backButton.scrollToElement();
			viewpriFullQuotePage.backButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button successfully");

			// bind the renewal quote
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, renewalQuoteNumber);
			Assertions.passTest("Account Overview Page",
					"Click on Bind Request Button successfully and Navigated to Request Bind page successfully");

			// enter additional policy information
			requestBindPage.floodCoverageArrow.scrollToElement();
			requestBindPage.floodCoverageArrow.click();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy"))
					.scrollToElement();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy")).click();
			Assertions.passTest("Request Bind Page",
					"Selected the Flood as :" + testData.get("ApplicantHaveFloodPolicy"));
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
					false);

			// Sign Out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC070 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC070 ", "Executed Successfully");
			}
		}
	}
}
