package com.example.administer.houserenting_android.model;

public class JudgeInfo {
	// ���۱�
	private String judgeNo;
	// ���۱��
	private AppointmentInfo appointmentNo;
	// ���ݱ��
	private UserInfo userNo;
	// �����û����
	private String judge;
	// ��������
	private String judgeState;
	// ����״̬
	private String judgeDate;

	public String getJudgeNo() {
		return judgeNo;
	}

	public void setJudgeNo(String judgeNo) {
		this.judgeNo = judgeNo;
	}

	public AppointmentInfo getAppointmentNo() {
		return appointmentNo;
	}

	public void setAppointmentNo(AppointmentInfo appointmentNo) {
		this.appointmentNo = appointmentNo;
	}

	public UserInfo getUserNo() {
		return userNo;
	}

	public void setUserNo(UserInfo userNo) {
		this.userNo = userNo;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public String getJudgeState() {
		return judgeState;
	}

	public void setJudgeState(String judgeState) {
		this.judgeState = judgeState;
	}

	public String getJudgeDate() {
		return judgeDate;
	}

	public void setJudgeDate(String judgeDate) {
		this.judgeDate = judgeDate;
	}
}
