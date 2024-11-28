
/** Program Description: Object Locators and methods defined in AccountOverview page
 *  Author			   : SMNetserv
 *  Date of Creation   : 27/02/2024
 **/

package com.icat.epicenter.pom;

import java.util.Map;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class PremiumAdjustmentRequestPage extends BasePageControl {

	public ButtonControl reasonForRequestArrow;
	public ButtonControl reasonForRequestOptions;
	public BaseWebElementControl premiumQuote;
	public TextFieldControl targetPremium;
	public TextFieldControl competitor;
	public ButtonControl competitorSuggestions;
	public TextAreaControl additionalInformation;
	public TextFieldControl yourName;
	public TextFieldControl yourEmailAddress;
	public ButtonControl rpcCancelBtn;
	public ButtonControl rpcUpdateBtn;
	public BaseWebElementControl originalPremiumData;
	public TextFieldControl requestReasonComment;

	public PremiumAdjustmentRequestPage() {
		PageObject pageobject = new PageObject("PremiumAdjustmentRequest");

		reasonForRequestArrow = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReasonForRequestArrow")));
		reasonForRequestOptions = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReasonForRequestOptions")));
		premiumQuote = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumQuote")));
		targetPremium = new TextFieldControl(By.xpath(pageobject.getXpath("xp_TargetPremium")));
		competitor = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Competitor")));
		competitorSuggestions = new ButtonControl(By.xpath(pageobject.getXpath("xp_CompetitorSuggestions")));
		additionalInformation = new TextAreaControl(By.xpath(pageobject.getXpath("xp_AdditionalInformationTextArea")));
		yourName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YourName")));
		yourEmailAddress = new TextFieldControl(By.xpath(pageobject.getXpath("xp_YourEmailAddress")));
		rpcCancelBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_RPCCancelBtn")));
		rpcUpdateBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_RPCUpdateBtn")));
		originalPremiumData = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OriginalPremiumAmount")));
		requestReasonComment = new TextFieldControl(By.xpath(pageobject.getXpath("xp_requestReasonComment")));

	}

	public void requestPremiumChanges(Map<String, String> testData) {
		premiumQuote.waitTillVisibilityOfElement(60);
		reasonForRequestArrow.scrollToElement();
		reasonForRequestArrow.click();
		reasonForRequestOptions.formatDynamicPath(testData.get("PremiumAdjustment_Reason")).click();
		waitTime(2); // adding wait time to load the element
		if (requestReasonComment.checkIfElementIsPresent() && requestReasonComment.checkIfElementIsDisplayed()) {
			requestReasonComment.scrollToElement();
			requestReasonComment.appendData(testData.get("RequestReasonComment"));
		}
		targetPremium.setData(testData.get("PremiumAdjustment_TargetPremium"));
		competitor.setData(testData.get("PremiumAdjustment_Competitor"));
		competitor.tab();
		additionalInformation.setData(testData.get("PremiumAdjustment_Description"));
		yourName.scrollToElement();
		yourName.setData(testData.get("PremiumAdjustement_Name"));
		yourEmailAddress.scrollToElement();
		yourEmailAddress.setData(testData.get("PremiumAdjustment_Email"));
		rpcUpdateBtn.scrollToElement();
		rpcUpdateBtn.click();

	}
}
