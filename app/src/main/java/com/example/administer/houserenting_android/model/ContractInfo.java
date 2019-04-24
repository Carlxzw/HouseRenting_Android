package com.example.administer.houserenting_android.model;

public class ContractInfo {
	//��ͬ��
	public String contractNo;
	//��ͬ���
	public String contractState;
	//��ͬ״̬
	public String contractSignDate;
	//ǩ������
	public RoomInfo roomNo;
	//ǩ������
	public String contractSignTime;
	//ǩ��ʱ��
	public UserInfo buyer;
	//ǩ����
	public UserInfo saler;
	//���ⷽ
	public UserInfo operate;
	//����Ա
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getContractState() {
		return contractState;
	}
	public void setContractState(String contractState) {
		this.contractState = contractState;
	}
	public String getContractSignDate() {
		return contractSignDate;
	}
	public void setContractSignDate(String contractSignDate) {
		this.contractSignDate = contractSignDate;
	}
	public String getContractSignTime() {
		return contractSignTime;
	}
	public void setContractSignTime(String contractSignTime) {
		this.contractSignTime = contractSignTime;
	}
	public RoomInfo getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(RoomInfo roomNo) {
		this.roomNo = roomNo;
	}
	public UserInfo getBuyer() {
		return buyer;
	}
	public void setBuyer(UserInfo buyer) {
		this.buyer = buyer;
	}
	public UserInfo getSaler() {
		return saler;
	}
	public void setSaler(UserInfo saler) {
		this.saler = saler;
	}
	public UserInfo getOperate() {
		return operate;
	}
	public void setOperate(UserInfo operate) {
		this.operate = operate;
	}
	
	
}
