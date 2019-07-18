package com.tuyano.springboot;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "mdgdata")
public class MsgData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull
	private long Id;
	
	@Column
	private String title;
	
	@Column
	@NotEmpty
	private String message;
	
	@ManyToOne
	private MyData mydata;
	
	public MsgData() {
		super();
		
		mydata = new MyData();
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MyData getMydata() {
		return mydata;
	}

	public void setMydata(MyData mydata) {
		this.mydata = mydata;
	}
	
	
	
	
}
