/** Program Description: Perform NPB Endorsement to changeNamed Insured,Insured Address and Inspection contact Deatils.Assert the Warning message when Business sold is Yes.
 *  Author			   : John
 *  Date of Creation   : 08/03/2020
 **/
package com.icat.epicenter.test.commercial.wholesaleRegression;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BindRequestSubmittedPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.ChangeNamedInsuredPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.EndorseInspectionContactPage;
import com.icat.epicenter.pom.EndorsePolicyPage;
import com.icat.epicenter.pom.EndorseProducerContact;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC089 extends AbstractCommercialTest {

	public TC089() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/wholesaleRegression/NBTCID089.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		// Initializing the Pages
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
		PriorLossesPage priorLossPage = new PriorLossesPage();
		EndorsePolicyPage endorsePolicyPage = new EndorsePolicyPage();
		ChangeNamedInsuredPage namedInsuredpage = new ChangeNamedInsuredPage();
		EndorseInspectionContactPage endorseInspectionContactPage = new EndorseInspectionContactPage();
		EndorseProducerContact endorseProducerContact = new EndorseProducerContact();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();

		// Initializing the variables
		String quoteNumber;
		String policyNumber;
		int data_Value1 = 0;
		Map<String, String> testData = data.get(data_Value1);
		int data_Value2 = 1;
		Map<String, String> testData1 = data.get(data_Value2);
		int data_Value3 = 2;
		Map<String, String> testData2 = data.get(data_Value3);
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

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			// getting the quote number
			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);
			quoteNumber = accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 11);
			Assertions.passTest("Account Overview Page", "Quote Number :  " + quoteNumber);

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

			// initiate NPB endorsement
			Assertions.addInfo("Policy Summary Page",
					"Initiating Endorsement to change the Insured Name and Insured Address");
			policySummarypage.endorseNPB.scrollToElement();
			policySummarypage.endorseNPB.click();

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Click on change named insured
			endorsePolicyPage.changeNamedInsuredLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeNamedInsuredLink.scrollToElement();
			endorsePolicyPage.changeNamedInsuredLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Named Insured link");

			// Change insured name
			namedInsuredpage.namedInsured.scrollToElement();
			Assertions.passTest("Change Named Insured Page",
					"Named Insured original Value : " + namedInsuredpage.namedInsured.getData());
			namedInsuredpage.namedInsured.setData(testData1.get("InsuredName"));
			Assertions.passTest("Change Named Insured Page",
					"Named Insured Latest Value : " + namedInsuredpage.namedInsured.getData());

			// Change entered named insured
			namedInsuredpage.extendedNamedInsured.scrollToElement();
			Assertions.passTest("Change Named Insured Page",
					"Extended Named Insured original Value : " + namedInsuredpage.extendedNamedInsured.getData());
			namedInsuredpage.extendedNamedInsured.setData(testData1.get("ExtendedNamedInsured"));
			Assertions.passTest("Change Named Insured Page",
					"Extended Named Insured Latest Value : " + namedInsuredpage.extendedNamedInsured.getData());

			// Change insured address
			if (namedInsuredpage.insuredCountry.checkIfElementIsPresent()) {
				namedInsuredpage.insuredCountry.scrollToElement();
				Assertions.addInfo("Change Named Insured Page",
						"Insured Country original Value : " + namedInsuredpage.insuredCountry.getData());
				WebElement ele = WebDriverManager.getCurrentDriver()
						.findElement(By.xpath("//div[div[contains(text(),'Country')]]//input"));
				ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), testData1.get("InsuredCountry"));
				Assertions.passTest("Change Named Insured Page",
						"Insured Country Latest Value : " + namedInsuredpage.insuredCountry.getData());
				namedInsuredpage.insuredCountry.tab();
			} else if (namedInsuredpage.insuredCountrySelect.checkIfElementIsPresent()) {
				namedInsuredpage.insuredCountrySelect.scrollToElement();
				Assertions.addInfo("Change Named Insured Page",
						"Insured Country original Value : " + namedInsuredpage.insuredCountrySelect.getData());
				namedInsuredpage.insuredCountrySelect.selectByVisibleText(testData1.get("InsuredCountry"));
				Assertions.passTest("Change Named Insured Page",
						"Insured Country Latest Value : " + namedInsuredpage.insuredCountrySelect.getData());
				namedInsuredpage.insuredCountrySelect.tab();
			}
			namedInsuredpage.waitTime(3);// need wait time to load the page
			if (namedInsuredpage.enterAddressManuallLink.checkIfElementIsDisplayed()) {
				namedInsuredpage.enterAddressManuallLink.waitTillVisibilityOfElement(60);
				namedInsuredpage.enterAddressManuallLink.scrollToElement();
				namedInsuredpage.enterAddressManuallLink.click();
			}
			namedInsuredpage.addressLine1.waitTillVisibilityOfElement(60);
			namedInsuredpage.addressLine1.scrollToElement();
			Assertions.passTest("Change Named Insured Page",
					"Insured Address1 original Value : " + namedInsuredpage.addressLine1.getData());
			namedInsuredpage.addressLine1.clearData();
			namedInsuredpage.addressLine1.appendData(testData1.get("InsuredAddr1"));
			Assertions.passTest("Change Named Insured Page",
					"Insured Address1 Latest Value : " + namedInsuredpage.addressLine1.getData());
			namedInsuredpage.addressLine2.waitTillVisibilityOfElement(60);
			namedInsuredpage.addressLine2.scrollToElement();
			namedInsuredpage.addressLine2.setData(testData1.get("InsuredAddr2"));
			namedInsuredpage.insuredCity.scrollToElement();
			Assertions.passTest("Change Named Insured Page",
					"Insured City original Value : " + namedInsuredpage.insuredCity.getData());
			namedInsuredpage.insuredCity.setData(testData1.get("InsuredCity"));
			Assertions.passTest("Change Named Insured Page",
					"Insured City Latest Value : " + namedInsuredpage.insuredCity.getData());
			namedInsuredpage.insuredState.scrollToElement();
			Assertions.passTest("Change Named Insured Page",
					"Insured State original Value : " + namedInsuredpage.insuredState.getData());
			namedInsuredpage.insuredState.setData(testData1.get("InsuredState"));
			Assertions.passTest("Change Named Insured Page",
					"Insured State Latest Value : " + namedInsuredpage.insuredState.getData());
			Assertions.addInfo("Change Named Insured Page",
					"Insured zipcode original Value : " + namedInsuredpage.zipCode.getData());
			namedInsuredpage.zipCode.setData(testData1.get("InsuredZIP"));
			namedInsuredpage.zipCode.tab();
			Assertions.passTest("Change Named Insured Page",
					"Insured zipcode Latest Value : " + namedInsuredpage.zipCode.getData());
			namedInsuredpage.okButton.scrollToElement();
			namedInsuredpage.okButton.click();

			// select yes for business sold
			Assertions.addInfo("Change Named Insured Page", "Select Yes after Name Change for Business sold");
			namedInsuredpage.yes_NameChange.scrollToElement();
			namedInsuredpage.yes_NameChange.click();
			Assertions.passTest("Change Named Insured Page", "Clicked on Yes for Business sold");
			Assertions.addInfo("Change Named Insured Page",
					"Assert the Warning Message when Yes after Name Change for Business sold");
			Assertions.verify(namedInsuredpage.okBtnNameChange_Yes.checkIfElementIsDisplayed(), true,
					"Change Named Insured Page", namedInsuredpage.warningMsgNameChange_Yes.getData() + " is displayed",
					false, false);
			namedInsuredpage.okBtnNameChange_Yes.scrollToElement();
			namedInsuredpage.okBtnNameChange_Yes.click();

			// Assert endorsement deleted message and Policy status
			Assertions.addInfo("Policy Summary Page",
					"Assert the Policy Status and Endorsement Deleted Warning Message");
			Assertions.verify(policySummarypage.policyStatus.getData().contains("Active"), true, "Policy Summary Page",
					"Policy Summary Page is displayed", false, false);
			Assertions.verify(policySummarypage.endorsementDeletedWarningMsg.checkIfElementIsDisplayed(), true,
					"Change Named Insured Page",
					policySummarypage.endorsementDeletedWarningMsg.getData() + " message is displayed", false, false);

			// initiate NPB endorsement
			Assertions.addInfo("Policy Summary Page",
					"Initiating Endorsement to change the Inspection Contact Details");
			policySummarypage.endorseNPB.scrollToElement();
			policySummarypage.endorseNPB.click();

			// setting Endorsement Effective Date
			Assertions.verify(endorsePolicyPage.endorsementEffDate.checkIfElementIsDisplayed(), true,
					"Endorse Policy Page", "Endorse Policy page loaded successfully", false, false);
			endorsePolicyPage.endorsementEffDate.scrollToElement();
			endorsePolicyPage.endorsementEffDate.setData(testData.get("TransactionEffectiveDate"));
			endorsePolicyPage.endorsementEffDate.tab();
			Assertions.passTest("Endorse Policy Page", "Entered Transaction Effective Date");

			// Click on change inspection contact
			endorsePolicyPage.changeInspectionContactLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.changeInspectionContactLink.scrollToElement();
			endorsePolicyPage.changeInspectionContactLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Change Inspection contact link");

			// Update inspection contact
			endorseInspectionContactPage.enterInspectionContactPB(testData2);

			// Click on producer contact link
			endorsePolicyPage.producerContactLink.waitTillVisibilityOfElement(60);
			endorsePolicyPage.producerContactLink.scrollToElement();
			endorsePolicyPage.producerContactLink.click();
			Assertions.passTest("Endorse Policy Page", "Clicked on Producer contact link");

			// Update producer contact
			endorseProducerContact.enterEndorseProducerContactDetails(testData2);

			// Complete endorsement
			endorsePolicyPage.completeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.completeButton.scrollToElement();
			endorsePolicyPage.completeButton.click();
			endorsePolicyPage.scrollToTopPage();
			endorsePolicyPage.closeButton.waitTillVisibilityOfElement(60);
			endorsePolicyPage.closeButton.scrollToElement();
			endorsePolicyPage.closeButton.click();

			// verifying NPB endorsement record in policy summary page
			Assertions.addInfo("Policy Summary Page", "Verifying NPB endorsement record in policy summary page");
			Assertions.verify(
					policySummarypage.transactionType.formatDynamicPath(testData.get("TransactionType")).getData(),
					testData.get("TransactionType"), "Policy Summary Page", "NPB Endorsement Transaction is Recorded",
					false, false);

			// verifying producer contact name and email on policy summary page
			Assertions.addInfo("Policy Summary Page",
					"Verifying producer contact name and email on policy summary page");
			Assertions.verify(policySummarypage.producerContactName.getData(), testData2.get("ProducerName"),
					"Policy Summary Page",
					"Producer contact name is updated to " + policySummarypage.producerContactName.getData(), false,
					false);
			Assertions.verify(policySummarypage.producerContactEmail.getData(), testData2.get("ProducerEmail"),
					"Policy Summary Page",
					"Producer contact email is updated to " + policySummarypage.producerContactEmail.getData(), false,
					false);

			// Click on policy snapshot
			policySummarypage.viewPolicySnapshot.waitTillVisibilityOfElement(60);
			policySummarypage.viewPolicySnapshot.scrollToElement();
			policySummarypage.viewPolicySnapshot.click();
			Assertions.passTest("Policy Summary Page", "Clicked on View Policy Snapshot link");

			// Asserting Inspection Contact
			Assertions.addInfo("View Policy Snapshot Page",
					"Asserting Inspection Contact Details after Endorsement on Policy snapshot page");
			Assertions.verify(viewPolicySnapShot.policyDeductiblesValues.formatDynamicPath("Inspection", "1").getData(),
					testData2.get("InspectionContact") + " " + "(" + testData2.get("InspectionAreaCode") + "."
							+ testData2.get("InspectionPrefix") + "." + testData2.get("InspectionNumber") + ")",
					"View Policy Snapshot Page",
					"Inspection contact "
							+ viewPolicySnapShot.policyDeductiblesValues.formatDynamicPath("Inspection", "1").getData()
							+ " is updated in poliy snapshot page",
					false, false);

			// signout
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial NBRegression Test Case 89", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial NBRegression Test Case 89", "Executed Successfully");
			}
		}
	}
}
