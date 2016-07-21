package program.Objects;

public class Nurse extends Base{
	//String fname;
	//String lname;
	String designation;
	//String location;
	String status;
	String statusDate;
	String recordID;
	/*public Nurse(String fname2, String lname2) {
		this.fname=fname2;
		this.lname=lname2;
		// TODO Auto-generated constructor stub
	}
	*/
	public Nurse(String strFirstName, String strLastName, String designation, String status, String statusDate, String record) {
		super(strFirstName,strLastName);
		//this.location=location;
		this.designation=designation;
		this.status=status;
		this.statusDate=statusDate;
		this.recordID=record;
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
	public String getLname()
	{
		return lname;
	}
	public void setLname(String lname)
	{
		this.lname=lname;
	}
	public String getDesignation()
	{
		return designation;
	}
	public void setDesignation(String designation)
	{
		this.designation=designation;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	public String getStatusDate()
	{
		return statusDate;
	}
	public void setStatusDate(String statusDate)
	{
		this.statusDate=statusDate;
	}
	

}
