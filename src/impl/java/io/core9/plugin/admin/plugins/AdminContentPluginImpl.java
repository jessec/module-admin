package io.core9.plugin.admin.plugins;

import io.core9.plugin.admin.AbstractAdminPlugin;
import io.core9.plugin.server.request.Request;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class AdminContentPluginImpl extends AbstractAdminPlugin implements AdminContentPlugin {
	
	@InjectPlugin
	private AdminContentRepository repository;
	
	@Override
	public String getControllerName() {
		return "content";
	}
	
	@Override
	public void process(Request request) {
		request.getResponse().setStatusCode(404);
	}
	
	@Override
	public void process(Request request, String type) {
		switch(request.getMethod()) {
		case POST:
			request.getResponse().sendJsonMap(repository.createContent(request, type));
			break;
		default:
			request.getResponse().sendJsonArray(repository.getContentList(request, type));
		}
	}
	
	@Override
	public void process(Request request, String type, String id) {
		switch(request.getMethod()) {
		case PUT:
			request.getResponse().sendJsonMap(repository.updateContent(request, type, id));
			break;
		case DELETE:
			request.getResponse().sendJsonMap(repository.deleteContent(request, type, id));
			break;
		default:
			request.getResponse().sendJsonMap(repository.readContent(request, type, id));
		}
	}

}
