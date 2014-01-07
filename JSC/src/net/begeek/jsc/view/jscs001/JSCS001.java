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
 * DB�ڑ��p�T�u��ʁB
 * 
 * @author kiyono
 * @version 1.0
 */
public class JSCS001 extends ApplicationWindow {

	/** ��ʃ^�C�g�� */
	private static final String WINDOW_TITLE = "DB�ڑ�";
	/** ��ʃT�C�Y���� */
	private static final int WINDOW_WIDTH = 800;
	/** ��ʃT�C�Y�c�� */
	private static final int WINDOW_HEIGHT = 300;
	
	// ��ʕ\�������B
	private static final String PROFILE = "�v���t�@�C����";
	private static final String USER = "���[�U��";
	private static final String PASSWORD = "�p�X���[�h";
	private static final String URL = "URL";
	private static final String DRIVER_C = "�h���C�o�[�E�N���X";
	private static final String DRIVER_F = "�h���C�o�[�E�t�@�C��";
	
	// �ݒ�t�@�C���ړ����B
	private static final String PREF_DB = "db";
	private static final String PREF_USER = "user";
	private static final String PREF_URL = "url";
	private static final String PREF_DRIVER_C = "driverC";
	private static final String PREF_DRIVER_F = "driverF";

	// ��ʃR���|�[�l���g�B
	private Combo combo;
	private Text txtUser;
	private Text txtURL;
	private Text txtDrvC;
	private Text txtDrvF;
	private Text txtPass;
	
	// �v���t�@�C�����Ɛݒ�t�@�C���̃C���f�b�N�X�ԍ��̑Ή��}�b�v�B
	private Map<String, Integer> prefMap;
	
	// �ݒ�t�@�C������N���X�B
	private PreferenceStore ps;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parentShell �e�V�F��
	 */
	public JSCS001(Shell parentShell) {
		super(parentShell);
		// ���[�_���Ŗ{��ʂ��J���B
		setShellStyle(SWT.APPLICATION_MODAL | SWT.TITLE | SWT.CLOSE);
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
		Composite container = new Composite(parent, SWT.NONE);
		
		// �ݒ�t�@�C���ǂݍ��݁B
		ps = new PreferenceStore("db.properties");
		try {
			ps.load();
			
			prefMap = new HashMap<String, Integer>();
			// �ݒ�t�@�C���̐ݒ荀�ڂ��擾�B
			String[] prefNames = ps.preferenceNames();
			for (int i = 0; i < prefNames.length; i++) {
				// �v���t�@�C�����p�̍��ڐړ����̏ꍇ
				if (prefNames[i].startsWith(PREF_DB)) {
					// �v���t�@�C�����ƃC���f�b�N�X�ԍ���R�t����B
					int cnt = Integer.parseInt(prefNames[i].substring(PREF_DB.length()));
					prefMap.put(ps.getString(prefNames[i]), cnt);
				}
			}
			
			// �\���G���A�̐ݒ�B
			container.setLayout(new GridLayout(4, true));
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 3;
			
			// �R���{�{�b�N�X�̐ݒ�B
			new Label(container, SWT.NONE).setText(PROFILE);
			combo = new Combo(container, SWT.READ_ONLY);
			combo.setLayoutData(gridData);
			for (String item : prefMap.keySet()) {
				combo.add(item);
			}
			
			// �R���{�{�b�N�X�̃A�C�e���I�����̃C�x���g�ݒ�B
			combo.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// �I�����ꂽ�v���t�@�C�����ɕR�t���ݒ�l��\������B
					int pos = prefMap.get(combo.getText());
					txtUser.setText(ps.getString(PREF_USER + pos));
					txtURL.setText(ps.getString(PREF_URL + pos));
					txtDrvC.setText(ps.getString(PREF_DRIVER_C + pos));
					txtDrvF.setText(ps.getString(PREF_DRIVER_F + pos).replaceAll(",", "\n"));
					txtPass.setText("");
				}
				
			});
			
			// ���[�U���̐ݒ�B
			new Label(container, SWT.NONE).setText(USER);
			txtUser = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
			txtUser.setLayoutData(gridData);
			// �p�X���[�h�̐ݒ�B
			new Label(container, SWT.NONE).setText(PASSWORD);
			txtPass = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
			txtPass.setLayoutData(gridData);
			// URL�̐ݒ�B
			new Label(container, SWT.NONE).setText(URL);
			txtURL = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
			txtURL.setLayoutData(gridData);
			// �h���C�o�[�E�N���X�̐ݒ�B
			new Label(container, SWT.NONE).setText(DRIVER_C);
			txtDrvC = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.READ_ONLY);
			txtDrvC.setLayoutData(gridData);
			//�@�h���C�o�[�E�t�@�C���̐ݒ�B
			new Label(container, SWT.NONE).setText(DRIVER_F);
			txtDrvF = new Text(container, SWT.MULTI | SWT.BORDER | SWT.READ_ONLY);
			GridData drvData = new GridData(GridData.FILL_BOTH);
			drvData.horizontalSpan = 3;
			txtDrvF.setLayoutData(drvData);
			
			// �{�^���\���ʒu�̐ݒ�B
			GridData btnData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
			btnData.widthHint = 100;
			btnData.horizontalSpan = 2;
			
			// �uOK�v�{�^���̐����B
			Button btnOk = new Button(container, SWT.PUSH);
			btnOk.setText("OK");
			btnOk.setLayoutData(btnData);
			
			// �uOK�v�{�^���������̃C�x���g�ݒ�B
			btnOk.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (!combo.getText().isEmpty()) {		// �v���t�@�C�����I������Ă���ꍇ
						try {
							// �h���C�o�[�E�t�@�C�����N���X�p�X�ɒǉ�����B
							String[] files = txtDrvF.getText().split("\n");
							for (String file : files) {
								ClassPath.addClassPathToClassLoader(file);
							}
							
							// �����̃R�l�N�V����������Ε��Ă����B
							ConnectionManager.closeConnection();
							// DB�ڑ������B
							ConnectionManager.openConnection(txtURL.getText(), txtUser.getText(), txtPass.getText(), txtDrvC.getText());
							
							getShell().close();
						} catch (Exception ex) {
							// �G���[���b�Z�[�W�\���B
							showErrorMessage(ex.toString());
						}
					} else {								// �v���t�@�C�������I������Ă���ꍇ
						// �G���[���b�Z�[�W�\���B
						showErrorMessage("�v���t�@�C����I�����Ă�������");
					}
				}
				
			});
			
			// �u�L�����Z���v�{�^���̐����B
			Button btnCancel = new Button(container, SWT.PUSH);
			btnCancel.setText("Cancel");
			btnCancel.setLayoutData(btnData);
			
			// �u�L�����Z���v�{�^���������̃C�x���g�ݒ�B
			btnCancel.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					// �{��ʂ����B
					getShell().close();
				}
				
			});
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return container;
	}
	
	/**
	 * �G���[���b�Z�[�W�\���B
	 * 
	 * @param str ���b�Z�[�W
	 */
	private void showErrorMessage(String str) {
		MessageBox msg = new MessageBox(getShell(), SWT.ICON_ERROR);
		msg.setText("�G���[");
		msg.setMessage(str);
		msg.open();
	}
}
