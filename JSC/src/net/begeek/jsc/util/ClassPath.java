package net.begeek.jsc.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * ���I�N���X�p�X�ǉ����[�e�B���e�B�B
 * 
 * @author kiyono
 * @version 1.0
 */
public class ClassPath {

	/**
	 * �����̕����񂩂�t�@�C���I�u�W�F�N�g�������A{@link ClassPath#addClassPathToClassLoader(File)}���Ăяo���B
	 * 
	 * @param classPath �N���X�p�X�ɒǉ�����t�@�C����
	 * @throws Exception
	 */
	public static void addClassPathToClassLoader(String classPath) throws Exception {
		addClassPathToClassLoader(new File(classPath));
	}
	
	/**
	 * �V�X�e���N���X���[�_�Ƀt�@�C����ǉ�����B
	 * 
	 * @param classPath �N���X�p�X�ɒǉ�����t�@�C��
	 * @throws Exception
	 */
	public static void addClassPathToClassLoader(File classPath) throws Exception {
		addClassPathToClassLoader((URLClassLoader) ClassLoader.getSystemClassLoader(), classPath);
	}
	
	/**
	 * �w�肳�ꂽ�N���X���[�_�̃N���X�p�X�Ƀt�@�C����ǉ�����B
	 * 
	 * @param classLoader �N���X�p�X��ǉ�����N���X���[�_
	 * @param classPath �N���X�p�X�ɒǉ�����t�@�C��
	 * @throws Exception
	 */
	public static void addClassPathToClassLoader(URLClassLoader classLoader, File classPath) throws Exception {
		Class<URLClassLoader> classClassLoader = URLClassLoader.class;
		Method methodAddUrl = classClassLoader.getDeclaredMethod("addURL", URL.class);
		
		methodAddUrl.setAccessible(true);
		methodAddUrl.invoke(classLoader, classPath.toURI().toURL());
	}
}
