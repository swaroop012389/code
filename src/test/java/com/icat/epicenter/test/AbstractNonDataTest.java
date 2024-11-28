package com.icat.epicenter.test;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.NetServAutomationFramework.config.ConfigException;
import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.ReportsManager;
import com.NetServAutomationFramework.generic.WebDriverManager;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
import com.NetServAutomationFramework.test.TestRetryAnalyzer;
import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.config.TestType;
import com.icat.epicenter.pom.LoginPage;

public abstract class AbstractNonDataTest extends AbstractTest {
	private static final String PACKAGE_PREFIX = "com.icat.epicenter.test.";

	public AbstractNonDataTest(TestType testType, LoginType loginType) {
		super(testType, loginType);
	}

	/**
	 * Entry point for test. First, load the data associated with the test.
	 * Next, let the implementing class(es) do whatever setup they need.
	 * Set up reporting.
	 * Finally, tell the test to execute itself.
	 */
	@Test(retryAnalyzer = TestRetryAnalyzer.class, timeOut = 600000)
	public void runTest(ITestContext context) {
		try {
			// load data for test
			List<Map<String, String>> testData = loadTestData();

			// Check setup
			checkSetup(testData);

			// set up report manager
			ReportsManager.startTest(context, getTestName(), testData.get(0).get("TestDescription"));

			// Launch browser
			WebDriverManager.launchBrowser();

			// Enter login details
			enterLoginDetailsData();

			// call the test to execute itself
			System.out.println(this.getClass().getName() + " execution started");
			execute(testData, buildTestSetupData(testData));

		} catch (Throwable t) {
			t.printStackTrace();
			Assert.fail(t.getMessage());
		} finally {
			// test ended; tell report manager
			WebDriverManager.closeCurrentBrowser();
			ReportsManager.endTest(this.getClass().getName());
		}
	}

	@AfterClass
	public void endTest(ITestContext context) {
		System.out.println(this.getClass().getName() + " execution ended");
	}

	/**
	 * Depending on the login type, get the correct credentials from the properties file.
	 */
	public void enterLoginDetailsData() {
		if (getLoginType() != LoginType.NONE) {
			LoginPage login = new LoginPage();
			Assertions.passTest("Login Page", "Login Page loaded successfully");
			String userName, password;
			switch (getLoginType()) {
			case USM:
				userName = EnvironmentDetails.getEnvironmentDetails().getString("test.login.usm.userName");
				password = EnvironmentDetails.getEnvironmentDetails().getString("test.login.usm.password");
				break;
			case PRODUCER:
				userName = EnvironmentDetails.getEnvironmentDetails().getString("test.login.producer.userName");
				password = EnvironmentDetails.getEnvironmentDetails().getString("test.login.producer.password");
				break;
			case RETAILPRODUCER:
				userName = EnvironmentDetails.getEnvironmentDetails().getString("test.login.retailproducer.userName");
				password = EnvironmentDetails.getEnvironmentDetails().getString("test.login.retailproducer.password");
				break;
			case NAHOUSM:
				userName = EnvironmentDetails.getEnvironmentDetails().getString("test.login.nahousm.userName");
				password = EnvironmentDetails.getEnvironmentDetails().getString("test.login.nahousm.password");
				break;
			case NAHOPRODUCER:
				userName = EnvironmentDetails.getEnvironmentDetails().getString("test.login.nahoproducer.userName");
				password = EnvironmentDetails.getEnvironmentDetails().getString("test.login.nahoproducer.password");
				break;
			case ADMIN:
				userName = EnvironmentDetails.getEnvironmentDetails().getString("test.login.admin.userName");
				password = EnvironmentDetails.getEnvironmentDetails().getString("test.login.admin.password");
				break;
			default:
				userName = "unknown_login";
				password = null;
			}
			if (StringUtils.equals("qa-testing-local-usr", password)) {
				Assert.fail("The placeholder password was found in application.properties.  This needs to be updated before you can run tests");
			}
			login.enterLoginDetails(userName, password);
		}
	}

	protected abstract void execute(List<Map<String, String>> data, Map<String, String> testDataSetup);

	private List<Map<String, String>> loadTestData() {
		SheetMatchedAccessManager testDataSheet = new SheetMatchedAccessManager(getDataFilePath(), "TestData");
		return testDataSheet.readExcelColumnWise();
	}

	/**
	 * Verify the the data file is configured properly.
	 *
	 * @param testData the testData loaded
	 * @throws ConfigException If the data file isn't set up correctly.
	 */
	private void checkSetup(List<Map<String, String>> testData) throws ConfigException {
		if (testData == null) {
			throw new ConfigException("Unable to load test data from '" + getDataFilePath() + "'");
		}
		if (StringUtils.isBlank(testData.get(0).get("TestName"))) {
			throw new ConfigException("'TestName' not found in '" + getDataFilePath() + "'");
		}
		if (StringUtils.isBlank(testData.get(0).get("TestDescription"))) {
			throw new ConfigException("'TestDescription' not found in '" + getDataFilePath() + "'");
		}
		if (StringUtils.isBlank(testData.get(0).get("TestLongDescription"))) {
			throw new ConfigException("'TestLongDescription' not found in '" + getDataFilePath() + "'");
		}
	}

	/**
	 * Strip off the "com.icat..." from the test name.
	 *
	 * @return
	 */
	private String getTestName() {
		String className = this.getClass().getName();
		if (className.startsWith(PACKAGE_PREFIX)) {
			className = className.substring(PACKAGE_PREFIX.length());
		}
		return className;
	}
}
