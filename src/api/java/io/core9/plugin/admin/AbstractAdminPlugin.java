package io.core9.plugin.admin;

import io.core9.plugin.server.request.Request;

public abstract class AbstractAdminPlugin implements AdminPlugin {
	
	@Override
	public void handle(Request request) {
		String type = (String) request.getParams().get("type");
		String id = (String) request.getParams().get("id");
		if(type == null) {
			process(request);
		} else if (id == null) {
			process(request, type);
		} else {
			process(request, type, id);
		}
	}

	/**
	 * Handle a root request (a request on /admin/:controller)
	 * @param request
	 */
	protected abstract void process(Request request);
	
	/**
	 * Handle a type request (a request on /admin/:controller/:type)
	 * @param request
	 */
	protected abstract void process(Request request, String type);

	/**
	 * Handle an item request (a request on /admin/:controller/:type/:id)
	 * @param request
	 */
	protected abstract void process(Request request, String type, String id);
	
}
