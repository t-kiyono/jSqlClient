package net.begeek.jsc.view.component;

import net.begeek.jsc.common.AbstractObservableBean;

/**
 * ���C����ʗp��Bean�B
 * 
 * @author kiyono
 * @version 1.0
 */
public class MainPanelOBean extends AbstractObservableBean {

	/** SQL�� */
	protected String iSQLText = "";
	/** ���ʕ\��Bean */
	protected ResultPanelOBean oOBean;

	/**
	 * SQL�����擾����B
	 * 
	 * @return SQL��
	 */
	public String getiSQLText() {
		return iSQLText;
	}

	/**
	 * SQL����ݒ肷��B
	 * 
	 * @param iSQLText SQL��
	 */
	public void setiSQLText(String iSQLText) {
		this.iSQLText = iSQLText;
	}

	/**
	 * ���ʕ\��Bean���擾����B
	 * 
	 * @return ���ʕ\��Bean
	 */
	public ResultPanelOBean getoOBean() {
		return oOBean;
	}

	/**
	 * ���ʕ\��Bean��ݒ肷��B
	 * 
	 * @param oOBean ���ʕ\��Bean
	 */
	public void setoOBean(ResultPanelOBean oOBean) {
		this.oOBean = oOBean;
	}
}
