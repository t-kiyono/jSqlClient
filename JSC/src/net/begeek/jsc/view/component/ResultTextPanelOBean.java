package net.begeek.jsc.view.component;

/**
 * ���ʃ��b�Z�[�W�\���pBean�B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultTextPanelOBean extends ResultPanelOBean {

	/** ���ʃ��b�Z�[�W */
	protected String oResultText;

	/**
	 * ���ʃ��b�Z�[�W���擾����B
	 * 
	 * @return ���ʃ��b�Z�[�W
	 */
	public String getoResultText() {
		return oResultText;
	}

	/**
	 * ���ʃ��b�Z�[�W��ݒ肷��B
	 * 
	 * @param oResultText ���ʃ��b�Z�[�W
	 */
	public void setoResultText(String oResultText) {
		this.oResultText = oResultText;
	}
}
