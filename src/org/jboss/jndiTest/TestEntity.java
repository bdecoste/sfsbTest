package org.jboss.jndiTest;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TestEntity
 *
 */
@Entity
@Table(name="TESTENTITY")
public class TestEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String value;

	public TestEntity() {
		super();
	}
	
	@Id
	//@GeneratedValue(strategy= GenerationType.AUTO)
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
   
}
