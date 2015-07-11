package com.bloomberg.connector;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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
import quickfix.field.SenderCompID;
import quickfix.field.TradeRequestID;
import quickfix.field.TradeRequestType;
import quickfix.fix44.MessageCracker;
import quickfix.fix44.TradeCaptureReport;
import quickfix.fix44.TradeCaptureReportRequest;

public class TraceInitiatorApp  extends MessageCracker implements Application{
	 
	MessageTranslator msgTranslator = new MessageTranslator();
	private BlockingQueue<Message> queue;
	
	public TraceInitiatorApp(ArrayBlockingQueue<Message> messageQueue) {
		this.queue = messageQueue;
	}

	public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		// Send Logon Message here
		
	}

	public void fromApp(Message arg0, SessionID arg1) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		// TODO Auto-generated method stub
		
	}
	
	public void toApp(Message arg0, SessionID sessionId)  {
		System.out.println("Inside toApp() in Trace Initiator with session::"+sessionId);
		
		// TODO Auto-generated method stub
	//	MessageStore msgStore = new MessageStore();
		//gets trade message
	//	Message tradeMsg = msgStore.getMessage();
		TradeCaptureReportRequest req = new TradeCaptureReportRequest(new TradeRequestID("234"), new TradeRequestType(3));
		try {
		Message msg = queue.take();
		System.out.println("Bloomberg message ::\n"+msg);
		TradeCaptureReportRequest tMsg=null;
		if(msg instanceof TradeCaptureReportRequest)
			tMsg = (TradeCaptureReportRequest)msg;
		if(tMsg!=null){
			TradeCaptureReportRequest req1 = new TradeCaptureReportRequest();
			TradeRequestID reqId =new TradeRequestID();
			tMsg.getField(reqId);
			req1.setField(reqId);
			
			TradeRequestType type = new TradeRequestType();
			tMsg.getField(type);
			req1.setField(type);
			System.out.println("Sending msg to finra ::"+req);
			boolean f= Session.sendToTarget(req, sessionId);
			System.out.println("Sent msg to FINRA:"+f);
		}
		} catch (SessionNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FieldNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void onCreate(SessionID arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onLogon(SessionID arg0) {
		// TODO Auto-generated method stub
		System.out.println("Trace Initiator logged on with session id::"+arg0);
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

//	// callback for application messages that you are sending to a counterparty
//	public void toApp(Message msg, SessionID sessionId) throws DoNotSend {
//		// TODO Auto-generated method stub
//		MessageStore msgStore = MessageStore.getInstance();
//		//gets trade message
//		ArrayBlockingQueue<Message> messageQueue = msgStore.getMessageCollection();
//	   
//		try {
//			Message message = messageQueue.take();
//			Message convertedTrdMsg = msgTranslator.translateMessage(message);
//			Session.sendToTarget(convertedTrdMsg, sessionId);
//		} catch (SessionNotFound e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	public void onMessage(TradeCaptureReport message, SessionID sessionID) {
		
	}
	
}
