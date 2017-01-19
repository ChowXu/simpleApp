package service;

import model.Customer;
import org.junit.Assert;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;

/**
 * Project: simpleApp
 * Author: Chow xi
 * Email: zhouxu_1994@163.com
 * Time: 17/1/17 下午3:56
 */
public class CustomerServiceTest {

    private CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    /**
     * init database
     */
    @Before
    public void init() {

    }

    @org.junit.Test
    public void getCustomer() throws Exception {
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);

    }

    @org.junit.Test
    public void getCustomers() throws Exception {
        List<Customer> lists = customerService.getCustomers();
        Assert.assertEquals(2, lists.size());

    }

    @org.junit.Test
    public void deleteCustomer() throws Exception {
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }

    @org.junit.Test
    public void updateCustomer() throws Exception {
        long id = 1;
        HashMap<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("name", "weiyu");
        boolean result = customerService.updateCustomer(id, fieldMap);
        Assert.assertTrue(result);
    }

    @org.junit.Test
    public void addCustomer() throws Exception {
        HashMap<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("name", "chown");


    }

}