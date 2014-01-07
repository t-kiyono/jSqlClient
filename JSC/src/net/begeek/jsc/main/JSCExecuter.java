package net.begeek.jsc.main;

import net.begeek.jsc.util.ClassPath;
import net.begeek.jsc.view.jscm001.JSCM001;

/**
 * Java SQL Client 実行クラス。<br>
 * SWT/JFace系のjarファイルを含めずに配布できるように、SWT/JFaceに依存しない形で作成。<br>
 * 実行時引数で必要なjarファイルを指定することで、動的にクラスパスを通して実行可能
 * 
 * @author kiyono
 * @version 1.0
 */
public class JSCExecuter {

	/**
	 * メインメソッド
	 * 
	 * @param args クラスパスに追加するjarファイル
	 */
	public static void main(String[] args) {
		try {
			// 動的にクラスパスにjarファイルを追加する
			for (int i = 0; i < args.length; i++) {
				ClassPath.addClassPathToClassLoader(args[i]);
			}
			// メインウィンドウのオープン
			JSCM001 w = new JSCM001(null);
			w.setBlockOnOpen(true);
			w.open();
		} catch (Exception e) {
			// TODO エラー処理はちゃんと考える必要あり
			e.printStackTrace();
		} finally {
			// リソース開放
			ResourceManager.dispose();
		}
	}

}
