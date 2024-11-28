package com.icat.epicenter.test.hiho;

import com.icat.epicenter.config.LoginType;
import com.icat.epicenter.config.TestType;
import com.icat.epicenter.test.AbstractNonDataTest;

public abstract class AbstractHIHOTest extends AbstractNonDataTest {

	public AbstractHIHOTest(LoginType loginType) {
		super(TestType.HIHO, loginType);
	}

	@Override
	protected String getReportName() {
		return "HIHO Report";
	}
}
