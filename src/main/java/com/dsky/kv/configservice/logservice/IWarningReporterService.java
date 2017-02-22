package com.dsky.kv.configservice.logservice;

public interface IWarningReporterService {
	public boolean reportWarn(LogReport logReportData);
	public void reportWarnString(String msg,int code);
	public void reportWarnString(String msg);

}
