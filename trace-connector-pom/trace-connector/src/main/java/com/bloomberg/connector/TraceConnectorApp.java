package com.bloomberg.connector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;

import org.quickfixj.jmx.JmxExporter;

import quickfix.Acceptor;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.Initiator;
import quickfix.LogFactory;
import quickfix.Message;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.SocketInitiator;
import quickfix.Message.Header;
import quickfix.field.BeginString;
import quickfix.field.HeartBtInt;
import quickfix.field.ResetSeqNumFlag;
import quickfix.fix44.Logon;

import com.bloomberg.connector.TraceInitiatorApp;;

public class TraceConnectorApp  {

	static ArrayBlockingQueue<Message> messageQueue = new ArrayBlockingQueue<Message>(100,true);
	
	  public static void main(String args[]) throws Exception {
		  
		  //initialize bloomberg acceptor
		   initializeBloombergAcceptor();
		  //Initiator to send messages to Trace
		    initializeTraceInitiator();
		    
	
		  }

	private static void initializeBloombergAcceptor() {
		// TODO Auto-generated method stub
		SessionSettings settings=null;
		Acceptor bbAcceptor=null;
		try {
			
			settings = new SessionSettings("bloomberg_acceptor.properties");
		
	    MessageStoreFactory storeFactory = new FileStoreFactory(settings);
	    LogFactory logFactory = new FileLogFactory(settings);
	    MessageFactory messageFactory = new DefaultMessageFactory();
	
	    BloombergAcceptorApp acceptorApp = new BloombergAcceptorApp(messageQueue);
		bbAcceptor = new SocketAcceptor(acceptorApp, storeFactory, settings, logFactory, messageFactory);
		
		JmxExporter exporter = new JmxExporter();
        exporter.register(bbAcceptor);				  
		
        bbAcceptor.start();
        SessionID sessionId = bbAcceptor.getSessions().get(0);
        sendLogonRequest(sessionId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private static void initializeTraceInitiator() {
			// load the session settings
			SessionSettings settings=null;
			Initiator traceInitiator=null;
			try {
				
				settings = new SessionSettings("trace_initiator.properties");
			
		    MessageStoreFactory storeFactory = new FileStoreFactory(settings);
		    LogFactory logFactory = new FileLogFactory(settings);
		    MessageFactory messageFactory = new DefaultMessageFactory();
		
		    TraceInitiatorApp traceInitiatorApp = new TraceInitiatorApp(messageQueue);
			traceInitiator = new SocketInitiator(traceInitiatorApp, storeFactory, settings, logFactory, messageFactory);
			
			JmxExporter exporter = new JmxExporter();
	        exporter.register(traceInitiator);				  
			
	        traceInitiator.start();
            SessionID sessionId = traceInitiator.getSessions().get(0);
            sendLogonRequest(sessionId);
            int i = 0;
            do {
                try {
                    Thread.sleep(10000);
                    System.out.println(traceInitiator.isLoggedOn());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            } while ((!traceInitiator.isLoggedOn()) && (i < 30));
        } catch (ConfigError e) {
            e.printStackTrace();
        } catch (SessionNotFound e) {
            e.printStackTrace();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (traceInitiator != null) {
            	//traceInitiator.stop(true);
            }
        }

			
	}

	private static void sendLogonRequest(SessionID sessionId)
	        throws SessionNotFound {
	    Logon logon = new Logon();
	    Header header = logon.getHeader();
	    header.setField(new BeginString("FIX.4.4"));
	    logon.set(new HeartBtInt(30));
	    logon.set(new ResetSeqNumFlag(true));
	    boolean sent = Session.sendToTarget(logon, sessionId);
	    System.out.println("Logon Message Sent : " + sent);
	}
}