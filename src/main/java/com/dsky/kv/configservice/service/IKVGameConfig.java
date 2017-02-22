package com.dsky.kv.configservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dsky.kv.configservice.model.GameBean;
@Component
public interface IKVGameConfig {
	public List<GameBean> selectAllGame();
    public int updateGameState(Map<String,Object> map);
    public int updateGameById(GameBean gameBean);
    public int insertGame(GameBean gameBean);
    public int deleteGameById(int id);
    public String selectGameIdByName(String gameName);

}
