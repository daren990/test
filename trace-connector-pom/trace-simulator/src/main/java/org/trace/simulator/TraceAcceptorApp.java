package org.trace.simulator;

import java.util.concurrent.ArrayBlockingQueue;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.fix44.MessageCracker;

public class TraceAcceptorApp  extends MessageCracker implements Application{

	public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		// TODO Auto-generated method stub
		
	}

	public void fromApp(Message msg, SessionID sessionId) throws FieldNotFound,
	IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
	System.out.println("Received message from Trace Connector.." );
	
	//gets trade message
	
	}
	
	public void onCreate(SessionID arg0) {
	// TODO Auto-generated method stub
	
	}
	
	public void onLogon(SessionID arg0) {
	// TODO Auto-generated method stub
	System.out.println("acceptor logged in");
	}
	
	public void onLogout(SessionID arg0) {
	// TODO Auto-generated method stub
	
	}
	
	public void toAdmin(Message arg0, SessionID arg1) {
	// TODO Auto-generated method stub
	
	}
	
	public void toApp(Message arg0, SessionID arg1) throws DoNotSend {
	// TODO Auto-generated method stub
	
	}

}
