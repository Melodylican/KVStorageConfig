package com.dsky.kv.configservice.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.dsky.kv.configservice.dao.jdbc.MysqlDbManager;
import com.dsky.kv.configservice.logservice.IWarningReporterService;
import com.dsky.kv.configservice.model.GameBean;
import com.dsky.kv.configservice.model.StoreBean;
import com.dsky.kv.configservice.model.WebStoreBean;
import com.dsky.kv.configservice.service.IKVConfigService;
import com.dsky.kv.configservice.service.IKVGameConfig;
import com.dsky.kv.configservice.util.CommonUtils;

/**
 * @ClassName: KVController
 * @Description: TODO(用于配置管理)
 * @author Chris.li
 */
@Controller
public class KVController {
	private static final Logger logger = Logger.getLogger(KVController.class);
	@Resource
	private IKVConfigService configService;
	@Resource
	private IKVGameConfig gameConfig;
	private IWarningReporterService warningReporterService;

	@Autowired
	public void setWarningReporterService(
			IWarningReporterService warningReporterService) {
		this.warningReporterService = warningReporterService;
	}

	@Autowired
	public void setGameConfigService(IKVConfigService configService) {
		this.configService = configService;
	}

	@Autowired
	public void setGameConfig(IKVGameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	/**
	 * 进入index页面
	 * 
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String gameCenter(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			HttpServletRequest request, Model model) {
		logger.info("KVController  -->   【/index】" + page + "  " + pageSize);
		/*
		 * List<GameBean> gameList = gameConfig.selectAllGame();//获取所有游戏
		 * GameBean gameBean =null; if(!gameList.isEmpty()) gameBean=
		 * gameList.get(0); else { gameBean = new GameBean();
		 * gameBean.setGameName(""); }
		 */
		List<StoreBean> listOrgin = configService.selectAllStore();
		logger.info("listOrgin size " + listOrgin.size());
		List<WebStoreBean> list = new ArrayList<WebStoreBean>();
		if (listOrgin != null) {
			for (StoreBean sb : listOrgin) {
				list.add(CommonUtils.parsStoreBeanList2WebStoreBeanList(sb));
			}
		}
		logger.info("list size " + list.size());
		model.addAttribute("list", list);
		return "index";
	}

	@RequestMapping(value = "/store/create", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String create(HttpServletRequest request, Model model) {
		logger.info("KVController  -->   【/store/create】");
		List<GameBean> gameList = gameConfig.selectAllGame();// 获取所有游戏

		model.addAttribute("gameList", gameList);
		return "storecreate";
	}

	@RequestMapping(value = "/store/update", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String update(HttpServletRequest request, Model model) {
		logger.info("KVController  -->   【/store/update】");
		List<GameBean> gameList = gameConfig.selectAllGame();// 获取所有游戏
		logger.info("获得的json为 " + request.getParameter("id"));
		String id = request.getParameter("id").toString();
		StoreBean storeBean = configService.selectStoreById(id);

		model.addAttribute("storeBean", storeBean);
		model.addAttribute("gameList", gameList);
		return "storeupdate";
	}

	@RequestMapping(value = "/store/savecreate", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveCeate(@ModelAttribute("storeBean") StoreBean storeBean,
			Model model) {
		logger.info("KVController  -->   【/store/savecreate】 "
				+ storeBean.getGameName());
		String gameName = storeBean.getGameName();
		String gameId = gameConfig.selectGameIdByName(gameName);
		storeBean.setGameId(gameId);

		// String ip_port = storeBean.
		// String URL = "jdbc:mysql://"+11+"127.0.0.1:3306/openfire";
		// MysqlDbManager.existsDB(URL, storeBean.getUserName(),
		// storeBean.getPassword(), storeBean.getDbName())
		WebStoreBean wb = CommonUtils
				.parsStoreBeanList2WebStoreBeanList(storeBean);
		if (wb != null) {
			// 分析出主中的ip 端口 用户名 密码 数据库名 表名
			String[] ip = wb.getmIp().split("<br>");
			String[] port = wb.getmPort().split("<br>");
			String[] userName = wb.getmUserName().split("<br>");
			String[] password = wb.getmPassword().split("<br>");

			// 开始在主数据库服务器上创建中数据库和表
			String URL = "jdbc:mysql://" + ip[0] + ":" + port[0] + "/test";
			logger.info("URL =" + URL + " 用户名： " + userName[0] + " 密码 "
					+ password[0]);
			try {
				int resultExists = MysqlDbManager.existsDB(URL, userName[0],
						password[0], wb.getDbName());
				logger.info("resultExists " + resultExists);
				if (resultExists < 0) {
					int resultCreate = MysqlDbManager.createDB(URL,
							userName[0], password[0], wb.getDbName());
					if (resultCreate < 0) {
						model.addAttribute("createMsg",
								"在主数据库中创建数据库 ！【" + wb.getDbName()
										+ "】时出现错误，请确保该【" + ip[0] + "】正常并重试！");
						List<GameBean> gameList = gameConfig.selectAllGame();// 获取所有游戏
						model.addAttribute("gameList", gameList);
						model.addAttribute("storeBean", storeBean);
						return "storeupdate";
					}
				}
				// 开始创建表
				int resultTbExists = MysqlDbManager.existsTB(URL, userName[0],
						password[0], wb.getDbName(), wb.getTbName());
				if (resultTbExists < 0) {
					String TB_URL = "jdbc:mysql://" + ip[0] + ":" + port[0]
							+ "/" + wb.getDbName();
					logger.info("TB_URL =" + TB_URL + " 用户名： " + userName[0]
							+ " 密码 " + password[0]);
					int resultCreate = MysqlDbManager.createTable(TB_URL,
							userName[0], password[0], wb.getTbName());
					if (resultCreate < 0) {
						model.addAttribute("createMsg",
								"在主数据库中创建数据表 ！【" + wb.getTbName()
										+ "】时出现错误，请确保该【" + ip[0] + "】正常并重试！");
						List<GameBean> gameList = gameConfig.selectAllGame();// 获取所有游戏
						model.addAttribute("gameList", gameList);
						model.addAttribute("storeBean", storeBean);
						return "storeupdate";
					}
				}
			} catch (Exception e) {
				logger.info("KVController /store/savecreate 方法出现异常  "
						+ e.getMessage());
				warningReporterService
						.reportWarnString("KVController /store/savecreate 方法出现异常  "
								+ e.getMessage());
			}
		}
		int i = configService.insertKVConfig(storeBean);
		if (i != 0)
			model.addAttribute("createMsg", "创建成功！");
		List<GameBean> gameList = gameConfig.selectAllGame();// 获取所有游戏
		model.addAttribute("gameList", gameList);
		model.addAttribute("storeBean", storeBean);
		return "storeupdate";
	}

	@RequestMapping(value = "/store/saveupdate", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveUpdate(@ModelAttribute("storeBean") StoreBean storeBean,
			Model model) {
		logger.info("KVController  -->   【/store/saveupdate】 "
				+ storeBean.getGameName());
		List<GameBean> gameList = gameConfig.selectAllGame();// 获取所有游戏
		String gameName = storeBean.getGameName();
		String gameId = gameConfig.selectGameIdByName(gameName);
		storeBean.setGameId(gameId);
		int i = configService.updateStoreById(storeBean);
		if (i != 0)
			model.addAttribute("updateMsg", "更新成功！");
		else
			model.addAttribute("updateMsg", "更新失败，请重试");
		model.addAttribute("storeBean", storeBean);
		model.addAttribute("gameList", gameList);
		return "storeupdate";
	}

	@RequestMapping(value = "/store/delete", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String delete(HttpServletRequest request, Model model) {
		logger.info("KVController  -->   【/store/create】");
		String id = request.getParameter("id");
		configService.deleteConfigById(id);// 调用删除配置
		return "forward:/index";
	}

}
