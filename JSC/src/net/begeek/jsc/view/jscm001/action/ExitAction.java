package net.begeek.jsc.view.jscm001.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.ApplicationWindow;

/**
 * �I���A�N�V�����B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ExitAction extends Action {

	/** ��� */
	private ApplicationWindow window;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param window ���
	 */
	public ExitAction(ApplicationWindow window) {
		this.window = window;
		setText("�I��(&E)@Ctrl+X");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// ��ʂ����B
		window.close();
	}
}
