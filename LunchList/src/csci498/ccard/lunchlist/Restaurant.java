/**
 * Chris Card
 * 8/28/12
 * This class stores a restaurant by its type name and address
 */
package csci498.ccard.lunchlist;

public class Restaurant 
{

	private String name = "";
	private String address = "";
	private String type = "";
	private String notes = "";
	
	
	public String getType() 
	{
		return type;
	}
	
	public String getNotes() 
	{
		return notes;
	}

	public void setNotes(String notes) 
	{
		this.notes = notes;
	}

	public void setType(String type) 
	{
		this.type = type;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getAddress() 
	{
		return address;
	}
	
	public void setAddress(String address) 
	{
		this.address = address;
	}
	
	/**
	 * overides toString to return the name of the restaurant
	 */
	public String toString()
	{
		return getName();
	}
}
