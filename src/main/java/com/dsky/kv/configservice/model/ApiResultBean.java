package com.dsky.kv.configservice.model;

import java.io.Serializable;
/**
 * @ClassName: ApiResultBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public class ApiResultBean implements Serializable{

	    private static final long serialVersionUID = 7259584042890356969L;
		//访问状态码
		private Integer code ;
		//访问附加信息
		private String msg ;
		//活动id
		private String id;
		//游戏id
		private String gameId;
		//游戏名称
		private String gameName;
		//活动地区
		private String location;
	    //活动开始时间
		private String beginTime;
		//活动结束时间
		private String endTime;
		//活动预热时间
		private String preheatingTime;
		//活动简要描述
		private String description;
		//当前活动是开启还是暂停 0暂停 1开启
		private int state;
		//单台设备上可生成邀请码个数
		private int deviceCount;
		//推广员B获得成长积分时间要求
		private int time;
		//推官员B获得成长积分等级要求
		private int level;
		//一个邀请码使用次数限制
		private int recommandCount;
		//h5 分享连接地址
		private String h5Url;
		//分享小图标地址
		private String imgUrl;
		//分享标题
		private String title;
		//分享描述
		private String redeemDesc;
		//推广员A第一次可获得积分
		private int promoterA;
		//推广员A第二次可获得积分
		private int promoterASecond;
		//推广员B可获得的奖励积分
		private int promoterB;
		//新注册用户可获得积分
		private int register;
		//成为推广员的在线时间要求
		private int promoterATime;
		//成为推广员的等级要求
		private int promoterALevel;
		//可分享的方式1：代表微信,2:代表朋友圈,4:代表QQ,8:代表微博
		private String shareMethod;
		
		public Integer getCode() {
			return code;
		}
		public void setCode(Integer code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getGameId() {
			return gameId;
		}
		public void setGameId(String gameId) {
			this.gameId = gameId;
		}
		public String getGameName() {
			return gameName;
		}
		public void setGameName(String gameName) {
			this.gameName = gameName;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}

		public String getBeginTime() {
			return beginTime;
		}
		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public String getPreheatingTime() {
			return preheatingTime;
		}
		public void setPreheatingTime(String preheatingTime) {
			this.preheatingTime = preheatingTime;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getDeviceCount() {
			return deviceCount;
		}
		public void setDeviceCount(int deviceCount) {
			this.deviceCount = deviceCount;
		}
		public int getTime() {
			return time;
		}
		public void setTime(int time) {
			this.time = time;
		}
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public int getRecommandCount() {
			return recommandCount;
		}
		public void setRecommandCount(int recommandCount) {
			this.recommandCount = recommandCount;
		}
		public int getPromoterA() {
			return promoterA;
		}
		public void setPromoterA(int promoterA) {
			this.promoterA = promoterA;
		}
		public int getPromoterB() {
			return promoterB;
		}
		public void setPromoterB(int promoterB) {
			this.promoterB = promoterB;
		}
		public int getRegister() {
			return register;
		}
		public void setRegister(int register) {
			this.register = register;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public String getH5Url() {
			return h5Url;
		}
		public void setH5Url(String h5Url) {
			this.h5Url = h5Url;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getRedeemDesc() {
			return redeemDesc;
		}
		public void setRedeemDesc(String redeemDesc) {
			this.redeemDesc = redeemDesc;
		}
		public int getPromoterASecond() {
			return promoterASecond;
		}
		public void setPromoterASecond(int promoterASecond) {
			this.promoterASecond = promoterASecond;
		}
		public int getPromoterATime() {
			return promoterATime;
		}
		public void setPromoterATime(int promoterATime) {
			this.promoterATime = promoterATime;
		}
		public int getPromoterALevel() {
			return promoterALevel;
		}
		public void setPromoterALevel(int promoterALevel) {
			this.promoterALevel = promoterALevel;
		}
		public String getShareMethod() {
			return shareMethod;
		}
		public void setShareMethod(String shareMethod) {
			this.shareMethod = shareMethod;
		}
}
