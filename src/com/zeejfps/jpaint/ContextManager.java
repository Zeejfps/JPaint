package com.zeejfps.jpaint;

public class ContextManager {

	private OldContext currentContext;
	
	public ContextManager() {
		
	}
	
	public OldContext getCurrentContext() {
		return currentContext;
	}
	
	public void switchContext(OldContext context) {
		currentContext = context;
	}

}
