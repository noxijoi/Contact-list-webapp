package contactsapp.dao.connectionmanager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import contactsapp.utils.PropertiesManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariConnectionManager implements ConManager {
    private static volatile HikariConnectionManager instance;

    private static HikariConfig config;
    private static HikariDataSource ds ;
    private HikariConnectionManager(){
    }

    public static HikariConnectionManager getInstance() throws IOException {
        if(instance == null){
            synchronized (ConnectionManager.class) {
                if(instance == null) {
                    instance = new HikariConnectionManager();
                    initPool();
                }
            }
        }
        return instance;
    }

    public static void close() throws SQLException {
        ds.close();
    }

    private static void initPool() throws IOException {

        Properties dbProp = PropertiesManager.readProperties("db.properties");

        String host = dbProp.getProperty("db.host");
        String login = dbProp.getProperty("db.login");
        String password = dbProp.getProperty("db.password");

        config = new HikariConfig();
        config.setJdbcUrl(host);
        config.setUsername(login);
        config.setPassword(password);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }


    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
