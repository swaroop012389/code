/** Program Description: Object Locators and methods defined in Approve/Decline Quote page
 *  Author			   : SMNetserv
 *  Date of Creation   : 27/10/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class ApproveDeclineQuotePage extends BasePageControl {
	public TextFieldControl internalUnderwriterComments;
	public TextFieldControl externalUnderwriterComments;
	public BaseWebElementControl pageName;
	public ButtonControl approveButton;
	public ButtonControl declineButton;
	public ButtonControl customizeQuoteButton;
	public ButtonControl pendingModificationsButton;
	public ButtonControl handleExternallyButton;
	public ButtonControl closeButton;
	public ButtonControl displayQuoteButton;
	public ButtonControl pushToRmsButton;
	public ButtonControl apprvButton;
	public ButtonControl backdateApproveButton;
	public ButtonControl handleInMMBUButton;
	public BaseWebElementControl quoteDeclinedMessage;
	public HyperLink statusLink;
	public BaseWebElementControl aAL;
	public BaseWebElementControl eLR;

	public ApproveDeclineQuotePage() {
		PageObject pageobject = new PageObject("ApproveDeclineQuote");
		internalUnderwriterComments = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_InternalUnderwriterComments")));
		externalUnderwriterComments = new TextFieldControl(
				By.xpath(pageobject.getXpath("xp_ExternalUnderwriterComments")));
		pageName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PageName")));
		approveButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ApproveButton")));
		declineButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_DeclineButton")));
		customizeQuoteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CustomizeQuoteButton")));
		pendingModificationsButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_PendingModificationsButton")));
		handleExternallyButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_HandleExternallyButton")));
		closeButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_CloseButton")));
		displayQuoteButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_DisplayQuoteButton")));
		pushToRmsButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_PushToRMSQuoteButton")));
		apprvButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_ApprvButton")));
		backdateApproveButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_BackdateApproveButton")));
		handleInMMBUButton = new ButtonControl(By.xpath(pageobject.getXpath("xp_HandleInMMBUButton")));
		quoteDeclinedMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteDeclinedMessage")));
		statusLink = new HyperLink(By.xpath(pageobject.getXpath("xp_StatusLink")));
		aAL = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AAL")));
		eLR = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ELR")));

	}

	public void clickOnApprove() {
		if (internalUnderwriterComments.checkIfElementIsPresent()) {
			internalUnderwriterComments.scrollToElement();
			internalUnderwriterComments.setData("Test");
		}
		if (externalUnderwriterComments.checkIfElementIsPresent()) {
			externalUnderwriterComments.scrollToElement();
			externalUnderwriterComments.setData("Test");
		}
		approveButton.scrollToElement();
		approveButton.click();
	}
}
