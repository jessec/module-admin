package io.core9.plugin.admin.plugins;

import io.core9.plugin.admin.AbstractAdminPlugin;
import io.core9.plugin.server.request.Request;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class AdminConfigPluginImpl extends AbstractAdminPlugin implements AdminConfigPlugin {
	
	@InjectPlugin
	private AdminConfigRepository repository;

	@Override
	public String getControllerName() {
		return "config";
	}

	@Override
	protected void process(Request request) {
		if(request.getParams().get("listtypes") != null) {
			request.getResponse().sendJsonArray(repository.getConfigTypes(request.getVirtualHost()));
		} else {
			request.getResponse().setStatusCode(404);
		}
	}

	@Override
	protected void process(Request request, String type) {
		switch(request.getMethod()) {
		case POST:
			request.getResponse().sendJsonMap(repository.createConfig(request, type));
			break;
		default:
			request.getResponse().sendJsonArray(repository.getConfigList(request, type));
		}
	}

	@Override
	protected void process(Request request, String type, String id) {
		switch(request.getMethod()) {
		case PUT:
			request.getResponse().sendJsonMap(repository.updateConfig(request, type, id));
			break;
		case DELETE:
			request.getResponse().sendJsonMap(repository.deleteConfig(request, type, id));
			break;
		default:
			request.getResponse().sendJsonMap(repository.readConfig(request, id));
		}
	}

}
