package net.begeek.jsc.view.jscs001;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.begeek.jsc.util.ClassPath;
import net.begeek.jsc.util.ConnectionManager;

import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * DB接続用サブ画面。
 * 
 * @author kiyono
 * @version 1.0
 */
public class JSCS001 extends ApplicationWindow {

	/** 画面タイトル */
	private static final String WINDOW_TITLE = "DB接続";
	/** 画面サイズ横幅 */
	private static final int WINDOW_WIDTH = 800;
	/** 画面サイズ縦幅 */
	private static final int WINDOW_HEIGHT = 300;
	
	// 画面表示文言。
	private static final String PROFILE = "プロファイル名";
	private static final String USER = "ユーザ名";
	private static final String PASSWORD = "パスワード";
	private static final String URL = "URL";
	private static final String DRIVER_C = "ドライバー・クラス";
	private static final String DRIVER_F = "ドライバー・ファイル";
	
	// 設定ファイル接頭辞。
	private static final String PREF_DB = "db";
	private static final String PREF_USER = "user";
	private static final String PREF_URL = "url";
	private static final String PREF_DRIVER_C = "driverC";
	private static final String PREF_DRIVER_F = "driverF";

	// 画面コンポーネント。
	private Combo combo;
	private Text txtUser;
	private Text txtURL;
	private Text txtDrvC;
	private Text txtDrvF;
	private Text txtPass;
	
	// プロファイル名と設定ファイルのインデックス番号の対応マップ。
	private Map<String, Integer> prefMap;
	
	// 設定ファイル操作クラス。
	private PreferenceStore ps;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parentShell 親シェル
	 */
	public JSCS001(Shell parentShell) {
		super(parentShell);
		// モーダルで本画面を開く。
		setShellStyle(SWT.APPLICATION_MODAL | SWT.TITLE | SWT.CLOSE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		// 画面タイトルを設定する。
		getShell().setText(WINDOW_TITLE);
		// 画面サイズを設定する。
		getShell().setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		Composite container = new Composite(parent, SWT.NONE);
		
		// 設定ファイル読み込み。
		ps = new PreferenceStore("db.properties");
		try {
			ps.load();
			
			prefMap = new HashMap<String, Integer>();
			// 設定ファイルの設定項目を取得。
			String[] prefNames = ps.preferenceNames();
			for (int i = 0; i < prefNames.length; i++) {
				// プロファイル名用の項目接頭辞の場合
				if (prefNames[i].startsWith(PREF_DB)) {
					// プロファイル名とインデックス番号を紐付ける。
					int cnt = Integer.parseInt(prefNames[i].substring(PREF_DB.length()));
					prefMap.put(ps.getString(prefNames[i]), cnt);
				}
			}
			
			// 表示エリアの設定。
			container.setLayout(new GridLayout(4, true));
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 3;
			
			// コンボボックスの設定。
			new Label(container, SWT.NONE).setText(PROFILE);
			combo = new Combo(container, SWT.READ_ONLY);
			combo.setLayoutData(gridData);
			for (String item : prefMap.keySet()) {
				combo.add(item);
			}
			
			// コンボボックスのアイテム選択時のイベント設定。
			combo.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// 選択されたプロファイル名に紐付く設定値を表示する。
					int pos = prefMap.get(combo.getText());
					txtUser.setText(ps.getString(PREF_USER + pos));
					txtURL.setText(ps.getString(PREF_URL + pos));
					txtDrvC.setText(ps.getString(PREF_DRIVER_C + pos));
					txtDrvF.setText(ps.getString(PREF_DRIVER_F + pos).replaceAll(",", "\n"));
					txtPass.setText("");
				}
				
			});
			
			// ユーザ名の設定。
			new Label(container, SWT.NONE).setText(USER);
			txtUser = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
			txtUser.setLayoutData(gridData);
			// パスワードの設定。
			new Label(container, SWT.NONE).setText(PASSWORD);
			txtPass = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
			txtPass.setLayoutData(gridData);
			// URLの設定。
			new Label(container, SWT.NONE).setText(URL);
			txtURL = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
			txtURL.setLayoutData(gridData);
			// ドライバー・クラスの設定。
			new Label(container, SWT.NONE).setText(DRIVER_C);
			txtDrvC = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
			txtDrvC.setLayoutData(gridData);
			//　ドライバー・ファイルの設定。
			new Label(container, SWT.NONE).setText(DRIVER_F);
			txtDrvF = new Text(container, SWT.MULTI | SWT.BORDER | SWT.READ_ONLY);
			GridData drvData = new GridData(GridData.FILL_BOTH);
			drvData.horizontalSpan = 3;
			txtDrvF.setLayoutData(drvData);
			
			// ボタン表示位置の設定。
			GridData btnData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
			btnData.widthHint = 100;
			btnData.horizontalSpan = 2;
			
			// 「OK」ボタンの生成。
			Button btnOk = new Button(container, SWT.PUSH);
			btnOk.setText("OK");
			btnOk.setLayoutData(btnData);
			
			// 「OK」ボタン押下時のイベント設定。
			btnOk.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (!combo.getText().isEmpty()) {		// プロファイルが選択されている場合
						try {
							// ドライバー・ファイルをクラスパスに追加する。
							String[] files = txtDrvF.getText().split("\n");
							for (String file : files) {
								ClassPath.addClassPathToClassLoader(file);
							}
							
							// 既存のコネクションがあれば閉じておく。
							ConnectionManager.closeConnection();
							// DB接続処理。
							ConnectionManager.openConnection(txtURL.getText(), txtUser.getText(), txtPass.getText(), txtDrvC.getText());
							
							getShell().close();
						} catch (Exception ex) {
							// エラーメッセージ表示。
							showErrorMessage(ex.toString());
						}
					} else {								// プロファイルが未選択されている場合
						// エラーメッセージ表示。
						showErrorMessage("プロファイルを選択してください");
					}
				}
				
			});
			
			// 「キャンセル」ボタンの生成。
			Button btnCancel = new Button(container, SWT.PUSH);
			btnCancel.setText("Cancel");
			btnCancel.setLayoutData(btnData);
			
			// 「キャンセル」ボタン押下時のイベント設定。
			btnCancel.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// 本画面を閉じる。
					getShell().close();
				}
				
			});
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return container;
	}
	
	/**
	 * エラーメッセージ表示。
	 * 
	 * @param str メッセージ
	 */
	private void showErrorMessage(String str) {
		MessageBox msg = new MessageBox(getShell(), SWT.ICON_ERROR);
		msg.setText("エラー");
		msg.setMessage(str);
		msg.open();
	}
}
