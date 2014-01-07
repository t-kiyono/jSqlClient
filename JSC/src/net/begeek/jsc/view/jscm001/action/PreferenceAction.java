package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.conf.DBPreferencePage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;

/**
 * DB接続パラメータ設定ページを開くアクション。
 * 
 * @author kiyono
 * @version 1.0
 */
public class PreferenceAction extends Action {

	/** 親シェル */
	private Shell parentShell;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parentShell　親シェル
	 */
	public PreferenceAction(Shell parentShell) {
		this.parentShell = parentShell;
		setText("設定(&P)");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		PreferenceManager pm = new PreferenceManager();
		
		PreferenceNode node = new PreferenceNode("ROOT");
		// DB接続パラメータ設定ページを設定。
		node.setPage(new DBPreferencePage());
		pm.addToRoot(node);
		
		PreferenceDialog dialog = new PreferenceDialog(parentShell, pm);
		dialog.open();
	}

}
