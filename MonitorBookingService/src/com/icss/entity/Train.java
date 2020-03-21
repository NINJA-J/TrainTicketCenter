package com.icss.entity;

import java.util.Date;
import java.util.List;


public class Train {
	private String tno;
	private String sArea;
	private String eArea;
	private Date sDepTime;
	private String longTime;
	private int cusDepArea,cusDepAreaIndex;
	private int cusDesArea,cusDesAreaIndex;
	private int cusOccupation;

	private List<BanCi> bans;

	public List<BanCi> getBans() {
		return this.bans;
	}

	public void setBans(List<BanCi> bans) {
		this.bans = bans;
	}

	public String getsArea() {
		return this.sArea;
	}

	public void setsArea(String sArea) {
		this.sArea = sArea;
	}

	public String geteArea() {
		return this.eArea;
	}

	public void seteArea(String eArea) {
		this.eArea = eArea;
	}

	public Date getsDepTime() {
		return this.sDepTime;
	}

	public void setsDepTime(Date sDepTime) {
		this.sDepTime = sDepTime;
	}

	public int getCusDepArea() {
		return this.cusDepArea;
	}

	public void setCusDepArea(int cusDepArea) {
		this.cusDepArea = cusDepArea;
	}

	public int getCusDepAreaIndex() {
		return this.cusDepAreaIndex;
	}

	public void setCusDepAreaIndex(int cusDepAreaIndex) {
		this.cusDepAreaIndex = cusDepAreaIndex;
	}

	public int getCusDesArea() {
		return this.cusDesArea;
	}

	public void setCusDesArea(int cusDesArea) {
		this.cusDesArea = cusDesArea;
	}

	public int getCusDesAreaIndex() {
		return this.cusDesAreaIndex;
	}

	public void setCusDesAreaIndex(int cusDesAreaIndex) {
		this.cusDesAreaIndex = cusDesAreaIndex;
	}

	public int getCusOccupation() {
		return this.cusOccupation;
	}

	public void setCusOccupation(int cusOccupation) {
		this.cusOccupation = cusOccupation;
	}

	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	public String getLongTime() {
		return longTime;
	}
	public void setLongTime(String longTime) {
		this.longTime = longTime;
	}

	@Override
	public String toString(){
		return "[" + tno + ", " + sArea + ", " + eArea + ", " + cusDepAreaIndex + "-" + cusDesAreaIndex + ", bans=" + bans + "]";
	}
	
	
    
}
	
	