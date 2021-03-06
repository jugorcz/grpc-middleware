# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
import grpc

import currencyservice_pb2 as currencyservice__pb2


class CurrencyServiceStub(object):
  # missing associated documentation comment in .proto file
  pass

  def __init__(self, channel):
    """Constructor.

    Args:
      channel: A grpc.Channel.
    """
    self.GetCurrencies = channel.unary_stream(
        '/CurrencyService/GetCurrencies',
        request_serializer=currencyservice__pb2.CurrencyTypes.SerializeToString,
        response_deserializer=currencyservice__pb2.Currency.FromString,
        )


class CurrencyServiceServicer(object):
  # missing associated documentation comment in .proto file
  pass

  def GetCurrencies(self, request, context):
    # missing associated documentation comment in .proto file
    pass
    context.set_code(grpc.StatusCode.UNIMPLEMENTED)
    context.set_details('Method not implemented!')
    raise NotImplementedError('Method not implemented!')


def add_CurrencyServiceServicer_to_server(servicer, server):
  rpc_method_handlers = {
      'GetCurrencies': grpc.unary_stream_rpc_method_handler(
          servicer.GetCurrencies,
          request_deserializer=currencyservice__pb2.CurrencyTypes.FromString,
          response_serializer=currencyservice__pb2.Currency.SerializeToString,
      ),
  }
  generic_handler = grpc.method_handlers_generic_handler(
      'CurrencyService', rpc_method_handlers)
  server.add_generic_rpc_handlers((generic_handler,))
