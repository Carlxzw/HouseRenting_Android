package com.example.administer.houserenting_android.model;

import java.util.List;

public class HouseDetail {

    private RoomDevice device;
    private RoomInfo room;
    private UserInfo userNo;
    private List<RoomPicture> picture;

    public RoomDevice getDevice() {
        return device;
    }

    public void setDevice(RoomDevice device) {
        this.device = device;
    }

    public RoomInfo getRoom() {
        return room;
    }

    public void setRoom(RoomInfo room) {
        this.room = room;
    }

    public UserInfo getUserNo() {
        return userNo;
    }

    public void setUserNo(UserInfo userNo) {
        this.userNo = userNo;
    }

    public List<RoomPicture> getPicture() {
        return picture;
    }

    public void setPicture(List<RoomPicture> picture) {
        this.picture = picture;
    }
}
