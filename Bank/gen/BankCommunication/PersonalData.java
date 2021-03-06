// **********************************************************************
//
// Copyright (c) 2003-2018 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.7.1
//
// <auto-generated>
//
// Generated from file `bankcommunication.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package BankCommunication;

public class PersonalData implements java.lang.Cloneable,
                                     java.io.Serializable
{
    public String firstName;

    public String lastName;

    public String PESEL;

    public PersonalData()
    {
        this.firstName = "";
        this.lastName = "";
        this.PESEL = "";
    }

    public PersonalData(String firstName, String lastName, String PESEL)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.PESEL = PESEL;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        PersonalData r = null;
        if(rhs instanceof PersonalData)
        {
            r = (PersonalData)rhs;
        }

        if(r != null)
        {
            if(this.firstName != r.firstName)
            {
                if(this.firstName == null || r.firstName == null || !this.firstName.equals(r.firstName))
                {
                    return false;
                }
            }
            if(this.lastName != r.lastName)
            {
                if(this.lastName == null || r.lastName == null || !this.lastName.equals(r.lastName))
                {
                    return false;
                }
            }
            if(this.PESEL != r.PESEL)
            {
                if(this.PESEL == null || r.PESEL == null || !this.PESEL.equals(r.PESEL))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::BankCommunication::PersonalData");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, firstName);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, lastName);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, PESEL);
        return h_;
    }

    public PersonalData clone()
    {
        PersonalData c = null;
        try
        {
            c = (PersonalData)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeString(this.firstName);
        ostr.writeString(this.lastName);
        ostr.writeString(this.PESEL);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.firstName = istr.readString();
        this.lastName = istr.readString();
        this.PESEL = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, PersonalData v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public PersonalData ice_read(com.zeroc.Ice.InputStream istr)
    {
        PersonalData v = new PersonalData();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<PersonalData> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, PersonalData v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<PersonalData> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(PersonalData.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final PersonalData _nullMarshalValue = new PersonalData();

    public static final long serialVersionUID = 4106779954143257501L;
}
