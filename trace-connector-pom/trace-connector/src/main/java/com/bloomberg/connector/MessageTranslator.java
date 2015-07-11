package com.bloomberg.connector;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.field.*;
import quickfix.fix43.component.Parties;
import quickfix.fix44.Message;
import quickfix.fix44.TradeCaptureReport;
import quickfix.fix50.component.TrdCapRptSideGrp;

public class MessageTranslator {

	public Message translateMessage(quickfix.Message msg){
		//MsgType=AE for TradeCaptureReport
		TradeCaptureReport translatedMsg = new  TradeCaptureReport();
		//generate unique id for TradeReportID
	//	String id = IdGenerator.getUniqueId();
		translatedMsg.setField( new TradeReportID("123"));
		translatedMsg.setField( new TradeReportTransType(0)); //  0=New
		translatedMsg.setField( new TradeReportType(0));  //0=submit
		translatedMsg.setField( new PreviouslyReported(Boolean.FALSE));  //valid value is always 'NO' by FINRA
		try {
			translatedMsg.setField((SecurityID)msg.getField(new SecurityID()));
			translatedMsg.setField((SecurityIDSource)msg.getField(new SecurityIDSource()));
			translatedMsg.setField((LastQty)msg.getField(new LastQty()));
			translatedMsg.setField((LastPx)msg.getField(new LastPx()));
			translatedMsg.setField((TradeDate)msg.getField(new TradeDate()));
			translatedMsg.setField(new TransactTime(getCurrentTimeInUTC()));
			translatedMsg.setField((SettlDate)msg.getField(new SettlDate()));
			
			translatedMsg.addGroup(getSideInfoGroup(msg));
			
			//ExecutionTime, user defined field required by FINRA
			//Execution time (in UTC/GMT). Format: HH:MM:SS
			
		} catch (FieldNotFound e) {
			System.out.println("Field not found: "+e.getMessage());
			e.printStackTrace();
		}
		
		return translatedMsg;
	}
	
	private Group getSideInfoGroup(quickfix.Message msg) {
		TrdCapRptSideGrp.NoSides rptSideGrp = new TrdCapRptSideGrp.NoSides();
		//Always set value to 2. One side for the Reporting party and one side for the Contra party.
		
		try {
			rptSideGrp.set((Side)msg.getField(new Side()));
			rptSideGrp.set((OrderID)msg.getField(new OrderID())); //required by FIX, but can be ignored
			
			//populate party info required by INFRA for new trade submission
		/*	quickfix.fix50.component.Parties ptySet = new quickfix.fix50.component.Parties();
			//Identifier for the type of party defined in PartyRole. Either an MPID or a Clearing Account number or “C” for customer on the contra side.
			ptySet.setField(new PartyID());
			//C = Generally accepted market participant identifier (e.g. FINRA mnemonic)
			ptySet.setField(new PartyIDSource('C'));
			//Valid values: 1 = Executing Firm  7 = Entering Firm	14 = Giveup Firm 17 = Contra Firm	83 = Clearing Account (required) 
			ptySet.setField(new PartyRole());	
			rptSideGrp.set(ptySet); */
			
			//OrderCapacity, required by FINRA
			//Designates the capacity of the reporting/contra party. Valid values:
			// A = Agency P = Principal
			//rptSideGrp.set(new OrderCapacity());
			
		} catch (FieldNotFound e) {
			System.out.println("Field not found: "+e.getMessage());
			e.printStackTrace();
		}
		
		
		return rptSideGrp;
	}

	private Date getCurrentTimeInUTC() {
		
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("YYYYMMDD-HH:MM:SS");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		//Local time zone   
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("YYYYMMDD-HH:MM:SS");
		Date currentDtGMT=new Date();
		//Time in GMT
		try {
			currentDtGMT = dateFormatLocal.parse( dateFormatGmt.format(new Date()) );
		} catch (ParseException e) {
			System.out.println("Error while formatting the date.."+e.getMessage());
			e.printStackTrace();
		}
		return currentDtGMT;
	}


	
}