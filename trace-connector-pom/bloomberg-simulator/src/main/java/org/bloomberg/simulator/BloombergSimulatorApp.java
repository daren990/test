package org.bloomberg.simulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.quickfixj.jmx.JmxExporter;

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
import quickfix.SocketInitiator;
import quickfix.Message.Header;
import quickfix.field.BeginString;
import quickfix.field.HeartBtInt;
import quickfix.field.ResetSeqNumFlag;
import quickfix.fix44.Logon;


/**
 * Bloomberg simulator
 *
 */
public class BloombergSimulatorApp 
{
    public static void main( String[] args )
    {
    	initializeBloombergInitiator();
    }

	private static void initializeBloombergInitiator() {
		SessionSettings settings=null;
		Initiator bbergInitiator=null;
		try {
			
			settings = new SessionSettings("bloomberg_initiator.properties");
		
	    MessageStoreFactory storeFactory = new FileStoreFactory(settings);
	    LogFactory logFactory = new FileLogFactory(settings);
	    MessageFactory messageFactory = new DefaultMessageFactory();
	
	    BloombergInitiatorApp bbergInitiatorApp = new BloombergInitiatorApp();
	    bbergInitiator = new SocketInitiator(bbergInitiatorApp, storeFactory, settings, logFactory, messageFactory);
		
		JmxExporter exporter = new JmxExporter();
        exporter.register(bbergInitiator);				  
        
        bbergInitiator.start();
        SessionID sessionId = bbergInitiator.getSessions().get(0);
        sendLogonRequest(sessionId);
        int i = 0;
        do {
            try {
                Thread.sleep(10000);
                System.out.println(bbergInitiator.isLoggedOn());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        } while ((!bbergInitiator.isLoggedOn()) && (i < 30));
    } catch (ConfigError e) {
        e.printStackTrace();
    } catch (SessionNotFound e) {
        e.printStackTrace();
    } catch (Exception exp) {
        exp.printStackTrace();
    } finally {
        if (bbergInitiator != null) {
        //	bbergInitiator.stop(true);
        }
    }
     //   Thread.sleep(10000);
//    MessageStore msgStore = new MessageStore();
//		//gets trade message
//		Message tradeMsg = msgStore.getMessage();
//     //   SessionID ses=bbergInitiator.getSessions().get(0);
//        bbergInitiatorApp.toApp(tradeMsg, ses);
       //   sendMsg(ses,tradeMsg);
		try{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
       while (true) {
            System.out.println("type #quit to quit");
            String value = in.readLine();
            if (value != null) {
              if (value.equals("#quit")) {
                    break;
                } 
            }
        }
        bbergInitiator.stop();
        System.exit(0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	private static void sendMsg(SessionID ses, Message tradeMsg) {
		try {
			Session.sendToTarget(tradeMsg,ses);
		} catch (SessionNotFound e) {
			// TODO Auto-generated catch block
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
