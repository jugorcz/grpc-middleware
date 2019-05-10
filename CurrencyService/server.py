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
        print("Server start")
        self._currencyRateGenerator = CurrencyRateGenerator()
        signal.signal(signal.SIGINT, self.shutdown_on_termination)
        signal.signal(signal.SIGTERM, self.shutdown_on_termination)
        self._currencyRateGenerator.start()
        self._server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
        currencyservice_pb2_grpc.add_CurrencyServiceServicer_to_server(
            CurrencyService(self._currencyRateGenerator), self._server
        )
        self._server.add_insecure_port('localhost:50051')
        self._server.start()
        self.await_termination()

    def await_termination(self):
        self._terminationLock.acquire()

    def shutdown_on_termination(self, signum, frame):
        print('SHUTDOWN')
        if self._currencyRateGenerator is not None:
            self._currencyRateGenerator.stop()
        self._server.stop(0)
        self._terminationLock.release()


if __name__ == '__main__':
    print("Hello world")
    server = Server()
    server.start()