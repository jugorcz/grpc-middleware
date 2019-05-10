import signal
from concurrent import futures
from threading import Lock
import grpc

import currencyservice_pb2_grpc
from currency_rate_generator import CurrencyRateGenerator
from currency_rate_generator import CurrencyService


class Server:

    def __init__(self):
        self._currencyRateGenerator = None
        self._server = None
        self._terminationLock = Lock()
        self._terminationLock.acquire()

    def start(self):
        self._currencyRateGenerator = CurrencyRateGenerator()
        self._currencyRateGenerator.start()
        self._server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
        currencyservice_pb2_grpc.add_CurrencyServiceServicer_to_server(
            CurrencyService(self._currencyRateGenerator), self._server
        )
        self._server.add_insecure_port('[::]:50051')
        self._server.start()
        self.await_termination()

    def await_termination(self):
        self._terminationLock.acquire()


if __name__ == '__main__':
    print("Hello world")
    server = Server()
    server.start()