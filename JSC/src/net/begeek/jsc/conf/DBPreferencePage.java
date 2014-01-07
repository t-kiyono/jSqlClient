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
 * DB�ڑ��p�����[�^�ݒ�y�[�W�B
 * 
 * @author kiyono
 * @version 1.0
 */
public class DBPreferencePage extends PreferencePage {

	// ��ʕ\�������B(�w�b�_��)
	private static final String TITLE = "DB�ݒ�";
	private static final String MSG = "DB�ڑ��p�����[�^�ݒ�";
	
	// ��ʕ\�������B(�R���e���c��)
	private static final String PROFILE = "�v���t�@�C����";
	private static final String USER = "���[�U��";
	private static final String URL = "URL";
	private static final String DRIVER_C = "�h���C�o�[�E�N���X";
	private static final String DRIVER_F = "�h���C�o�[�E�t�@�C��";
	
	// �ݒ�t�@�C���ړ����B
	private static final String PREF_DB = "db";
	private static final String PREF_USER = "user";
	private static final String PREF_URL = "url";
	private static final String PREF_DRIVER_C = "driverC";
	private static final String PREF_DRIVER_F = "driverF";
	
	// �h���C�o�[�t�@�C���̐ݒ�t�@�C���i�[���̃f���~�^�B
	private static final String FILE_DELIMS = ",";
	// �h���C�o�[�t�@�C���̉�ʕ\�����̃f���~�^�B
	private static final String LF = "\n";
	
	// ��ʃR���|�[�l���g�B
	private Combo combo;
	private Text txtUser;
	private Text txtURL;
	private Text txtDrvC;
	private Text txtDrvF;
	
	// �v���t�@�C�����Ɛݒ�t�@�C���̃C���f�b�N�X�ԍ��̑Ή��}�b�v�B
	private Map<String, Integer> prefMap;
	// �ݒ�t�@�C������N���X�B
	private PreferenceStore ps;
	// �ݒ肳��Ă���v���t�@�C�����ێ��̂��߂̕ϐ��B(�v���t�@�C���ǉ�����+1���邽�߂Ɏg�p)
	private int prefCnt = 0;
	
	/**
	 * �R���X�g���N�^
	 */
	public DBPreferencePage() {
		setTitle(TITLE);
		setMessage(MSG);
		
		// �f�t�H���g�E�K�p�{�^���͕\�����Ȃ��B
		noDefaultAndApplyButton();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite c = new Composite(parent, SWT.NONE);
		
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
					// �C���f�b�N�X�ԍ��ő�l��ێ�����B
					if (cnt > prefCnt) {
						prefCnt = cnt;
					}
				}
			}
			
			// �\���G���A�̐ݒ�B
			c.setLayout(new GridLayout(3, true));
			GridData spanData = new GridData(GridData.FILL_HORIZONTAL);
			spanData.horizontalSpan = 2;
			
			// �R���{�{�b�N�X�̐ݒ�B
			new Label(c, SWT.NONE).setText(PROFILE);
			combo = new Combo(c, SWT.DROP_DOWN);
			combo.setLayoutData(spanData);
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
					txtDrvF.setText(ps.getString(PREF_DRIVER_F + pos).replaceAll(FILE_DELIMS, LF));
				}
				
			});
			
			// ���[�U���̐ݒ�B
			new Label(c, SWT.NONE).setText(USER);
			txtUser = new Text(c, SWT.SINGLE | SWT.BORDER);
			txtUser.setLayoutData(spanData);
			// URL�̐ݒ�B
			new Label(c, SWT.NONE).setText(URL);
			txtURL = new Text(c, SWT.SINGLE | SWT.BORDER);
			txtURL.setLayoutData(spanData);
			// �h���C�o�[�E�N���X�̐ݒ�B
			new Label(c, SWT.NONE).setText(DRIVER_C);
			txtDrvC = new Text(c, SWT.SINGLE | SWT.BORDER);
			txtDrvC.setLayoutData(spanData);
			// �h���C�o�[�E�t�@�C���̐ݒ�B
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
		// �R���{�{�b�N�X�ɒl���ݒ肳��Ă���ꍇ�̂ݏ������s���B
		if (!combo.getText().isEmpty()) {
			int pos = 0;
			// ���ɒ�`����Ă��閼�O�̏ꍇ�͍X�V�B�����łȂ��ꍇ�͒ǉ��B
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
			
			// �ݒ�t�@�C���ɏ������݁B
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
