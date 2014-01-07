package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.util.ConnectionManager;

import org.eclipse.jface.action.Action;

/**
 * DB�ؒf�A�N�V�����B
 * 
 * @author kiyono
 * @version 1.0
 */
public class DisconnectAction extends Action {

	/**
	 * �R���X�g���N�^�B
	 */
	public DisconnectAction() {
		setText("�ؒf(&D)@Alt+D");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// �R�l�N�V�����N���[�Y�B
		ConnectionManager.closeConnection();
	}
}
