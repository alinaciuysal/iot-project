package dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import model.MachineStatus;
import model.Product;

@Repository
public interface DataDao {

	public boolean addEntity(Product employee) throws Exception;
	public Product getEntityById(long id) throws Exception;
	public List<Product> getEntityList() throws Exception;
	public boolean deleteEntity(long id) throws Exception;
	public boolean updateEntity(long id, int update_amount, int machineId) throws Exception;
	public void updateEntitiesToSpecificNumberWithMachineId(List<Product> productList, int machineId);
	public List<Product> getEntityListByMachineId(int machineId) throws SQLException;
	public List<MachineStatus> getTemperatureById(int machineId);
}
