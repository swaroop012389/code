/** Program Description: Object Locators and methods defined in Health Dashboard page
 *  Author			   : john
 *  Date of Creation   : 26/09/19
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class HealthDashBoardPage {
	public HyperLink moratoriumLink;
	public HyperLink reofferaccountLink;
	public HyperLink scheduledJobManagerlink;
	public HyperLink policyInspectorLink;
	public TextFieldControl quoteNumberField;
	public ButtonControl findPolicyBtn;
	public BaseWebElementControl eqbPremium;
	public HyperLink toolList;
	public TextFieldControl policyNumberField;
	public BaseWebElementControl eqbPremiumEndt;

	public HealthDashBoardPage() {
		PageObject pageobject = new PageObject("HealthDashBoardPage");
		moratoriumLink = new HyperLink(By.xpath(pageobject.getXpath("xp_MoratoriumLink")));
		reofferaccountLink =new HyperLink(By.xpath(pageobject.getXpath("xp_ReOfferAccountLink")));
		scheduledJobManagerlink = new HyperLink(By.xpath(pageobject.getXpath("xp_ScheduledJobManagerlink")));
		policyInspectorLink = new HyperLink(By.xpath(pageobject.getXpath("xp_PolicyInspectorLink")));
		quoteNumberField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_QuoteNumberField")));
		findPolicyBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_FindPolicyBtn")));
		eqbPremium= new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EQBPremium")));
		toolList = new HyperLink(By.xpath(pageobject.getXpath("xp_ToolList")));
		policyNumberField = new TextFieldControl(By.xpath(pageobject.getXpath("xp_PolicyNumberField")));
		eqbPremiumEndt = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_EQBPremiumEndt")));
	}
}
