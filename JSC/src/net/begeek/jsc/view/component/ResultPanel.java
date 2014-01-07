package net.begeek.jsc.view.component;

import java.util.Observable;
import java.util.Observer;

import net.begeek.jsc.common.AbstractObserverComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * ���ʕ\���p��ʕ��i�̒��ۃN���X�B<br>
 * 
 * @author kiyono
 * @version 1.0
 */
public abstract class ResultPanel extends AbstractObserverComposite implements Observer {

	/** ���s����SQL���\���� */
	protected Text text;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parent �e�E�B�W�F�b�g
	 * @param style �E�B�W�F�b�g�̃X�^�C��
	 */
	public ResultPanel(Composite parent, int style) {
		super(parent, style);
		// ���C�A�E�g�ݒ�
		setLayout(new FormLayout());
		
		// ���s����SQL�\�����𐶐�����B
		text = new Text(this, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		FormData textData = new FormData();
		textData.top = new FormAttachment(0, 0);
		textData.left = new FormAttachment(0, 0);
		textData.right = new FormAttachment(100, 0);
		text.setLayoutData(textData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		// ���s����SQL����\�����ɐݒ肷��B
		text.setText(((ResultPanelOBean)o).getoSQLText());
	}
	

}
