package com.dsky.kv.configservice.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dsky.kv.configservice.dao.GameMapper;
import com.dsky.kv.configservice.model.GameBean;
import com.dsky.kv.configservice.service.IKVGameConfig;


@Service("gameConfig")
public class KVGameConfigImpl implements IKVGameConfig {
	
	private GameMapper gameMapper;

	public GameMapper getGameMapper() {
		return gameMapper;
	}
	@Autowired
	public void setGameMapper(GameMapper gameMapper) {
		this.gameMapper = gameMapper;
	}

	@Override
	public List<GameBean> selectAllGame() {
		return gameMapper.selectAllGame();
	}


	@Override
	public int updateGameState(Map<String, Object> map) {
		return gameMapper.updateGameState(map);
	}

	@Override
	public int updateGameById(GameBean gameBean) {
		return gameMapper.updateGameById(gameBean);
	}

	@Override
	public int insertGame(GameBean gameBean) {
		return gameMapper.insertGame(gameBean);
	}

	@Override
	public int deleteGameById(int id) {
		return gameMapper.deleteGameById(id);
	}
	@Override
	public String selectGameIdByName(String gameName) {
		return gameMapper.selectGameIdByName(gameName);
	}

}
