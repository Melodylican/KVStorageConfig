package com.dsky.kv.configservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.kv.configservice.dao.UserConfigMapper;
import com.dsky.kv.configservice.model.UserBean;
import com.dsky.kv.configservice.service.IKVUserConfig;
@Service("userConfig")
public class KVUserConfig implements IKVUserConfig {
	private UserConfigMapper userMapper;

	public UserConfigMapper getGameConfigMapper() {
		return userMapper;
	}
	@Autowired
	public void setUserConfigMapper(UserConfigMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public String selectUserGameType(String userName) {
		return userMapper.selectUserGameType(userName);
	}

	@Override
	public String selectUserRole(String userName) {
		return null;
	}

	@Override
	public List<UserBean> selectAllUser() {
		return userMapper.selectAllUser();
	}

	@Override
	public List<UserBean> selectUserByUserName(String userName) {
		return null;
	}

	@Override
	public int updateUserState(String userName,int state) {
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("state", state);
		return userMapper.updateUserState(map);
	}

	@Override
	public int updateUser(UserBean userBean) {
		return userMapper.updateUser(userBean);
	}

	@Override
	public int insertUser(UserBean userBean) {
		return userMapper.insertUser(userBean);
	}

	@Override
	public int deleteUserByUserName(String userName) {
		return userMapper.deleteUserByUserName(userName);
	}

}
