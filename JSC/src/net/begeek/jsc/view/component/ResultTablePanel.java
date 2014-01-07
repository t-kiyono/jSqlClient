package net.begeek.jsc.view.component;

import java.util.Observable;

import net.begeek.jsc.service.SQLExecuteService.Action;
import net.begeek.jsc.view.component.action.ResultCopyAction;
import net.begeek.jsc.view.component.action.ResultDataCopyAction;
import net.begeek.jsc.view.component.action.SelectAllAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * 結果テーブル表示用画面部品。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultTablePanel extends ResultPanel {
	/** 結果表示用テーブル */
	private Table table;
	/** レコードインデックス番号 */
	private int idx = 0;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parent 親ウィジェット
	 * @param style ウィジェットのスタイル
	 */
	public ResultTablePanel(Composite parent, int style) {
		super(parent, style);
		
		// 結果表示用のテーブルを生成する。
		table = new Table(this, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		// 表示位置を設定する。
		FormData resultData = new FormData();
		resultData.top = new FormAttachment(text, 5);
		resultData.bottom = new FormAttachment(100, 0);
		resultData.left = new FormAttachment(0, 0);
		resultData.right = new FormAttachment(100, 0);
		table.setLayoutData(resultData);
		
		// コンテキストメニューを設定する。
		MenuManager menuManager = new MenuManager();
		menuManager.add(new ResultCopyAction(table));
		menuManager.add(new ResultDataCopyAction(table));
		menuManager.add(new SelectAllAction(table));
		table.setMenu(menuManager.createContextMenu(table));
		
		// キーイベントを設定する。
		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == 'c') {							// Ctrl+C
					// コピーアクション実行
					new ResultCopyAction(table).run();
				} else if (e.stateMask == (SWT.CTRL | SWT.SHIFT) && e.keyCode == 'c') {		// Ctrl+Shift+C
					// 列名なしコピーアクション実行
					new ResultDataCopyAction(table).run();
				} else if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {					// Ctrl + A
					// 全選択アクション実行
					new SelectAllAction(table).run();
				}
			}
			
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		ResultTablePanelOBean bean = (ResultTablePanelOBean) o;
		if (arg instanceof Action) {
			switch ((Action)arg) {
			// 初期処理
			case INIT:
				super.update(o, arg);
				// テーブルカラムの設定
				TableColumn colIdx = new TableColumn(table, SWT.LEAD);
				// 1列目は行番号表示欄として、列名は空白にする。
				colIdx.setText("");
				colIdx.setWidth(30);
				// この列名をクリックするとテーブルデータを全選択する。
				colIdx.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						table.selectAll();
					}
					
				});
				
				// 2列目以降から実際の列名を設定する。
				for (int i = 0; i < bean.getColumns().length; i++) {
					TableColumn col = new TableColumn(table, SWT.LEAD);
					col.setText(bean.getColumns()[i]);
					col.setWidth(100);
				}
				break;
			// データ取得内容を画面に描画するタイミング
			case ADD:
				// 通知されたデータを画面描画する。
				Display.getDefault().syncExec(new CreateTableData(bean));
				break;
			// データ取得が終了したタイミング
			case END:
				// 画面描画していない余りを描画する。
				Display.getDefault().syncExec(new CreateTableData(bean));
				break;
			}
		}
	}
	
	/**
	 * 結果表示テーブルにデータを描画するクラス。
	 * 
	 * @author kiyono
	 * @version 1.0
	 */
	private class CreateTableData implements Runnable {
		/** 結果テーブル表示用Bean */
		private ResultTablePanelOBean bean;
		
		/**
		 * コンストラクタ。
		 * 
		 * @param bean 結果テーブル表示用Bean
		 */
		public CreateTableData(ResultTablePanelOBean bean) {
			this.bean = bean;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			// 結果表示用のテーブルが破棄されている場合は処理を終了。
			if (table.isDisposed()) return;
			// レコード描画処理
			for (String[] data : bean.getData()) {
				TableItem item= new TableItem(table, SWT.NONE);
				String[] rowData = new String[data.length + 1];
				// 1列目は行番号を設定する。
				rowData[0] = String.valueOf(++idx);
				System.arraycopy(data, 0, rowData, 1, data.length);
				item.setText(rowData);
				// 1列目の文字色をグレーに設定する。
				item.setForeground(0, Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
			}
		}
		
	}
	
	
}
