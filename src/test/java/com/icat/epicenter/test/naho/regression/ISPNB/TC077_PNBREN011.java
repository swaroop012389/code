package com.icat.epicenter.test.naho.regression.ISPNB;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.ConfirmBindRequestPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC077_PNBREN011 extends AbstractNAHOTest {

	public TC077_PNBREN011() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN011.xls";
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
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		ConfirmBindRequestPage confirmBindRequestPage = new ConfirmBindRequestPage();
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		BindRequestSubmittedPage bindRequestSubmittedPage = new BindRequestSubmittedPage();
		String quoteNumber;
		String policyNumber;
		double calcNewPremiumValue;
		double calIcatFees;
		double calPremium;
		String windPremiumValue;
		String aopPremiumValue;
		String glPremiumValue;
		String premiumAmount;
		String inspectionFee;
		String policyFee;
		double surplusTax;
		DecimalFormat df = new DecimalFormat("0.00");
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
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

			// Go to home page and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");

			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy " + policyNumber + " successfully");

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

			// Getting Renewal Quote Number
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNumber);

			// Approving Referral
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.approveRenewalReferralUSM(renewalQuoteNumber);
			}

			Assertions.verify(accountOverviewPage.overridePremiumLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// click on override premium link
			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Override Premium link successfully");

			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
					false);
			if (testData.get("WindPremiumOverride") != null && !testData.get("WindPremiumOverride").equals("")) {
				Assertions.addInfo("Override Premium and Fees Page",
						"Wind Premium Original Value : " + overridePremiumAndFeesPage.originalWindPremium.getData());
				overridePremiumAndFeesPage.windPremiumOverride.scrollToElement();
				overridePremiumAndFeesPage.windPremiumOverride.setData(testData.get("WindPremiumOverride"));
				Assertions.passTest("Override Premium and Fees Page", "Wind Premium Latest Value : " + "$"
						+ overridePremiumAndFeesPage.windPremiumOverride.getData());
			}
			if (testData.get("AOPPremiumOverride") != null && !testData.get("AOPPremiumOverride").equals("")) {
				Assertions.addInfo("Override Premium and Fees Page",
						"AOP Premium Original Value : " + overridePremiumAndFeesPage.originalAopPremium.getData());
				overridePremiumAndFeesPage.aOPPremiumOverride.scrollToElement();
				overridePremiumAndFeesPage.aOPPremiumOverride.setData(testData.get("AOPPremiumOverride"));
				Assertions.passTest("Override Premium and Fees Page",
						"AOP Premium Latest Value :" + "$" + overridePremiumAndFeesPage.aOPPremiumOverride.getData());
			}
			if (testData.get("LiabilityPremiumOverride") != null
					&& !testData.get("LiabilityPremiumOverride").equals("")) {
				Assertions.addInfo("Override Premium and Fees Page",
						"GL Premium Original Value : " + overridePremiumAndFeesPage.originalGlPremium.getData());
				overridePremiumAndFeesPage.gLPremiumOverride.scrollToElement();
				overridePremiumAndFeesPage.gLPremiumOverride.setData(testData.get("LiabilityPremiumOverride"));
				Assertions.passTest("Override Premium and Fees Page",
						"GL Premium Latest Value : " + "$" + overridePremiumAndFeesPage.gLPremiumOverride.getData());
			}
			if (testData.get("InspectionFeeOverride") != null && !testData.get("InspectionFeeOverride").equals("")) {
				Assertions.addInfo("Override Premium and Fees Page", "Inspection Fee Original Value : "
						+ overridePremiumAndFeesPage.originalInspectionFee.getData());
				overridePremiumAndFeesPage.totalInspectionFee.scrollToElement();
				overridePremiumAndFeesPage.totalInspectionFee.setData(testData.get("InspectionFeeOverride"));
				Assertions.passTest("Override Premium and Fees Page", "Inspection Fee Latest Value : " + "$"
						+ overridePremiumAndFeesPage.totalInspectionFee.getData());
			}

			if (testData.get("PolicyFeeOverride") != null && !testData.get("PolicyFeeOverride").equals("")) {
				Assertions.addInfo("Override Premium and Fees Page",
						"Policy Fee Original Value : " + overridePremiumAndFeesPage.originalPolicyFee.getData());
				overridePremiumAndFeesPage.policyFee.scrollToElement();
				overridePremiumAndFeesPage.policyFee.setData(testData.get("PolicyFeeOverride"));
				Assertions.passTest("Override Premium and Fees Page",
						"Policy Fee Latest Value : " + "$" + overridePremiumAndFeesPage.policyFee.getData());
			}

			if (testData.get("FeeOverrideJustification") != null
					&& !testData.get("FeeOverrideJustification").equals("")) {
				overridePremiumAndFeesPage.feeOverrideJustification.scrollToElement();
				overridePremiumAndFeesPage.feeOverrideJustification.setData(testData.get("FeeOverrideJustification"));
			}

			// Calculate the new premium value
			windPremiumValue = testData.get("WindPremiumOverride");
			aopPremiumValue = testData.get("AOPPremiumOverride");
			glPremiumValue = testData.get("LiabilityPremiumOverride");
			inspectionFee = testData.get("InspectionFeeOverride");
			policyFee = testData.get("PolicyFeeOverride");

			calcNewPremiumValue = Double.parseDouble(windPremiumValue) + Double.parseDouble(aopPremiumValue)
					+ Double.parseDouble(glPremiumValue) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee);
			df.format(calcNewPremiumValue);
			Assertions.passTest("Override Premium and Fees Page",
					"The Calculated New Premium Fees : " + "$" + df.format(calcNewPremiumValue));

			// Asserting new Premium value
			String actualNewPremiumFee = overridePremiumAndFeesPage.newTotalPremiumFees.getData().replace(",", "");
			Assertions.verify(overridePremiumAndFeesPage.newTotalPremiumFees.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "The Actual New Premium Fees : " + actualNewPremiumFee, false,
					false);

			// Compare calculated and actual New Premium fee
			Assertions.verify(actualNewPremiumFee, "$" + df.format(calcNewPremiumValue),
					"Override Premium and Fees Page", "The Calculated and Actual New Premium Fees are equal", false,
					false);

			overridePremiumAndFeesPage.overrideFeesButton.scrollToElement();
			overridePremiumAndFeesPage.overrideFeesButton.click();
			Assertions.passTest("Override Premium and Fees Page", "Clicked on Override Premium/Fees Button");

			Assertions.verify(accountOverviewPage.editDwelling.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Asserting the new Values are updated on Premium section
			// Calculating the Premium
			calPremium = Double.parseDouble(windPremiumValue) + Double.parseDouble(aopPremiumValue)
					+ Double.parseDouble(glPremiumValue);

			// Printing Calculated Premium
			Assertions.passTest("Account Overview Page", "The Calculated Premium : " + "$" + df.format(calPremium));
			String actualPremium = accountOverviewPage.premiumValue.getData().replace(",", "");

			// Printing the Actual Premium
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "The Actual Premium : " + actualPremium, false, false);

			// Compare calculated and actual premium
			Assertions.verify(actualPremium, "$" + df.format(calPremium), "Account Overview Page",
					"The Actual and Calculated Premiums are equal", false, false);

			// calculating Icat fees
			calIcatFees = Double.parseDouble(inspectionFee) + Double.parseDouble(policyFee);

			// Printing the calculated Icat fees
			Assertions.passTest("Account Overview Page", "The Calculated ICAT Fees : " + "$" + df.format(calIcatFees));
			String actualIcatFees = accountOverviewPage.feesValue.getData().replace(",", "");

			// Printing the Actual ICat fees
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"The Actual ICAT Fees : " + actualIcatFees, false, false);

			// Compare calculated and actual Icat fees
			Assertions.verify(actualIcatFees, "$" + df.format(calIcatFees), "Account Overview Page",
					"The Actual and Calculated ICAT Fees are equal", false, false);

			// Click on edit dwelling
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Dwelling successfully");

			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);

			// Change the Year Built to Less than 1940 and construction type as Other
			testData = data.get(dataValue2);

			if (!testData.get("L1D1-DwellingConstType").equals("")) {
				dwellingPage.constructionTypeData.waitTillVisibilityOfElement(60);
				dwellingPage.constructionTypeData.scrollToElement();
				Assertions.addInfo("Dwelling Page",
						"Dwelling Construction Type original Value : " + dwellingPage.constructionTypeData.getData());
				dwellingPage.constructionTypeArrow.waitTillPresenceOfElement(60);
				dwellingPage.constructionTypeArrow.waitTillButtonIsClickable(60);
				dwellingPage.constructionTypeArrow.waitTillVisibilityOfElement(60);
				dwellingPage.constructionTypeArrow.scrollToElement();
				dwellingPage.constructionTypeArrow.click();
				dwellingPage.constructionTypeOption.formatDynamicPath(testData.get("L1D1-DwellingConstType"))
						.scrollToElement();
				dwellingPage.constructionTypeOption.formatDynamicPath(testData.get("L1D1-DwellingConstType")).click();

				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				Assertions.passTest("Dwelling Page",
						"Dwelling Construction Type Latest Value : " + dwellingPage.constructionTypeData.getData());
			}

			if (!testData.get("L1D1-DwellingYearBuilt").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Dwelling year built Original Value : " + dwellingPage.yearBuilt.getData());
				dwellingPage.yearBuilt.scrollToElement();
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				Assertions.passTest("Dwelling Page",
						"Dwelling year built Latest Value : " + dwellingPage.yearBuilt.getData());
			}
			Assertions.passTest("Dwelling Page", "Dwelling Details Modified successfully");

			dwellingPage.reviewDwelling();

			if (dwellingPage.createQuote.checkIfElementIsPresent()
					|| dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillPresenceOfElement(60);
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.waitTillButtonIsClickable(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}
			if (dwellingPage.override.checkIfElementIsPresent() && dwellingPage.override.checkIfElementIsDisplayed()) {
				dwellingPage.override.scrollToElement();
				dwellingPage.override.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			if (createQuotePage.getAQuote.checkIfElementIsPresent()
					&& createQuotePage.getAQuote.checkIfElementIsDisplayed()) {
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
			}
			Assertions.passTest("Create Quote Page", "Clicked on Get a Quote");

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {

				Assertions.verify(
						createQuotePage.warningMessages
								.formatDynamicPath("construction class").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Warning message "
								+ createQuotePage.warningMessages.formatDynamicPath("construction class").getData()
								+ " displayed is verified",
						false, false);

				Assertions.verify(
						createQuotePage.warningMessages.formatDynamicPath("roof age").checkIfElementIsDisplayed(), true,
						"Create Quote Page",
						"The Warning message " + createQuotePage.warningMessages.formatDynamicPath("roof age").getData()
								+ " displayed is verified",
						false, false);

				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Asserting Warning message appears for Year built less than 1950 and
			// Construction type as Other.
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				Assertions.verify(
						referQuotePage.referralMessages
								.formatDynamicPath("construction class").checkIfElementIsDisplayed(),
						true, "Refer Quote Page",
						"The Referral message "
								+ referQuotePage.referralMessages.formatDynamicPath("construction class").getData()
								+ " displayed is verified",
						false, false);

				Assertions.verify(
						referQuotePage.referralMessages.formatDynamicPath("roof age").checkIfElementIsDisplayed(), true,
						"Refer Quote Page",
						"The Referral message "
								+ referQuotePage.referralMessages.formatDynamicPath("roof age").getData()
								+ " displayed is verified",
						false, false);

				Assertions.verify(
						referQuotePage.referralMessages
								.formatDynamicPath("year built prior to 1950").checkIfElementIsDisplayed(),
						true, "Refer Quote Page",
						"The Referral message " + referQuotePage.referralMessages
								.formatDynamicPath("year built prior to 1950").getData() + " displayed is verified",
						false, false);

				// Enter Referral Contact Details
				testData = data.get(dataValue1);
				referQuotePage.contactName.setData(testData.get("ProducerName"));
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.waitTillButtonIsClickable(60);
				referQuotePage.referQuote.click();
				Assertions.passTest("Refer Quote Page", "Quote referred successfully");
				quoteNumber = referQuotePage.quoteNumberforReferral.getData();
				Assertions.passTest("Refer Quote Page", "The Quote Number is " + quoteNumber);

				// Clicking on home page button
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");
				Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
						"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");
				referralPage.close.waitTillPresenceOfElement(60);
				referralPage.close.waitTillVisibilityOfElement(60);
				referralPage.close.scrollToElement();
				referralPage.close.click();

				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home Page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched the Quote " + quoteNumber + " successfully");
			}

			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page",
					"Account Overview Page loaded successfully.The Quote Number : " + quoteNumber, false, false);
			String renewalQuoteNum = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNum);

			// click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button successfully");

			// Click on Release renewal to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			// clicking on request bind in Account overview page
			accountOverviewPage.clickOnRenewalRequestBind(testData, renewalQuoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			testData = data.get(dataValue1);
			requestBindPage.floodCoverageArrow.scrollToElement();
			requestBindPage.floodCoverageArrow.click();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy"))
					.scrollToElement();
			requestBindPage.floodCoverageOption.formatDynamicPath(testData.get("ApplicantHaveFloodPolicy")).click();
			Assertions.passTest("Request Bind Page", "Selected the Flood as No");

			requestBindPage.addContactInformation(testData);

			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
			}

			if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
				Assertions.passTest("Confirm Bind Request Page", "Confirm Bind Request page loaded successfully");
				confirmBindRequestPage.confirmBind();
				Assertions.passTest("Confirm Bind Request Page", "Clicked on Request Bind");
			}

			if (requestBindPage.overrideEffectiveDate.checkIfElementIsPresent()
					&& requestBindPage.overrideEffectiveDate.checkIfElementIsDisplayed()) {
				requestBindPage.overrideEffectiveDate.scrollToElement();
				requestBindPage.overrideEffectiveDate.select();
				requestBindPage.waitTime(2);
			}

			if (requestBindPage.submit.checkIfElementIsPresent()
					&& requestBindPage.submit.checkIfElementIsDisplayed()) {
				requestBindPage.scrollToBottomPage();
				requestBindPage.submit.scrollToElement();
				requestBindPage.submit.click();
			}

			if (confirmBindRequestPage.requestBind.checkIfElementIsPresent()) {
				confirmBindRequestPage.confirmBind();
			}

			if (bindRequestSubmittedPage.homePage.checkIfElementIsPresent()
					&& bindRequestSubmittedPage.homePage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Searching the Bind Referral
				homePage.searchQuote(renewalQuoteNum);
				Assertions.passTest("Home Page", "Quote for referral is searched successfully");

				accountOverviewPage.openReferralLink.checkIfElementIsDisplayed();
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();

				// Approve Referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Bind referral approved successfully");

				requestBindPage.approveRequestNAHO(testData);
			}

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
					false);

			// Go to home page and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");

			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy " + policyNumber + " successfully");

			// click on cancel policy link
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancel Policy link successfully");

			// Entering Number of NOC and Complete the transaction
			testData = data.get(dataValue2);
			Assertions.verify(cancelPolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Cancel Policy Page",
					"Cancel Policy Page loaded successfully", false, false);

			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();

			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());

			// Asserting the Policy Effective date
			Assertions.verify(cancelPolicyPage.policyEffectiveDate.checkIfElementIsDisplayed(), true,
					"Cancel Policy Page",
					"The Policy Effective Date for Renewal Policy is : "
							+ cancelPolicyPage.policyEffectiveDate.getData().substring(0, 10)
							+ " displayed is verified",
					false, false);

			// Asserting the cancellation effective date after selecting cancel reason
			Assertions.verify(cancelPolicyPage.cancellationEffectiveDate.checkIfElementIsDisplayed(), true,
					"Cancel Policy Page", "The Populated Cancellation Effective Date : "
							+ cancelPolicyPage.cancellationEffectiveDate.getData(),
					false, false);

			// Comparing policy effective date and Cancellation effective date
			Assertions.verify(cancelPolicyPage.policyEffectiveDate.getData().substring(0, 10),
					cancelPolicyPage.cancellationEffectiveDate.getData(), "Cancel Policy Page",
					"The Populated Cancellation effective date is same as Renewal Policy Effective date is verified",
					false, false);

			// Click on Next Button
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();

			// click on Complete transaction
			cancelPolicyPage.completeTransactionButton.scrollToElement();
			cancelPolicyPage.completeTransactionButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on Complete Transaction");

			if (cancelPolicyPage.closeButton.checkIfElementIsPresent()
					&& cancelPolicyPage.closeButton.checkIfElementIsDisplayed()) {
				cancelPolicyPage.closeButton.scrollToElement();
				cancelPolicyPage.closeButton.click();
			}

			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully", false, false);

			// Asserting the Policy Status
			Assertions.verify(policySummaryPage.policyStatus.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Policy Status : " + policySummaryPage.policyStatus.getData() + " displayed is verified", false,
					false);

			// Asserting Cancellation Endorsement link is available
			policySummaryPage.cancelEnd.waitTillPresenceOfElement(60);
			policySummaryPage.cancelEnd.waitTillVisibilityOfElement(60);
			Assertions.verify(policySummaryPage.cancelEnd.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"The Cancellation Endorsement link present is verified", false, false);

			// Click on Renewal Transaction history
			policySummaryPage.transHistReason.formatDynamicPath("2").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("2").click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal Transaction History");

			// Getting the premium,fees and sltf values from premium section in Policy
			// summary page
			Assertions.verify(policySummaryPage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Premium Value is : " + policySummaryPage.transactionPremium.getData(),
					false, false);
			Assertions.verify(policySummaryPage.inspectionFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Inspection Fee Value is : " + policySummaryPage.inspectionFee.getData(), false, false);
			Assertions.verify(policySummaryPage.policyFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Fee Value is : " + policySummaryPage.policyFee.getData(), false, false);

			// click on cancellation txn history
			policySummaryPage.cancelType.waitTillPresenceOfElement(60);
			policySummaryPage.cancelType.waitTillButtonIsClickable(60);
			policySummaryPage.cancelType.scrollToElement();
			policySummaryPage.cancelType.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Cancellation Transaction History");

			// Calculating sltf for Transaction
			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replace("-", "").replace("$", "");
			double d_inspectionFee = Double.parseDouble(inspectionFee);
			policyFee = policySummaryPage.policyFee.getData().replace("-", "").replace("$", "");
			double d_policyFee = Double.parseDouble(policyFee);
			String surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData()
					.replace("$", "").replace(",", "").replace("-", "");

			// double d_surplusContributionValue =
			// Double.parseDouble(surplusContributionValue);
			double icatFees = d_inspectionFee + d_policyFee;
			String s_icatFees = Double.toString(icatFees);

			// Getting the premium,fees and sltf values from premium section in Policy
			// summary page
			Assertions.verify(policySummaryPage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Premium Value is : " + policySummaryPage.transactionPremium.getData(),
					false, false);
			Assertions.verify(policySummaryPage.inspectionFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Inspection Fee Value is : " + policySummaryPage.inspectionFee.getData(), false, false);
			Assertions.verify(policySummaryPage.policyFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Fee Value is : " + policySummaryPage.policyFee.getData(), false, false);

			testData = data.get(dataValue1);
			double d_sltfPercentage = Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// calculating sltf by adding Premium+Fees and multiplying by sltf percentage
			// 0.0485
			surplusTax = (Double.parseDouble(premiumAmount.replace("-", ""))
					+ Double.parseDouble(surplusContributionValue) + Double.parseDouble(s_icatFees.replace("-", "")))
					* d_sltfPercentage;
			Assertions.passTest("Policy Summary Page",
					"The Calculated Surplus Lines Taxes and Fees : " + "-" + "$" + df.format(surplusTax));

			String actualsltfValue = policySummaryPage.TaxesAndStateFees.formatDynamicPath("1").getData().replace(",",
					"");
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath("1").checkIfElementIsDisplayed(),
					true, "Policy Summary Page", "The Actual Surplus Lines Taxes and Fees : " + actualsltfValue, false,
					false);

			Assertions.verify(actualsltfValue, "-" + "$" + df.format(surplusTax), "Policy Summary Page",
					"The Actual and Calculated Surplus Lines Taxes and Fees are equal", false, false);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC077 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC077 ", "Executed Successfully");
			}
		}
	}

}
