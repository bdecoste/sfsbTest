package org.jboss.sfsbTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlTest {
	public static final String MYSQL_USERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    public static final String MYSQL_PASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
    public static final String MYSQL_DATABASE_HOST = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
    public static final String MYSQL_DATABASE_PORT = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
    public static final String MYSQL_DATABASE_NAME = System.getenv("OPENSHIFT_APP_NAME");
    public static final String MYSQL_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    
    private static Connection con;
    /**
     * returns connection to DB
     * @param con Connection
     */
    public static String initConnection(){
        String url = "";
       
        if(con==null){
            try {
            	Class.forName("com.mysql.jdbc.Driver");
                url = "jdbc:mysql://" + MYSQL_DATABASE_HOST + ":" + MYSQL_DATABASE_PORT + "/" + MYSQL_DATABASE_NAME;
                System.out.println("!!!!!!!!!!! url " + url);
                con = DriverManager.getConnection(url, MYSQL_USERNAME, MYSQL_PASSWORD);
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        return "Connection Initialized!";
    }
 
    public static Connection getConnection() {
        if (con == null) {
            initConnection();
        }
        return con;
    }
}
