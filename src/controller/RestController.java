package controller;

import java.util.List;

import services.DataServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import model.MachineStatus;
import model.Product;
import model.Status;

@Controller
@RequestMapping("/product")
public class RestController {

	@Autowired
	DataServices dataServices;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public String goHome(){
		return "index";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addProduct(@RequestBody Product p) {
		try {
			dataServices.addEntity(p);
			return new Status(1, "Product added Successfully !");
		} catch (Exception e) {
			return new Status(0, e.toString());
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Product getProduct(@PathVariable("id") long id) {
		try {
			Product p = dataServices.getEntityById(id);
			return p;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@RequestMapping(value = "/list/{machineId}", method = RequestMethod.GET)
	public @ResponseBody
	List<Product> getProduct(@PathVariable("machineId") int machineId) {
		List<Product> productList = null;
		try {
			productList = dataServices.getEntityListByMachineId(machineId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productList;
	}
	
	// positive "amount" variable means "refilling by company"
	// negative "amount" means "customer buys that product if available"
	@RequestMapping(value = "/update/{id}/{amount}/{machineId}", method = RequestMethod.GET)
	public @ResponseBody
	List<Product> updateProduct(@PathVariable("id") long id, @PathVariable("amount") int update_amount, @PathVariable("machineId") int machineId) {
		try {
			Product product_in_db = dataServices.getEntityById(id);
			if( (-1 * update_amount) <= product_in_db.getAmount() && product_in_db != null) {
				int new_amount = product_in_db.getAmount() - 1;
				dataServices.updateEntity(id, new_amount, machineId);
				MQTT m = new MQTT();
				m.connect();
				m.publishId(id, machineId);
				m.getClient().disconnect();
				return dataServices.getEntityListByMachineId(machineId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/updateAll/{machineId}", method = RequestMethod.GET)
	public @ResponseBody List<Product> updateAllProducts(@PathVariable("machineId") int machineId) {
		try {
			List<Product> productList = dataServices.getEntityList();
			dataServices.updateEntitiesToSpecificNumberWithMachineId(productList, machineId);
			return dataServices.getEntityListByMachineId(machineId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Status deleteProduct(@PathVariable("id") long id) {
		try {
			dataServices.deleteEntity(id);
			return new Status(1, "Product deleted Successfully !");
		} catch (Exception e) {
			return new Status(0, e.toString());
		}

	}
	
	@RequestMapping(value = "/temperature/{id}", method = RequestMethod.GET)
	public @ResponseBody List<MachineStatus> deleteProduct(@PathVariable("id") int machineId) {
		try {
			return dataServices.getTemperatureById(machineId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
