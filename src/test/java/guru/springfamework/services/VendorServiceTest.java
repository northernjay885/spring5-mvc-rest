package guru.springfamework.services;

import guru.springfamework.api.v1.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class VendorServiceTest {

    private final static String VENDORNAME = "Yellow Jimmy Fan";
    private final static Long ID = 1L;
    private VendorService vendorService;

    @Mock
    private VendorRepository vendorRepository;



    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        vendorService = new VendorServiceImpl(vendorRepository, VendorMapper.INSTANCE);
    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(3, vendorDTOS.size());
    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(VENDORNAME);
        vendor.setId(1L);
        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(vendor));

        //when
        VendorDTO vendorDto =  vendorService.getVendorById(1L);

        //then
        assertEquals(VENDORNAME, vendorDto.getName());
        assertEquals("/api/v1/vendors/1", vendorDto.getVendorUrl());
    }

    @Test
    public void createNewVendor() {
        //given
        VendorDTO vendorDto = new VendorDTO();
        vendorDto.setName(VENDORNAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(1L);
        savedVendor.setName(vendorDto.getName());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO returnDto = vendorService.createNewVendor(vendorDto);

        //then
        assertEquals(returnDto.getName(), savedVendor.getName());
        assertEquals("/api/v1/vendors/1",returnDto.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(VENDORNAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setId(1L);
        savedVendor.setName(vendorDTO.getName());

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO returnDto = vendorService.saveVendorByDTO(ID, vendorDTO);

        //then
        assertEquals(returnDto.getName(), VENDORNAME);
        assertEquals("/api/v1/vendors/1", returnDto.getVendorUrl());
    }


    @Test
    public void deleteVendorById() {

        vendorRepository.deleteById(ID);

        Mockito.verify(vendorRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void patchVendorById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setName(VENDORNAME);
        vendor.setId(1L);

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Updated Name");

        Vendor savedVendor = new Vendor();
        savedVendor.setName("Updated Name");
        savedVendor.setId(1L);

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO patchedVendorDTO = vendorService.patchVendor(1L, vendorDTO);

        //then
        assertEquals("/api/v1/vendors/1", patchedVendorDTO.getVendorUrl());
        assertEquals("Updated Name", patchedVendorDTO.getName());

    }
}