package net.begeek.jsc.view.component;

import java.util.Observable;

import net.begeek.jsc.service.SQLExecuteService.Action;
import net.begeek.jsc.view.component.action.ResultCopyAction;
import net.begeek.jsc.view.component.action.ResultDataCopyAction;
import net.begeek.jsc.view.component.action.SelectAllAction;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * ���ʃe�[�u���\���p��ʕ��i�B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResultTablePanel extends ResultPanel {
	/** ���ʕ\���p�e�[�u�� */
	private Table table;
	/** ���R�[�h�C���f�b�N�X�ԍ� */
	private int idx = 0;
	
	/**
	 * �R���X�g���N�^�B
	 * 
	 * @param parent �e�E�B�W�F�b�g
	 * @param style �E�B�W�F�b�g�̃X�^�C��
	 */
	public ResultTablePanel(Composite parent, int style) {
		super(parent, style);
		
		// ���ʕ\���p�̃e�[�u���𐶐�����B
		table = new Table(this, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.VIRTUAL);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		// �\���ʒu��ݒ肷��B
		FormData resultData = new FormData();
		resultData.top = new FormAttachment(text, 5);
		resultData.bottom = new FormAttachment(100, 0);
		resultData.left = new FormAttachment(0, 0);
		resultData.right = new FormAttachment(100, 0);
		table.setLayoutData(resultData);
		
		// �R���e�L�X�g���j���[��ݒ肷��B
		MenuManager menuManager = new MenuManager();
		menuManager.add(new ResultCopyAction(table));
		menuManager.add(new ResultDataCopyAction(table));
		menuManager.add(new SelectAllAction(table));
		table.setMenu(menuManager.createContextMenu(table));
		
		// �L�[�C�x���g��ݒ肷��B
		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == 'c') {							// Ctrl+C
					// �R�s�[�A�N�V�������s
					new ResultCopyAction(table).run();
				} else if (e.stateMask == (SWT.CTRL | SWT.SHIFT) && e.keyCode == 'c') {		// Ctrl+Shift+C
					// �񖼂Ȃ��R�s�[�A�N�V�������s
					new ResultDataCopyAction(table).run();
				} else if (e.stateMask == SWT.CTRL && e.keyCode == 'a') {					// Ctrl + A
					// �S�I���A�N�V�������s
					new SelectAllAction(table).run();
				}
			}
			
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable o, Object arg) {
		ResultTablePanelOBean bean = (ResultTablePanelOBean) o;
		if (arg instanceof Action) {
			switch ((Action)arg) {
			// ��������
			case INIT:
				super.update(o, arg);
				// �e�[�u���J�����̐ݒ�
				TableColumn colIdx = new TableColumn(table, SWT.LEAD);
				// 1��ڂ͍s�ԍ��\�����Ƃ��āA�񖼂͋󔒂ɂ���B
				colIdx.setText("");
				colIdx.setWidth(30);
				// ���̗񖼂��N���b�N����ƃe�[�u���f�[�^��S�I������B
				colIdx.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						table.selectAll();
					}
					
				});
				
				// 2��ڈȍ~������ۂ̗񖼂�ݒ肷��B
				for (int i = 0; i < bean.getColumns().length; i++) {
					TableColumn col = new TableColumn(table, SWT.LEAD);
					col.setText(bean.getColumns()[i]);
					col.setWidth(100);
				}
				break;
			// �f�[�^�擾���e����ʂɕ`�悷��^�C�~���O
			case ADD:
				// �ʒm���ꂽ�f�[�^����ʕ`�悷��B
				Display.getDefault().syncExec(new CreateTableData(bean));
				break;
			// �f�[�^�擾���I�������^�C�~���O
			case END:
				// ��ʕ`�悵�Ă��Ȃ��]���`�悷��B
				Display.getDefault().syncExec(new CreateTableData(bean));
				break;
			}
		}
	}
	
	/**
	 * ���ʕ\���e�[�u���Ƀf�[�^��`�悷��N���X�B
	 * 
	 * @author kiyono
	 * @version 1.0
	 */
	private class CreateTableData implements Runnable {
		/** ���ʃe�[�u���\���pBean */
		private ResultTablePanelOBean bean;
		
		/**
		 * �R���X�g���N�^�B
		 * 
		 * @param bean ���ʃe�[�u���\���pBean
		 */
		public CreateTableData(ResultTablePanelOBean bean) {
			this.bean = bean;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void run() {
			// ���ʕ\���p�̃e�[�u�����j������Ă���ꍇ�͏������I���B
			if (table.isDisposed()) return;
			// ���R�[�h�`�揈��
			for (String[] data : bean.getData()) {
				TableItem item= new TableItem(table, SWT.NONE);
				String[] rowData = new String[data.length + 1];
				// 1��ڂ͍s�ԍ���ݒ肷��B
				rowData[0] = String.valueOf(++idx);
				System.arraycopy(data, 0, rowData, 1, data.length);
				item.setText(rowData);
				// 1��ڂ̕����F���O���[�ɐݒ肷��B
				item.setForeground(0, Display.getCurrent().getSystemColor(SWT.COLOR_GRAY));
			}
		}
		
	}
	
	
}
