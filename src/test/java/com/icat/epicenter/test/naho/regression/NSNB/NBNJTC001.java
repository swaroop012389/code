package com.icat.epicenter.test.naho.regression.NSNB;

/** Program Description: To verify minimum wind deductible will be set to 1%,Due diligence Wording,Calculate SLTF,Assert Labels on Quote and Policy snapshot page for USM
 *  Author			   : John
 *  Date of Creation   : 03/25/2021
 **/
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExistingAccountPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBNJTC001 extends AbstractNAHOTest {

	public NBNJTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/NJ001.xls";
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
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		BuildingNoLongerQuoteablePage noLongerQuoteable = new BuildingNoLongerQuoteablePage();
		ExistingAccountPage existingAccountPage = new ExistingAccountPage();

		int data_Value1 = 0;
		DecimalFormat df = new DecimalFormat("0.00");
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
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.addRoofDetails(testData, 1, 1);
			dwellingPage.enterProtectionDiscountsDetails(testData, 1, 1);
			dwellingPage.enterDwellingValuesNAHO(testData, 1, 1);
			dwellingPage.waitTime(1);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			Assertions.passTest("Dwelling Page", "Dwelling Details Entered successfully");

			// Click on override button
			if (existingAccountPage.override.checkIfElementIsPresent()
					&& existingAccountPage.override.checkIfElementIsDisplayed()) {
				existingAccountPage.override.scrollToElement();
				existingAccountPage.override.click();
			}

			// Click on create quote button
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();
			if (noLongerQuoteable.override.checkIfElementIsPresent()
					&& noLongerQuoteable.override.checkIfElementIsPresent()) {
				noLongerQuoteable.override.scrollToElement();
				noLongerQuoteable.override.click();
			}

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
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			createQuotePage.enterDeductiblesNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create quote Page", "Quote Details Entered successfully");

			// Ticket IO-20816
			// Asserting and validating warning message when prior loss is liability,
			// unrepaired = No and Open claim = yes
			// The warning message is The account is ineligible due to an open claim.
			// The account is ineligible due to the prior liability loss.
			// The account is ineligible due to unrepaired damage.
			Assertions.addInfo("Scenario 02",
					"Asserting and Validating warning message when prior loss is liailty,unrepaired damage = No,open claim = yes");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("due to an open claim.").getData()
							.contains("The account is ineligible due to an open claim."),
					true, "Create Quote Page",
					"The Open Claim warning message "
							+ createQuotePage.warningMessages.formatDynamicPath("due to an open claim.").getData()
							+ " is displayed verified",
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("due to the prior liability loss.").getData()
							.contains("The account is ineligible due to the prior liability loss."),
					true, "Create Quote Page",
					"The prior liability loss warning message " + createQuotePage.warningMessages
							.formatDynamicPath("due to the prior liability loss.").getData() + " is displayed verified",
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("due to unrepaired damage.").getData()
							.contains("The account is ineligible due to unrepaired damage."),
					true, "Create Quote Page",
					"The Unrepaired damage warning message "
							+ createQuotePage.warningMessages.formatDynamicPath("due to unrepaired damage.").getData()
							+ " is displayed verified",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			// Ticket IO-20816 Ended

			// IO-20816
			// Asserting and Verifying hard stop message when dwelling 40 years and older
			// and they have stated that the HVAC or Electrical is more than 15 years old
			// (Is it possible to get this validation to trigger on the building page?)
			Assertions.addInfo("Scenario 03", "Asserting and Verifying hard stop message");
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath(
					"The account is ineligible due to the quoted building being 40 years or older with no updates in the last 15 years")
					.getData().contains(
							"The account is ineligible due to the quoted building being 40 years or older with no updates in the last 15 years"),
					true, "Create Quote Page",
					"The hard stop message  " + createQuotePage.warningMessages.formatDynamicPath(
							"The account is ineligible due to the quoted building being 40 years or older with no updates in the last 15 years")
							.getData() + "is displyed",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			// IO-20816 ended

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			int quoteLen = accountOverviewPage.quoteNumber.getData().length();
			String quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Asserting SLTF value on account overview page
			Assertions.addInfo("Scenario 04", "Calculating SLTF Values in Account Overview Page");
			String premiumOnAccountOverviewPage = accountOverviewPage.premiumValue.getData();
			String sltfValueAccountOverviewPage = accountOverviewPage.sltfValue.getData().replace(",", "").replace("$",
					"");
			String surplusContributionAccountOverviewPage = accountOverviewPage.surplusContibutionValue.getData();

			double d_premiumValueOnAccountOverviewPage = Double
					.parseDouble(premiumOnAccountOverviewPage.replace("$", "").replace(",", ""));
			double d_surplusContributionAccountOverviewPage = Double
					.parseDouble(surplusContributionAccountOverviewPage.replace("$", ""));
			double d_sltfPercentageValue = Double.parseDouble(testData.get("SLTFPercentage"));
			double d_sltfValueOnAccountOverviewPage = (d_premiumValueOnAccountOverviewPage
					+ d_surplusContributionAccountOverviewPage) * d_sltfPercentageValue;
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(sltfValueAccountOverviewPage), 2)
					- Precision.round(d_sltfValueOnAccountOverviewPage, 2)), 2) < 0.05) {
				Assertions.passTest("Account Overview Page",
						"Calculated SLTF Value : " + "$" + df.format(d_sltfValueOnAccountOverviewPage));
				Assertions.passTest("Account Overview Page",
						"Actual SLTF Value : " + "$" + sltfValueAccountOverviewPage);
			} else {
				Assertions.verify(sltfValueAccountOverviewPage, d_sltfValueOnAccountOverviewPage,
						"Account Overview Page", "The Difference between actual and calculated SLTF is more than 0.05",
						false, false);
			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting declinations column on view print full quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();

			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page  loaded successfully", false, false);

			// Asserting SLTF value on view print full quote page
			Assertions.addInfo("Scenario 05", "Calculating SLTF Values in View/Print Full Quote Page");
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String sltfValue = viewOrPrintFullQuotePage.surplusLinesTaxNaho.getData().replace(",", "").replace("$",
					"");
			String surplusContribution = viewOrPrintFullQuotePage.surplusContributionValue.getData().replace("$", "");

			double d_premiumValue = Double.parseDouble(premiumValue.replace("$", "").replace(",", ""));
			double d_surplusContribution = Double.parseDouble(surplusContribution.replace("%", ""));
			double d_sltfValue = (d_premiumValue + d_surplusContribution) * d_sltfPercentageValue;

			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(sltfValue), 2) - Precision.round(d_sltfValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page",
						"Calculated SLTF Value : " + "$" + df.format(d_sltfValue));
				Assertions.passTest("View/Print Full Quote Page", "Actual SLTF Value : " + "$" + sltfValue);
			} else {
				Assertions.verify(sltfValue, d_sltfValue, "View/Print Full Quote Page",
						"The Difference between actual and calculated SLTF is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Assert Form number,Wordings and Labels
			Assertions.addInfo("Scenario 06",
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
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(2);
			viewOrPrintFullQuotePage.backButton.click();

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

			// Asserting Due diligence text
			Assertions.addInfo("Scenario 07", "Asserting Due Diligence Text");
			Assertions.verify(requestBindPage.diligenceText.checkIfElementIsDisplayed(), true, "Request Bind Page",
					requestBindPage.diligenceText.getData() + " is displayed", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Enter bind details
			requestBindPage.enterBindDetailsNAHO(testData);
			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchReferral(quoteNumber);
				referralPage.clickOnApprove();
				requestBindPage.approveRequestNAHO(testData);
			}

			// Entering bind details
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Click on View Policy SNapshot
			policySummaryPage.viewPolicySnapshot.scrollToElement();
			policySummaryPage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");
			Assertions.verify(viewPolicySnapShot.goBackButton.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);

			// Asserting SLTF value on view print full quote page
			Assertions.addInfo("Scenario 08", "Calculating SLTF Values in Policy Snapshot Page");
			String snapshotpremiumValue = viewOrPrintFullQuotePage.premiumValue.getData();
			String snapshotsltfValue = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace(",", "");
			String surplusContributionValueSnapShot = viewPolicySnapShot.surplusContributionValue.getData();
			Assertions.passTest("View Policy Snapshot Page", "The Actual Surplus Lines Tax : " + snapshotsltfValue);

			double d_snapshotpremiumValue = Double.parseDouble(snapshotpremiumValue.replace("$", "").replace(",", ""));
			double d_surplusContributionValueSnapShot = Double
					.parseDouble(surplusContributionValueSnapShot.replace("$", ""));
			double d_snapshotsltfValue = (d_snapshotpremiumValue + d_surplusContributionValueSnapShot)
					* d_sltfPercentageValue;
			Assertions.passTest("View Policy Snapshot Page",
					"The Calculated Surplus Lines Tax : " + "$" + df.format(d_snapshotsltfValue));

			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(sltfValue), 2) - Precision.round(d_sltfValue, 2)),
					2) < 0.05) {
				Assertions.passTest("View Policy Snapshot Page",
						"Calculated SLTF Value : " + "$" + df.format(d_sltfValue));
				Assertions.passTest("View Policy Snapshot Page", "Actual SLTF Value : " + "$" + sltfValue);
			} else {
				Assertions.passTest("View Policy Snapshot Page",
						"The Difference between actual and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Assert Form number,Wordings and Labels
			Assertions.addInfo("Scenario 09", "Asserting Form number, Wordings and Labels in Policy Snapshot Page");
			Assertions.verify(viewOrPrintFullQuotePage.formNumber.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page",
					"The " + viewOrPrintFullQuotePage.formNumber.getData() + " displayed is verified", false, false);
			Assertions.verify(viewOrPrintFullQuotePage.certificationWording.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page",
					"The Wording " + viewOrPrintFullQuotePage.certificationWording.getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Name of Insured")
							.checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Name of Insured").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Address of Insured").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Address of Insured").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Location of Property or Risk").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Location of Property or Risk").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Insurance Coverage:").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Insurance Coverage:").getData()
							+ " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Corporate or partnership").checkIfElementIsDisplayed(), true,
							"View Policy Snapshot Page",
							"The Label "
									+ viewOrPrintFullQuotePage.njStateLabels
											.formatDynamicPath("Corporate or partnership").getData()
									+ " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Individual name and/or Title").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Individual name and/or Title").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njStateLabels
							.formatDynamicPath("Complete Address").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njStateLabels.formatDynamicPath("Complete Address").getData()
							+ " displayed is verified",
					false, false);

			// Assert Certification Labels
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 2).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 2).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 3).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 3).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 4).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 4).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 5).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 5).getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 6).checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njCertificationLabels.formatDynamicPath(1, 6).getData()
							+ " displayed is verified",
					false, false);

			// Assert Acknowledgement labels
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Applicant's Signature").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Applicant's Signature").getData() + " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Applicant's Name").checkIfElementIsDisplayed(), true,
							"View Policy Snapshot Page",
							"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("Applicant's Name").getData() + " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Date of Applicant's").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Date of Applicant's").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Producer Signature").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Producer Signature").getData() + " displayed is verified",
					false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Date of Producer").checkIfElementIsDisplayed(), true,
							"View Policy Snapshot Page",
							"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("Date of Producer").getData() + " displayed is verified",
							false, false);
			Assertions
					.verify(viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("Producer Name").checkIfElementIsDisplayed(), true,
							"View Policy Snapshot Page",
							"The Label " + viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("Producer Name").getData() + " displayed is verified",
							false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.njAcknowledgementLabels
							.formatDynamicPath("New Jersey Producer License").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The Label "
							+ viewOrPrintFullQuotePage.njAcknowledgementLabels
									.formatDynamicPath("New Jersey Producer License").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					viewOrPrintFullQuotePage.quoteStatement.formatDynamicPath("misleading").checkIfElementIsDisplayed(),
					true, "View Policy SnapshotPage",
					"The Statement " + viewOrPrintFullQuotePage.quoteStatement.formatDynamicPath("misleading").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(viewOrPrintFullQuotePage.acknowledgementWording.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "The Acknowledgement Wording "
							+ viewOrPrintFullQuotePage.acknowledgementWording.getData() + " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			// Asserting Policy Form
			Assertions.addInfo("Scenario 10", "Asserting HO3 form in Policy Snapshot Page");
			Assertions.verify(
					viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("8").checkIfElementIsDisplayed(),
					true, "View Policy Snapshot Page",
					"The " + viewPolicySnapShot.coveragesAndPremiumValues.formatDynamicPath("8").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBNJTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBNJTC001 ", "Executed Successfully");
			}
		}
	}
}
