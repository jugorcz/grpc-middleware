package Bank.server;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.zeroc.Ice.Current;

import BankCommunication.Account;
import BankCommunication.CurrencyType;
import BankCommunication.Decimal;
import BankCommunication.InvalidMonthNumberException;
import BankCommunication.LoanCost;
import BankCommunication.NotPremiumAccountException;
import BankCommunication.PersonalData;
import BankCommunication.UnsupportedCurrencyException;

public class AccountI implements Account {
	private static final BigDecimal interest = new BigDecimal("1.10");
	private PersonalData data;
	private boolean isPremium;
	private BigDecimal funds;
	
	public AccountI(PersonalData data, boolean isPremium) {
		System.out.println("New account created: " + data.firstName + " " + data.lastName + " PESEL: " + data.PESEL );
		this.data = data;
		this.isPremium = isPremium;
		this.funds = BigDecimal.ZERO;
	}

	public static BigDecimal decimalToBigDecimal(Decimal number) {
		return BigDecimal.valueOf(number.value, number.precision);

	}

	public static Decimal bigDecimalToDecimal(BigDecimal number) {
		return new Decimal(number.unscaledValue().longValue(), (short)number.scale());
	}

	@Override
	public void addFunds(Decimal newFunds, Current current) {

		BigDecimal newFundsBD = decimalToBigDecimal(newFunds);
		System.out.println(data.PESEL + " added funds: " + newFundsBD.toString());
		funds = funds.add(newFundsBD);
	}

	@Override
	public Decimal getFunds(Current current) {
		System.out.println(data.PESEL + " get funds: " + funds.toString());
		return bigDecimalToDecimal(funds);
	}

	@Override
	public LoanCost getMonthlyLoanCost(Decimal loan, CurrencyType currencyType, int months, Current current)
			throws InvalidMonthNumberException, NotPremiumAccountException , UnsupportedCurrencyException {
		if(months < 0) 
			throw new InvalidMonthNumberException();

		if(!isPremium && currencyType != CurrencyType.PLN)
			throw new NotPremiumAccountException();


		if(currencyType != CurrencyType.PLN &&
				!ExchangeRateProvider.currencyList
						.contains(CurrencyServer.CurrencyType
								.forNumber(currencyType.value())))
			throw new UnsupportedCurrencyException();

		BigDecimal loanCost = decimalToBigDecimal(loan)
				.multiply(interest)
				.divide(BigDecimal.valueOf(months, 0),
						2,
						RoundingMode.HALF_UP);

		if(currencyType == CurrencyType.PLN) {
			return new LoanCost(bigDecimalToDecimal(loanCost),
					bigDecimalToDecimal(loanCost));
		} else {

			BigDecimal currencyValue = ExchangeRateProvider.getInstance().getCurrencyValue(
					CurrencyServer.CurrencyType.forNumber((currencyType.value())));

			return new LoanCost(bigDecimalToDecimal(loanCost
					.multiply(currencyValue)
					.setScale(2, RoundingMode.HALF_UP)),
					bigDecimalToDecimal(loanCost));
		}
	}
	
	@Override
	public boolean isPremium(Current current) {
		return isPremium;
	}
}
