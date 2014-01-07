package net.begeek.jsc.main;

import net.begeek.jsc.util.ClassPath;
import net.begeek.jsc.view.jscm001.JSCM001;

/**
 * Java SQL Client ���s�N���X�B<br>
 * SWT/JFace�n��jar�t�@�C�����܂߂��ɔz�z�ł���悤�ɁASWT/JFace�Ɉˑ����Ȃ��`�ō쐬�B<br>
 * ���s�������ŕK�v��jar�t�@�C�����w�肷�邱�ƂŁA���I�ɃN���X�p�X��ʂ��Ď��s�\
 * 
 * @author kiyono
 * @version 1.0
 */
public class JSCExecuter {

	/**
	 * ���C�����\�b�h
	 * 
	 * @param args �N���X�p�X�ɒǉ�����jar�t�@�C��
	 */
	public static void main(String[] args) {
		try {
			// ���I�ɃN���X�p�X��jar�t�@�C����ǉ�����
			for (int i = 0; i < args.length; i++) {
				ClassPath.addClassPathToClassLoader(args[i]);
			}
			// ���C���E�B���h�E�̃I�[�v��
			JSCM001 w = new JSCM001(null);
			w.setBlockOnOpen(true);
			w.open();
		} catch (Exception e) {
			// TODO �G���[�����͂����ƍl����K�v����
			e.printStackTrace();
		} finally {
			// ���\�[�X�J��
			ResourceManager.dispose();
		}
	}

}
