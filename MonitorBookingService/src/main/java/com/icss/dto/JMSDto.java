package com.icss.dto;

public class JMSDto {

	private String orderno; 			// ������
	private String uname; 				// �û���
	private int bno; 					// ����
	private int sArea;
	private int eArea;
	private String type;				// ��Ʊ����


	public int getsArea() {
		return sArea;
	}

	public void setsArea(int sArea) {
		this.sArea = sArea;
	}

	public int geteArea() {
		return eArea;
	}

	public void seteArea(int eArea) {
		this.eArea = eArea;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
