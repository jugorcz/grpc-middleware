package Bank.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import Bank.server.AccountI;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import BankCommunication.AccountFactoryPrx;
import BankCommunication.AccountPrx;
import BankCommunication.AlreadyRegisteredException;
import BankCommunication.CurrencyType;
import BankCommunication.InvalidGUIDException;
import BankCommunication.InvalidMonthNumberException;
import BankCommunication.LoanCost;
import BankCommunication.NotPremiumAccountException;
import BankCommunication.PersonalData;
import BankCommunication.UnsupportedCurrencyException;

public class ClientMain {
	public static void main(String[] args) {
		int status = 0;
		Communicator communicator = null;
		try {
			communicator = Util.initialize(args);

			ObjectPrx base = communicator.stringToProxy("factory/factory:tcp -h localhost -p 10000:udp -h localhost -p 10000");

			AccountFactoryPrx factoryPrx = AccountFactoryPrx.checkedCast(base);
			if (factoryPrx == null) throw new Error("Invalid proxy");
/*			accountPrx.addFunds(new Decimal((long)2000, (short)0));
			Decimal decimal = accountPrx.getFunds();
			BigDecimal bigDecimal = BigDecimal.valueOf(decimal.value, decimal.precision);
			
			
			
			System.out.println("Masz " + bigDecimal.toString() + " piniadzow");
			LoanCost loan = accountPrx.getMonthlyLoanCost(new Decimal((long)50000, (short)0), CurrencyType.JPY, 12); 
			System.out.println(AccountI.decimalToBigDecimal(loan.localCurrencyCost).toString() + " " + AccountI.decimalToBigDecimal(loan.orderedCurrencyCost).toString());
*/
			AccountPrx accountPrx = null;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String line = "";
			do
			{
				try
				{
					System.out.print("> ");
					System.out.flush();
					line = in.readLine();
					if (line == null) {
						break;
					}
					if(accountPrx == null) {

						if(line.equals("create")) {
							String firstName, lastName, PESEL;
							BigDecimal income;
							System.out.print("Type your firstname: ");
							firstName = in.readLine();
							System.out.print("Type your lastname: ");
							lastName = in.readLine();
							System.out.print("Type your PESEL number: ");
							PESEL = in.readLine();
							System.out.print("Type your monthly income: ");
							income = new BigDecimal(in.readLine());
							System.out.println("Your GUID: " + factoryPrx.createAccount(new PersonalData(firstName, lastName, PESEL), AccountI.bigDecimalToDecimal(income)));
						}
						if(line.equals("login")) {
							System.out.print("Type your GUID: ");
							accountPrx = factoryPrx.logIn(in.readLine());
							System.out.println("Logged in");
						}
						if(line.equals("help")) {
							System.out.println("create - create account");
							System.out.println("login - login to account");
						}
					}
					else {

						if (line.equals("add")) {

							BigDecimal newFunds;
							System.out.print("Type amount: ");
							newFunds = new BigDecimal(in.readLine());
							accountPrx.addFunds(AccountI.bigDecimalToDecimal(newFunds));
							System.out.println("Added amount");
						}

						if(line.equals("get")) {
							System.out.println("Your account status: " + AccountI.decimalToBigDecimal(accountPrx.getFunds()));
						}

						if(line.equals("loan")) {

							System.out.print("Type amount: ");
							BigDecimal amount = new BigDecimal(in.readLine());
							System.out.print("Type currency type: ");
							CurrencyType currencyType = getCurrencyType(in.readLine());
							System.out.print("Type months amount: ");
							int months = Integer.parseInt(in.readLine());
							LoanCost loanCost = accountPrx.getMonthlyLoanCost(AccountI.bigDecimalToDecimal(amount), currencyType, months);
							System.out.print("Your loan will cost ");
							if(currencyType == CurrencyType.PLN)
								System.out.println(AccountI.decimalToBigDecimal(loanCost.localCurrencyCost) + " PLN monthly");
							else
								System.out.println(AccountI.decimalToBigDecimal(loanCost.localCurrencyCost) + " PLN/" + AccountI.decimalToBigDecimal(loanCost.orderedCurrencyCost) + " " + getCurrencyName(currencyType) + " monthly");
							
						}
						
						if(line.equals("logoff")) {
							accountPrx = null;
							System.out.println("Logged off");
						}
						
						if(line.equals("help")) {
							System.out.println("add - add funds to your account");
							System.out.println("get - show your account funds");
							System.out.println("loan - loan money");
							System.out.println("logoff - log out");
						}
					}
				}
				catch (java.io.IOException e) {
					System.err.println(e);
				} catch (InvalidGUIDException e) {
					System.err.println("Invalid GUID");
				} catch (AlreadyRegisteredException e) {
					System.out.println("User already registered");
					
				} catch (InvalidMonthNumberException e) {
					System.out.println("Invalid month number");

				} catch (NotPremiumAccountException e) {
					System.out.println("Can't loan for other currency than local if not premium account");
					
				} catch (UnsupportedCurrencyException e) {
					System.out.println("Unsuported Currency");
				}
			}
			while (!line.equals("quit"));
			
		} catch (LocalException e) {
			e.printStackTrace();
			status = 1;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			status = 1;
		}
		if (communicator != null) {
			// Clean up
			try {
				communicator.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status = 1;
			}
		}
		System.exit(status);
	}
	
	public static CurrencyType getCurrencyType(String s) {
		if(s.equals("EUR")) return CurrencyType.EUR;
		if(s.equals("USD")) return CurrencyType.USD;
		if(s.equals("GBP")) return CurrencyType.GBP;
		if(s.equals("CHF")) return CurrencyType.CHF;
		if(s.equals("SEK")) return CurrencyType.SEK;
		if(s.equals("AUD")) return CurrencyType.AUD;
		if(s.equals("CAD")) return CurrencyType.CAD;
		if(s.equals("CZK")) return CurrencyType.CZK;
		if(s.equals("JPY")) return CurrencyType.JPY;
		if(s.equals("RUB")) return CurrencyType.RUB;
		return CurrencyType.PLN;
	}
	public static String getCurrencyName(CurrencyType type)
	{
		String s = "";
		switch(type)
		{
		case EUR:
			s = "EUR";
			break;
		case USD:
			s = "USD";
			break;
		case GBP:
			s = "USD";
			break;
		case CHF:
			s = "CHF";
			break;
		case SEK:
			s = "SEK";
			break;
		case AUD:
			s = "AUD";
			break;
		case CAD:
			s = "CAD";
			break;
		case CZK:
			s = "CZK";
			break;
		case JPY:
			s = "JPY";
			break;
		case RUB:
			s = "RUB";
			break;
		default:
			break;
		}
		return s;
	}

}
