package com.icat.epicenter.test.naho.regression.ISPNB;

import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC055_PNBGRA007 extends AbstractNAHOTest {

	public TC055_PNBGRA007() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBGRA007.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		BuildingNoLongerQuoteablePage buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ViewOrPrintFullQuotePage viewpriFullQuotePage = new ViewOrPrintFullQuotePage();
		String namedStorm = "2%";
		int data_Value1 = 0;
		int data_Value2 = 1;
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

			// Io-20810
			// Verifying minimum default named storm deductible is 2% as USM
			Assertions.addInfo("Scenario 01", "Verifying minimum default named storm deductible is 2% as USM");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"All other risks in TX, outside of Tri County, will have a minimum Named Storm deductible set to "
							+ createQuotePage.namedStormData.getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			// Adding Policy fee and Inspection fee
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Adding the ticket IO-20902 as part of NB
			// click on edit deductibles and limits
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();
			Assertions.passTest("Account Overview Page", "Clicked on edit deductibles and limits");

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 02",
					"Verifying Coverage E and Coverage F are retained None during NB Policy creation");
			Assertions.verify(createQuotePage.covEValue.getData(), testData.get("L1D1-DwellingCovE"),
					"Create Quote Page", "The Coverage E value retained is " + createQuotePage.covEValue.getData(),
					false, false);
			Assertions.verify(createQuotePage.covFValue.getData(), testData.get("L1D1-DwellingCovF"),
					"Create Quote Page", "The Coverage F value retained is " + createQuotePage.covFValue.getData(),
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Go to home page and open the quote
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			homePage.searchQuote(quoteNumber);

			// Assert Other Deductible Options
			Assertions.addInfo("Scenario 03", "Asserting other deductible options");
			String allOtherPeril = accountOverviewPage.altQuoteOptEarthquakeDed.formatDynamicPath(1).getData();
			String aop_YourQuote = accountOverviewPage.altQuoteOptEarthquakeDed.formatDynamicPath(2).getData();
			String aop_Opt1 = accountOverviewPage.altQuoteOptEarthquakeDed.formatDynamicPath(3).getData();
			String named_Storm = accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath(1).getData();
			String namedStorm_YourQuote = accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath(2)
					.getData();
			String namedStorm_Opt1 = accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath(3).getData();
			String totalPremium_YourQuote = accountOverviewPage.quoteOptionsTotalPremium.getData();
			String totalPremium_Opt1 = accountOverviewPage.quoteOptions1TotalPremium.getData();
			Assertions.verify(
					accountOverviewPage.altQuoteOptEarthquakeDed.formatDynamicPath(1).checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					allOtherPeril + " - Your Quote Value is : " + aop_YourQuote + " Opt1 Value is : " + aop_Opt1, false,
					false);
			Assertions.verify(
					accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath(1)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", named_Storm + " - Your Quote Value is : " + namedStorm_YourQuote
							+ " Opt1 Value is : " + namedStorm_Opt1,
					false, false);
			Assertions.verify(
					accountOverviewPage.altQuoteOptChosenCoverageOption.formatDynamicPath(1)
							.checkIfElementIsDisplayed(),
					true, "Account Overview Page", "Total Premium & ICAT Fees" + " - Your Quote Value is : "
							+ totalPremium_YourQuote + " Opt1 Value is : " + totalPremium_Opt1,
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Binding the quote and creating the policy
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

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Clicking on homepage button

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Asserting policy number
			String policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + policyNumber, false, false);

			// Click on PB endorsement
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Endorse PB link");

			// Entering Endorsement effective date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page Loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// click on change coverage options link
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change coverage options link");

			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 04",
					"Verifying Coverage E and Coverage F are retained None during Endorsement");
			Assertions.verify(createQuotePage.covEValue.getData(), testData.get("L1D1-DwellingCovE"),
					"Create Quote Page", "The Coverage E value retained is " + createQuotePage.covEValue.getData(),
					false, false);
			Assertions.verify(createQuotePage.covFValue.getData(), testData.get("L1D1-DwellingCovF"),
					"Create Quote Page", "The Coverage F value retained is " + createQuotePage.covFValue.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Add Coverage E value
			testData = data.get(data_Value2);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page", "Insured value entered successfully");

			// click on continue
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();

			// click on continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Click on next
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Next button");

			// click on continue
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// Adding the ticket IO-20877
			// Getting Surplus contribution value of Annual column
			String annualSurplusContribution = endorsePolicyPage.surplusContributionValue.formatDynamicPath("3")
					.getData();

			// Click on view endorsement quote
			endorsePolicyPage.viewEndorsementQuote.scrollToElement();
			endorsePolicyPage.viewEndorsementQuote.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on View Endorsement Quote Button");
			Assertions.verify(viewpriFullQuotePage.closeButton.checkIfElementIsDisplayed(), true,
					"View Endorsement Quote Page", "View Endorsement Quote Page loaded successfully", false, false);

			// Verifying View endorsement quote has surplus contribution value same as
			// annual txn value
			Assertions.addInfo("Scenario 05",
					"Verifying view endorsement quote has surplus contribution value same as annual transaction value of endorse policy page");
			Assertions.verify(viewpriFullQuotePage.surplusContributionValue.getData(), annualSurplusContribution,
					"View Endorsement Quote Page", annualSurplusContribution, false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// click on close

			viewpriFullQuotePage.closeButton.scrollToElement();
			viewpriFullQuotePage.closeButton.click();

			// click on complete
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// click on close
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Renew policy
			testData = data.get(data_Value1);
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link");

			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {

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

			// Getting renewal quote number
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				quoteLen = accountOverviewPage.quoteNumber.getData().length();
				quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
				Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + quoteNumber);

				// Click on open referral link
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
				Assertions.passTest("Account Overview Page", "Clicked on open referral link successfully");

				// Approve Referral
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();

				// Go to home page and Search for renewal quote number
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuote(quoteNumber);

			}
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);

			// Click on edit deductibles
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on create another quote link successfully");

			// Io-20810
			// Verifying minimum default named storm deductible is 2% on renewal quote
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 06", "Verifying minimu default named storm deductible is 2% on renewal quote");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"All other risks in TX, outside of Tri County, renewal quote will have a minimum Named Storm deductible set to "
							+ createQuotePage.namedStormData.getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on previous link
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			Assertions.passTest("Account Overview Page", "Clicked on getAQuote link successfully");
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + quoteNumber);
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);

			// Click on previous policy link
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy summary page loaded successfully", false, false);

			// Click on rewrite link
			policySummaryPage.rewritePolicy.scrollToElement();
			policySummaryPage.rewritePolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on rewrite policy link successfully");

			policyRenewalPage.deleteAndContinue.scrollToElement();
			policyRenewalPage.deleteAndContinue.click();
			Assertions.verify(accountOverviewPage.createAnotherQuote.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account overview page loaded successfully", false, false);

			// Click on create another quote
			accountOverviewPage.createAnotherQuote.scrollToElement();
			accountOverviewPage.createAnotherQuote.click();
			Assertions.passTest("Account Overview Page", "Clicked on create another quote link successfully");

			if (buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()
					&& buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed()) {
				buildingNoLongerQuoteablePage.overrideNoLongerQuotableBuildings();
			}

			// Io-20810
			// Verifying minimu default named storm deductible is 2% on rewrite quote
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			Assertions.addInfo("Scenario 07", "Verifying minimu default named storm deductible is 2% on rewrite quote");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"All other risks in TX, outside of Tri County, rewrite quote will have a minimum Named Storm deductible set to "
							+ createQuotePage.namedStormData.getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on previous link
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Rewrite Quote Number is : " + quoteNumber);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC055 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC055 ", "Executed Successfully");
			}
		}
	}
}
