package io.core9.plugin.admin.plugins;

import io.core9.plugin.admin.GUID;
import io.core9.plugin.database.mongodb.MongoDatabase;
import io.core9.plugin.server.VirtualHost;
import io.core9.plugin.server.request.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class AdminConfigRepositoryImpl implements AdminConfigRepository {
	
	@InjectPlugin
	private MongoDatabase database;
		
	@Override
	public List<Map<String,Object>> getConfigList(Request request, String configtype) {
		return getConfigList(request.getVirtualHost(), configtype);
	}
	
	@Override
	public List<Map<String,Object>> getConfigList(VirtualHost vhost, String configtype) {
		Map<String, Object> query = new HashMap<String,Object>();
		query.put("configtype", configtype);
		return database.getMultipleResults((String) vhost.getContext("database"), vhost.getContext("prefix") + "configuration", query);
	}
	
	@Override
	public Map<String, Object> createConfig(Request request, String configtype) {
		return createConfig(request.getVirtualHost(), configtype, request.getBodyAsMap());
	}
	
	@Override
	public Map<String, Object> createConfig(VirtualHost vhost, String configtype, Map<String, Object> doc) {
		if(doc.get("_id") == null) {
			doc.put("_id", GUID.getUUID());
		}
		doc.put("configtype", configtype);
		database.upsert((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + "configuration", doc, doc);
		return database.getSingleResult((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + "configuration", doc);
	}
	
	@Override
	public Map<String, Object> readConfig(Request request, String id) {
		return readConfig(request.getVirtualHost(), id);
	}
	
	@Override
	public Map<String, Object> readConfig(VirtualHost vhost, String id) {
		return database.findByID((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + "configuration", id);
	}	
	
	@Override
	public Map<String, Object> updateConfig(Request request, String configtype, String id) {
		return updateConfig(request.getVirtualHost(), configtype, id, request.getBodyAsMap());
	}
		
	@Override
	public Map<String, Object> updateConfig(VirtualHost vhost, String configtype, String id, Map<String, Object> doc) {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("configtype", configtype);
		query.put("_id", id);
		database.upsert((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + "configuration", doc, query);
		return database.getSingleResult((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + "configuration", query);
	}
	
	@Override
	public Map<String, Object> deleteConfig(Request request, String type, String id) {
		return deleteConfig(request.getVirtualHost(), type, id);
	}
	
	@Override
	public Map<String, Object> deleteConfig(VirtualHost vhost, String type, String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> query = new HashMap<String, Object>();
			query.put("configtype", type);
			query.put("_id", id);
			database.delete((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + "configuration", query);
			result.put("status", "success");
		} catch(Exception e) {
			result.put("status", "failure");
		}
		return result;
	}

	@Override
	public List<String> getConfigTypes(VirtualHost vhost) {
		return database.getDistinctStrings((String) vhost.getContext("database"), vhost.getContext("prefix") + "configuration", "configtype", new HashMap<String,Object>());
	}
}
