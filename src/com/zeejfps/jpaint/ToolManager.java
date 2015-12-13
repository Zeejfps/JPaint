package com.zeejfps.jpaint;

import com.zeejfps.jpaint.tools.Tool;

public class ToolManager {

	private final ContextManager contextManager;
	
	private Tool currentTool;
	
	public ToolManager(ContextManager contextManager) {
		this.contextManager = contextManager;
	}
	
	public Tool getCurrentTool() {
		return currentTool;
	}
	
	public void switchTool(Tool tool) {
		currentTool = tool;
	}
	
}
