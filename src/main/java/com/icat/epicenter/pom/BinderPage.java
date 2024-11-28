/** Program Description: Object Locators and methods defined in Binder page
 *  Author			   : Abha
 *  Date of Creation   : 07/11/2019
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;

public class BinderPage extends BasePageControl {

	public BaseWebElementControl apcName;
	public BaseWebElementControl apcLimit;
	public BaseWebElementControl apcPremium;
	public BaseWebElementControl propertyCovBldgLimit;
	public BaseWebElementControl propertyCovBldgPremium;
	public BaseWebElementControl building;
	public BaseWebElementControl location;
	public BaseWebElementControl locationLevelCovLimit;
	public BaseWebElementControl locationLevelCovPremium;
	public BaseWebElementControl propertyCovBPPLimit;
	public BaseWebElementControl propertyCovBPPPremium;
	public BaseWebElementControl propertyCovOrdinanceLimit;
	public BaseWebElementControl propertyCovOrdinancePremium;
	public BaseWebElementControl gLInfoClass;
	public BaseWebElementControl gLInfoRatingBaseCount;
	public BaseWebElementControl gLInfoPremium;
	public BaseWebElementControl terrorism;
	public BaseWebElementControl policyDeductibles;
	public BaseWebElementControl policyOtherDeductibles;
	public BaseWebElementControl buildingName;
	public BaseWebElementControl locationName;
	public BaseWebElementControl binderMsg;
	public ButtonControl goBackBtn;
	public BaseWebElementControl coverNotePolicyNumber;

	public BinderPage() {
		PageObject pageobject = new PageObject("Binder");
		apcName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_APCName")));
		apcLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_APCLimit")));
		apcPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_APCPremium")));
		propertyCovBldgLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovBldgLimit")));
		propertyCovBldgPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovBldgPremium")));
		propertyCovBPPLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovBPPLimit")));
		propertyCovBPPPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovBPPPremium")));
		propertyCovOrdinanceLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovOrdinanceLimit")));
		propertyCovOrdinancePremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PropertyCovOrdinancePremium")));
		locationLevelCovLimit = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationLevelCovLimit")));
		locationLevelCovPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationLevelCovPremium")));
		building = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Building")));
		location = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Location")));
		gLInfoClass = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GLInfoClass")));
		gLInfoRatingBaseCount = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GLInfoRatingBaseCount")));
		gLInfoPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_GLInfoPremium")));
		terrorism= new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Terrorism")));
		policyDeductibles= new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyDeductibles")));
		policyOtherDeductibles= new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_PolicyOtherDeductibles")));
		locationLevelCovPremium = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationLevelCovPremium")));
		buildingName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BuidingName")));
		locationName = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_LocationName")));
		binderMsg = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_BinderMsg")));
		goBackBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_GoBackBtn")));
                         coverNotePolicyNumber = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_CoverNotePolicyNumber")));
	}
}
