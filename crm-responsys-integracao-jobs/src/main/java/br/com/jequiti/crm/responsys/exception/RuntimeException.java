package br.com.jequiti.crm.responsys.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuntimeException extends Exception {

	private static final long serialVersionUID = 758372394639574417L;
	private static final Logger logger = LoggerFactory.getLogger(RuntimeException.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private String msg;

	public RuntimeException(String msg) {
		
		super(msg);
		this.msg = msg;

		logger.info(msg , dateFormat.format(new Date()));
	}

	public String getMessage() {
		
		return msg;
	}
}
