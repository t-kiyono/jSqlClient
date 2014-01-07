package net.begeek.jsc.view.component.action;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * ���ʕ\���e�[�u���̑I�����ꂽ���e��񖼂Ȃ��ŃN���b�v�{�[�h�ɃR�s�[����A�N�V�����B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultDataCopyAction extends Action {

	/** ���ʕ\���e�[�u�� */
	private Table table;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param table ���ʕ\���e�[�u��
	 */
	public ResultDataCopyAction(Table table) {
		this.table = table;
		setText("�񖼂Ȃ��R�s�[@Ctrl+Shift+C");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// �N���b�v�{�[�h�����B
		Clipboard clipboard = new Clipboard(Display.getDefault());
		// �N���b�v�{�[�h�ɃR�s�[���镶����̃o�b�t�@�B
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;
		
		// ���R�[�h�f�[�^�擾�B
		TableItem[] items = table.getSelection();
		for (int i = 0; i < items.length; i++) {
			isFirst = true;
			for (int j = 1; j < table.getColumnCount(); j++) {
				if (!isFirst) sb.append("\t");
				sb.append(items[i].getText(j));
				isFirst = false;
			}
			sb.append("\n");
		}
		
		// �����񂪋�łȂ���΁A�N���b�v�{�[�h�ɃR�s�[����B
		if (!sb.toString().isEmpty()) {
			clipboard.setContents(new Object[]{sb.toString()}, new Transfer[]{TextTransfer.getInstance()});
		}
	}
}
