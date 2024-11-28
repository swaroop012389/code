/** Program Description: Object Locators and methods defined in Email quote page
 *  Author			   : SMNetserv
 *  Date of Creation   : 06/11/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.CheckBoxControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class EmailQuotePage extends BasePageControl {
	public TextFieldControl fromAddress;
	public TextFieldControl toAddress;
	public TextFieldControl subject;
	public TextFieldControl comments;
	public CheckBoxControl includeQuote;
	public CheckBoxControl includeQuoteDetails;
	public CheckBoxControl combineIntoSinglePDF;
	public ButtonControl cancel;
	public ButtonControl sendEmail;
	public BaseWebElementControl subscriptionAgreementlabel;

	public EmailQuotePage() {
		PageObject pageobject = new PageObject("EmailQuote");
		fromAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_FromAddress")));
		toAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ToAddress")));
		subject = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Subject")));
		comments = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Comments")));
		includeQuote = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_IncludeQuote")));
		includeQuoteDetails = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_IncludeQuoteDetails")));
		combineIntoSinglePDF = new CheckBoxControl(By.xpath(pageobject.getXpath("xp_CombineIntoSinglePDF")));
		cancel = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		sendEmail = new ButtonControl(By.xpath(pageobject.getXpath("xp_SendEmail")));
		subscriptionAgreementlabel = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SubscriptionAgreementLabel")));
	}
}
