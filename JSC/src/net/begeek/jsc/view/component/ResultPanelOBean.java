package net.begeek.jsc.view.component;

import net.begeek.jsc.common.AbstractObservableBean;

/**
 * 結果表示用Beanの抽象クラス。
 * 
 * @author kiyono
 * @version 1.0
 */
public abstract class ResultPanelOBean extends AbstractObservableBean {

	/** 実行したSQL文 */
	protected String oSQLText;

	/**
	 * 実行したSQL文を取得する、。
	 * 
	 * @return 実行したSQL文
	 */
	public String getoSQLText() {
		return oSQLText;
	}

	/**
	 * 実行したSQL文を設定する。
	 * 
	 * @param oSQLText 実行したSQL文
	 */
	public void setoSQLText(String oSQLText) {
		this.oSQLText = oSQLText;
	}
}
