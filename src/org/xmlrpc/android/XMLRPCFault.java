package org.xmlrpc.android;

public class XMLRPCFault extends XMLRPCException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5676562456612956519L;
	private String faultString;
	private String faultCode;

	public XMLRPCFault(String faultString, String faultCode) {
		super("XMLRPC Fault: " + faultString + " [code1 " + faultCode + "]");
		this.faultString = faultString;
		this.faultCode = faultCode;
	}
	
	public String getFaultString() {
		return faultString;
	}
	
	public String getFaultCode() {
		return faultCode;
	}
}
