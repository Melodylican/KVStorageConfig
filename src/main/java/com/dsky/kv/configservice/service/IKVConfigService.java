package com.dsky.kv.configservice.service;


import java.util.List;

import org.springframework.stereotype.Component;

import com.dsky.kv.configservice.model.StoreBean;

/**
 * @ClassName: IGameConfigService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Component
public interface IKVConfigService {
	public int insertKVConfig(StoreBean storeBean);
	public List<StoreBean> selectAllStore();
	public int updateStoreById(StoreBean storeBean);
	public int deleteConfigById(String id);
	public StoreBean selectStoreById(String id);
	public StoreBean selectStoreByGameId(String gameId);
   
}
