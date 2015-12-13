package com.zeejfps.jpaint;

public class ContextManager {

	private Context currentContext;
	
	public ContextManager() {
		
	}
	
	public Context getCurrentContext() {
		return currentContext;
	}
	
	public void switchContext(Context context) {
		currentContext = context;
	}

}
