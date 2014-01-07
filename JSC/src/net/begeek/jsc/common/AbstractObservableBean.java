package net.begeek.jsc.common;

import java.util.Observable;

/**
 * 画面側への通知機能を持った抽象Beanクラス。<br>
 * {@link AbstractObserverComposite}クラスと合わせて使用する。
 * 
 * @author kiyono
 * @version 1.0
 * @see AbstractObserverComposite
 */
public abstract class AbstractObservableBean extends Observable {

	/**
	 * argをnullとして{@link AbstractObservableBean#update(Object) update(arg)}を呼び出す。
	 */
	public void update() {
		update(null);
	}
	
	/**
	 * {@link AbstractObserverComposite#update(Observable, Object)}を呼び出す。<br>
	 * 引数として本オブジェクトとargに指定した、任意のオブジェクトが渡される。
	 * 
	 * @param arg 任意のオブジェクト
	 */
	public void update(Object arg) {
		setChanged();
		notifyObservers(arg);
		clearChanged();
	}
	
}
