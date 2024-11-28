/** Program Description: To validate if Roll forward is applied on renewal quote of AOP policy and IO-21583
 *  Author			   : Abha
 *  Date of Creation   : 01/08/2019
**/

package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Precision;

import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.GLInformationPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PreferenceOptionsPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.QuoteDetailsPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC071 extends AbstractCommercialTest {

	public TC071() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID071.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Pages
		LoginPage loginPage = new LoginPage();
		PreferenceOptionsPage preferenceOptionsPage = new PreferenceOptionsPage();
		HomePage homePage = new HomePage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		BuildingPage buildingPage = new BuildingPage();
		LocationPage locationPage = new LocationPage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		RequestBindPage requestBindPage = new RequestBindPage();
		BindRequestSubmittedPage bindRequestPage = new BindRequestSubmittedPage();
		ReferralPage referralPage = new ReferralPage();
		PolicySummaryPage policySummarypage = new PolicySummaryPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		GLInformationPage glInformationPage = new GLInformationPage();
		PriorLossesPage priorLossPage = new PriorLossesPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		QuoteDetailsPage quoteDetailsPage = new QuoteDetailsPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();
		ViewOrPrintFullQuotePage viewOrPrintFullQuotePage = new ViewOrPrintFullQuotePage();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		int data_Value2 = 1;
		Map<String, String> testData = data.get(data_Value1);
		DecimalFormat df = new DecimalFormat("0.00");
		boolean isTestPassed = false;

		try {
			// Added code for Taxes and fees update for the TX state
			// Sign out as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as usm successfully");

			// Sign in as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("Producer"), setUpData.get("Password"));
			homePage.enterPersonalLoginDetails();
			Assertions.passTest("Login", "Logged in as Producer successfully");

			// Click on user preference link and enter the details
			homePage.userPreferences.scrollToElement();
			homePage.userPreferences.click();
			Assertions.passTest("Home Page", "Clicked on User Preferences");

			// Add broker fees
			Assertions.passTest("Preference Options Page", "Preference Options Page loaded successfully");
			testData = data.get(data_Value1);
			preferenceOptionsPage.addBrokerFees(testData);
			Assertions.passTest("Preference Options Page", "Details entered successfully");

			// Navigate to homepage
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Sign out as producer successfully");
			// New code ended

			// Login as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Login", "Logged in to application successfully");

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

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// selecting peril
			if (!testData.get("Peril").equals("")) {
				Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
						"Select Peril Page loaded successfully", false, false);
				selectPerilPage.selectPeril(testData.get("Peril"));
			}

			// enter prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);
				priorLossPage.selectPriorLossesInformation(testData);
			}

			// entering general liability details
			if (!testData.get("L1-GLLocationClass").equals("")) {
				Assertions.verify(glInformationPage.locationClassArrow.checkIfElementIsDisplayed(), true,
						"GL Information Page", "GL Information Page loaded successfully", false, false);
				glInformationPage.enterGLInformation(testData);
				Assertions.passTest("GL Information Page", "GL Information details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.enterBindDetails(testData);
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
					"Bind Request Page loaded successfully", false, false);
			bindRequestPage.clickOnHomepagebutton();
			Assertions.passTest("Home Page", "Clicked on Home button");

			// searching the quote number in grid and clicking on the quote link
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote opened successfully");

			// click on open referral link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// getting the policy number
			Assertions.verify(policySummarypage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary page loaded successfully", false, false);
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.passTest("Policy Summary Page", "Policy Number is : " + policyNumber);

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);

			// Entering Expacc Details
			homePage.expaccLink.scrollToElement();
			homePage.expaccLink.click();
			expaccInfoPage.enterExpaccInfo(testData, policyNumber);
			Assertions.passTest("ExpaccInfo Page", "Expacc Info details entered successfully");

			// Go to Home Page
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home page loaded successfully", false, false);
			homePage.searchPolicy(policyNumber);

			// Adding the CR IN-4812
			// Click on Renew Policy link
			policySummarypage.renewPolicy.scrollToElement();
			policySummarypage.renewPolicy.click();

			// click on Yes
			if (policyRenewalPage.yesButton.checkIfElementIsPresent()
					&& policyRenewalPage.yesButton.checkIfElementIsDisplayed()) {
				policyRenewalPage.yesButton.scrollToElement();
				policyRenewalPage.yesButton.click();
			}
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.scrollToElement();
				policyRenewalPage.continueRenewal.click();
			}

			// Getting the Renewal Quote Number
			quoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number :  " + quoteNumber);

			// Adding IO-21583
			// Calculating sltf value by adding (Premium+ICATfees+OtherFees)*SLTF percent
			String premiumAmount = accountOverviewPage.premiumValue.getData().replace("$", "").replace(",", "");
			String icatFees = accountOverviewPage.feesValue.getData().replace("$", "").replace(",", "");
			String actualSLTFValue = accountOverviewPage.sltfValue.getData().replace("$", "").replace(",", "");
			String actualSurpluscontribution = accountOverviewPage.surplusContributionValue.getData().replace("$", "")
					.replace(",", "");
			String otherFees = accountOverviewPage.otherFees.getData().replace("$", "").replace(",", "");
			String stampingFeePercent = testData.get("StampingPercentage");
			String sltfPercent = testData.get("SurplusLinesTaxesPercentage");

			// Calculating sltf value
			double a_calStaming = (Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(actualSurpluscontribution) + Double.parseDouble(otherFees))
					* Double.parseDouble(stampingFeePercent);

			double a_calSLTF = ((Double.parseDouble(premiumAmount) + Double.parseDouble(icatFees)
					+ Double.parseDouble(actualSurpluscontribution) + Double.parseDouble(otherFees))
					* Double.parseDouble(sltfPercent)) + a_calStaming;

			// Verifying actual and calculated STFL
			Assertions.addInfo("Scenario 01",
					"Verifying the actual and calculated SLTF on account overview page 4.85%");
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualSLTFValue), 2) - Precision.round(a_calSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("Account overview page ", "Actual SLTF value :" + "$" + actualSLTFValue);
				Assertions.passTest("Account overview page", "Calculated SLTF Vaule :" + "$" + df.format(a_calSLTF));
			} else {
				Assertions.verify(actualSLTFValue, a_calSLTF, "Account overview page",
						"The Difference between actual  and calculated SLTF value is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			// Clicking on view/Print full quote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/Print full quote link");

			// Fetching original data for named hurricane
			String originalValue = quoteDetailsPage.deductibleData.formatDynamicPath("Named Hurricane Deductible")
					.getData();

			// Getting values from view print full quote page
			String actualPremiumValue = viewOrPrintFullQuotePage.premiumValue.getData().replace("$", "").replace(",",
					"");
			String actualPolicyFee = viewOrPrintFullQuotePage.policyFee.getData().replace("$", "").replace(",", "");
			String actualSurplusContributionValue = viewOrPrintFullQuotePage.surplusContributionValue.getData()
					.replace("$", "").replace(",", "");
			String actualSLTF = viewOrPrintFullQuotePage.surplusLinesTaxesValue.getData().replace("$", "").replace(",",
					"");
			String actualStampingFee = viewOrPrintFullQuotePage.stampingFeeValue.getData().replace("$", "").replace(",",
					"");

			// Calculating stamping fee = (premium+ policy fee+surplus
			// contribution)*stamping percent(0.04=.0004)
			double calStampingFee = (Double.parseDouble(actualPremiumValue) + Double.parseDouble(actualPolicyFee)
					+ Double.parseDouble(actualSurplusContributionValue) + Double.parseDouble(otherFees))
					* Double.parseDouble(stampingFeePercent);

			// Calculating SLTF = (premium+ policy fee+surplus contribution+stamping
			// fee)*SLTF Percent(4.85%)
			double calSLTF = (Double.parseDouble(actualPremiumValue) + Double.parseDouble(actualPolicyFee)
					+ Double.parseDouble(actualSurplusContributionValue) + Double.parseDouble(otherFees))
					* Double.parseDouble(sltfPercent);

			// Verifying actual and calculated stamping fee
			Assertions.addInfo("Scenario 02",
					"Verifying the actual and calculated staming fee on view print full qoute page 0.04%");
			if (Precision.round(Math.abs(
					Precision.round(Double.parseDouble(actualStampingFee), 2) - Precision.round(calStampingFee, 2)),
					2) < 0.05) {
				Assertions.passTest("View print full quote page", "Actual Stamping fee :" + "$" + actualStampingFee);
				Assertions.passTest("View print full quote page",
						"Calculated Stamping fee :" + "$" + df.format(calStampingFee));
			} else {
				Assertions.verify(actualStampingFee, calStampingFee, "View print full quote page",
						"The Difference between actual  and calculated stamping fee is more than 0.05", false, false);

			}
			Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

			// Verifying actual and calculated STFL
			Assertions.addInfo("Scenario 03",
					"Verifying the actual and calculated SLTF on view print full qoute page 4.85%");
			if (Precision.round(
					Math.abs(Precision.round(Double.parseDouble(actualSLTF), 2) - Precision.round(calSLTF, 2)),
					2) < 0.05) {
				Assertions.passTest("View print full quote page", "Actual SLTF value :" + "$" + actualSLTF);
				Assertions.passTest("View print full quote page", "Calculated SLTF Vaule :" + "$" + df.format(calSLTF));
			} else {
				Assertions.verify(actualSLTF, calSLTF, "View print full quote page",
						"The Difference between actual  and calculated SLTF value is more than 0.05", false, false);

			}

			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");
			// IO-21583 Ended

			// Clicking on go Back button
			quoteDetailsPage.goBackButton.scrollToElement();
			quoteDetailsPage.goBackButton.click();

			// Clicking on view previous policy
			accountOverviewPage.viewPreviousPolicyButton.scrollToElement();
			accountOverviewPage.viewPreviousPolicyButton.click();

			// Clicking on Endorse PB

			policySummarypage.endorsePB.waitTillVisibilityOfElement(60);
			policySummarypage.endorsePB.scrollToElement();
			policySummarypage.endorsePB.click();

			// Clicking on ok button
			endorsePolicyPage.okButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.okButton.scrollToElement();
			endorsePolicyPage.okButton.click();

			// setting Endoresment Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// clicking on change coverage options hyperlink
			endorsePolicyPage.changeCoverageOptionsLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeCoverageOptionsLink.scrollToElement();
			endorsePolicyPage.changeCoverageOptionsLink.click();
			endorsePolicyPage.loading.waitTillInVisibilityOfElement(60);

			// modifying deductibles and coverages in create quote page
			Assertions.verify(createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed(), true,
					"Create Quote Page", "Create Quote Page loaded successfully", false, false);
			testData = data.get(data_Value2);
			createQuotePage.editDeductiblesCommercialPNB(testData);
			Assertions.passTest("Create Quote Page", "Deductibles and coverages details modified successfully");
			if (createQuotePage.continueEndorsementButton.checkIfElementIsPresent()
					&& createQuotePage.continueEndorsementButton.checkIfElementIsDisplayed()) {
				createQuotePage.scrollToBottomPage();
				createQuotePage.continueEndorsementButton.scrollToElement();
				createQuotePage.continueEndorsementButton.click();
			}

			// clicking on next button in endorse policy page
			endorsePolicyPage.nextButton.scrollToElement();
			endorsePolicyPage.nextButton.click();

			// clicking on complete button in endorse policy page
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();

			// Validating Roll Forward button display
			Assertions.addInfo("Scenario 04", "Verifying the presence of Roll Forward Button");
			endorsePolicyPage.rollForwardBtn.waitTillVisibilityOfElement(60);
			Assertions.verify(endorsePolicyPage.rollForwardBtn.checkIfElementIsDisplayed(), true, "EndorsePolicy Page",
					"Roll Forward the endorsement button is displayed", false, false);
			Assertions.passTest("Scenario 04", "Scenario 04 Ended");

			// Click on roll forward button
			endorsePolicyPage.rollForwardBtn.scrollToElement();
			endorsePolicyPage.rollForwardBtn.click();
			Assertions.passTest("Endorse policy page", "Clicked on roll forwar button successfully");

			// clicking on close button in endorse policy page
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// Clicking on view active renewal link
			policySummarypage.viewActiveRenewal.scrollToElement();
			policySummarypage.viewActiveRenewal.click();

			// Fetching new quote No
			String quoteNumber1 = accountOverviewPage.quoteNumber.getData().substring(1, 11);

			// Validating deletion of previous Renewal quote
			Assertions.verify(accountOverviewPage.quoteNumber.getData().contains(quoteNumber), false,
					"Account Overview Page", "Previous Renewal Quote : " + quoteNumber + " is deleted successfully",
					false, false);

			// New renewal quote no
			Assertions.verify(accountOverviewPage.quoteNumber.getData().contains(quoteNumber1), true,
					"Account Overview Page", "New Renewal Quote No : " + quoteNumber1, false, false);

			// Clicking on view/Print full quote
			accountOverviewPage.viewPrintFullQuoteLink.waitTillVisibilityOfElement(60);
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View/Print Full Quote link");

			// Verification of applied changes in endorsement
			Assertions.addInfo("Scenario 05", "Asserting Named Hurricane Value Before and After the Endorsement");
			testData = data.get(data_Value1);
			Assertions.verify(
					quoteDetailsPage.deductibleData.formatDynamicPath("Named Hurricane Deductible")
							.checkIfElementIsDisplayed(),
					true, "Quote Details Page", "Named Hurricane original value : " + originalValue, false, false);
			testData = data.get(data_Value2);
			Assertions.verify(
					quoteDetailsPage.deductibleData.formatDynamicPath("Named Hurricane Deductible").getData()
							.contains(testData.get("DeductibleValue")),
					true, "Quote Details Page",
					"Named Hurricane value after endorsement : "
							+ quoteDetailsPage.deductibleData.formatDynamicPath("Named Hurricane Deductible").getData(),
					false, false);
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Clicking on go Back button
			quoteDetailsPage.goBackButton.scrollToElement();
			quoteDetailsPage.goBackButton.click();

			// Clicking on release to producer button
			accountOverviewPage.releaseRenewalToProducerButton.waitTillVisibilityOfElement(60);
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer");

			// Clicking on request bind button
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber);
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// entering details in request bind page
			Assertions.verify(requestBindPage.submit.checkIfElementIsDisplayed(), true, "Request Bind Page",
					"Request Bind Page loaded successfully", false, false);
			requestBindPage.contactSurplusLicenseNumber.scrollToElement();
			requestBindPage.contactSurplusLicenseNumber.clearData();
			testData = data.get(data_Value1);
			requestBindPage.contactSurplusLicenseNumber.setData(testData.get("SurplusLicenceNumber"));
			requestBindPage.submit.scrollToElement();
			requestBindPage.submit.click();
			requestBindPage.confirmBind();
			Assertions.passTest("Request Bind Page", "Bind details entered successfully");

			// clicking on home button
			if (bindRequestPage.homePage.checkIfElementIsPresent()
					&& bindRequestPage.homePage.checkIfElementIsDisplayed()) {
				Assertions.verify(bindRequestPage.homePage.checkIfElementIsDisplayed(), true, "Bind Request Page",
						"Bind Request Page loaded successfully", false, false);
				bindRequestPage.clickOnHomepagebutton();
				Assertions.passTest("Home Page", "Clicked on Home button");

				// searching the quote number in grid and clicking on the quote link
				Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
						"Home page loaded successfully", false, false);
				homePage.searchQuote(quoteNumber1);
				Assertions.passTest("Home Page", "Quote opened successfully");

				// click on open referral link
				accountOverviewPage.openReferralLink.scrollToElement();
				accountOverviewPage.openReferralLink.click();
				Assertions.passTest("Account Overview Page", "Clicked on Open Referral link");

				// approving referral
				Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
						"Referral page loaded successfully", false, false);
				referralPage.clickOnApprove();
				Assertions.passTest("Referral Page", "Referral request approved successfully");

				requestBindPage.approveRequest();
				Assertions.passTest("Request Bind Page", "Bind Request approved successfully");
			}

			// Get policy number from policy summary page
			Assertions.passTest("Policy Summary Page", "Policy Summary Page loaded successfully");
			policyNumber = policySummarypage.getPolicynumber();
			Assertions.verify(policySummarypage.policyNumber.getData().contains(policyNumber), true,
					"Policy Summary Page", "Policy Number is " + policyNumber, false, false);

			// Signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 71", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 71", "Executed Successfully");
			}
		}
	}
}
