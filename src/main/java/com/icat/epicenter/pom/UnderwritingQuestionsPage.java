package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.RadioButtonControl;

public class UnderwritingQuestionsPage extends BasePageControl {
	public ButtonControl answerNoToAllQuestions;

	public RadioButtonControl developerSpeculationHome_Yes;
	public RadioButtonControl developerSpeculationHome_No;
	public RadioButtonControl unrepairedDamage_Yes;
	public RadioButtonControl unrepairedDamage_No;
	public RadioButtonControl commercialBusinessExposure_Yes;
	public RadioButtonControl commercialBusinessExposure_No;
	public RadioButtonControl namedInsuredCancelled_Yes;
	public RadioButtonControl namedInsuredCancelled_No;
	public RadioButtonControl targetRisk_Yes;
	public RadioButtonControl targetRisk_No;
	public RadioButtonControl specialtyHome_Yes;
	public RadioButtonControl specialtyHome_No;
	public RadioButtonControl polybutylenePlumbing_Yes;
	public RadioButtonControl polybutylenePlumbing_No;
	public RadioButtonControl heatSource_Yes;
	public RadioButtonControl heatSource_No;
	public RadioButtonControl standardTankSystem_Yes;
	public RadioButtonControl standardTankSystem_No;

	public ButtonControl backButton;
	public ButtonControl saveButton;

	public BaseWebElementControl pageName;

	public UnderwritingQuestionsPage() {
		PageObject pageobject = new PageObject("UnderwritingQuestions");
		answerNoToAllQuestions = new ButtonControl(By.xpath(pageobject.getXpath("xp_AnswerNoToAllQuestions")));

		developerSpeculationHome_Yes = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_DeveloperSpeculationHome_Yes")));
		developerSpeculationHome_No = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_DeveloperSpeculationHome_No")));
		unrepairedDamage_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_UnrepairedDamage_Yes")));
		unrepairedDamage_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_UnrepairedDamage_No")));
		commercialBusinessExposure_Yes = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_CommercialBusinessExposure_Yes")));
		commercialBusinessExposure_No = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_CommercialBusinessExposure_No")));
		namedInsuredCancelled_Yes = new RadioButtonControl(
				By.xpath(pageobject.getXpath("xp_NamedInsuredCancelled_Yes")));
		namedInsuredCancelled_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_NamedInsuredCancelled_No")));
		targetRisk_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_HighProfileOrTargetRisk_Yes")));
		targetRisk_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_HighProfileOrTargetRisk_No")));
		specialtyHome_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_SpecialtyHome_Yes")));
		specialtyHome_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_SpecialtyHome_No")));
		polybutylenePlumbing_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_PolybutylenePlumbing_Yes")));
		polybutylenePlumbing_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_PolybutylenePlumbing_No")));
		heatSource_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_PrimaryOrSecondaryHeatSource_Yes")));
		heatSource_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_PrimaryOrSecondaryHeatSource_No")));
		standardTankSystem_Yes = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_StandardTankSystem_Yes")));
		standardTankSystem_No = new RadioButtonControl(By.xpath(pageobject.getXpath("xp_StandardTankSystem_No")));

		backButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackButton")));
		saveButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SaveButton")));

		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
	}

	public void enterUnderwritingQuestionsDetails(Map<String, String> Data) {

		if (Data.get("NamedInsuredCancelled/NonRenewed").equalsIgnoreCase("Yes")) {
			namedInsuredCancelled_Yes.scrollToElement();
			namedInsuredCancelled_Yes.click();
		} else {
			namedInsuredCancelled_No.scrollToElement();
			namedInsuredCancelled_No.click();
		}

		if (Data.get("HighProfileOrTargetRisk").equalsIgnoreCase("Yes")) {
			targetRisk_Yes.scrollToElement();
			targetRisk_Yes.click();
		} else {
			targetRisk_No.scrollToElement();
			targetRisk_No.click();
		}

		if (Data.get("HeatSource").equalsIgnoreCase("Yes")) {
			heatSource_Yes.scrollToElement();
			heatSource_Yes.click();
		} else {
			heatSource_No.scrollToElement();
			heatSource_No.click();
		}

		if (Data.get("StandardTankSystem").equalsIgnoreCase("Yes")) {
			standardTankSystem_Yes.scrollToElement();
			standardTankSystem_Yes.click();
		} else {
			standardTankSystem_No.scrollToElement();
			standardTankSystem_No.click();
		}

		saveButton.scrollToElement();
		saveButton.click();
	}

}