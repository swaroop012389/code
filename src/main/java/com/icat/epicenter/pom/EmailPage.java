package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class EmailPage {
	public TextFieldControl toAddressFiled;
	public TextFieldControl commentField;
	public ButtonControl sendEmailButton;
	public ButtonControl cancelButton;

	public EmailPage() {
		PageObject pageobject = new PageObject("Email");
		toAddressFiled = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ToAddressFiled")));
		commentField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_CommentField")));
		sendEmailButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_SendEmailButton")));
		cancelButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CancelButton")));
	}

}
