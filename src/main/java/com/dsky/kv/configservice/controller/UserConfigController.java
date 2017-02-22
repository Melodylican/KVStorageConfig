package com.dsky.kv.configservice.controller;

import java.text.SimpleDateFormat;
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
import com.dsky.kv.configservice.model.GameBean;
import com.dsky.kv.configservice.model.UserBean;
import com.dsky.kv.configservice.service.IKVGameConfig;
import com.dsky.kv.configservice.service.IKVUserConfig;

/**
 * @ClassName: UserConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Chris.li
 */
@Controller
public class UserConfigController {
	private static final Logger logger = Logger
			.getLogger(UserConfigController.class);
	@Resource
	private IKVUserConfig userConfig;
	@Resource
	private IKVGameConfig gameConfig;
	@Autowired
	public void setUserConfig(IKVUserConfig userConfig) {
		this.userConfig = userConfig;
	}
	@Autowired
	public void setGameConfig(IKVGameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	/**
	 * 进入创建用户页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/user", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String user(Model model) {
		logger.info("UserConfigController  -->   【/user/user】");
		List<UserBean> list = userConfig.selectAllUser();
		model.addAttribute("list", list);
		return "user";
	}
	
	@RequestMapping(value = "/user/create", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String createUser(Model model) {
		logger.info("UserConfigController  -->   【/user/create】");
		List<GameBean> gameList = gameConfig.selectAllGame();
		model.addAttribute("gameList", gameList);
		return "usercreate";
	}

	/**
	 * 根据用户名查询用户
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/search", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String userSearch(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/search】");

		return "user";
	}

	/**
	 * 进入更新用户配置信息
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/update", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String updateUser(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/update】");
		UserBean userBean = JSONObject.parseObject(
				(request.getParameter("userBean")), UserBean.class);
		model.addAttribute("userBean", userBean);
		
		List<GameBean> gameList = gameConfig.selectAllGame();
		logger.info("更新时游戏种数："+gameList.size());
		model.addAttribute("gameList", gameList);
		return "userupdate";
	}

	/**
	 * 保存更新用户信息入库
	 * @param userBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/saveupdate", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveUserUpdate(@ModelAttribute("userBean") UserBean userBean,
			Model model) throws InterruptedException {

		logger.info("UserConfigController  -->   【/user/saveupdate】 "+userBean.getGameType());
		int i = userConfig.updateUser(userBean);
		if(i!=0)
			model.addAttribute("updateMsg", "更新成功！");
		
		model.addAttribute("userBean", userBean);
		List<GameBean> gameList = gameConfig.selectAllGame();
		logger.info("保存更新时游戏种数："+gameList.size());
		model.addAttribute("gameList", gameList);

		return "userupdate";
	}

	/**
	 * 保存创建用户信息入库
	 * 
	 * @param userBean
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/savecreate", method = {
			RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveUserCreate(@ModelAttribute("userBean") UserBean userBean,
			Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/saveusercreate】 "+userBean.getGameType());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long now = System.currentTimeMillis();
		userBean.setRegisterTime(sf.format(now));
		logger.info("注册时间是："+sf.format(now));
		
		int i = userConfig.insertUser(userBean);
		if(i!=0) 
			model.addAttribute("createMsg", "创建用户成功");

		return "userupdate";
	}

	/**
	 * 删除用户
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String deleteUser(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/delete】");
		String userName = request.getParameter("userName");
		int i= userConfig.deleteUserByUserName(userName);
		String deleteMsg="";
		if(i!=0)
			deleteMsg = "删除成功！";
		logger.info("删除信息 ："+deleteMsg);
		return "forward:/user/user";
	}

	/**
	 * 更新用户
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/user/state", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String userState(HttpServletRequest request, Model model)
			throws InterruptedException {
		logger.info("UserConfigController  -->   【/user/state】");
		String userName = request.getParameter("userName").toString();
		int state = Integer.parseInt(request.getParameter("state").toString());

		String msg = "";
		if (state == 1)
			msg = "关闭成功！";
		else
			msg = "开启成功！";
		int updateStateMsg = userConfig.updateUserState(userName,
				state ^ 1);
		if (updateStateMsg > 0)
			model.addAttribute("updateStateMsg", msg);
		return "forward:/user/user";
	}

}
