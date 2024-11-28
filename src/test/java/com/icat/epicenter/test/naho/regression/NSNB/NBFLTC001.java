/** Program Description: To Verify the updated risk question in Eligibility Page,Questions in Prior Loss page,minimum Coverage A of $500,000,default named storm deductible and premium override absence for Sinkhole/CGCC for Florida(Tricountry) and IO-21540
 *  Author			   : Yeshashwini
 *  Date of Creation   : 03/25/2021
 **/
package com.icat.epicenter.test.naho.regression.NSNB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.DropDownControl;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.ApproveDeclineQuotePage;
import com.icat.epicenter.pom.BuildingNoLongerQuoteablePage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.DwellingPage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.OverridePremiumAndFeesPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.UnderwritingQuestionsPage;
import com.icat.epicenter.pom.ViewOrPrintFullQuotePage;
import com.icat.epicenter.pom.ViewOrPrintRateTrace;
import com.icat.epicenter.pom.ViewPolicySnapShot;
import com.icat.epicenter.test.naho.AbstractNAHOTest;

public class NBFLTC001 extends AbstractNAHOTest {

	public NBFLTC001() {
		super(LoginType.NAHOUSM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/naho/regression/NSNB/FL001.xls";
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
		ViewOrPrintRateTrace viewOrPrintRateTrace = new ViewOrPrintRateTrace();
		OverridePremiumAndFeesPage overridePremiumAndFeesPage = new OverridePremiumAndFeesPage();
		UnderwritingQuestionsPage underwritingQuestionsPage = new UnderwritingQuestionsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		ReferralPage referralPage = new ReferralPage();
		ApproveDeclineQuotePage approveDeclineQuotePage = new ApproveDeclineQuotePage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		ViewPolicySnapShot viewPolicySnapShot = new ViewPolicySnapShot();
		ViewOrPrintFullQuotePage viewprFullQuotePage = new ViewOrPrintFullQuotePage();
		BuildingNoLongerQuoteablePage  buildingNoLongerQuoteablePage = new BuildingNoLongerQuoteablePage();

		Map<String, String> testData;
		int dataValue1 = 0;
		int dataValue3 = 2;
		int dataValue4 = 3;
		int dataValue10 = 9;
		testData = data.get(dataValue1);
		String beforePremiumOverride;
		String namedStorm = "5%";
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

			// verify the question Is the home value less than $150,000 changed to Is the
			// home value less than $150,000 ($250,000 in FL)?
			Assertions.addInfo("Scenario 01", "Verify the updated risk question in Eligibility Page");
			Assertions.verify(
					eligibilityPage.riskQuestions
							.formatDynamicPath("replacement cost less than").checkIfElementIsDisplayed(),
					true, "Eligibility Page",
					"Risk Question updated to "
							+ eligibilityPage.riskQuestions.formatDynamicPath("replacement cost less than").getData()
							+ " is verified",
					false, false);

			if (!testData.get("ZipCode").equals("")) {
				eligibilityPage.zipCode1.setData(testData.get("ZipCode"));
				eligibilityPage.riskAppliedYes.waitTillElementisEnabled(60);
				Assertions.passTest("Eligibility", "Zip code is " + eligibilityPage.zipCode1.getData());
			}
			Assertions.verify(eligibilityPage.zipCodeClosedMsg.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Hardstop for Monroe country is verified", false, false);

			eligibilityPage.refreshPage();
			testData = data.get(dataValue3);
			eligibilityPage.processSingleZip(testData);
			Assertions.addInfo("Scenario 01", "Scenario 01 Ended");

			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location 1 Dwelling 1 Details
			Assertions.verify(dwellingPage.reviewDwelling.checkIfElementIsDisplayed(), true, "Dwelling Page",
					"Dwelling Page loaded successfully", false, false);
			dwellingPage.enterDwellingDetailsNAHO(testData);
			Assertions.passTest("Dwelling Page", "Dwelling details entered successfully");

			// Entering prior loss details
			if (!testData.get("PriorLoss1").equals("")) {
				Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
						"Prior Loss Page loaded successfully", false, false);

				// verifying the presence of questions for FL
				Assertions.addInfo("Scenario 02", "Verifying the presence of Prior Loss questions");
				Assertions.verify(priorLossesPage.priorLossQuestions
						.formatDynamicPath("one or more losses in the last 3 years").checkIfElementIsDisplayed(), true,
						"Prior Loss Page",
						"The Question "
								+ priorLossesPage.priorLossQuestions
										.formatDynamicPath("one or more losses in the last 3 years").getData()
								+ "  present is verified",
						false, false);

				Assertions.verify(
						priorLossesPage.priorLossQuestions.formatDynamicPath("Sinkhole").checkIfElementIsDisplayed(),
						true, "Prior Loss Page",
						"The Question " + priorLossesPage.priorLossQuestions.formatDynamicPath("Sinkhole").getData()
								+ " present is verified",
						false, false);
				Assertions.addInfo("Scenario 02", "Scenario 02 Ended");

				priorLossesPage.selectPriorLossesInformation(testData);
				Assertions.passTest("Prior Loss Page", "Prior Loss details entered successfully");
			}

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);

			// Ticket 20810
			// Verifying A minimum Named Storm deductible of 5% will be set for all risks
			// located in Tri County microzone, the minimum named storm dedcutible
			Assertions.addInfo("Scenario 03",
					"Verify Named Storm Deductible and AOWH deductible minimum value and Sinkhole/CGCC are available");
			Assertions.verify(createQuotePage.namedStormData.getData(), namedStorm, "Create Quote Page",
					"A minimum Named Storm deductible of 5% will be set for all risks located in Tri County microzone, the minimum named storm dedcutible "
							+ createQuotePage.namedStormData.getData() + " is displayed verified",
					false, false);

			Assertions.verify(createQuotePage.aowhDeductibleArrow.checkIfElementIsEnabled(), true, "Create Quote Page",
					"For USM AOWH deductible is selectable is verified", false, false);

			createQuotePage.sinkholeArrow.scrollToElement();
			createQuotePage.sinkholeArrow.click();

			Assertions.verify(createQuotePage.sinkholeArrow.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"When we add prior loss with sinkhole and  other damages Sinkhole /CGCC coverage available for USM is verified",
					false, false);

			// verifying the presence of sinkhole and values in the dropdown
			DropDownControl sinkholeOptions = new DropDownControl(By.xpath("//select[contains(@id,'sinkhole')]"));
			List<String> optionsApp = sinkholeOptions.getAllOptions();
			ArrayList<String> optionsExpected = new ArrayList<>(3);
			optionsExpected.add("None");
			optionsExpected.add("Sinkhole");
			optionsExpected.add("Catastrophic Ground Cover Collapse");

			for (int i = 0; i < optionsApp.size(); i++) {
				BaseWebElementControl opt = new BaseWebElementControl(By.xpath(
						"(//select[contains(@id,'sinkhole')]/..//span[contains(@id,'sinkholeSelectBoxItContainer')]//a)["
								+ (i + 1) + "]"));
				Assertions.verify(opt.getData(), optionsExpected.get(i), "Create Quote Page",
						"Sinkhole value " + opt.getData() + " present in dropdown is verified", false, false);
			}

			createQuotePage.scrollToBottomPage();
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");
			Assertions.addInfo("Scenario 03", "Scenario 03 Ended");

			// IO-21540
			// As usm Asserting and validating Warning message when year built 21-25 years
			// and
			// roof cladding='Hurricane Shingle'
			// 'Normal Shingle','Steel or Metal','Other', and
			// 'Architectural Shingle'
			// Warning message is = The quoted building has a roof age outside of our
			// guidelines. For consideration, please provide your ICAT Online Underwriter
			// with additional information regarding the condition of the roof, such as a
			// recent inspection
			Assertions.addInfo("Scenario 04", "Change Roof Year and Assert Roof Age Message");
			for (int i = 0; i <= 8; i++) {
				testData = data.get(i);
				createQuotePage.goBack.waitTillVisibilityOfElement(60);
				createQuotePage.goBack.scrollToElement();
				createQuotePage.goBack.click();
				createQuotePage.previous.waitTillVisibilityOfElement(60);
				createQuotePage.previous.scrollToElement();
				createQuotePage.previous.click();

				// Edit dwelling and update Year built and roof cladding
				accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
				accountOverviewPage.editDwelling.scrollToElement();
				accountOverviewPage.editDwelling.click();

				// Entering Location 1 Dwelling 1 Details
				dwellingPage.enterDwellingDetailsNAHO(testData);
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();

				// Asserting Alert message for USM
				if (createQuotePage.roofAgeAlertMessage.checkIfElementIsPresent()
						&& createQuotePage.roofAgeAlertMessage.checkIfElementIsDisplayed()) {
					Assertions.verify(
							createQuotePage.roofAgeAlertMessage.getData()
									.contains("The quoted building has a roof age outside of our guidelines"),
							true, "Create Quote Page",
							"Warning message is " + createQuotePage.roofAgeAlertMessage.getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
							false, false);
				}

				// Asserting and verifying when year built = 21-25 and roof cladding = Built Up,
				// Single Ply Membrane, Tile or Clay, Wood Shakes or Wood Shingles
				// warning message = The account is ineligible due to the roof age being
				// outside of ICAT's guidelines. If you have additional information that adjusts
				// the roof age, please email it to your ICAT Online Underwriter. as usm
				if (createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
						.checkIfElementIsPresent()
						&& createQuotePage.warningMessages
								.formatDynamicPath("The account is ineligible due to the roof age")
								.checkIfElementIsDisplayed()) {

					Assertions.verify(
							createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
									.contains("The account is ineligible due to the roof age"),
							true, "Create Quote Page",
							"Warning message is  " + createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
							false, false);
				}

			}
			Assertions.addInfo("Scenario 04", "Scenario 04 Ended");

			// Asserting warning message when year built 16-20 years and roof
			// cladding='Hurricane Shingle'
			// 'Normal Shingle','Steel or Metal','Built Up','Single Ply Membrane' and
			// 'Architectural Shingle'
			// Warning message is = Roof Excluded From Replacement Cost and Covered Only at
			// Actual Cash Value.
			Assertions.addInfo("Scenario 05",
					"Asserting and Validating warning message when roof age = 16-20 years and roof cladding");
			for (int i = 9; i <= 17; i++) {
				testData = data.get(i);
				createQuotePage.goBack.waitTillVisibilityOfElement(60);
				createQuotePage.goBack.scrollToElement();
				createQuotePage.goBack.click();
				createQuotePage.previous.waitTillVisibilityOfElement(60);
				createQuotePage.previous.scrollToElement();
				createQuotePage.previous.click();

				// Edit dwelling and update Year built and roof cladding
				accountOverviewPage.editDwelling.scrollToElement();
				accountOverviewPage.editDwelling.click();

				// Entering Location 1 Dwelling 1 Details
				dwellingPage.yearBuilt.scrollToElement();
				dwellingPage.yearBuilt.clearData();
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.tab();
				dwellingPage.addRoofDetails(testData, 1, 1);
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

				// Review dwelling and continue
				dwellingPage.waitTime(2);
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();

				if(buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()&&buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed())
				{
					buildingNoLongerQuoteablePage.override.scrollToElement();
					buildingNoLongerQuoteablePage.override.click();
				}

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
				if (createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value")
						.checkIfElementIsPresent()
						&& createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value")
								.checkIfElementIsDisplayed()) {
					createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value")
							.waitTillPresenceOfElement(60);
					Assertions.verify(
							createQuotePage.errorMessageWarningPage
									.formatDynamicPath("Actual Cash Value").getData().contains("Actual Cash Value"),
							true, "Create Quote Page",
							"Warning message is "
									+ createQuotePage.errorMessageWarningPage.formatDynamicPath("Actual Cash Value")
											.getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
							false, false);
				}

				// roof cladding = 'Tile or Clay' and year built 16-20, warning message is =
				// 'Roof Coverage Excluded'
				if (createQuotePage.errorMessageWarningPage.formatDynamicPath("Roof Coverage Excluded")
						.checkIfElementIsPresent()
						&& createQuotePage.errorMessageWarningPage.formatDynamicPath("Roof Coverage Excluded")
								.checkIfElementIsDisplayed()) {
					Assertions.verify(createQuotePage.errorMessageWarningPage
							.formatDynamicPath("Roof Coverage Excluded").getData().contains("Roof Coverage Excluded"),
							true, "Create Quote Page",
							"Warning message is "
									+ createQuotePage.errorMessageWarningPage
											.formatDynamicPath("Roof Coverage Excluded").getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
							false, false);
				}

				// Asserting warning message when year built 16-20 years and roof
				// cladding= other. the message is 'The quoted building has a roof age outside
				// of
				// our guidelines. For consideration, please provide your ICAT Online
				// Underwriter with additional information regarding the condition of the roof,
				// such as a recent inspection
				if (createQuotePage.roofAgeAlertMessage.checkIfElementIsPresent()
						&& createQuotePage.roofAgeAlertMessage.checkIfElementIsDisplayed()) {
					Assertions.verify(
							createQuotePage.roofAgeAlertMessage.getData()
									.contains("The quoted building has a roof age outside of our guidelines"),
							true, "Create Quote Page",
							"Warning message is " + createQuotePage.roofAgeAlertMessage.getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
							false, false);
				}

				// Asserting warning message when year built 16-20 years and roof cladding =Wood
				// Shakes or Wood Shingles.warning message 'The account is ineligible due to the
				// roof age being outside of ICAT's guidelines. If you have additional
				// information that adjusts the roof age, please email it to your ICAT Online
				// Underwriter.
				if (createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
						.checkIfElementIsPresent()
						&& createQuotePage.warningMessages
								.formatDynamicPath("The account is ineligible due to the roof age")
								.checkIfElementIsDisplayed()) {
					Assertions.verify(
							createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
									.contains("The account is ineligible due to the roof age"),
							true, "Create Quote Page",
							"Warning message is " + createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
							false, false);
				}

			}
			Assertions.addInfo("Scenario 05", "Scenario 05 Ended");

			// Asserting warning message when year built 26+ years and roof
			// cladding='Hurricane Shingle'
			// 'Normal Shingle','Steel or Metal','Built Up','Single Ply Membrane','Wood
			// Shakes or Wood Shingles','Other and
			// 'Architectural Shingle'
			// Warning message is = The account is ineligible due to the roof age being
			// outside of ICAT's guidelines. If you have additional information that adjusts
			// the roof age, please email it to your ICAT Online Underwriter.
			Assertions.addInfo("Scenario 06",
					"Asserting and Validating warning message when roof age = 26+ years and roof cladding");
			for (int i = 18; i <= 26; i++) {
				testData = data.get(i);
				createQuotePage.goBack.waitTillVisibilityOfElement(60);
				createQuotePage.goBack.scrollToElement();
				createQuotePage.goBack.click();
				createQuotePage.previous.waitTillVisibilityOfElement(60);
				createQuotePage.previous.scrollToElement();
				createQuotePage.previous.click();

				// Edit dwelling and update Year built and roof cladding
				accountOverviewPage.editDwelling.scrollToElement();
				accountOverviewPage.editDwelling.click();

				// Entering Location 1 Dwelling 1 Details
				dwellingPage.yearBuilt.scrollToElement();
				dwellingPage.yearBuilt.clearData();
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.setData(testData.get("L1D1-DwellingYearBuilt"));
				if (dwellingPage.expiredQuotePopUp.checkIfElementIsPresent()) {
					dwellingPage.expiredQuotePopUp.scrollToElement();
					dwellingPage.continueWithUpdateBtn.scrollToElement();
					dwellingPage.continueWithUpdateBtn.click();
					dwellingPage.continueWithUpdateBtn.waitTillInVisibilityOfElement(60);
				}
				dwellingPage.yearBuilt.tab();
				dwellingPage.addRoofDetails(testData, 1, 1);
				Assertions.passTest("Dwelling Page",
						"Roof Cladding is updated to " + testData.get("L1D1-DwellingRoofCladding")
								+ " and Year Built is updated to " + testData.get("L1D1-DwellingYearBuilt"));

				// Review dwelling and continue
				dwellingPage.waitTime(2);
				dwellingPage.reviewDwelling.scrollToElement();
				dwellingPage.reviewDwelling.click();
				dwellingPage.createQuote.scrollToElement();
				dwellingPage.createQuote.click();

				if(buildingNoLongerQuoteablePage.override.checkIfElementIsPresent()&&buildingNoLongerQuoteablePage.override.checkIfElementIsDisplayed())
				{
					buildingNoLongerQuoteablePage.override.scrollToElement();
					buildingNoLongerQuoteablePage.override.click();
				}

				// Entering Create quote page Details
				createQuotePage.scrollToBottomPage();
				createQuotePage.getAQuote.scrollToElement();
				createQuotePage.getAQuote.click();
				if (createQuotePage.warningMessages.formatDynamicPath("The account is ineligible due to the roof age")
						.checkIfElementIsPresent()
						&& createQuotePage.warningMessages
								.formatDynamicPath("The account is ineligible due to the roof age")
								.checkIfElementIsDisplayed()) {
					Assertions.verify(
							createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
									.contains("The account is ineligible due to the roof age"),
							true, "Create Quote Page",
							"Warning message is " + createQuotePage.warningMessages
									.formatDynamicPath("The account is ineligible due to the roof age").getData()
									+ " is displayed when Roof Cladding is " + testData.get("L1D1-DwellingRoofCladding")
									+ " and Year Built is " + testData.get("L1D1-DwellingYearBuilt"),
							false, false);
				}
			}
			Assertions.addInfo("Scenario 06", "Scenario 06 Ended");
			// IO-21540 Ended

			createQuotePage.goBack.waitTillVisibilityOfElement(60);
			createQuotePage.goBack.scrollToElement();
			createQuotePage.goBack.click();

			createQuotePage.previous.waitTillVisibilityOfElement(60);
			createQuotePage.previous.scrollToElement();
			createQuotePage.previous.click();

			// Edit dwelling and update Year built and roof cladding
			accountOverviewPage.editDwelling.waitTillVisibilityOfElement(60);
			accountOverviewPage.editDwelling.scrollToElement();
			accountOverviewPage.editDwelling.click();

			// Entering Location 1 Dwelling 1 Details
			testData = data.get(dataValue10);
			dwellingPage.enterDwellingDetailsNAHO(testData);

			// change coverage value
			createQuotePage.enterInsuredValuesNAHO(testData, Integer.parseInt(testData.get("LocCount")), Integer
					.parseInt(testData.get("L" + Integer.parseInt(testData.get("LocCount")) + "-DwellingCount")));
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();
			Assertions.addInfo("Scenario 07",
					"Verify minimum Coverage A of $500,000 is required for risks located in the Tri County and Assert Error message");

			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"A minimum Coverage A of $500,000 is required for risks located in the Tri County ", false, false);

			Assertions.verify(createQuotePage.globalErr.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"The Message " + createQuotePage.globalErr.getData() + " displayed is verified", false, false);
			Assertions.addInfo("Scenario 07", "Scenario 07 Ended");

			testData = data.get(dataValue3);
			createQuotePage.enterInsuredValuesNAHO(testData, Integer.parseInt(testData.get("LocCount")), Integer
					.parseInt(testData.get("L" + Integer.parseInt(testData.get("LocCount")) + "-DwellingCount")));

			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.warningMessages.formatDynamicPath("modeling service results").checkIfElementIsPresent()
					&& createQuotePage.warningMessages.formatDynamicPath("modeling service results")
							.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			Assertions.passTest("Account Overview page", "Quote Number : "
					+ accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12));

			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.addInfo("Scenario 08",
					"Assert the Premium Coverage and sinkhole details on Rate Trace page before Override Premium");
			Assertions.verify(
					viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium")
							.checkIfElementIsPresent()
							&& viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium")
									.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium").getData()
							+ " is displayed",
					false, false);

			beforePremiumOverride = viewOrPrintRateTrace.sinkholeOrCgccpremium.getData();
			Assertions.passTest("Rate Trace Page", "Sinkhole/CGCC value before premium override : " + "$"
					+ viewOrPrintRateTrace.sinkholeOrCgccpremium.getData());

			viewOrPrintRateTrace.backBtn.click();
			Assertions.addInfo("Scenario 08", "Scenario 06 Ended");

			// asserting sinkhole and cgcc premium fields are not available
			Assertions.addInfo("Scenario 09",
					"Verify sinkhole and cgcc premium fields are not available and Override the Premium and assert Premium details on Rate trace page");
			Assertions.verify(overridePremiumAndFeesPage.sinkholePremiumOverride.checkIfElementIsPresent(), false,
					"Override Premium and Fees Page",
					"Sinkhole or CGCC premium override option not available when Sinkhole is selected is verified",
					false, false);

			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();

			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
					false);
			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);
			Assertions.passTest("Override Premium and Fees Page", "Fees details updated successfully");

			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.verify(
					viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium")
							.checkIfElementIsPresent()
							&& viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium")
									.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium").getData()
							+ " is displayed",
					false, false);

			Assertions.passTest("Rate Trace Page", "Sinkhole/CGCC value after premium override : " + "$"
					+ viewOrPrintRateTrace.sinkholeOrCgccpremium.getData());

			Assertions.verify(viewOrPrintRateTrace.sinkholeOrCgccpremium.getData(), beforePremiumOverride,
					"Override Premium and Fees Page",
					"Sinkhole or CGCC premium is not changed after premium override when Sinkhole is selected is verified",
					false, false);

			viewOrPrintRateTrace.backBtn.click();
			Assertions.addInfo("Scenario 09", "Scenario 09 Ended");

			testData = data.get(dataValue4);
			Assertions.addInfo("Scenario 10",
					"Create another quote,Assert the Premium Coverage and sinkhole details on Rate Trace page before Override Premium");
			accountOverviewPage.editDeductibleAndLimits.scrollToElement();
			accountOverviewPage.editDeductibleAndLimits.click();

			createQuotePage.enterOptionalcoverageDetailsNAHO(testData);
			createQuotePage.getAQuote.scrollToElement();
			createQuotePage.getAQuote.click();

			if (createQuotePage.warningMessages.formatDynamicPath("modeling service results").checkIfElementIsPresent()
					&& createQuotePage.warningMessages.formatDynamicPath("modeling service results")
							.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}

			if (createQuotePage.continueButton.checkIfElementIsPresent()
					&& createQuotePage.continueButton.checkIfElementIsDisplayed()) {
				createQuotePage.continueButton.scrollToElement();
				createQuotePage.continueButton.click();
			}
			Assertions.passTest("Account Overview page", "Quote Number : "
					+ accountOverviewPage.quoteNumber.formatDynamicPath(1).getData().substring(1, 12));

			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.verify(
					viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium")
							.checkIfElementIsPresent()
							&& viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium")
									.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium").getData()
							+ " is displayed",
					false, false);
			beforePremiumOverride = viewOrPrintRateTrace.sinkholeOrCgccpremium.getData();
			Assertions.passTest("Rate Trace Page", "Sinkhole/CGCC value before premium override : " + "$"
					+ viewOrPrintRateTrace.sinkholeOrCgccpremium.getData());

			viewOrPrintRateTrace.backBtn.click();
			Assertions.addInfo("Scenario 10", "Scenario 10 Ended");

			// asserting sinkhole and cgcc premium fields are not available
			Assertions.addInfo("Scenario 11",
					"Verify sinkhole and cgcc premium fields are not available and Override the Premium and assert Premium details on Rate trace page");
			Assertions.verify(overridePremiumAndFeesPage.sinkholePremiumOverride.checkIfElementIsPresent(), false,
					"Override Premium and Fees Page",
					"Sinkhole or CGCC premium override option not available when CGCC is selected  is verified", false,
					false);

			accountOverviewPage.overridePremiumLink.scrollToElement();
			accountOverviewPage.overridePremiumLink.click();

			Assertions.verify(overridePremiumAndFeesPage.overrideFeesButton.checkIfElementIsDisplayed(), true,
					"Override Premium and Fees Page", "Override Premium and Fees Page loaded successfully", false,
					false);
			overridePremiumAndFeesPage.enterFeesDetailsNAHO(testData);
			Assertions.passTest("Override Premium and Fees Page", "Fees details updated successfully");

			accountOverviewPage.viewOrPrintRateTrace.scrollToElement();
			accountOverviewPage.viewOrPrintRateTrace.click();
			Assertions.verify(
					viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium")
							.checkIfElementIsPresent()
							&& viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium")
									.checkIfElementIsDisplayed(),
					true, "View/Print Rate Trace Page",
					viewOrPrintRateTrace.premiumandCoverageData.formatDynamicPath("Sinkhole or CGCC Premium").getData()
							+ " is displayed",
					false, false);
			Assertions.passTest("Rate Trace Page", "Sinkhole/CGCC value after premium override : " + "$"
					+ viewOrPrintRateTrace.sinkholeOrCgccpremium.getData());
			Assertions.verify(viewOrPrintRateTrace.sinkholeOrCgccpremium.getData(), beforePremiumOverride,
					"Override Premium and Fees Page",
					"Sinkhole or CGCC premium is not changed after premium override when CGCC is selected  is verified",
					false, false);

			viewOrPrintRateTrace.backBtn.click();
			Assertions.addInfo("Scenario 11", "Scenario 11 Ended");

			// Click on View print full quote link
			accountOverviewPage.viewPrintFullQuoteLink.scrollToElement();
			accountOverviewPage.viewPrintFullQuoteLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on View Print Full quote link");

			Assertions.addInfo("Scenario 12",
					"Verify Sinkhole Not available and CGCC availablity on view print full quote page");
			Assertions.verify(viewprFullQuotePage.backButton.checkIfElementIsDisplayed(), true,
					"View/Print Full Quote Page ", "View/Print Full Quote Page loaded successfully", false, false);

			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Not Available"),
					true, "View/Print Full Quote Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("21").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);

			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData().equals("Included"),
					true, "View/Print Full Quote Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("23").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData() + " is verified",
					false, false);

			viewprFullQuotePage.backButton.click();
			Assertions.addInfo("Scenario 12", "Scenario 12 Ended");

			// Click on Request Bind
			accountOverviewPage.clickOnRequestBind(testData, accountOverviewPage.quoteNumber.getData());

			// Clicking on answer no button
			testData = data.get(dataValue1);
			Assertions.verify(underwritingQuestionsPage.answerNoToAllQuestions.checkIfElementIsDisplayed(), true,
					"Underwriting Questions Page", "Underwriting Questions Page loaded successfully", false, false);
			underwritingQuestionsPage.enterUnderwritingQuestionsDetails(testData);

			// Entering bind details
			String quoteNumber = requestBindPage.quoteNumber.getData();
			requestBindPage.enterBindDetailsNAHO(testData);

			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Searching the Bind Referral
			homePage.searchReferral(quoteNumber);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber + " successfullly");

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

			// Asserting Sinkhole and cgcc availability in quote document
			policySummaryPage.quoteNoLinkNAHO.scrollToElement();
			policySummaryPage.quoteNoLinkNAHO.click();

			Assertions.addInfo("Scenario 13",
					"Verify Sinkhole Not available and CGCC availablity on view policy snapshot page");
			Assertions.verify(
					viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData().equals("Not Available"),
					true, "Quote Document Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("21").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("22").getData() + " is verified",
					false, false);

			Assertions.verify(viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData().equals("Included"),
					true, "Quote Document Page",
					"The Option " + viewPolicySnapShot.endorsementValues.formatDynamicPath("23").getData() + " "
							+ viewPolicySnapShot.endorsementValues.formatDynamicPath("24").getData() + " is verified",
					false, false);
			Assertions.addInfo("Scenario 13", "Scenario 13 Ended");

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			// Sign out
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("NBFLTC001 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("NBFLTC001 ", "Executed Successfully");
			}
		}
	}
}