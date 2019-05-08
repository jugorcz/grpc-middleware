#ifndef _BANK_COMMUNICATION_ICE
#define _BANK_COMMUNICATION_ICE

module BankCommunication
{
	enum CurrencyType
	{
		EUR = 0,
		USD = 1,
		GBP = 2,
		CHF = 3,
		SEK = 4,
		AUD = 5,
		CAD = 6,
		CZK = 7,
		JPY = 8,
		RUB = 9,
		PLN = 10
	}
	
	exception NotPremiumAccountException {}
	exception UnsupportedCurrencyException {}
	exception AlreadyRegisteredException{}
	exception InvalidMonthNumberException {}
	exception InvalidGUIDException{};
	
	struct Decimal
	{
		long value;
		short precision;
	}
	
	struct PersonalData
	{
		string firstName;
		string lastName;
		string PESEL;
	}
	
	struct LoanCost
	{
		Decimal localCurrencyCost;
		Decimal orderedCurrencyCost;  
	}
	
	interface Account
	{
		void addFunds(Decimal newFunds);
		Decimal getFunds();
		LoanCost getMonthlyLoanCost(Decimal loan, CurrencyType currency, int months) 
			throws NotPremiumAccountException, InvalidMonthNumberException, UnsupportedCurrencyException ;
		bool isPremium();
	}
	
	interface AccountFactory
	{
		string createAccount(PersonalData data, Decimal monthlyIncome) throws AlreadyRegisteredException;
		Account* logIn(string GUID) throws InvalidGUIDException;
	}
}
#endif
