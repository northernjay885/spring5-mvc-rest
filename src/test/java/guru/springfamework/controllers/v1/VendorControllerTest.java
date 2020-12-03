package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest extends AbstractRestControllerTest{

    private final static String NAME = "Yellow Jimmy Fan";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllVendors() throws Exception {
        //given
        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName(NAME);
        vendorDTO1.setVendorUrl(VendorController.BASE_URL + "/" + "1");

        VendorDTO vendorDTO2 = new VendorDTO();
        vendorDTO2.setName("Rib and Steak");
        vendorDTO2.setVendorUrl(VendorController.BASE_URL + "/" + "2");

        List<VendorDTO> vendorDTOList = new LinkedList<>();
        vendorDTOList.add(vendorDTO1);
        vendorDTOList.add(vendorDTO2);
        //when
        when(vendorService.getAllVendors()).thenReturn(vendorDTOList);
        //then
        mockMvc.perform(get(VendorController.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));

    }

    @Test
    public void getVendorById() throws Exception{
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        //then
        mockMvc.perform(get(VendorController.BASE_URL + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));

    }

    @Test
    public void createVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        VendorDTO returnDto = new VendorDTO();
        returnDto.setName(NAME);
        returnDto.setVendorUrl(VendorController.BASE_URL + "/" + "1");

        //when
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(returnDto);

        //then
        mockMvc.perform(post(VendorController.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url",
                        equalTo(VendorController.BASE_URL + "/" + "1")));
    }

    @Test
    public void putVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        VendorDTO returnDto = new VendorDTO();
        returnDto.setName(NAME);
        returnDto.setVendorUrl(VendorController.BASE_URL + "/" + "1");

        //when
        when(vendorService.saveVendorByDTO(1L, vendorDTO)).thenReturn(returnDto);

        //then
        mockMvc.perform(put(VendorController.BASE_URL + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url",
                        equalTo(VendorController.BASE_URL + "/" + "1")));
    }

    @Test
    public void patchVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Updated Name");

        VendorDTO returnDto = new VendorDTO();
        returnDto.setName("Updated Name");
        returnDto.setVendorUrl(VendorController.BASE_URL + "/" + "1");

        //when
        when(vendorService.patchVendor(1L, vendorDTO)).thenReturn(returnDto);

        //then
        mockMvc.perform(patch(VendorController.BASE_URL + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Updated Name")))
                .andExpect(jsonPath("$.vendor_url",
                        equalTo(VendorController.BASE_URL + "/" + "1")));
    }

    @Test
    public void deleteVendor() throws Exception {

        mockMvc.perform(delete(VendorController.BASE_URL + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception {

        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(VendorController.BASE_URL + "/" + "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}