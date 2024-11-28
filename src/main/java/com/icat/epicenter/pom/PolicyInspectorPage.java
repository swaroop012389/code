package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class PolicyInspectorPage  extends BasePageControl {

	public TextFieldControl enterPolicyNumber;
	public ButtonControl findPolicyutton;
	public BaseWebElementControl glPremiumValue;
	public ButtonControl  backButton;
	public TextFieldControl enterQuoteNumber;
	public BaseWebElementControl premiumFromBuildingSection;
	public BaseWebElementControl premiumFromOrdinanceSection;

	public PolicyInspectorPage() {
		PageObject pageobject = new PageObject("PolicyInspector");
		enterPolicyNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyNumber")));
		findPolicyutton = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindPolicyButton")));
		glPremiumValue = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GLPremiumValue")));
		backButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackButton")));
		enterQuoteNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_QuoteNumber")));

		premiumFromBuildingSection = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumFromBuildingSection")));
		premiumFromOrdinanceSection = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumFromOrdinanceSection")));


	}

}
