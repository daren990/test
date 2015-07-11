package org.bloomberg.simulator;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.concurrent.ArrayBlockingQueue;

import org.apache.commons.io.IOUtils;

import quickfix.DataDictionary;
import quickfix.DefaultMessageFactory;
import quickfix.MessageFactory;
import quickfix.MessageUtils;
import quickfix.Message;

public class MessageStore {

	ArrayBlockingQueue<Message> messageQueue = new ArrayBlockingQueue<Message>(100,true);
	
	public MessageStore(){
		/*	try{
		InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream("TradeMsg.xml");
		 MessageFactory messageFactory = new DefaultMessageFactory();
		 StringWriter writer = new StringWriter();
		 IOUtils.copy(in, writer, "UTF-8");
		 String theString = writer.toString();
		 
		 InputStream dictIn = this.getClass().getClassLoader()
	                .getResourceAsStream("TradeMsg.xml");
		 DataDictionary dict = new DataDictionary(dictIn);
		 
		 Message msg = MessageUtils.parse(messageFactory, dict, theString);
		 for(int i=0;i<10;i++)
		   messageQueue.put(msg);
		 
		}catch(Exception e){
			e.printStackTrace();
		} */
			try{
			  DataDictionary dd = new DataDictionary("FIX44.xml");
			  
		        Message m = new Message("8=FIX.4.4\0019=247\00135=s\00134=1\001"
		                + "49=sender\00152=20060319-09:08:20.881\001"
		                + "56=target\00122=8\00140=2\00144=9\00148=ABC\00155=ABC\001"
		                + "60=20060319-09:08:19\001548=184214\001549=2\001"
		                + "550=0\001552=2\00154=1\001453=2\001448=8\001447=D\001"
		                + "452=4\001448=AAA35777\001447=D\001452=3\00138=9\00154=2\001"
		                + "453=2\001448=8\001447=D\001452=4\001448=aaa\001447=D\001"
		                + "452=3\00138=9\00110=052\001", dd);
		        messageQueue.put(m); 
		        
			}catch(Exception e){
				e.printStackTrace();
			}
		 
	}
	
	public Message getMessage(){
		return messageQueue.poll();
	}
	
}
