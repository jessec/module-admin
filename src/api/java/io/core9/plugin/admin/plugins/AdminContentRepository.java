package io.core9.plugin.admin.plugins;

import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.server.VirtualHost;
import io.core9.plugin.server.request.Request;

import java.util.List;
import java.util.Map;

public interface AdminContentRepository extends Core9Plugin {
	
	/**
	 * Return a list of existing content
	 * @param data
	 */
	List<Map<String, Object>> getContentList(Request request, String contenttype);
	
	/**
	 * Return a list of existing content
	 * @param vhost
	 * @param contenttype
	 * @return
	 */
	List<Map<String, Object>> getContentList(VirtualHost vhost, String contenttype);
	
	/**
	 * Add a new content item
	 * @param request
	 * @param contenttype
	 * @return
	 */
	Map<String, Object> createContent(Request request, String contenttype);
	
	/**
	 * Add a new content item
	 * @param vhost
	 * @param contenttype
	 * @param doc
	 * @return
	 */
	Map<String, Object> createContent(VirtualHost vhost, String contenttype, Map<String,Object> doc);
	
	/**
	 * Return a content item
	 * @param request
	 * @param contenttype
	 * @param id
	 * @return
	 */
	Map<String, Object> readContent(Request request, String contenttype, String id);
	
	/**
	 * Return a content item
	 * @param vhost
	 * @param contenttype
	 * @param id
	 * @return
	 */
	Map<String, Object> readContent(VirtualHost vhost, String contenttype, String id);

	/**
	 * Update a content item
	 * @param request
	 * @param contenttype
	 * @param id
	 * @return
	 */
	Map<String, Object> updateContent(Request request, String contenttype, String id);
	
	/**
	 * Update a content item
	 * @param vhost
	 * @param contenttype
	 * @param id
	 * @param doc
	 * @return
	 */
	Map<String, Object> updateContent(VirtualHost vhost, String contenttype, String id, Map<String, Object> doc);

	/**
	 * Delete a content item
	 * @param request
	 * @param contenttype
	 * @param id
	 * @return
	 */
	Map<String, Object> deleteContent(Request request, String contenttype, String id);
	
	/**
	 * Delete a content item
	 * @param vhost
	 * @param contenttype
	 * @param id
	 * @return
	 */
	Map<String, Object> deleteContent(VirtualHost vhost, String contenttype, String id);

}
