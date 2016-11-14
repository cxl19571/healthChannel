package com.laya.system.model;

import java.io.File;

public class Data {
	
	private String userName;
	private String passWord;
	private File file;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "Data [userName=" + userName + ", passWord=" + passWord
				+ ", file=" + file + "]";
	}

}
