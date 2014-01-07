package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.service.SQLExecuteService;
import net.begeek.jsc.view.component.MainPanel;
import net.begeek.jsc.view.component.MainPanelOBean;
import net.begeek.jsc.view.jscm001.JSCM001;

import org.eclipse.jface.action.Action;

/**
 * 選択済みのSQL文実行アクション。
 * 
 * @author kiyono
 * @version 1.0
 */
public class SelectionExecuteAction extends Action {

	/** SQL文の区切り文字 */
	private static final String SQL_DELIMNS = ";";
	
	/** メイン画面 */
	private JSCM001 window;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param window メイン画面
	 */
	public SelectionExecuteAction(JSCM001 window) {
		this.window = window;
		setText("選択テキスト実行(&E)@Ctrl+Alt+X");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// SQL実行サービスを生成。
		SQLExecuteService service = new SQLExecuteService();
		MainPanel panel = (MainPanel) window.getTabFolder().getSelection().getControl();
		// 選択済みのテキストが空でない場合、SQL実行
		if (!panel.getSelectionText().isEmpty()) {
			// タブフォルダ初期化
			panel.initTabFolder();
			MainPanelOBean oBean = (MainPanelOBean) panel.getOBean();
			// SQL文を区切り文字で分割して、分割した数分実行する。
			String[] inText = panel.getSelectionText().split(SQL_DELIMNS);
			for (String sql : inText) {
				oBean.setiSQLText(sql);
				service.execute(oBean);
			}
			panel.forceFocus();
		}
	}
	
}
