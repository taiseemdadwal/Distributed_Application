package program.Objects;



public class Doctor extends Base {
//	String fname;
//	String lname;
	String address;
	String location;
	String phone;
	String specialization;
	String recordID;
	//private HashMap<Doctor,Integer> listDoctors;

	public Doctor(String strFirstName, String strLastName, String address, String location, String phone, String specialization, String record ) {
		super(strFirstName,strLastName);
		this.address=address;
		this.location=location;
		this.phone=phone;
		this.specialization=specialization;
		this.recordID=record;
		// TODO Auto-generated constructor stub
	}
	
	public void setRecordID(String id)
	{
		this.recordID=id;
	}
	public String getRecordID(){
		return recordID;
	}


	public String getFname() 
	{
		return fname;
	}
	
	
	public void setFname(String fname)
	{
		this.fname=fname;
	}
	public  String getLname()
	{
		return lname;
	}
	public void setLname(String lname)
	{
		this.lname=lname;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address=address;
	}
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location=location;
	}
	public String getPhoneNumber()
	{
		return phone;
	}
	public void setPhoneNumber(String phone)
	{
		this.phone=phone;
	}
	public String getSpecialization()
	{
		return specialization;
	}
	public void setSpecialization(String specialization)
	{
		this.specialization=specialization;
	}
	

}
