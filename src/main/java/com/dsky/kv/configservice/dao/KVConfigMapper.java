package com.dsky.kv.configservice.dao;

import java.util.List;

import com.dsky.kv.configservice.model.StoreBean;
/**
 * @ClassName: KVConfigMapper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
public interface KVConfigMapper {
	int insertKVConfig(StoreBean storeBean);
	List<StoreBean> selectAllStore();
	int updateStoreById(StoreBean storeBean);
	int deleteConfigById(String id);
	StoreBean selectStoreById(String id);
	StoreBean selectStoreByGameId(String gameId);
    
}