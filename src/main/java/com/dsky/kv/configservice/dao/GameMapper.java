package com.dsky.kv.configservice.dao;

import java.util.List;
import java.util.Map;

import com.dsky.kv.configservice.model.GameBean;
/**
 * @ClassName: GameMapper
 * @Description: TODO(游戏管理相关方法)
 * @author Chris.li
 */
public interface GameMapper {
	List<GameBean> selectAllGame();
    int updateGameState(Map<String,Object> map);
    int updateGameById(GameBean gameBean);
    int insertGame(GameBean gameBean);
    int deleteGameById(int id);
    String selectGameIdByName(String gameName);
}