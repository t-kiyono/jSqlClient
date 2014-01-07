package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.util.ConnectionManager;

import org.eclipse.jface.action.Action;

/**
 * DB切断アクション。
 * 
 * @author kiyono
 * @version 1.0
 */
public class DisconnectAction extends Action {

	/**
	 * コンストラクタ。
	 */
	public DisconnectAction() {
		setText("切断(&D)@Alt+D");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// コネクションクローズ。
		ConnectionManager.closeConnection();
	}
}
