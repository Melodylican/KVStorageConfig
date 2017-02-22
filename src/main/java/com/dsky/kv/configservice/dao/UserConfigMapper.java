package com.dsky.kv.configservice.dao;

import java.util.List;
import java.util.Map;

import com.dsky.kv.configservice.model.UserBean;

public interface UserConfigMapper {
	String selectUserGameType(String userName);
	String selectUserRole(String userName);
	List<UserBean> selectAllUser();
	List<UserBean> selectUserByUserName(String userName);
	int updateUserState(Map<String,Object> map);
	int updateUser(UserBean userBean);
	int insertUser(UserBean userBean);
	int deleteUserByUserName(String userName);

}
