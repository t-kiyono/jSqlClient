package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.view.jscm001.JSCM001;

import org.eclipse.jface.action.Action;

/**
 * 新規タブ追加アクション。
 * 
 * @author kiyono
 * @version 1.0
 */
public class NewTabAction extends Action {

	/** メイン画面 */
	private JSCM001 window;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param window メイン画面
	 */
	public NewTabAction(JSCM001 window) {
		this.window = window;
		setText("新規作成(&N)@Ctrl+N");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// タブ追加。
		window.newTab();
	}
}
