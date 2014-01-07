package net.begeek.jsc.view.jscm001;

import net.begeek.jsc.common.AbstractObserverComposite;
import net.begeek.jsc.view.component.MainPanel;
import net.begeek.jsc.view.component.MainPanelOBean;
import net.begeek.jsc.view.jscm001.action.DisconnectAction;
import net.begeek.jsc.view.jscm001.action.ExitAction;
import net.begeek.jsc.view.jscm001.action.NewTabAction;
import net.begeek.jsc.view.jscm001.action.OpenJSCS001Action;
import net.begeek.jsc.view.jscm001.action.PreferenceAction;
import net.begeek.jsc.view.jscm001.action.SQLExecuteAction;
import net.begeek.jsc.view.jscm001.action.SelectionExecuteAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * メイン画面。
 * 
 * @author kiyono
 * @version 1.0
 */
public class JSCM001 extends ApplicationWindow {

	/** 画面タイトル */
	private static final String WINDOW_TITLE = "Java SQL Client";
	/** 画面サイズ横幅 */
	private static final int WINDOW_WIDTH = 1024;
	/** 画面サイズ縦幅 */
	private static final int WINDOW_HEIGHT = 768;
	
	/** アイコンパス */
	private static final String ICON_PATH = "com/gmail/tkiyono/jsc/icon/kexi.png";
	
	/** メイン画面部品の親となるタブフォルダ */
	private CTabFolder tabFolder;
	
	/**
	 * コンストラクタ。
	 * 
	 * @param parentShell 親シェル
	 */
	public JSCM001(Shell parentShell) {
		super(parentShell);

		// メニューバーの生成。
		addMenuBar();
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
		
		// アイコンの設定
		ImageData imgData = new ImageData(JSCM001.class.getClassLoader().getResourceAsStream(ICON_PATH));
		Image img  = new Image(getShell().getDisplay(), imgData);
		getShell().setImage(img);
		
		// 画面表示エリアを生成する。
		Composite container = new Composite(parent, SWT.NONE);
		// レイアウト設定
		container.setLayout(new FillLayout());
		// メイン画面部品用のタブフォルダを設定する。
		tabFolder = new CTabFolder(container, SWT.CLOSE);
		tabFolder.setSimple(true);
		Display display = Display.getCurrent();
		// タブの高さを設定する。
		tabFolder.setTabHeight(24);
		// 選択タブの背景色にグラデーションを設定する。
		tabFolder.setSelectionBackground(
			new Color[]{
				display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND),
				display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)},
			new int[] {90},
			true
		);
		
		// 新規タブを生成する。
		newTab();
		
		return container;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MenuManager createMenuManager() {
		// メニューバー
		MenuManager menubar = new MenuManager();
		
		// ファイルアイテム
		MenuManager fileMenu = new MenuManager("ファイル(&F)");
		fileMenu.add(new NewTabAction(this));
		fileMenu.add(new ExitAction(this));
		menubar.add(fileMenu);
		
		// DBアイテム
		MenuManager dbMenu = new MenuManager("DB(&D)");
		dbMenu.add(new OpenJSCS001Action(getShell()));
		dbMenu.add(new DisconnectAction());
		menubar.add(dbMenu);
		
		// 実行アイテム
		MenuManager execMenu = new MenuManager("実行(&E)");
		execMenu.add(new SQLExecuteAction(this));
		execMenu.add(new SelectionExecuteAction(this));
		menubar.add(execMenu);
		
		// 設定アイテム
		menubar.add(new PreferenceAction(getShell()));
		
		return menubar;
	}
	
	/**
	 * メイン画面部品用のタブフォルダを取得する。
	 * 
	 * @return メイン画面部品用のタブフォルダ
	 */
	public CTabFolder getTabFolder() {
		return tabFolder;
	}
	
	/**
	 * 新規タブ追加。
	 */
	public void newTab() {
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("タブ" + tabFolder.getItemCount());
		AbstractObserverComposite MainPanel = new MainPanel(tabFolder, SWT.NONE);
		MainPanelOBean oBean = new MainPanelOBean();
		oBean.addObserver(MainPanel);
		MainPanel.setOBean(oBean);
		tabItem.setControl(MainPanel);
		tabFolder.setSelection(tabItem);
	}

}
