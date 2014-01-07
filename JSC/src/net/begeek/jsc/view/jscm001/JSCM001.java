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
 * ���C����ʁB
 * 
 * @author kiyono
 * @version 1.0
 */
public class JSCM001 extends ApplicationWindow {

	/** ��ʃ^�C�g�� */
	private static final String WINDOW_TITLE = "Java SQL Client";
	/** ��ʃT�C�Y���� */
	private static final int WINDOW_WIDTH = 1024;
	/** ��ʃT�C�Y�c�� */
	private static final int WINDOW_HEIGHT = 768;
	
	/** �A�C�R���p�X */
	private static final String ICON_PATH = "com/gmail/tkiyono/jsc/icon/kexi.png";
	
	/** ���C����ʕ��i�̐e�ƂȂ�^�u�t�H���_ */
	private CTabFolder tabFolder;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parentShell �e�V�F��
	 */
	public JSCM001(Shell parentShell) {
		super(parentShell);

		// ���j���[�o�[�̐����B
		addMenuBar();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		// ��ʃ^�C�g����ݒ肷��B
		getShell().setText(WINDOW_TITLE);
		// ��ʃT�C�Y��ݒ肷��B
		getShell().setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		// �A�C�R���̐ݒ�
		ImageData imgData = new ImageData(JSCM001.class.getClassLoader().getResourceAsStream(ICON_PATH));
		Image img  = new Image(getShell().getDisplay(), imgData);
		getShell().setImage(img);
		
		// ��ʕ\���G���A�𐶐�����B
		Composite container = new Composite(parent, SWT.NONE);
		// ���C�A�E�g�ݒ�
		container.setLayout(new FillLayout());
		// ���C����ʕ��i�p�̃^�u�t�H���_��ݒ肷��B
		tabFolder = new CTabFolder(container, SWT.CLOSE);
		tabFolder.setSimple(true);
		Display display = Display.getCurrent();
		// �^�u�̍�����ݒ肷��B
		tabFolder.setTabHeight(24);
		// �I���^�u�̔w�i�F�ɃO���f�[�V������ݒ肷��B
		tabFolder.setSelectionBackground(
			new Color[]{
				display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND),
				display.getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)},
			new int[] {90},
			true
		);
		
		// �V�K�^�u�𐶐�����B
		newTab();
		
		return container;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MenuManager createMenuManager() {
		// ���j���[�o�[
		MenuManager menubar = new MenuManager();
		
		// �t�@�C���A�C�e��
		MenuManager fileMenu = new MenuManager("�t�@�C��(&F)");
		fileMenu.add(new NewTabAction(this));
		fileMenu.add(new ExitAction(this));
		menubar.add(fileMenu);
		
		// DB�A�C�e��
		MenuManager dbMenu = new MenuManager("DB(&D)");
		dbMenu.add(new OpenJSCS001Action(getShell()));
		dbMenu.add(new DisconnectAction());
		menubar.add(dbMenu);
		
		// ���s�A�C�e��
		MenuManager execMenu = new MenuManager("���s(&E)");
		execMenu.add(new SQLExecuteAction(this));
		execMenu.add(new SelectionExecuteAction(this));
		menubar.add(execMenu);
		
		// �ݒ�A�C�e��
		menubar.add(new PreferenceAction(getShell()));
		
		return menubar;
	}
	
	/**
	 * ���C����ʕ��i�p�̃^�u�t�H���_���擾����B
	 * 
	 * @return ���C����ʕ��i�p�̃^�u�t�H���_
	 */
	public CTabFolder getTabFolder() {
		return tabFolder;
	}
	
	/**
	 * �V�K�^�u�ǉ��B
	 */
	public void newTab() {
		CTabItem tabItem = new CTabItem(tabFolder, SWT.NONE);
		tabItem.setText("�^�u" + tabFolder.getItemCount());
		AbstractObserverComposite MainPanel = new MainPanel(tabFolder, SWT.NONE);
		MainPanelOBean oBean = new MainPanelOBean();
		oBean.addObserver(MainPanel);
		MainPanel.setOBean(oBean);
		tabItem.setControl(MainPanel);
		tabFolder.setSelection(tabItem);
	}

}
