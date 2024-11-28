/** Program Description: To verify minimum wind deductible will be set to 1%,Due diligence Wording,Calculate SLTF,Assert Labels on Quote and Policy snapshot page for Producer
 *  Author			   : John
 *  Date of Creation   : 03/25/2021
 **/
package com.icat.epicenter.test.naho.regression.NSNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBNJTC002 extends AbstractNAHOTest {

	public NBNJTC002() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/NJ002.xls";
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
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		ReferralPage referralPage = new ReferralPage();
		LoginPage loginPage = new LoginPage();
		DecimalFormat df = new DecimalFormat("0.00");
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();

		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		String quoteNumber;
		int quoteLen;
		boolean isTestPassed = false;

		try {
			// Creating New Account
			homePage.enterPersonalLoginDetails();
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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
			Assertions.addInfo("Scenario 01", "Asserting Default NS value in Create Quote Page");
			Assertions.verify(createQuotePage.namedStormData.getData(), testData.get("NamedStormValue"),
					"Create Quote Page", "Named Strom default value is " + testData.get("NamedStormValue"), false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Entering Quote Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsNAHO(testData);
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Below code adding because of quote is referring because of modeling service
			// down message
			if (referQuotePage.referQuote.checkIfElementIsPresent()
					&& referQuotePage.referQuote.checkIfElementIsDisplayed()) {
				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// SignOut as Producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as Producer successfully");

				// Sign in as USM
				loginPage.refreshPage();
				Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
						"Login page loaded successfully", false, false);
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number
				// link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);

				// homePage.searchReferral(quoteNumber);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();

				// approving referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// SignOut as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Home Page", "Logged out as USM successfully");

				// Sign in as Producer
				loginPage.refreshPage();
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as Producer successfully");

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// Search for quote
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchQuoteByProducer(quoteNumber);
				Assertions.passTest("Home Page", "Searched the Quote " + quoteNumber + " sucessfully");

			}
			// Ended

			// getting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Asserting SLTF value on account overview page
			Assertions.addInfo("Scenario 02", "Calculating SLTF Values in Account Overview Page");
			String premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "");
			String surplusContributionAccountOverviewPage = accountOverviewPage.surplusContibutionValue.getData();
			Assertions.passTest("Account Overview Page",
					"The Actual Surplus Lines Taxes & Fees : " + sltfValueAccountOverviewPage);

			double d_premiumValueOnAccountOverviewPage = Double
					.parseDouble(premiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_surplusContributionAccountOverviewPage = Double
					.parseDouble(surplusContributionAccountOverviewPage.replace("$", ""));
			double d_sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_sltfValueOnAccountOverviewPage = (d_premiumValueOnAccountOverviewPage
					+ d_surplusContributionAccountOverviewPage) * d_sltfPercentageValue;
			Assertions.passTest("Account Overview Page",
					"The Calculated Surplus Lines Taxes & Fees : " + "$" + df.format(d_sltfValueOnAccountOverviewPage));
			Assertions.verify(sltfValueAccountOverviewPage, "$" + df.format(d_sltfValueOnAccountOverviewPage),
					"Account Overview Page",
					"Actual and Calculated SLTF Values are matching and calculated according to 5% for NJ", false,
					false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Asserting declinations column on view print full quote
			Assertions.addInfo("Scenario 03", "Calculating SLTF Values in View/Print Full Quote Page");
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page  loaded successfully", false, false);

			// Asserting SLTF value on view print full quote page
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String sltfValue = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "");
			Assertions.passTest("View/Print Full Quote Page", "The Actual Surplus Lines Tax : " + "$" + sltfValue);

			double d_premiumValue = Double.parseDouble(premiumValue.replace("$", "").replace(",", ""));
			double d_surplusContribution = Double.parseDouble(surplusContribution.replace("%", ""));
			double d_sltfValue = (d_premiumValue + d_surplusContribution) * d_sltfPercentageValue;
			Assertions.passTest("View/Print Full Quote Page",
					"The Calculated Surplus Lines Tax : " + "$" + df.format(d_sltfValue));

			Assertions.verify(sltfValue, "$" + df.format(d_sltfValue), "View/Print Full Quote Page",
					"Actual and Calculated SLTF Values are matching and calculated according 5% for NJ", false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Assert Form number,Wordings and Labels
			Assertions.addInfo("Scenario 04",
					"Asserting Form number, Wordings and Labels in View/Print Full Quote Page");
			Assertions.verify(viewOrPrintFullQuotePage.formNumber.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The " + viewOrPrintFullQuotePage.formNumber.getData() + " displayed is verified", false, false);
			Assertions.verify(viewOrPrintFullQuotePage.certificationWording.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page",
					"The Wording " + viewOrPrintFullQuotePage.certificationWording.getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Name of Insured")
							.checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Name of Insured").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Address of Insured").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Address of Insured").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Location of Property or Risk").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Location of Property or Risk").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Insurance Coverage:").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Insurance Coverage:").getData()
							+ " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Corporate or partnership").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"The Label "
									+ viewOrPrintFullQuotePage.njStateLabels
											.formatDynamicPath("Corporate or partnership").getData()
									+ " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Individual name and/or Title").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Individual name and/or Title").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Complete Address").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Complete Address").getData()
							+ " displayed is verified",
					false, false);

			// Assert Certification Labels
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 2).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 2).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 3).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 4).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 4).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 5).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 5).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 6).checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 6).getData()
							+ " displayed is verified",
					false, false);

			// Assert Acknowledgement labels
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Applicant's Signature").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Applicant's Signature").getData() + " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Applicant's Name").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("Applicant's Name").getData() + " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Date of Applicant's").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Date of Applicant's").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Producer Signature").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Producer Signature").getData() + " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Date of Producer").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("Date of Producer").getData() + " displayed is verified",
							false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Producer Name").checkIfElementIsDisplayed(), true,
							"View/Print Full Quote Page",
							"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("Producer Name").getData() + " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("New Jersey Producer License").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("New Jersey Producer License").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.quoteStatement.formatDynamicPath("misleading").checkIfElementIsDisplayed(),
					true, "View/Print Full Quote Page",
					"The Statement " + viewOrPrintFullQuotePage.quoteStatement.formatDynamicPath("misleading").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.acknowledgementWording.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "The Acknowledgement Wording "
							+ viewOrPrintFullQuotePage.acknowledgementWording.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);

			// Clicking on answer no button
			underwritingQuestionsPage.answerNoToAllQuestions.waitTillVisibilityOfElement(30);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.answerNoToAllQuestions.scrollToElement();
			underwritingQuestionsPage.answerNoToAllQuestions.click();

			// Clicking on save button
			underwritingQuestionsPage.saveButton.scrollToElement();
			underwritingQuestionsPage.saveButton.click();
			Assertions.addInfo("Scenario 05", "Asserting Due Diligence Text");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					requestBindPage.diligenceText.getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Entering bind details
			requestBindPage.enterBindDetailsNAHO(testData);

			// SignOut as Producer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as Producer successfully");

			// Sign in as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");
			// searching the quote number in grid and clicking on the quote number link
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

			accountOverviewPage.uploadPreBindApproveAsUSM();
			// approving referral
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Asserting policy number
			String policyNumber = policySummaryPage.policyNumber.getData();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged Out as USM Successfully");

			// Login as producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			homePage.searchPolicyByProducer(policyNumber);

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			policySummaryPage.quoteNoLinkNAHO.scrollToElement();
			policySummaryPage.quoteNoLinkNAHO.click();
			Assertions.passTest("Policy Summary Page", "Clicked on QuoteNumber link");
			Assertions.verify(viewOrPrintFullQuotePage.closeButton.checkIfElementIsDisplayed(), true,
					"Quote Document Page", "Quote Document Page loaded successfully", false, false);

			// Asserting SLTF value on view print full quote page
			Assertions.addInfo("Scenario 06", "Calculating SLTF Values in Policy Snapshot Page");
			String snapshotpremiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String snapshotsltfValue = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "");
			String surplusContributionValueSnapShot = viewPolicySnapShot.surplusContributionValue.getData()
					.replace("$", "").replace("%", "");
			Assertions.passTest("Quote Document Page", "The Actual Surplus Lines Tax : " + snapshotsltfValue);

			double d_snapshotpremiumValue = Double.parseDouble(snapshotpremiumValue.replace("$", "").replace(",", ""));
			double d_surplusContributionValueSnapShot = Double
					.parseDouble(surplusContributionValueSnapShot.replace("$", ""));
			double d_snapshotsltfValue = (d_snapshotpremiumValue + d_surplusContributionValueSnapShot)
					* d_sltfPercentageValue;
			Assertions.passTest("Quote Document Page",
					"The Calculated Surplus Lines Tax : " + "$" + df.format(d_snapshotsltfValue));

			Assertions.verify(sltfValue, "$" + df.format(d_sltfValue), "Quote Document Page",
					"Actual and Calculated SLTF Values are matching and calculated according 5% for NJ", false, false);
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Assert Form number,Wordings and Labels
			Assertions.addInfo("Scenario 07", "Asserting Form number, Wordings and Labels in Policy Snapshot Page");
			Assertions.verify(viewOrPrintFullQuotePage.formNumber.checkIfElementIsDisplayed(), true,
					"Quote Document Page",
					"The " + viewOrPrintFullQuotePage.formNumber.getData() + " displayed is verified", false, false);
			Assertions.verify(viewOrPrintFullQuotePage.certificationWording.checkIfElementIsDisplayed(), true,
					"Quote Document Page",
					"The Wording " + viewOrPrintFullQuotePage.certificationWording.getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Name of Insured")
							.checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Name of Insured").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Address of Insured").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Address of Insured").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Location of Property or Risk").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Location of Property or Risk").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Insurance Coverage:").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Insurance Coverage:").getData()
							+ " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Corporate or partnership").checkIfElementIsDisplayed(), true,
							"Quote Document Page",
							"The Label "
									+ viewOrPrintFullQuotePage.njStateLabels
											.formatDynamicPath("Corporate or partnership").getData()
									+ " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Individual name and/or Title").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Individual name and/or Title").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Complete Address").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Complete Address").getData()
							+ " displayed is verified",
					false, false);

			// Assert Certification Labels
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 2).checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 2).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 3).checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 4).checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 4).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 5).checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 5).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 6).checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 6).getData()
							+ " displayed is verified",
					false, false);

			// Assert Acknowledgement labels
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Applicant's Signature").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Applicant's Signature").getData() + " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Applicant's Name").checkIfElementIsDisplayed(), true,
							"Quote Document Page",
							"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("Applicant's Name").getData() + " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Date of Applicant's").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Date of Applicant's").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Producer Signature").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Producer Signature").getData() + " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Date of Producer").checkIfElementIsDisplayed(), true,
							"Quote Document Page",
							"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("Date of Producer").getData() + " displayed is verified",
							false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Producer Name").checkIfElementIsDisplayed(), true,
							"Quote Document Page",
							"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("Producer Name").getData() + " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("New Jersey Producer License").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("New Jersey Producer License").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.quoteStatement.formatDynamicPath("misleading").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The Statement " + viewOrPrintFullQuotePage.quoteStatement.formatDynamicPath("misleading").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.acknowledgementWording.checkIfElementIsDisplayed(), true,
					"Quote Document Page", "The Acknowledgement Wording "
							+ viewOrPrintFullQuotePage.acknowledgementWording.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Asserting Policy Form
			Assertions.addInfo("Scenario 08", "Asserting HO3 form in Policy Snapshot Page");
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("8").checkIfElementIsDisplayed(),
					true, "Quote Document Page",
					"The " + viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("8").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBNJTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBNJTC002 ", "Executed Successfully");
			}
		}
	}
}
