package contactsapp.dao.connectionmanager;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Properties;

public class ConnectionManager  implements ConManager {
    private static volatile ConnectionManager instance;
    private static BasicDataSource ds ;
    private ConnectionManager(){
    }

    public static ConnectionManager getInstance() throws IOException {
        if(instance == null){
            synchronized (ConnectionManager.class) {
                if(instance == null) {
                    instance = new ConnectionManager();
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
        ds = new BasicDataSource();
        Properties dbProp = new Properties();
        String path = ConnectionManager.class.getClassLoader().getResource("db.properties").getPath();
        FileInputStream fis = new FileInputStream(path);
        dbProp.load(fis);

        String host = dbProp.getProperty("db.host");
        String login = dbProp.getProperty("db.login");
        String password = dbProp.getProperty("db.password");
        String driverClassName = dbProp.getProperty("db.driverClassName");
        String useUnicode = dbProp.getProperty("db.useUnicode");
        String useJDBCCompliantTimezoneShift = dbProp.getProperty("db.useJDBCCompliantTimezoneShift");
        String serverTimezome = dbProp.getProperty("db.serverTimezone");
        String useLegacyDatetimeCode = dbProp.getProperty("db.useLegacyDatetimeCode");

        ds.setUrl(host);
        ds.setUsername(login);
        ds.setPassword(password);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setDriverClassName(driverClassName);
        ds.setMaxOpenPreparedStatements(10);
    }


    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }


}
