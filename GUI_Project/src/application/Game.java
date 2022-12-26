package application;

public class Game
{
	private String name;
	private String size;
	private int price;
	private String publisher;
	private String developer;
	private String minRamReq;
	private String minGraphCardReq;
	private String info;
	
	public Game(String name, String size, int price, String publisher, String developer, String minRamReq,String minGraphCardReq, String info)
	{
		this.name = name;
		this.size = size;
		this.price = price;
		this.publisher = publisher;
		this.developer = developer;
		this.minRamReq = minRamReq;
		this.minGraphCardReq = minGraphCardReq;
		this.info = info;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getSize()
	{
		return size;
	}
	public void setSize(String size)
	{
		this.size = size;
	}
	public int getPrice()
	{
		return price;
	}
	public void setPrice(int price)
	{
		this.price = price;
	}
	public String getPublisher()
	{
		return publisher;
	}
	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}
	public String getDeveloper()
	{
		return developer;
	}
	public void setDeveloper(String developer)
	{
		this.developer = developer;
	}
	public String getMinRamReq()
	{
		return minRamReq;
	}
	public void setMinRamReq(String minRamReq)
	{
		this.minRamReq = minRamReq;
	}
	public String getMinGraphCardReq()
	{
		return minGraphCardReq;
	}
	public void setMinGraphCardReq(String minGraphCardReq)
	{
		this.minGraphCardReq = minGraphCardReq;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
}
