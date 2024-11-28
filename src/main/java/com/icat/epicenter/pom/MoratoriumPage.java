/** Program Description: Object Locators and methods defined in Moratorium page
 *  Author			   : John
 *  Date of Creation   : 26/09/19
**/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class MoratoriumPage {
	public HyperLink createMoratoriumLink;
	public TextFieldControl startTimeField;
	public TextFieldControl endTimeField;
	public CheckBoxControl noLossLetterCheckBox;
	public ButtonControl perilSelectionArrow;
	public ButtonControl perilSelectionOption;
	public ButtonControl businessUnitSelectionArrow;
	public ButtonControl businessUnitSelectionOption;
	public TextFieldControl descriptionField;
	public TextFieldControl zipCodeField;
	public ButtonControl submitBtn;
	public ButtonControl cancelBtn;
	public BaseWebElementControl moratriumCreatedMsg;
	public TextFieldControl noLossLetterEndDate;
	public TextFieldControl noLossLetterRenewalBDT;
	public TextFieldControl noLossLetterNoticeText;
	public HyperLink deleteLink;
	public HyperLink yesLink;
	public BaseWebElementControl moratoriumDeleteMsg;

	public HomePage homePage;
	public HealthDashBoardPage healthDashBoardPage;

	public MoratoriumPage() {
		PageObject pageobject = new PageObject("Moratorium");
		createMoratoriumLink = new HyperLink(By.xpath(pageobject.getXpath("xp_CreateMoratoriumLink")));
		startTimeField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_StartTimeField")));
		endTimeField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EndTimeField")));
		noLossLetterCheckBox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_NoLossLetterCheckBox")));
		perilSelectionArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_PerilSelectionArrow")));
		perilSelectionOption = new ButtonControl(By.xpath(pageobject.getXpath("xp_PerilSelectionOption")));
		businessUnitSelectionArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_BusinessUnitSelectionArrow")));
		businessUnitSelectionOption = new ButtonControl(
				By.xpath(pageobject.getXpath("xp_BusinessUnitSelectionOption")));
		descriptionField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_DescriptionField")));
		zipCodeField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ZipCodeField")));
		submitBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_SubmitBtn")));
		cancelBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelBtn")));
		moratriumCreatedMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoratriumCreatedMsg")));
		noLossLetterEndDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoLossLetterEndDate")));
		noLossLetterRenewalBDT = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoLossLetterRenewalBDT")));
		noLossLetterNoticeText = new TextFieldControl(By.xpath(pageobject.getXpath("xp_NoLossLetterNoticeText")));
		deleteLink = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteLink")));
		yesLink = new HyperLink(By.xpath(pageobject.getXpath("xp_Yes")));
		moratoriumDeleteMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MoratoriumDeleteMsg")));

		homePage = new HomePage();
		healthDashBoardPage = new HealthDashBoardPage();
	}

	public void enterMoratoriumDetails(Map<String, String> data) {

		if(data.get("MoratoriumStartDate") !=null) {
			startTimeField.setData(data.get("MoratoriumStartDate"));
			endTimeField.setData(data.get("MoratoriumEndDate"));
		}else if(data.get("StartDate") !=null){
			startTimeField.setData(data.get("StartDate"));
			endTimeField.setData(data.get("EndDate"));
		}


		if (!noLossLetterCheckBox.checkIfElementIsSelected() && data.get("NoPriorLoss") != null
				&& data.get("NoPriorLoss").equalsIgnoreCase("Yes")) {
			noLossLetterCheckBox.scrollToElement();
			noLossLetterCheckBox.select();
			noLossLetterEndDate.setData(data.get("NoLossLetterEndDate"));
			noLossLetterRenewalBDT.setData(data.get("NoLossLetterRenewalBDT"));
			noLossLetterNoticeText.setData(data.get("NoLossLetterNoticeText"));
		}
		perilSelectionArrow.scrollToElement();
		perilSelectionArrow.click();
		perilSelectionOption.formatDynamicPath(data.get("PerilSelection")).scrollToElement();
		perilSelectionOption.formatDynamicPath(data.get("PerilSelection")).click();
		businessUnitSelectionArrow.scrollToElement();
		businessUnitSelectionArrow.click();
		businessUnitSelectionOption.formatDynamicPath(data.get("BusinessUnitSelection")).scrollToElement();
		businessUnitSelectionOption.formatDynamicPath(data.get("BusinessUnitSelection")).click();
		descriptionField.setData(data.get("MoratoriumDescription"));
		zipCodeField.setData(data.get("InsuredZIP"));
		submitBtn.scrollToElement();
		submitBtn.click();
	}

	public void deleteMoratorium(String moratoriumName) {
		homePage.goToHomepage.click();
		homePage.adminLink.scrollToElement();
		homePage.adminLink.click();
		healthDashBoardPage.moratoriumLink.scrollToElement();
		healthDashBoardPage.moratoriumLink.click();
		deleteLink.formatDynamicPath(moratoriumName).scrollToElement();
		deleteLink.formatDynamicPath(moratoriumName).click();
		yesLink.waitTillVisibilityOfElement(60);
		yesLink.click();
	}
}
