/** Program Description: Object Locators and methods defined in Endorse additional interest page
 *  Author			   : SMNetserv
 *  Date of Creation   : 08/11/2017
 **/

package com.icat.epicenter.pom;

import java.util.Arrays;
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
import com.NetServAutomationFramework.util.FrameworkUtil;

public class EndorseAdditionalInterestsPage extends BasePageControl {
	public ButtonControl aITypeArrow;
	public HyperLink aITypeOption;
	public BaseWebElementControl aITypeData;
	public ButtonControl aIRankArrow;
	public HyperLink aIRankoption;
	public BaseWebElementControl aIRankData;
	public TextFieldControl aILoanNumber;

	public TextFieldControl aIName;
	public TextFieldControl aICountry;
	public TextFieldControl aIAddress;
	public TextFieldControl aIAddressLine2;
	public HyperLink aIEnterAddressManuallyLink;
	public TextFieldControl aIManualAddressLine1;
	public TextFieldControl aIManualAddressLine2;
	public TextFieldControl aIAddressLine1;
	public TextFieldControl aICity;
	public TextFieldControl aIState;
	public TextFieldControl aIZipCode1;
	public TextFieldControl aIZipCode2;

	public HyperLink aIUseAutocomplteLink;
	public RadioButtonControl aIByPolicy;
	public RadioButtonControl aIByLocation;
	public TextFieldControl zip;
	public CheckBoxControl buildingSelection;
	public HyperLink aIAddSymbol;
	public ButtonControl completeWithRollForwarButton;

	public HyperLink no_DeleteAi;
	public HyperLink yes_DeleteAi;
	public ButtonControl cancelButton;
	public ButtonControl okButton;
	public ButtonControl aIIntlCountry;
	public HyperLink deleteIcon;

	public EndorseAdditionalInterestsPage() {
		PageObject pageobject = new PageObject("EndorseAdditionalInterests");
		aITypeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_AITypeArrow")));
		aITypeOption = new HyperLink(By.xpath(pageobject.getXpath("xp_AITypeOption")));
		aITypeData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AITypeData")));
		aIRankArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_RankArrow")));
		aIRankoption = new HyperLink(By.xpath(pageobject.getXpath("xp_RankOption")));
		aIRankData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIRankData")));
		aILoanNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_LoanNumber")));
		aIName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AdditionalInterestName")));

		aICountry = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AICountry")));
		aIAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Address")));
		aIAddressLine2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine2")));
		aIEnterAddressManuallyLink = new HyperLink(By.xpath(pageobject.getXpath("xp_EnterAddressManually")));
		aIManualAddressLine1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine1")));
		aIManualAddressLine2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine2")));
		aIAddressLine1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AddressLine1")));
		aICity = new TextFieldControl(By.xpath(pageobject.getXpath("xp_City")));
		aIState = new TextFieldControl(By.xpath(pageobject.getXpath("xp_State")));
		aIZipCode1 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Zipcode")));
		aIZipCode2 = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipcodePlus4")));

		aIUseAutocomplteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_AutoComplete")));
		aIByPolicy = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AIByPolicy")));
		aIByLocation = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_AIByLocation")));
		zip = new TextFieldControl(By.xpath(pageobject.getXpath("xp_zip")));
		buildingSelection = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_BuildingSelection")));
		aIAddSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_AddAdditionalInterest")));
		completeWithRollForwarButton = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_CompleteWithRollForwarButton")));

		no_DeleteAi = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteAdditionalInterest_No")));
		yes_DeleteAi = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteAdditionalInterest_Yes")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancelbutton")));
		okButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_okButton")));
		aIIntlCountry = new ButtonControl(By.xpath(pageobject.getXpath("xp_AIIntlCountry")));
		deleteIcon = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteIcon")));

	}

	public void deleteAdditionalInterestPB(Map<String, String> Data) {
		if (Data.get("DeleteAllExistingAI") != null) {
			if (Data.get("DeleteAllExistingAI").equalsIgnoreCase("yes")) {
				Assertions.addInfo("Additional Interest - Deleted", "");
				ButtonControl deleteButton = new ButtonControl(By.xpath("//div[@class='sp-trash sp-bg']"));
				int deleteAI = deleteButton.getNoOfWebElements();
				for (int i = 1; i <= deleteAI; i++) {
					deleteButton.scrollToElement();
					deleteButton.click();
					yes_DeleteAi.waitTillVisibilityOfElement(BasePageControl.TIME_OUT_SIXTY_SECS);
					yes_DeleteAi.click();
				}
			}
			if (Data.get("DeleteAlByAIType") != null) {
				if (!Data.get("DeleteAlByAIType").equals("")) {
					Assertions.addInfo("Additional Interest - Deleted", "");
					List<String> aI_Type = Arrays.asList(Data.get("DeleteAlByAIType").split(","));
					for (String element : aI_Type) {
						ButtonControl deleteButton = new ButtonControl(By.xpath("//span[span[contains(text(),'"
								+ element + "')]]/../..//following-sibling::div[3]/a/div"));
						deleteButton.scrollToElement();
						deleteButton.click();
						yes_DeleteAi.waitTillVisibilityOfElement(60);
						yes_DeleteAi.click();
					}
				}
			}
		}
	}

	public void addAdditionalInterest(Map<String, String> Data) {
		for (int addintrstNo = 1; addintrstNo < 4; addintrstNo++) {
			System.out.println("addintrstNo = " + addintrstNo);
			if (Data.get(addintrstNo + "-AddAI").equalsIgnoreCase("yes")) {
				System.out.println("About to click AI add symbol");
				System.out.println("addintrstNo = " + addintrstNo);
				aIAddSymbol.scrollToElement();
				aIAddSymbol.click();

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

				List<String> applicability = Arrays.asList(Data.get(addintrstNo + "-AIApplicability").split(","));
				for (int j = 0; j < applicability.size(); j++) {
					buildingSelection.formatDynamicPath((addintrstNo - 1), applicability.get(j)).scrollToElement();
					buildingSelection.formatDynamicPath((addintrstNo - 1), applicability.get(j)).select();
				}
			}
		}
	}

	public void modifyAdditionalInterest(Map<String, String> Data) {
		Assertions.addInfo("Additional Interest - Modified", "");
		for (int addintrstNo = 1; addintrstNo < 4; addintrstNo++) {
			if (!Data.get(addintrstNo + "-AIType").equals("")) {
				aITypeData.scrollToElement();
				Assertions.addInfo("Endorse Additional Interest Page",
						"Additional Interest Type original Value : " + aITypeData.getData());
				aITypeArrow.formatDynamicPath(addintrstNo).scrollToElement();
				aITypeArrow.formatDynamicPath(addintrstNo).click();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).scrollToElement();
				aITypeOption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIType")).click();
				Assertions.passTest("Endorse Additional Interest Page",
						"Additional Interest Type Latest Value : " + aITypeData.getData());
				if (!Data.get(addintrstNo + "-AIName").equals("")) {
					Assertions.addInfo("Endorse Additional Interest Page", "Additional Interest Name original Value : "
							+ aIName.formatDynamicPath(addintrstNo).getData());
					aIName.formatDynamicPath(addintrstNo).setData(Data.get(addintrstNo + "-AIName"));
					Assertions.passTest("Endorse Additional Interest Page", "Additional Interest Name Latest Value : "
							+ aIName.formatDynamicPath(addintrstNo).getData());
				}
				if (Data.get(addintrstNo + "-AIType").equalsIgnoreCase("Mortgagee")) {
					if (aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsPresent()
							&& aIRankArrow.formatDynamicPath((addintrstNo - 1)).checkIfElementIsDisplayed()) {
						aIRankData.scrollToElement();
						Assertions.addInfo("Endorse Additional Interest Page",
								"Additional Interest Rank original Value : " + aIRankData.getData());
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).scrollToElement();
						aIRankArrow.formatDynamicPath((addintrstNo - 1)).click();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.waitTillVisibilityOfElement(60);
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank"))
								.scrollToElement();
						aIRankoption.formatDynamicPath((addintrstNo - 1), Data.get(addintrstNo + "-AIRank")).click();
						Assertions.passTest("Endorse Additional Interest Page",
								"Additional Interest Rank Latest Value : " + aIRankData.getData());
					}
				}
				if (!Data.get(addintrstNo + "-AILoanNumber").equals("")) {
					Assertions.addInfo("Endorse Additional Interest Page",
							"Additional Interest Loan Number original Value : "
									+ aILoanNumber.formatDynamicPath(addintrstNo).getData());
					aILoanNumber.formatDynamicPath(addintrstNo).setData(Data.get(addintrstNo + "-AILoanNumber"));
					Assertions.passTest("Endorse Additional Interest Page",
							"Additional Interest Loan Number Latest Value : "
									+ aILoanNumber.formatDynamicPath(addintrstNo).getData());
				}
				if (Data.containsKey(addintrstNo + "-AICountry")) {
					if (!Data.get(addintrstNo + "-AICountry").equalsIgnoreCase("")) {
						Assertions.addInfo("Endorse Additional Interest Page",
								"Additional Interest Country original Value : " + aICountry.getData());
						aICountry.formatDynamicPath(addintrstNo).scrollToElement();
						WebElement ele = WebDriverManager.getCurrentDriver().findElement(
								By.xpath("(//input[contains(@id,'address.country')])[" + addintrstNo + "]"));
						ele.sendKeys(Keys.chord(FrameworkUtil.KEY_CMD_CTRL, "a"), Data.get(addintrstNo + "-AICountry"));
						Assertions.passTest("Endorse Additional Interest Page",
								"Additional Interest Country Latest Value : " + aICountry.getData());
						aICountry.formatDynamicPath(addintrstNo).tab();
					}
				}
				if (aIEnterAddressManuallyLink.formatDynamicPath(addintrstNo).checkIfElementIsPresent()
						&& aIEnterAddressManuallyLink.formatDynamicPath(addintrstNo).checkIfElementIsDisplayed()) {
					aIEnterAddressManuallyLink.formatDynamicPath(addintrstNo).scrollToElement();
					aIEnterAddressManuallyLink.formatDynamicPath(addintrstNo).click();
				}
				if (!Data.get(addintrstNo + "-AIAddr1").equals("")) {
					Assertions.addInfo("Endorse Additional Interest Page",
							"Additional Interest Address Line1 original Value : "
									+ aIAddressLine1.formatDynamicPath(addintrstNo).getData().replace(",", ""));
					aIAddressLine1.formatDynamicPath(addintrstNo).setData(Data.get(addintrstNo + "-AIAddr1"));
					Assertions.passTest("Endorse Additional Interest Page",
							"Additional Interest Address Line1 Latest Value : "
									+ aIAddressLine1.formatDynamicPath(addintrstNo).getData().replace(",", ""));
				}
				if (!Data.get(addintrstNo + "-AIAddr2").equals("")) {
					Assertions.addInfo("Endorse Additional Interest Page",
							"Additional Interest Address Line2 original Value : "
									+ aIAddressLine2.formatDynamicPath(addintrstNo).getData());
					aIAddressLine2.formatDynamicPath(addintrstNo).setData(Data.get(addintrstNo + "-AIAddr2"));
					Assertions.passTest("Endorse Additional Interest Page",
							"Additional Interest Address Line2 Latest Value : "
									+ aIAddressLine2.formatDynamicPath(addintrstNo).getData());
				}
				if (!Data.get(addintrstNo + "-AICity").equals("")) {
					Assertions.addInfo("Endorse Additional Interest Page", "Additional Interest City original Value : "
							+ aICity.formatDynamicPath(addintrstNo).getData());
					aICity.formatDynamicPath(addintrstNo).setData(Data.get(addintrstNo + "-AICity"));
					Assertions.passTest("Endorse Additional Interest Page", "Additional Interest City Latest Value : "
							+ aICity.formatDynamicPath(addintrstNo).getData());
				}
				if (!Data.get(addintrstNo + "-AIState").equals("")) {
					Assertions.addInfo("Endorse Additional Interest Page", "Additional Interest State original Value : "
							+ aIState.formatDynamicPath(addintrstNo).getData());
					aIState.formatDynamicPath(addintrstNo).setData(Data.get(addintrstNo + "-AIState"));
					Assertions.passTest("Endorse Additional Interest Page", "Additional Interest State Latest Value : "
							+ aIState.formatDynamicPath(addintrstNo).getData());
				}
				if (!Data.get(addintrstNo + "-AIZIP").equals("")) {
					TextFieldControl code = zip.formatDynamicPath((addintrstNo - 1));
					Assertions.addInfo("Endorse Additional Interest Page",
							"Additional Interest Zipcode original Value : " + code.getData());
					code.setData(Data.get(addintrstNo + "-AIZIP"));
					Assertions.passTest("Endorse Additional Interest Page",
							"Additional Interest Zipcode Latest Value : " + code.getData());
				}
				if (aIByLocation.formatDynamicPath(addintrstNo).checkIfElementIsPresent()
						&& aIByLocation.formatDynamicPath(addintrstNo).checkIfElementIsEnabled()
						&& aIByLocation.formatDynamicPath(addintrstNo).checkIfElementIsDisplayed()) {
					aIByLocation.formatDynamicPath(addintrstNo).scrollToElement();
					aIByLocation.formatDynamicPath(addintrstNo).click();
					List<String> applicability = Arrays.asList(Data.get(addintrstNo + "-AIApplicability").split(","));
					for (int j = 0; j < applicability.size(); j++) {
						System.out.println("In setting by building j = " + j);
						System.out.println("addintrstNo = " + addintrstNo);
						waitTime(5);
						buildingSelection.formatDynamicPath((addintrstNo - 1), applicability.get(j)).scrollToElement();
						buildingSelection.formatDynamicPath((addintrstNo - 1), applicability.get(j)).select();
					}
				}
			}
		}
	}

	public void enterEndorsementAdditionalInterestDetails(Map<String, String> Data) {
		deleteAdditionalInterestPB(Data);
		if (!Data.get("ModifyExistingAI").equalsIgnoreCase("")) {
			modifyAdditionalInterest(Data);
		}

		//not sure if this works - the RequestBindPage elements are different from the EndorseAdditionalInterestPage elements
		if (Data.get("ModifyExistingAI").equalsIgnoreCase("") && (!Data.get("1-AIType").equalsIgnoreCase("")
				|| !Data.get("2-AIType").equalsIgnoreCase("") || !Data.get("3-AIType").equalsIgnoreCase(""))) {
			System.out.println("About to use Request Bind elements");
			RequestBindPage requestBindPage = new RequestBindPage();
			requestBindPage.addAdditionalInterestforPNB(Data);
		}

		if(Data.containsKey("AddNewAI")) {
			if (!Data.get("AddNewAI").equalsIgnoreCase("")) {
				modifyAdditionalInterest(Data);
			}
		}

		okButton.scrollToElement();
		okButton.click();
		okButton.waitTillInVisibilityOfElement(60);
	}

	public void enterEndorsementAdditionalInterestDetailsNAHO(Map<String, String> Data, String productSelection) {
		deleteAdditionalInterestPB(Data);
		if (!Data.get("ModifyExistingAI").equalsIgnoreCase("")) {
			modifyAdditionalInterest(Data);
		}
		if (Data.get("ModifyExistingAI").equalsIgnoreCase("") && (!Data.get("1-AIType").equalsIgnoreCase("")
				|| !Data.get("2-AIType").equalsIgnoreCase("") || !Data.get("3-AIType").equalsIgnoreCase(""))) {
			RequestBindPage requestBindPage = new RequestBindPage();

			if (productSelection.equals("Residential Non-Admitted"))
				requestBindPage.addAdditionalInterest(Data);

			else
				requestBindPage.addAdditionalInterestforPNB(Data);
		}
		okButton.scrollToElement();
		okButton.click();
		okButton.waitTillInVisibilityOfElement(60);
	}

}
