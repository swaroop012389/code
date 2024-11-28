package com.icat.epicenter.test.naho.regression.ISPNB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
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

public class TC059_PNBSLTFREG003 extends AbstractNAHOTest {

	public TC059_PNBSLTFREG003() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBSLTFREG003.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		LoginPage loginPage = new LoginPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ReferralPage referralPage = new ReferralPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage changeNamedInsuredPage = new ChangeNamedInsuredPage();
		ViewPolicySnapShot viewPolicySnapshot = new ViewPolicySnapShot();

		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

		String quoteNumber;
		String policyNumber;
		String surplusContributionValue;
		String premiumAmount;
		String inspectionFee;
		String policyFee;
		double surplusTax;
		double stampingFee;
		String fees;
		BigDecimal surplustaxes;
		BigDecimal stampingtaxes;
		BigDecimal surplustaxesandFees;
		int dataValue1 = 0;
		int dataValue2 = 1;
		Map<String, String> testData = data.get(dataValue1);
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// creating New account
			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
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

			// Verifying and Entering details in refer quote page
			if (referQuotePage.contactName.checkIfElementIsPresent()
					&& referQuotePage.contactName.checkIfElementIsDisplayed()) {
				Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
						"Refer Quote page loaded successfully", false, false);

				referQuotePage.contactName.scrollToElement();
				referQuotePage.contactName.setData(testData.get("ProducerName"));

				referQuotePage.contactEmail.scrollToElement();
				referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));

				referQuotePage.referQuote.scrollToElement();
				referQuotePage.referQuote.click();

				// verifying referral message
				Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true,
						"Referral Page", "Quote " + referQuotePage.quoteNumberforReferral.getData()
								+ " referring to USM " + " is verified",
						false, false);

				quoteNumber = referQuotePage.quoteNumberforReferral.getData();

				// sign out as producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

				loginPage.refreshPage();

				// Login to USM account
				Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
						"Login page loaded successfully", false, false);
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number
				// link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchReferral(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote successfullly");

				// approving referral
				Assertions.verify(
						referralPage.pickUp.checkIfElementIsDisplayed()
								|| referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(),
						true, "Referral Page", "Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// verifying referral complete message
				Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true, "Referral Page",
						referralPage.referralCompleteMsg.getData() + " message is verified successfully", false, false);
				referralPage.close.scrollToElement();
				referralPage.close.click();

				// sign out as USM
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();

				Assertions.passTest("Login Page", "Logged Out as USM Successfully");

				loginPage.refreshPage();

				// login as producer
				Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
						"Login page loaded successfully", false, false);
				loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));
				Assertions.passTest("Login Page",
						"Logged in as Producer " + setUpData.get("NahoProducer") + " Successfully");

				// Handling the pop up
				homePage.enterPersonalLoginDetails();

				// search for the quote number
				homePage.searchQuoteByProducer(quoteNumber);
				Assertions.passTest("Producer Home Page", "Quote Number : " + quoteNumber + " searched successfully");
			}

			// getting quote number 1
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Quote Number : " + quoteNumber);

			// Getting the premium,fees and sltf values from premium section in Account
			// Overview page
			Assertions.addInfo("Scenario 01", "Verifying SLTF value on account 0verview page");
			Assertions.verify(accountOverviewPage.premiumValue.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Premium Value is : " + accountOverviewPage.premiumValue.getData(), false,
					false);
			Assertions.verify(accountOverviewPage.feesValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Fees Value is : " + accountOverviewPage.feesValue.getData(), false, false);
			Assertions.verify(accountOverviewPage.sltfValue.checkIfElementIsDisplayed(), true, "Account Overview Page",
					"Surplus Lines Taxes and Fees label displayed is verified", false, false);

			// getting premium amount
			premiumAmount = accountOverviewPage.premiumValue.getData().replaceAll("[^\\d-.]", "");

			// getting fees value
			fees = accountOverviewPage.feesValue.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = accountOverviewPage.surplusContibutionValue.getData().replaceAll("[^\\d-.]", "");

			// calculating sltf percentage by adding Premium+Fees and multiplying by sltf
			// percentage 0.04
			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees)
					+ Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating stamping percentage by adding Premium+Fees and multiplying by
			// stamping percentage 0.0025
			stampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(fees)
					+ Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("StampingFeePercentage"));

			// Rounding stamping decimal value to 2 digits
			stampingtaxes = BigDecimal.valueOf(stampingFee);
			stampingtaxes = stampingtaxes.setScale(2, RoundingMode.HALF_UP);

			// Rounding Surplus Lines,Taxes and Fees decimal value to 2 digits
			surplustaxesandFees = surplustaxes.add(stampingtaxes);
			surplustaxesandFees = surplustaxesandFees.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(accountOverviewPage.sltfValue.getData(), format.format(surplustaxesandFees),
					"Account Overview Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(surplustaxesandFees), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(accountOverviewPage.sltfValue.getData(), format.format(surplustaxesandFees),
					"Account Overview Page",
					"Actual Surplus Lines Taxes and Fees : " + accountOverviewPage.sltfValue.getData(), false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData(),
					format.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
							+ (Double.parseDouble(surplustaxesandFees + "") + (Double.parseDouble(fees)))),
					"Account Overview Page",
					"Calculated Premium, Taxes and Fees : " + format
							.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
									+ (Double.parseDouble(surplustaxesandFees + "") + (Double.parseDouble(fees)))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(accountOverviewPage.totalPremiumValue.getData(),
					format.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
							+ (Double.parseDouble(surplustaxesandFees + "") + (Double.parseDouble(fees)))),
					"Account Overview Page",
					"Actual Premium, Taxes and Fees : " + accountOverviewPage.totalPremiumValue.getData(), false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// click on veiw/Print full Quote
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on view/Print Full Quote Link");

			// Comparing actual and expected SLTF value and printing actual and expected
			// value
			Assertions.addInfo("Scenario 02", "Verifying SLTF value on view/print full quote page");
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData(),
					format.format(surplustaxes).replace(",", ""), "View/Print Full Quote Page",
					"Calculated Surplus Fees : " + format.format(surplustaxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData(),
					format.format(surplustaxes).replace(",", ""), "View/Print Full Quote Page",
					"Actual Surplus Fees : " + viewPolicySnapshot.surplusLinesTaxesValue.getData(), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData(),
					format.format(surplustaxes).replace(",", ""), "View/Print Full Quote Page",
					"Surplus Fees calculated as per Surplus Lines Taxes Percentage 4.85% for TX is verified", false,
					false);

			// Comparing actual and expected Stamping fee value and printing actual and
			// expected value
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View/Print Full Quote Page",
					"Calculated Stamping Fees : " + format.format(stampingtaxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View/Print Full Quote Page",
					"Actual Stamping Fees : " + viewPolicySnapshot.stampingFeeValue.getData(), false, false);
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View/Print Full Quote Page",
					"Stamping Fees calculated as per Stamping Fees Percentage 0.75% for TX is verified", false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// click on go back
			viewOrPrintFullQuotePage.scrollToTopPage();
			viewOrPrintFullQuotePage.waitTime(3);
			viewOrPrintFullQuotePage.backButton.click();

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

			if (bindRequestPage.pageName.getData().equalsIgnoreCase("Bind Request Submitted")) {
				// clicking on home button
				bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Home button");

				// sign out as producer
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

				loginPage.refreshPage();

				// Login to USM account
				Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
						"Login page loaded successfully", false, false);
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				// searching the quote number in grid and clicking on the quote number
				// link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

				// approving referral
				accountOverviewPage.uploadPreBindApproveAsUSM();
				referralPage.clickOnApprove();

				// click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			} else {
				// sign out as producer
				policyNumber = policySummaryPage.getPolicynumber();
				homePage.signOutButton.scrollToElement();
				homePage.signOutButton.click();
				Assertions.passTest("Login Page", "Logged Out as Producer Successfully");

				loginPage.refreshPage();

				// Login to USM account
				Assertions.verify(loginPage.signInButton.checkIfElementIsDisplayed(), true, "Login Page",
						"Login page loaded successfully", false, false);
				loginPage.enterLoginDetails(setUpData.get("NahoUsername"), setUpData.get("Password"));
				Assertions.passTest("Home Page", "Logged in as USM Successfully");

				homePage.searchPolicy(policyNumber);
			}

			// getting the policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// click on Endorse Policy Link
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();

			// Setting Endorsement effective date
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();

			// click on Named Insured/Mailing Address/Contact Information Link
			endorsePolicyPage.changeNamedInsuredLink.click();

			// entering details in change named insured page
			testData = data.get(dataValue2);
			changeNamedInsuredPage.enterInsuredAddressDetailPB(testData);
			Assertions.passTest("Change Named Insured Page", "Details modified successfully");

			// click on submit button
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			if (endorsePolicyPage.pageName.getData().contains("Overrides Required")) {
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// click on complete button
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// click on close button
			endorsePolicyPage.scrollToBottomPage();
			endorsePolicyPage.waitTime(3);
			endorsePolicyPage.closeButton.waitTillButtonIsClickable(60);
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// click on NB link in Transaction History section
			testData = data.get(dataValue1);
			policySummaryPage.transHistReason.formatDynamicPath("2").waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath("2").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("2").click();

			Assertions.addInfo("Scenari 03", " NB Policy SLTF Verification");
			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			policyFee = policySummaryPage.policyFee.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData().replaceAll("[^\\d-.]",
					"");

			// Getting the premium,fees and sltf values from premium section in Policy
			// summary page
			Assertions.verify(policySummaryPage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Premium Value is : " + policySummaryPage.transactionPremium.getData(),
					false, false);
			Assertions.verify(policySummaryPage.inspectionFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Inspection Fee Value is : " + policySummaryPage.inspectionFee.getData(), false, false);
			Assertions.verify(policySummaryPage.policyFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Fee Value is : " + policySummaryPage.policyFee.getData(), false, false);
			Assertions.verify(policySummaryPage.transactionSurplusContribution.checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"Surplus Contribution Value is : " + policySummaryPage.transactionSurplusContribution.getData(),
					false, false);

			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating stamping percentage by adding Premium+Fees and multiplying by
			// stamping percentage 0.0025
			stampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("StampingFeePercentage"));

			// Rounding stamping decimal value to 2 digits
			stampingtaxes = BigDecimal.valueOf(stampingFee);
			stampingtaxes = stampingtaxes.setScale(2, RoundingMode.HALF_UP);

			// Rounding Surplus Lines,Taxes and Fees decimal value to 2 digits
			surplustaxesandFees = surplustaxes.add(stampingtaxes);
			surplustaxesandFees = surplustaxesandFees.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(),
					format.format(surplustaxesandFees), "Policy Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(surplustaxesandFees), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(),
					format.format(surplustaxesandFees), "Policy Summary Page", "Actual Surplus Lines Taxes and Fees : "
							+ policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(policySummaryPage.policyTotalPremium.getData(),
					format.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
							+ (Double.parseDouble(surplustaxesandFees + "")
									+ (Double.parseDouble(inspectionFee)) + (Double.parseDouble(policyFee)))),
					"Policy Summary Page",
					"Calculated Premium, Taxes and Fees : " + format
							.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
									+ (Double.parseDouble(surplustaxesandFees + "")
											+ (Double.parseDouble(inspectionFee)) + (Double.parseDouble(policyFee)))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(policySummaryPage.policyTotalPremium.getData(),
					format.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
							+ (Double.parseDouble(surplustaxesandFees + "") + (Double.parseDouble(inspectionFee))
									+ (Double.parseDouble(policyFee)))),
					"Policy Summary Page",
					"Actual Premium, Taxes and Fees : " + policySummaryPage.policyTotalPremium.getData(), false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on Endorsement link in Transaction History section
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillPresenceOfElement(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).waitTillButtonIsClickable(60);
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).scrollToElement();
			policySummaryPage.endorsementTransaction.formatDynamicPath(1).click();
			policySummaryPage.waitTime(1);
			Assertions.passTest("Policy Summary Page", "Clicked on Endorsement History");

			Assertions.addInfo("Scenario 04 ", "Endorsement SLTF Verification");
			policySummaryPage.transactionPremium.waitTillVisibilityOfElement(60);
			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			policyFee = policySummaryPage.policyFee.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData().replaceAll("[^\\d-.]",
					"");

			// Getting the premium,fees and sltf values from premium section in Policy
			// summary page
			Assertions.verify(policySummaryPage.transactionPremium.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Premium Value is : " + policySummaryPage.transactionPremium.getData(),
					false, false);
			Assertions.verify(policySummaryPage.inspectionFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Inspection Fee Value is : " + policySummaryPage.inspectionFee.getData(), false, false);
			Assertions.verify(policySummaryPage.policyFee.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Fee Value is : " + policySummaryPage.policyFee.getData(), false, false);
			Assertions.verify(policySummaryPage.transactionSurplusContribution.checkIfElementIsDisplayed(), true,
					"Policy Summary Page",
					"Surplus Contribution Value is : " + policySummaryPage.transactionSurplusContribution.getData(),
					false, false);

			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating stamping percentage by adding Premium+Fees and multiplying by
			// stamping percentage 0.0025
			stampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("StampingFeePercentage"));

			// Rounding stamping decimal value to 2 digits
			stampingtaxes = BigDecimal.valueOf(stampingFee);
			stampingtaxes = stampingtaxes.setScale(2, RoundingMode.HALF_UP);

			// Rounding Surplus Lines,Taxes and Fees decimal value to 2 digits
			surplustaxesandFees = BigDecimal.valueOf(surplusTax + stampingFee);
			surplustaxesandFees = surplustaxesandFees.setScale(2, RoundingMode.HALF_UP);

			// Comparing actual and expected SLTF value and printing calculated value
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(),
					format.format(surplustaxesandFees), "Policy Summary Page",
					"Calculated Surplus Lines Taxes and Fees : " + format.format(surplustaxesandFees), false, false);

			// Comparing actual and expected SLTF value and printing actual value
			Assertions.verify(policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(),
					format.format(surplustaxesandFees), "Policy Summary Page", "Actual Surplus Lines Taxes and Fees : "
							+ policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData(),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// calculated value
			Assertions.verify(policySummaryPage.policyTotalPremium.getData() + ".00",
					format.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
							+ (Double.parseDouble(surplustaxesandFees + "")
									+ (Double.parseDouble(inspectionFee)) + (Double.parseDouble(policyFee)))),
					"Policy Summary Page",
					"Calculated Premium, Taxes and Fees : " + format
							.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
									+ (Double.parseDouble(surplustaxesandFees + "")
											+ (Double.parseDouble(inspectionFee)) + (Double.parseDouble(policyFee)))),
					false, false);

			// Comparing actual and expected total premium and fees value and printing
			// actual value
			Assertions.verify(policySummaryPage.policyTotalPremium.getData() + ".00",
					format.format((Double.parseDouble(premiumAmount) + Double.parseDouble(surplusContributionValue))
							+ (Double.parseDouble(surplustaxesandFees + "") + (Double.parseDouble(inspectionFee))
									+ (Double.parseDouble(policyFee)))),
					"Policy Summary Page",
					"Actual Premium, Taxes and Fees : " + policySummaryPage.policyTotalPremium.getData(), false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			policySummaryPage.transHistReason.formatDynamicPath("2").waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath("2").scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath("2").click();
			Assertions.passTest("Policy Summary Page", "Clicked on transactio 01");

			Assertions.addInfo("Scenario 05", " View Policy Snapshot Page SLTF Verification");
			premiumAmount = policySummaryPage.transactionPremium.getData().replaceAll("[^\\d-.]", "");
			inspectionFee = policySummaryPage.inspectionFee.getData().replaceAll("[^\\d-.]", "");
			policyFee = policySummaryPage.policyFee.getData().replaceAll("[^\\d-.]", "");
			surplusContributionValue = policySummaryPage.transactionSurplusContribution.getData().replaceAll("[^\\d-.]",
					"");

			surplusTax = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("SurplusLinesTaxesPercentage"));

			// Rounding sltf decimal value to 2 digits
			surplustaxes = BigDecimal.valueOf(surplusTax);
			surplustaxes = surplustaxes.setScale(2, RoundingMode.HALF_UP);

			// calculating stamping percentage by adding Premium+Fees and multiplying by
			// stamping percentage 0.0025
			stampingFee = (Double.parseDouble(premiumAmount) + Double.parseDouble(inspectionFee)
					+ Double.parseDouble(policyFee) + Double.parseDouble(surplusContributionValue))
					* Double.parseDouble(testData.get("StampingFeePercentage"));

			// Rounding stamping decimal value to 2 digits
			stampingtaxes = BigDecimal.valueOf(stampingFee);
			stampingtaxes = stampingtaxes.setScale(2, RoundingMode.HALF_UP);

			// Rounding Surplus Lines,Taxes and Fees decimal value to 2 digits
			surplustaxesandFees = surplustaxes.add(stampingtaxes);
			surplustaxesandFees = surplustaxesandFees.setScale(2, RoundingMode.HALF_UP);

			// clicking on view policy snapshot link
			policySummaryPage.viewPolicySnapshot.click();

			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.checkIfElementIsDisplayed(), true,
					"View Policy Snapshot Page", "View Policy Snapshot Page loaded successfully", false, false);

			// Comparing actual and expected SLTF value and printing actual and expected
			// value
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData(),
					format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Calculated Surplus Fees : " + format.format(surplustaxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData(),
					format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Actual Surplus Fees : " + viewPolicySnapshot.surplusLinesTaxesValue.getData(), false, false);
			Assertions.verify(viewPolicySnapshot.surplusLinesTaxesValue.getData(),
					format.format(surplustaxes).replace(",", ""), "View Policy Snapshot Page",
					"Surplus Fees calculated as per Surplus Lines Taxes Percentage 4.85% for TX is verified", false,
					false);

			// Comparing actual and expected Stamping fee value and printing actual and
			// expected value
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View Policy Snapshot Page",
					"Calculated Stamping Fees : " + format.format(stampingtaxes).replace(",", ""), false, false);
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View Policy Snapshot Page",
					"Actual Stamping Fees : " + viewPolicySnapshot.stampingFeeValue.getData(), false, false);
			Assertions.verify(viewPolicySnapshot.stampingFeeValue.getData(),
					format.format(stampingtaxes).replace(",", ""), "View Policy Snapshot Page",
					"Stamping Fees calculated as per Stamping Fees Percentage 0.75% for TX is verified", false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC059 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC059 ", "Executed Successfully");
			}
		}
	}
}