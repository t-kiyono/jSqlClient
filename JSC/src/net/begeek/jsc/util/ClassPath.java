package net.begeek.jsc.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 動的クラスパス追加ユーティリティ。
 * 
 * @author kiyono
 * @version 1.0
 */
public class ClassPath {

	/**
	 * 引数の文字列からファイルオブジェクト生成し、{@link ClassPath#addClassPathToClassLoader(File)}を呼び出す。
	 * 
	 * @param classPath クラスパスに追加するファイル名
	 * @throws Exception
	 */
	public static void addClassPathToClassLoader(String classPath) throws Exception {
		addClassPathToClassLoader(new File(classPath));
	}
	
	/**
	 * システムクラスローダにファイルを追加する。
	 * 
	 * @param classPath クラスパスに追加するファイル
	 * @throws Exception
	 */
	public static void addClassPathToClassLoader(File classPath) throws Exception {
		addClassPathToClassLoader((URLClassLoader) ClassLoader.getSystemClassLoader(), classPath);
	}
	
	/**
	 * 指定されたクラスローダのクラスパスにファイルを追加する。
	 * 
	 * @param classLoader クラスパスを追加するクラスローダ
	 * @param classPath クラスパスに追加するファイル
	 * @throws Exception
	 */
	public static void addClassPathToClassLoader(URLClassLoader classLoader, File classPath) throws Exception {
		Class<URLClassLoader> classClassLoader = URLClassLoader.class;
		Method methodAddUrl = classClassLoader.getDeclaredMethod("addURL", URL.class);
		
		methodAddUrl.setAccessible(true);
		methodAddUrl.invoke(classLoader, classPath.toURI().toURL());
	}
}
