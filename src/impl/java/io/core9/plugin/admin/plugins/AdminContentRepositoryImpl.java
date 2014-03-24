package io.core9.plugin.admin.plugins;

import io.core9.plugin.admin.GUID;
import io.core9.plugin.database.mongodb.MongoDatabase;
import io.core9.plugin.server.VirtualHost;
import io.core9.plugin.server.request.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class AdminContentRepositoryImpl implements AdminContentRepository {
	
	@InjectPlugin
	private MongoDatabase database;
	
	@Override
	public List<Map<String, Object>> getContentList(Request request, String contenttype) {
		return getContentList(request.getVirtualHost(), contenttype);
	}
	
	@Override
	public List<Map<String, Object>> getContentList(VirtualHost vhost, String contenttype) {
		List<Map<String,Object>> result = database.getMultipleResults((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + contenttype, new HashMap<String,Object>());
		if(result == null) {
			result = new ArrayList<Map<String,Object>>();
			result.add(new HashMap<String,Object>());
		}
		return result;
	}

	@Override
	public Map<String, Object> createContent(Request request, String contenttype) {
		return createContent(request.getVirtualHost(), contenttype, request.getBodyAsMap());
	}

	@Override
	public Map<String, Object> createContent(VirtualHost vhost, String contenttype, Map<String,Object> doc) {
		if(doc.get("_id") == null) {
			doc.put("_id", GUID.getUUID());
		}
		doc.put("contenttype", contenttype);
		database.upsert((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + contenttype, doc, doc);
		return database.findByID((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + contenttype, (String) doc.get("_id"));
	}

	@Override
	public Map<String, Object> readContent(Request request, String contenttype, String id) {
		return readContent(request.getVirtualHost(), contenttype, id);
	}
	
	@Override
	public Map<String, Object> readContent(VirtualHost vhost, String contenttype, String id) {
		return database.findByID((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + contenttype, id);
	}

	@Override
	public Map<String, Object> updateContent(Request request, String contenttype, String id) {
		return updateContent(request.getVirtualHost(), contenttype, id, request.getBodyAsMap());
	}

	@Override
	public Map<String, Object> updateContent(VirtualHost vhost, String contenttype, String id, Map<String,Object> doc) {
		Map<String, Object> query = new HashMap<String, Object>();
		query.put("contenttype", contenttype);
		query.put("_id", id);
		database.upsert((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + contenttype, doc, query);
		return database.getSingleResult((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + contenttype, query);
	}

	@Override
	public Map<String, Object> deleteContent(Request request, String contenttype, String id) {
		return deleteContent(request.getVirtualHost(), contenttype, id);
	}
	
	@Override
	public Map<String, Object> deleteContent(VirtualHost vhost, String contenttype, String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> query = new HashMap<String, Object>();
			query.put("contenttype", contenttype);
			query.put("_id", id);
			database.delete((String) vhost.getContext("database"), (String) vhost.getContext("prefix") + contenttype, query);
			result.put("status", "success");
		} catch(Exception e) {
			result.put("status", "failure");
		}
		return result;
	}
	
}
