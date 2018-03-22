package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id  
	@Column(name="id")  
	@GeneratedValue()
	private long id;
	
	@Column(name="productName")
	String productName;
	
	@Column(name="productAmount")
	int productAmount;
	
	@Column(name="machineId")
	int machineId;
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getAmount() {
		return productAmount;
	}
	public void setAmount(int productAmount) {
		this.productAmount = productAmount;
	}
	
	public int getMachineId() {
		return machineId;
	}
	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}
}
