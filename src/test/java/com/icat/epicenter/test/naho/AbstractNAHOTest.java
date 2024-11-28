package com.icat.epicenter.test.naho;

import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.config.TestType;
import com.icat.epicenter.test.AbstractNonDataTest;

public abstract class AbstractNAHOTest extends AbstractNonDataTest {

	public AbstractNAHOTest(LoginType loginType) {
		super(TestType.NAHO, loginType);
	}

	@Override
	protected String getReportName() {
		return "NAHO Report";
	}
}
