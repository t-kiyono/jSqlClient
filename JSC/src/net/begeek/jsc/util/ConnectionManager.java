package net.begeek.jsc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBコネクションユーティリティ。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ConnectionManager {

	/** DBコネクション */
	private static Connection con = null;
	
	/**
	 * DB接続メソッド。
	 * 
	 * @param url jdbc:subprotocol:subname 形式のデータベース URL
	 * @param user　データベースユーザ
	 * @param password データベースパスワード
	 * @param driverClass ドライバークラス
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void openConnection(String url, String user, String password, String driverClass) throws ClassNotFoundException, SQLException {
		Class.forName(driverClass);
		con = DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * DB切断メソッド。
	 */
	public static void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				con = null;
			}
		}
	}
	
	/**
	 * DBコネクション取得メソッド。
	 * 
	 * @return DBコネクション
	 */
	public static Connection getConnection() {
		return con;
	}
}
