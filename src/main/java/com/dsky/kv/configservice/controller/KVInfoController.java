package com.dsky.kv.configservice.controller;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.dsky.kv.configservice.dao.jdbc.MysqlDbManager;
import com.dsky.kv.configservice.logservice.IWarningReporterService;
import com.dsky.kv.configservice.model.GameBean;
import com.dsky.kv.configservice.model.InfoBean;
import com.dsky.kv.configservice.model.StoreBean;
import com.dsky.kv.configservice.model.WebStoreBean;
import com.dsky.kv.configservice.service.IKVConfigService;
import com.dsky.kv.configservice.service.IKVGameConfig;
import com.dsky.kv.configservice.service.IKVUserConfig;
import com.dsky.kv.configservice.util.CommonUtils;
/**
 * @ClassName: KVController
 * @Description: TODO(用于配置管理)
 * @author Chris.li
 */
@Controller
public class KVInfoController {
	private static final Logger logger = Logger.getLogger(KVInfoController.class);
	@Resource
	private IKVConfigService configService;
	@Resource
	private IKVGameConfig gameConfig;
	
	@Resource
	private IKVUserConfig userConfig;
	@Resource
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
	
	@Autowired
	public void setUserConfig(IKVUserConfig userConfig) {
		this.userConfig = userConfig;
	}

	/**
	 * 进入index页面
	 * @param page
	 * @param pageSize
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/info", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String info(@RequestParam(value="page" ,defaultValue="1") int page, @RequestParam(value="pageSize" ,defaultValue="12")int pageSize,HttpServletRequest request,Model model) {
		logger.info("KVInfoController  -->   【/info】"+page+"  "+pageSize);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		logger.info("当前登录的用户名是：" + userName);
		
		String gameType = userConfig.selectUserGameType(userName);
		logger.info("拥有权限的游戏是："+gameType);
		String[] gameNameArr = null;
		String gameId="";
		if (gameType != null) {
			gameNameArr = gameType.split(",");
			gameId = gameConfig.selectGameIdByName(gameNameArr[0]);
			logger.info("当前查询的游戏Id是 ： "+gameId);
			model.addAttribute("searchGameName", gameNameArr[0]);
		} else
			gameNameArr = new String[] { "" };
		List<String> gameNameList = Arrays.asList(gameNameArr);//将用户拥有权限的游戏保存为List
		logger.info("gameId="+gameId);
		if(!gameId.equals("")) {
			logger.info("开始查询配置信息");
			StoreBean sBean = configService.selectStoreByGameId(gameId);
			if(sBean != null )
			    logger.info("查询的配置信息是 ："+sBean.getMaster());
			else
				logger.info("查询的配置信息为空！");
			WebStoreBean wBean = CommonUtils.parsStoreBeanList2WebStoreBeanList(sBean);
			if(wBean == null)
				logger.info("转换时出现异常！");
			if(wBean != null) {
				String[] sIp = wBean.getsIp().split("<br>");
				String[] sPort = wBean.getsPort().split("<br>");
				String[] sUserName =wBean.getsUserName().split("<br>");
				String[] sPassword =wBean.getsPassword().split("<br>");
				
				Random rand = new Random();
				int i = rand.nextInt(sIp.length); //生成0-sIp.length以内的随机数
				logger.info("在随机选择备机是的随机数是： "+i);
				
				String TB_URL = "jdbc:mysql://"+sIp[i]+":"+sPort[i]+"/"+wBean.getDbName();
				String password="";
				if(sPassword.length >= i)
					password = sPassword[i];
				logger.info("连接数据库的URL = "+TB_URL +"  其它信息  "+sUserName[i]+"  "+ password+ "  "+wBean.getDbName()+ " " + wBean.getTbName());
				try{
				List<InfoBean> list = MysqlDbManager.getInfoFromDB(TB_URL, sUserName[i], password, wBean.getDbName(), wBean.getTbName(),(page-1)*pageSize, pageSize);
				int rowCount = MysqlDbManager.getInfoTotalRows(TB_URL, sUserName[i], password, wBean.getDbName(), wBean.getTbName());
				model.addAttribute("list", list);
				int pages=0;
				if(rowCount % pageSize == 0)
					pages = rowCount/pageSize;
				else
					pages = rowCount/pageSize+1;

				model.addAttribute("pages", pages);
				model.addAttribute("page", page);
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("list", list);
				logger.info("pages = "+pages +"  page="+page);
				}catch(Exception e) {
					logger.error("KVInfoController /info"+e.getMessage());
					warningReporterService.reportWarnString("KVInfoController /info"+e.getMessage());
				}
			}
		}
		
		model.addAttribute("gameNameList", gameNameList);
		return "info";
	}
	
	@RequestMapping(value = "/info/search", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String searchInfo(@RequestParam(value="page" ,defaultValue="1") int page, @RequestParam(value="pageSize" ,defaultValue="12")int pageSize,HttpServletRequest request,Model model) {
		logger.info("KVInfoController  -->   【/info/search】"+page+"  "+pageSize);
		
		String searchGameName = request.getParameter("searchGameName");
		String key = request.getParameter("key");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String userId = request.getParameter("userId");
		//用于接收其它方法跳转时传递的供页面展示的信息
		String msg = request.getParameter("msg");
		
		if(msg!= null && !msg.equals(""))
			model.addAttribute("msg", msg);
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		logger.info("当前登录的用户名是：" + userName);
		
		String gameType = userConfig.selectUserGameType(userName);
		logger.info("拥有权限的游戏是："+gameType);
		String[] gameNameArr = null;
		String gameId="";
		if (gameType != null) {
			gameNameArr = gameType.split(",");
			gameId = gameConfig.selectGameIdByName(searchGameName);
			logger.info("当前查询的游戏Id是 ： "+gameId);
			model.addAttribute("searchGameName", searchGameName);
		} else
			gameNameArr = new String[] { "" };
		List<String> gameNameList = Arrays.asList(gameNameArr);//将用户拥有权限的游戏保存为List
		logger.info("gameId="+gameId);
		if(gameId != null && !gameId.equals("")) {
			logger.info("开始查询配置信息");
			StoreBean sBean = configService.selectStoreByGameId(gameId);
			if(sBean != null )
			    logger.info("查询的配置信息是 ："+sBean.getMaster());
			else
				logger.info("查询的配置信息为空！");
			WebStoreBean wBean = CommonUtils.parsStoreBeanList2WebStoreBeanList(sBean);
			if(wBean == null)
				logger.info("转换时出现异常！");
			if(wBean != null) {
				String[] sIp = wBean.getsIp().split("<br>");
				String[] sPort = wBean.getsPort().split("<br>");
				String[] sUserName = wBean.getsUserName().split("<br>");
				String[] sPassword = wBean.getsPassword().split("<br>");
				
				Random rand = new Random();
				int i = rand.nextInt(sIp.length); //生成0-sIp.length以内的随机数
				logger.info("在随机选择备机是的随机数是： "+i);
				
				String TB_URL = "jdbc:mysql://"+sIp[i]+":"+sPort[i]+"/"+wBean.getDbName();
				
				String password="";
				if(sPassword.length >= i)
					password = sPassword[i];
				logger.info("连接数据库的URL = "+TB_URL +"  其它信息  "+sUserName[i]+"  "+ password+ "  "+wBean.getDbName()+ " " + wBean.getTbName());

				List<InfoBean> list = null;
				int rowCount = 0;
				try{
					list = MysqlDbManager.getInfoFromDB(TB_URL, sUserName[i], password, wBean.getDbName(), wBean.getTbName(),(page-1)*pageSize, pageSize,beginTime,endTime,key,userId);
					rowCount = MysqlDbManager.getInfoTotalRows(TB_URL, sUserName[i], password, wBean.getDbName(), wBean.getTbName(),beginTime,endTime,key,userId);
				}catch(Exception e) {
					logger.error("KVInfoController /info/search 出现异常"+e.getMessage());
					warningReporterService.reportWarnString("KVInfoController /info/search 出现异常"+e.getMessage());
					
				}
				model.addAttribute("list", list);
				int pages=0;
				if(rowCount % pageSize == 0)
					pages = rowCount/pageSize;
				else
					pages = rowCount/pageSize+1;
				
				if(userId != null && !userId.equals("")) {
					model.addAttribute("searchUserId", userId);
				}
				if(key != null && !key.equals("")) {
					model.addAttribute("searchKey", key);
				}
				if(beginTime != null && !beginTime.equals("")) {
					model.addAttribute("searchBeginTime", beginTime);
				}
				if(endTime != null && !endTime.equals("")) {
					model.addAttribute("searchEndTime", endTime);
				}
				
				model.addAttribute("pages", pages);
				model.addAttribute("page", page);
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("list", list);
			}
		}
		
		model.addAttribute("gameNameList", gameNameList);
		return "info";
	}
	
	@RequestMapping(value = "/info/delete", method = {RequestMethod.GET,RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public String delete(HttpServletRequest request,Model model) throws UnsupportedEncodingException {
		logger.info("KVInfoController  -->   【/info/delete】");
		
		String searchGameName = request.getParameter("searchGameName");
		logger.info("删除时传递的游戏名字是： "+searchGameName);
		String id = request.getParameter("id");
		logger.info("删除时传递的删除行号是 ："+id);
		
		//获取当前登录用户的用户名
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		logger.info("当前登录的用户名是：" + userName);

		String gameId= gameConfig.selectGameIdByName(searchGameName);
		logger.info("当前查询的游戏Id是 ： "+gameId);
		String msg="";//记录删除结果
		if(gameId != null && !gameId.equals("")) {
			logger.info("开始查询配置信息");
			StoreBean sBean = configService.selectStoreByGameId(gameId);
			if(sBean != null )
			    logger.info("查询的配置信息是 ："+sBean.getMaster());
			else
				logger.info("查询的配置信息为空！");
			WebStoreBean wBean = CommonUtils.parsStoreBeanList2WebStoreBeanList(sBean);
			if(wBean == null)
				logger.info("转换时出现异常！");
			if(wBean != null) {
				String[] mIp = wBean.getmIp().split("<br>");
				String[] mPort = wBean.getmPort().split("<br>");
				String[] mUserName =wBean.getmUserName().split("<br>");
				String[] mPassword =wBean.getmPassword().split("<br>");
				
				Random rand = new Random();
				int i = rand.nextInt(mIp.length); //生成0-mIp.length以内的随机数
				logger.info("在随机选择备机是的随机数是： "+i);
				//查询数据库中数据库对应的行数
				String TB_URL = "jdbc:mysql://"+mIp[i]+":"+mPort[i]+"/"+wBean.getDbName();
				logger.info("连接数据库的URL = "+TB_URL +"  其它信息  "+mUserName[i]+"  "+ mPassword[i]+ "  "+wBean.getDbName()+ " " + wBean.getTbName());
				int result = -1;
				try{
					result = MysqlDbManager.delInfo(TB_URL, mUserName[i], mPassword[i], wBean.getDbName(), wBean.getTbName(),id);
				}catch(Exception e) {
					logger.error("KVInfoController /info/delete 出现异常"+e.getMessage());
					warningReporterService.reportWarnString("KVInfoController /info/delete 出现异常"+e.getMessage());
				}
 
				if(result >= 0)
					msg = "删除数据成功！";
			}
		}
		//使用请求转发
		return "forward:/info/search?searchGameName="+java.net.URLEncoder.encode(searchGameName,"utf-8")+"&msg="+msg;
	}
	
	/**
	 * 更新已存储信息
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/info/update", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String updateInfo(HttpServletRequest request, Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/info/update】");
		InfoBean infoBean = JSONObject.parseObject(
				(request.getParameter("infoBean")), InfoBean.class);
		model.addAttribute("infoBean", infoBean);
		
		List<GameBean> gameList = gameConfig.selectAllGame();
		logger.info("更新时游戏种数："+gameList.size());
		model.addAttribute("gameList", gameList);
		return "infoupdate";
	}
	
	/**
	 * 更新已存储信息
	 * @param request
	 * @param model
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/info/saveupdate", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String saveUpdate(@ModelAttribute("infoBean") InfoBean infoBean, Model model) throws InterruptedException {
		logger.info("UserConfigController  -->   【/info/update】");

		
		//获取当前登录用户的用户名
		UserDetails userDetails = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userName = userDetails.getUsername();
		logger.info("当前登录的用户名是：" + userName);
		logger.info("当前查询的游戏名是："+infoBean.getGameName());

		String gameId= gameConfig.selectGameIdByName(infoBean.getGameName());
		logger.info("当前查询的游戏Id是 ： "+gameId);
		String msg="";//记录删除结果
		if(gameId != null && !gameId.equals("")) {
			logger.info("开始查询配置信息");
			StoreBean sBean = configService.selectStoreByGameId(gameId);
			if(sBean != null )
			    logger.info("查询的配置信息是 ："+sBean.getMaster());
			else
				logger.info("查询的配置信息为空！");
			WebStoreBean wBean = CommonUtils.parsStoreBeanList2WebStoreBeanList(sBean);
			if(wBean == null)
				logger.info("转换时出现异常！");
			if(wBean != null) {
				String[] mIp = wBean.getmIp().split("<br>");
				String[] mPort = wBean.getmPort().split("<br>");
				String[] mUserName =wBean.getmUserName().split("<br>");
				String[] mPassword =wBean.getmPassword().split("<br>");
				
				Random rand = new Random();
				int i = rand.nextInt(mIp.length); //生成0-mIp.length以内的随机数
				logger.info("在随机选择备机是的随机数是： "+i);
				//查询数据库中数据库对应的行数
				String TB_URL = "jdbc:mysql://"+mIp[i]+":"+mPort[i]+"/"+wBean.getDbName();
				logger.info("连接数据库的URL = "+TB_URL +"  其它信息  "+mUserName[i]+"  "+ mPassword[i]+ "  "+wBean.getDbName()+ " " + wBean.getTbName());
				int result = -1;
				try{
				result = MysqlDbManager.updateInfo(TB_URL, mUserName[i], mPassword[i], wBean.getDbName(), wBean.getTbName(),infoBean);
				}catch(Exception e) {
					logger.error("UserConfigController  -->   【/info/update】出现异常 "+e.getMessage());
					warningReporterService.reportWarnString("UserConfigController  -->   【/info/update】出现异常 "+e.getMessage());
				}

				if(result >= 0)
					msg = "修改数据成功！";
			}
		}
		logger.info("msg="+msg);
		model.addAttribute("msg", msg);
		model.addAttribute("infoBean", infoBean);
		return "infoupdate";
	}
	

}
