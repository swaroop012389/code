/** Program Description: Object Locators and methods defined in Integrations Admin
 *  for now just enough to check that PBU Commercial Wholesale transactions are not sent to BIN
 *  Author			   : TFodor
 *  Date of Creation   : 07/13/2021
 **/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.NetServAutomationFramework.generic.WebDriverManager;

public class IntegrationsAdminPage extends BasePageControl {
	public HyperLink loginLink;
	public TextFieldControl username;
	public TextFieldControl password;
	public ButtonControl loginButton;
	public HyperLink documentDelivery;
	public HyperLink deliverySearch;
	public TextFieldControl policyNumber;
	public ButtonControl submitButton;
	public BaseWebElementControl resultsTable;
	public HyperLink logoutLink;
	public String polNum;
	public HyperLink billingServiceLink;
	public HyperLink billingAdminFunctions;
	public TextFieldControl generateNOCDocumentForPolicyNumber;
	public TextFieldControl generatedNOCDocumentToBeSentEmailID;
	public ButtonControl generateNOCDocumentGoButton;
	public BaseWebElementControl generateAndFileTestStatementsPopUp;
	public ButtonControl oKButtonInPopUp;

	public IntegrationsAdminPage() {
		PageObject pageobject = new PageObject("IntegrationsAdmin");
		loginLink = new HyperLink(By.xpath(pageobject.getXpath("xp_LoginLink")));
		username = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Username")));
		password = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Password")));
		loginButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_LoginButton")));
		documentDelivery = new HyperLink(By.xpath(pageobject.getXpath("xp_DocumentDelivery")));
		deliverySearch = new HyperLink(By.xpath(pageobject.getXpath("xp_DeliverySearch")));
		policyNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyNumber")));
		submitButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SubmitButton")));
		resultsTable = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ResultsTable")));
		logoutLink = new HyperLink(By.xpath(pageobject.getXpath("xp_LogoutLink")));
		billingServiceLink = new HyperLink(By.xpath(pageobject.getXpath("xp_BillingService")));
		billingAdminFunctions = new HyperLink(By.xpath(pageobject.getXpath("xp_BillingAdminFunctions")));
		generateNOCDocumentForPolicyNumber = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_GenerateNOCDocumentForPolicyNumber")));
		generatedNOCDocumentToBeSentEmailID = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_GeneratedNOCDocumentToBeSentEmailID")));
		generateNOCDocumentGoButton = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_GenerateNOCDocumentGoButton")));
		generateAndFileTestStatementsPopUp = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_GenerateAndFileTestStatementsPopUp")));
		oKButtonInPopUp = new ButtonControl(By.xpath(pageobject.getXpath("xp_OKButtonInPopUp")));
	}

	public void intAdminLogin() {
		loginLink.waitTillVisibilityOfElement(60);
		loginLink.click();

		username.waitTillVisibilityOfElement(60);
		username.setData("rfitzgibbons");
		password.setData("L1ghthouse");
		loginButton.click();

	}

	public void checkBINStatus(String polNum) {
		documentDelivery.waitTillVisibilityOfElement(60);
		documentDelivery.click();
		deliverySearch.waitTillVisibilityOfElement(60);
		deliverySearch.click();

		policyNumber.waitTillVisibilityOfElement(60);
		policyNumber.setData(polNum);
		submitButton.click();

		resultsTable.waitTillVisibilityOfElement(60);

	}

	public void intAdminLogout() {
		logoutLink.waitTillVisibilityOfElement(60);
		logoutLink.click();
	}

	public void generateNOCDocument(String policyNumber, String eMailID) {
		billingServiceLink.scrollToElement();
		billingServiceLink.click();

		billingAdminFunctions.scrollToElement();
		billingAdminFunctions.click();

		generateNOCDocumentForPolicyNumber.scrollToElement();
		generateNOCDocumentForPolicyNumber.setData(policyNumber);

		generatedNOCDocumentToBeSentEmailID.scrollToElement();
		generatedNOCDocumentToBeSentEmailID.setData(eMailID);

		generateNOCDocumentGoButton.scrollToElement();
		generateNOCDocumentGoButton.click();
		Assertions.passTest("Integration Admin Page", "Clicked on Go Button in Billing Admin Functions");

		oKButtonInPopUp.scrollToElement();
		oKButtonInPopUp.click();
		Assertions.passTest("Integrations Admin Page", "Clicked on Ok Button in the Pop Up");

	}

	public void loadURL(String url) {
		try {
			WebDriverManager.getCurrentDriver().navigate().to(url);
			WebDriverManager.getCurrentDriver().manage().deleteAllCookies();
		} catch (Exception e) {
			Assertions.exceptionError("Error loadURL", "" + e.toString());
		}
	}


}
