package org.example.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: stockEx.proto")
public final class StocksServiceGrpc {

  private StocksServiceGrpc() {}

  public static final String SERVICE_NAME = "StocksService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.example.grpc.Empty,
      org.example.grpc.Company> getGetAllCompaniesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllCompanies",
      requestType = org.example.grpc.Empty.class,
      responseType = org.example.grpc.Company.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.grpc.Empty,
      org.example.grpc.Company> getGetAllCompaniesMethod() {
    io.grpc.MethodDescriptor<org.example.grpc.Empty, org.example.grpc.Company> getGetAllCompaniesMethod;
    if ((getGetAllCompaniesMethod = StocksServiceGrpc.getGetAllCompaniesMethod) == null) {
      synchronized (StocksServiceGrpc.class) {
        if ((getGetAllCompaniesMethod = StocksServiceGrpc.getGetAllCompaniesMethod) == null) {
          StocksServiceGrpc.getGetAllCompaniesMethod = getGetAllCompaniesMethod = 
              io.grpc.MethodDescriptor.<org.example.grpc.Empty, org.example.grpc.Company>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "StocksService", "getAllCompanies"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.Company.getDefaultInstance()))
                  .setSchemaDescriptor(new StocksServiceMethodDescriptorSupplier("getAllCompanies"))
                  .build();
          }
        }
     }
     return getGetAllCompaniesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.grpc.StockRequest,
      org.example.grpc.SellOrder> getAskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Ask",
      requestType = org.example.grpc.StockRequest.class,
      responseType = org.example.grpc.SellOrder.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.grpc.StockRequest,
      org.example.grpc.SellOrder> getAskMethod() {
    io.grpc.MethodDescriptor<org.example.grpc.StockRequest, org.example.grpc.SellOrder> getAskMethod;
    if ((getAskMethod = StocksServiceGrpc.getAskMethod) == null) {
      synchronized (StocksServiceGrpc.class) {
        if ((getAskMethod = StocksServiceGrpc.getAskMethod) == null) {
          StocksServiceGrpc.getAskMethod = getAskMethod = 
              io.grpc.MethodDescriptor.<org.example.grpc.StockRequest, org.example.grpc.SellOrder>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "StocksService", "Ask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.StockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.SellOrder.getDefaultInstance()))
                  .setSchemaDescriptor(new StocksServiceMethodDescriptorSupplier("Ask"))
                  .build();
          }
        }
     }
     return getAskMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.grpc.StockRequest,
      org.example.grpc.BuyOrder> getBidMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Bid",
      requestType = org.example.grpc.StockRequest.class,
      responseType = org.example.grpc.BuyOrder.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.grpc.StockRequest,
      org.example.grpc.BuyOrder> getBidMethod() {
    io.grpc.MethodDescriptor<org.example.grpc.StockRequest, org.example.grpc.BuyOrder> getBidMethod;
    if ((getBidMethod = StocksServiceGrpc.getBidMethod) == null) {
      synchronized (StocksServiceGrpc.class) {
        if ((getBidMethod = StocksServiceGrpc.getBidMethod) == null) {
          StocksServiceGrpc.getBidMethod = getBidMethod = 
              io.grpc.MethodDescriptor.<org.example.grpc.StockRequest, org.example.grpc.BuyOrder>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "StocksService", "Bid"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.StockRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.BuyOrder.getDefaultInstance()))
                  .setSchemaDescriptor(new StocksServiceMethodDescriptorSupplier("Bid"))
                  .build();
          }
        }
     }
     return getBidMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.grpc.OrderRequest,
      org.example.grpc.OrderResponse> getOrderMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Order",
      requestType = org.example.grpc.OrderRequest.class,
      responseType = org.example.grpc.OrderResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.grpc.OrderRequest,
      org.example.grpc.OrderResponse> getOrderMethod() {
    io.grpc.MethodDescriptor<org.example.grpc.OrderRequest, org.example.grpc.OrderResponse> getOrderMethod;
    if ((getOrderMethod = StocksServiceGrpc.getOrderMethod) == null) {
      synchronized (StocksServiceGrpc.class) {
        if ((getOrderMethod = StocksServiceGrpc.getOrderMethod) == null) {
          StocksServiceGrpc.getOrderMethod = getOrderMethod = 
              io.grpc.MethodDescriptor.<org.example.grpc.OrderRequest, org.example.grpc.OrderResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "StocksService", "Order"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.OrderRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.OrderResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new StocksServiceMethodDescriptorSupplier("Order"))
                  .build();
          }
        }
     }
     return getOrderMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.example.grpc.TradeHistoryRequest,
      org.example.grpc.TradeHistoryResponse> getTradeHistoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "tradeHistory",
      requestType = org.example.grpc.TradeHistoryRequest.class,
      responseType = org.example.grpc.TradeHistoryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.grpc.TradeHistoryRequest,
      org.example.grpc.TradeHistoryResponse> getTradeHistoryMethod() {
    io.grpc.MethodDescriptor<org.example.grpc.TradeHistoryRequest, org.example.grpc.TradeHistoryResponse> getTradeHistoryMethod;
    if ((getTradeHistoryMethod = StocksServiceGrpc.getTradeHistoryMethod) == null) {
      synchronized (StocksServiceGrpc.class) {
        if ((getTradeHistoryMethod = StocksServiceGrpc.getTradeHistoryMethod) == null) {
          StocksServiceGrpc.getTradeHistoryMethod = getTradeHistoryMethod = 
              io.grpc.MethodDescriptor.<org.example.grpc.TradeHistoryRequest, org.example.grpc.TradeHistoryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "StocksService", "tradeHistory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.TradeHistoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.TradeHistoryResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new StocksServiceMethodDescriptorSupplier("tradeHistory"))
                  .build();
          }
        }
     }
     return getTradeHistoryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StocksServiceStub newStub(io.grpc.Channel channel) {
    return new StocksServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StocksServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new StocksServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StocksServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new StocksServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class StocksServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getAllCompanies(org.example.grpc.Empty request,
        io.grpc.stub.StreamObserver<org.example.grpc.Company> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAllCompaniesMethod(), responseObserver);
    }

    /**
     */
    public void ask(org.example.grpc.StockRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.SellOrder> responseObserver) {
      asyncUnimplementedUnaryCall(getAskMethod(), responseObserver);
    }

    /**
     */
    public void bid(org.example.grpc.StockRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.BuyOrder> responseObserver) {
      asyncUnimplementedUnaryCall(getBidMethod(), responseObserver);
    }

    /**
     */
    public void order(org.example.grpc.OrderRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.OrderResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getOrderMethod(), responseObserver);
    }

    /**
     */
    public void tradeHistory(org.example.grpc.TradeHistoryRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.TradeHistoryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getTradeHistoryMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetAllCompaniesMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                org.example.grpc.Empty,
                org.example.grpc.Company>(
                  this, METHODID_GET_ALL_COMPANIES)))
          .addMethod(
            getAskMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                org.example.grpc.StockRequest,
                org.example.grpc.SellOrder>(
                  this, METHODID_ASK)))
          .addMethod(
            getBidMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                org.example.grpc.StockRequest,
                org.example.grpc.BuyOrder>(
                  this, METHODID_BID)))
          .addMethod(
            getOrderMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.example.grpc.OrderRequest,
                org.example.grpc.OrderResponse>(
                  this, METHODID_ORDER)))
          .addMethod(
            getTradeHistoryMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                org.example.grpc.TradeHistoryRequest,
                org.example.grpc.TradeHistoryResponse>(
                  this, METHODID_TRADE_HISTORY)))
          .build();
    }
  }

  /**
   */
  public static final class StocksServiceStub extends io.grpc.stub.AbstractStub<StocksServiceStub> {
    private StocksServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StocksServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StocksServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StocksServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAllCompanies(org.example.grpc.Empty request,
        io.grpc.stub.StreamObserver<org.example.grpc.Company> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetAllCompaniesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ask(org.example.grpc.StockRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.SellOrder> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getAskMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void bid(org.example.grpc.StockRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.BuyOrder> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getBidMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void order(org.example.grpc.OrderRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.OrderResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getOrderMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void tradeHistory(org.example.grpc.TradeHistoryRequest request,
        io.grpc.stub.StreamObserver<org.example.grpc.TradeHistoryResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getTradeHistoryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class StocksServiceBlockingStub extends io.grpc.stub.AbstractStub<StocksServiceBlockingStub> {
    private StocksServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StocksServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StocksServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StocksServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<org.example.grpc.Company> getAllCompanies(
        org.example.grpc.Empty request) {
      return blockingServerStreamingCall(
          getChannel(), getGetAllCompaniesMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.example.grpc.SellOrder> ask(
        org.example.grpc.StockRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getAskMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.example.grpc.BuyOrder> bid(
        org.example.grpc.StockRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getBidMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.example.grpc.OrderResponse order(org.example.grpc.OrderRequest request) {
      return blockingUnaryCall(
          getChannel(), getOrderMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<org.example.grpc.TradeHistoryResponse> tradeHistory(
        org.example.grpc.TradeHistoryRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getTradeHistoryMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StocksServiceFutureStub extends io.grpc.stub.AbstractStub<StocksServiceFutureStub> {
    private StocksServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private StocksServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StocksServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new StocksServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.grpc.OrderResponse> order(
        org.example.grpc.OrderRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getOrderMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_ALL_COMPANIES = 0;
  private static final int METHODID_ASK = 1;
  private static final int METHODID_BID = 2;
  private static final int METHODID_ORDER = 3;
  private static final int METHODID_TRADE_HISTORY = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StocksServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StocksServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_ALL_COMPANIES:
          serviceImpl.getAllCompanies((org.example.grpc.Empty) request,
              (io.grpc.stub.StreamObserver<org.example.grpc.Company>) responseObserver);
          break;
        case METHODID_ASK:
          serviceImpl.ask((org.example.grpc.StockRequest) request,
              (io.grpc.stub.StreamObserver<org.example.grpc.SellOrder>) responseObserver);
          break;
        case METHODID_BID:
          serviceImpl.bid((org.example.grpc.StockRequest) request,
              (io.grpc.stub.StreamObserver<org.example.grpc.BuyOrder>) responseObserver);
          break;
        case METHODID_ORDER:
          serviceImpl.order((org.example.grpc.OrderRequest) request,
              (io.grpc.stub.StreamObserver<org.example.grpc.OrderResponse>) responseObserver);
          break;
        case METHODID_TRADE_HISTORY:
          serviceImpl.tradeHistory((org.example.grpc.TradeHistoryRequest) request,
              (io.grpc.stub.StreamObserver<org.example.grpc.TradeHistoryResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StocksServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StocksServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.example.grpc.StockEx.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StocksService");
    }
  }

  private static final class StocksServiceFileDescriptorSupplier
      extends StocksServiceBaseDescriptorSupplier {
    StocksServiceFileDescriptorSupplier() {}
  }

  private static final class StocksServiceMethodDescriptorSupplier
      extends StocksServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StocksServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StocksServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StocksServiceFileDescriptorSupplier())
              .addMethod(getGetAllCompaniesMethod())
              .addMethod(getAskMethod())
              .addMethod(getBidMethod())
              .addMethod(getOrderMethod())
              .addMethod(getTradeHistoryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
