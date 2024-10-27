package com.example.User_Service_Jwt.repository;

import com.example.User_Service_Jwt.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
