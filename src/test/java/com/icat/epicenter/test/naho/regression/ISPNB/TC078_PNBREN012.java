/** Program Description: Checking Renewal Reoffer is referred for Roof Material = steel or metal and year built is older than 25 years and Checking for Referral conditions on Renewal Re-quote and IO-21801
 *
 *  Author			   :
 *  Date of Creation   : 08/09/2024
 **/

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
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseSummaryDetailsPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferQuotePage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class TC078_PNBREN012 extends AbstractNAHOTest {

	public TC078_PNBREN012() {
		super(LoginType.NAHOPRODUCER);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/ISPNB/PNBREN012.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		LoginPage loginPage = new LoginPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		DwellingPage dwellingPage = new DwellingPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		EndorseSummaryDetailsPage endorseSummaryDetailsPage = new EndorseSummaryDetailsPage();
		ReferQuotePage referQuotePage = new ReferQuotePage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing variables
		double transactionExpectedsltf;
		String transactionactualSLTF;
		String transactionPremium;
		String transactioninspectionFee;
		String transactionpolicyFee;
		BigDecimal roundOfftransactionExpectedsltf;

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		Map<String, String> testData = data.get(data_Value1);
		String covAvalue;
		String inflationGuardPercentage;
		double calCovAValue;
		String actualCovAValue;
		String calCovAValue_s;
		boolean isTestPassed = false;

		try {
			// Handling the pop up
			homePage.enterPersonalLoginDetails();

			// Creating New Account
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
			String roofMaterial = testData.get("L1D1-DwellingRoofCladding");
			String yearBuilt = testData.get("L1D1-DwellingYearBuilt");
			Assertions.passTest("Dwelling Page", "Roof Material selected as:" + roofMaterial);
			Assertions.passTest("Dwelling Page", "Year Built selected as:" + yearBuilt);
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
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote");

			// Asserting the Hard stop message When year built older 26+ and roof material =
			// still or metal 'The account is ineligible due to the roof age being outside
			// of ICAT's guidelines. If you have additional information that adjusts the
			// roof age, please email it to your ICAT Online Underwriter'
			Assertions.addInfo("Scenario 01", "Asserting the Hard stop message");
			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"The Hard Stop Message " + createQuotePage.globalErr.getData() + " displayed is verified", false,
					false);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();
			Assertions.passTest("Create Quote Page", "Clicked on Previous Button");

			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			testData = data.get(data_Value2);
			if (!testData.get("L1D1-DwellingYearBuilt").equals("")) {
				Assertions.passTest("Dwelling Page",
						"Dwelling year built original Value : " + dwellingPage.yearBuilt.getData());
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				Assertions.passTest("Dwelling Page",
						"Dwelling year built Latest Value : " + dwellingPage.yearBuilt.getData());
			}
			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			testData = data.get(data_Value1);
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

			// Quote referring when roof age 26 years old and roof material = still or metal
			// or outside guidelines
			Assertions.verify(referQuotePage.referQuote.checkIfElementIsDisplayed(), true, "Refer Quote Page",
					"Refer Quote Page loaded successfully", false, false);

			// Enter Referral Contact Details
			referQuotePage.contactName.setData(testData.get("ProducerName"));
			referQuotePage.contactEmail.setData(testData.get("ProducerEmail"));
			referQuotePage.referQuote.click();

			// verifying referral message
			Assertions.verify(referQuotePage.quoteNumberforReferral.checkIfElementIsDisplayed(), true, "Referral Page",
					"Quote " + referQuotePage.quoteNumberforReferral.getData() + " referring to USM " + " is verified",
					false, false);

			String quoteNumber = referQuotePage.quoteNumberforReferral.getData();

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
			Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();
			Assertions.passTest("Account Overview Page", "Clicked on open refferal link");

			// approving referral
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			referralPage.close.scrollToElement();
			referralPage.close.click();

			// Search referred quote
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched the Quote " + quoteNumber + " successfully");

			// Getting quote number
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Quote Number1 is : " + quoteNumber);

			// click on edit dwelling
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			Assertions.passTest("Account Overview Page", "Clicked on Edit Dwelling");

			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			if (!testData.get("L1D1-DwellingYearBuilt").equals("")) {
				Assertions.passTest("Dwelling Page",
						"Dwelling year built original Value : " + dwellingPage.yearBuilt.getData());
				dwellingPage.yearBuilt.clearData();
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()
						&& dwellingPage.expiredQuotePopUp.checkIfElementIsDisplayed()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));

				Assertions.passTest("Dwelling Page",
						"Dwelling year built Latest Value : " + dwellingPage.yearBuilt.getData());
			}

			dwellingPage.reviewDwelling();
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Clicked on Get A Quote");

			// Click on Continue
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			// Getting quote number
			Assertions.verify(accountOverviewPage.quoteNumber.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "The Quote Number2 is : " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on bind button");

			// Clicking on answer no button
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);
			Assertions.passTest("Underwriting Questions Page", "Underwriting Questions details entered");

			// Entering bind details
			quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered");

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote" + quoteNumber + " successfullly");

			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approve Referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();

			// click on approve in Approve Decline Quote page
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Referral Page", "Quote referral approved successfully");

			// Asserting policy number
			String policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. PolicyNumber is : " + policyNumber, false, false);

			// Go to home page and search the policy
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.passTest("Home Page", "Home Page loaded successfully");
			homePage.searchPolicy(policyNumber);
			Assertions.passTest("Home Page", "Searched the policy successfully");

			// Click on Renewal Link
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();
			Assertions.passTest("Policy Summary Page", "Clicked on Renewal link successfully ");

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
					"Account Overvew Page", "Account Overview Page loaded successfully", false, false);

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
			// Cov A = Cov A*inflationguard%(1.0798)
			Assertions.addInfo("Scenario 02", "Verifying inflation guard applied to renewal Cov A");
			actualCovAValue = viewOrPrintFullQuotePage.coveragesValues.formatDynamicPath(4).getData().replace(",", "");
			Assertions.verify(actualCovAValue, "$" + calCovAValue_s, "View Print Full Quote Page",
					"Inflation guard " + testData.get("InflationGuardPercentage")
							+ " applied to Coverage A, actual coverage A value " + actualCovAValue
							+ " and calculated coverage A value $" + calCovAValue_s + " bothe are same",
					false, false);
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");
			viewOrPrintFullQuotePage.backButton.scrollToElement();
			viewOrPrintFullQuotePage.backButton.click();
			// IO-21801 Ended

			// Getting Renewal Quote Number,Checking renewl offer is referred
			String renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "Renewal Quote Number is : " + renewalQuoteNumber);
			Assertions.addInfo("Scenario 03", "Checking renewl offer is referred");
			Assertions.verify(accountOverviewPage.referredStatus.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Renewal offer is referred for the Roof material " + roofMaterial + " and "
							+ yearBuilt + " verified",
					false, false);
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// click on Open Referral link
			Assertions.verify(accountOverviewPage.openReferral.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Clicked on open referral link successfully", false, false);
			accountOverviewPage.openReferral.waitTillPresenceOfElement(60);
			accountOverviewPage.openReferral.waitTillVisibilityOfElement(60);
			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approve the quote for referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
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

			// Searched for New Quote
			homePage.findFilterArrow.scrollToElement();
			homePage.findFilterArrow.waitTillPresenceOfElement(60);
			homePage.findFilterArrow.click();
			homePage.findFilterQuoteOption.click();
			homePage.findQuoteNumber.setData(renewalQuoteNumber);
			homePage.findBtnQuote.waitTillPresenceOfElement(60);
			homePage.findBtnQuote.click();
			Assertions.passTest("Home Page", "Clicked on Searched Quote successfully ");

			// Click on Release Renewal to Producer
			Assertions.verify(accountOverviewPage.releaseRenewalToProducerButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			accountOverviewPage.releaseRenewalToProducerButton.waitTillPresenceOfElement(60);
			accountOverviewPage.releaseRenewalToProducerButton.waitTillVisibilityOfElement(60);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal To Producer successfully");

			// Click on Edit Dwelling link
			accountOverviewPage.editDwelling.waitTillPresenceOfElement(60);
			accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();
			String previousAddress = dwellingPage.address.getData();
			String previousNoOfUnits = dwellingPage.noOfUnits.getData();
			String previousNoOfStories = dwellingPage.numOfFloors.getData();
			Assertions.passTest("Account Overview Page",
					"Clicked Edit Dwelling link successfully and Navigated to Dwelling page ");

			// enter dwelling details
			testData = data.get(data_Value2);
			dwellingPage.addDwellingDetails(testData, 1, 1);
			dwellingPage.waitTime(2);
			dwellingPage.reviewDwelling.waitTillPresenceOfElement(60);
			dwellingPage.reviewDwelling.waitTillVisibilityOfElement(60);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();

			Assertions.passTest("Dwelling Page",
					"Changing Address From: " + previousAddress + " To: " + dwellingPage.dwellingAddress1.getData());
			Assertions.passTest("Dwelling Page", "Changing NoOfUnits From: " + previousNoOfUnits + " To: "
					+ dwellingPage.dwellingNoOfunits.getData());
			Assertions.passTest("Dwelling Page", "Changing NoOfStories From: " + previousNoOfStories + " To: "
					+ dwellingPage.dwellingNoOfstories.getData());

			// Click on create quote button on dwelling page
			Assertions.verify(dwellingPage.createQuote.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Clicked on CreateQuote Button successfully", false, false);
			dwellingPage.createQuote.waitTillPresenceOfElement(60);
			dwellingPage.createQuote.waitTillVisibilityOfElement(60);
			dwellingPage.createQuote.scrollToElement();
			dwellingPage.createQuote.click();

			// Enter Cov A value
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded", false, false);
			String previousCovA = createQuotePage.coverageADwelling.getData().replaceAll("[^\\d-.]", "");
			createQuotePage.coverageADwelling.waitTillPresenceOfElement(60);
			createQuotePage.coverageADwelling.waitTillVisibilityOfElement(60);
			createQuotePage.coverageADwelling.scrollToElement();
			createQuotePage.coverageADwelling.clearData();

			createQuotePage.coverageADwelling.appendData(testData.get("L1D1-DwellingCovA"));

			createQuotePage.coverageADwelling.tab();
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Cov A value changing From: " + "$" + previousCovA + "To: " + "$"
					+ testData.get("L1D1-DwellingCovA"));

			// Asserting Hard Stop message
			Assertions.addInfo("Scenario 04", "Asserting hard stop message when Cov A less than minimum limit");
			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Coverage A less than minimum limit of $150000 message is verified, Referral message is:"
							+ createQuotePage.globalErr.getData(),
					false, false);
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// changing cov A value more than $150k but less than $300k
			previousCovA = createQuotePage.coverageADwelling.getData().replaceAll("[^\\d-.]", "");
			testData = data.get(data_Value3);
			createQuotePage.coverageADwelling.waitTillPresenceOfElement(60);
			createQuotePage.coverageADwelling.waitTillVisibilityOfElement(60);
			createQuotePage.coverageADwelling.scrollToElement();
			createQuotePage.coverageADwelling.clearData();
			createQuotePage.coverageADwelling.appendData(testData.get("L1D1-DwellingCovA"));
			createQuotePage.coverageADwelling.tab();
			createQuotePage.getAQuote.waitTillPresenceOfElement(60);
			createQuotePage.getAQuote.waitTillVisibilityOfElement(60);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Cov A value changing From: " + "$" + previousCovA + "To: " + "$"
					+ testData.get("L1D1-DwellingCovA"));

			Assertions.addInfo("Scenario 05", "Asserting warning message");
			Assertions.verify(
					referQuotePage.referralMessages.formatDynamicPath("limit of less than").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Coverage A limit less than $300,000 warning message is "
							+ referQuotePage.referralMessages.formatDynamicPath("limit of less than").getData()
							+ " verified ",
					false, false);

			Assertions.verify(
					referQuotePage.referralMessages.formatDynamicPath("more than").checkIfElementIsDisplayed(), true,
					"Create Quote Page",
					"Number of stories more than 3 warning message is"
							+ referQuotePage.referralMessages.formatDynamicPath("more than").getData() + " verified ",
					false, false);
			Assertions.verify(
					referQuotePage.referralMessages
							.formatDynamicPath("The quoted building is ineligible due").checkIfElementIsDisplayed(),
					true, "Create Quote Page",
					"Coverage C greater than 70% of Coverage A warning message is " + referQuotePage.referralMessages
							.formatDynamicPath("The quoted building is ineligible due").getData() + " verified ",
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Click on continue button
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("Create Quote Page", "Clicked on continue button");
			}

			// clicking on issue Quote
			Assertions.verify(accountOverviewPage.issueQuoteButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded", false, false);

			// Getting Renewal Quote Number
			String NewrenewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 12);
			Assertions.passTest("Account Overview Page", "New Renewal Quote Number2 is : " + NewrenewalQuoteNumber);
			accountOverviewPage.issueQuoteButton.scrollToElement();
			accountOverviewPage.issueQuoteButton.click();
			Assertions.passTest("Account Overview Page", "Click on Issue Quote Button successfully");

			// Click on bind button
			accountOverviewPage.clickOnRenewalRequestBindNAHO(testData, NewrenewalQuoteNumber);
			Assertions.passTest("Account Overview Page",
					"Click on Bind Request Button successfully and Navigated to Request Bind page successfully");

			// Entering renewal bind details
			requestBindPage.renewalRequestBindNAHO(testData);
			Assertions.passTest("Request Bind Page",
					"Selected the Flood as :" + testData.get("ApplicantHaveFloodPolicy"));
			Assertions.passTest("Request Bind Page", "Bind Details Entered successfully");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.searchQuote(NewrenewalQuoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote" + NewrenewalQuoteNumber + " successfullly");

			accountOverviewPage.openReferral.scrollToElement();
			accountOverviewPage.openReferral.click();

			// Approve the quote for referral
			if (referralPage.pickUp.checkIfElementIsPresent() && referralPage.pickUp.checkIfElementIsDisplayed()) {
				referralPage.pickUp.scrollToElement();
				referralPage.pickUp.click();
			}

			Assertions.verify(referralPage.approveOrDeclineRequest.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			Assertions.verify(approveDeclineQuotePage.approveButton.checkIfElementIsDisplayed(), true,
					"Approve Decline Quote Page", "Approve Decline Quote page loaded successfully", false, false);
			approveDeclineQuotePage.clickOnApprove();
			Assertions.passTest("Approve Decline Quote Page", "Bind Request approved successfully");

			// Click on override
			requestBindPage.overrideEffectiveDate.waitTillPresenceOfElement(60);
			requestBindPage.overrideEffectiveDate.waitTillVisibilityOfElement(60);
			requestBindPage.overrideEffectiveDate.scrollToElement();
			requestBindPage.overrideEffectiveDate.select();
			requestBindPage.approve.waitTillPresenceOfElement(60);
			requestBindPage.approve.waitTillVisibilityOfElement(60);
			requestBindPage.approve.scrollToElement();
			requestBindPage.approve.click();

			// Getting Policy Number from policy summary Page
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
					"Renewal Policy Summary Page",
					"Renewal Policy Summary Page loaded successfully. Renewal PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// Click on Endorse PB link on policy summary page
			policySummaryPage.endorsePB.waitTillPresenceOfElement(60);
			policySummaryPage.endorsePB.waitTillVisibilityOfElement(60);
			policySummaryPage.endorsePB.scrollToElement();
			policySummaryPage.endorsePB.click();
			Assertions.passTest("Policy Summary Page", "Clicked on EndorsePB link successfully");
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy Page loaded", false, false);

			// Enter endorsement effective date
			endorsePolicyPage.endorsementEffDate.waitTillPresenceOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillVisibilityOfElement(60);
			endorsePolicyPage.endorsementEffDate.waitTillElementisEnabled(60);
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page",
					"Entered Endorsement Effective Date successfully,Endorsement Effective Date: "
							+ testData.get("TransactionEffectiveDate"));

			// click on Edit Location/Building Information
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillPresenceOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.editLocOrBldgInformationLink.scrollToElement();
			endorsePolicyPage.editLocOrBldgInformationLink.click();
			Assertions.passTest("Endorse Policy Page",
					"Clicked on Edit Location/BuildingInformation link successfully");

			// Entering dwelling details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded", false, false);
			dwellingPage.OccupiedByArrow.waitTillPresenceOfElement(60);
			dwellingPage.OccupiedByArrow.waitTillVisibilityOfElement(60);
			dwellingPage.OccupiedByArrow.scrollToElement();
			dwellingPage.OccupiedByArrow.click();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).scrollToElement();
			dwellingPage.OccupiedByOption.formatDynamicPath(testData.get("L1D1-OccupiedBy")).click();
			Assertions.passTest("Dwelling Page", "Occupancy selected as:" + testData.get("L1D1-OccupiedBy"));
			dwellingPage.reviewDwelling.waitTillPresenceOfElement(60);
			dwellingPage.reviewDwelling.waitTillVisibilityOfElement(60);
			dwellingPage.reviewDwelling.scrollToElement();
			dwellingPage.reviewDwelling.click();
			dwellingPage.continueButton.waitTillPresenceOfElement(60);
			dwellingPage.continueButton.waitTillVisibilityOfElement(60);
			dwellingPage.continueButton.scrollToElement();
			dwellingPage.continueButton.click();
			Assertions.passTest("Dwelling Page", "Clicked on Continue button successfully");

			// Clicked on continue button on create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded", false, false);
			createQuotePage.continueEndorsementButton.waitTillPresenceOfElement(60);
			createQuotePage.continueEndorsementButton.waitTillVisibilityOfElement(60);
			createQuotePage.continueEndorsementButton.scrollToElement();
			createQuotePage.continueEndorsementButton.click();
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
				Assertions.passTest("Create Quote Page", "Clicked Continue button successfully");
			}

			// Clicked on Next Button on endorse policy page
			Assertions.verify(endorsePolicyPage.nextButton.checkIfElementIsDisplayed(), true, "Enodrse Policy Page",
					"Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.nextButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.nextButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();
			if (endorsePolicyPage.continueButton.checkIfElementIsPresent()
					&& endorsePolicyPage.continueButton.checkIfElementIsDisplayed()) {
				endorsePolicyPage.continueButton.waitTillPresenceOfElement(60);
				endorsePolicyPage.continueButton.waitTillVisibilityOfElement(60);
				endorsePolicyPage.continueButton.scrollToElement();
				endorsePolicyPage.continueButton.click();
			}

			// calculating SLTFv value on endorse policy page
			// (premium+inspectionfee+policyfee)*sltf%=5%

			transactionPremium = endorsePolicyPage.newPremium.formatDynamicPath(1).getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Endorse Policy Page", "Transaction Premium: " + "$" + transactionPremium);
			transactioninspectionFee = endorsePolicyPage.newInspectionFee.formatDynamicPath(2).getData()
					.replaceAll("[^\\d-.]", "");
			Assertions.passTest("Endorse Policy Page", "Transaction Inspection Fee: " + "$" + transactioninspectionFee);
			transactionpolicyFee = endorsePolicyPage.newPolicyFee.formatDynamicPath(2).getData().replaceAll("[^\\d-.]",
					"");
			Assertions.passTest("Endorse Policy Page", "Transaction Policy Fee: " + "$" + transactionpolicyFee);
			String transactionSurplusContributionValue = endorsePolicyPage.surplusContributionValueNAHO
					.formatDynamicPath(1).getData().replaceAll("[^\\d-.]", "");
			Assertions.passTest("Endorse Policy Page",
					"Transaction Surplus Contribution Value: " + "$" + transactionSurplusContributionValue);
			String sltfPercentage = testData.get("SLTFPercentage");
			Assertions.passTest("Endorse Policy Page", "SLTF Percentage:" + sltfPercentage);
			transactionactualSLTF = endorsePolicyPage.newSLTF.formatDynamicPath(1).getData();
			Assertions.passTest("Endorse Policy Page", "Transaction Actual SLTF value: " + transactionactualSLTF);
			transactionExpectedsltf = (Double.parseDouble(transactionPremium) + Double.parseDouble(transactionpolicyFee)
					+ Double.parseDouble(transactionSurplusContributionValue)
					+ Double.parseDouble(transactioninspectionFee)) * (Double.parseDouble(sltfPercentage));
			roundOfftransactionExpectedsltf = BigDecimal.valueOf(transactionExpectedsltf);
			roundOfftransactionExpectedsltf = roundOfftransactionExpectedsltf.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Endorse Policy Page",
					"Transaction Expected SLTF value: " + format.format(roundOfftransactionExpectedsltf));
			Assertions.passTest("Endorse Policy Page",
					"Calculated SLTF value = " + "(" + transactionPremium + "+" + transactionpolicyFee + "+"
							+ transactioninspectionFee + "*" + "(" + sltfPercentage + ")" + "="
							+ format.format(roundOfftransactionExpectedsltf));

			// Verifying actual SLTF and calculated SLTF are the same
			Assertions.addInfo("Scenario 06", "Verifying actual SLTF and calculated SLTF are the same");
			if (transactionactualSLTF.equalsIgnoreCase(format.format(roundOfftransactionExpectedsltf))) {
				Assertions.verify(transactionactualSLTF, format.format(roundOfftransactionExpectedsltf),
						"Endorse Policy Page",
						"The Calculated and Actual SLTF value are the same for AL Percentage 6% Actual Transaction SLTF value : "
								+ transactionactualSLTF,
						false, false);
			} else {
				Assertions.verify(transactionactualSLTF, format.format(roundOfftransactionExpectedsltf),
						"Endorse Policy Page",
						"The Calculated and Actual SLTF value are Not the same for AL Percentage 6% : "
								+ transactionactualSLTF,
						false, false);

			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");

			// Click on complete
			endorsePolicyPage.completeButton.waitTillPresenceOfElement(60);
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			Assertions.passTest("Endorse Policy Page", "Clciked on Complete button successfully");

			// Clciked on close button on Enodrse Summary page
			Assertions.verify(endorseSummaryDetailsPage.closeBtn.checkIfElementIsDisplayed(), true,
					"Endorse SUmmary Details Page", "Endorse Summary Details Page loaded successfully", false, false);
			endorseSummaryDetailsPage.closeBtn.waitTillPresenceOfElement(60);
			endorseSummaryDetailsPage.closeBtn.waitTillVisibilityOfElement(60);
			endorseSummaryDetailsPage.closeBtn.scrollToElement();
			endorseSummaryDetailsPage.closeBtn.click();
			Assertions.passTest("Endorse Summary Details Page", "Clicked on Close button successfully");

			// Getting Policy Number from policy summary Page
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true,
					"Renewal Policy Summary Page",
					"Renewal Policy Summary Page loaded successfully. Renewal PolicyNumber is "
							+ policySummaryPage.policyNumber.getData(),
					false, false);

			// verifying Endorsement link is created on Transaction history section.
			Assertions.addInfo("Scenario 07", "verifying Endorsement link is created on Transaction history section.");
			Assertions.verify(policySummaryPage.transHistReason.formatDynamicPath(3).checkIfElementIsDisplayed(), true,
					"Renewal Policy Summary Page",
					"Endorsement link is created on Transaction history section is verified", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			// Click on Endorsement link
			policySummaryPage.transHistReason.formatDynamicPath(3).waitTillPresenceOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath(3).waitTillVisibilityOfElement(60);
			policySummaryPage.transHistReason.formatDynamicPath(3).scrollToElement();
			policySummaryPage.transHistReason.formatDynamicPath(3).click();
			Assertions.passTest("Policy Summary Page",
					"Clicked on Endorsement link successfully and policy summary page loaded");

			// Checking SLTF calculated correctly for endorsement on Policy Summary Page
			transactionPremium = policySummaryPage.PremiumFee.formatDynamicPath(1).getData().replaceAll("[^\\d-.]", "");

			transactioninspectionFee = policySummaryPage.PremiunInspectionFee.formatDynamicPath(1).getData()
					.replaceAll("[^\\d-.]", "");
			transactionpolicyFee = policySummaryPage.PremiumPolicyFee.formatDynamicPath(1).getData()
					.replaceAll("[^\\d-.]", "");
			transactionactualSLTF = policySummaryPage.TaxesAndStateFees.formatDynamicPath(1).getData();

			transactionSurplusContributionValue = policySummaryPage.transactionSurplusContribution.getData()
					.replaceAll("[^\\d-.%]", "");

			Assertions.passTest("Policy Summary Page", "Transaction Premium value : " + "$" + transactionPremium);
			Assertions.passTest("Policy Summary Page", "Transaction Inspection fee: " + "$" + transactioninspectionFee);
			Assertions.passTest("Policy Summary Page", "Transaction Policy fee: " + "$" + transactionpolicyFee);
			Assertions.passTest("Policy Summary Page",
					"Transaction Surplus Contribution Value: " + "$" + transactionSurplusContributionValue);
			sltfPercentage = testData.get("SLTFPercentage");
			Assertions.passTest("Policy Summary Page", "SLTF Percentage:" + sltfPercentage);
			Assertions.passTest("Policy Summary Page", "Transaction Actual SLTF value: " + transactionactualSLTF);
			transactionExpectedsltf = (Double.parseDouble(transactionPremium) + Double.parseDouble(transactionpolicyFee)
					+ Double.parseDouble(transactionSurplusContributionValue)
					+ Double.parseDouble(transactioninspectionFee)) * (Double.parseDouble(sltfPercentage));
			roundOfftransactionExpectedsltf = BigDecimal.valueOf(transactionExpectedsltf);
			roundOfftransactionExpectedsltf = roundOfftransactionExpectedsltf.setScale(2, RoundingMode.HALF_UP);
			Assertions.passTest("Policy Summary Page",
					"Transaction Expected SLTF value: " + format.format(roundOfftransactionExpectedsltf));
			Assertions.passTest("Policy Summary Page",
					"Calculated SLTF value = " + "(" + transactionPremium + "+" + transactionpolicyFee + "+"
							+ transactioninspectionFee + ")" + "*" + "(" + sltfPercentage + ")" + "="
							+ format.format(roundOfftransactionExpectedsltf));

			// Verifying actual SLTF and calculated SLTF are the same
			Assertions.addInfo("Scenario 08", "Verifying actual SLTF and calculated SLTF are the same");
			if (transactionactualSLTF.equalsIgnoreCase(format.format(roundOfftransactionExpectedsltf))) {
				Assertions.verify(transactionactualSLTF, format.format(roundOfftransactionExpectedsltf),
						"Policy Summary Page",
						"The Calculated and Actual SLTF value are the same for AL Percentage 6%, Actual SLTF value : "
								+ transactionactualSLTF,
						false, false);
			} else {
				Assertions.verify(transactionactualSLTF, format.format(roundOfftransactionExpectedsltf),
						"Policy Summary Page",
						"The Calculated and Actual SLTF value are Not the same for AL Percentage 6% : "
								+ transactionactualSLTF,
						false, false);

			}
			Assertions.addInfo("Scenario 08", "Scenario 08 Ended");

			// Sign Out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("PNBTC078 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("PNBTC078 ", "Executed Successfully");
			}
		}
	}
}
