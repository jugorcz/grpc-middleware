package Bank.server;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import CurrencyServer.Currency;
import CurrencyServer.CurrencyServiceGrpc;
import CurrencyServer.CurrencyType;
import CurrencyServer.CurrencyTypes;

public class ExchangeRateProvider implements Runnable{
	private static ExchangeRateProvider provider = null;
	private static final String hostname = "localhost";
	private static final int port = 50051;
	public static final List<CurrencyType> currencyList =  Arrays.asList(CurrencyType.EUR, CurrencyType.JPY);
	private final ManagedChannel channel;
	private final CurrencyServiceGrpc.CurrencyServiceBlockingStub blockingStub;
	private Map<CurrencyType, BigDecimal> currencyValueMap; // odwzorowanie PLNy -> kurs

	public ExchangeRateProvider(String host, int port) 
	{
		currencyValueMap = new HashMap<>(currencyList.size());

		// We use a ManagedChannelBuilder to create the channel.
		channel = ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext(true)
				.build();
		// Now we can use the channel to create our stubs using
		// newBlockingStub method provided in the CurrencyServiceGrpc class
		// we generated from our .proto.
		blockingStub = CurrencyServiceGrpc.newBlockingStub(channel);
		// a blocking/synchronous stub: this means that the RPC call
		// waits for the server to respond, and will either return a
		// response or raise an exception.
	}

	public static ExchangeRateProvider getInstance() {
		if(provider == null) {
			provider = new ExchangeRateProvider(hostname, port);
		}
		return provider;
	}

	@Override
	public void run() {
		CurrencyTypes request = CurrencyTypes.newBuilder().addAllTypes(currencyList).build();
		System.out.println("Initializing getting currency");
		Iterator<Currency> currencyIterator;
		try {
			currencyIterator = blockingStub.getCurrencies(request);
			while(currencyIterator.hasNext()) {
				Currency currency = currencyIterator.next();
				BigDecimal newValue = BigDecimal.valueOf(currency.getValue().getVal(), currency.getValue().getPrecision());
				System.out.println(currency.getType() + " = " + newValue);
				setCurrencyValue(currency.getType(), newValue);
			}
		} catch (StatusRuntimeException e) {
			System.out.println("No currency server available. Stack trace:");
			e.printStackTrace();
		} finally {
			try {
				shutdown();
			} catch (InterruptedException e) {
				System.out.println("Error: try to shutdown.");
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void setCurrencyValue(CurrencyType type, BigDecimal value) {
		currencyValueMap.put(type, value);
	}
	
	public synchronized BigDecimal getCurrencyValue(CurrencyType type) {
		return currencyValueMap.get(type);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}
}
