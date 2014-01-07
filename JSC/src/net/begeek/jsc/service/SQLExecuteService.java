package net.begeek.jsc.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.begeek.jsc.util.ConnectionManager;
import net.begeek.jsc.view.component.MainPanelOBean;
import net.begeek.jsc.view.component.ResultTablePanelOBean;
import net.begeek.jsc.view.component.ResultTextPanelOBean;


/**
 * SQL���s�T�[�r�X�N���X�B<br>
 * SQL�̎��s���ʂ�ResultSet�̏ꍇ�́A�ʃX���b�h��ResultSet����l���擾���A��ʂɕ`�悷��B<br>
 * ���̍ہA{@link SQLExecuteService#enabled}��false�ɂ��邱�ƂŁA�擾������r���ŏI���ł���B<br>
 * SQL�̎��s���ʂ��X�V�J�E���g�̏ꍇ�́A�X�V��������ʂɕ`�悷��B
 * 
 * @author kiyono
 * @version 1.0
 */
public class SQLExecuteService {
 
	/**
	 * ��ʏ�������p�̗񋓌^�B
	 * 
	 * @author kiyono
	 * @version 1.0
	 */
	public enum Action {
		INIT,
		ADD,
		END
	}
	
	/** 1�x�ɉ�ʂɕ`�悷�錏�� */
	public static final int TRANSFER_COUNT = 500;
	
	/**
	 * �擾�������荞�ݗp�̕ϐ�<br>
	 * false���ݒ肳���ƁA�ʃX���b�h�̎擾�������I�����A���ʂ���ʂɒʒm�����B
	 */
	public static boolean enabled = true;
	
	/**
	 * SQL���s�����B
	 * 
	 * @param bean ���C�����Bean
	 */
	public void execute(MainPanelOBean bean) {
		// �����̎n�߂Ɋ��荞�ݗp�ϐ���L�������Ă����B
		enabled = true;
		Connection con = ConnectionManager.getConnection();
		try {
			// �R�l�N�V�������I�[�v�����Ă���ꍇ�̂݁A�������s���B
			if (con != null && !con.isClosed()) {
				final Statement stmt = con.createStatement();
				
				// SQL���s
				if (stmt.execute(bean.getiSQLText())) {		// ���ʂ�ResultSet�̏ꍇ
					// �e�[�u���\���pBean���쐬����B
					final ResultTablePanelOBean resultTablePanelOBean = new ResultTablePanelOBean();
					resultTablePanelOBean.setoSQLText(bean.getiSQLText());
					bean.setoOBean(resultTablePanelOBean);
					// ���ʃ^�u�𐶐����邽�߁A��ʂ֒ʒm����B
					bean.update();
					
					// ResultSet�̏������s���B
					final ResultSet rs = stmt.getResultSet();
					final ResultSetMetaData rsmd = rs.getMetaData();
					// �J�������̐ݒ菈���B
					final int columnCount = rsmd.getColumnCount();
					String[] columns = new String[columnCount];
					for (int i = 0; i < columns.length; i++) {
						columns[i] = rsmd.getColumnName(i+1);
					}
					// �J�������݂̂̏�ԂŁA��U��ʕ`����s���B
					resultTablePanelOBean.setColumns(columns);
					resultTablePanelOBean.update(Action.INIT);
					
					// �f�[�^�擾�X���b�h
					Thread thread = new Thread(){

						@Override
						public void run() {
							try {
								List<String[]> data = new ArrayList<String[]>();
								// �f�[�^�擾����
								int cnt = 0;
								while (rs.next()) {
									// 1���R�[�h���̃o�b�t�@
									String[] buf = new String[columnCount];
									for (int i = 0; i < columnCount; i++) {
										buf[i] = rs.getString(i+1);
									}
									data.add(buf);
									cnt++;
									
									// �f�[�^�擾������1�x�ɉ�ʂɕ`�悷�錏���ɒB�������ʕ`����s���B
									if (cnt % TRANSFER_COUNT == 0) {
										resultTablePanelOBean.setData(data);
										resultTablePanelOBean.update(Action.ADD);
										data = new ArrayList<String[]>();
										cnt = 0;
									}
									
									// ���荞�ݗp�ϐ���false�̏ꍇ�́A�������I������B
									if (!enabled) break;
								}
								// 1�x�ɉ�ʂɕ`�悷�錏���ŁA����؂�Ȃ������]�����ʕ`�悷��B
								resultTablePanelOBean.setData(data);
								resultTablePanelOBean.update(Action.END);
							} catch (SQLException e) {
								new RuntimeException(e);
							} finally {
								// ���\�[�X���
								if (rs != null) {
									try {
										rs.close();
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
								if (stmt != null) {
									try {
										stmt.close();
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
							}
						}
						
					};
					thread.start();
				} else {		// ���ʂ��X�V�J�E���g�̏ꍇ
					// ��ʕ\�����b�Z�[�W��������B
					int count = stmt.getUpdateCount();
					String msg = count + "���������܂����B";
					
					// ��ʕ\��
					createResultTextPanel(bean, bean.getiSQLText(), msg);
					// ���\�[�X���
					if (stmt != null) {
						try {
							stmt.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (SQLException e) {
			// ��O�������́A��O���e�����ʗ��ɕ\������B
			String msg = e.getMessage();
			createResultTextPanel(bean, bean.getiSQLText(), msg);
		}
	}
	
	/**
	 * SQL���s���ʕ\�����\�b�h�B<br>
	 * SQL�̎��s���ʂ�ResultSet�ł͂Ȃ��ꍇ�ɁA���ʂ���ʂɕ\������B
	 * 
	 * @param parentBean ���s����Bean�̐eBean
	 * @param sql ���sSQL��
	 * @param msg ���s����
	 */
	private void createResultTextPanel(MainPanelOBean parentBean, String sql, String msg) {
		// ���b�Z�[�W�\���pBean���쐬����B
		ResultTextPanelOBean resultTextPanelOBean = new ResultTextPanelOBean();
		parentBean.setoOBean(resultTextPanelOBean);
		// ���ʃ^�u�𐶐����邽�߁A��ʂ֒ʒm����B
		parentBean.update();
		
		// ���b�Z�[�W�\���pBean�ɕ\�����e��ݒ肷��B
		resultTextPanelOBean.setoSQLText(sql);
		resultTextPanelOBean.setoResultText(msg);
		// ���b�Z�[�W�\���pBean�̓��e����ʂɒʒm����B
		resultTextPanelOBean.update();
	}
}
