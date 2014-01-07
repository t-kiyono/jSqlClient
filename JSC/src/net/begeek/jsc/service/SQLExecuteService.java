package net.begeek.jsc.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.begeek.jsc.util.ConnectionManager;
import net.begeek.jsc.view.component.MainPanelOBean;
import net.begeek.jsc.view.component.ResultTablePanelOBean;
import net.begeek.jsc.view.component.ResultTextPanelOBean;


/**
 * SQL実行サービスクラス。<br>
 * SQLの実行結果がResultSetの場合は、別スレッドでResultSetから値を取得し、画面に描画する。<br>
 * その際、{@link SQLExecuteService#enabled}をfalseにすることで、取得処理を途中で終了できる。<br>
 * SQLの実行結果が更新カウントの場合は、更新件数を画面に描画する。
 * 
 * @author kiyono
 * @version 1.0
 */
public class SQLExecuteService {
 
	/**
	 * 画面処理分岐用の列挙型。
	 * 
	 * @author kiyono
	 * @version 1.0
	 */
	public enum Action {
		INIT,
		ADD,
		END
	}
	
	/** 1度に画面に描画する件数 */
	public static final int TRANSFER_COUNT = 500;
	
	/**
	 * 取得処理割り込み用の変数<br>
	 * falseが設定されると、別スレッドの取得処理が終了し、結果が画面に通知される。
	 */
	public static boolean enabled = true;
	
	/**
	 * SQL実行処理。
	 * 
	 * @param bean メイン画面Bean
	 */
	public void execute(MainPanelOBean bean) {
		// 処理の始めに割り込み用変数を有効化しておく。
		enabled = true;
		Connection con = ConnectionManager.getConnection();
		try {
			// コネクションがオープンしている場合のみ、処理を行う。
			if (con != null && !con.isClosed()) {
				final Statement stmt = con.createStatement();
				
				// SQL実行
				if (stmt.execute(bean.getiSQLText())) {		// 結果がResultSetの場合
					// テーブル表示用Beanを作成する。
					final ResultTablePanelOBean resultTablePanelOBean = new ResultTablePanelOBean();
					resultTablePanelOBean.setoSQLText(bean.getiSQLText());
					bean.setoOBean(resultTablePanelOBean);
					// 結果タブを生成するため、画面へ通知する。
					bean.update();
					
					// ResultSetの処理を行う。
					final ResultSet rs = stmt.getResultSet();
					final ResultSetMetaData rsmd = rs.getMetaData();
					// カラム名の設定処理。
					final int columnCount = rsmd.getColumnCount();
					String[] columns = new String[columnCount];
					for (int i = 0; i < columns.length; i++) {
						columns[i] = rsmd.getColumnName(i+1);
					}
					// カラム名のみの状態で、一旦画面描画を行う。
					resultTablePanelOBean.setColumns(columns);
					resultTablePanelOBean.update(Action.INIT);
					
					// データ取得スレッド
					Thread thread = new Thread(){

						@Override
						public void run() {
							try {
								List<String[]> data = new ArrayList<String[]>();
								// データ取得件数
								int cnt = 0;
								while (rs.next()) {
									// 1レコード分のバッファ
									String[] buf = new String[columnCount];
									for (int i = 0; i < columnCount; i++) {
										buf[i] = rs.getString(i+1);
									}
									data.add(buf);
									cnt++;
									
									// データ取得件数が1度に画面に描画する件数に達したら画面描画を行う。
									if (cnt % TRANSFER_COUNT == 0) {
										resultTablePanelOBean.setData(data);
										resultTablePanelOBean.update(Action.ADD);
										data = new ArrayList<String[]>();
										cnt = 0;
									}
									
									// 割り込み用変数がfalseの場合は、処理を終了する。
									if (!enabled) break;
								}
								// 1度に画面に描画する件数で、割り切れなかった余りを画面描画する。
								resultTablePanelOBean.setData(data);
								resultTablePanelOBean.update(Action.END);
							} catch (SQLException e) {
								new RuntimeException(e);
							} finally {
								// リソース解放
								if (rs != null) {
									try {
										rs.close();
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
								if (stmt != null) {
									try {
										stmt.close();
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
							}
						}
						
					};
					thread.start();
				} else {		// 結果が更新カウントの場合
					// 画面表示メッセージ生成する。
					int count = stmt.getUpdateCount();
					String msg = count + "件処理しました。";
					
					// 画面表示
					createResultTextPanel(bean, bean.getiSQLText(), msg);
					// リソース解放
					if (stmt != null) {
						try {
							stmt.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (SQLException e) {
			// 例外発生時は、例外内容を結果欄に表示する。
			String msg = e.getMessage();
			createResultTextPanel(bean, bean.getiSQLText(), msg);
		}
	}
	
	/**
	 * SQL実行結果表示メソッド。<br>
	 * SQLの実行結果がResultSetではない場合に、結果を画面に表示する。
	 * 
	 * @param parentBean 実行結果Beanの親Bean
	 * @param sql 実行SQL文
	 * @param msg 実行結果
	 */
	private void createResultTextPanel(MainPanelOBean parentBean, String sql, String msg) {
		// メッセージ表示用Beanを作成する。
		ResultTextPanelOBean resultTextPanelOBean = new ResultTextPanelOBean();
		parentBean.setoOBean(resultTextPanelOBean);
		// 結果タブを生成するため、画面へ通知する。
		parentBean.update();
		
		// メッセージ表示用Beanに表示内容を設定する。
		resultTextPanelOBean.setoSQLText(sql);
		resultTextPanelOBean.setoResultText(msg);
		// メッセージ表示用Beanの内容を画面に通知する。
		resultTextPanelOBean.update();
	}
}
