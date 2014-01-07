package net.begeek.jsc.view.component;

import net.begeek.jsc.common.AbstractObservableBean;

/**
 * ���ʕ\���pBean�̒��ۃN���X�B
 * 
 * @author kiyono
 * @version 1.0
 */
public abstract class ResultPanelOBean extends AbstractObservableBean {

	/** ���s����SQL�� */
	protected String oSQLText;

	/**
	 * ���s����SQL�����擾����A�B
	 * 
	 * @return ���s����SQL��
	 */
	public String getoSQLText() {
		return oSQLText;
	}

	/**
	 * ���s����SQL����ݒ肷��B
	 * 
	 * @param oSQLText ���s����SQL��
	 */
	public void setoSQLText(String oSQLText) {
		this.oSQLText = oSQLText;
	}
}
