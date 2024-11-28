package com.icat.epicenter.test.naho.regression.ISPNB;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.CancelPolicyPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReinstatePolicyPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewDocumentsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC035_PNBCR001 extends AbstractNAHOTest {

	public TC035_PNBCR001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBCR001.xls";
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
		CancelPolicyPage cancelPolicyPage = new CancelPolicyPage();
		ReinstatePolicyPage reinsatePolicyPage = new ReinstatePolicyPage();
		ViewDocumentsPage viewDocumentsPage = new ViewDocumentsPage();
		LoginPage loginPage = new LoginPage();
		ViewOrPrintFullQuotePage viewPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
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

			// Following part is added for IO-18709
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.verify(
					viewPrintFullQuotePage.viewPrintFullQuoteDetails.formatDynamicPath("hiho1@test.com")
							.checkIfElementIsPresent()
							&& viewPrintFullQuotePage.viewPrintFullQuoteDetails.formatDynamicPath("hiho1@test.com")
									.checkIfElementIsDisplayed(),
					false, "View/Print Full Quote Page", "Agent Email Address is not displayed in quote document",
					false, false);
			viewPrintFullQuotePage.scrollToTopPage();
			viewPrintFullQuotePage.waitTime(2); // Need wait to click on back button or fails to reach the element
			viewPrintFullQuotePage.backButton.click();

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
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// Asserting policy number
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Cancelling Policy
			policySummaryPage.cancelPolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();

			// Assert all cancellation reasons
			cancelPolicyPage.cancelReasonArrow.waitTillVisibilityOfElement(60);
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption
							.formatDynamicPath("Insured's Request").getData().contains("Insured's Request"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption.formatDynamicPath("Insured's Request").getData()
							+ " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption.formatDynamicPath("Insured's Request - Duplicate coverage")
							.getData().contains("Insured's Request - Duplicate coverage"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption
									.formatDynamicPath("Insured's Request - Duplicate coverage").getData()
							+ " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption
							.formatDynamicPath("Ineligible risk").getData().contains("Ineligible risk"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption.formatDynamicPath("Ineligible risk").getData()
							+ " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption
							.formatDynamicPath("Failure to comply with underwriting requirements").getData()
							.contains("Failure to comply with underwriting requirements"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption
									.formatDynamicPath("Failure to comply with underwriting requirements").getData()
							+ " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption.formatDynamicPath("Material misrepresentation").getData()
							.contains("Material misrepresentation"),
					true, "Cancellation Page", "Cancellation Reason " + cancelPolicyPage.cancelReasonOption
							.formatDynamicPath("Material misrepresentation").getData() + " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption.formatDynamicPath("ICAT Request - Non payment of premium")
							.getData().contains("ICAT Request - Non payment of premium"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption
									.formatDynamicPath("ICAT Request - Non payment of premium").getData()
							+ " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption
							.formatDynamicPath("Cancel/Rewrite").getData().contains("Cancel/Rewrite"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption.formatDynamicPath("Cancel/Rewrite").getData()
							+ " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption
							.formatDynamicPath("Material change in risk").getData().contains("Material change in risk"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption.formatDynamicPath("Material change in risk").getData()
							+ " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption
							.formatDynamicPath("Violation of law which materially increases risk").getData()
							.contains("Violation of law which materially increases risk"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption
									.formatDynamicPath("Violation of law which materially increases risk").getData()
							+ " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption.formatDynamicPath("Failure to provide requested information")
							.getData().contains("Failure to provide requested information"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption
									.formatDynamicPath("Failure to provide requested information").getData()
							+ " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption.formatDynamicPath("Age, condition, maintenance").getData()
							.contains("Age, condition, maintenance"),
					true, "Cancellation Page", "Cancellation Reason " + cancelPolicyPage.cancelReasonOption
							.formatDynamicPath("Age, condition, maintenance").getData() + " is present",
					false, false);
			Assertions.verify(
					cancelPolicyPage.cancelReasonOption.formatDynamicPath("Dwelling sold or transfer of ownership")
							.getData().contains("Dwelling sold or transfer of ownership"),
					true, "Cancellation Page",
					"Cancellation Reason "
							+ cancelPolicyPage.cancelReasonOption
									.formatDynamicPath("Dwelling sold or transfer of ownership").getData()
							+ " is present",
					false, false);

			cancelPolicyPage.refreshPage();
			cancelPolicyPage.enterCancellationDetails(testData);

			// Assert cancellation transaction under transaction history

			Assertions.verify(policySummaryPage.policyStatus.getData().contains("Cancelled"), true,
					"Policy Summary Page", "Policy status is changed to " + policySummaryPage.policyStatus.getData(),
					false, false);
			policySummaryPage.transactionType.formatDynamicPath("CANCEL").waitTillPresenceOfElement(60);
			Assertions.verify(policySummaryPage.transactionType.formatDynamicPath("CANCEL").checkIfElementIsDisplayed(),
					true, "Policy Summary Page", "Cancellation transaction is displayed in transaction history", false,
					false);
			Assertions.verify(
					policySummaryPage.decLink.checkIfElementIsPresent()
							&& policySummaryPage.decLink.checkIfElementIsDisplayed(),
					true, "Policy Summary Page", "DEC link is displayed", false, false);

			// Assert cancellation documents under view documents
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			viewDocumentsPage.waitTime(10);// to load cancellation documents waittime is given
			viewDocumentsPage.refreshPage();
			Assertions.verify(viewDocumentsPage.documents.formatDynamicPath("CAN ENDT").checkIfElementIsDisplayed(),
					true, "View Documents Page",
					"Cancellation endorsement document is generated under view documents section", false, false);
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();

			// Reinstate Policy
			Assertions.verify(policySummaryPage.reinstatePolicy.checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Policy is cancelled and Reinstate link is displayed", false, false);
			policySummaryPage.reinstatePolicy.scrollToElement();
			policySummaryPage.reinstatePolicy.click();
			reinsatePolicyPage.enterReinstatePolicyDetails(testData);

			// click on close
			if (reinsatePolicyPage.closeButton.checkIfElementIsPresent()
					&& reinsatePolicyPage.closeButton.checkIfElementIsDisplayed()) {
				reinsatePolicyPage.closeButton.scrollToElement();
				reinsatePolicyPage.closeButton.click();
			}

			// Assert reinstatement status under transaction history
			Assertions.verify(
					policySummaryPage.transactionType.formatDynamicPath("RE-INST").checkIfElementIsDisplayed(), true,
					"Policy Summary Page", "Reinstate transaction is displayed in transaction history", false, false);

			// Assert Reinstate documents under view documents
			policySummaryPage.viewDocuments.scrollToElement();
			policySummaryPage.viewDocuments.click();
			viewDocumentsPage.refreshPage();
			viewDocumentsPage.waitTime(10);// To load Reinstate documents waittime is given
			viewDocumentsPage.refreshPage();
			Assertions.verify(viewDocumentsPage.documents.formatDynamicPath("REIN").checkIfElementIsDisplayed(), true,
					"View Documents Page", "Reinstate documents is generated under view documents section", false,
					false);
			viewDocumentsPage.backBtn.scrollToElement();
			viewDocumentsPage.backBtn.click();

			// Following part is added for IO-18718
			testData = data.get(data_Value2);
			policySummaryPage.cancelPolicy.waitTillVisibilityOfElement(60);
			policySummaryPage.cancelPolicy.scrollToElement();
			policySummaryPage.cancelPolicy.click();
			cancelPolicyPage.cancelReasonArrow.scrollToElement();
			cancelPolicyPage.cancelReasonArrow.click();
			cancelPolicyPage.waitTime(2); // to select cancellation reason option as sometimes it fails without
											// selecting
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).scrollToElement();
			cancelPolicyPage.cancelReasonOption.formatDynamicPath(testData.get("CancellationReason")).click();
			Assertions.passTest("Cancel Policy Page",
					"Cancellation Reason : " + cancelPolicyPage.cancelReasonData.getData());
			cancelPolicyPage.cancellationEffectiveDate.scrollToElement();
			cancelPolicyPage.cancellationEffectiveDate.setData(testData.get("CancellationEffectiveDate"));
			cancelPolicyPage.cancellationEffectiveDate.tab();
			cancelPolicyPage.legalNoticeWording.scrollToElement();
			cancelPolicyPage.legalNoticeWording.setData(testData.get("Cancellation_LegalNoticeWording"));
			cancelPolicyPage.nextButton.scrollToElement();
			cancelPolicyPage.nextButton.click();
			Assertions.passTest("Cancel Policy Page", "Clicked on next button successfully");

			String sltfPercentage = testData.get("SurplusLinesTaxesPercentage");
			double d_sltfPercentage = Double.parseDouble(sltfPercentage);

			String newPremiumProMinEarned = cancelPolicyPage.newPremium.getData().replace("-", "").replace(",", "")
					.replace("$", "");
			String newInspectionProMinEarned = cancelPolicyPage.newInspectionFee.getData().replace("-", "")
					.replace(",", "").replace("$", "");
			String newPolicyProMinEarned = cancelPolicyPage.newPolicyFee.getData().replace("-", "").replace(",", "")
					.replace("$", "");
			String newSLTFProMinEarned = cancelPolicyPage.newSLTF.getData().replace("-", "").replace(",", "")
					.replace("$", "");
			String newSurplusContributionValueminEarned = cancelPolicyPage.surplusContributionVal.formatDynamicPath(2)
					.getData().replace("$", "").replace(",", "").replace("%", "").replace("%", "");
			double d_newPremiumProMinEarned = Double.parseDouble(newPremiumProMinEarned);
			double d_newInspectionProMinEarned = Double.parseDouble(newInspectionProMinEarned);
			double d_newPolicyProMinEarned = Double.parseDouble(newPolicyProMinEarned);
			double d_newSurplusContributionValueminEarned = Double.parseDouble(newSurplusContributionValueminEarned);
			double d_CalculatedEarnedSLTF = (d_newPremiumProMinEarned + d_newInspectionProMinEarned
					+ d_newPolicyProMinEarned + d_newSurplusContributionValueminEarned) * d_sltfPercentage;
			double d_newSLTFProMinEarned = Double.parseDouble(newSLTFProMinEarned);

			if (Precision.round(
					Math.abs(Precision.round(d_newSLTFProMinEarned, 2) - Precision.round(d_CalculatedEarnedSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page",
						"Earned SLTF calculated as per 0.05% for Pro Rata Min Earned");
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual Earned SLTF and calculated Earned SLTF is more than 0.05");
			}
			String returnedPremiumProMinEarned = cancelPolicyPage.returnedPremium.getData().replace("-", "")
					.replace(",", "").replace("$", "");
			String returnedInspectionProMinEarned = cancelPolicyPage.returnedInspectionFee.getData().replace("-", "")
					.replace(",", "").replace("$", "");
			String returnedPolicyProMinEarned = cancelPolicyPage.returnedPolicyFee.getData().replace("-", "")
					.replace(",", "").replace("$", "");
			String returnedSLTFProMinEarned = cancelPolicyPage.returnedSLTF.getData().replace("-", "").replace(",", "")
					.replace("$", "");
			String returnedSurplusContributionProMinEarned = cancelPolicyPage.surplusContributionVal
					.formatDynamicPath(3).getData().replace("-", "").replace(",", "").replace("$", "").replace("%", "");
			double d_returnedPremiumProMinEarned = Double.parseDouble(returnedPremiumProMinEarned);
			double d_returnedInspectionProMinEarned = Double.parseDouble(returnedInspectionProMinEarned);
			double d_returnedPolicyProMinEarned = Double.parseDouble(returnedPolicyProMinEarned);
			double d_returnedSurplusContributionProMinEarned = Double
					.parseDouble(returnedSurplusContributionProMinEarned);
			double d_CalculatedReturnedSLTF = (d_returnedPremiumProMinEarned + d_returnedInspectionProMinEarned
					+ d_returnedPolicyProMinEarned + d_returnedSurplusContributionProMinEarned) * d_sltfPercentage;
			if (Precision.round(Math.abs(Precision.round(Double.parseDouble(returnedSLTFProMinEarned), 2)
					- Precision.round(d_CalculatedReturnedSLTF, 2)), 2) < 0.05) {
				Assertions.passTest("Cancel Policy Page",
						"Retured SLTF calculated as per 0.05% for Pro Rata Min Earned");
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual Returned SLTF and calculated Returned SLTF is more than 0.05");
			}

			// Selecting proRata radio button
			cancelPolicyPage.proRataRadioBtn.scrollToElement();
			cancelPolicyPage.proRataRadioBtn.click();
			Assertions.passTest("Cancel Policy Page", "Selected Pro Rata radio button successfully");
			cancelPolicyPage.waitTime(3); // page is refreshing after selcting checkbox. Need waittime to avoid stale
											// element as values taking time to load
			String newPremiumProRata = cancelPolicyPage.newPremium.getData().replace("-", "").replace(",", "")
					.replace("$", "");
			String newInspectionProRata = cancelPolicyPage.newInspectionFee.getData().replace("-", "").replace(",", "")
					.replace("$", "");
			String newPolicyProRata = cancelPolicyPage.newPolicyFee.getData().replace("-", "").replace(",", "")
					.replace("$", "");
			String newSLTFProRata = cancelPolicyPage.newSLTF.getData().replace("-", "").replace(",", "").replace("$",
					"");
			String newSurplusContributionValueProRata = cancelPolicyPage.surplusContributionVal.formatDynamicPath(2)
					.getData().replace("-", "").replace(",", "").replace("$", "").replace("%", "");
			double d_newSLTFProRata = Double.parseDouble(newSLTFProRata);
			double d_newPremiumProRata = Double.parseDouble(newPremiumProRata);
			double d_newInspectionProRata = Double.parseDouble(newInspectionProRata);
			double d_newSurplusContributionValueProRata = Double.parseDouble(newSurplusContributionValueProRata);
			double d_newPolicyProRata = Double.parseDouble(newPolicyProRata);
			double d_CalculatedEarnedSLTFProRata = (d_newPremiumProRata + d_newInspectionProRata + d_newPolicyProRata
					+ d_newSurplusContributionValueProRata) * d_sltfPercentage;

			System.out.println("RproRata SLTF " + d_newSLTFProRata);
			if (Precision.round(
					Math.abs(Precision.round(d_newSLTFProRata, 2) - Precision.round(d_CalculatedEarnedSLTFProRata, 2)),
					2) < 0.05) {
				Assertions.passTest("Cancel Policy Page", "Earned SLTF calculated as per 0.05% for Pro Rata");
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual Earned SLTF and calculated Earned SLTF is more than 0.05");
			}

			String returnedPremiumProRata = cancelPolicyPage.returnedPremium.getData().replace("-", "").replace(",", "")
					.replace("$", "");
			String returnedInspectionProRata = cancelPolicyPage.returnedInspectionFee.getData().replace("-", "")
					.replace(",", "").replace("$", "");
			String returnedPolicyProRata = cancelPolicyPage.returnedPolicyFee.getData().replace("-", "")
					.replace(",", "").replace("$", "");
			String returnedSLTFProRata = cancelPolicyPage.returnedSLTF.getData().replace("-", "").replace(",", "")
					.replace("$", "");
			String returnedSurplusContributionProRata = cancelPolicyPage.surplusContributionVal.formatDynamicPath(3)
					.getData().replace("$", "").replace("-", "").replace(",", "").replace("%", "");
			double d_returnedPremiumProRata = Double.parseDouble(returnedPremiumProRata);
			double d_returnedInspectionProRata = Double.parseDouble(returnedInspectionProRata);
			double d_returnedPolicyProRata = Double.parseDouble(returnedPolicyProRata);
			double d_returnedSurplusContributionProRata = Double.parseDouble(returnedSurplusContributionProRata);
			double d_CalculatedReturnedSLTFProRata = (d_returnedPremiumProRata + d_returnedInspectionProRata
					+ d_returnedPolicyProRata + d_returnedSurplusContributionProRata) * d_sltfPercentage;
			double d_actualreturnedSLTFProRata = Double.parseDouble(returnedSLTFProRata);

			if (Math.abs(Precision.round(d_actualreturnedSLTFProRata, 2)
					- Precision.round(d_CalculatedReturnedSLTFProRata, 2)) < 0.05) {
				Assertions.passTest("Cancel Policy Page",
						"The Actual and calculated sltf values are matching as per 0.05%");
			} else {
				Assertions.passTest("Cancel Policy Page",
						"The Difference between actual SLTF and calculated SLTF is more than 0.05");
			}

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			// Following part is added for IO-18629
			loginPage.waitTime(2); // failing in headless and also browser modes while entering login details
			loginPage.refreshPage();
			testData = data.get(data_Value1);
			loginPage.enterLoginDetails(setUpData.get("NahoProducer"), setUpData.get("Password"));

			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			Assertions.verify(homePage.createNewAccountProducer.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);

			// Entering zipcode in Eligibility page
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			testData = data.get(data_Value2);
			// Assert roof cladding value - Architectural Shingles
			dwellingPage.roofDetailsLink.waitTillVisibilityOfElement(60);
			dwellingPage.roofDetailsLink.scrollToElement();
			dwellingPage.roofDetailsLink.click();
			dwellingPage.waitTime(5);
			dwellingPage.roofCladdingArrow.scrollToElement();
			dwellingPage.roofCladdingArrow.click();
			dwellingPage.waitTime(5);
			dwellingPage.roofCladdingOption.formatDynamicPath(testData.get("L1D1-DwellingRoofCladding"))
					.scrollToElement();
			dwellingPage.roofCladdingOption.formatDynamicPath(testData.get("L1D1-DwellingRoofCladding"))
					.waitTillVisibilityOfElement(60);
			Assertions.verify(dwellingPage.roofCladdingOption
					.formatDynamicPath(testData.get("L1D1-DwellingRoofCladding")).checkIfElementIsPresent()
					&& dwellingPage.roofCladdingOption
							.formatDynamicPath(testData.get("L1D1-DwellingRoofCladding")).checkIfElementIsDisplayed(),
					true, "Dwelling Page",
					"Roof Cladding value "
							+ dwellingPage.roofCladdingOption
									.formatDynamicPath(testData.get("L1D1-DwellingRoofCladding")).getData()
							+ " is displayed for Agent",
					false, false);

			// Sign out
			homePage.refreshPage();
			homePage.signOutButton.waitTillVisibilityOfElement(60);
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC035 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC035 ", "Executed Successfully");
			}
		}
	}
}
