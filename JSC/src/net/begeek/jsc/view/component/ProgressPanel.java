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
 * SQL�擾�i���\�����i�B<br>
 * �L�����Z���{�^�����������ƂŁA�擾�������I���ł���B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ProgressPanel extends AbstractObserverComposite {

	/** ��ʕ\�����b�Z�[�W�e���v���[�g */
	private static final String MSG_TMPL = "{0}���f�[�^�]����";
	/** ��ʕ\������ */
	private Label lblProgress;
	/** �]���ς݌��� */
	private int count = 0;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parent �e�E�B�W�F�b�g
	 * @param style �E�B�W�F�b�g�̃X�^�C��
	 */
	public ProgressPanel(Composite parent, int style) {
		super(parent, style);
		// ���C�A�E�g�ݒ�B
		setLayout(new GridLayout(1, true));
		// ��ʕ\�������𐶐�����B
		lblProgress = new Label(this, SWT.NONE);
		lblProgress.setText(MessageFormat.format(MSG_TMPL, 0));
		lblProgress.setLayoutData(new GridData(GridData.BEGINNING | GridData.FILL_HORIZONTAL));
		// �L�����Z���{�^���𐶐�����B
		Button btnCancel = new Button(this, SWT.PUSH);
		btnCancel.setText("�L�����Z��");
		btnCancel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.FILL_HORIZONTAL));
		btnCancel.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//�@�������荞�ݗp�ϐ��ɖ�������ݒ肷��B
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
			// �f�[�^�擾���e����ʂɕ`�悷��^�C�~���O
			case ADD:
				Display.getDefault().syncExec(new Runnable() {
					
					@Override
					public void run() {
						// �]���ς݌�����1�x�ɓ]������Ă��錏���𑫂��B
						count = count + SQLExecuteService.TRANSFER_COUNT;
						// ��ʕ\���������X�V����B
						lblProgress.setText(MessageFormat.format(MSG_TMPL, count));
					}
				});
				break;
			// �f�[�^�擾���I�������^�C�~���O
			case END:
				Display.getDefault().syncExec(new Runnable() {
					
					@Override
					public void run() {
						// �{��ʂ����B
						getShell().close();
					}
				});
				break;
			}
		}
		
	}
}
