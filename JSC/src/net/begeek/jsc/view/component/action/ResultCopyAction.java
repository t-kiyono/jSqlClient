package net.begeek.jsc.view.component.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * 結果表示テーブルの選択された内容をクリップボードにコピーするアクション。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultCopyAction extends Action {

	/** 結果表示テーブル */
	private Table table;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param table 結果表示テーブル
	 */
	public ResultCopyAction(Table table) {
		this.table = table;
		setText("コピー@Ctrl+C");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// クリップボード生成。
		Clipboard clipboard = new Clipboard(Display.getDefault());
		// クリップボードにコピーする文字列のバッファ。
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		
		// 列名取得。
		for (int i = 1; i < table.getColumnCount(); i++) {
			if (!isFirst) sb.append("\t");
			sb.append(table.getColumn(i).getText());
			isFirst = false;
		}
		sb.append("\n");
		
		// レコードデータ取得。
		TableItem[] items = table.getSelection();
		for (int i = 0; i < items.length; i++) {
			isFirst = true;
			for (int j = 1; j < table.getColumnCount(); j++) {
				if (!isFirst) sb.append("\t");
				sb.append(items[i].getText(j));
				isFirst = false;
			}
			sb.append("\n");
		}
		
		// 文字列が空でなければ、クリップボードにコピーする。
		if (!sb.toString().isEmpty()) {
			clipboard.setContents(new Object[]{sb.toString()}, new Transfer[]{TextTransfer.getInstance()});
		}
	}
	
}
