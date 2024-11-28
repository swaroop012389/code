/** Program Description: Object Locators and methods defined in Bind request submitted page
 *  Author			   : SMNetserv
 *  Date of Creation   : 27/03/2019
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;

public class BindRequestSubmittedPage extends BasePageControl {
	public ButtonControl quoteDetails;
	public ButtonControl homePage;
	public BaseWebElementControl pageName;
	public BaseWebElementControl bindRequestMessage;

	public BaseWebElementControl priorLossesMessage;
	public BaseWebElementControl entityMessage;
	public BaseWebElementControl aiRelationshipMessage;
	public BaseWebElementControl underwritingMessage;
	public BaseWebElementControl quoteNoReferralMessage;
	public BaseWebElementControl quoteNoReferralMessageProducer;
	public BaseWebElementControl referralMessagePolicyEffDtPast;
	public BaseWebElementControl mortgageReferralWarMessage;
	public BaseWebElementControl waterHeaterYesRefMsg;
    public BaseWebElementControl mortgageReferralMessage;
    public BaseWebElementControl highTargerRiskRefMsg;
    public BaseWebElementControl premiumOverrideMessage;
    public BaseWebElementControl galvanizedMessage;
    public BaseWebElementControl dueDiligence;
    public BaseWebElementControl referralMessages;
    public BaseWebElementControl requestReceivedMsg;

	public BindRequestSubmittedPage() {
		PageObject pageobject = new PageObject("BindRequestSubmitted");
		quoteDetails = new ButtonControl(By.xpath(pageobject.getXpath("xp_QuoteDetails")));
		homePage = new ButtonControl(By.xpath(pageobject.getXpath("xp_HomePage")));
		pageName = new ButtonControl(By.xpath(pageobject.getXpath("xp_PageName")));
		bindRequestMessage = new ButtonControl(By.xpath(pageobject.getXpath("xp_BindRequestMessage")));
		priorLossesMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossesMessage")));
		entityMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EntityMessage")));
		aiRelationshipMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AIRelationshipMessage")));
		underwritingMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_UnderwritingQuestionsMessage")));
		quoteNoReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNoReferralMessage")));
		quoteNoReferralMessageProducer = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNoReferralMessageProducer")));
		referralMessagePolicyEffDtPast = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferralMessagePolicyEffDtPast")));
		mortgageReferralWarMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MortgageReferralWarMessage")));
		waterHeaterYesRefMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_WaterHeaterYes")));
		mortgageReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MortgageReferralMessage")));
		highTargerRiskRefMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_HigTargetRisk")));
		premiumOverrideMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PremiumOverrideMessage")));
		galvanizedMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Galvanized")));
		dueDiligence = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_DueDiligence")));
		referralMessages = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferralMessages")));
		requestReceivedMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RequestReceivedMsg")));

	}

	public HomePage clickOnHomepagebutton() {
		homePage.waitTillVisibilityOfElement(60);
		homePage.scrollToElement();
		homePage.click();
		homePage.waitTillInVisibilityOfElement(60);
		if (pageName.getData().contains("Welcome")) {
			return new HomePage();
		}
		return null;
	}
}
