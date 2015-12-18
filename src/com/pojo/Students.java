package com.pojo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name="students")   
@Indexed
public class Students{
	
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(generator = "generator")
	@Column(name = "id")
	@DocumentId
	private int id;
	
	@Column(name = "username", length = 40)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
	private String username;
	
	@Column(name = "address", length = 40)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
	private String address;
	
	public Students() {
	}
	
	public Students(String username, String address) {
		super();
		
		this.username = username;
		this.address = address;
	}
	
	public Students(int id, String username, String address) {
		super();
		this.id = id;
		this.username = username;
		this.address = address;
	}

	@Override
	public String toString() {
		return "Students [id=" + id + ", username=" + username + ", address=" + address + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}