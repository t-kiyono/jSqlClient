package net.begeek.jsc.common;

import java.util.Observer;

import org.eclipse.swt.widgets.Composite;

/**
 * {@link AbstractObservableBean}からの通知を受けて、描画内容を更新可能な抽象Compsiteクラス。
 * 
 * @author kiyono
 * @version 1.0
 * @see AbstractObservableBean
 */
public abstract class AbstractObserverComposite extends Composite implements Observer {

	/** 画面に対応したObservableBean。 */
	protected AbstractObservableBean oBean;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parent　親ウィジェット
	 * @param style　ウィジェットのスタイル
	 */
	public AbstractObserverComposite(Composite parent, int style) {
		super(parent, style);
	}
	
	/**
	 * 画面に対応したObservableBeanを設定する。
	 * 
	 * @param oBean 画面に対応したObservableBean
	 */
	public void setOBean(AbstractObservableBean oBean) {
		this.oBean = oBean;
	}
	
	/**
	 * 画面に対応したObservableBeanを取得する。
	 * 
	 * @return 画面に対応したObservableBean
	 */
	public AbstractObservableBean getOBean() {
		return oBean;
	}

}
