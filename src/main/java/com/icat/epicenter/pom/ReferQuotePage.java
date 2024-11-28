/** Program Description: Object Locators and methods defined in Refer quote page
 *  Author			   : SMNetserv
 *  Date of Creation   : 03/11/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class ReferQuotePage extends BasePageControl {
	public TextFieldControl contactName;
	public TextFieldControl contactEmail;
	public TextFieldControl comments;
	public ButtonControl referQuote;
	public BaseWebElementControl refMessageForUSM;
	public BaseWebElementControl quoteForRef;

	public BaseWebElementControl referralMsg;
	public ButtonControl referralClose;
	public BaseWebElementControl quoteNum;
	public BaseWebElementControl covCExceedWarning;
	public BaseWebElementControl referQuoteNumber;

    public BaseWebElementControl seismicReferralMsg;
    public BaseWebElementControl referralMsgforProd;
    public HyperLink pageName;
    public BaseWebElementControl covA6MReferralMsg;
    public BaseWebElementControl priorLossesReferralMessage;
    public HyperLink locationBtn;
    public BaseWebElementControl referralMessages;
	public BaseWebElementControl quoteNumberforReferral;
	public BaseWebElementControl roofReferralMessage;
	public BaseWebElementControl quoteReferralMsgs;
	public BaseWebElementControl noofStoriesReferralMessage;
	public BaseWebElementControl constructionClassOtherReferralMessage;
	public BaseWebElementControl referralMessagesNAHO;
	public BaseWebElementControl lapseInCoverageReferralMessage;
	public BaseWebElementControl PriorLossWithin3YearsReferralMessage;
	public BaseWebElementControl covBGreaterReferralMessage;
	public BaseWebElementControl covCGreaterReferralMessage;
	public BaseWebElementControl covDGreaterReferralMessage;
	public BaseWebElementControl covEGreaterReferralMessage;
	public BaseWebElementControl mortgageeReferrralMessage;
	public BaseWebElementControl aiReferralMessage;
	public BaseWebElementControl outofSequenceReferralMessage;

	public ReferQuotePage() {
		PageObject pageobject = new PageObject("ReferQuote");
		contactName = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ContactName")));
		contactEmail = new TextFieldControl(By.xpath(pageobject.getXpath("xp_ContactEmail")));
		comments = new TextFieldControl(By.xpath(pageobject.getXpath("xp_Comments")));
		referQuote = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReferQuote")));
		refMessageForUSM = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AlertMessageForUSMRef")));
		quoteForRef = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GetQuoteNumberforReferral")));

		referralMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferralMsg")));
		referralMessagesNAHO = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferralMessagesNAHO")));
		referralClose = new ButtonControl(By.xpath(pageobject.getXpath("xp_ReferralClose")));
		quoteNum = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNum")));
		covCExceedWarning = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovCExceedWarning")));
		referQuoteNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RefQuoteNum")));
        seismicReferralMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_SeismicReferralMsg")));
        referralMsgforProd = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ProdReferralMessage")));
    	pageName = new HyperLink(By.xpath(pageobject.getXpath("xp_PageName")));
    	covA6MReferralMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovA6MReferralMsg")));

    	priorLossesReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossesReferralMessgae")));
    	locationBtn = new HyperLink(By.xpath(pageobject.getXpath("xp_LocationBtn")));
    	referralMessages = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ReferralMessages")));
        quoteNumberforReferral = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteNumberforReferral")));
        roofReferralMessage=new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RoofReferralMessage")));
        quoteReferralMsgs = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_QuoteReferralMsgs")));
		noofStoriesReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_NoofStoriesReferralMessage")));
		constructionClassOtherReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_ConstructionClassOtherReferralMessage")));
		lapseInCoverageReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LapseInCoverageReferralMessage")));
		PriorLossWithin3YearsReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PriorLossWithin3Years")));
		covBGreaterReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovBGreaterReferralMessage")));
		covCGreaterReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverageCGreaterReferralMessage")));
		covDGreaterReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovDGreaterReferralMessage")));
		covEGreaterReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CovEGreaterReferralMessage")));
		mortgageeReferrralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_MortgageeReferrralMessage")));
		aiReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_AiReferralMessage")));
		outofSequenceReferralMessage = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_OutofSequenceReferralMessage")));
	}
}
