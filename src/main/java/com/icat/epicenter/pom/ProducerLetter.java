package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;

public class ProducerLetter {
	public ButtonControl emailProducerLetterButton;
	public ButtonControl closeButton;
	public BaseWebElementControl policyNumber;

	public ProducerLetter() {
		PageObject pageobject = new PageObject("ProducerLetter");
		emailProducerLetterButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_EmailProducerLetterButton")));
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseButton")));
		policyNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyNumber")));
	}

}
