package guru.springfamework.api.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Lice");
        customer.setLastName("Ray");

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(Long.valueOf(1L), customerDTO.getId());
        assertEquals("Lice", customerDTO.getFirstName());
        assertEquals("Ray", customerDTO.getLastName());
    }
}