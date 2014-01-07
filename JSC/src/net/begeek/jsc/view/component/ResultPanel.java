package net.begeek.jsc.view.component;

import java.util.Observable;
import java.util.Observer;

import net.begeek.jsc.common.AbstractObserverComposite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * 結果表示用画面部品の抽象クラス。<br>
 * 
 * @author kiyono
 * @version 1.0
 */
public abstract class ResultPanel extends AbstractObserverComposite implements Observer {

	/** 実行したSQL文表示欄 */
	protected Text text;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parent 親ウィジェット
	 * @param style ウィジェットのスタイル
	 */
	public ResultPanel(Composite parent, int style) {
		super(parent, style);
		// レイアウト設定
		setLayout(new FormLayout());
		
		// 実行したSQL表示欄を生成する。
		text = new Text(this, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
		FormData textData = new FormData();
		textData.top = new FormAttachment(0, 0);
		textData.left = new FormAttachment(0, 0);
		textData.right = new FormAttachment(100, 0);
		text.setLayoutData(textData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		// 実行したSQL文を表示欄に設定する。
		text.setText(((ResultPanelOBean)o).getoSQLText());
	}
	

}
