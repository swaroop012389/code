/** Program Description: Object Locators and methods defined in Rate trace page
 *  Author			   : SMNetserv
 *  Date of Creation   : 12/11/2017
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.PageObject;
import com.NetServAutomationFramework.generic.TextAreaControl;
import com.NetServAutomationFramework.generic.TextFieldControl;

public class RateTracePage extends BasePageControl {
	public TextFieldControl location1Dwelling1Header;
	public TextFieldControl location2Dwelling1Header;
	public TextFieldControl location1Dwelling3Header;
	public ButtonControl closeButton;
	public TextFieldControl rateTraceHeader;
	public ButtonControl backButton;

	public TextFieldControl L1D1WindNBDiscount;
	public TextFieldControl L1D1EarthquakeNBDiscount;
	public TextFieldControl L2D1WindNBDiscount;
	public TextFieldControl L2D1EarthquakeNBDiscount;
	public BaseWebElementControl L1D1WindNBDiscValue;
	public BaseWebElementControl L1D1EarthquakeNBDiscValue;
	public BaseWebElementControl L1D2WindNBDiscValue;
	public BaseWebElementControl L1D2EarthquakeNBDiscValue;
	public BaseWebElementControl L1D3WindNBDiscValue;
	public BaseWebElementControl L1D3EarthquakeNBDiscValue;
	public BaseWebElementControl L2D1WindNBDiscValue;
	public BaseWebElementControl L2D1EarthquakeNBDiscValue;

	public RateTracePage() {
		PageObject pageObject = new PageObject("RateTrace");
		location1Dwelling1Header = new TextFieldControl(By.xpath(pageObject.getXpath("xp_Location1Dwelling1Header")));
		location2Dwelling1Header = new TextAreaControl(By.xpath(pageObject.getXpath("xp_Location2Dwelling1Header")));
		location1Dwelling3Header = new TextAreaControl(By.xpath(pageObject.getXpath("xp_Location1Dwelling3Header")));
		closeButton = new ButtonControl(By.xpath(pageObject.getXpath("xp_CloseButton")));
		rateTraceHeader = new TextFieldControl(By.xpath(pageObject.getXpath("xp_RateTraceHeader")));
		backButton = new ButtonControl(By.xpath(pageObject.getXpath("xp_BackButton")));

		L1D1WindNBDiscount = new TextFieldControl(By.xpath(pageObject.getXpath("xp_L1D1WindNBDiscount")));
		L1D1EarthquakeNBDiscount = new TextFieldControl(By.xpath(pageObject.getXpath("xp_L1D1EarthquakeNBDiscount")));
		L2D1WindNBDiscount = new TextFieldControl(By.xpath(pageObject.getXpath("xp_L2D1WindNBDiscount")));
		L2D1EarthquakeNBDiscount = new TextFieldControl(By.xpath(pageObject.getXpath("xp_L2D1EarthquakeNBDiscount")));
		L1D1WindNBDiscValue = new BaseWebElementControl(By.xpath(pageObject.getXpath("xp_L1D1WindNBDiscValue")));
		L1D1EarthquakeNBDiscValue = new BaseWebElementControl(
				By.xpath(pageObject.getXpath("xp_L1D1EarthquakeNBDiscValue")));
		L1D2WindNBDiscValue = new BaseWebElementControl(By.xpath(pageObject.getXpath("xp_L1D2WindNBDiscValue")));
		L1D2EarthquakeNBDiscValue = new BaseWebElementControl(
				By.xpath(pageObject.getXpath("xp_L1D2EarthquakeNBDiscValue")));
		L1D3WindNBDiscValue = new BaseWebElementControl(By.xpath(pageObject.getXpath("xp_L1D3WindNBDiscValue")));
		L1D3EarthquakeNBDiscValue = new BaseWebElementControl(
				By.xpath(pageObject.getXpath("xp_L1D3EarthquakeNBDiscValue")));

		L2D1WindNBDiscValue = new BaseWebElementControl(By.xpath(pageObject.getXpath("xp_L1D1WindNBDiscValue")));
		L2D1EarthquakeNBDiscValue = new BaseWebElementControl(
				By.xpath(pageObject.getXpath("xp_L1D1EarthquakeNBDiscValue")));
	}
}
