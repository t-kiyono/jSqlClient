package net.begeek.jsc.main;

import net.begeek.jsc.util.ConnectionManager;

import org.eclipse.swt.widgets.Display;

/**
 * ���\�[�X����p�N���X�B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResourceManager {

	/**
	 * ���\�[�X������\�b�h�B�K�����C�����\�b�h��finally��ŌĂяo�����ƁB
	 */
	public static void dispose() {
		ConnectionManager.closeConnection();
		Display.getCurrent().dispose();
	}
}
