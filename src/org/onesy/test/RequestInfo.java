package org.onesy.test;

public class RequestInfo {
	
	//使用这个来模拟对象
	
	private String user;
	
	private int age ;
	
	public String RequestMagic ;
	
	public RequestInfo(String username, int age, String RequstMagic){
		this.setUser(username);
		this.setAge(age);
		this.RequestMagic = RequstMagic;
	}
	
	public String toString(){
		return user + age + RequestMagic;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
