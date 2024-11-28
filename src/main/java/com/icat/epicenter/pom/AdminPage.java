package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.PageObject;

public class AdminPage extends BasePageControl {
	public BaseWebElementControl ratingEffectiveDate;


	public AdminPage() {
		PageObject pageobject = new PageObject("AdminPage");
		ratingEffectiveDate= new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_RatingEffectiveDate")));

	}
}
