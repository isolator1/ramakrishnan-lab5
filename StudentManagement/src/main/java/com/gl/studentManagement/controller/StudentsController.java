package com.gl.studentManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gl.studentManagement.entity.Student;
import com.gl.studentManagement.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentsController {

	@Autowired
	private StudentService studentService;

	// add mapping for "/list"
	@RequestMapping("/list")
	public String listStudents(Model theModel) {
		// get Students from dbase
		System.out.println("request recieved");
		List<Student> theStudents = studentService.findAll();
		theModel.addAttribute("Students", theStudents);
		return "list-Students";
	}

	@RequestMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind form data
		Student theStudent = new Student();

		theModel.addAttribute("Students", theStudent);

		return "Student-form";
	}

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentId") int theId, Model theModel) {
		// get the student from the service
		Student theStudent = studentService.findById(theId);

		// set the student as a model to pre-populate the form
		theModel.addAttribute("Students", theStudent);

		return "Student-form";
	}

	@RequestMapping("/delete")
	public String delete(@RequestParam("studentId") int theId) {

		// delete the student
		studentService.deleteById(theId);

		// redirect to /students/list
		return "redirect:/student/list";

	}

	@PostMapping("/save")
	public String saveStudent(@RequestParam("id") int id, @RequestParam("name") String name,
			@RequestParam("course") String course, @RequestParam("country") String country) {

		System.out.println(id);
		Student theStudent;
		if (id != 0) {
			theStudent = studentService.findById(id);
			theStudent.setName(name);
			theStudent.setCourse(course);
			theStudent.setCountry(country);
		} else {
			theStudent = new Student(name, course, country);
		// save the student
		studentService.save(theStudent);
		}
		// use a redirect to prevent duplicate submissions
		return "redirect:/student/list";
		

	}

	@RequestMapping("/search")
	public String search(@RequestParam("name") String name, @RequestParam("country") String country, Model theModel) {

		// check names, if both are empty then just give list of all Students

		if (name.trim().isEmpty() && country.trim().isEmpty()) {
			return "redirect:/student/list";
		} else {
			// else, search by first name and last name
			List<Student> theStudents = studentService.searchBy(name, country);

			// add to the spring model
			theModel.addAttribute("Students", theStudents);

			// send to list-Students
			return "list-Students";

		}
	}

}
