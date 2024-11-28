/*Program Description: Account documentation validations for NB and RNL accounts:
 *Scenario-1: Generate a PBU FL state NB Quote, upload documents with specified categories,
              validate uploads, manage document deletion and restoration and proceed with binding.
 *Scenario-2: Generate a PBU MS state NB Quote, upload three specified documents, confirm successful uploads, switch to producer login, verify displayed documents,
              and ensure that only the 'Subscription Agreement' option is available in the Category drop-down.
 *Scenario-3: Generate a PBU SC state NB Policy, initiate a Renewal, release it to the producer, upload categorized documents, validate uploads, proceed with RNL Bind, complete binding,
 			  and ensure user-uploaded documents include Edit options.
 */

package com.icat.epicenter.test.commercial.retailRegression;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.pom.AccountOverviewPage;
import com.icat.epicenter.pom.BuildingPage;
import com.icat.epicenter.pom.CreateQuotePage;
import com.icat.epicenter.pom.EligibilityPage;
import com.icat.epicenter.pom.ExpaccInfoPage;
import com.icat.epicenter.pom.HomePage;
import com.icat.epicenter.pom.LocationPage;
import com.icat.epicenter.pom.LoginPage;
import com.icat.epicenter.pom.PolicyDocumentsPage;
import com.icat.epicenter.pom.PolicyRenewalPage;
import com.icat.epicenter.pom.PolicySummaryPage;
import com.icat.epicenter.pom.PriorLossesPage;
import com.icat.epicenter.pom.ReferralPage;
import com.icat.epicenter.pom.RequestBindPage;
import com.icat.epicenter.pom.SelectPerilPage;
import com.icat.epicenter.pom.ViewDocumentsPage;
import com.icat.epicenter.test.commercial.AbstractCommercialTest;

public class TC059 extends AbstractCommercialTest {

	public TC059() {
		super(LoginType.USM);
	}

	@Override
	protected String getDataFilePath() {
		return "src/test/resources/testdata/commercial/retailRegression/NBTCID059.xls";
	}

	@Override
	protected void execute(List<Map<String, String>> data, Map<String, String> setUpData) {

		HomePage homePage = new HomePage();
		LocationPage locationPage = new LocationPage();
		BuildingPage buildingPage = new BuildingPage();
		EligibilityPage eligibilityPage = new EligibilityPage();
		PriorLossesPage priorLossesPage = new PriorLossesPage();
		SelectPerilPage selectPerilPage = new SelectPerilPage();
		CreateQuotePage createQuotePage = new CreateQuotePage();
		AccountOverviewPage accountOverviewPage = new AccountOverviewPage();
		ReferralPage referralPage = new ReferralPage();
		PolicyDocumentsPage policyDocumentsPage = new PolicyDocumentsPage();
		RequestBindPage requestBindPage = new RequestBindPage();
		PolicySummaryPage policySummaryPage = new PolicySummaryPage();
		LoginPage loginPage = new LoginPage();
		ViewDocumentsPage viewDocumentsPage = new ViewDocumentsPage();
		ExpaccInfoPage expaccInfoPage = new ExpaccInfoPage();
		PolicyRenewalPage policyRenewalPage = new PolicyRenewalPage();

		int data_Value1 = 0;
		int data_Value2 = 1;
		int data_Value3 = 2;
		int quoteLen2, quoteLen3, renewalquoteLen, categoryCount;
		String quoteNumber, quoteNumber2, quoteNumber3, policyNumber, renewalQuoteNumber;
		Map<String, String> testData = data.get(data_Value1);
		boolean isTestPassed = false;

		try {
			Assertions.addInfo("Scenario-1(NB-USM)",
					"Generate a PBU FL state NB Quote, upload documents with specified categories, validate uploads, manage document deletion and restoration and proceed with binding.");

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

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

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

			// validating the account document categories
			testData = data.get(data_Value1);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
			}
			if (testData.get("QuoteState").contains("FL")) {
				if (!testData.get("FileNameToUpload").equals("")) {
					String fileName = testData.get("FileNameToUpload");
					if (StringUtils.isBlank(fileName)) {
						Assertions.failTest("Upload File", "Filename is blank");
					}
					String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
					waitTime(8);
					if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
							&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
						policyDocumentsPage.chooseDocument
								.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element
					} else {
						policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element

					}
					policyDocumentsPage.waitTime(2);
					for (int i = 0; i < 6; i++) {
						int dataValue = i;
						testData = data.get(dataValue);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
						policyDocumentsPage.waitTime(2);
						String documentCategoryOptions = policyDocumentsPage.categoryOptions
								.formatDynamicPath(testData.get("AccountDocumentName")).getData();
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.waitTillPresenceOfElement(60);
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.waitTillVisibilityOfElement(60);
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.scrollToElement();
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.click();
						Assertions.passTest("Upload Quote Documents Page", documentCategoryOptions
								+ " document category option is present and verified for FL State.");
						policyDocumentsPage.waitTime(2);
					}
				}
				policyDocumentsPage.cancelButtonUAT.scrollToElement();
				policyDocumentsPage.cancelButtonUAT.click();
				policyDocumentsPage.waitTime(5);// adding wait time to load the element
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
			}

			// uploading the account documents
			testData = data.get(data_Value1);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsPresent()
					&& accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				if (policyDocumentsPage.pageName.getData().contains("Quote Documents")) {

					if (testData.get("QuoteState").contains("FL")) {
						policyDocumentsPage.addDocumentButton.scrollToElement();
						policyDocumentsPage.addDocumentButton.click();
						String fileName = testData.get("FileNameToUpload");
						if (StringUtils.isBlank(fileName)) {
							Assertions.failTest("Upload File", "Filename is blank");
						}
						String uploadFileDir = EnvironmentDetails.getEnvironmentDetails()
								.getString("test.file.uploadpath");
						waitTime(8);
						if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
								&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
							policyDocumentsPage.chooseDocument
									.setData(new File(uploadFileDir + fileName).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element
						} else {
							policyDocumentsPage.chooseFile
									.setData(new File(uploadFileDir + fileName).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element

						}
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
								.click();
						// policyDocumentsPage.fileUpload("NBTCID01.xls");
						String fileName1 = "TC_78Code.txt";
						if (StringUtils.isBlank(fileName)) {
							Assertions.failTest("Upload File", "Filename is blank");
						}
						String uploadFileDir1 = EnvironmentDetails.getEnvironmentDetails()
								.getString("test.file.uploadpath");
						waitTime(8);
						if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
								&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
							policyDocumentsPage.chooseDocument
									.setData(new File(uploadFileDir1 + fileName1).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element
						} else {
							policyDocumentsPage.chooseFile
									.setData(new File(uploadFileDir1 + fileName1).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element

						}
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 3)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 3).click();
						policyDocumentsPage.externalCheckbox.formatDynamicPath("Inspections").scrollToElement();
						policyDocumentsPage.externalCheckbox.formatDynamicPath("Inspections").select();
						// policyDocumentsPage.fileUpload("NBTCID02.xls");
						String fileName2 = "dummy_doc.txt";
						if (StringUtils.isBlank(fileName)) {
							Assertions.failTest("Upload File", "Filename is blank");
						}
						String uploadFileDir2 = EnvironmentDetails.getEnvironmentDetails()
								.getString("test.file.uploadpath");
						waitTime(8);
						if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
								&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
							policyDocumentsPage.chooseDocument
									.setData(new File(uploadFileDir2 + fileName2).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element
						} else {
							policyDocumentsPage.chooseFile
									.setData(new File(uploadFileDir2 + fileName2).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element

						}
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("UW Support Documents", 4)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("UW Support Documents", 4)
								.click();
						policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
						policyDocumentsPage.uploadButtonUAT.scrollToElement();
						policyDocumentsPage.uploadButtonUAT.click();
						Assertions.passTest("Policy Documents Page",
								"The uploading of the Subscription Agreement, Inspections, and UW Support Documents has been completed successfully.");

						// Validating uploaded documents
						policyDocumentsPage.documentCategory.formatDynamicPath("Subscription Agreement")
								.checkIfElementIsPresent();
						policyDocumentsPage.documentCategory.formatDynamicPath("Inspections").checkIfElementIsPresent();
						policyDocumentsPage.documentCategory.formatDynamicPath("UW Support Documents")
								.checkIfElementIsPresent();
						Assertions.passTest("Policy Documents Page",
								"The policy documents page contains and has verified the Subscription Agreement, Inspections, and UW Support Documents.");

						// Performing deleting and restoring operations for subscription agreement
						Assertions.addInfo("Policy Documents Page",
								"Validating the presence of delete options for the uploaded documents and performing deletion and restoration actions for the subscription agreement document.");
						policyDocumentsPage.deleteIcon.formatDynamicPath("Subscription Agreement")
								.checkIfElementIsPresent();
						policyDocumentsPage.deleteIcon.formatDynamicPath("Inspections").checkIfElementIsPresent();
						policyDocumentsPage.deleteIcon.formatDynamicPath("UW Support Documents")
								.checkIfElementIsPresent();
						Assertions.passTest("Policy Documents Page",
								"Successfully validated the presence of delete options for the Subscription Agreement,Inspections and UW Support documents.");
						// Deleting Subscription Agreement Document
						policyDocumentsPage.deleteIcon.formatDynamicPath("Subscription Agreement").scrollToElement();
						policyDocumentsPage.deleteIcon.formatDynamicPath("Subscription Agreement").click();
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.deleteButton.scrollToElement();
						policyDocumentsPage.deleteButton.click();
						Assertions.passTest("Policy Documents Page",
								"Successfully deleted the Subscription Agreement document.");
						// Checking for restore option available after deleting the subscription
						// agreement document.
						policyDocumentsPage.restoreOption.formatDynamicPath("Subscription Agreement")
								.checkIfElementIsPresent();
						policyDocumentsPage.restoreOption.formatDynamicPath("Subscription Agreement")
								.checkIfElementIsDisplayed();
						// Restoring the Subscription Agreement document
						policyDocumentsPage.restoreOption.formatDynamicPath("Subscription Agreement").scrollToElement();
						policyDocumentsPage.restoreOption.formatDynamicPath("Subscription Agreement").click();
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.restoreButton.scrollToElement();
						policyDocumentsPage.restoreButton.click();
						Assertions.passTest("Policy Documents Page",
								"Successfully validated the presence of restore options for the Subscription Agreement and Restored.");
						Assertions.addInfo("Policy Documents Page",
								"Successfully validated the presence of delete options for the uploaded documents and performed delete and restore operations for subscription agreement document.");
					}
					policyDocumentsPage.waitTime(5);// adding wait time to load the element
					policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
					policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
					policyDocumentsPage.backButton.scrollToElement();
					policyDocumentsPage.backButton.click();
					accountOverviewPage.requestBind.scrollToElement();
					accountOverviewPage.requestBind.click();
				}
			}

			// Entering bind details
			testData = data.get(data_Value1);
			requestBindPage.enterBindDetails(testData);

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting policy number in policy summary page.
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Clicking on home page button
			homePage.goToHomepage.scrollToElement();
			homePage.goToHomepage.click();

			Assertions.addInfo("Scenario-1(NB-USM)", "Scenario-1(NB-USM) Ended");

			Assertions.addInfo("Scenario-2(NB-USM to Producer)",
					"Generate a PBU MS state NB Quote, upload three specified documents, confirm successful uploads, switch to producer login, verify displayed documents, and ensure that only the 'Subscription Agreement' option is available in the Category dropdown.");

			// Creating New Account
			testData = data.get(data_Value2);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen2 = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber2 = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen2 - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber2);

			// uploading the account documents
			testData = data.get(data_Value2);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsPresent()
					&& accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				if (policyDocumentsPage.pageName.getData().contains("Quote Documents")) {

					if (testData.get("QuoteState").contains("MS")) {
						policyDocumentsPage.addDocumentButton.scrollToElement();
						policyDocumentsPage.addDocumentButton.click();
						// policyDocumentsPage.fileUpload("AutomationTest.pdf");
						String fileName = testData.get("FileNameToUpload");
						policyDocumentsPage.waitTime(2);
						// policyDocumentsPage.fileUpload("NBTCID01.xls");
						String fileName1 = "TC_78Code.txt";
						if (StringUtils.isBlank(fileName)) {
							Assertions.failTest("Upload File", "Filename is blank");
						}
						String uploadFileDir1 = EnvironmentDetails.getEnvironmentDetails()
								.getString("test.file.uploadpath");
						waitTime(8);
						if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
								&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
							policyDocumentsPage.chooseDocument
									.setData(new File(uploadFileDir1 + fileName1).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element
						} else {
							policyDocumentsPage.chooseFile
									.setData(new File(uploadFileDir1 + fileName1).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element

						}
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 2)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 2).click();
						policyDocumentsPage.externalCheckbox.formatDynamicPath("Inspections").scrollToElement();
						policyDocumentsPage.externalCheckbox.formatDynamicPath("Inspections").select();
						// policyDocumentsPage.fileUpload("NBTCID02.xls");
						String fileName2 = "dummy_doc.txt";
						if (StringUtils.isBlank(fileName)) {
							Assertions.failTest("Upload File", "Filename is blank");
						}
						String uploadFileDir2 = EnvironmentDetails.getEnvironmentDetails()
								.getString("test.file.uploadpath");
						waitTime(8);
						if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
								&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
							policyDocumentsPage.chooseDocument
									.setData(new File(uploadFileDir2 + fileName2).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element
						} else {
							policyDocumentsPage.chooseFile
									.setData(new File(uploadFileDir2 + fileName2).getAbsolutePath());
							waitTime(5);// Adding wait time to load the element

						}
						policyDocumentsPage.waitTime(2);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).click();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("UW Support Documents", 3)
								.scrollToElement();
						policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("UW Support Documents", 3)
								.click();
						policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
						policyDocumentsPage.uploadButtonUAT.scrollToElement();
						policyDocumentsPage.uploadButtonUAT.click();
						Assertions.passTest("Policy Documents Page",
								"The uploading of the Subscription Agreement, Inspections, and UW Support Documents has been completed successfully.");

						// Validating uploaded documents

						policyDocumentsPage.documentCategory.formatDynamicPath("Inspections").checkIfElementIsPresent();
						policyDocumentsPage.documentCategory.formatDynamicPath("UW Support Documents")
								.checkIfElementIsPresent();
						Assertions.passTest("Policy Documents Page",
								"The policy documents page contains and has verified the Subscription Agreement, Inspections, and UW Support Documents.");

					}
					policyDocumentsPage.waitTime(5);// adding wait time to load the element
					policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
					policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
					policyDocumentsPage.backButton.scrollToElement();
					policyDocumentsPage.backButton.click();
					accountOverviewPage.requestBind.scrollToElement();
					accountOverviewPage.requestBind.click();
				}

			}

			// SignOut as USM
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			Assertions.passTest("Home Page", "Logged out as USM successfully");

			// Signin as Producer
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("RetailProducer"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as Producer Successfully");

			// searching the quote number in grid and clicking on the quote number link
			homePage.searchQuoteByProducer(quoteNumber2);
			Assertions.passTest("Home Page", "Searched Submitted Quote " + quoteNumber2 + " successfullly");

			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
				String fileName = testData.get("FileNameToUpload");
				if (StringUtils.isBlank(fileName)) {
					Assertions.failTest("Upload File", "Filename is blank");
				}
				String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
				waitTime(8);
				if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
						&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
					policyDocumentsPage.chooseDocument.setData(new File(uploadFileDir + fileName).getAbsolutePath());
					waitTime(5);// Adding wait time to load the element
				} else {
					policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
					waitTime(5);// Adding wait time to load the element

				}
				policyDocumentsPage.waitTime(2);
				policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
				policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();

			}

			Assertions.addInfo("Producer login",
					"Validating the document category dropdown contains only the 'Subscription Agreement' option.");

			// Get no of category options present from the dropdown
			categoryCount = policyDocumentsPage.categoryList.getNoOfWebElements();

			// Check if there is only one option
			if (categoryCount == 2) {
				// Check if the only option is "subscription agreement"
				String categoryOption = policyDocumentsPage.categoryOptions.formatDynamicPath("Subscription Agreement")
						.getData();
				if (categoryOption.equals("Subscription Agreement")) {
					Assertions.passTest("Upload Quote Documents Page",
							"Dropdown contains only 'Subscription Agreement' option.");
					policyDocumentsPage.cancelButtonUAT.scrollToElement();
					policyDocumentsPage.cancelButtonUAT.click();
				} else {
					Assertions.verify(categoryOption.equals("Subscription Agreement"), false,
							"Upload Quote Documents Page",
							"Dropdown contains one option, but it is not 'Subscription Agreement'.", false, false);
				}
			} else {
				Assertions.verify(categoryCount == 2, false, "Upload Quote Documents Page",
						"Dropdown contains more than one option.", false, false);
			}
			Assertions.addInfo("Producer login",
					"Successfully validated the document category option for the producer.");

			Assertions.addInfo("Producer login", "Verifying the USM uploaded documents through producer login");
			// Checking only two documents are present in producer login
			if (policyDocumentsPage.documentCategory.formatDynamicPath("Subscription Agreement")
					.checkIfElementIsPresent()) {
				policyDocumentsPage.documentCategory.formatDynamicPath("Subscription Agreement")
						.checkIfElementIsPresent();
				policyDocumentsPage.documentCategory.formatDynamicPath("Inspections").checkIfElementIsPresent();
				if (!policyDocumentsPage.documentCategory.formatDynamicPath("UW Support Documents")
						.checkIfElementIsPresent()) {
					Assertions.passTest("Policy Documents Page", "UW Support Document is not displayed");
				} else {
					Assertions.verify(policyDocumentsPage.documentCategory.formatDynamicPath("UW Support Documents"),
							true, "Upload Quote Documents Page", "UW Support Document is displayed", false, false);
				}
				Assertions.passTest("Policy Documents Page",
						"Only Subscription Agreement and Inspections documents are present");
			}
			Assertions.addInfo("Producer login",
					"Successfully verified the USM uploaded documents through producer login");

			// Signout as prodcuer
			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();

			Assertions.passTest("Home Page", "Logged out as Producer successfully");
			Assertions.addInfo("Scenario-2(NB-USM to Producer)", "Scenario-2(NB-USM to Producer) Ended");

			Assertions.addInfo("Scenario-3(RNL-As USM)",
					"Generate a PBU SC state NB Policy, initiate a Renewal, release it to the producer, upload categorized documents, validate uploads, proceed with RNL Bind, complete binding, and ensure user-uploaded documents include Edit options.");

			// Signin as USM
			loginPage.refreshPage();
			loginPage.enterLoginDetails(setUpData.get("UserName"), setUpData.get("Password"));
			Assertions.passTest("Home Page", "Logged in as USM Successfully");

			// Creating New Account
			testData = data.get(data_Value3);
			Assertions.verify(homePage.createNewAccount.checkIfElementIsDisplayed(), true, "Home Page",
					"Home Page loaded successfully", false, false);
			homePage.createNewAccountWithNamedInsured(testData, setUpData);
			Assertions.passTest("New Account", "New Account Created successfully");

			// Entering Zipcode
			Assertions.verify(eligibilityPage.continueButton.checkIfElementIsDisplayed(), true, "Eligibility Page",
					"Eligibility Page loaded successfully", false, false);
			eligibilityPage.processSingleZip(testData);
			Assertions.passTest("Eligibility Page", "Zipcode entered successfully");

			// Entering Location Details
			Assertions.verify(locationPage.businessIncome.checkIfElementIsDisplayed(), true, "Location Page",
					"Location Page loaded successfully", false, false);
			locationPage.enterLocationDetails(testData);
			Assertions.passTest("Location Page", "Location details entered successfully");

			// Enter Building Details
			//Assertions.verify(buildingPage.createQuote.checkIfElementIsDisplayed(), true, "Building Page",
					//"Building Page loaded successfully", false, false);
			buildingPage.enterBuildingDetails(testData);

			// Selecting a peril
			Assertions.verify(selectPerilPage.continueButton.checkIfElementIsDisplayed(), true, "Select Peril Page",
					"Select Peril Page loaded successfully", false, false);
			selectPerilPage.selectPeril(testData.get("Peril"));

			// Entering Prior Losses
			Assertions.verify(priorLossesPage.continueButton.checkIfElementIsDisplayed(), true, "Prior Loss Page",
					"Prior Loss Page loaded successfully", false, false);
			priorLossesPage.selectPriorLossesInformation(testData);

			// Entering Create quote page Details
			Assertions.verify(createQuotePage.getAQuote.checkIfElementIsDisplayed(), true, "Create Quote Page",
					"Create Quote Page loaded successfully", false, false);
			createQuotePage.enterQuoteDetailsCommercialNew(testData);
			Assertions.passTest("Create Quote Page", "Quote details entered successfully");

			Assertions.verify(accountOverviewPage.requestBind.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page Loaded successfully", false, false);
			quoteLen3 = accountOverviewPage.quoteNumber.getData().length();
			quoteNumber3 = accountOverviewPage.quoteNumber.getData().substring(1, quoteLen3 - 1);
			Assertions.passTest("Account Overview Page", "Quote Number is : " + quoteNumber3);

			// Added IO-21670

			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
			}

			if (testData.get("QuoteState").contains("SC")) {

				// policyDocumentsPage.fileUpload("FileExtensionTest.dif");
				String fileName = "FileExtensionTest.dif";
				if (StringUtils.isBlank(fileName)) {
					Assertions.failTest("Upload File", "Filename is blank");
				}
				String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
				waitTime(8);
				if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
						&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
					policyDocumentsPage.chooseDocument.setData(new File(uploadFileDir + fileName).getAbsolutePath());
					waitTime(5);// Adding wait time to load the element
				} else {
					policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
					waitTime(5);// Adding wait time to load the element

				}
				policyDocumentsPage.waitTime(2);
				policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
				policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
				policyDocumentsPage.waitTime(2);
				policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
						.scrollToElement();
				policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2).click();
				policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
				policyDocumentsPage.uploadButtonUAT.scrollToElement();
				policyDocumentsPage.uploadButtonUAT.click();
			}
			// Checking for the warning message displayed on the file upload page when
			// uploaded file extension is invalid.
			Assertions.verify(policyDocumentsPage.fileExtensionError.checkIfElementIsPresent(), true,
					"Upload Quote Documents Page",
					"Warning message is displayed when the uploaded file extension is invalid.", false, false);

			Assertions.verify(policyDocumentsPage.fileExtensionError.getData().equals(
					"Invalid file type. Allowed file extentions are: csv, doc, docx, eml, gif, jpeg, jpg, msg, pdf, png, ppt, pptx, tif, tiff, txt, xls, xlsx"),
					true, "Upload Quote Documents Page",
					"File Extension Error message is matching with the expected result is verified.", false, false);
			Assertions.passTest("File Extension Warning Message: ", policyDocumentsPage.fileExtensionError.getData());

			policyDocumentsPage.cancelButtonUAT.scrollToElement();
			policyDocumentsPage.cancelButtonUAT.click();
			policyDocumentsPage.waitTime(5);// adding wait time to load the element
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			// Ticket IO-21670 is Ended

			// Binding the quote and creating the policy
			accountOverviewPage.clickOnRequestBind(testData, quoteNumber3);

			// Entering bind details
			testData = data.get(data_Value3);
			requestBindPage.enterBindDetails(testData);

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(quoteNumber3);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting policy number in policy summary page.
			policyNumber = policySummaryPage.policyNumber.getData();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Number is " + policySummaryPage.policyNumber.getData(), false, false);

			// Click on Renew policy
			policySummaryPage.renewPolicy.scrollToElement();
			policySummaryPage.renewPolicy.click();

			// Go to Home Page
			if (policySummaryPage.expaacMessage.checkIfElementIsPresent()
					&& policySummaryPage.expaacMessage.checkIfElementIsDisplayed()) {
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();
				homePage.expaccLink.scrollToElement();
				homePage.expaccLink.click();
				expaccInfoPage.enterExpaccInfo(testData, policyNumber);
				Assertions.passTest("ExpaccInfo Page", "ExpaccInfo details entered successfully");

				// Go to Home Page
				homePage.goToHomepage.scrollToElement();
				homePage.goToHomepage.click();

				// Performing Renewal Searches
				homePage.searchPolicy(policyNumber);

				// clicking on renewal policy link
				policySummaryPage.renewPolicy.scrollToElement();
				policySummaryPage.renewPolicy.click();
				Assertions.passTest("Policy Summary Page", "Clicked on Renew Policy Link successfully");
			}

			// Click on continue button
			if (policyRenewalPage.continueRenewal.checkIfElementIsPresent()
					&& policyRenewalPage.continueRenewal.checkIfElementIsDisplayed()) {
				policyRenewalPage.continueRenewal.click();
			}

			// Click on renewal pop up
			if (policyRenewalPage.renewalYes.checkIfElementIsPresent()
					&& policyRenewalPage.renewalYes.checkIfElementIsDisplayed()) {
				policyRenewalPage.renewalYes.checkIfElementIsPresent();
				policyRenewalPage.renewalYes.click();
			}

			Assertions.verify(accountOverviewPage.viewPreviousPolicyButton.checkIfElementIsDisplayed(), true,
					"Account Overview Page", "Account Overview Page loaded successfully", false, false);

			// Getting Renewal Quote Number
			renewalquoteLen = accountOverviewPage.quoteNumber.getData().length();
			renewalQuoteNumber = accountOverviewPage.quoteNumber.getData().substring(1, renewalquoteLen - 1);
			Assertions.passTest("Account Overview Page", "The Renewal Quote Number is : " + renewalQuoteNumber);

			// click on release to producer
			accountOverviewPage.releaseRenewalToProducerButton.scrollToElement();
			accountOverviewPage.releaseRenewalToProducerButton.click();
			Assertions.passTest("Account Overview Page", "Clicked on Release Renewal to Producer successfully");

			// validating the account document categories
			testData = data.get(data_Value3);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();

				policyDocumentsPage.addDocumentButton.scrollToElement();
				policyDocumentsPage.addDocumentButton.click();
			}
			if (testData.get("QuoteState").contains("SC")) {
				if (!testData.get("FileNameToUpload").equals("")) {
					String fileName = testData.get("FileNameToUpload");
					if (StringUtils.isBlank(fileName)) {
						Assertions.failTest("Upload File", "Filename is blank");
					}
					String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
					waitTime(8);
					if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
							&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
						policyDocumentsPage.chooseDocument
								.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element
					} else {
						policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element

					}
					policyDocumentsPage.waitTime(2);
					for (int i = 0; i < 7; i++) {
						int dataValue = i;
						testData = data.get(dataValue);
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
						policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
						policyDocumentsPage.waitTime(2);
						String documentCategoryOptions = policyDocumentsPage.categoryOptions
								.formatDynamicPath(testData.get("AccountDocumentName")).getData();
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.waitTillPresenceOfElement(60);
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.waitTillVisibilityOfElement(60);
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.scrollToElement();
						policyDocumentsPage.categoryOptions.formatDynamicPath(testData.get("AccountDocumentName"))
								.click();
						Assertions.passTest("Upload Quote Documents Page", documentCategoryOptions
								+ " document category option is present and verified for SC State.");
						policyDocumentsPage.waitTime(2);
					}
				}
				policyDocumentsPage.cancelButtonUAT.scrollToElement();
				policyDocumentsPage.cancelButtonUAT.click();
				policyDocumentsPage.waitTime(5);// adding wait time to load the element
				policyDocumentsPage.backButton.scrollToElement();
				policyDocumentsPage.backButton.click();
			}

			// uploading the account documents
			testData = data.get(data_Value3);
			if (accountOverviewPage.uploadPreBindDocuments.checkIfElementIsPresent()
					&& accountOverviewPage.uploadPreBindDocuments.checkIfElementIsDisplayed()) {
				accountOverviewPage.uploadPreBindDocuments.scrollToElement();
				accountOverviewPage.uploadPreBindDocuments.click();
			}
			if (policyDocumentsPage.pageName.getData().contains("Quote Documents")) {
				if (testData.get("QuoteState").contains("SC")) {
					policyDocumentsPage.addDocumentButton.scrollToElement();
					policyDocumentsPage.addDocumentButton.click();
					String fileName = testData.get("FileNameToUpload");
					if (StringUtils.isBlank(fileName)) {
						Assertions.failTest("Upload File", "Filename is blank");
					}
					String uploadFileDir = EnvironmentDetails.getEnvironmentDetails().getString("test.file.uploadpath");
					waitTime(8);
					if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
							&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
						policyDocumentsPage.chooseDocument
								.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element
					} else {
						policyDocumentsPage.chooseFile.setData(new File(uploadFileDir + fileName).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element

					}
					policyDocumentsPage.waitTime(2);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).scrollToElement();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(2).click();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Subscription Agreement", 2)
							.click();
					String fileName1 = "TC_78Code.txt";
					if (StringUtils.isBlank(fileName)) {
						Assertions.failTest("Upload File", "Filename is blank");
					}
					String uploadFileDir1 = EnvironmentDetails.getEnvironmentDetails()
							.getString("test.file.uploadpath");
					waitTime(8);
					if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
							&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
						policyDocumentsPage.chooseDocument
								.setData(new File(uploadFileDir1 + fileName1).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element
					} else {
						policyDocumentsPage.chooseFile.setData(new File(uploadFileDir1 + fileName1).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element

					}
					policyDocumentsPage.waitTime(2);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).scrollToElement();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(3).click();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Due Diligence", 3)
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Due Diligence", 3).click();
					String fileName2 = "dummy_doc.txt";
					if (StringUtils.isBlank(fileName)) {
						Assertions.failTest("Upload File", "Filename is blank");
					}
					String uploadFileDir2 = EnvironmentDetails.getEnvironmentDetails()
							.getString("test.file.uploadpath");
					waitTime(8);
					if (policyDocumentsPage.chooseDocument.checkIfElementIsPresent()
							&& policyDocumentsPage.chooseDocument.checkIfElementIsDisplayed()) {
						policyDocumentsPage.chooseDocument
								.setData(new File(uploadFileDir2 + fileName2).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element
					} else {
						policyDocumentsPage.chooseFile.setData(new File(uploadFileDir2 + fileName2).getAbsolutePath());
						waitTime(5);// Adding wait time to load the element

					}
					policyDocumentsPage.waitTime(2);
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).scrollToElement();
					policyDocumentsPage.documentCategoryArrowUAT.formatDynamicPath(4).click();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 4)
							.scrollToElement();
					policyDocumentsPage.documentCategoryOptionsUAT.formatDynamicPath("Inspections", 4).click();
					policyDocumentsPage.uploadButtonUAT.waitTillPresenceOfElement(60);
					policyDocumentsPage.uploadButtonUAT.scrollToElement();
					policyDocumentsPage.uploadButtonUAT.click();

					// Validating uploaded documents
					Assertions.passTest("Policy Documents Page",
							"The uploading of the Subscription Agreement, Due Diligence and InspectionsDocuments has been completed successfully.");
					policyDocumentsPage.documentCategory.formatDynamicPath("Subscription Agreement")
							.checkIfElementIsPresent();
					policyDocumentsPage.documentCategory.formatDynamicPath("Inspections").checkIfElementIsPresent();
					policyDocumentsPage.documentCategory.formatDynamicPath("Due Diligence").checkIfElementIsPresent();
					Assertions.passTest("Policy Documents Page",
							"The policy documents page contains and has verified the Subscription Agreement, Inspections, and Due Diligence Documents.");
				}
			}
			policyDocumentsPage.waitTime(5);// adding wait time to load the element
			policyDocumentsPage.backButton.waitTillPresenceOfElement(60);
			policyDocumentsPage.backButton.waitTillVisibilityOfElement(60);
			policyDocumentsPage.backButton.scrollToElement();
			policyDocumentsPage.backButton.click();
			accountOverviewPage.requestBind.scrollToElement();
			accountOverviewPage.requestBind.click();
			Assertions.passTest("Account Overview Page", "Clicked on Request Bind");

			// binding the renewal
			requestBindPage.refreshPage();
			testData = data.get(data_Value3);
			requestBindPage.renewalRequestBind(testData);

			// Go to HomePage
			homePage.goToHomepage.click();
			homePage.searchQuote(renewalQuoteNumber);
			Assertions.passTest("Home Page", "Quote is searched successfully");

			// Click on Open Referral Link
			accountOverviewPage.openReferralLink.scrollToElement();
			accountOverviewPage.openReferralLink.click();
			Assertions.passTest("Account Overview Page", "Clicked on Open Referral link successfully");

			// approving referral
			Assertions.verify(referralPage.close.checkIfElementIsDisplayed(), true, "Referral Page",
					"Referral page loaded successfully", false, false);
			referralPage.clickOnApprove();
			Assertions.passTest("Referral Page", "Referral request approved successfully");

			// Click on approve button
			requestBindPage.approveRequest();
			Assertions.passTest("Request Bind Page", "Bind Request approved successfully");

			// Asserting policy number
			policyNumber = policySummaryPage.getPolicynumber();
			Assertions.verify(policySummaryPage.policyNumber.checkIfElementIsDisplayed(), true, "Policy Summary Page",
					"Policy Summary Page loaded successfully. The Renewal PolicyNumber is : " + policyNumber, false,
					false);

			// Validating the user-uploaded documents on the view documents page
			if (policySummaryPage.viewDocuments.checkIfElementIsPresent()
					&& policySummaryPage.viewDocuments.checkIfElementIsDisplayed()) {
				Assertions.addInfo("View Documents Page",
						"Validating the presence of edit options for the user uploaded docs.");
				policySummaryPage.viewDocuments.scrollToElement();
				policySummaryPage.viewDocuments.click();
				waitTime(2);
				homePage.goToHomepage.click();
				homePage.searchPolicy(policyNumber);
				policySummaryPage.refreshPage();
				policySummaryPage.waitTime(4);
				policySummaryPage.viewDocuments.scrollToElement();
				policySummaryPage.viewDocuments.click();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Subscription Agreement")
						.checkIfElementIsPresent();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Subscription Agreement").scrollToElement();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Subscription Agreement").click();
				viewDocumentsPage.waitTime(2);
				viewDocumentsPage.cancelBtn.scrollToElement();
				viewDocumentsPage.cancelBtn.click();
				Assertions.passTest("View Documents Page",
						"Edit option is present for the 'Subscription Agreement' Document");
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Due Diligence").checkIfElementIsPresent();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Due Diligence").scrollToElement();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Due Diligence").click();
				viewDocumentsPage.waitTime(2);
				viewDocumentsPage.cancelBtn.scrollToElement();
				viewDocumentsPage.cancelBtn.click();
				Assertions.passTest("View Documents Page", "Edit option is present for the 'Due Diligence' Document");
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Inspections").checkIfElementIsPresent();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Inspections").scrollToElement();
				viewDocumentsPage.editUserDocuments.formatDynamicPath("Inspections").click();
				viewDocumentsPage.waitTime(2);
				viewDocumentsPage.cancelBtn.scrollToElement();
				viewDocumentsPage.cancelBtn.click();
				Assertions.passTest("View Documents Page", "Edit option is present for the 'Inspections' Document");
				Assertions.addInfo("View Documents Page",
						"Successfully validated the presence of edit options for the Subscription Agreement,Due Diligence and Inspections documents.");
			}

			Assertions.addInfo("Scenario-3(RNL-As USM)", "Scenario-3(RNL-As USM) Ended");

			homePage.signOutButton.scrollToElement();
			homePage.signOutButton.click();
			isTestPassed = true;
		} catch (Exception e) {
			Assertions.failTest("Commercial Retail TC059 ", "Test Failed " + e.getMessage());
		} finally {
			if (isTestPassed) {
				Assertions.passTest("Commercial Retail TC059 ", "Executed Successfully");
			}
		}
	}
}
