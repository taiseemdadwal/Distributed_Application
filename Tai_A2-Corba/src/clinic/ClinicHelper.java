package clinic;

/** 
 * Helper class for : Clinic
 *  
 * @author OpenORB Compiler
 */ 
public class ClinicHelper
{
    /**
     * Insert Clinic into an any
     * @param a an any
     * @param t Clinic value
     */
    public static void insert(org.omg.CORBA.Any a, clinic.Clinic t)
    {
        a.insert_Object(t , type());
    }

    /**
     * Extract Clinic from an any
     *
     * @param a an any
     * @return the extracted Clinic value
     */
    public static clinic.Clinic extract( org.omg.CORBA.Any a )
    {
        if ( !a.type().equivalent( type() ) )
        {
            throw new org.omg.CORBA.MARSHAL();
        }
        try
        {
            return clinic.ClinicHelper.narrow( a.extract_Object() );
        }
        catch ( final org.omg.CORBA.BAD_PARAM e )
        {
            throw new org.omg.CORBA.MARSHAL(e.getMessage());
        }
    }

    //
    // Internal TypeCode value
    //
    private static org.omg.CORBA.TypeCode _tc = null;

    /**
     * Return the Clinic TypeCode
     * @return a TypeCode
     */
    public static org.omg.CORBA.TypeCode type()
    {
        if (_tc == null) {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            _tc = orb.create_interface_tc( id(), "Clinic" );
        }
        return _tc;
    }

    /**
     * Return the Clinic IDL ID
     * @return an ID
     */
    public static String id()
    {
        return _id;
    }

    private final static String _id = "IDL:clinic/Clinic:1.0";

    /**
     * Read Clinic from a marshalled stream
     * @param istream the input stream
     * @return the readed Clinic value
     */
    public static clinic.Clinic read(org.omg.CORBA.portable.InputStream istream)
    {
        return(clinic.Clinic)istream.read_Object(clinic._ClinicStub.class);
    }

    /**
     * Write Clinic into a marshalled stream
     * @param ostream the output stream
     * @param value Clinic value
     */
    public static void write(org.omg.CORBA.portable.OutputStream ostream, clinic.Clinic value)
    {
        ostream.write_Object((org.omg.CORBA.portable.ObjectImpl)value);
    }

    /**
     * Narrow CORBA::Object to Clinic
     * @param obj the CORBA Object
     * @return Clinic Object
     */
    public static Clinic narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Clinic)
            return (Clinic)obj;

        if (obj._is_a(id()))
        {
            _ClinicStub stub = new _ClinicStub();
            stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
            return stub;
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }

    /**
     * Unchecked Narrow CORBA::Object to Clinic
     * @param obj the CORBA Object
     * @return Clinic Object
     */
    public static Clinic unchecked_narrow(org.omg.CORBA.Object obj)
    {
        if (obj == null)
            return null;
        if (obj instanceof Clinic)
            return (Clinic)obj;

        _ClinicStub stub = new _ClinicStub();
        stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
        return stub;

    }

}
