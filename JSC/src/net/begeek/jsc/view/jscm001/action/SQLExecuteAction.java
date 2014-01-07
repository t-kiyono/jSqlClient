package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.service.SQLExecuteService;
import net.begeek.jsc.view.component.MainPanel;
import net.begeek.jsc.view.component.MainPanelOBean;
import net.begeek.jsc.view.jscm001.JSCM001;

import org.eclipse.jface.action.Action;

/**
 * SQL文実行アクション。
 * 
 * @author kiyono
 * @version 1.0
 */
public class SQLExecuteAction extends Action {

	/** SQL文の区切り文字 */
	private static final String SQL_DELIMNS = ";";
	
	/** メイン画面 */
	private JSCM001 window;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param window メイン画面
	 */
	public SQLExecuteAction(JSCM001 window) {
		this.window = window;
		setText("すべて実行(&A)@Alt+X");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// SQL実行サービスを生成。
		SQLExecuteService service = new SQLExecuteService();
		MainPanel panel = (MainPanel) window.getTabFolder().getSelection().getControl();
		// テキストが空でない場合、SQL実行
		if (!panel.getSQLText().isEmpty()) {
			// タブフォルダ初期化
			panel.initTabFolder();
			MainPanelOBean oBean = (MainPanelOBean) panel.getOBean();
			// SQL文を区切り文字で分割して、分割した数分実行する。
			String[] inText = panel.getSQLText().split(SQL_DELIMNS);
			for (String sql : inText) {
				oBean.setiSQLText(sql);
				service.execute(oBean);
			}
			panel.forceFocus();
		}
	}
	
}
