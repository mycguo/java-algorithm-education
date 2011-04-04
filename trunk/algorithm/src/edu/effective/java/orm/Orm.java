package edu.effective.java.orm;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * http://www.javaworld.com/javaworld/jw-07-2008/jw-07-orm-comparison.html?page=6
 * http://www.vogella.de/articles/JavaPersistenceAPI/article.html
 * @author cg
 *
 */
public class Orm {
	  private EntityManagerFactory emf;
	  private EntityManager em;
	  private String PERSISTENCE_UNIT_NAME = "EmployeePU";
	  public static void main(String[] args) {
	      try      {
	          Orm main = new Orm();
	          main.initEntityManager();
	          main.create();
	          System.out.println("Employee successfully added");
	          main.closeEntityManager();
	      }
	      catch(Exception ex)      {
	          System.out.println("Error in adding employee");
	          ex.printStackTrace();
	      }
	  }
	 private void initEntityManager()  {
	 emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	         em = emf.createEntityManager();
	  }
	  private void closeEntityManager()   {
	      em.close();    emf.close(); }
	 private void create() {
	      em.getTransaction().begin();
	      Employee employee=new Employee(100);
	      employee.setEmpFirstname("bob");
	      employee.setEmpLastname("smith");
	      em.persist(employee);
	      em.getTransaction().commit();
	    }
	}