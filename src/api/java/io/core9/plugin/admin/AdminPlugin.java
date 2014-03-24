package io.core9.plugin.admin;

import io.core9.plugin.server.handler.Middleware;

public interface AdminPlugin extends Middleware {
	
	String getControllerName();
	
}
