package net.begeek.jsc.view.component.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Table;

/**
 * 結果表示テーブル全選択アクション。
 * 
 * @author kiyono
 * @version 1.0
 */
public class SelectAllAction extends Action {

	/** 結果表示テーブル */
	private Table table;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param table 結果表示テーブル
	 */
	public SelectAllAction(Table table) {
		this.table = table;
		setText("全選択@Ctrl+A");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// テーブル全選択
		table.selectAll();
	}
}
