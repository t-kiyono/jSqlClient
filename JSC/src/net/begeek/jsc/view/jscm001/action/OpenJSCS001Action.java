package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.view.jscs001.JSCS001;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

/**
 * DB接続サブ画面を開くアクション。
 * 
 * @author kiyono
 * @version 1.0
 */
public class OpenJSCS001Action extends Action {

	/** 親シェル */
	private Shell parentShell;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parentShell 親シェル
	 */
	public OpenJSCS001Action(Shell parentShell) {
		this.parentShell = parentShell;
		setText("接続(&C)@Alt+C");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		JSCS001 window = new JSCS001(parentShell);
		window.open();
	}
}
