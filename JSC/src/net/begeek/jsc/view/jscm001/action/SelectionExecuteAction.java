package net.begeek.jsc.view.jscm001.action;

import net.begeek.jsc.service.SQLExecuteService;
import net.begeek.jsc.view.component.MainPanel;
import net.begeek.jsc.view.component.MainPanelOBean;
import net.begeek.jsc.view.jscm001.JSCM001;

import org.eclipse.jface.action.Action;

/**
 * �I���ς݂�SQL�����s�A�N�V�����B
 * 
 * @author kiyono
 * @version 1.0
 */
public class SelectionExecuteAction extends Action {

	/** SQL���̋�؂蕶�� */
	private static final String SQL_DELIMNS = ";";
	
	/** ���C����� */
	private JSCM001 window;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param window ���C�����
	 */
	public SelectionExecuteAction(JSCM001 window) {
		this.window = window;
		setText("�I���e�L�X�g���s(&E)@Ctrl+Alt+X");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		// SQL���s�T�[�r�X�𐶐��B
		SQLExecuteService service = new SQLExecuteService();
		MainPanel panel = (MainPanel) window.getTabFolder().getSelection().getControl();
		// �I���ς݂̃e�L�X�g����łȂ��ꍇ�ASQL���s
		if (!panel.getSelectionText().isEmpty()) {
			// �^�u�t�H���_������
			panel.initTabFolder();
			MainPanelOBean oBean = (MainPanelOBean) panel.getOBean();
			// SQL������؂蕶���ŕ������āA���������������s����B
			String[] inText = panel.getSelectionText().split(SQL_DELIMNS);
			for (String sql : inText) {
				oBean.setiSQLText(sql);
				service.execute(oBean);
			}
			panel.forceFocus();
		}
	}
	
}
