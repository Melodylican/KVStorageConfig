package com.dsky.kv.configservice.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dsky.kv.configservice.logservice.IWarningReporterService;
import com.dsky.kv.configservice.model.InfoBean;
import com.dsky.kv.configservice.util.DateUtil;
/**
 * 链接mysql数据库
 * @author chris.li
 */
public class MysqlDbManager {
    //private static final String URL = "jdbc:mysql://127.0.0.1:3306/openfire";
    //private static final String USER = "root";
    //private static final String PASSWORD = "123456";
	private static final Logger logger = Logger.getLogger(MysqlDbManager.class);
	private IWarningReporterService warningReporterService;
	
	@Autowired
	public void setWarningReporterService(
			IWarningReporterService warningReporterService) {
		this.warningReporterService = warningReporterService;
	}
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("加载Mysql数据库驱动失败！");
        }
    }
    /**
     * 获取Connection
     * 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection(String URL,String USER,String PASSWORD) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            //System.out.println("获取数据库连接失败！");
            logger.info("MysqlDbManager类：getConnection(String URL,String USER,String PASSWORD) 获取数据库连接失败！");
            throw e;
        }
        return conn;
    }
    /**
     * 关闭ResultSet
     * @param rs
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                //System.out.println(e.getMessage());
                logger.info("MysqlDbManager类：closeResultSet(ResultSet rs) 出现异常 ");
            }
        }
    }
    /**
     * 关闭Statement
     * @param stmt
     */
    public static void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }       
            catch (Exception e) {
                //System.out.println(e.getMessage());
                logger.info("MysqlDbManager类： closeStatement(Statement stmt) 方法出现异常！");
            }
        }
    }
    /**
     * 关闭ResultSet、Statement
     * @param rs
     * @param stmt
     */
    public static void closeStatement(ResultSet rs, Statement stmt) {
        closeResultSet(rs);
        closeStatement(stmt);
    }
    /**
     * 关闭PreparedStatement
     * @param pstmt
     * @throws SQLException
     */
    public static void fastcloseStmt(PreparedStatement pstmt) throws SQLException
    {
        pstmt.close();
    }
    /**
     * 关闭ResultSet、PreparedStatement
     * @param rs
     * @param pstmt
     * @throws SQLException
     */
    public static void fastcloseStmt(ResultSet rs, PreparedStatement pstmt) throws SQLException
    {
        rs.close();
        pstmt.close();
    }
    /**
     * 关闭ResultSet、Statement、Connection
     * @param rs
     * @param stmt
     * @param con
     */
    public static void closeConnection(ResultSet rs, Statement stmt, Connection con) {
        closeResultSet(rs);
        closeStatement(stmt);
        closeConnection(con);
    }
    /**
     * 关闭Statement、Connection
     * @param stmt
     * @param con
     */
    public static void closeConnection(Statement stmt, Connection con) {
        closeStatement(stmt);
        closeConnection(con);
    }
    /**
     * 关闭Connection
     * @param con
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
               con.close();
            }
            catch (Exception e) {
                //System.out.println(e.getMessage());
                logger.info("MysqlDbManager类： closeConnection(Connection con) 出现异常");
            }
        }
    }
    
    /**
     * 创建数据库表
     * @param URL
     * @param USER
     * @param PASSWORD
     * @param tableName
     * @return
     * @throws Exception 
     */
    public static int createTable(String URL,String USER,String PASSWORD,String tableName) throws Exception {
    	
    	System.out.println("成功加载MySQL驱动程序");
        // 一个Connection代表一个数据库连接
        Connection conn;
        int result = -1;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        String sql = "CREATE TABLE "+tableName+
	        " (id int(10) unsigned NOT NULL AUTO_INCREMENT,"+
	        "game_id int(10) unsigned NOT NULL DEFAULT '0',"+
	        "player_id int(10) unsigned NOT NULL DEFAULT '0',"+
	        "type int(10) unsigned NOT NULL DEFAULT '0' ,"+
	        "`key` varchar(255) NOT NULL,"+
	        "`val` varchar(5000) NOT NULL,"+
	        "create_at int(10) unsigned NOT NULL DEFAULT '0' ,"+
	        "life_sec int(11) NOT NULL DEFAULT '0' ,"+
	        "delete_at int(10) unsigned NOT NULL DEFAULT '0' ,"+
	        "PRIMARY KEY (`id`),KEY `gameId_key_deleteAt` (`game_id`,`key`,`delete_at`)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
	        logger.info("创建表的sql是 "+sql);
	        result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
	        logger.info("创建表的结果是 "+result);
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类：createTable(String URL,String USER,String PASSWORD,String tableName)  创建表【"+tableName+"】的过程中出现异常 "+e.getMessage());
			//e.printStackTrace();
			throw new Exception(e);
		}
		return result;
    }
    
    /**
     * 创建数据库
     * @param URL
     * @param USER
     * @param PASSWORD
     * @param dbName
     * @return
     * @throws Exception 
     */
    public static int createDB(String URL,String USER,String PASSWORD,String dbName) throws Exception {
        // 一个Connection代表一个数据库连接
        Connection conn;
        int result = -1;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        String sql = "CREATE DATABASE IF NOT EXISTS "+dbName+" CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'";
	        logger.info("创建数据库的sql "+sql);
	        result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
	        logger.info("创建数据库的结果 "+result);
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类： createDB(String URL,String USER,String PASSWORD,String dbName)  创建数据库的过程中出现异常 "+e.getMessage());
			throw new Exception(e);
		}
		return result;
    }
    
    /**
     * 判断数据库是否存在
     * @param URL
     * @param USER
     * @param PASSWORD
     * @param dbName
     * @return
     * @throws Exception 
     */
    public static int existsDB(String URL,String USER,String PASSWORD,String dbName) throws Exception {
        // 一个Connection代表一个数据库连接
        Connection conn;
        boolean result =true;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        
	        String sql = "SELECT * FROM information_schema.SCHEMATA where SCHEMA_NAME='"+dbName+"'";
	        logger.info("查询数据库是否存在的Sql "+sql);
	        ResultSet rs = stmt.executeQuery(sql);
	        if(rs.next()==false)
	        	result = false;
	        logger.info("查询数据库是否存在 "+result);
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类：existsDB(String URL,String USER,String PASSWORD,String dbName)   查询表是否存在的过程中出现异常 "+e.getMessage());
			throw new Exception(e);
		}
		if(result == false)
			return -1;
		else
			return 1;
    }
    
    public static int existsTB(String URL,String USER,String PASSWORD,String dbName,String tbName) throws Exception {
        // 一个Connection代表一个数据库连接
        Connection conn;
        boolean result = true;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        String sql = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA='"+dbName+"' and TABLE_NAME='"+tbName+"'";
	        logger.info("查询表是否存在的Sql "+sql);
	        ResultSet rs = stmt.executeQuery(sql);
	        if(rs.next()==false)
	        	result = false;
	        logger.info("查询数据表是否存在 "+result);
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类：existsTB(String URL,String USER,String PASSWORD,String dbName,String tbName) 查询表是否存在的过程中出现异常 "+e.getMessage());
			throw new Exception(e);
		}
		if(result == false)
			return -1;
		else
			return 1;
    }
    
    public static List<InfoBean> getInfoFromDB(String URL,String USER,String PASSWORD,String dbName,String tbName,int startRow,int endRow) throws Exception {
    	List<InfoBean> list = new ArrayList<InfoBean>();
    	Connection conn;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        String sql = "select id,game_id,player_id,type,`key`,val,create_at,life_sec,delete_at from "+dbName+"."+tbName+" where delete_at <= 0 order by create_at desc limit "+startRow+","+endRow ;
	        logger.info("查询表是否存在的Sql "+sql);
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	        	InfoBean ib = new InfoBean();
	            ib.setId(rs.getInt("id"));
	            ib.setKey(rs.getString("key"));
	            ib.setPlayerId(rs.getString("player_id"));
	            ib.setValue(rs.getString("val"));
	            ib.setType(rs.getInt("type"));
	            ib.setGameId(rs.getInt("game_id")+"");
	            ib.setCreateAt(DateUtil.parseDate(rs.getLong("create_at")*1000,"yyyy/MM/dd HH:mm"));
	            ib.setLife(DateUtil.parseDate((rs.getLong("create_at")+rs.getLong("life_sec"))*1000,"yyyy/MM/dd HH:mm"));
	            list.add(ib);
	         }
	         rs.close();
	        logger.info("查询数据条数  "+list.size());
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类：getInfoFromDB(String URL,String USER,String PASSWORD,String dbName,String tbName,int startRow,int endRow)  查询【"+dbName+"."+tbName+"】是否存在的过程中出现异常 "+e.getMessage());
			throw new Exception(e);
		}
    	return list;
    }
    
    public static int getInfoTotalRows(String URL,String USER,String PASSWORD,String dbName,String tbName) throws Exception {
    	int totalRow=0;
    	Connection conn;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        String sql = "select count(*) as count from "+dbName+"."+tbName+" where delete_at <= 0 ";
	        logger.info("查询表是否存在的Sql "+sql);
	        ResultSet rs = stmt.executeQuery(sql);
	        rs.next();
			totalRow = rs.getInt("count");
            rs.close();
	        logger.info("查询数据条数  "+totalRow);
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类：getInfoTotalRows(String URL,String USER,String PASSWORD,String dbName,String tbName) 查询【"+dbName+"."+tbName+"】总条数的过程中出现异常 "+e.getMessage());
			throw new Exception(e);
		}
    	return totalRow;
    }
    
    /**
     * 用于用户搜索时查询符合搜索条件的数据
     * @param URL
     * @param USER
     * @param PASSWORD
     * @param dbName
     * @param tbName
     * @param startRow
     * @param endRow
     * @param beginTime
     * @param endTime
     * @return
     * @throws Exception 
     */
    public static List<InfoBean> getInfoFromDB(String URL,String USER,String PASSWORD,String dbName,String tbName,int startRow,int endRow,String beginTime,String endTime,String key,String userId) throws Exception {
    	List<InfoBean> list = new ArrayList<InfoBean>();
    	Connection conn;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        StringBuilder sql =new StringBuilder();
	        if(beginTime != null && !beginTime.equals("")) {
	        	logger.info("查询时的 开始时间 ："+beginTime);
	        	long begin = DateUtil.parseDateFromString(beginTime,"yyyy/MM/dd HH:mm",0)/1000;
	        	if(endTime != null && !endTime.equals("")) {
	        		logger.info("查询时的 结束时间 ："+endTime);
	        		long end = DateUtil.parseDateFromString(endTime,"yyyy/MM/dd HH:mm",0)/1000;
	        		sql.append( "select id,game_id,player_id,type,`key`,val,create_at,life_sec,delete_at from "+dbName+"."+tbName+" where delete_at <= 0 and create_at >="+begin+" and create_at <= "+end);
	        	}else {
	        		sql.append("select id,game_id,player_id,type,`key`,val,create_at,life_sec,delete_at from "+dbName+"."+tbName+" where delete_at <= 0 and create_at >="+begin);
	        	}
	        }else {
        		sql.append("select id,game_id,player_id,type,`key`,val,create_at,life_sec,delete_at from "+dbName+"."+tbName+" where delete_at <= 0");
	        }
	        if(key != null && !key.equals("")) {
	        	logger.info("查询时的 key ："+key);
	        	sql.append(" and `key` = '"+key+"'"); 
	        }
	        if(userId != null && !userId.trim().equals("")) {
	        	logger.info("查询时的userId ："+userId);
	        	sql.append( " and player_id  = "+userId.trim()+" order by create_at desc limit "+startRow+","+endRow); 
	        }else {
	        	sql.append(" order by create_at desc limit "+startRow+","+endRow) ;
	        }
	        logger.info("查询时的sql "+sql);
	        ResultSet rs = stmt.executeQuery(sql.toString());
	        while(rs.next()){
	        	InfoBean ib = new InfoBean();
	            ib.setId(rs.getInt("id"));
	            ib.setKey(rs.getString("key"));
	            ib.setPlayerId(rs.getString("player_id"));
	            ib.setValue(rs.getString("val"));
	            ib.setType(rs.getInt("type"));
	            ib.setGameId(rs.getInt("game_id")+"");
	            ib.setCreateAt(DateUtil.parseDate(rs.getLong("create_at")*1000,"yyyy/MM/dd HH:mm"));
	            ib.setLife(DateUtil.parseDate((rs.getLong("create_at")+rs.getLong("life_sec"))*1000,"yyyy/MM/dd HH:mm"));
	            list.add(ib);
	         }
	         rs.close();
	        logger.info("查询数据条数  "+list.size());
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类： getInfoFromDB(String URL,String USER,String PASSWORD,String dbName,String tbName,int startRow,int endRow,String beginTime,String endTime,String key,String userId)  查询【"+dbName+"."+tbName+"】是否存在的过程中出现异常 "+e.getMessage());
			throw new Exception(e);
		}
    	return list;
    }
    
    /**
     * 用于用户搜索时查询符合搜索条件的数据总行数
     * @param URL
     * @param USER
     * @param PASSWORD
     * @param dbName
     * @param tbName
     * @param beginTime
     * @param endTime
     * @return
     * @throws Exception 
     */
    public static int getInfoTotalRows(String URL,String USER,String PASSWORD,String dbName,String tbName,String beginTime,String endTime,String key,String userId) throws Exception {
    	int totalRow=0;
    	Connection conn;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        StringBuilder sql =new StringBuilder();
	        
	        if(beginTime != null && !beginTime.equals("")) {
	        	logger.info("查询总行数时的 开始时间 ："+beginTime);
	        	long begin = DateUtil.parseDateFromString(beginTime,"yyyy/MM/dd HH:mm",0)/1000;
	        	if(endTime != null && !endTime.equals("")) {
	        		logger.info("查询总行数时的 结束时间 ： "+endTime);
	        		long end = DateUtil.parseDateFromString(endTime,"yyyy/MM/dd HH:mm",0)/1000;
	        		sql.append("select count(*) as count from "+dbName+"."+tbName+" where delete_at <= 0 and create_at >="+begin+" and create_at <= "+end);
	        	}else {
	        		sql.append("select count(*) as count from "+dbName+"."+tbName+" where delete_at <= 0 and create_at >="+begin);
	        	}
	        }else {
        		sql.append("select count(*) as count from "+dbName+"."+tbName+" where delete_at <= 0");
	        }
	        if(key != null && !key.equals("")) {
	        	logger.info("查询总行数时的key ： "+key);
	        	sql.append(" and `key` = '"+key.trim()+"'"); 
	        }
	        if(userId != null && !userId.trim().equals("")) {
	        	logger.info("查询时的userId ："+userId);
	        	sql.append(" and player_id  = "+userId.trim()+" order by create_at desc"); 
	        }else {
	        	sql.append(" order by create_at desc");
	        }
	        
	        logger.info("查询表数据条数的sql "+sql);
	        ResultSet rs = stmt.executeQuery(sql.toString());
	        rs.next();
			totalRow = rs.getInt("count");
            rs.close();
	        logger.info("查询数据条数  "+totalRow);
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类：getInfoTotalRows(String URL,String USER,String PASSWORD,String dbName,String tbName,String beginTime,String endTime,String key,String userId)  查询【"+dbName+"."+tbName+"】总条数的过程中出现异常 "+e.getMessage());
			throw new Exception(e);
		}
    	return totalRow;
    }
    
    /**
     * @throws Exception 
     * 
     * 方法功能说明：删除配置信息
     * 创建： by chris.li 
     * 修改：日期 by 修改者
     * 修改内容：
     * @参数： @param URL
     * @参数： @param USER
     * @参数： @param PASSWORD
     * @参数： @param dbName
     * @参数： @param tbName
     * @参数： @param id
     * @参数： @return    
     * @return int   
     * @throws
     */
    public static int delInfo(String URL,String USER,String PASSWORD,String dbName,String tbName,String id) throws Exception {
        // 一个Connection代表一个数据库连接
        Connection conn;
        int result = -1;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        long timeNow = System.currentTimeMillis()/1000;
	        logger.info("删除【"+id+"】时的时间是：【"+timeNow+"】秒");
	        String sql = "update "+dbName+"."+tbName+" set delete_at="+timeNow+" where id="+id;
	        logger.info("删除数据时的sql "+sql);
	        result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
	        logger.info("删除数据时的结果 "+result);
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类：delInfo(String URL,String USER,String PASSWORD,String dbName,String tbName,String id) 创建数据库的过程中出现异常 "+e.getMessage());
			throw new Exception(e);
		}
		return result;
    	
    }
    
    public static int updateInfo(String URL,String USER,String PASSWORD,String dbName,String tbName,InfoBean infoBean) throws Exception {
        // 一个Connection代表一个数据库连接
        Connection conn;
        int result = -1;
		try {
			conn = getConnection(URL,USER,PASSWORD);
	        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
	        Statement stmt = conn.createStatement();
	        long life = (DateUtil.parseDateFromString(infoBean.getLife(),"yyyy/MM/dd HH:mm",0)- DateUtil.parseDateFromString(infoBean.getCreateAt(),"yyyy/MM/dd HH:mm",0))/1000;
            logger.info("过期时间是 ："+infoBean.getLife()+"  "+life);
	        String sql = "update "+dbName+"."+tbName+" set  game_id=" +infoBean.getGameId()+
	        		     ",player_id ='"+infoBean.getPlayerId()+
	        		     "', `key`='"+infoBean.getKey()+
	        		     "',val='"+infoBean.getValue()+
	        		     "',create_at="+DateUtil.parseDateFromString(infoBean.getCreateAt(),"yyyy/MM/dd HH:mm",0)/1000+
	        		     ",life_sec="+ life + " where id="+infoBean.getId();
	        logger.info("更新info时的sql "+sql);
	        result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
	        logger.info("更新info时的结果 "+result);
	        closeConnection(stmt,conn);//关闭资源
		} catch (SQLException e) {
			logger.error("MysqlDbManager类： updateInfo(String URL,String USER,String PASSWORD,String dbName,String tbName,InfoBean infoBean) 更新数据的过程中出现异常 "+e.getMessage());
			throw new Exception(e);
		}
		return result;
    	
    }
        
}