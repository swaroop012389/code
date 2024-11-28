package com.icat.epicenter.utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.NetServAutomationFramework.config.EnvironmentDetails;
import com.NetServAutomationFramework.generic.Assertions;
import com.NetServAutomationFramework.generic.excel.SheetMatchedAccessManager;
import com.icat.epicenter.config.TestType;

/**
 *  Program Description: Methods used to perform read/write operations on policy number (stored in excel sheet)
 *  generated during test execution.  Keep this class thread safe.  There can be many threads sharing the same
 *  file because the underlying ExcelAccessManager (used by SheetMatchedAccessManager) methods are synchronized.
 *  @author SMNetserv
 *  @since 10/11/2017
 */
public class ExcelReportUtil {

	private static final String[] REPORT_COLUMNS = {
			"tcid",
			"Insured Name",
			"Quote Number",
			"Policy Number",
			"Rating Effective Date",
			"Policy Effective Date",
			"Policy Expiration Date",
			"Transaction Date",
			"Processing Date",
			"Bind Request Date",
			"Inspection Fee",
			"Policy Fee",
			"Fees Total",
			"Premium Total",
			"Policy Total",
			"Transaction Policy Fee",
			"Transaction Inspection Fee",
			"Transaction Premium",
			"ProRata Factor",
			"Annual Transaction Premium",
			"Annual Policy Premium",
			"Transaction ID",
			"Product",
			"State Taxes and Fees",
			"Previous Policy Number"
	};

	private static ThreadLocal<ExcelReportUtil> currentInstance = new ThreadLocal<>();

	private String fileName;
	private String sheetName;

	/**
	 * Create an instance for this thread.  Initialize our filename and sheet name based on test type.
	 */
	private ExcelReportUtil(TestType testType) {
		String configKey;
		if (testType == TestType.COMMERCIAL) {
			configKey = "test.report.data.commercial";
			this.sheetName = "Commercial";
		}
		else {
			configKey = "test.report.data.residential";
			this.sheetName = "Residential";
		}
		this.fileName = EnvironmentDetails.getEnvironmentDetails().getString(configKey);
		if (StringUtils.isBlank(this.fileName)) {
			throw new RuntimeException("ExcelReportUtil() error: key '" + configKey + "' is not set.");
		}
	}

	public static void initialize(TestType testType) {
		ExcelReportUtil excelReportUtil = new ExcelReportUtil(testType);
		currentInstance.set(excelReportUtil);
	}

	public static ExcelReportUtil getInstance() {
		ExcelReportUtil instance = currentInstance.get();
		if (instance == null) {
			throw new RuntimeException("ExcelReportUtil.getInstance() called without first calling initialize.");
		}
		return instance;
	}

	public void addRow(String... data) {
		try {
			SheetMatchedAccessManager excel = new SheetMatchedAccessManager(fileName, sheetName, REPORT_COLUMNS);
			excel.addRow(data);

		} catch (Exception e) {
			e.printStackTrace();
			Assertions.exceptionError("NB DT Exe Report", "Failed to add data due to some error");
		}
	}

	public String getLatestPolicyFromMaster(String tcid) {
		SheetMatchedAccessManager Data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = Data.readExcelRowWise();

		Optional<Map<String, String>> lastMatchingPolicyNumber = masterData.stream().filter(m -> tcid.equalsIgnoreCase(m.get("tcid"))).reduce((first,second)->second);

		if(lastMatchingPolicyNumber.isPresent()) {
			return lastMatchingPolicyNumber.get().get("Policy Number");
		}
		throw new IllegalArgumentException("Could not find the last policy number for testcaseId"+tcid);
	}

	public String getLatestInsuredNameFromMaster(String tcid) {
		String InsuredName = null;
		SheetMatchedAccessManager Data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = Data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				InsuredName = element.get("Insured Name");
			}
		}
		return InsuredName;
	}

	public String getLatestQuoteFromMaster(String tcid) {
		String quoteNum = null;
		SheetMatchedAccessManager data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				quoteNum = element.get("Quote Number");
			}
		}
		return quoteNum;
	}

	public String getLatestPolicyEffectiveDateFromMaster(String tcid) {
		String policyEffectiveDate = null;
		SheetMatchedAccessManager data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				policyEffectiveDate = element.get("Policy Effective Date");
			}
		}
		return policyEffectiveDate;
	}

	public String getLatestPolicyExpirationDateFromMaster(String tcid) {
		String policyExpirationDate = null;
		SheetMatchedAccessManager data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				policyExpirationDate = element.get("Policy Expiration Date");
			}
		}
		return policyExpirationDate;
	}

	public String getLatestTransactionDateFromMaster(String tcid) {
		String txnDate = null;
		SheetMatchedAccessManager data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				txnDate = element.get("Transaction Date");
			}
		}
		return txnDate;
	}

	public String getLatestProcessingDateFromMaster(String tcid) {
		String processingDate = null;
		SheetMatchedAccessManager data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				processingDate = element.get("Processing Date");
			}
		}
		return processingDate;
	}

	public String getLatestBindRequestDateFromMaster(String tcid) {
		String bindReqDate = null;
		SheetMatchedAccessManager data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				bindReqDate = element.get("Bind Request Date");
			}
		}
		return bindReqDate;
	}

	public String getLatestTransactionIdFromMaster(String tcid) {
		String txnId = null;
		SheetMatchedAccessManager data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				txnId = element.get("Transaction ID");
			}
		}
		return txnId;
	}

	public String getLatestPreviousPolicyNumberFromMaster(String tcid) {
		String prevPolNum = null;
		SheetMatchedAccessManager data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				prevPolNum = element.get("Previous Policy Number");
			}
		}
		return prevPolNum;
	}

	public String getLatestValueFromMaster(String tcid, String val) {
		String retValue = null;
		SheetMatchedAccessManager data = new SheetMatchedAccessManager(fileName, sheetName);
		List<Map<String, String>> masterData = data.readExcelRowWise();
		for (Map<String, String> element : masterData) {
			if (element.get("tcid").equalsIgnoreCase(tcid)) {
				retValue = element.get(val);
			}
		}
		return retValue;
	}

}