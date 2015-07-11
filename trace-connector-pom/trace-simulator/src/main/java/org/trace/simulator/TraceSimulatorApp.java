package org.trace.simulator;

import org.quickfixj.jmx.JmxExporter;

import quickfix.Acceptor;
import quickfix.DefaultMessageFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.Message.Header;
import quickfix.field.BeginString;
import quickfix.field.HeartBtInt;
import quickfix.field.ResetSeqNumFlag;
import quickfix.fix44.Logon;


/**
 * Trace Simulator
 *
 */
public class TraceSimulatorApp 
{
    public static void main( String[] args )
    {
        initializeTraceSimulator();
    }

	private static void initializeTraceSimulator() {
		SessionSettings settings=null;
		Acceptor traceAcceptor=null;
		try {
			
			settings = new SessionSettings("trace_acceptor.properties");
		
	    MessageStoreFactory storeFactory = new FileStoreFactory(settings);
	    LogFactory logFactory = new FileLogFactory(settings);
	    MessageFactory messageFactory = new DefaultMessageFactory();
	
	    TraceAcceptorApp acceptorApp = new TraceAcceptorApp();
		traceAcceptor = new SocketAcceptor(acceptorApp, storeFactory, settings, logFactory, messageFactory);
		
		JmxExporter exporter = new JmxExporter();
        exporter.register(traceAcceptor);				  
		
        traceAcceptor.start();
        SessionID sessionId = traceAcceptor.getSessions().get(0);
        sendLogonRequest(sessionId);

		}catch(Exception e){
			e.printStackTrace();
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
