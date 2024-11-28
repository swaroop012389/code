package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class PreferenceOptionsPage extends BasePageControl {
	public CheckBoxControl enableSLTFCheckbox;
	public CheckBoxControl brokerFeeCheckbox;
	public ButtonControl brokerFeeArrow;
	public HyperLink brokerFeeOption;
	public TextFieldControl brokerFeeValue;
	public HyperLink deleteBrokerSymbol;
	public HyperLink deleteBroker;
	public ButtonControl brokerFeeStateArrow;
	public HyperLink brokerFeeStateOption;
	public ButtonControl savePreferences;
	public ButtonControl cancel;
	public ButtonControl taxesAndFeesTab;

	public PreferenceOptionsPage() {
		PageObject pageobject = new PageObject("PreferenceOptions");
		enableSLTFCheckbox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_SurplusTaxes")));
		brokerFeeCheckbox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_BrokerFee")));
		brokerFeeArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_BrokerFeeTypeArrow")));
		brokerFeeOption = new HyperLink(By.xpath(pageobject.getXpath("xp_BrokerFeeTypeOption")));
		brokerFeeValue = new TextFieldControl(By.xpath(pageobject.getXpath("xp_BrokerFeeValue")));
		deleteBroker = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteBroker")));
		deleteBrokerSymbol = new HyperLink(By.xpath(pageobject.getXpath("xp_DeleteBrokerSymbol")));
		brokerFeeStateArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_BrokerStateArrow")));
		brokerFeeStateOption = new HyperLink(By.xpath(pageobject.getXpath("xp_BrokerStateOption")));
		savePreferences = new ButtonControl(By.xpath(pageobject.getXpath("xp_SavePreferences")));
		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		taxesAndFeesTab = new ButtonControl(By.xpath(pageobject.getXpath("xp_TaxesAndFeesTab")));
	}

	public void addBrokerFees(Map<String, String> Data) {
		if (taxesAndFeesTab.checkIfElementIsPresent() && taxesAndFeesTab.checkIfElementIsDisplayed()) {
			taxesAndFeesTab.scrollToElement();
			taxesAndFeesTab.click();
		}
		if (!Data.get("SurplusCheckbox").equals("")) {
			if (Data.get("SurplusCheckbox").equalsIgnoreCase("Yes")) {
				if (!enableSLTFCheckbox.checkIfElementIsSelected()) {
					enableSLTFCheckbox.select();
					Assertions.passTest("Preference Opitons Page", "Surplus Lines,Taxes and Fees checkbox is selected");
				}
			} else if (Data.get("SurplusCheckbox").equalsIgnoreCase("No")) {
				if (enableSLTFCheckbox.checkIfElementIsSelected()) {
					enableSLTFCheckbox.deSelect();
					Assertions.passTest("Preference Opitons Page",
							"Surplus Lines,Taxes and Fees checkbox is deselected");
				}
			}
		}
		if (!Data.get("BrokerFeeCheckbox").equals("")) {
			if (Data.get("BrokerFeeCheckbox").equalsIgnoreCase("Yes")) {
				if (!brokerFeeCheckbox.checkIfElementIsSelected()) {
					brokerFeeCheckbox.select();
					Assertions.passTest("Preference Opitons Page", "Broker Fees checkbox is selected");
				}
			} else if (Data.get("BrokerFeeCheckbox").equalsIgnoreCase("No")) {
				if (brokerFeeCheckbox.checkIfElementIsSelected()) {
					brokerFeeCheckbox.deSelect();
					Assertions.passTest("Preference Opitons Page", "Broker Fees checkbox is deselected");
				}
			}
		}
		if (!Data.get("BrokerFeePercentageOrDollar").equals("") || !Data.get("BrokerFeeValue").equals("")) {
			if (deleteBrokerSymbol.formatDynamicPath(1).checkIfElementIsPresent()
					&& deleteBrokerSymbol.formatDynamicPath(1).checkIfElementIsDisplayed()) {
				int brokerElements = deleteBroker.getNoOfWebElements();
				for (int i = 1; i <= brokerElements - 1; i++) {
					deleteBrokerSymbol.formatDynamicPath(i).scrollToElement();
					deleteBrokerSymbol.formatDynamicPath(i).click();
				}
			}
			brokerFeeArrow.waitTillVisibilityOfElement(60);
			brokerFeeArrow.scrollToElement();
			brokerFeeArrow.click();
			brokerFeeOption.formatDynamicPath(Data.get("BrokerFeePercentageOrDollar")).waitTillVisibilityOfElement(60);
			brokerFeeOption.formatDynamicPath(Data.get("BrokerFeePercentageOrDollar")).scrollToElement();
			brokerFeeOption.formatDynamicPath(Data.get("BrokerFeePercentageOrDollar")).click();
			Assertions.passTest("Preference Opitons Page",
					"Broker Fee Type is " + Data.get("BrokerFeePercentageOrDollar"));
		}
		if (!Data.get("BrokerFeeValue").equals("")) {
			brokerFeeValue.waitTillVisibilityOfElement(60);
			brokerFeeValue.scrollToElement();
			brokerFeeValue.clearData();
			brokerFeeValue.setData(Data.get("BrokerFeeValue"));
			brokerFeeValue.tab();
			Assertions.passTest("Preference Opitons Page", "Broker Fee Value is " + Data.get("BrokerFeeValue"));
		}
		if (!Data.get("PolicyState").equals("")) {
			brokerFeeStateArrow.click();
			brokerFeeStateOption.formatDynamicPath(Data.get("PolicyState")).waitTillVisibilityOfElement(60);
			brokerFeeStateOption.formatDynamicPath(Data.get("PolicyState")).scrollToElement();
			brokerFeeStateOption.formatDynamicPath(Data.get("PolicyState")).click();
		}
		savePreferences.scrollToElement();
		savePreferences.click();
	}
}
