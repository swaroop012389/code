/** Program Description: Object Locators and methods defined in Edit additional interest information page
 *  Author			   : SMNetserv
 *  Date of Creation   : 02/11/2017
**/

package com.icat.epicenter.pom;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;
import com.NetServAutomationFramework.generic.WebDriverManager;

public class EditAdditionalInterestInformationPage extends BasePageControl {
	public ButtonControl aITypeArrow;
	public HyperLink aITypeOption;
	public ButtonControl aIRankArrow;
	public HyperLink aIRankoption;
	public TextFieldControl aILoanNumber;
	public BaseWebElementControl deleteAdditionalInterest;
	public BaseWebElementControl deleteAdditionalInterest_No;
	public BaseWebElementControl deleteAdditionalInterest_Yes;
	public TextFieldControl aIName;
	public TextFieldControl aICountry;
	public TextFieldControl aIAddress;
	public TextFieldControl aIAddressLine2;
	public HyperLink aIEnterAddressManuallyLink;
	public TextFieldControl aIManualAddressLine1;
	public TextFieldControl aIManualAddressLine2;
	public TextFieldControl aICity;
	public TextFieldControl aIState;
	public TextFieldControl aIZipCode1;
	public TextFieldControl aIZipCode2;
	public TextFieldControl postalCode;

	public ButtonControl cancel;
	public ButtonControl update;
	public HyperLink aIAddSymbol;
	public BaseWebElementControl aiSecondaryMartgageeError;

	public HyperLink aIUseAutocomplteLink;
	public ButtonControl okButton;
	public ButtonControl prodSubmitButton;

	public CheckBoxControl buildingSelection;
	public RadioButtonControl aIByLocation;
	public ButtonControl aIRelationShipArrow;
	public ButtonControl aIRelationShipOption;
	public TextFieldControl aiZipCodeQ3;
	public TextFieldControl zip;

	public EditAdditionalInterestInformationPage() {
		PageObject pageobject = new PageObject("EditAdditionalInterestInformation");
		aITypeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_AITypeArrow")));
		aITypeOption = new HyperLink(By.xpath(pageobject.getXpath("xp_AITypeOption")));
		aIRankArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_RankArrow")));
		aIRankoption = new HyperLink(By.xpath(pageobject.getXpath("xp_RankOption")));
		aILoanNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_LoanNumber")));
		deleteAdditionalInterest = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DeleteAdditionalInterestSymbol")));
		deleteAdditionalInterest_No = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DeleteAdditionalInterest_No")));
		deleteAdditionalInterest_Yes = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_DeleteAdditionalInterest_Yes")));
		aIName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIName")));
		aICountry = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Country")));
		aIAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Address")));
		aIAddressLine2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine2")));
		aIEnterAddressManuallyLink = new HyperLink(By.xpath(pageobject.getXpath("xp_EnterAddressManually")));
		aIManualAddressLine1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine1")));
		aIManualAddressLine2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine2")));
		aICity = new TextFieldControl(By.xpath(pageobject.getXpath("xp_City")));
		aIState = new TextFieldControl(By.xpath(pageobject.getXpath("xp_State")));
		aIZipCode1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Zipcode")));
		aIZipCode2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipcodePlus4")));
		postalCode = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PostalCode")));

		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		update = new ButtonControl(By.xpath(pageobject.getXpath("xp_Update")));
		aIAddSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_AddAdditionalInterest")));
		aiSecondaryMartgageeError = new BaseWebElementControl(
				By.xpath(pageobject.getXpath("xp_AISecondaryMortgageeError")));

		aIUseAutocomplteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_AutoComplete")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_OKButton")));
		prodSubmitButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ProdSubmitButton")));

		buildingSelection = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_BuildingSelection")));
		aIByLocation = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AIByLocation")));
		aIRelationShipArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_AIRelationShipArrow")));
		aIRelationShipOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_AIRelationShipOption")));
		aiZipCodeQ3 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AIZipCodeQ3")));
		zip = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Zip")));
	}

	public void addAdditionalInterest(Map<String, String> Data) {
		for (int addintrstNo = 1; addintrstNo < 4; addintrstNo++) {
			if (!Data.get(addintrstNo + "-AIType").equals("")) {
				if (addintrstNo > 1) {
					aIAddSymbol.scrollToElement();
					aIAddSymbol.click();
				}
				waitTime(3);
				aITypeArrow.scrollToElement();
				aITypeArrow.click();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).scrollToElement();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).click();
				if (Data.get(addintrstNo + "-AIType").equalsIgnoreCase("Mortgagee")) {
					if (aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsPresent()
							&& aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsDisplayed()) {
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).scrollToElement();
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).click();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.waitTillVisibilityOfElement(60);
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.scrollToElement();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank")).click();
					}
				}
				aIName.scrollToElement();
				aIName.setData(Data.get(addintrstNo + "-AIName"));
				if (!Data.get(addintrstNo + "-AILoanNumber").equals("")) {
					aILoanNumber.scrollToElement();
					aILoanNumber.setData(Data.get(addintrstNo + "-AILoanNumber"));
				}
				aIEnterAddressManuallyLink.scrollToElement();
				aIEnterAddressManuallyLink.click();
				aIManualAddressLine1.waitTillVisibilityOfElement(60);
				aIManualAddressLine1.setData(Data.get(addintrstNo + "-AIAddr1"));
				aIManualAddressLine2.setData(Data.get(addintrstNo + "-AIAddr2"));
				aICity.setData(Data.get(addintrstNo + "-AICity"));
				aIState.setData(Data.get(addintrstNo + "-AIState"));
				TextFieldControl code = aIZipCode1.formatDynamicPath((addintrstNo - 1));
				code.setData(Data.get(addintrstNo + "-AIZIP"));
			}
		}
	}

	public void addAdditionalInterestNAHO(Map<String, String> Data) {
		for (int addintrstNo = 1; addintrstNo < 4; addintrstNo++) {
			if (!Data.get(addintrstNo + "-AIType").equals("")) {
				if (addintrstNo > 1) {
					aIAddSymbol.scrollToElement();
					aIAddSymbol.click();
				}
				waitTime(3);
				aITypeArrow.scrollToElement();
				aITypeArrow.click();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).scrollToElement();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).click();
				if (Data.get(addintrstNo + "-AIType").equalsIgnoreCase("Mortgagee")) {
					if (aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsPresent()
							&& aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsDisplayed()) {
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).scrollToElement();
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).click();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.waitTillVisibilityOfElement((60));
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.scrollToElement();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank")).click();
					}
				}

				if (aIRelationShipArrow.formatDynamicPath(addintrstNo - 1).checkIfElementIsPresent()
						&& aIRelationShipArrow.formatDynamicPath(addintrstNo - 1).checkIfElementIsDisplayed()) {
					aIRelationShipArrow.formatDynamicPath(addintrstNo - 1).scrollToElement();
					aIRelationShipArrow.formatDynamicPath(addintrstNo - 1).click();

					aIRelationShipOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRelationship"))
							.scrollToElement();
					aIRelationShipOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRelationship"))
							.click();
					Assertions.passTest("Request Bind Page",
							"Additional Interest Relationship Type : " + Data.get(addintrstNo + "-AIRelationship"));
				}
				aIName.scrollToElement();
				aIName.setData(Data.get(addintrstNo + "-AIName"));
				if (!Data.get(addintrstNo + "-AILoanNumber").equals("")) {
					aILoanNumber.scrollToElement();
					aILoanNumber.setData(Data.get(addintrstNo + "-AILoanNumber"));
				}

				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
						aICountry.scrollToElement();
						aICountry.setData(Data.get(addintrstNo + "-AICountry"));
						Assertions.passTest("Edit Additional Interest Information Page",
								"Additional Interest Country : " + aICountry.getData());
					}
				}

				if (Data.containsKey(addintrstNo + "-AICountry") && !Data.get(addintrstNo + "-AICountry").equals("")) {
					if (!Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
						WebElement ele = WebDriverManager.getCurrentDriver()
								.findElement(By.xpath("(//input[contains(@id,'address.country')])[last()]"));
						ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
						ele.sendKeys(Data.get(addintrstNo + "-AICountry"));
						ele.sendKeys(Keys.ARROW_RIGHT);
						waitTime(5);
						List<WebElement> listOfElements = WebDriverManager.getCurrentDriver()
								.findElements(By.xpath("//select[contains(@id,'address.country')]//option"));
						waitTime(5);

						for (WebElement listOfElement : listOfElements) {
							if (listOfElement.equals(Data.get(addintrstNo + "-AICountry"))) {
								listOfElement.click();
								break;
							}
						}
					}
				}
				if (aIEnterAddressManuallyLink.checkIfElementIsPresent()
						&& aIEnterAddressManuallyLink.checkIfElementIsDisplayed()) {
					aIEnterAddressManuallyLink.waitTillPresenceOfElement(60);
					aIEnterAddressManuallyLink.waitTillVisibilityOfElement(60);
					aIEnterAddressManuallyLink.scrollToElement();
					aIEnterAddressManuallyLink.click();
					aIManualAddressLine1.waitTillVisibilityOfElement(60);
				}
				aIManualAddressLine1.setData(Data.get(addintrstNo + "-AIAddr1"));
				aIManualAddressLine2.setData(Data.get(addintrstNo + "-AIAddr2"));
				if (aICity.checkIfElementIsPresent() && aICity.checkIfElementIsDisplayed()) {
					aICity.setData(Data.get(addintrstNo + "-AICity"));
				}
				if (aIState.checkIfElementIsPresent() && aIState.checkIfElementIsDisplayed()) {
					aIState.setData(Data.get(addintrstNo + "-AIState"));
				}
				if (aIZipCode1.checkIfElementIsPresent() && aIZipCode1.checkIfElementIsDisplayed()) {
					TextFieldControl code = aIZipCode1.formatDynamicPath((addintrstNo - 1));
					code.setData(Data.get(addintrstNo + "-AIZIP"));
				} else if (!Data.get(addintrstNo + "-AIZIP").equals("")
						&& aiZipCodeQ3.formatDynamicPath(addintrstNo - 1).checkIfElementIsPresent()
						&& aiZipCodeQ3.formatDynamicPath(addintrstNo - 1).checkIfElementIsDisplayed()) {
					aiZipCodeQ3.formatDynamicPath(addintrstNo - 1).scrollToElement();
					aiZipCodeQ3.formatDynamicPath(addintrstNo - 1).setData(Data.get(addintrstNo + "-AIZIP"));
				} else if (postalCode.formatDynamicPath(addintrstNo - 1).checkIfElementIsPresent()
						&& postalCode.formatDynamicPath(addintrstNo - 1).checkIfElementIsDisplayed()) {
					TextFieldControl code = postalCode.formatDynamicPath((addintrstNo - 1));
					code.setData(Data.get(addintrstNo + "-AIZIP"));
				}

			}
		}
	}
	public void addAdditionalInterestHIHO(Map<String, String> Data) {
		for (int addintrstNo = 1; addintrstNo < 3; addintrstNo++) {
			if (!Data.get(addintrstNo + "-AIType").equals("")) {
				if (addintrstNo > 1) {
					aIAddSymbol.scrollToElement();
					aIAddSymbol.click();
				}
				aITypeArrow.scrollToElement();
				aITypeArrow.click();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType"))
						.scrollToElement();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).click();
				aIName.setData(Data.get(addintrstNo + "-AIName"));
				if (Data.get(addintrstNo + "-AIType").equalsIgnoreCase("Mortgagee")) {
					if (aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsPresent()
							&& aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsDisplayed()) {
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).scrollToElement();
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).click();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.waitTillVisibilityOfElement(60);
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.scrollToElement();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank")).click();
					}
				}
				aILoanNumber.setData(Data.get(addintrstNo + "-AILoanNumber"));
				aILoanNumber.setData(Data.get(addintrstNo + "-AILoanNumber"));
				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
						aICountry.scrollToElement();
						aICountry.setData(Data.get(addintrstNo + "-AICountry"));
					}
				}

				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (!Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("USA")) {
						WebElement ele = WebDriverManager.getCurrentDriver()
								.findElement(By.xpath("(//input[contains(@id,'address.country')])[last()]"));
						ele.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
						ele.sendKeys(Data.get(addintrstNo + "-AICountry"));
						ele.sendKeys(Keys.ARROW_RIGHT);
						waitTime(5);

						List<WebElement> listOfElements = WebDriverManager.getCurrentDriver()
								.findElements(By.xpath("//select[contains(@id,'address.country')]//option"));
						waitTime(5);

						for (WebElement listOfElement : listOfElements) {
							if (listOfElement.equals(Data.containsKey(addintrstNo + "-AICountry"))) {
								listOfElement.click();
								break;
							}
						}
					}
				}
				if (aIEnterAddressManuallyLink.checkIfElementIsPresent()
						&& aIEnterAddressManuallyLink.checkIfElementIsDisplayed()) {
					aIEnterAddressManuallyLink.waitTillPresenceOfElement(60);
					aIEnterAddressManuallyLink.waitTillVisibilityOfElement(60);
					aIEnterAddressManuallyLink.scrollToElement();
					aIEnterAddressManuallyLink.click();
				}
				aIManualAddressLine1.setData(Data.get(addintrstNo + "-AIAddr1"));
				aIAddressLine2.setData(Data.get(addintrstNo + "-AIAddr2"));
				if (aICity.checkIfElementIsPresent() && aICity.checkIfElementIsDisplayed()) {
					aICity.setData(Data.get(addintrstNo + "-AICity"));
				}
				if (aIState.checkIfElementIsPresent() && aIState.checkIfElementIsDisplayed()) {
					aIState.setData(Data.get(addintrstNo + "-AIState"));
				}
				if(zip.formatDynamicPath(addintrstNo - 1).checkIfElementIsPresent() && zip.formatDynamicPath(addintrstNo - 1).checkIfElementIsDisplayed()){
					TextFieldControl code = zip.formatDynamicPath((addintrstNo - 1));
					code.setData(Data.get(addintrstNo + "-AIZIP"));
				} else if (aiZipCodeQ3.formatDynamicPath(addintrstNo - 1).checkIfElementIsPresent()
						&& aiZipCodeQ3.formatDynamicPath(addintrstNo - 1).checkIfElementIsDisplayed()) {
					aiZipCodeQ3.formatDynamicPath(addintrstNo - 1).setData(Data.get(addintrstNo + "-AIZIP"));
				}
			}
		}
	}
}
