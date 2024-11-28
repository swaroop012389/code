package com.icat.epicenter.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.BasePageControl;
import com.NetServAutomationFramework.test.SuiteListener;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.config.TestType;

@Listeners(com.NetServAutomationFramework.test.TestListener.class)
public abstract class AbstractTest extends BasePageControl {
	private LoginType loginType;
	private TestType testType;

	public AbstractTest(TestType testType, LoginType loginType) {
		this.loginType = loginType;
		this.testType = testType;
	}

	@BeforeClass
	public void beforeClass() {
		if (!SuiteListener.isConfigured()) {
			Assert.fail("The test suite must define the SuiteListener.  Include the listeners tag in your xml suite.");
		}
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public TestType getTestType() {
		return testType;
	}

	protected abstract String getReportName();
	protected abstract String getDataFilePath();

	/**
	 * Login and producer info for the test.  For ProducerNumber, use from the test if it exists,
	 * otherwise use the producer number defined in the config.
	 * @return a map with the info
	 */
	protected Map<String, String> buildTestSetupData(List<Map<String, String>> testData) {
		Map<String, String> loginData = new HashMap<>();
		loginData.put("UserName", EnvironmentDetails.getEnvironmentDetails().getString("test.login.usm.userName"));
		loginData.put("Producer", EnvironmentDetails.getEnvironmentDetails().getString("test.login.producer.userName"));
		loginData.put("RetailProducer", EnvironmentDetails.getEnvironmentDetails().getString("test.login.retailproducer.userName"));
		loginData.put("NahoUsername", EnvironmentDetails.getEnvironmentDetails().getString("test.login.nahousm.userName"));
		loginData.put("NahoProducer", EnvironmentDetails.getEnvironmentDetails().getString("test.login.nahoproducer.userName"));
		loginData.put("Admin", EnvironmentDetails.getEnvironmentDetails().getString("test.login.admin.userName"));
		loginData.put("Password", EnvironmentDetails.getEnvironmentDetails().getString("test.login.usm.password"));
		loginData.put("GetTransactionID", EnvironmentDetails.getEnvironmentDetails().getString("test.data.getTransactionID"));

		if (!testData.isEmpty() && testData.get(0).containsKey("ProducerNumber")) {
			loginData.put("ProducerNumber", testData.get(0).get("ProducerNumber"));
		}
		else {
			loginData.put("ProducerNumber", EnvironmentDetails.getEnvironmentDetails().getString("test.data.producerNumber." + getTestType().getName()));
		}

		return loginData;
	}

}
