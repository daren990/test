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
import quickfix.fix44.MessageCracker;

public class BloombergAcceptorApp  implements Application{
	private BlockingQueue<Message> queue;
	
	public BloombergAcceptorApp(ArrayBlockingQueue<Message> messageQueue) {
		this.queue = messageQueue;
	}

	public void fromAdmin(Message arg0, SessionID arg1) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		// TODO Auto-generated method stub
		
	}

	public void fromApp(Message msg, SessionID sessionId) throws FieldNotFound,
			IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
		System.out.println("Received bloomberg message.." );
//		MessageStore msgStore = MessageStore.getInstance();
		//gets trade message
		
	//	ArrayBlockingQueue<Message> messageQueue = msgStore.getMessageCollection();
	    try {
			queue.put(msg);
			System.out.println("Added the received Bloomberg message to the Message Store");
		} catch (InterruptedException e) {
			System.out.println("Exception while putting the bloomberg message in the queue");
			e.printStackTrace();
		}
			
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
