package guru.springfamework.services;

import guru.springfamework.api.v1.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    public VendorServiceImpl(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
                    return vendorDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        Optional<Vendor> vendorOpt = vendorRepository.findById(id);
        Vendor vendor;
        if (vendorOpt.isPresent()) {
            vendor = vendorOpt.get();
        } else {
            throw new ResourceNotFoundException();
        }
        VendorDTO returnDto = vendorMapper.vendorToVendorDTO(vendor);
        returnDto.setVendorUrl(getVendorUrl(id));
        return returnDto;
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        vendor.setId(id);
        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }
            VendorDTO returnDto = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
            returnDto.setVendorUrl(getVendorUrl(id));
            return returnDto;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        savedVendorDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));
        return savedVendorDTO;
    }

    private String getVendorUrl(Long id) {
        return "/api/v1/vendors" + "/" + id;
    }
}
