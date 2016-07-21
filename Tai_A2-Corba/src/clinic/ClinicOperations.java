package clinic;

/**
 * Interface definition: Clinic.
 * 
 * @author OpenORB Compiler
 */
public interface ClinicOperations
{
    /**
     * Operation createDRecord
     */
    public boolean createDRecord(String managerID, String fname, String lname, String address, String location, String phone, String specialization);

    /**
     * Operation createNRecord
     */
    public boolean createNRecord(String managerID, String fname, String lname, String designation, String status, String statusDate);

    /**
     * Operation recordCount
     */
    public String recordCount(String managerID, String recordType);

    /**
     * Operation editRecord
     */
    public boolean editRecord(String managerID, String recordID, String fieldName, String newValue);

    /**
     * Operation accessRecord
     */
    public String accessRecord(String managerID, String recordID);

    /**
     * Operation transferRecord
     */
    public boolean transferRecord(String managerID, String recordID, String remoteClinicServer);

}
