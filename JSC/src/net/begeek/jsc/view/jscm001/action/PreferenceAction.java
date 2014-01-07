package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.conf.DBPreferencePage;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.widgets.Shell;

/**
 * DB�ڑ��p�����[�^�ݒ�y�[�W���J���A�N�V�����B
 * 
 * @author kiyono
 * @version 1.0
 */
public class PreferenceAction extends Action {

	/** �e�V�F�� */
	private Shell parentShell;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parentShell�@�e�V�F��
	 */
	public PreferenceAction(Shell parentShell) {
		this.parentShell = parentShell;
		setText("�ݒ�(&P)");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		PreferenceManager pm = new PreferenceManager();
		
		PreferenceNode node = new PreferenceNode("ROOT");
		// DB�ڑ��p�����[�^�ݒ�y�[�W��ݒ�B
		node.setPage(new DBPreferencePage());
		pm.addToRoot(node);
		
		PreferenceDialog dialog = new PreferenceDialog(parentShell, pm);
		dialog.open();
	}

}
