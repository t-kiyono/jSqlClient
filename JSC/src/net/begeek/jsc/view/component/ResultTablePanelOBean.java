package net.begeek.jsc.view.component;

import java.util.List;

/**
 * ���ʃe�[�u���\���pBean�B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultTablePanelOBean extends ResultPanelOBean {

	/** �J������ */
	protected String[] columns;
	
	/** ���R�[�h�f�[�^ */
	protected List<String[]> data;

	/**
	 * �J���������擾����B
	 * 
	 * @return �J������
	 */
	public String[] getColumns() {
		return columns;
	}

	/**
	 * �J��������ݒ肷��B
	 * 
	 * @param columns �J������
	 */
	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	/**
	 * ���R�[�h�f�[�^���擾����B
	 * 
	 * @return ���R�[�h�f�[�^
	 */
	public List<String[]> getData() {
		return data;
	}

	/**
	 * ���R�[�h�f�[�^��ݒ肷��B
	 * 
	 * @param data ���R�[�h�f�[�^
	 */
	public void setData(List<String[]> data) {
		this.data = data;
	}
	
}
