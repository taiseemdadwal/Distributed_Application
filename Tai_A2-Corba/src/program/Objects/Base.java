package program.Objects;

public class Base {
	String fname;
	String lname;
	String recordID;
	private Doctor doctorRecord;
	private Nurse nurseRecord;

	public Base(String recordID, Doctor doctorRecord) {
		this.recordID = recordID;
		this.doctorRecord = doctorRecord;
	}
	public Base(String recordID, Nurse nRecord) {
		this.recordID = recordID;
		this.nurseRecord = nRecord;
	}
	public Doctor getDoctorRecord(){
		return doctorRecord;
	}
	public Nurse getNurseRecord(){
		return nurseRecord;
	}
	public Base(String frName, String lrName){
		this.fname=frName;
		this.lname=lrName;
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
	public String getRecordID() {
		// TODO Auto-generated method stub
		return recordID;
	}
	

}
