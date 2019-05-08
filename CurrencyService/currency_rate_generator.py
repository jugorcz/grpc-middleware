from threading import Condition, Lock, Thread
import random
import math
from time import sleep
from currencyservice_pb2 import CurrencyType
import currencyservice_pb2_grpc

class CurrencyRateGenerator:
    timeDivisor = 10

    def __init__(self):
        self._currencyValues = 0
        self._timestamp = 0
        self._changesLock = Lock()
        self._newUpdate = Condition()
        self._threadExitFlag = False
        self._currencyUpdateThread = None

    def start(self):
        thread = Thread(name='currency_update', target=self._currencyUpdateTask)
        thread.start()
        self._currencyUpdateThread = thread

    def _currencyUpdateTask(self):
        while True:
            print("Hi")
            sleep(random.randint(3,8))



class CurrencyService(currencyservice_pb2_grpc.CurrencyServiceServicer):

    def __init__(self, currencyRateGenerator: CurrencyRateGenerator):
        self._currencyRateGenerator = CurrencyRateGenerator