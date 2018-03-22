package dao;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import dao.TestMysql;
import model.MachineStatus;
import model.Product;

public class DataDaoImpl implements DataDao {

	Session session = null;
	Transaction tx = null;

	@Override
	@Transactional
	public boolean addEntity(Product p) throws Exception {
		tx = session.beginTransaction();
		session.save(p);
		tx.commit();
		session.close();
		return false;
	}

	@Override
	@Transactional
	public Product getEntityById(long id) throws Exception {
		return TestMysql.getEntityById(id);
	}

	@Override
	public List<Product> getEntityList() throws Exception {
		List<Product> p_list = TestMysql.getEntityList();
		return p_list;
	}
	
	@Override
	public boolean updateEntity(long id, int update_amount, int machineId) throws Exception {
		return TestMysql.updateEntity(id, update_amount, machineId);
	}
	
	@Override
	public boolean deleteEntity(long id)
			throws Exception {
		Object o = session.load(Product.class, id);
		tx = session.getTransaction();
		session.beginTransaction();
		session.delete(o);
		tx.commit();
		return false;
	}

	@Override
	public void updateEntitiesToSpecificNumberWithMachineId(List<Product> productList, int machineId) {
		System.out.println("DataDaoImpl size " + productList.size() + " " + machineId );
		for (int i = 0; i < productList.size(); i++) {
			Product product_in_db = productList.get(i);
			
			long product_machine_id = product_in_db.getMachineId();
			System.out.println("product_in_db_id " + String.valueOf(product_machine_id));
			try {
				if(product_machine_id == machineId) {
					System.out.println("DataDaoImpl updateEntitiesToSpecificNumberWithMachineId girdi");
					System.out.println("product_in_db amount: " + product_in_db.getAmount());
					// 20 is predefined, can be changed
					TestMysql.updateEntity(product_in_db.getId(), 20, machineId);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public List<Product> getEntityListByMachineId(int machineId) throws SQLException {
		return TestMysql.getEntityListByMachineId(machineId);
	}

	@Override
	public List<MachineStatus> getTemperatureById(int machineId) {
		return TestMysql.getTemperatureById(machineId);
	}
}