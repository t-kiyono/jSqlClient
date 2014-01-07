package net.begeek.jsc.view.jscm001.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;

/**
 * 終了アクション。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ExitAction extends Action {

	/** 画面 */
	private ApplicationWindow window;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param window 画面
	 */
	public ExitAction(ApplicationWindow window) {
		this.window = window;
		setText("終了(&E)@Ctrl+X");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// 画面を閉じる。
		window.close();
	}
}
