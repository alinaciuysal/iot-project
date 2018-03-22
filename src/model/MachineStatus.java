package model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "machinestatus")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MachineStatus implements Serializable {

		private static final long serialVersionUID = 1L;

		@Id  
		@Column(name="id")  
		@GeneratedValue()
		private long id;
		
		@Column(name="temperature")
		double temperature;
		
		@Column(name="date")
		Timestamp t;
		
		@Column(name="isOperating")
		boolean isOperating;
		
		@Column(name="machineId")
		int machineId;
		
		String proper_time_format;
		
		String proper_is_operational;
		
		public long getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public double getTemperature() {
			return temperature;
		}
		public void setTemperature(double temperature) {
			this.temperature = temperature;
		}
		
		public boolean getIsOperating() {
			return isOperating;
		}
		public void setIsOperating(boolean isOperating) {
			this.isOperating = isOperating;
		}
		
		public int getMachineId() {
			return machineId;
		}
		public void setMachineId(int machineId) {
			this.machineId = machineId;
		}
		
		public Timestamp setTimestamp() {
			return t;
		}
		
		public void setTimestamp(Timestamp t) {
			this.t = t;
		}
		
		public void setProperTime(String proper_time_format) {
			this.proper_time_format = proper_time_format;	
		}
		
		public String getProperTime() {
			return proper_time_format;
		}
		
		public void setProperOperational(boolean isOp) {
			if(isOp)
				proper_is_operational = "Yes";
			else
				proper_is_operational = "No";
		}
		
		public String getProperOperational() {
			return proper_is_operational;
		}
}