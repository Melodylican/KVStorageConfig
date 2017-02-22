package com.dsky.kv.configservice.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.dsky.kv.configservice.logservice.IWarningReporterService;
import com.dsky.kv.configservice.model.GameBean;
import com.dsky.kv.configservice.service.IKVGameConfig;

/**
 * @ClassName: GameController
 * @Description: TODO(用于游戏管理)
 * @author Chris.li
 */
@Controller
public class GameController {
	private static final Logger logger = Logger.getLogger(GameController.class);
	@Resource
	private IKVGameConfig gameConfig;
	private IWarningReporterService warningReporterService;
	
	@Autowired
	public void setWarningReporterService(
			IWarningReporterService warningReporterService) {
		this.warningReporterService = warningReporterService;
	}
	@Autowired
	public void setGameConfig(IKVGameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	/**
	 * 进入创建游戏页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/game/create", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String createUser(Model model) {
		logger.info("GameController  -->   【/game/create】");
		return "gamecreate";
	}

	/**
	 * 更新用户配置信息
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/update", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String updateUser(HttpServletRequest request, Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/update】");
		//获得总行数
		GameBean gameBean =JSONObject.parseObject((request.getParameter("gameBean")),GameBean.class);
		try{
			logger.info("更新的游戏的行号 "+gameBean.getId());
			model.addAttribute("gameBean", gameBean);
		//数据入库
		}catch(Exception e ) {
			logger.error("GameController /game/update 更新游戏【"+gameBean.getGameName()+"】时出现异常:\n"+e.getMessage(),e);
			warningReporterService.reportWarnString("GameController /game/update 更新游戏【"+gameBean.getGameName()+"】时出现异常:\n"+e.getMessage());
		}
		return "gameupdate";
	}
	/**
	 * 保存更新用户信息入库
	 * @param userBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/saveupdate", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String saveUserUpdate(@ModelAttribute("userBean") GameBean gameBean,
			           Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/saveupdate】");
		int i= gameConfig.updateGameById(gameBean);
		if(i!=0) {
			model.addAttribute("updateMsg", "更新成功！");
		}else {
			model.addAttribute("updateMsg", "更新失败,请重新更新！");
		}
		model.addAttribute("gameBean", gameBean);
		return "gameupdate";
	}
	
	/**
	 * 保存创建游戏信息入库
	 * @param gameBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/savecreate", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String saveUserCreate(@ModelAttribute("gameBean") GameBean gameBean,Model model) throws InterruptedException {
		logger.info("GameController  -->   【/game/savegamecreate】");
		int i= gameConfig.insertGame(gameBean);
		if(i!=0) {
			model.addAttribute("createMsg", "新建成功！");
		}else {
			model.addAttribute("createMsg", "新建失败,请重新创建！");
		}
		model.addAttribute("gameBean", gameBean);
		return "gameupdate";
	}
	
	/**
	 * 删除游戏
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/game/delete", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String deleteUser(HttpServletRequest request, Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/game/delete】");
		int id =0;
		int i =0;
		if(request.getParameter("id")!=null){
		    id = Integer.parseInt(request.getParameter("id").toString());
		    i= gameConfig.deleteGameById(id);
		}
		if(i!=0 ) {
			model.addAttribute("deleteMsg", "删除成功！");
		}else {
			model.addAttribute("deleteMsg", "删除失败,请重新删除！");
		}
		return "forward:/game/game";
	}
	

	/**
	 * 进入游戏信息总页面
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/game/game", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String gameCenter(HttpServletRequest request,Model model) {
		logger.info("GameController  -->   【/game/game】");
		
		List<GameBean> list = gameConfig.selectAllGame();
		model.addAttribute("list", list);

		return "game";
	}

}
