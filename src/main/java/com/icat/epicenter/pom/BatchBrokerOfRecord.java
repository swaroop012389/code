package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class BatchBrokerOfRecord  extends BasePageControl {
	public TextFieldControl fromProducerNumber;
	public TextFieldControl toProducerNumber;
	public ButtonControl submit;
	public BaseWebElementControl policyNumber;
	public CheckBoxControl policyNumberCheckBox;
	public ButtonControl changeBrokerOfRecord;
	public BaseWebElementControl confirmationMessage;

	public BatchBrokerOfRecord() {
		PageObject pageobject = new PageObject("BatchBrokerOfRecord");
		fromProducerNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FromProducerNumber")));
		toProducerNumber = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ToProducerNumber")));
		submit = new ButtonControl(By.xpath(pageobject.getXpath("xp_Submit")));
		policyNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyNumber")));
		policyNumberCheckBox = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_PolicyNumberCheckBox")));
		changeBrokerOfRecord = new ButtonControl(By.xpath(pageobject.getXpath("xp_ChangeBrokerOfRecord")));
		confirmationMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ConfirmationMessage")));
	}
}
