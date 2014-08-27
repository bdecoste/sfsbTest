package org.jboss.jndiTest;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TestEntity
 *
 */
@Entity
@Table(name="TESTENTITY")
@javax.persistence.Cacheable
public class TestEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long value;

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
	
	public long getValue(){
		return value;
	}
	
	public void setValue(long value){
		this.value = value;
	}
   
}
