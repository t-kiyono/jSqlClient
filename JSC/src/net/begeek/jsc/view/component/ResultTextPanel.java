package net.begeek.jsc.view.component;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * ���ʃ��b�Z�[�W�\���p��ʕ��i�B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultTextPanel extends ResultPanel {
	/** ���ʃ��b�Z�[�W�\���p�e�L�X�g */
	private Text result;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parent�@�e�E�B�W�F�b�g
	 * @param style �E�B�W�F�b�g�̃X�^�C��
	 */
	public ResultTextPanel(Composite parent, int style) {
		super(parent, style);
		
		// ���ʃ��b�Z�[�W�\���p�̃e�L�X�g�𐶐�����B
		result = new Text(this, SWT.MULTI | SWT.BORDER);
		
		// �\���ʒu��ݒ肷��B 
		FormData resultData = new FormData();
		resultData.top = new FormAttachment(text, 5);
		resultData.bottom = new FormAttachment(100, 0);
		resultData.left = new FormAttachment(0, 0);
		resultData.right = new FormAttachment(100, 0);
		result.setLayoutData(resultData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		// ���ʃ��b�Z�[�W��ݒ肷��B
		result.setText(((ResultTextPanelOBean)o).getoResultText());
	}
	
}
