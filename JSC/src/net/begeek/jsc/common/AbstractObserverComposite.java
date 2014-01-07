package net.begeek.jsc.common;

import java.util.Observer;

import org.eclipse.swt.widgets.Composite;

/**
 * {@link AbstractObservableBean}����̒ʒm���󂯂āA�`����e���X�V�\�Ȓ���Compsite�N���X�B
 * 
 * @author kiyono
 * @version 1.0
 * @see AbstractObservableBean
 */
public abstract class AbstractObserverComposite extends Composite implements Observer {

	/** ��ʂɑΉ�����ObservableBean�B */
	protected AbstractObservableBean oBean;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parent�@�e�E�B�W�F�b�g
	 * @param style�@�E�B�W�F�b�g�̃X�^�C��
	 */
	public AbstractObserverComposite(Composite parent, int style) {
		super(parent, style);
	}
	
	/**
	 * ��ʂɑΉ�����ObservableBean��ݒ肷��B
	 * 
	 * @param oBean ��ʂɑΉ�����ObservableBean
	 */
	public void setOBean(AbstractObservableBean oBean) {
		this.oBean = oBean;
	}
	
	/**
	 * ��ʂɑΉ�����ObservableBean���擾����B
	 * 
	 * @return ��ʂɑΉ�����ObservableBean
	 */
	public AbstractObservableBean getOBean() {
		return oBean;
	}

}
