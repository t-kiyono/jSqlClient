package net.begeek.jsc.common;

import java.util.Observable;

/**
 * ��ʑ��ւ̒ʒm�@�\������������Bean�N���X�B<br>
 * {@link AbstractObserverComposite}�N���X�ƍ��킹�Ďg�p����B
 * 
 * @author kiyono
 * @version 1.0
 * @see AbstractObserverComposite
 */
public abstract class AbstractObservableBean extends Observable {

	/**
	 * arg��null�Ƃ���{@link AbstractObservableBean#update(Object) update(arg)}���Ăяo���B
	 */
	public void update() {
		update(null);
	}
	
	/**
	 * {@link AbstractObserverComposite#update(Observable, Object)}���Ăяo���B<br>
	 * �����Ƃ��Ė{�I�u�W�F�N�g��arg�Ɏw�肵���A�C�ӂ̃I�u�W�F�N�g���n�����B
	 * 
	 * @param arg �C�ӂ̃I�u�W�F�N�g
	 */
	public void update(Object arg) {
		setChanged();
		notifyObservers(arg);
		clearChanged();
	}
	
}
