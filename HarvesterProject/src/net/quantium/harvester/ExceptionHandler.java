package net.quantium.harvester;

import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionHandler implements UncaughtExceptionHandler {

	private Main main;
	
	public ExceptionHandler(Main main) {
		this.main = main;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable e) {
		main.writeCrashlog(e);
		System.exit(1);
	}

}
