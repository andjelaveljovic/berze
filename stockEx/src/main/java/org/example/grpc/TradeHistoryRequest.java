// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: stockEx.proto

package org.example.grpc;

/**
 * <pre>
 * Message for representing a trade history request
 * </pre>
 *
 * Protobuf type {@code TradeHistoryRequest}
 */
public  final class TradeHistoryRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:TradeHistoryRequest)
    TradeHistoryRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use TradeHistoryRequest.newBuilder() to construct.
  private TradeHistoryRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private TradeHistoryRequest() {
    symbol_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private TradeHistoryRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            symbol_ = s;
            break;
          }
          case 18: {
            com.google.protobuf.Timestamp.Builder subBuilder = null;
            if (date_ != null) {
              subBuilder = date_.toBuilder();
            }
            date_ = input.readMessage(com.google.protobuf.Timestamp.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(date_);
              date_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.example.grpc.StockEx.internal_static_TradeHistoryRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.example.grpc.StockEx.internal_static_TradeHistoryRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.example.grpc.TradeHistoryRequest.class, org.example.grpc.TradeHistoryRequest.Builder.class);
  }

  public static final int SYMBOL_FIELD_NUMBER = 1;
  private volatile java.lang.Object symbol_;
  /**
   * <code>string symbol = 1;</code>
   */
  public java.lang.String getSymbol() {
    java.lang.Object ref = symbol_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      symbol_ = s;
      return s;
    }
  }
  /**
   * <code>string symbol = 1;</code>
   */
  public com.google.protobuf.ByteString
      getSymbolBytes() {
    java.lang.Object ref = symbol_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      symbol_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int DATE_FIELD_NUMBER = 2;
  private com.google.protobuf.Timestamp date_;
  /**
   * <code>.google.protobuf.Timestamp date = 2;</code>
   */
  public boolean hasDate() {
    return date_ != null;
  }
  /**
   * <code>.google.protobuf.Timestamp date = 2;</code>
   */
  public com.google.protobuf.Timestamp getDate() {
    return date_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : date_;
  }
  /**
   * <code>.google.protobuf.Timestamp date = 2;</code>
   */
  public com.google.protobuf.TimestampOrBuilder getDateOrBuilder() {
    return getDate();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getSymbolBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, symbol_);
    }
    if (date_ != null) {
      output.writeMessage(2, getDate());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getSymbolBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, symbol_);
    }
    if (date_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getDate());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.example.grpc.TradeHistoryRequest)) {
      return super.equals(obj);
    }
    org.example.grpc.TradeHistoryRequest other = (org.example.grpc.TradeHistoryRequest) obj;

    boolean result = true;
    result = result && getSymbol()
        .equals(other.getSymbol());
    result = result && (hasDate() == other.hasDate());
    if (hasDate()) {
      result = result && getDate()
          .equals(other.getDate());
    }
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + SYMBOL_FIELD_NUMBER;
    hash = (53 * hash) + getSymbol().hashCode();
    if (hasDate()) {
      hash = (37 * hash) + DATE_FIELD_NUMBER;
      hash = (53 * hash) + getDate().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.example.grpc.TradeHistoryRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.example.grpc.TradeHistoryRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.example.grpc.TradeHistoryRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.example.grpc.TradeHistoryRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.example.grpc.TradeHistoryRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.example.grpc.TradeHistoryRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.example.grpc.TradeHistoryRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.example.grpc.TradeHistoryRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.example.grpc.TradeHistoryRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.example.grpc.TradeHistoryRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.example.grpc.TradeHistoryRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.example.grpc.TradeHistoryRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.example.grpc.TradeHistoryRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * Message for representing a trade history request
   * </pre>
   *
   * Protobuf type {@code TradeHistoryRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:TradeHistoryRequest)
      org.example.grpc.TradeHistoryRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.example.grpc.StockEx.internal_static_TradeHistoryRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.example.grpc.StockEx.internal_static_TradeHistoryRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.example.grpc.TradeHistoryRequest.class, org.example.grpc.TradeHistoryRequest.Builder.class);
    }

    // Construct using org.example.grpc.TradeHistoryRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      symbol_ = "";

      if (dateBuilder_ == null) {
        date_ = null;
      } else {
        date_ = null;
        dateBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.example.grpc.StockEx.internal_static_TradeHistoryRequest_descriptor;
    }

    @java.lang.Override
    public org.example.grpc.TradeHistoryRequest getDefaultInstanceForType() {
      return org.example.grpc.TradeHistoryRequest.getDefaultInstance();
    }

    @java.lang.Override
    public org.example.grpc.TradeHistoryRequest build() {
      org.example.grpc.TradeHistoryRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.example.grpc.TradeHistoryRequest buildPartial() {
      org.example.grpc.TradeHistoryRequest result = new org.example.grpc.TradeHistoryRequest(this);
      result.symbol_ = symbol_;
      if (dateBuilder_ == null) {
        result.date_ = date_;
      } else {
        result.date_ = dateBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.example.grpc.TradeHistoryRequest) {
        return mergeFrom((org.example.grpc.TradeHistoryRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.example.grpc.TradeHistoryRequest other) {
      if (other == org.example.grpc.TradeHistoryRequest.getDefaultInstance()) return this;
      if (!other.getSymbol().isEmpty()) {
        symbol_ = other.symbol_;
        onChanged();
      }
      if (other.hasDate()) {
        mergeDate(other.getDate());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.example.grpc.TradeHistoryRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.example.grpc.TradeHistoryRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object symbol_ = "";
    /**
     * <code>string symbol = 1;</code>
     */
    public java.lang.String getSymbol() {
      java.lang.Object ref = symbol_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        symbol_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string symbol = 1;</code>
     */
    public com.google.protobuf.ByteString
        getSymbolBytes() {
      java.lang.Object ref = symbol_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        symbol_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string symbol = 1;</code>
     */
    public Builder setSymbol(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      symbol_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string symbol = 1;</code>
     */
    public Builder clearSymbol() {
      
      symbol_ = getDefaultInstance().getSymbol();
      onChanged();
      return this;
    }
    /**
     * <code>string symbol = 1;</code>
     */
    public Builder setSymbolBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      symbol_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.Timestamp date_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> dateBuilder_;
    /**
     * <code>.google.protobuf.Timestamp date = 2;</code>
     */
    public boolean hasDate() {
      return dateBuilder_ != null || date_ != null;
    }
    /**
     * <code>.google.protobuf.Timestamp date = 2;</code>
     */
    public com.google.protobuf.Timestamp getDate() {
      if (dateBuilder_ == null) {
        return date_ == null ? com.google.protobuf.Timestamp.getDefaultInstance() : date_;
      } else {
        return dateBuilder_.getMessage();
      }
    }
    /**
     * <code>.google.protobuf.Timestamp date = 2;</code>
     */
    public Builder setDate(com.google.protobuf.Timestamp value) {
      if (dateBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        date_ = value;
        onChanged();
      } else {
        dateBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Timestamp date = 2;</code>
     */
    public Builder setDate(
        com.google.protobuf.Timestamp.Builder builderForValue) {
      if (dateBuilder_ == null) {
        date_ = builderForValue.build();
        onChanged();
      } else {
        dateBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Timestamp date = 2;</code>
     */
    public Builder mergeDate(com.google.protobuf.Timestamp value) {
      if (dateBuilder_ == null) {
        if (date_ != null) {
          date_ =
            com.google.protobuf.Timestamp.newBuilder(date_).mergeFrom(value).buildPartial();
        } else {
          date_ = value;
        }
        onChanged();
      } else {
        dateBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Timestamp date = 2;</code>
     */
    public Builder clearDate() {
      if (dateBuilder_ == null) {
        date_ = null;
        onChanged();
      } else {
        date_ = null;
        dateBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Timestamp date = 2;</code>
     */
    public com.google.protobuf.Timestamp.Builder getDateBuilder() {
      
      onChanged();
      return getDateFieldBuilder().getBuilder();
    }
    /**
     * <code>.google.protobuf.Timestamp date = 2;</code>
     */
    public com.google.protobuf.TimestampOrBuilder getDateOrBuilder() {
      if (dateBuilder_ != null) {
        return dateBuilder_.getMessageOrBuilder();
      } else {
        return date_ == null ?
            com.google.protobuf.Timestamp.getDefaultInstance() : date_;
      }
    }
    /**
     * <code>.google.protobuf.Timestamp date = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder> 
        getDateFieldBuilder() {
      if (dateBuilder_ == null) {
        dateBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Timestamp, com.google.protobuf.Timestamp.Builder, com.google.protobuf.TimestampOrBuilder>(
                getDate(),
                getParentForChildren(),
                isClean());
        date_ = null;
      }
      return dateBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:TradeHistoryRequest)
  }

  // @@protoc_insertion_point(class_scope:TradeHistoryRequest)
  private static final org.example.grpc.TradeHistoryRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.example.grpc.TradeHistoryRequest();
  }

  public static org.example.grpc.TradeHistoryRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<TradeHistoryRequest>
      PARSER = new com.google.protobuf.AbstractParser<TradeHistoryRequest>() {
    @java.lang.Override
    public TradeHistoryRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new TradeHistoryRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<TradeHistoryRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<TradeHistoryRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.example.grpc.TradeHistoryRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

