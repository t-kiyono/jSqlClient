package net.begeek.jsc.view.component;

import java.util.Observable;

import net.begeek.jsc.common.AbstractObserverComposite;
import net.begeek.jsc.view.jscs002.JSCS002;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * ���C����ʕ��i�B<br>
 * SQL���̓��͗��ƁA���ʕ\���p�̃^�u�t�H���_�����B
 * 
 * @author kiyono
 * @version 1.0
 */
public class MainPanel extends AbstractObserverComposite {
	
	/** SQL�����͗� */
	private StyledText text;
	
	/** ���ʕ\���p�̃^�u�t�H���_ */
	private CTabFolder tabFolder;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parent �e�E�B�W�F�b�g
	 * @param style �E�B�W�F�b�g�̃X�^�C��
	 */
	public MainPanel(Composite parent, int style) {
		super(parent, style);
		
		// ���C�A�E�g�ݒ�B
		setLayout(new FillLayout());
		// �\����ύX�\�t�H�[���̐����B
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		// SQL���͗��̐����B
		text = new StyledText(sashForm, SWT.NONE);
		// ���ʕ\���p�^�u�t�H���_�̐����B
		tabFolder = new CTabFolder(sashForm, SWT.NONE);
		// �f�t�H���g�̕\����̔䗦��ݒ�B
		sashForm.setWeights(new int[] { 2, 3 });
	}
	
	/**
	 * SQL�����擾����B
	 * 
	 * @return SQL��
	 */
	public String getSQLText() {
		return text.getText();
	}
	
	/**
	 * �I���ς݂�SQL�����擾����B
	 * 
	 * @return �I���ς݂�SQL��
	 */
	public String getSelectionText() {
		return text.getSelectionText();
	}
	
	/**
	 * �^�u�t�H���_�����������B
	 */
	public void initTabFolder() {
		CTabItem[] items = tabFolder.getItems();
		for (CTabItem item : items) {
			item.dispose();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		// ���ʕ\������Bean���擾����B
		ResultPanelOBean bean = ((MainPanelOBean)o).getoOBean();
		
		// ���ʃ^�u�𐶐�����B
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("����" + (tabFolder.getItemCount()));
		if (bean instanceof ResultTextPanelOBean) {
			// ���ʂ����b�Z�[�W�\���B
			ResultTextPanel panel = new ResultTextPanel(tabFolder, SWT.NONE);
			bean.addObserver(panel);
			panel.setOBean(bean);
			tabItem.setControl(panel);
			tabFolder.setSelection(tabFolder.getItemCount());
			tabFolder.forceFocus();
		} else if (bean instanceof ResultTablePanelOBean) {
			// ���ʂ��e�[�u���\���B
			ResultTablePanel panel = new ResultTablePanel(tabFolder, SWT.NONE);
			bean.addObserver(panel);
			panel.setOBean(bean);
			tabItem.setControl(panel);
			tabFolder.setSelection(tabFolder.getItemCount());
			tabFolder.forceFocus();
			
			// �擾�������f�p�̃T�u��ʂ�\������B
			JSCS002 w = new JSCS002(getShell());
			w.open();
			bean.addObserver(w.getProgressPanel());
			w.getProgressPanel().setOBean(bean);
		}
		
	}

}
