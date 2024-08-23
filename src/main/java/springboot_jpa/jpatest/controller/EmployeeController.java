package springboot_jpa.jpatest.controller;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot_jpa.jpatest.model.Employee;
import springboot_jpa.jpatest.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@PostMapping("/insert")
	public ResponseEntity<Employee> insertEmployee(@RequestBody Employee emp){
		try {
			Employee employee = employeeRepository.save(new Employee(emp.getName(),emp.getDepartment(),emp.getGender()));
			return new ResponseEntity<>(employee,HttpStatus.CREATED);	
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/fetch/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable int id){
		Optional<Employee> empData = employeeRepository.findById(id);
		if(empData.isPresent()) 
			return new ResponseEntity<>(empData.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
	}
	
	@GetMapping("/fetchAll")
	public ResponseEntity<List<Employee>> getEmployeeList(){
		List<Employee> empList = new ArrayList<Employee>();
		employeeRepository.findAll().forEach(x -> empList.add(x));
		if(empList.size() > 0)
			return new ResponseEntity<> (empList,HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/updateById")
	public ResponseEntity<Employee> updateEmployeeById(@RequestParam int id, @RequestBody Employee employee){
		Optional<Employee> empData =  employeeRepository.findById(id);
		if(empData.isPresent()) {
			Employee emp = empData.get();
			emp.setName(employee.getName());
			emp.setDepartment(employee.getDepartment());
			emp.setGender(employee.getGender());
			return new ResponseEntity<>(employeeRepository.save(emp), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
	}
	
	@DeleteMapping("deleteById")
	public ResponseEntity<HttpStatus> deleteEmployeeById(@RequestParam int id){
		try {
			employeeRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}

