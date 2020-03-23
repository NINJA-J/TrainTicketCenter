package com.icss.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JMSDto implements Serializable {

	private String orderno; 			// 订单号
	private String uname; 				// 用户名
	private int bno; 					// 车次
	private int sArea;
	private int eArea;
	private String type;				// 车票类型
}
