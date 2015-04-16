/**
 * 
 */
package com.fb.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jeffee
 *
 */
public class FileProcess {

	private static void checkDir(File dFile){
		File dir = dFile.getParentFile();
		if(!dir.exists()||!dir.isDirectory())
			dir.mkdirs();
	}
	
	/***如果文件夹存在返回true，否则创建文件夹并返回false***/
	public static boolean checkDir(String dirPath){
		File file = new File(dirPath);
		if(!file.exists()||!file.isDirectory()){
			file.mkdirs();
			return false;
		}
		return true;
	}
	
	public static void write(String fileName, List<String> infoList){
		System.out.println(fileName);
		FileWriter fWriter;
		try {
			File dFile = new File(fileName);
			checkDir(dFile);
			fWriter = new FileWriter(dFile);
			for (int i = 0; i < infoList.size(); i++) {
			fWriter.write(infoList.get(i));
			fWriter.write("\r\n");
			}
			fWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

 
	public static void write(String fileName, String info){
		FileWriter fw;
		try {
			File dFile = new File(fileName);
			checkDir(dFile);
			fw = new FileWriter(dFile);
			fw.write(info);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<String> read(String fileStr){
		return read(new File(fileStr));
	}
	public static List<String> read(File sFile){
		List<String> list = new LinkedList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sFile)));
			String line = br.readLine();
			while(line!=null){
				list.add(line.trim());
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static String readLine(String fileStr) {
		return readLine(new File(fileStr));
	}
	
	public static String readLine(File sFile){
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sFile)));
			line = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return line;
	}
}
