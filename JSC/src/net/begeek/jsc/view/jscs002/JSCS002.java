package net.begeek.jsc.view.jscs002;

import net.begeek.jsc.view.component.ProgressPanel;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * SQL�擾�i���\����ʁB
 * 
 * @author kiyono
 * @version 1.0
 */
public class JSCS002 extends ApplicationWindow {

	/** ��ʃT�C�Y���� */
	private static final int WINDOW_WIDTH = 200;
	/** ��ʃT�C�Y�c�� */
	private static final int WINDOW_HEIGHT = 80;
	
	/** SQL�擾�i���\�����i */
	private ProgressPanel progressPanel;

	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parentShell �e�V�F��
	 */
	public JSCS002(Shell parentShell) {
		super(parentShell);
		// �{��ʂ����[�_���ŊJ���B
		setShellStyle(SWT.APPLICATION_MODAL);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		getShell().setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// SQL�擾�i���\�����i�̐����B
		progressPanel = new ProgressPanel(parent, SWT.NONE);
		
		return progressPanel;
	}
	
	/**
	 * SQL�擾�i���\�����i���擾����B
	 * 
	 * @return SQL�擾�i���\�����i
	 */
	public ProgressPanel getProgressPanel() {
		return progressPanel;
	}
}