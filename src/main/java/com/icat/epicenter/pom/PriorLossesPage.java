package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class PriorLossesPage extends BasePageControl {
	public RadioButtonControl lossesInThreeYearsYes;
	public RadioButtonControl lossesInThreeYearsNo;
	public HyperLink typeOfLossArrow;
	public HyperLink typeOfLossOption;

	public TextFieldControl dateOfLoss;
	public TextFieldControl grossLossAmount;
	public RadioButtonControl damagesRepairedRadioYes;
	public RadioButtonControl damagesRepairedRadioNo;
	public ButtonControl continueButton;
	public ButtonControl cancelButton;

	public BaseWebElementControl pageName;
	public ButtonControl addButton;
	public BaseWebElementControl priorLossData;
	public ButtonControl deletePriorLoss;

	public RadioButtonControl openClaimYes;
	public RadioButtonControl openClaimNo;

	public BaseWebElementControl priorLossQuestions;
	public HyperLink deleteSymbol;
	public BaseWebElementControl lossHistoryAlert;

	public BaseWebElementControl claimsMessage;
	public BaseWebElementControl priorLossData1;
	public ButtonControl goToAccountOverviewBtn;

	public PriorLossesPage() {
		PageObject pageobject = new PageObject("PriorLosses");
		lossesInThreeYearsYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_LossesInThreeYearsYes")));
		lossesInThreeYearsNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_LossesInThreeYearsNo")));
		typeOfLossArrow = new HyperLink(By.xpath(pageobject.getXpath("xp_TypeOfLossArrow")));
		typeOfLossOption = new HyperLink(By.xpath(pageobject.getXpath("xp_TypeOfLossOption")));

		dateOfLoss = new TextFieldControl(By.xpath(pageobject.getXpath("xp_DateOfLoss")));
		grossLossAmount = new TextFieldControl(By.xpath(pageobject.getXpath("xp_GrossLossAmount")));
		damagesRepairedRadioYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_DamagesRepairedRadioYes")));
		damagesRepairedRadioNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_DamagesRepairedRadioNo")));
		continueButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinueButton")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));

		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		addButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_AddButton")));
		priorLossData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossData")));
		deletePriorLoss = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeletePriorLoss")));
		openClaimYes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ClaimYes")));
		openClaimNo = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_ClaimNo")));

		priorLossQuestions = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossQuestions")));
		deleteSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteSymbol")));
		lossHistoryAlert = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LossHistoryAlert")));

		claimsMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ClaimsMessage")));
		priorLossData1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossData1")));
		goToAccountOverviewBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_GotoAccountOverviewPage")));

	}

	public void selectPriorLossesInformation(Map<String, String> data) {
		boolean loss = true;
		for (int i = 0; i < 6; i++) {
			if (data.get("PriorLoss" + (i + 1)) != null) {
				if (!data.get("PriorLoss" + (i + 1)).equals("")) {
					if (i > 0) {
						addButton.scrollToElement();
						addButton.click();
					}
					if (data.get("PriorLoss" + (i + 1)).equalsIgnoreCase("Yes")) {
						lossesInThreeYearsYes.scrollToElement();
						lossesInThreeYearsYes.click();
						typeOfLossArrow.scrollToElement();
						typeOfLossArrow.click();

						typeOfLossOption.formatDynamicPath(i, data.get("PriorLossType" + (i + 1))).scrollToElement();
						typeOfLossOption.formatDynamicPath(i, data.get("PriorLossType" + (i + 1))).click();
						dateOfLoss.setData(data.get("PriorLossDate" + (i + 1)));
						grossLossAmount.setData(data.get("PriorLossAmount" + (i + 1)));
						if (data.get("IsPriorLossDamageRepaired?" + (i + 1)).equalsIgnoreCase("Yes")) {
							damagesRepairedRadioYes.click();
						} else {
							damagesRepairedRadioNo.click();
						}
						if(openClaimYes.checkIfElementIsPresent()&&openClaimYes.checkIfElementIsDisplayed())
						{
						if (data.get("IsThereAnOpenClaim?" + (i + 1)).equalsIgnoreCase("Yes")) {
							openClaimYes.click();
						} else {
							openClaimNo.click();
						}
						}
						Assertions.passTest("Prior Loss Page", "Prior Losses " + (i + 1) + " entered successfully");
					} else {
						lossesInThreeYearsNo.scrollToElement();
						lossesInThreeYearsNo.click();
						loss = false;
						Assertions.passTest("Prior Loss Page", "No prior losses selected");
					}
				}
			}
			if (!loss) {
				break;
			}
		}
		continueButton.scrollToElement();
		continueButton.click();

	}

	public void editPriorLossesInformation(Map<String, String> data) {
		for (int i = 0; i < 5; i++) {
			if (!data.get("PriorLoss" + (i + 1)).equals("")) {
				if (i > 0) {
					addButton.scrollToElement();
					addButton.click();
				}
				if (data.get("PriorLoss" + (i + 1)).equalsIgnoreCase("Yes")) {

					if (lossesInThreeYearsYes.checkIfElementIsSelected()) {
						Assertions.addInfo("Prior Loss Page", "Loss in last 3 years original Value : " + "Yes");
					}

					if (lossesInThreeYearsNo.checkIfElementIsSelected()) {
						Assertions.addInfo("Prior Loss Page", "Loss in last 3 years original Value : " + "No");
					}

					lossesInThreeYearsYes.scrollToElement();
					lossesInThreeYearsYes.click();
					Assertions.passTest("Prior Loss Page", "Loss in last 3 years Latest Value : " + "Yes");

					typeOfLossArrow.click();
					Assertions.addInfo("Prior Loss Page",
							"Type of Loss original Value : " + priorLossData.formatDynamicPath(i).getData());
					typeOfLossOption.formatDynamicPath(i, data.get("PriorLossType" + (i + 1))).scrollToElement();
					typeOfLossOption.formatDynamicPath(i, data.get("PriorLossType" + (i + 1))).click();
					Assertions.passTest("Prior Loss Page",
							"Type of Loss Latest Value : " + priorLossData.formatDynamicPath(i).getData());

					Assertions.addInfo("Prior Loss Page", "Date of Loss original Value : " + dateOfLoss.getData());
					dateOfLoss.setData(data.get("PriorLossDate" + (i + 1)));
					Assertions.passTest("Prior Loss Page", "Date of Loss Latest Value : " + dateOfLoss.getData());

					Assertions.addInfo("Prior Loss Page",
							"Gross Loss Amount original Value : " + grossLossAmount.getData());
					grossLossAmount.setData(data.get("PriorLossAmount" + (i + 1)));
					Assertions.passTest("Prior Loss Page",
							"Gross Loss Amount Latest Value : " + grossLossAmount.getData());

					if (damagesRepairedRadioYes.checkIfElementIsSelected()) {
						Assertions.addInfo("Prior Loss Page", "Have the damages repaired original Value : " + "Yes");
					}

					if (damagesRepairedRadioNo.checkIfElementIsSelected()) {
						Assertions.addInfo("Prior Loss Page", "Have the damages repaired original Value : " + "No");
					}

					if (!damagesRepairedRadioYes.checkIfElementIsSelected()
							&& !damagesRepairedRadioNo.checkIfElementIsSelected()) {
						Assertions.addInfo("Prior Loss Page", "Have the damages repaired original Value : " + "Null");
					}

					if (data.get("IsPriorLossDamageRepaired?" + (i + 1)).equalsIgnoreCase("Yes")) {
						damagesRepairedRadioYes.click();
						Assertions.passTest("Prior Loss Page", "Have the damages repaired Latest Value : " + "Yes");
					} else {
						damagesRepairedRadioNo.click();
						Assertions.passTest("Prior Loss Page", "Have the damages repaired Latest Value : " + "No");
					}
				} else {
					lossesInThreeYearsNo.scrollToElement();
					lossesInThreeYearsNo.click();
					Assertions.passTest("Prior Loss Page", "Loss in last 3 years Latest Value : " + "No");
				}
			}
		}
		continueButton.scrollToElement();
		continueButton.click();

	}

	public void deletePriorLoss(Map<String, String> Data) {
		deleteSymbol.formatDynamicPath(Data.get("PriorLossDelete")).scrollToElement();
		deleteSymbol.formatDynamicPath(Data.get("PriorLossDelete")).click();
		continueButton.scrollToElement();
		continueButton.click();
		Assertions.addInfo("Prior Loss " + Data.get("PriorLossDelete") + " - Deleted", "");
	}
}
