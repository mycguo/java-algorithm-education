package edu.java.commons.digester;

import java.io.IOException;
import java.util.Hashtable;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

public class SampleDigester
{
  private Hashtable<String, DataSource> dataSources = new Hashtable<String, DataSource>();

  public static void main(String[] args)
  {
    SampleDigester sample = new SampleDigester();

    try
    {
      sample.run();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public void run() throws IOException, SAXException
  {
    Digester digester = new Digester();

    digester.push(this);

    digester.addCallMethod("datasources/datasource", "addDataSource", 5 );
    digester.addCallParam("datasources/datasource/name", 0);
    digester.addCallParam("datasources/datasource/driver", 1);
    digester.addCallParam("datasources/datasource/url", 2);
    digester.addCallParam("datasources/datasource/username", 3);
    digester.addCallParam("datasources/datasource/password", 4);


    digester.parse("src/edu/java/commons/digester/datasource.xml");
  }

  public void addDataSource(String name,
                            String driver,
                            String url,
                            String userName,
                            String password)
  {
    DataSource dataSource = new DataSource(name, driver,url, userName, password);
    dataSources.put(name, dataSource);
    System.out.println("DataSource added: " + name);
  }
}