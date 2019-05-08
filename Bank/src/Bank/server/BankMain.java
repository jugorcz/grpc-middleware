package Bank.server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

public class BankMain {
	public static void main(String[] args) {
		Thread exchange = new Thread(ExchangeRateProvider.getInstance());
		exchange.start(); // czytanie zmian w kursie walut w oddzielnym watku (funkcja run w ExchangeRateProviederze)
		
		int status = 0;
		Communicator communicator = null;

		try {
			communicator = Util.initialize(args);

			ObjectAdapter adapter = communicator.
					createObjectAdapterWithEndpoints("Adapter", "tcp -h localhost -p 10000:udp -h localhost -p 10000");

			// 3. Stworzenie serwanta/serwantow
			AccountFactoryI factoryServant = new AccountFactoryI(adapter);    
			
						    
			// 4. Dodanie wpisow do tablicy ASM
			adapter.add(factoryServant, new Identity("factory", "factory"));
	        
			// 5. Aktywacja adaptera i przejscie w petli przetwarzania zadan
			adapter.activate();
			
			System.out.println("Entering event processing loop...");
			
			communicator.waitForShutdown(); 		
			
		}
		catch (Exception e) {
			System.err.println(e);
			status = 1;
		}
		if (communicator != null) {
			// Clean up
			//
			try {
				communicator.destroy();
			}
			catch (Exception e) {
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}
}
