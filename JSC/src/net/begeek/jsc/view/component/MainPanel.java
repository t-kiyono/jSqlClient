package net.begeek.jsc.view.component;

import java.util.Observable;

import net.begeek.jsc.common.AbstractObserverComposite;
import net.begeek.jsc.view.jscs002.JSCS002;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * メイン画面部品。<br>
 * SQL文の入力欄と、結果表示用のタブフォルダを持つ。
 * 
 * @author kiyono
 * @version 1.0
 */
public class MainPanel extends AbstractObserverComposite {
	
	/** SQL文入力欄 */
	private StyledText text;
	
	/** 結果表示用のタブフォルダ */
	private CTabFolder tabFolder;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parent 親ウィジェット
	 * @param style ウィジェットのスタイル
	 */
	public MainPanel(Composite parent, int style) {
		super(parent, style);
		
		// レイアウト設定。
		setLayout(new FillLayout());
		// 表示域変更可能フォームの生成。
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		// SQL入力欄の生成。
		text = new StyledText(sashForm, SWT.NONE);
		// 結果表示用タブフォルダの生成。
		tabFolder = new CTabFolder(sashForm, SWT.NONE);
		// デフォルトの表示域の比率を設定。
		sashForm.setWeights(new int[] { 2, 3 });
	}
	
	/**
	 * SQL文を取得する。
	 * 
	 * @return SQL文
	 */
	public String getSQLText() {
		return text.getText();
	}
	
	/**
	 * 選択済みのSQL文を取得する。
	 * 
	 * @return 選択済みのSQL文
	 */
	public String getSelectionText() {
		return text.getSelectionText();
	}
	
	/**
	 * タブフォルダ初期化処理。
	 */
	public void initTabFolder() {
		CTabItem[] items = tabFolder.getItems();
		for (CTabItem item : items) {
			item.dispose();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		// 結果表示欄のBeanを取得する。
		ResultPanelOBean bean = ((MainPanelOBean)o).getoOBean();
		
		// 結果タブを生成する。
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("結果" + (tabFolder.getItemCount()));
		if (bean instanceof ResultTextPanelOBean) {
			// 結果がメッセージ表示。
			ResultTextPanel panel = new ResultTextPanel(tabFolder, SWT.NONE);
			bean.addObserver(panel);
			panel.setOBean(bean);
			tabItem.setControl(panel);
			tabFolder.setSelection(tabFolder.getItemCount());
			tabFolder.forceFocus();
		} else if (bean instanceof ResultTablePanelOBean) {
			// 結果がテーブル表示。
			ResultTablePanel panel = new ResultTablePanel(tabFolder, SWT.NONE);
			bean.addObserver(panel);
			panel.setOBean(bean);
			tabItem.setControl(panel);
			tabFolder.setSelection(tabFolder.getItemCount());
			tabFolder.forceFocus();
			
			// 取得処理中断用のサブ画面を表示する。
			JSCS002 w = new JSCS002(getShell());
			w.open();
			bean.addObserver(w.getProgressPanel());
			w.getProgressPanel().setOBean(bean);
		}
		
	}

}
