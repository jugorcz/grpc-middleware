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

public class Decimal implements java.lang.Cloneable,
                                java.io.Serializable
{
    public long value;

    public short precision;

    public Decimal()
    {
    }

    public Decimal(long value, short precision)
    {
        this.value = value;
        this.precision = precision;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        Decimal r = null;
        if(rhs instanceof Decimal)
        {
            r = (Decimal)rhs;
        }

        if(r != null)
        {
            if(this.value != r.value)
            {
                return false;
            }
            if(this.precision != r.precision)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::BankCommunication::Decimal");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, value);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, precision);
        return h_;
    }

    public Decimal clone()
    {
        Decimal c = null;
        try
        {
            c = (Decimal)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeLong(this.value);
        ostr.writeShort(this.precision);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.value = istr.readLong();
        this.precision = istr.readShort();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, Decimal v)
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

    static public Decimal ice_read(com.zeroc.Ice.InputStream istr)
    {
        Decimal v = new Decimal();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<Decimal> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, Decimal v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            ostr.writeSize(10);
            ice_write(ostr, v);
        }
    }

    static public java.util.Optional<Decimal> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.VSize))
        {
            istr.skipSize();
            return java.util.Optional.of(Decimal.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final Decimal _nullMarshalValue = new Decimal();

    public static final long serialVersionUID = 4164145987712444347L;
}