package net.begeek.jsc.view.component;

import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * 結果メッセージ表示用画面部品。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultTextPanel extends ResultPanel {
	/** 結果メッセージ表示用テキスト */
	private Text result;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parent　親ウィジェット
	 * @param style ウィジェットのスタイル
	 */
	public ResultTextPanel(Composite parent, int style) {
		super(parent, style);
		
		// 結果メッセージ表示用のテキストを生成する。
		result = new Text(this, SWT.MULTI | SWT.BORDER);
		
		// 表示位置を設定する。 
		FormData resultData = new FormData();
		resultData.top = new FormAttachment(text, 5);
		resultData.bottom = new FormAttachment(100, 0);
		resultData.left = new FormAttachment(0, 0);
		resultData.right = new FormAttachment(100, 0);
		result.setLayoutData(resultData);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		// 結果メッセージを設定する。
		result.setText(((ResultTextPanelOBean)o).getoResultText());
	}
	
}
