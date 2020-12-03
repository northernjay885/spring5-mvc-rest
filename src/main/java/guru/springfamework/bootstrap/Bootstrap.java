package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository,
                     CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories Loaded = " + categoryRepository.count());

        Customer customer1 = new Customer();
        customer1.setLastName("Ray");
        customer1.setFirstName("Terra");

        Customer customer2 = new Customer();
        customer2.setLastName("Evan");
        customer2.setFirstName("Lee");

        Customer customer3 = new Customer();
        customer3.setLastName("Wee");
        customer3.setFirstName("Queue");

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        System.out.println("Customers Loaded = " + customerRepository.count());

        Vendor vendor1 = new Vendor();
        vendor1.setName("Yellow Jimmy Fan");

        Vendor vendor2 = new Vendor();
        vendor2.setName("Rib and Steak");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        System.out.println("Vendors Loaded = " + vendorRepository.count());
    }
}
