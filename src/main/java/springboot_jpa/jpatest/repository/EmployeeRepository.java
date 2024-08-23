package springboot_jpa.jpatest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import springboot_jpa.jpatest.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	

}
