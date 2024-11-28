/** Program Description: Check the Policy is able to be placed under Non Renewal either before or after Renewal offer is created and IO-21801
 *
 *  Author			   :
 *  Date of Creation   : 08/09/2024
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
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RenewalIndicatorsPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC071_PNBREN005 extends AbstractNAHOTest {

	public TC071_PNBREN005() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN005.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		RenewalIndicatorsPage renewalIndicatorsPage = new RenewalIndicatorsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		String quoteNumber;
		String policyNumber;
		int dataValue1 = 0;
		Map<String, String> testData = data.get(dataValue1);
		String covAvalue;
		String inflationGuardPercentage;
		double calCovAValue;
		String actualCovAValue;
		String calCovAValue_s;
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

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting quote number 1
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// clicking on request bind in Account overview page
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Referral page
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			requestBindPage.approveRequestNAHO(testData);

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Go to homepage and serach the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");

			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy successfully");

			// Click on Renew policy
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");

			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				Assertions.verify(policySummaryPage.expaacMessage.checkIfElementIsDisplayed(), true,
						"Policy Renewal Page", policySummaryPage.expaacMessage.getData() + " Message verified", false,
						false);

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
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Adding IO-21801
			// Calculating inflation guard applying to Cov A
			covAvalue = testData.get("L1D1-DwellingCovA");
			inflationGuardPercentage = testData.get("InflationGuardPercentage");
			calCovAValue = (Double.parseDouble(covAvalue) * Double.parseDouble(inflationGuardPercentage));
			Assertions.passTest("View/Print Full Quote Page",
					"NB Coverage A value " + testData.get("L1D1-DwellingCovA"));
			Assertions.passTest("View/Print Full Quote Page",
					"Inflation Guard Percentage " + testData.get("InflationGuardPercentage"));

			// Converting double to string
			calCovAValue_s = Double.toString(calCovAValue).replace(".0", "");

			// Click on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("View/Print Full Quote Page", "Clicked on vieworprint full quote link");

			// Verifying and Asserting actual and calculated Cov A value
			// Cov A = Cov A*inflationguard%(1.0721)
			Assertions.addInfo("Scenario 01", "Verifying inflation guard applied to renewal Cov A");
			actualCovAValue = viewOrPrintFullQuotePage.coveragesValues.formatDynamicPath(4).getData().replace(",", "");
			Assertions.verify(actualCovAValue, "$" + calCovAValue_s, "View Print Full Quote Page",
					"Inflation guard " + testData.get("InflationGuardPercentage")
							+ " applied to Coverage A, actual coverage A value " + actualCovAValue
							+ " and calculated coverage A value $" + calCovAValue_s + " bothe are same",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			// IO-21801 Ended

			// click on view previous policy button
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Previous Policy Button");

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Previous Policy Summary Page loaded successfully", false, false);

			// Click on Renewal Indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// Selecting UW review check box
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);

			// selecting Non-renewal check box
			renewalIndicatorsPage.nonRenewal.waitTillVisibilityOfElement(60);
			renewalIndicatorsPage.nonRenewal.select();
			Assertions.passTest("Renewal Indicators Page", "Selected the Non-Renewal check box successfully");

			// Verifying the Dropdown for Non-Renewal reasons, Legal wordings and Comments
			// fields available
			Assertions.addInfo("Scenario 02",
					"Verifying the Dropdown for Non-Renewal reasons, Legal wordings and Comments fields available");
			Assertions.verify(renewalIndicatorsPage.nonRenewalReasonArrow.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Non-Renewal Reason dropdown displayed is verified", false, false);

			Assertions.verify(renewalIndicatorsPage.nonRenewalLegalNoticeWording.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "The Legal Notice Wording field available is verified", false, false);

			Assertions.verify(renewalIndicatorsPage.nonRenewalInternalComments.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "The Internal Comments field available is verified", false, false);
			Assertions.addInfo("Scenario 2", "Scenario 02 Ended");

			// Assert the Non renewal reasons in the dropdown
			Assertions.addInfo("Scenario 03", "Asserting the Non renewal reasons in the dropdown");
			for (int i = 0; i < 5; i++) {
				int dataValuei = i;
				testData = data.get(dataValuei);
				renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
				renewalIndicatorsPage.nonRenewalReasonArrow.click();
				String nonRenewalReason = renewalIndicatorsPage.nonRenewalReasonOption
						.formatDynamicPath(testData.get("NonRenewalReason")).getData();
				renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
						.scrollToElement();
				renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
						.click();
				Assertions.passTest("Renewal Indicators Page",
						"The Non-Renewal Reason " + nonRenewalReason + " present is verified");
			}
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Selecting a Non renewal reason enter the legal wordings
			testData = data.get(dataValue1);
			renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonArrow.click();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
					.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason")).click();
			Assertions.passTest("Renewal Indicators Page", "The Non-Renewal Reason " + "'"
					+ testData.get("NonRenewalReason") + "'" + " selected successfully");

			renewalIndicatorsPage.nonRenewalLegalNoticeWording.setData(testData.get("NonRenewalLegalNoticeWording"));
			renewalIndicatorsPage.nonRenewalInternalComments.setData(testData.get("NonRenewalComment"));
			Assertions.passTest("Renewal Indicators Page", "Non Renewal Details entered successfully");

			// Click on Update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update button");

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Asserting warning message for outstanding renewal will be deleted'The
			// Existing Renewal has been removed from the policy. The Non-Renewal Notice for
			// this policy will be sent by ---date
			Assertions.addInfo("Scenario 04", "Asserting warning message for outstanding renewal will be deleted");
			Assertions.verify(policySummaryPage.nocMessage.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Warning message " + policySummaryPage.nocMessage.getData() + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting policy status
			Assertions
					.verify(policySummaryPage.autoRenewalIndicators.checkIfElementIsDisplayed(), true,
							"Policy Summary Page", "The Policy Status : "
									+ policySummaryPage.autoRenewalIndicators.getData() + " displayed is verified",
							false, false);

			// Click on Renewal Indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// Selecting UW review check box
			Assertions.verify(renewalIndicatorsPage.updateButton.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "Renewal Indicators Page loaded successfully", false, false);

			// un selecting Non-renewal check box
			renewalIndicatorsPage.nonRenewal.waitTillVisibilityOfElement(60);
			renewalIndicatorsPage.nonRenewal.deSelect();
			Assertions.passTest("Renewal Indicators Page", "Unselected the Non-Renewal check box successfully");

			// Click on Update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update button");

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Click on Renew policy
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");

			// Getting Renewal Quote Number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNumber);

			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.approveRenewalReferralUSM(renewalQuoteNumber);
			}

			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on release to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			// click on view previous policy button
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Previous Policy Button");

			// Click on Renewal Indicators link
			policySummaryPage.renewalIndicators.scrollToElement();
			policySummaryPage.renewalIndicators.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Indicators link");

			// selecting Non-renewal check box
			renewalIndicatorsPage.nonRenewal.waitTillVisibilityOfElement(60);
			renewalIndicatorsPage.nonRenewal.select();
			Assertions.passTest("Renewal Indicators Page", "Selected the Non-Renewal check box successfully");

			// Selecting a Non renewal reason enter the legal wordings
			renewalIndicatorsPage.nonRenewalReasonArrow.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonArrow.click();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason"))
					.scrollToElement();
			renewalIndicatorsPage.nonRenewalReasonOption.formatDynamicPath(testData.get("NonRenewalReason")).click();
			Assertions.passTest("Renewal Indicators Page", "The Non-Renewal Reason selected successfully");

			renewalIndicatorsPage.nonRenewalLegalNoticeWording.setData(testData.get("NonRenewalLegalNoticeWording"));
			renewalIndicatorsPage.nonRenewalInternalComments.setData(testData.get("NonRenewalComment"));
			Assertions.passTest("Renewal Indicators Page", "Non Renewal Details entered successfully");

			// Click on Update
			renewalIndicatorsPage.updateButton.scrollToElement();
			renewalIndicatorsPage.updateButton.click();
			Assertions.passTest("Renewal Indicators Page", "Clicked on Update button");

			// Verify the warning message as internal user cannot set up non renewal.' A
			// renewal offer has already generated. A non-renewal cannot be processed until
			// the current term has expired
			Assertions.addInfo("Scenario 05",
					"Verifying and asserting the warning message as internal user cannot set up non renewal");
			Assertions.verify(renewalIndicatorsPage.nonRenewalErrorMessage.checkIfElementIsDisplayed(), true,
					"Renewal Indicators Page", "The Warning message "
							+ renewalIndicatorsPage.nonRenewalErrorMessage.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC071 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC071 ", "Executed Successfully");
			}
		}
	}

}
