package io.core9.plugin.admin;

import io.core9.core.boot.CoreBootStrategy;
import io.core9.plugin.server.HostManager;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.vertx.VertxServer;
import io.core9.plugin.session.Session;
import io.core9.plugin.session.SessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.xeoh.plugins.base.Plugin;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import org.apache.commons.lang3.ClassUtils;
import org.apache.shiro.subject.Subject;

@PluginImplementation
public class AdminDispatcherImpl extends CoreBootStrategy implements AdminDispatcher {

	private static final Map<String, AdminPlugin> adminplugins = new HashMap<String, AdminPlugin>();

	@InjectPlugin
	private VertxServer server;
	
	@InjectPlugin
	private HostManager manager;

	@InjectPlugin
	private SessionManager sessionManager;

	@Override
	public Integer getPriority() {
		return 720;
	}

	@Override
	public void processPlugins() {
		for (Plugin plugin : this.registry.getPlugins()) {
			List<Class<?>> interfaces = ClassUtils.getAllInterfaces(plugin.getClass());
			if (interfaces.contains(AdminPlugin.class)) {
				AdminPlugin aPlugin = (AdminPlugin) plugin;
				adminplugins.put(aPlugin.getControllerName(), aPlugin);
				System.out.println("Found admin plugin." + aPlugin.getControllerName());
			}
		}
	}

	@Override
	public void execute() {
		server.use("/admin/:controller((/:type)(/:id)?)?", new Middleware() {
			@Override
			public void handle(Request request) {
				Session session = sessionManager.getVertxSession(request, server);
				Subject currentUser = sessionManager.getCurrentUser(session);
				if (currentUser.hasRole("admin") || (System.getProperty("DEBUG") != null && System.getProperty("DEBUG").equals("true"))) {
					adminplugins.get(request.getParams().get("controller")).handle(request);
				} else {
					request.getResponse().setStatusCode(401);
					request.getResponse().setStatusMessage("You are not authorized to view admin data");
				}
			}
		});
		//FIXME Only make install available when a key is presented
		server.use("/install", manager.getInstallationProcedure());
	}
}
