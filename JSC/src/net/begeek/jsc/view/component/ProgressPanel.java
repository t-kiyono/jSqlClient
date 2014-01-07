package net.begeek.jsc.view.component;

import java.text.MessageFormat;
import java.util.Observable;

import net.begeek.jsc.common.AbstractObserverComposite;
import net.begeek.jsc.service.SQLExecuteService;
import net.begeek.jsc.service.SQLExecuteService.Action;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

/**
 * SQL取得進捗表示部品。<br>
 * キャンセルボタンを押すことで、取得処理を終了できる。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ProgressPanel extends AbstractObserverComposite {

	/** 画面表示メッセージテンプレート */
	private static final String MSG_TMPL = "{0}件データ転送中";
	/** 画面表示文言 */
	private Label lblProgress;
	/** 転送済み件数 */
	private int count = 0;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parent 親ウィジェット
	 * @param style ウィジェットのスタイル
	 */
	public ProgressPanel(Composite parent, int style) {
		super(parent, style);
		// レイアウト設定。
		setLayout(new GridLayout(1, true));
		// 画面表示文言を生成する。
		lblProgress = new Label(this, SWT.NONE);
		lblProgress.setText(MessageFormat.format(MSG_TMPL, 0));
		lblProgress.setLayoutData(new GridData(GridData.BEGINNING | GridData.FILL_HORIZONTAL));
		// キャンセルボタンを生成する。
		Button btnCancel = new Button(this, SWT.PUSH);
		btnCancel.setText("キャンセル");
		btnCancel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.FILL_HORIZONTAL));
		btnCancel.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//　処理割り込み用変数に無効化を設定する。
				SQLExecuteService.enabled = false;
			}
			
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("incomplete-switch")
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Action) {
			switch ((Action)arg) {
			// データ取得内容を画面に描画するタイミング
			case ADD:
				Display.getDefault().syncExec(new Runnable() {
					
					@Override
					public void run() {
						// 転送済み件数に1度に転送されてくる件数を足す。
						count = count + SQLExecuteService.TRANSFER_COUNT;
						// 画面表示文言を更新する。
						lblProgress.setText(MessageFormat.format(MSG_TMPL, count));
					}
				});
				break;
			// データ取得が終了したタイミング
			case END:
				Display.getDefault().syncExec(new Runnable() {
					
					@Override
					public void run() {
						// 本画面を閉じる。
						getShell().close();
					}
				});
				break;
			}
		}
		
	}
}
