package service;

import model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.DatabaseHelper;

import java.util.HashMap;
import java.util.List;

/**
 * Project: simpleApp
 * Author: Chow xi
 * Email: zhouxu_1994@163.com
 * Time: 17/1/17 下午3:33
 */
public class CustomerService {



    /**
     * get customer
     */
    public Customer getCustomer(long id) {
        String sql = "select * from customer where id = ?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }


    /**
     * get customer list
     */
    public List<Customer> getCustomers() {

        String sql = "select * from customer";
        List<Customer> results = DatabaseHelper.queryEntityList(Customer.class,sql);
        return results;
    }


    /**
     * delete customer
     */

    public boolean deleteCustomer(long id) {

        return DatabaseHelper.deleteEntity(Customer.class, id);

    }

    /**
     * update customer
     */
    public boolean updateCustomer(long id, HashMap<String, Object> updateField) {
        return false;
    }


    /**
     * add customer
     */
    public boolean addCustomer(HashMap<String, Object> addField) {
        return false;
    }


}
