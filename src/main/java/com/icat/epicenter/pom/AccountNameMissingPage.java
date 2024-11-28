package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class AccountNameMissingPage extends BasePageControl{

	public TextFieldControl accountName;
	public ButtonControl continuebutton;
	public ButtonControl cancel;

	public AccountNameMissingPage() {
		PageObject pageobject = new PageObject("AccountNameMissing");
		accountName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_AccountName")));
		continuebutton = new ButtonControl(By.xpath(pageobject.getXpath("xp_Continue")));
		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
	}
}
