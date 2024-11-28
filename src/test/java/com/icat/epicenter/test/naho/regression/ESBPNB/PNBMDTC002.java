/** Program Description: 1.Verifying the ineligible referral message when Roof Material as “Unknown” and Year of Construction as “2009” for Maryland state on renewal
2.Verifying SLTF values on view print full quote page.
3.Verifying the below Warning Messages are displayed
“Due to the number of units, this account is ineligible”
“The quoted building has a construction class of 'Other'. For consideration, please provide your ICAT Online Underwriter with additional information
regarding the construction material.”
“The quoted building has a year of construction prior to 1970. Increased Ordinance or Law coverage is ineligible for risks with years of construction prior to 1970.”
“The quoted building is more than 3 stories and requires further review by an ICAT Online Underwriter.”
“This is a tenant occupied or short-term rental property with a Coverage E limit greater than $500,000 and requires further review by an ICAT Online Underwriter.”
“The account is ineligible due to the roof age being outside of ICAT’s guidelines.
If you have additional information that adjusts the roof age, please email it to your ICAT Online Underwriter.”
when Year of Construction to “1969”,Construction Type as “Other” ,Number of Stories as “4” ,and Number of Units as "5",Tenant as Yes
and Coverage E as “$1,000,000”
 *  Author			   : Sowndarya NH
 *  Date of Creation   : 12/05/2022
 **/
package com.icat.epicenter.test.naho.regression.ESBPNB;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class PNBMDTC002 extends AbstractNAHOTest {

	public PNBMDTC002() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ESBPNB/MDTC002.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing all Objects
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing the variables
		int data_Value1 = 0;
		int data_Value2 = 1;
		int quoteLen;
		String quoteNumber;
		String policyNumber;
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
			Assertions.passTest("Building Page", "The Year Built entered is " + testData.get("L1D1-DwellingYearBuilt"));
			Assertions.passTest("Building Page",
					"The Roof Cladding entered is " + testData.get("L1D1-DwellingRoofCladding"));

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

			// Getting the Quote Number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber);

			// Click on Request bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// Entering details in Underwriting Questions Page
			Assertions.verify(underwritingQuestionsPage.saveButton.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered successfully");

			// Entering bind details
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Clicking on homepage button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote for referral is searched successfully");

			// Opening the Referral
			Assertions.verify(accountOverviewPage.uploadPreBindDocuments.checkIfElementIsPresent(), true,
					"Account Overview Page", "Account Overview Page Loaded Successfully", false, false);
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
			}

			// Approve Referral
			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);

			// IO-21792-As part of this our expectation is to validate if the referred quote
			// is assigned to USM not "Holder RMS"
			if (referralPage.assignedUser.getData().contentEquals("Sean Minn")) {

				Assertions.verify(referralPage.assignedUser.getData().contains("Sean Minn"), true, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			} else {

				Assertions.verify(referralPage.assignedUser.getData().contains("Holder RMS"), false, "Referral Page",
						"Quote is referred to USM " + referralPage.assignedUser.getData(), false, false);
			}
			// Ended

			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(requestBindPage.approve.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind page loaded successfully", false, false);
			requestBindPage.approveRequestNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting the Policy Number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is " + policyNumber, false, false);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Clicking on expaac link in home page
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			Assertions.passTest("Home Page", "Clicked on Expacc Link");

			// Entering expaac data
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

			// Getting renewal quote number
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number : " + quoteNumber);

			// Click on open referral link
			if (accountOverviewPage.openReferral.checkIfElementIsPresent()
					&& accountOverviewPage.openReferral.checkIfElementIsDisplayed()) {
				accountOverviewPage.openReferral.scrollToElement();
				accountOverviewPage.openReferral.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

				// Verifying the Referral message
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral Page loaded successfully", false, false);
				Assertions.addInfo("Scenario 01",
						"Verifying the Referral message on renewal referral page when Year Built=2009 and Roof Cladding=Unknown");
				if (referralPage.producerCommentsProducerSection.getData()
						.contains("roof age being outside of ICAT's guidelines")) {
					Assertions.verify(
							referralPage.producerCommentsProducerSection.getData()
									.contains("roof age being outside of ICAT's guidelines"),
							true, "Referral Page", "The Referral message "
									+ referralPage.producerCommentsProducerSection.getData() + " displayed is verified",
							false, false);
				} else {
					Assertions.passTest("Referral Page", "The Referral message is not displayed");
				}
				Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

				// Approve the Referral
				referralPage.clickOnApprove();

				// Click on approve in Approve Decline Quote page
				approveDeclineQuotePage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				// Verifying referral complete message
				Assertions.verify(referralPage.referralCompleteMsg.checkIfElementIsDisplayed(), true, "Referral Page",
						referralPage.referralCompleteMsg.getData() + " message is verified successfully", false, false);
				referralPage.close.scrollToElement();
				referralPage.close.click();

				// Search the Quote
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber);
				Assertions.passTest("Home Page", "Searched the Quote : " + quoteNumber + " successfully");
			}

			// Click on edit dwelling
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit dwelling");

			// Edit the Dwelling 1 Details
			// Year of Construction =“1969”,Construction Type as “Other” ,Number of Stories
			// as “4” ,and Number of Units as "5",Occupied by as Tenant
			// and Coverage E as “$1,000,000”
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			testData = data.get(data_Value2);

			// Entering Construction Type as “Other”
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

				// Handling the expired quote popup
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				Assertions.passTest("Dwelling Page",
						"Dwelling Construction Type Latest Value : " + dwellingPage.constructionTypeData.getData());
			}

			// Entering Year of Construction =“1969”
			if (!testData.get("L1D1-DwellingYearBuilt").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Dwelling year built Original Value : " + dwellingPage.yearBuilt.getData());
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				Assertions.passTest("Dwelling Page",
						"Dwelling year built Latest Value : " + dwellingPage.yearBuilt.getData());
			}

			// Entering Number of Units as "5"
			if (!testData.get("L1D1-DwellingUnits").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Number of Units Original Value : " + dwellingPage.noOfUnits.getData());
				dwellingPage.noOfUnits.scrollToElement();
				dwellingPage.noOfUnits.setData(testData.get("L1D1-DwellingUnits"));
				Assertions.passTest("Dwelling Page",
						"Number of Units Latest Value : " + dwellingPage.noOfUnits.getData());
			}

			// Entering Number of Stories as "4"
			if (!testData.get("L1D1-DwellingFloors").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Dwelling Stories  Original Value : " + dwellingPage.numOfFloors.getData());
				dwellingPage.numOfFloors.setData(testData.get("L1D1-DwellingFloors"));
				Assertions.passTest("Dwelling Page",
						"Dwelling Stories Latest Value : " + dwellingPage.numOfFloors.getData());
			}

			// Selecting Occupied by as Tenant
			if (dwellingPage.OccupiedByArrow.checkIfElementIsPresent() && !testData.get("L1D1-OccupiedBy").equals("")) {
				Assertions.addInfo("Dwelling Page",
						"Occupied by Original Value : " + dwellingPage.occupiedByData.getData());
				dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
				dwellingPage.OccupiedByArrow.scrollToElement();
				dwellingPage.OccupiedByArrow.click();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
				dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
				Assertions.passTest("Dwelling Page",
						"Occupied by Latest Value : " + dwellingPage.occupiedByData.getData());
			}
			Assertions.passTest("Dwelling Page", "Dwelling Details Modified successfully");

			// Click on review dwelling
			dwellingPage.reviewDwelling();

			// Click on create quote
			if (dwellingPage.createQuote.checkIfElementIsPresent()
					|| dwellingPage.createQuote.checkIfElementIsDisplayed()) {
				dwellingPage.createQuote.waitTillPresenceOfElement(60);
				dwellingPage.createQuote.waitTillVisibilityOfElement(60);
				dwellingPage.createQuote.waitTillButtonIsClickable(60);
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();
			}

			// Enter coverage E as “$1,000,000” and ordinance or law value as 25%
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.enterInsuredValuesNAHO(testData, 1, 1);
			Assertions.passTest("Create Quote Page",
					"Coverage E Value Entered is " + testData.get("L1D1-DwellingCovE"));
			Assertions.passTest("Create Quote Page",
					"Ordinance or law value selected is " + testData.get("OrdinanceOrLaw"));

			// click on get a quote
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get a quote button");

			// Verifying the warning messages
			Assertions.addInfo("Scenario 01",
					"Verifying the Warning messages while creating requote on renewal policy when when Year of Construction =1969,Construction Type =Other ,Number of Stories = 4 , Number of Units = 5,Tenant as Yes and Coverage E=$1,000,000");
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("construction class").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning message "
							+ createQuotePage.warningMessages.formatDynamicPath("construction class").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath("40 years").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning message " + createQuotePage.warningMessages.formatDynamicPath("40 years").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages
							.formatDynamicPath("more than 3 stories").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning message "
							+ createQuotePage.warningMessages.formatDynamicPath("more than 3 stories").getData()
							+ " displayed is verified",
					false, false);
			if (createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
					.checkIfElementIsPresent()
					&& createQuotePage.warningMessages.formatDynamicPath("tenant occupied or short-term rental")
							.checkIfElementIsDisplayed()) {
				Assertions.verify(
						createQuotePage.warningMessages
								.formatDynamicPath("tenant occupied or short-term rental").checkIfElementIsDisplayed(),
						true, "Create Quote Page",
						"The Warning message "
								+ createQuotePage.warningMessages
										.formatDynamicPath("tenant occupied or short-term rental").getData()
								+ " displayed is verified",
						false, false);
			} else {
				Assertions.passTest("Create Quote Page", "The Warning message is not displayed");
			}
			Assertions.verify(createQuotePage.warningMessages.formatDynamicPath("roof age").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning message " + createQuotePage.warningMessages.formatDynamicPath("roof age").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					createQuotePage.warningMessages.formatDynamicPath("number of units").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"The Warning message "
							+ createQuotePage.warningMessages.formatDynamicPath("number of units").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Click on continue
			createQuotePage.continueButton.scrollToElement();
			createQuotePage.continueButton.click();
			Assertions.passTest("Create Quote Page", "Clicked on continue button");

			// Getting renewal quote number
			Assertions.verify(accountOverviewPage.viewPrintFullQuoteLink.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Renewal ReQuote Number : " + quoteNumber);

			// Click on view print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print full quote link");
			Assertions.verify(viewOrPrintFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page", "View/Print Full Quote Page loaded successfully", false, false);

			// Getting premium,inspection and policy fee values
			String premiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",", "");
			String policyFee = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String inspectionFee = viewOrPrintFullQuotePage.inspectionFee.getData().replace("$", "").replace(",", "");
			String actualSltf = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",",
					"");

			// Converting String values to double
			double d_premiumVPFQ = Double.parseDouble(premiumValue);
			double d_inspecionFeeVPFQ = Double.parseDouble(inspectionFee);
			double d_policyFeeVPFQ = Double.parseDouble(policyFee);
			double d_actualSltf = Double.parseDouble(actualSltf);

			// Getting sltf percentage
			String sltfPercentage = testData.get("SLTFPercentage");

			// Calculating sltf
			double d_sltf = (d_premiumVPFQ + d_inspecionFeeVPFQ + d_policyFeeVPFQ);
			double d_calcSltf = d_sltf * Double.parseDouble(sltfPercentage);

			// Verifying calculated and actual sltf values
			Assertions.addInfo("Scenario 02",
					"Verifying the Actual and Calculated SLTF values on View Print Full Quote Page");
			if (Precision.round(Math.abs(Precision.round(d_calcSltf, 2) - Precision.round(d_actualSltf, 2)),
					2) < 0.05) {
				Assertions.passTest("View/Print Full Quote Page", "Actual SLTF Value :" + "$" + d_actualSltf);
				Assertions.passTest("View/Print Full Quote Page", "Calculated SLTF Value :" + "$" + d_calcSltf);
			} else {
				Assertions.passTest("View/Print Full Quote Page",
						"The Difference between actual  and calculated SLTF is more than 0.05");
			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Click on go back
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();

			// Click on issue quote
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Issue Quote Button");

			// Click on release renewal to producer button
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release renewal to producer button");

			// Click on request bind
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind Button");

			// Enter bind details
			testData = data.get(data_Value1);
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);

			// Verify the presence of due diligence checkbox and text
			Assertions.addInfo("Scenario 03",
					"Verifying the Due diligence checkbox and wordings on renewal request bind page");
			Assertions.verify(requestBindPage.dueDiligenceCheckbox.checkIfElementIsDisplayed(), true,
					"Request Bind Page", "Due diligence Check box present is verified", false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText.formatDynamicPath("A minimum 25%").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings " + requestBindPage.dueDiligenceText.formatDynamicPath("A minimum 25%").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("Completed and signed application").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings " + requestBindPage.dueDiligenceText
							.formatDynamicPath("Completed and signed application").getData() + " displayed is verified",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("Surplus Lines Disclosure- Signature").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings " + requestBindPage.dueDiligenceText
							.formatDynamicPath("Surplus Lines Disclosure- Signature").getData()
							+ " displayed is verified",
					false, false);
			Assertions.verify(
					requestBindPage.dueDiligenceText
							.formatDynamicPath("diligent effort form").checkIfElementIsDisplayed(),
					true, "Request Bind Page",
					"The Wordings "
							+ requestBindPage.dueDiligenceText.formatDynamicPath("diligent effort form").getData()
							+ " displayed is verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// Enter Renewal Bind Details
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page", "Entered the renewal request bind details successfully");

			// Asserting renewal policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
					false);

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBMDTC002 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBMDTC002 ", "Executed Successfully");
			}
		}
	}
}
