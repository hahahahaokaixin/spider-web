package com.guo.base.util;

public class ProxyIp {

	public ProxyIp(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	public ProxyIp(String ip, int port,String time) {
		this.ip = ip;
		this.port = port;
		this.time=time;
	}

	private String ip;
	private int port;
	private String time;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
