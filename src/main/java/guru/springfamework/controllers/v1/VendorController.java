package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors";

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @GetMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable String id) {
        return vendorService.getVendorById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public VendorDTO putVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorByDTO(Long.valueOf(id), vendorDTO);
    }

    @PatchMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendor(Long.valueOf(id), vendorDTO);
    }

    @DeleteMapping("{id}")
    public void deleteVendor(@PathVariable String id) {
        vendorService.deleteVendorById(Long.valueOf(id));
    }
}
