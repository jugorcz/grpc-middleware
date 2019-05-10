from threading import Condition, Lock, Thread
import random
import math
from time import sleep
from currencyservice_pb2 import CurrencyType
import currencyservice_pb2_grpc

class CurrencyRateGenerator:
    timeDivisor = 10

    def __init__(self):
        self._currencyValues = self._initCurrencyValues()
        self._timestamp = 0
        self._changesLock = Lock()
        self._newUpdate = Condition()
        self._threadExitFlag = False
        self._currencyUpdateThread = None

    def start(self):
        thread = Thread(name='currency_update', target=self._currencyUpdateTask)
        thread.start()
        self._currencyUpdateThread = thread

    def stop(self):
        self._changesLock.acquire()
        self._threadExitFlag = True
        self._changesLock.release()
        if self._currencyUpdateThread is not None:
            self._currencyUpdateThread.join()
        self._newUpdate.acquire()
        self._newUpdate.notifyAll()
        self._newUpdate.release()


    def _currencyUpdateTask(self):
        while True:
            #print("\nHi")
            self._changesLock.acquire()
            if self._threadExitFlag:
                self._threadExitFlag.release()
                return None

            timestampDelta = random.randint(1,10)
            self._timestamp += timestampDelta
            self._updateCurrency()
            self._changesLock.release()
            self._newUpdate.acquire()
            self._newUpdate.notifyAll()
            self._newUpdate.release()
            sleep(random.randint(3,8))

    def _updateCurrency(self):
        for currencyType in CurrencyType.keys():
            change = random.uniform(-1,1)
            #print(currencyType + " change: " + str(change))
            currVal = self._currencyValues[CurrencyType.Value(currencyType)]
            if currVal + change > 0:
                self._currencyValues[CurrencyType.Value(currencyType)] += change
            #print(currVal)
            #print(currVal + change)

    def _initCurrencyValues(self):
        currencyValues = {}
        for currencyType in CurrencyType.keys():
            currencyValues[CurrencyType.Value(currencyType)] = random.uniform(3, 15)
            print(currencyType + " initial value: " + str(currencyValues[CurrencyType.Value(currencyType)] ))
        return currencyValues

    def getCurrenciesRate(self, currencyList):
        #print("getCurrenciesRate")
        self._newUpdate.acquire()
        self._newUpdate.wait()
        self._newUpdate.release()
        self._changesLock.acquire()
        result = []
        for currency in currencyList:
            result.append((currency, self._currencyValues[currency]))
        self._changesLock.release()
        return result



class CurrencyService(currencyservice_pb2_grpc.CurrencyServiceServicer):

    def __init__(self, currencyRateGenerator: CurrencyRateGenerator):
        #print("Currency service init")
        self._currencyRateGenerator = currencyRateGenerator

    def GetCurrencies(self, request, context):
        #print("GetCurrencies function")
        while True:
            print("Trying to obtain update")
            update = self._currencyRateGenerator.getCurrenciesRate(request.types)
            print("Update obtained")
            for currency, value in update:
                result = Currency(type == currency, value = value)
                yield result