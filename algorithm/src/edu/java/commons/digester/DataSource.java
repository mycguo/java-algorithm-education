package edu.java.commons.digester;

public class DataSource
{
  private String name;
  private String driver;
  private String url;
  private String password;
  private String userName;

  public DataSource(String name, String driver, String url, String userName, String password)
  {
    this.name = name;
    this.driver = driver;
    this.url = url;
    this.userName = userName;
    this.password = password;
  }

  public String getName()
  {
    return name;
  }

  public String getDriver()
  {
    return driver;
  }

  public String getURL()
  {
    return url;
  }

  public String getPassword()
  {
    return password;
  }

  public String getUserName()
  {
    return userName;
  }
}