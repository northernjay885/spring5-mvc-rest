package guru.springfamework.api.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VendorMapperTest {

    VendorMapper vendorMapper;

    @Before
    public void setUp() throws Exception {
        vendorMapper = VendorMapper.INSTANCE;
    }

    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setName("Yellow Jimmy Fan");
        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        //then

        assertEquals("Yellow Jimmy Fan", vendorDTO.getName());


    }

    @Test
    public void vendorDTOtoVendor() {
    }
}