package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.view.jscm001.JSCM001;

import org.eclipse.jface.action.Action;

/**
 * �V�K�^�u�ǉ��A�N�V�����B
 * 
 * @author kiyono
 * @version 1.0
 */
public class NewTabAction extends Action {

	/** ���C����� */
	private JSCM001 window;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param window ���C�����
	 */
	public NewTabAction(JSCM001 window) {
		this.window = window;
		setText("�V�K�쐬(&N)@Ctrl+N");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// �^�u�ǉ��B
		window.newTab();
	}
}
