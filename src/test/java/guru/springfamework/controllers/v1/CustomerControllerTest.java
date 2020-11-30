package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    private final static String FIRSTNAME1 = "Jim";
    private final static String LASTNAME1 = "John";
    private final static String FIRSTNAME2 = "Ork";
    private final static String LASTNAME2 = "Dick";

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName(FIRSTNAME1);
        customer1.setLastName(LASTNAME1);
        customer1.setId(1L);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstName(FIRSTNAME2);
        customer2.setLastName(LASTNAME2);
        customer2.setId(2L);

        List<CustomerDTO> customers = new LinkedList<>();
        customers.add(customer1);
        customers.add(customer2);

        //when
        when(customerService.getAllCustomers()).thenReturn(customers);

        //then
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));



    }

    @Test
    public void getByFirstName() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName(FIRSTNAME1);
        customer1.setLastName(LASTNAME1);
        customer1.setId(1L);

        //when
        when(customerService.getCustomerByFirstName(FIRSTNAME1)).thenReturn(customer1);

        //then
        mockMvc.perform(get("/api/v1/customers/Jim")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRSTNAME1)));
    }
}