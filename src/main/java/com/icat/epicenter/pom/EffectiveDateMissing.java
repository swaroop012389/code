package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class EffectiveDateMissing {
	public TextFieldControl effectiveDate;
	public ButtonControl continuButton;

	public EffectiveDateMissing() {
		PageObject pageobject = new PageObject("EffectiveDateMissing");
		effectiveDate = new TextFieldControl(By.xpath(pageobject.getXpath("xp_EffectiveDate")));
        continuButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ContinuButton")));
	}
}
