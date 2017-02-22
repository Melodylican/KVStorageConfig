package com.dsky.kv.configservice.service;

import java.util.List;

import com.dsky.kv.configservice.model.UserBean;

public interface IKVUserConfig {
	String selectUserGameType(String userName);
	String selectUserRole(String userName);
	List<UserBean> selectAllUser();
	List<UserBean> selectUserByUserName(String userName);
	int updateUserState(String userName,int state);
	int updateUser(UserBean userBean);
	int insertUser(UserBean userBean);
	int deleteUserByUserName(String userName);

}
