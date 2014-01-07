package net.begeek.jsc.view.component.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Table;

/**
 * ���ʕ\���e�[�u���S�I���A�N�V�����B
 * 
 * @author kiyono
 * @version 1.0
 */
public class SelectAllAction extends Action {

	/** ���ʕ\���e�[�u�� */
	private Table table;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param table ���ʕ\���e�[�u��
	 */
	public SelectAllAction(Table table) {
		this.table = table;
		setText("�S�I��@Ctrl+A");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// �e�[�u���S�I��
		table.selectAll();
	}
}
