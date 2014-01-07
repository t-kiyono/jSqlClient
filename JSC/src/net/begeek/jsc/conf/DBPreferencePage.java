package net.begeek.jsc.conf;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.PreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * DB接続パラメータ設定ページ。
 * 
 * @author kiyono
 * @version 1.0
 */
public class DBPreferencePage extends PreferencePage {

	// 画面表示文言。(ヘッダ部)
	private static final String TITLE = "DB設定";
	private static final String MSG = "DB接続パラメータ設定";
	
	// 画面表示文言。(コンテンツ部)
	private static final String PROFILE = "プロファイル名";
	private static final String USER = "ユーザ名";
	private static final String URL = "URL";
	private static final String DRIVER_C = "ドライバー・クラス";
	private static final String DRIVER_F = "ドライバー・ファイル";
	
	// 設定ファイル接頭辞。
	private static final String PREF_DB = "db";
	private static final String PREF_USER = "user";
	private static final String PREF_URL = "url";
	private static final String PREF_DRIVER_C = "driverC";
	private static final String PREF_DRIVER_F = "driverF";
	
	// ドライバーファイルの設定ファイル格納時のデリミタ。
	private static final String FILE_DELIMS = ",";
	// ドライバーファイルの画面表示時のデリミタ。
	private static final String LF = "\n";
	
	// 画面コンポーネント。
	private Combo combo;
	private Text txtUser;
	private Text txtURL;
	private Text txtDrvC;
	private Text txtDrvF;
	
	// プロファイル名と設定ファイルのインデックス番号の対応マップ。
	private Map<String, Integer> prefMap;
	// 設定ファイル操作クラス。
	private PreferenceStore ps;
	// 設定されているプロファイル数保持のための変数。(プロファイル追加時に+1するために使用)
	private int prefCnt = 0;
	
	/**
	 * コンストラクタ
	 */
	public DBPreferencePage() {
		setTitle(TITLE);
		setMessage(MSG);
		
		// デフォルト・適用ボタンは表示しない。
		noDefaultAndApplyButton();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		
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
					// インデックス番号最大値を保持する。
					if (cnt > prefCnt) {
						prefCnt = cnt;
					}
				}
			}
			
			// 表示エリアの設定。
			c.setLayout(new GridLayout(3, true));
			GridData spanData = new GridData(GridData.FILL_HORIZONTAL);
			spanData.horizontalSpan = 2;
			
			// コンボボックスの設定。
			new Label(c, SWT.NONE).setText(PROFILE);
			combo = new Combo(c, SWT.DROP_DOWN);
			combo.setLayoutData(spanData);
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
					txtDrvF.setText(ps.getString(PREF_DRIVER_F + pos).replaceAll(FILE_DELIMS, LF));
				}
				
			});
			
			// ユーザ名の設定。
			new Label(c, SWT.NONE).setText(USER);
			txtUser = new Text(c, SWT.SINGLE | SWT.BORDER);
			txtUser.setLayoutData(spanData);
			// URLの設定。
			new Label(c, SWT.NONE).setText(URL);
			txtURL = new Text(c, SWT.SINGLE | SWT.BORDER);
			txtURL.setLayoutData(spanData);
			// ドライバー・クラスの設定。
			new Label(c, SWT.NONE).setText(DRIVER_C);
			txtDrvC = new Text(c, SWT.SINGLE | SWT.BORDER);
			txtDrvC.setLayoutData(spanData);
			// ドライバー・ファイルの設定。
			new Label(c, SWT.NONE).setText(DRIVER_F);
			txtDrvF = new Text(c, SWT.MULTI | SWT.BORDER);
			GridData drvData = new GridData(GridData.FILL_BOTH);
			drvData.horizontalSpan = 2;
			txtDrvF.setLayoutData(drvData);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performApply() {
		// コンボボックスに値が設定されている場合のみ処理を行う。
		if (!combo.getText().isEmpty()) {
			int pos = 0;
			// 既に定義されている名前の場合は更新。そうでない場合は追加。
			if (prefMap.containsKey(combo.getText())) {
				pos = prefMap.get(combo.getText());
			} else {
				pos = ++prefCnt;
			}
			ps.setValue(PREF_DB + pos, combo.getText());
			ps.setValue(PREF_USER + pos, txtUser.getText());
			ps.setValue(PREF_URL + pos, txtURL.getText());
			ps.setValue(PREF_DRIVER_C + pos, txtDrvC.getText());
			ps.setValue(PREF_DRIVER_F + pos, txtDrvF.getText().replaceAll("\n", ","));
			
			// 設定ファイルに書き込み。
			if (ps.needsSaving()) {
				try {
					ps.save();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performOk() {
		performApply();
		return super.performOk();
	}

}
