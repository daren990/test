package org.bloomberg.simulator;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DataDictionary;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.InvalidMessage;
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
	boolean flag =true;
	public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		// TODO Auto-generated method stub
		
	}

	public void fromApp(Message arg0, SessionID sessionId) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		// TODO Auto-generated method stub
		try{
	
			if(flag){
			DataDictionary dd = new DataDictionary("FIX44.xml");
		  
	        Message m = new Message("8=FIX.4.4\0019=0300\00135=AE\00149=BLPTSOPEN\00156=SPECTOPEN\00134=269\00152=20150826-17:32:05\00117=26536\00122=1\00131=101.869\00132=5000000.0\00139=1\00148=90264AAA7\00155=UBS\00160=20150826-18:32:04\00164=20150831\00175=20150826\001150=F\001487=1\001570=Y\001571=COEF4065050\0016208=BBG0000CBS26\0017085=MKRISHNAN1\0019009=26536\0019701=TEST3\0019896=1049\0019998=2755717\001552=1\00154=1\00137=2808\00110=016\001", dd);
		
			boolean f= Session.sendToTarget(m, sessionId);
			
			System.out.println("Sent msg :"+f);
			flag=false;
			}
		} catch (SessionNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void onCreate(SessionID arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onLogon(SessionID arg0) {
		// TODO Auto-generated method stub
		System.out.println("Bloomberg initiator logged on with session id::"+arg0);
		try{
			
			if(flag){
			DataDictionary dd = new DataDictionary("FIX44.xml");
		  
	        Message m = new Message("8=FIX.4.4\0019=0300\00135=AE\00149=BLPTSOPEN\00156=SPECTOPEN\00134=269\00152=20150826-17:32:05\00117=26536\00122=1\00131=101.869\00132=5000000.0\00139=1\00148=90264AAA7\00155=UBS\00160=20150826-18:32:04\00164=20150831\00175=20150826\001150=F\001487=1\001570=Y\001571=COEF4065050\0016208=BBG0000CBS26\0017085=MKRISHNAN1\0019009=26536\0019701=TEST3\0019896=1049\0019998=2755717\001552=1\00154=1\00137=2808\00110=016\001", dd);
		
			boolean f= Session.sendToTarget(m, arg0);
			
			System.out.println("Sent msg :"+f);
			flag=false;
			}
		} catch (SessionNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public void onLogout(SessionID arg0) {
		// TODO Auto-generated method stub
		
	}

	public void toAdmin(Message arg0, SessionID arg1) {
		// TODO Auto-generated method stub
		
	}

	public void toApp(Message arg0, SessionID sessionId)  {
	/*	System.out.println("Inside toApp() with session::"+sessionId);
		
		try{
			
			if(flag){
			DataDictionary dd = new DataDictionary("FIX44.xml");
		  
	        Message m = new Message("8=FIX.4.4\0019=0300\00135=AE\00149=BLPTSOPEN\00156=SPECTOPEN\00134=269\00152=20150826-17:32:05\00117=26536\00122=1\00131=101.869\00132=5000000.0\00139=1\00148=90264AAA7\00155=UBS\00160=20150826-18:32:04\00164=20150831\00175=20150826\001150=F\001487=1\001570=Y\001571=COEF4065050\0016208=BBG0000CBS26\0017085=MKRISHNAN1\0019009=26536\0019701=TEST3\0019896=1049\0019998=2755717\001552=1\00154=1\00137=2808\00110=016\001", dd);
		
			boolean f= Session.sendToTarget(m, sessionId);
			
			System.out.println("Sent msg :"+f);
			flag=false;
			}
		} catch (SessionNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConfigError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		

		
	}

}
