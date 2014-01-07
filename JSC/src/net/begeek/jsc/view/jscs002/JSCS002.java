package net.begeek.jsc.view.jscs002;

import net.begeek.jsc.view.component.ProgressPanel;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * SQL取得進捗表示画面。
 * 
 * @author kiyono
 * @version 1.0
 */
public class JSCS002 extends ApplicationWindow {

	/** 画面サイズ横幅 */
	private static final int WINDOW_WIDTH = 200;
	/** 画面サイズ縦幅 */
	private static final int WINDOW_HEIGHT = 80;
	
	/** SQL取得進捗表示部品 */
	private ProgressPanel progressPanel;

	/**
	 * コンストラクタ。
	 * 
	 * @param parentShell 親シェル
	 */
	public JSCS002(Shell parentShell) {
		super(parentShell);
		// 本画面をモーダルで開く。
		setShellStyle(SWT.APPLICATION_MODAL);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		getShell().setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		// SQL取得進捗表示部品の生成。
		progressPanel = new ProgressPanel(parent, SWT.NONE);
		
		return progressPanel;
	}
	
	/**
	 * SQL取得進捗表示部品を取得する。
	 * 
	 * @return SQL取得進捗表示部品
	 */
	public ProgressPanel getProgressPanel() {
		return progressPanel;
	}
}