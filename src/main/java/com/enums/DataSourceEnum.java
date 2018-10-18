package com.enums;

public enum DataSourceEnum {

	LOG_AULOG("audit_log", "log"),

	MY_AULOG("log", "autoLog"),
	
	UNKNOW("pyx", "log");
	
	private final String index;

	private final String table;
	
	private DataSourceEnum(String index, String table) {
		this.index = index;
		this.table = table;
	}

	public String getIndex() {
		return index;
	}

	public String getTable() {
		return table;
	}

}
