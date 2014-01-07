package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.view.jscs001.JSCS001;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

/**
 * DB�ڑ��T�u��ʂ��J���A�N�V�����B
 * 
 * @author kiyono
 * @version 1.0
 */
public class OpenJSCS001Action extends Action {

	/** �e�V�F�� */
	private Shell parentShell;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parentShell �e�V�F��
	 */
	public OpenJSCS001Action(Shell parentShell) {
		this.parentShell = parentShell;
		setText("�ڑ�(&C)@Alt+C");
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
