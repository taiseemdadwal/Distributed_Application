package program.Server;
import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;


import Utility.ValidateInput;
import java.util.Iterator;




import program.Objects.Base;
import program.Objects.Doctor;
import program.Objects.Nurse;
import clinic.Clinic;
import clinic.ClinicHelper;
import clinic.ClinicPOA;

public class ClinicServer extends ClinicPOA implements Runnable{
	//private HashMap<Character,HashMap<String, ArrayList<Base>> >outerRecords = new HashMap<Character,HashMap<String, ArrayList<Base>>>();
	private HashMap<Character, ArrayList<Base>> tableRecords = new HashMap<Character, ArrayList<Base>>();
	private ArrayList<Base> RECORD_LIST = null;
	private static int dr=1000;
	private Logger logger;
	private String ClinicName;
	private int udpPort;
	private ServerThread socket;
	private static ArrayList<ClinicServer> ClinicServers = null;
	private int ncount=0;
	private int dcount=0;
	public ClinicServer(String clinic, int iPortNum) {
		// TODO Auto-generated constructor stub
			// TODO Auto-generated constructor stub
			this.ClinicName = clinic;
			this.udpPort = iPortNum;
			this.setLogger("./logs/clinic/"+ClinicName+".txt");
			//insertInitialData();

			
	}
	public ClinicServer(String clinic) {
		// TODO Auto-generated constructor stub
			// TODO Auto-generated constructor stub
			this.ClinicName = clinic;
			this.setLogger("./logs/clinic/"+ClinicName+".txt");

			
	}

	public ClinicServer() {
	}
	public int getUDPPort()
	{
		return this.udpPort;
	}
	private void setLogger(String location) {
		try{
			this.logger = Logger.getLogger(this.ClinicName);
			FileHandler fileTxt = new FileHandler(location);
			SimpleFormatter formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			this.logger.addHandler(fileTxt);
		}
		catch(Exception err) {
			System.out.println("Couldn't Initiate Logger. Please check file permission");
		}
	}
	

	public boolean createDRecord(String id, String fname, String lname, String address, String phone, String specialization,
			String location) {
		// TODO Auto-generated method stub
		String  dRecord;
		String manager=id;
		Base record = null;
		
		char firstChar= lname.charAt(0);
		if(tableRecords.containsKey(firstChar)){
			RECORD_LIST = tableRecords.get(firstChar);
		}else{
			RECORD_LIST = new ArrayList<Base>();
		}
		dRecord="DR"+ Integer.toString(++dr);
		Doctor doc_record = new Doctor(fname, lname, address, phone, specialization, location, dRecord);
		record = new Base(dRecord, doc_record);
		
		synchronized (this) {
			this.RECORD_LIST.add(record);
			tableRecords.put(firstChar,this.RECORD_LIST);
		}
		
		this.logger.info("New Doctor added to the.." +this.ClinicName +"Clinic with following details Record ID : "+dRecord+"  Name:"+doc_record.getFname());
		System.out.println("Successfully created Doctor record- Record-ID "+dRecord+"  by -"+manager);
		this.dcount++;
		return true;
	}
	public boolean createNRecord(String id,String fname, String lname, String designation, String status, String statusDate) {
	
		String nRecord="NR"+Integer.toString(++dr);
		String manager=id;
		//Base objNurse= new Nurse(fname,lname,designation,status,statusDate, nRecord);
		Base record = null;
		//nRecord="NR"+ Integer.toString(++dr);
		char firstChar= lname.charAt(0);
		if(tableRecords.containsKey(firstChar)){
			RECORD_LIST = tableRecords.get(firstChar);
		}else{
			RECORD_LIST = new ArrayList<Base>();
		}
		Nurse nur_record= new Nurse(fname,lname,designation,status,statusDate, nRecord);
		//Doctor doc_record = new Doctor(fname, lname, address, phone, specialization, location, dRecord);
		record = new Base(nRecord, nur_record);
		synchronized (this) {
			RECORD_LIST.add(record);
			tableRecords.put(firstChar,this.RECORD_LIST);
		}
		
			this.logger.info("New Nurse added to the.." +this.ClinicName +"by The Manager : "+manager+"Cilinic with Record ID : "+nRecord+"  Name:"+nur_record.getFname());

		
		System.out.println("Successfully created Nurse record with Nurse-ID--"+nRecord+"  Name:"+nur_record.getFname());
		this.ncount++;
		return true;
	}
	
/*
	public int recordCount(String recordType) {
		// TODO Auto-generated method stub
		return 0;
	}
*/
	public boolean editRecord(String id,String recordID, String fieldName, String newValue) {
		// TODO Auto-generated method stub
		String managerId=id;
		boolean flag=false;
		for(Map.Entry<Character, ArrayList<Base>> entry:tableRecords.entrySet()){
			for(Base record:entry.getValue()){
				
				if(recordID.equalsIgnoreCase(record.getRecordID())){
					synchronized(record){
					flag=true;
					if(recordID.contains("DR")||recordID.contains("dr")){
						if(fieldName.equalsIgnoreCase("Address")){
							record.getDoctorRecord().setAddress(newValue);
							this.logger.info("Manager: "+ managerId +  "from : "+this.ClinicName +" edited the Address of Doctor Record: "+ "\n" +  newValue);
							return true;
						}
						else if(fieldName.equalsIgnoreCase("Phone")){
							record.getDoctorRecord().setPhoneNumber(newValue);
							this.logger.info("Manager: "+ managerId +   "from : "+this.ClinicName +" edit the phone of Doctor Record: "+ "\n" +  newValue);
						return true;
						}
						else if (fieldName.equalsIgnoreCase("Location")){	
							ValidateInput v=new ValidateInput();
							String value=v.validateLocation(newValue);
							record.getDoctorRecord().setLocation(value);
							this.logger.info("Manager: "+ managerId +  "from : "+this.ClinicName + " edit the Location of Doctor Record: "+ "\n" +  value);
							return true;
						}
					}
					 else if(recordID.contains("NR")||recordID.contains("nr")){
						if(fieldName.equalsIgnoreCase("Designation")){							
							record.getNurseRecord().setDesignation(newValue);
							this.logger.info("Manager: "+ managerId +  "from : "+this.ClinicName + " edit the Designation of Nurse Record: "+ "\n" +  newValue);
							return true;
						}
						else if(fieldName.equalsIgnoreCase("Status")){
								record.getNurseRecord().setStatus(newValue);
								this.logger.info("Manager: "+ managerId + "from : "+this.ClinicName +" edit the Status of Nurse Record: "+ "\n" + newValue);
								return true;
							}
							else if (fieldName.equalsIgnoreCase("statusDate")){
								record.getNurseRecord().setStatusDate(newValue);
								this.logger.info("Manager: "+ managerId + " edit the Status date of Nurse Record: "+ "\n" + newValue);
								return true;
							}
							
					 	}
					}
				  }
				}
			}
	
		return flag;
	}
	
	public String accessRecord(String id, String recordID){
	    boolean found=false;
		//System.out.println(( (Doctor) tableDoctors.get(recordID)).getFname());
		String data="";
		String managerId=id;
		for(Map.Entry<Character, ArrayList<Base>> entry:tableRecords.entrySet()){
			for(Base record:entry.getValue()){
				synchronized(record){
				if(recordID.equalsIgnoreCase(record.getRecordID())){
					found=true;
					if(recordID.contains("DR")||recordID.contains("dr")){
						data="First Name: "+record.getDoctorRecord().getFname()+"\n Last Name"+record.getDoctorRecord().getLname()+"\nAddress:"+record.getDoctorRecord().getAddress()+"\nLocation: "+	record.getDoctorRecord().getLocation()+"\nSpecialization"+record.getDoctorRecord().getSpecialization();
						this.logger.info("Manager: "+ managerId +  "from : "+this.ClinicName +" accessed the Doctor Record: "+ "\n" + recordID);
					}
						
					
					if(recordID.contains("NR")||recordID.contains("nr")){
						 data="First Name: "+record.getNurseRecord().getFname()+"\n Last name: "+	record.getNurseRecord().getLname()+"\n Status: "+record.getNurseRecord().getStatus()+"\nStatus Date: "+	record.getNurseRecord().getStatusDate()+"\nDesignation: "+record.getNurseRecord().getDesignation();
						 this.logger.info("Manager: "+ managerId +  "from : "+this.ClinicName +" accessed the Nurse Record: "+ "\n" + recordID);
					  }
				 
				}
			  }
			}
		}
	if(!found)
		data="No such record found";

	return data;
	}
	
	public String recordCount(String id,String rtype) 
	{
		String response = "";
		response += getCountByServer(rtype);
		//String[] args = null;

		for(ClinicServer clinicServer : ClinicServers)
		{
			
			
			if(this.ClinicName!=clinicServer.ClinicName)
			{
				
				
				
				try 
				{
					DatagramSocket socket = null;
					try
					{
						socket = new DatagramSocket();
						byte[] msgOut = ("RecordType:"+rtype).getBytes();
						java.net.InetAddress host = java.net.InetAddress.getByName("localhost");
						int ServerPort = clinicServer.getUDPPort();
						DatagramPacket request = new DatagramPacket(msgOut, msgOut.length,host,ServerPort);
						socket.send(request);

						byte[] msgIn = new byte[10000];
						DatagramPacket reply = new DatagramPacket(msgIn, msgIn.length);
						socket.receive(reply);
						response+=new String(reply.getData());
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					finally
					{
						socket.close();
					}

				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		return response;
		
		
	}
		

	public String getCountByServer(String rtype){
		

			StringBuilder sbCountList = new StringBuilder();
			sbCountList.append("\n"+ClinicName+":: ");
			if(rtype.equalsIgnoreCase("NR"))
				sbCountList.append(ncount);
			else if(rtype.equalsIgnoreCase("dr"))
				sbCountList.append(dcount);

			return sbCountList.toString();
		}

		
	public boolean transferRecord(String managerID, String recordID,String remoteS) {
		boolean flag=false;
		for(Map.Entry<Character, ArrayList<Base>> entry:tableRecords.entrySet()){
			for(Base record:entry.getValue()){
				
				if(recordID.equalsIgnoreCase(record.getRecordID())){
					
					synchronized(record){
					for(ClinicServer clinicServer : ClinicServers)
					{
						if((clinicServer.ClinicName).equalsIgnoreCase(remoteS)){
							
							
							if(recordID.contains("DR")||recordID.contains("dr")){
								flag=clinicServer.createDRecord(managerID,record.getDoctorRecord().getFname(), record.getDoctorRecord().getLname(),record.getDoctorRecord().getAddress(),record.getDoctorRecord().getLocation(),record.getDoctorRecord().getPhoneNumber(),record.getDoctorRecord().getSpecialization());
								this.dcount--;
								this.logger.info("Manager: "+ managerID +  "from : "+this.ClinicName +" transferred the record :"+recordID+"+ to: "+ "\n" + remoteS.toUpperCase()+" server");
							}
								
							
							if(recordID.contains("NR")||recordID.contains("nr")){
								 flag=clinicServer.createNRecord(managerID,record.getNurseRecord().getFname(),	record.getNurseRecord().getLname(),record.getNurseRecord().getDesignation(),record.getNurseRecord().getStatus(),record.getNurseRecord().getStatusDate());
								 this.ncount--;
								 this.logger.info("Manager: "+ managerID +  "from : "+this.ClinicName +" transferred the nurse record:"+recordID+"+ to: "+ "\n" + remoteS.toUpperCase()+" server");
							  }
						 
					       }
						}
				     }
				  }
				synchronized(record){
				if(flag)	

					tableRecords.values().remove(record);
					
					
				
				}
				}
			}
					

		return flag;
	}



	public static void main(String args[]) {
				
		ClinicServer Server1 = new ClinicServer("MTL",50001);
		ClinicServer Server2 = new ClinicServer("LVL",50002);
		ClinicServer Server3 = new ClinicServer("DDO",50003);
		ClinicServers= new ArrayList<ClinicServer>();
		ClinicServers.add(Server1);
		ClinicServers.add(Server2);
		ClinicServers.add(Server3);
		String[] args1 = null;
		ORB orb = ORB.init(args1,null);
		POA rootPOA = null;
		for(ClinicServer clinicServer : ClinicServers)
		{

			new Thread(clinicServer).start();
			System.out.println(clinicServer.ClinicName + " Server is running!");	

			//Initialize ORB 
			try {
				rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));						
			} catch (InvalidName e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rootPOA.the_POAManager().activate();
			} catch (AdapterInactive e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Create servant and register it with ORB
			//Obtain a reference			
			byte[] id = null;					
			try {
				id = rootPOA.activate_object(clinicServer);
			} catch (ServantAlreadyActive | WrongPolicy e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			org.omg.CORBA.Object ref = null;
			try {
				ref = rootPOA.id_to_reference(id);
			} catch (ObjectNotActive | WrongPolicy e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Translate to ior and write it to a file
			String ior = orb.object_to_string(ref);
			System.out.println(ior);

			//Initialize the ORB 
			PrintWriter file = null;
			try {
				file = new PrintWriter("./Servers/"+clinicServer.ClinicName+".txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			file.println(ior);
			file.close();


		}

		try {
			rootPOA.the_POAManager().activate();
		} catch (AdapterInactive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orb.run();

	}

	private static int i=1;
	public void insertInitialData()
	{	
		
		if(this.ClinicName == "MTL")
		{
			this.createDRecord("mtl0001","Taiseem","ahmed","montreal","mtl","9232372841","doctor");
			this.createNRecord("mtl0001","Ana","taylor","senior","Active","Since 1995");
		}
		if(this.ClinicName == "LVL")
		{
			this.createDRecord("lvl001","Vaseem","ahmed","Vancouver","lvl","9232372841","doctor");
			this.createNRecord("lvl001","Ima","Dadwal","Junior","Active","Since 2016");

		}

		if(this.ClinicName == "DDO")
		{
			this.createDRecord("ddo0001","Karun","Sharma","Montreal","ddo","9953816182","doctor");
			
		}		
		
	}

	public void run()
	{

		this.socket = new ServerThread(this);
		socket.start();	


	}

	public Clinic getServerObject(String[] args, String name) throws Exception 
	{
		ORB orb = ORB.init(args, null);
		BufferedReader br = new BufferedReader(new FileReader("./Servers/"+name+".txt"));
		String ior = br.readLine();
		br.close();

		org.omg.CORBA.Object o = orb.string_to_object(ior);
		return ClinicHelper.narrow(o);		
	}


}