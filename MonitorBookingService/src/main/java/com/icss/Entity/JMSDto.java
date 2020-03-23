package com.icss.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JMSDto implements Serializable {

	private String orderno; 			// ������
	private String uname; 				// �û���
	private int bno; 					// ����
	private int sArea;
	private int eArea;
	private String type;				// ��Ʊ����
}
