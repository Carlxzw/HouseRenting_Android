package com.example.administer.houserenting_android.model;

public class AppointmentInfo {
	//????
	public String appointmentNo;
	//?????
	public String appointmentDate;
	//????
	public RoomInfo roomNo;
	//???????
	public UserInfo appointmenter;
	//????id
	public UserInfo manger;
	//???????id
	public UserInfo saler;
	//????id
	public String appointmentState;
	//??
	public String getAppointmentNo() {
		return appointmentNo;
	}
	public void setAppointmentNo(String appointmentNo) {
		this.appointmentNo = appointmentNo;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	public RoomInfo getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(RoomInfo roomNo) {
		this.roomNo = roomNo;
	}
	public UserInfo getAppointmenter() {
		return appointmenter;
	}
	public void setAppointmenter(UserInfo appointmenter) {
		this.appointmenter = appointmenter;
	}
	public UserInfo getManger() {
		return manger;
	}
	public void setManger(UserInfo manger) {
		this.manger = manger;
	}
	public UserInfo getSaler() {
		return saler;
	}
	public void setSaler(UserInfo saler) {
		this.saler = saler;
	}
	public String getAppointmentState() {
		return appointmentState;
	}
	public void setAppointmentState(String appointmentState) {
		this.appointmentState = appointmentState;
	}
	
}
