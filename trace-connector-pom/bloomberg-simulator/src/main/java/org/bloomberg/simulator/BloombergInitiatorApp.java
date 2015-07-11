package org.bloomberg.simulator;

import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.UnsupportedMessageType;
import quickfix.field.TradeRequestID;
import quickfix.field.TradeRequestType;
import quickfix.fix44.MessageCracker;
import quickfix.fix44.TradeCaptureReport;
import quickfix.fix44.TradeCaptureReportRequest;

public class BloombergInitiatorApp extends MessageCracker implements Application{

	public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		// TODO Auto-generated method stub
		
	}

	public void fromApp(Message arg0, SessionID arg1) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		// TODO Auto-generated method stub
		
	}

	public void onCreate(SessionID arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onLogon(SessionID arg0) {
		// TODO Auto-generated method stub
		System.out.println("Bloomberg initiator logged on with session id::"+arg0);
		   MessageStore msgStore = new MessageStore();
			//gets trade message
			Message tradeMsg = msgStore.getMessage();
	     //   SessionID ses=bbergInitiator.getSessions().get(0);
	       
				toApp(tradeMsg, arg0);
		
	}

	public void onLogout(SessionID arg0) {
		// TODO Auto-generated method stub
		
	}

	public void toAdmin(Message arg0, SessionID arg1) {
		// TODO Auto-generated method stub
		
	}

	public void toApp(Message arg0, SessionID sessionId)  {
		System.out.println("Inside toApp() with session::"+sessionId);
		
		// TODO Auto-generated method stub
	//	MessageStore msgStore = new MessageStore();
		//gets trade message
	//	Message tradeMsg = msgStore.getMessage();
		TradeCaptureReportRequest req = new TradeCaptureReportRequest(new TradeRequestID("123"), new TradeRequestType(3)); 
		System.out.println("Message from the store ::\n"+req);
		try {
			boolean f= Session.sendToTarget(req, sessionId);
			System.out.println("Sent msg :"+f);
		} catch (SessionNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
