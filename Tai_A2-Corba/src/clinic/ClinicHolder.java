package clinic;

/**
 * Holder class for : Clinic
 * 
 * @author OpenORB Compiler
 */
final public class ClinicHolder
        implements org.omg.CORBA.portable.Streamable
{
    /**
     * Internal Clinic value
     */
    public clinic.Clinic value;

    /**
     * Default constructor
     */
    public ClinicHolder()
    { }

    /**
     * Constructor with value initialisation
     * @param initial the initial value
     */
    public ClinicHolder(clinic.Clinic initial)
    {
        value = initial;
    }

    /**
     * Read Clinic from a marshalled stream
     * @param istream the input stream
     */
    public void _read(org.omg.CORBA.portable.InputStream istream)
    {
        value = ClinicHelper.read(istream);
    }

    /**
     * Write Clinic into a marshalled stream
     * @param ostream the output stream
     */
    public void _write(org.omg.CORBA.portable.OutputStream ostream)
    {
        ClinicHelper.write(ostream,value);
    }

    /**
     * Return the Clinic TypeCode
     * @return a TypeCode
     */
    public org.omg.CORBA.TypeCode _type()
    {
        return ClinicHelper.type();
    }

}
