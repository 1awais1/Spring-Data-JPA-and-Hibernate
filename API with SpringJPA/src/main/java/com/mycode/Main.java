package com.mycode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
@RequestMapping("api/v2/customers")
public class Main {
    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main (String[] args){
    SpringApplication.run(Main.class,args);
    System.out.println("Hello");
}
@GetMapping("/greet")
public GreetResponse greet (){

    GreetResponse response= new GreetResponse ("Hello","Hello",List.of("Java","React","flux"),new Person("Faizan","male",28,3000000.145));
return response;
}
record Person (String name, String sex, int age,double savings){

}
record GreetResponse (String Greet, String repeat, List<String> favProgLanguages, Person person ){}

    private final CustomerRepository customerRepository;
    @GetMapping
    public List<Customer> getCustomers(){
    return  customerRepository.findAll();
    }
record NewCustomerRequest(String name,String email,Integer age){}


 record DeleteCustomerRequest(Integer id,String name){}
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCustomer(@RequestBody DeleteCustomerRequest req){


        Customer customer = new Customer();
        customer.setId(req.id);
        customer.setName(req.name);
        Optional<Customer> optionalCustomer = customerRepository.findById(req.id());


        if (optionalCustomer.isPresent()) {
            Customer customerToDelete = optionalCustomer.get();

            // Check if the name matches (optional, depending on your use case)
            if (!customerToDelete.getName().equals(req.name)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer name does not match");
            }

            // Delete the customer
            customerRepository.delete(customerToDelete);
            return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }
    @PostMapping("/add")
    public void addCustomer(@RequestBody NewCustomerRequest req){
        Customer customer= new Customer();
        customer.setName(req.name);
        customer.setAge(req.age);
        customer.setEmail(req.email);
        customerRepository.save(customer);
    }

    record UpdateExistingCustomer(String name,Integer age,String email,Integer id){};

    @PutMapping("/update")
    public ResponseEntity<String> updateExistingCustomer(@RequestBody UpdateExistingCustomer req) {
        Optional<Customer> optionalCustomer = customerRepository.findById(req.id);

        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();

            // Update fields of the existing customer
            existingCustomer.setName(req.name);
            existingCustomer.setAge(req.age);
            existingCustomer.setEmail(req.email);

            // Save the updated customer
            customerRepository.save(existingCustomer);

            return ResponseEntity.status(HttpStatus.OK).body("Customer updated successfully");
        } else {
            System.out.println("Customer does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }
    }
}
