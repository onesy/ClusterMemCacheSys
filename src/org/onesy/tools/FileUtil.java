package org.onesy.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtil {

	public static Properties getAsProperties(File file){

		Properties properties = new Properties();

		try {
			if(!file.exists()){
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			properties.load(in);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return properties;
	}
	
	public static boolean ExistDir(File file){
		if(file.exists() && file.isDirectory()){
			return true;
		}
		return false;
		
	}

	/**
	 * @param file
	 * @param folder
	 * @descriptor 总之，创建文件或者文件夹
	 */
	public static boolean FileCheckCreate(File file, boolean folder) {
		if (folder && !file.exists()) {
			file.mkdirs();
			return true;
		} else if (!folder && !file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

}
