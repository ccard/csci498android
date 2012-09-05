/**
 * Chris Card
 * 8/28/12
 * This class stores a restaurant by its type name and address
 */
package csci498.ccard.lunchlist;

public class Restaurant {

	//class global variables
	private String name="";
	private String address="";
	private String type="";
	private int month;
	private int day;
	private int year;
	private String notes;
	
	//getters and setters
	
	public String getType() {
		return type;
	}
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public String getDateString()
	{
		return "Last visted on: "+month+"/"+day+"/"+year;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
	return(name);
	}
	
	public void setName(String name) {
	this.name=name;
	}
	
	public String getAddress() {
	return(address);
	}
	
	public void setAddress(String address) {
	this.address=address;
	}
	
	/**
	 * overides toString to return the name of the restaurant
	 */
	public String toString()
	{
		return(getName());
	}
}
