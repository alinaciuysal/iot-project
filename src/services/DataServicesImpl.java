package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import dao.DataDao;
import model.MachineStatus;
import model.Product;

public class DataServicesImpl implements DataServices {

	@Autowired
	DataDao dataDao;
	
	@Override
	public boolean addEntity(Product employee) throws Exception {
		return dataDao.addEntity(employee);
	}

	@Override
	public Product getEntityById(long id) throws Exception {
		return dataDao.getEntityById(id);
	}

	@Override
	public List<Product> getEntityList() throws Exception {
		return dataDao.getEntityList();
	}
	
	@Override
	public List<Product> getEntityListByMachineId(int machineId) throws Exception {
		return dataDao.getEntityListByMachineId(machineId);
	}

	@Override
	public boolean deleteEntity(long id) throws Exception {
		return dataDao.deleteEntity(id);
	}
	
	@Override
	public boolean updateEntity(long id, int update_amount, int machineId) throws Exception {
		return dataDao.updateEntity(id, update_amount, machineId);
	}

	@Override
	public void updateEntitiesToSpecificNumberWithMachineId(List<Product> productList, int machineId) {
		dataDao.updateEntitiesToSpecificNumberWithMachineId(productList, machineId);
	}

	@Override
	public List<MachineStatus> getTemperatureById(int machineId) {
		return dataDao.getTemperatureById(machineId);
	}

}
