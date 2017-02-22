package com.dsky.kv.configservice.model;

public class StoreBean {
	//该条配置在数据库中对应的行号
	private int id;
	//游戏名
	private String gameName;
	//游戏Id
	private String gameId;
	//主  ip:port
	private String master;
	//从 ip:port
	private String slave;
	//存放数据库名
	private String dbName;
	//存放表名
	private String tbName;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String getSlave() {
		return slave;
	}
	public void setSlave(String slave) {
		this.slave = slave;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getTbName() {
		return tbName;
	}
	public void setTbName(String tbName) {
		this.tbName = tbName;
	}

}
