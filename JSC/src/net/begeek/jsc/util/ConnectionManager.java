package net.begeek.jsc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB�R�l�N�V�������[�e�B���e�B�B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ConnectionManager {

	/** DB�R�l�N�V���� */
	private static Connection con = null;
	
	/**
	 * DB�ڑ����\�b�h�B
	 * 
	 * @param url jdbc:subprotocol:subname �`���̃f�[�^�x�[�X URL
	 * @param user�@�f�[�^�x�[�X���[�U
	 * @param password �f�[�^�x�[�X�p�X���[�h
	 * @param driverClass �h���C�o�[�N���X
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void openConnection(String url, String user, String password, String driverClass) throws ClassNotFoundException, SQLException {
		Class.forName(driverClass);
		con = DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * DB�ؒf���\�b�h�B
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
	 * DB�R�l�N�V�����擾���\�b�h�B
	 * 
	 * @return DB�R�l�N�V����
	 */
	public static Connection getConnection() {
		return con;
	}
}
