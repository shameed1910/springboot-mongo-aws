package com.example.mongodb.springbootmongodb.controller;

import java.util.List;
import java.util.Optional;

import com.example.mongodb.springbootmongodb.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.mongodb.springbootmongodb.model.Employee;
import com.example.mongodb.springbootmongodb.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}


	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.ok(employeeRepository.findAll());
	}

	@GetMapping("employees/{id}")
	public ResponseEntity<Employee> findEmployeeById(@PathVariable(value = "id") Integer employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException("Employee not found" + employeeId));
		return ResponseEntity.ok().body(employee);
	}

	@PutMapping("employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Integer employeeId,
												   @RequestBody Employee employeeDetails) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		employee.setName(employeeDetails.getName());
		final Employee updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);

	}

	@DeleteMapping("employees/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable(value = "id") Integer employeeId) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException("Employee not found" + employeeId));
		employeeRepository.delete(employee);
		return ResponseEntity.ok().build();
	}


}
