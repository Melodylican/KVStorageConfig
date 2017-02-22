package com.dsky.kv.configservice.model;

public class WebStoreBean {
	//该条配置在数据库中对应的行号
	private int id;
	//游戏名
	private String gameName;
	//游戏Id
	private String gameId;
	//主  ip
	private String mIp;
	//主 port
	private String mPort;
	//主数据库用户名
	private String mUserName;
	//主数据库密码
	private String mPassword;
	//从 ip
	private String sIp;
	//从 port
	private String sPort;
    //从数据库用户名
	private String sUserName;
	//从数据库密码
	private String sPassword;
	//从数据库所占权重
	private String sWeight;
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
	public String getmIp() {
		return mIp;
	}
	public void setmIp(String mIp) {
		this.mIp = mIp;
	}
	public String getmPort() {
		return mPort;
	}
	public void setmPort(String mPort) {
		this.mPort = mPort;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public String getmPassword() {
		return mPassword;
	}
	public void setmPassword(String mPassword) {
		this.mPassword = mPassword;
	}
	public String getsIp() {
		return sIp;
	}
	public void setsIp(String sIp) {
		this.sIp = sIp;
	}
	public String getsPort() {
		return sPort;
	}
	public void setsPort(String sPort) {
		this.sPort = sPort;
	}
	public String getsUserName() {
		return sUserName;
	}
	public void setsUserName(String sUserName) {
		this.sUserName = sUserName;
	}
	public String getsPassword() {
		return sPassword;
	}
	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}
	public String getsWeight() {
		return sWeight;
	}
	public void setsWeight(String sWeight) {
		this.sWeight = sWeight;
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
