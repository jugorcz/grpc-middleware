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

public class InvalidGUIDException extends com.zeroc.Ice.UserException
{
    public InvalidGUIDException()
    {
    }

    public InvalidGUIDException(Throwable cause)
    {
        super(cause);
    }

    public String ice_id()
    {
        return "::BankCommunication::InvalidGUIDException";
    }

    @Override
    protected void _writeImpl(com.zeroc.Ice.OutputStream ostr_)
    {
        ostr_.startSlice("::BankCommunication::InvalidGUIDException", -1, true);
        ostr_.endSlice();
    }

    @Override
    protected void _readImpl(com.zeroc.Ice.InputStream istr_)
    {
        istr_.startSlice();
        istr_.endSlice();
    }

    public static final long serialVersionUID = 3768216609942172248L;
}
