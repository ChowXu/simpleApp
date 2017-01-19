package util;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.CustomerService;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Project: simpleApp
 * Author: Chow xi
 * Email: zhouxu_1994@163.com
 * Time: 17/1/17 下午11:17
 */
public class DatabaseHelper {
    private static Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    private static final BasicDataSource dataSource;

    private static final QueryRunner queryRunner = new QueryRunner();

    private static final String driver;

    private static final String url;

    private static final String username;

    private static final String password;

    static {
        Properties config = PropUtil.loadProps("config.properties");
        driver = config.getProperty("jdbc.driver");
        url = config.getProperty("jdbc.url");
        username = config.getProperty("jdbc.username");
        password = config.getProperty("jdbc.password");

        //init dbcp
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        //load driver
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("cannot load driver");
        }

    }


    /**
     * 多表查询
     *
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection connection = getConnection();
            result = queryRunner.query(connection, sql, new MapListHandler(), params);
        } catch (Exception e) {
            logger.error("error query in tables", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 查询实体
     *
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;

        try {
            Connection connection = getConnection();
            entity = queryRunner.query(connection, sql, new BeanHandler<T>(entityClass), params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * dbHelper 帮助类, 跟框架类似
     *
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {

        List<T> entityList;
        try {
            Connection connection = getConnection();
            // new BeanListHander 生成 list 返回
            entityList = queryRunner.query(connection, sql, new BeanListHandler<T>(entityClass), params);

        }
        catch (Exception e) {
            logger.error("query failure");
            throw new RuntimeException(e);
        }
        return entityList;


    }


    /**
     * 更新操作
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows;
        try {
            Connection conn = getConnection();
            rows= queryRunner.update(conn,sql,params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return rows;
    }


    public static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql = "DELETE FROM " + getTableName(entityClass) + "WHERE id = ?";
        return executeUpdate(sql,id) == 1;
    }

    private static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }


    /**
     * 获取 连接
     *
     */
    public static Connection getConnection() {
        Connection connection = connectionHolder.get();
        if (connection == null) {
            try {
                connection = dataSource.getConnection();
            } catch (Exception e) {
                logger.error("failure in open connection", e);
                throw new RuntimeException(e);
            } finally {
                connectionHolder.set(connection);
            }
        }

        return connection;
    }


}
