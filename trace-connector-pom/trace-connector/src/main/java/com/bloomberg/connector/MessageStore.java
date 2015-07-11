package com.bloomberg.connector;

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
	private static MessageStore store = null;
	
	public static MessageStore getInstance()
	{
	  if (store == null)
	  {
	    synchronized(MessageStore.class) {      
	    	MessageStore inst = store;         
	      if (inst == null){
	         synchronized(MessageStore.class) {  
	        	 store = new MessageStore();               
	         }
	      }
	    }
	  }
	  return store;
	}
	
	public ArrayBlockingQueue<Message>  getMessageCollection(){
		return messageQueue;
	}
	
	public Message getMessage(){
		return messageQueue.poll();
	}
	
}
