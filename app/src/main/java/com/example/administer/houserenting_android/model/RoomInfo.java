package com.example.administer.houserenting_android.model;

public class RoomInfo {
	private String roomNo;

	private String roomAddress;

	private String roomKind;

	private String roomArea;

	private String roomPrice;

	private String roomBackup;

	private String roomState;

	private String roomType;

	private String roomTitle;


	private UserInfo userNo;

	private RoomPicture roompicture;

	public RoomPicture getRoompicture() {
		return roompicture;
	}

	public void setRoompicture(RoomPicture roompicture) {
		this.roompicture = roompicture;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getRoomAddress() {
		return roomAddress;
	}

	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
	}

	public String getRoomKind() {
		return roomKind;
	}

	public void setRoomKind(String roomKind) {
		this.roomKind = roomKind;
	}

	public String getRoomArea() {
		return roomArea;
	}

	public void setRoomArea(String roomArea) {
		this.roomArea = roomArea;
	}

	public String getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(String roomPrice) {
		this.roomPrice = roomPrice;
	}

	public String getRoomBackup() {
		return roomBackup;
	}

	public void setRoomBackup(String roomBackup) {
		this.roomBackup = roomBackup;
	}

	public String getRoomState() {
		return roomState;
	}

	public void setRoomState(String roomState) {
		this.roomState = roomState;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomTitle() {
		return roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}


	public UserInfo getUserNo() {
		return userNo;
	}

	public void setUserNo(UserInfo userNo) {
		this.userNo = userNo;
	}
}
