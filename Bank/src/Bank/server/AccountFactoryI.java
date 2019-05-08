package Bank.server;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ObjectPrx;

import BankCommunication.AccountFactory;
import BankCommunication.AccountPrx;
import BankCommunication.AlreadyRegisteredException;
import BankCommunication.Decimal;
import BankCommunication.InvalidGUIDException;
import BankCommunication.PersonalData;

public class AccountFactoryI implements AccountFactory {

	private static Map<String, AccountPrx> accounts = new HashMap();
	
	private static final int GUID_LENGTH = 10;
	private static final BigDecimal lowerPremiumLimit = BigDecimal.valueOf(10000);
	private final ObjectAdapter adapter;

	public AccountFactoryI(ObjectAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public synchronized String createAccount(PersonalData data, Decimal monthlyIncome, Current current) throws AlreadyRegisteredException {
		BigDecimal monthlyIncomeBD = BigDecimal.valueOf(monthlyIncome.value, monthlyIncome.precision);
		boolean isPremium = (monthlyIncomeBD.compareTo(lowerPremiumLimit) >= 0);
		AccountI account = new AccountI (data, isPremium);
		String GUID = (new RandomString(GUID_LENGTH)).nextString();
		ObjectPrx proxy = null;
		while(accounts.containsKey(GUID))
			GUID = (new RandomString(GUID_LENGTH)).nextString();
		try {
			proxy = adapter.add(account, new Identity(data.PESEL, isPremium ? "premium" : "standard"));
		} catch (Exception e) {
			throw new AlreadyRegisteredException();
		}
		accounts.put(GUID, AccountPrx.checkedCast(proxy));

		return GUID;
	}
	
	@Override
    public synchronized AccountPrx logIn(String GUID, Current current) throws InvalidGUIDException{
		if(!accounts.containsKey(GUID))
			throw new InvalidGUIDException();
		return accounts.get(GUID);
	}

}
