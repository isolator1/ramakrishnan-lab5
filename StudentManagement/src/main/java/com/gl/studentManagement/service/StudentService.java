package com.gl.studentManagement.service;

import java.util.List;

import com.gl.studentManagement.entity.Student;


public interface StudentService {
	
	public List<Student> findAll();

	public Student findById(int theId);

	public void save(Student theStudents);

	public void deleteById(int theId);
	
	public List<Student> searchBy(String name, String country);

}
