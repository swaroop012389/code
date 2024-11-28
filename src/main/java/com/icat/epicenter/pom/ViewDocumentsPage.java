/** Program Description: Object Locators and methods defined in View Documents Page
 *  Author			   : John
 *  Date of Creation   : 04/13/2020
**/

package com.icat.epicenter.pom;

import org.openqa.selenium.By;

import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.generic.BaseWebElementControl;
import com.NetServAutomationFramework.generic.ButtonControl;
import com.NetServAutomationFramework.generic.HyperLink;
import com.NetServAutomationFramework.generic.PageObject;

public class ViewDocumentsPage extends BasePageControl {

	public HyperLink documents;
	public BaseWebElementControl forms1;
	public BaseWebElementControl forms2;
	public ButtonControl backBtn;
	public ButtonControl cancelBtn;
	public HyperLink editUserDocuments;

	public ViewDocumentsPage() {
		PageObject pageobject = new PageObject("ViewDocuments");
		documents = new HyperLink(By.xpath(pageobject.getXpath("xp_Documents")));
		forms1 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Forms1")));
		forms2 = new BaseWebElementControl(By.xpath(pageobject.getXpath("xp_Forms2")));
		backBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_Back")));
		cancelBtn = new ButtonControl(By.xpath(pageobject.getXpath("xp_Cancel")));
		editUserDocuments = new HyperLink(By.xpath(pageobject.getXpath("xp_EditUserDocuments")));

	}

}