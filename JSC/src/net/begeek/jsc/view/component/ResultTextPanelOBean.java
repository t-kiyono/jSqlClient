package net.begeek.jsc.view.component;

/**
 * 結果メッセージ表示用Bean。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultTextPanelOBean extends ResultPanelOBean {

	/** 結果メッセージ */
	protected String oResultText;

	/**
	 * 結果メッセージを取得する。
	 * 
	 * @return 結果メッセージ
	 */
	public String getoResultText() {
		return oResultText;
	}

	/**
	 * 結果メッセージを設定する。
	 * 
	 * @param oResultText 結果メッセージ
	 */
	public void setoResultText(String oResultText) {
		this.oResultText = oResultText;
	}
}
