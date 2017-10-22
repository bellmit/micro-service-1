package com.module.api.pojo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.system.comm.model.BaseEntity;

@Alias("zipkinAnnotations")
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class ZipkinAnnotations extends BaseEntity implements Serializable {
	//If non zero, this means the trace uses 128 bit traceIds instead of 64 bit
	private Long traceIdHigh;
	//coincides with zipkin_spans.trace_id
	private Long traceId;
	//coincides with zipkin_spans.id
	private Long spanId;
	//BinaryAnnotation.key or Annotation.value if type == -1
	private String aKey;
	//BinaryAnnotation.value(), which must be smaller than 64KB
	private String aValue;
	//BinaryAnnotation.type() or -1 if Annotation
	private Integer aType;
	//Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp
	private Long aTimestamp;
	//Null when Binary/Annotation.endpoint is null
	private String endpointIpv4;
	//Null when Binary/Annotation.endpoint is null, or no IPv6 address
	private String endpointIpv6;
	//Null when Binary/Annotation.endpoint is null
	private Integer endpointPort;
	//Null when Binary/Annotation.endpoint is null
	private String endpointServiceName;
	
	public Long getTraceIdHigh() {
		return traceIdHigh;
	}
	public void setTraceIdHigh(Long traceIdHigh) {
		this.traceIdHigh = traceIdHigh;
	}
	public Long getTraceId() {
		return traceId;
	}
	public void setTraceId(Long traceId) {
		this.traceId = traceId;
	}
	public Long getSpanId() {
		return spanId;
	}
	public void setSpanId(Long spanId) {
		this.spanId = spanId;
	}
	public String getaKey() {
		return aKey;
	}
	public void setaKey(String aKey) {
		this.aKey = aKey;
	}
	public String getaValue() {
		return aValue;
	}
	public void setaValue(String aValue) {
		this.aValue = aValue;
	}
	public Integer getaType() {
		return aType;
	}
	public void setaType(Integer aType) {
		this.aType = aType;
	}
	public Long getaTimestamp() {
		return aTimestamp;
	}
	public void setaTimestamp(Long aTimestamp) {
		this.aTimestamp = aTimestamp;
	}
	public String getEndpointIpv4() {
		return endpointIpv4;
	}
	public void setEndpointIpv4(String endpointIpv4) {
		this.endpointIpv4 = endpointIpv4;
	}
	public String getEndpointIpv6() {
		return endpointIpv6;
	}
	public void setEndpointIpv6(String endpointIpv6) {
		this.endpointIpv6 = endpointIpv6;
	}
	public Integer getEndpointPort() {
		return endpointPort;
	}
	public void setEndpointPort(Integer endpointPort) {
		this.endpointPort = endpointPort;
	}
	public String getEndpointServiceName() {
		return endpointServiceName;
	}
	public void setEndpointServiceName(String endpointServiceName) {
		this.endpointServiceName = endpointServiceName;
	}
}
