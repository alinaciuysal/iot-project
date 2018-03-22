package services;

import java.util.List;

import model.MachineStatus;
import model.Product;

public interface DataServices {
	public boolean addEntity(Product employee) throws Exception;
	public Product getEntityById(long id) throws Exception;
	public List<Product> getEntityList() throws Exception;
	public boolean deleteEntity(long id) throws Exception;
	public boolean updateEntity(long id, int update_amount, int machineId) throws Exception;
	public void updateEntitiesToSpecificNumberWithMachineId(List<Product> productList, int machineId);
	public List<Product> getEntityListByMachineId(int machineId) throws Exception;
	public List<MachineStatus> getTemperatureById(int machineId);
}
