package net.begeek.jsc.view.component;

import net.begeek.jsc.common.AbstractObservableBean;

/**
 * メイン画面用のBean。
 * 
 * @author kiyono
 * @version 1.0
 */
public class MainPanelOBean extends AbstractObservableBean {

	/** SQL文 */
	protected String iSQLText = "";
	/** 結果表示Bean */
	protected ResultPanelOBean oOBean;

	/**
	 * SQL文を取得する。
	 * 
	 * @return SQL文
	 */
	public String getiSQLText() {
		return iSQLText;
	}

	/**
	 * SQL文を設定する。
	 * 
	 * @param iSQLText SQL文
	 */
	public void setiSQLText(String iSQLText) {
		this.iSQLText = iSQLText;
	}

	/**
	 * 結果表示Beanを取得する。
	 * 
	 * @return 結果表示Bean
	 */
	public ResultPanelOBean getoOBean() {
		return oOBean;
	}

	/**
	 * 結果表示Beanを設定する。
	 * 
	 * @param oOBean 結果表示Bean
	 */
	public void setoOBean(ResultPanelOBean oOBean) {
		this.oOBean = oOBean;
	}
}
