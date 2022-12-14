package com.gl.studentManagement.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gl.studentManagement.entity.Student;

@Repository
public class StudentServiceImpl implements StudentService {
	private SessionFactory sessionFactory;

	// create session
	private Session session;

	@Autowired
	StudentServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
	}

	@Transactional
	public List<Student> findAll() {

		Transaction tx = session.beginTransaction();
		// find all th records from database table
		List<Student> student = session.createQuery("from Student").list();
		tx.commit();
		return student;
	}

	@Override
	public Student findById(int id) {
		Student student = new Student();

		Transaction tx = session.beginTransaction();

		// find record with Id from the db

		student = session.get(Student.class, id);
		tx.commit();

		return student;
	}

	@Transactional
	public List<Student> searchBy(String name, String country) {

		Transaction tx = session.beginTransaction();
		String query = "";
		if (name.length() != 0 && country.length() != 0)
			query = "from Student where name like '%" + name + "%' or country like '%" + country + "%'";
		else if (name.length() != 0)
			query = "from Student where name like '%" + name + "%'";
		else if (country.length() != 0)
			query = "from Student where country like '%" + country + "%'";
		else
			System.out.println("Cannot search without input data");

		List<Student> student = session.createQuery(query).list();

		tx.commit();

		return student;
	}

	// print the loop
	@Transactional
	public void print(List<Student> student) {

		for (Student b : student) {
			System.out.println(" Student List " + b);
		}
	}

	@Transactional
	public void save(Student theStudent) {
		Transaction tx = session.beginTransaction();
		// save transaction
		session.saveOrUpdate(theStudent);
		tx.commit();

	}

	@Transactional
	public void deleteById(int id) {
		Transaction tx = session.beginTransaction();
		// get transaction
		Student student = session.get(Student.class, id);

		// delete record

		session.delete(student);
		tx.commit();

	}

}
