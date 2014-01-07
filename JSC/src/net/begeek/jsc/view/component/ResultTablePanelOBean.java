package net.begeek.jsc.view.component;

import java.util.List;

/**
 * 結果テーブル表示用Bean。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultTablePanelOBean extends ResultPanelOBean {

	/** カラム名 */
	protected String[] columns;
	
	/** レコードデータ */
	protected List<String[]> data;

	/**
	 * カラム名を取得する。
	 * 
	 * @return カラム名
	 */
	public String[] getColumns() {
		return columns;
	}

	/**
	 * カラム名を設定する。
	 * 
	 * @param columns カラム名
	 */
	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	/**
	 * レコードデータを取得する。
	 * 
	 * @return レコードデータ
	 */
	public List<String[]> getData() {
		return data;
	}

	/**
	 * レコードデータを設定する。
	 * 
	 * @param data レコードデータ
	 */
	public void setData(List<String[]> data) {
		this.data = data;
	}
	
}
