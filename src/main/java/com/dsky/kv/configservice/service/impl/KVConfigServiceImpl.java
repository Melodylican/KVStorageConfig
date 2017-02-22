package com.dsky.kv.configservice.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.kv.configservice.dao.KVConfigMapper;
import com.dsky.kv.configservice.model.StoreBean;
import com.dsky.kv.configservice.service.IKVConfigService;
/**
 * @ClassName: GameConfigServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Service("gameConfigService")
public  class KVConfigServiceImpl implements IKVConfigService {
	private static final Logger logger = Logger.getLogger(KVConfigServiceImpl.class);
	private KVConfigMapper kvConfigMapper;

	public KVConfigMapper getGameConfigMapper() {
		return kvConfigMapper;
	}
	@Autowired
	public void setGameConfigMapper(KVConfigMapper kvConfigMapper) {
		this.kvConfigMapper = kvConfigMapper;
	}
	@Override
	public int insertKVConfig(StoreBean storeBean) {
		return kvConfigMapper.insertKVConfig(storeBean);
	}
	@Override
	public List<StoreBean> selectAllStore() {
		return kvConfigMapper.selectAllStore();
	}
	@Override
	public int updateStoreById(StoreBean storeBean) {
		return kvConfigMapper.updateStoreById(storeBean);
	}
	@Override
	public int deleteConfigById(String id) {
		return kvConfigMapper.deleteConfigById(id);
	}
	@Override
	public StoreBean selectStoreById(String id) {
		return kvConfigMapper.selectStoreById(id);
	}
	@Override
	public StoreBean selectStoreByGameId(String gameId) {
		return kvConfigMapper.selectStoreByGameId(gameId);
	}
	
/*
	@Override
	public List<PromoterBean> selectByPaging(int startRow, int endRow,String createdBy,String userRole) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		map.put("createdBy", createdBy);
		map.put("userRole", userRole);
		return gameConfigMapper.selectByPaging(map);
	}
*/
	

}
