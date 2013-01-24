package org.onesy.test;

public class RequestInfo {
	
	使用这个来模拟对象
	
	private String user;
	
	private String age ;
	
	public String RequestMagic ;
	
	public String toString(){
		return user + age + RequestMagic;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
