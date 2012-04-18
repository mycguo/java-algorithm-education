package edu.jpa.projpa.ch2;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EmployeeTest {

    public static void main(String[] args) {
        EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("EmployeeService");
        
       
        
        EntityManager em = emf.createEntityManager();
        EmployeeService service = new EmployeeService(em);
        
        // find all employees and remove, so the test can be run again and again
        em.getTransaction().begin();
        Collection<Employee> emps = service.findAllEmployees();
        for (Employee e : emps) 
            em.remove(e);
        
        em.getTransaction().commit();
        
        
        //  create and persist an employee
        em.getTransaction().begin();
        Employee emp = service.createEmployee(158, "John Doe", 45000);
        em.getTransaction().commit();
        System.out.println("Persisted " + emp);
        
        // find a specific employee
        emp = service.findEmployee(158);
        System.out.println("Found " + emp);
        
        em.persist(emp);
        
        // find all employees
        emps = service.findAllEmployees();
        for (Employee e : emps) 
            System.out.println("Found Employee: " + e);
        
        // update the employee
        em.getTransaction().begin();
        emp = service.raiseEmployeeSalary(158, 1000);
        em.getTransaction().commit();
        System.out.println("Updated " + emp);

        // remove an employee
        em.getTransaction().begin();
        service.removeEmployee(158);
        em.getTransaction().commit();
        System.out.println("Removed Employee 158");        
        
        // close the EM and EMF when done
        em.close();
        emf.close();
    }
}
