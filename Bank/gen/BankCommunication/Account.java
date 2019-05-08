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

public interface Account extends com.zeroc.Ice.Object
{
    void addFunds(Decimal newFunds, com.zeroc.Ice.Current current);

    Decimal getFunds(com.zeroc.Ice.Current current);

    LoanCost getMonthlyLoanCost(Decimal loan, CurrencyType currency, int months, com.zeroc.Ice.Current current)
        throws InvalidMonthNumberException,
               NotPremiumAccountException,
               UnsupportedCurrencyException;

    boolean isPremium(com.zeroc.Ice.Current current);

    static final String[] _iceIds =
    {
        "::BankCommunication::Account",
        "::Ice::Object"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::BankCommunication::Account";
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_addFunds(Account obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        Decimal iceP_newFunds;
        iceP_newFunds = Decimal.ice_read(istr);
        inS.endReadParams();
        obj.addFunds(iceP_newFunds, current);
        return inS.setResult(inS.writeEmptyParams());
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getFunds(Account obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        Decimal ret = obj.getFunds(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        Decimal.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_getMonthlyLoanCost(Account obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        Decimal iceP_loan;
        CurrencyType iceP_currency;
        int iceP_months;
        iceP_loan = Decimal.ice_read(istr);
        iceP_currency = CurrencyType.ice_read(istr);
        iceP_months = istr.readInt();
        inS.endReadParams();
        LoanCost ret = obj.getMonthlyLoanCost(iceP_loan, iceP_currency, iceP_months, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        LoanCost.ice_write(ostr, ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_isPremium(Account obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        inS.readEmptyParams();
        boolean ret = obj.isPremium(current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeBool(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    final static String[] _iceOps =
    {
        "addFunds",
        "getFunds",
        "getMonthlyLoanCost",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "isPremium"
    };

    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_addFunds(this, in, current);
            }
            case 1:
            {
                return _iceD_getFunds(this, in, current);
            }
            case 2:
            {
                return _iceD_getMonthlyLoanCost(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 5:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 6:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 7:
            {
                return _iceD_isPremium(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
