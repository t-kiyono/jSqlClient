package net.begeek.jsc.main;

import net.begeek.jsc.util.ConnectionManager;

import org.eclipse.swt.widgets.Display;

/**
 * リソース解放用クラス。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ResourceManager {

	/**
	 * リソース解放メソッド。必ずメインメソッドのfinally句で呼び出すこと。
	 */
	public static void dispose() {
		ConnectionManager.closeConnection();
		Display.getCurrent().dispose();
	}
}
