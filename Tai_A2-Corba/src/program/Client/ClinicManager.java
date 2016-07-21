package program.Client;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import org.omg.CORBA.ORB;
import clinic.Clinic;
import clinic.ClinicHelper;
import Utility.ValidateInput;
public class ClinicManager extends Client{
	static Clinic MontrealServer;
	static Clinic LavalServer;
	static Clinic DollardServer;
	static final String Montreal="MTL", Laval="LVL", Dollard="DDO";
	public static String clinic;
	protected static String clinicName;
	private String[] args;
	public ArrayList<String> clientID= new ArrayList<String>();

	public static String id;
	public static boolean loginMenu(ClinicManager objClient, boolean flag){
		System.out.println("Please enter the Client ID");
		Scanner sc= new Scanner(System.in);
		id= objClient.clientIDValidation(sc);
		
		
		for(int i=0;i<objClient.clientID.size();i++){
			if(id.equalsIgnoreCase(objClient.clientID.get(i)))	
			{
				flag=true;
				System.out.println("You have successfully logged in with id:"+objClient.clientID.get(i)+"  on the--"+objClient.clientID.get(i).substring(0,3)+"--server");
				clinic=objClient.clientID.get(i).substring(0,3);
				break;
			}
		}
		return flag;
		
	}
	
	public static void showMenu(){
		System.out.println("Please enter your choice");
		System.out.println("1. Create Doctor Record");
		System.out.println("2. Create Nurse Record");
		System.out.println("3. Edit Record");
		System.out.println("4. View Record");
		System.out.println("5. Get Count");
		System.out.println("6. Transfer a record");
		System.out.println("7. Login into another clinic server");

		System.out.println("8. To Exit");
	}
	
	public Clinic initializeServer(String args[], String name) throws Exception
	{
		ORB orb = ORB.init(args, null);

		BufferedReader br = new BufferedReader(new FileReader("./Servers/"+name+".txt"));
		String ior = br.readLine();
		br.close();
		
		org.omg.CORBA.Object o = orb.string_to_object(ior);
		return ClinicHelper.narrow(o);		

	}
	
	public Clinic ServerValidation(String strClinicName)
	{
		boolean valid = false;
		Clinic server = null;
		while(!valid)
		{
			try{
				clinicName = strClinicName;
				server = LocateServer(clinicName);
				if(server != null) {
					valid=true;
				}
				else {
					System.out.println("Invalid Clinic Location");
				}
			}
			catch(Exception e)
			{
				System.out.println("Invalid Clinic Location");
				valid=false;
			}
		}
		return server;
	}
		// TODO Auto-generated constructor stub
	
	public static Clinic LocateServer(String id) {
		String clinic=id;
		if(clinic.equalsIgnoreCase("mtl")) {
			return MontrealServer;
		}
		else if(clinic.equalsIgnoreCase("lvl")) {
			return LavalServer;
		}
		else if(clinic.equalsIgnoreCase("ddo")) {
			return DollardServer;
		}
		return null;
	}
	
	
	@SuppressWarnings("null")
	public static void main(String args[]) throws Exception{
		ClinicManager objClient= new ClinicManager();
		objClient.args=args;
		MontrealServer = objClient.initializeServer(args, "MTL");
		LavalServer = objClient.initializeServer(args, "LVL");
		DollardServer = objClient.initializeServer(args, "DDO");


		Clinic objServer=null;
		objClient.clientID.add("MTL0001");
		objClient.clientID.add("MTL0002");
		objClient.clientID.add("LVL0001");
		objClient.clientID.add("LVL0002");
		objClient.clientID.add("DDO0001");
		objClient.clientID.add("DDO0002");
//		ClinicServerInterface objServer = null;
		
		boolean success=false;

		Integer userInput=0;
		boolean flag=false;
		flag=loginMenu(objClient,flag);
		
		while(flag){
			showMenu();
			Scanner keyboard= new Scanner(System.in);
			userInput=Integer.parseInt(objClient.InputStringValidation(keyboard));
			objServer = objClient.ServerValidation(clinic);
			
			switch(userInput){
			// Creates Doctor record
			case 1:
				System.out.println("Enter first name");
				String fname= objClient.InputStringValidation(keyboard);
				System.out.println("last Name");
				String lname= objClient.InputStringValidation(keyboard);
				System.out.println("Address");
				String address = objClient.InputStringValidation(keyboard);
				ValidateInput v=new ValidateInput();
				System.out.println("Location");
			//	String location = objClient.InputStringValidation(keyboard);
				String location= v.validateLocation(keyboard.nextLine().toString());
				System.out.println("phone");
			//  ValidateInput v=new ValidateInput();
				String phone= v.validatePhNo(keyboard.nextLine().toString());
				System.out.println("Designation");
				String designation=objClient.InputStringValidation(keyboard);
				
				success=objServer.createDRecord(id,fname,lname,address,location,phone,designation);
				if(success){
					System.out.println("Success");
					try(FileWriter fw = new FileWriter("./logs/"+id+".txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw))
						{
						    out.println("The doctor Record for:"+ fname +"  added");
						} catch (IOException e) {
							e.printStackTrace();
						}	
					
				}
				else{
					try(FileWriter fw = new FileWriter("./logs/"+id+".txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw))
						{
						    out.println("The doctor record could not be created...sorry!!");
						} catch (IOException e) {
							e.printStackTrace();
						}	
					
				}
				
				
				break;

				//Creates Nurse Record
			case 2: 

				System.out.println("Enter first name");
				String nfname= objClient.InputStringValidation(keyboard);
				System.out.println("last Name");
				String nlname= objClient.InputStringValidation(keyboard);
				System.out.println("Designation");
				String ndesignation=objClient.InputStringValidation(keyboard);
			
				System.out.println("Status- Active/Terminated");
				String status = objClient.InputStringValidation(keyboard);
				System.out.println("Date since active");
				String statusDate = objClient.InputStringValidation(keyboard);

				success=objServer.createNRecord(id,nfname,nlname,ndesignation,status,statusDate);
				if(success){
					System.out.println("Success");
					try(FileWriter fw = new FileWriter("./logs/"+id+".txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw))
						{
						    out.println("The nurse Record for:"+ nlname +"  added");
						} catch (IOException e) {
							e.printStackTrace();
						}	
					
				}
				else{
					try(FileWriter fw = new FileWriter("./logs/"+id+".txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw))
						{
						    out.println("The nurse record not created...sorry!!");
						} catch (IOException e) {
							e.printStackTrace();
						}	
					
				}
				
				break;
				
				//To edit a record
				
			case 3:
				
				System.out.println("Enter RecordID");
				String recordID= objClient.InputStringValidation(keyboard);
				System.out.println("Which field value to update? ");
				String fieldName= objClient.InputStringValidation(keyboard);
				System.out.println("Input new value");
				String fieldValue = objClient.InputStringValidation(keyboard);

				success=objServer.editRecord(id, recordID, fieldName, fieldValue);
				if(success){
					System.out.println("Success");
					try(FileWriter fw = new FileWriter("./logs/"+id+".txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw))
						{
						    out.println("The nurse Record for:"+ recordID +" field :"+ fieldName+ "chnaged to :"+fieldValue );
						} catch (IOException e) {
							e.printStackTrace();
						}	
					
				}
				else{
					try(FileWriter fw = new FileWriter("./logs/"+id+".txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw))
						{
						    out.println("Sorry edit operation was unsuccessfull for:"+recordID +"...sorry!!");
						} catch (IOException e) {
							e.printStackTrace();
						}	
					
				}
				
				break;
				
				
			//To access a record	
			case 4:
				System.out.println("Enter RecordID");
				
				String recordId= objClient.InputStringValidation(keyboard);

				String info=objServer.accessRecord(id,recordId);
				System.out.println(info);
				if(success){
					System.out.println("Success");
					try(FileWriter fw = new FileWriter("./logs/"+id+".txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw))
						{
						    out.println("The accesses Record for:"+ recordId +" is :"+info );
						} catch (IOException e) {
							e.printStackTrace();
						}	
					
				}
				else{
					try(FileWriter fw = new FileWriter("./logs/"+id+".txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw))
						{
						    out.println("Sorry the given id "+recordId +"...doesn't exist!!");
						} catch (IOException e) {
							e.printStackTrace();
						}	
					
				}
				
				break;
			case 5:
				System.out.println("Please enter the record-type ..NR or DR");
				String recordType=objClient.InputStringValidation(keyboard);
				String count=objServer.recordCount(id, recordType);
				System.out.println("The record count is:"+count);
				break;
			case 6:
				System.out.println("Enter RecordID you want to transfer");
				
				String recordI= objClient.InputStringValidation(keyboard);
				ValidateInput v1=new ValidateInput();
				System.out.println("Enter remote server name");
			//	String location = objClient.InputStringValidation(keyboard);
				String sname= v1.validateLocation(keyboard.nextLine().toString());
				success=objServer.transferRecord(id, recordI,sname);
				if(success){
					System.out.println("Record tranferred");
				}
				else
					System.out.println("Record not found");

				break;
				
				
			case 7:
				flag=loginMenu(objClient,flag);
				break;
				
			default:
				System.out.println("Invalid Input");
				
			}			
			
		}
		
	}
	
	
	}
	
