package io.core9.plugin.admin.plugins;

import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.server.VirtualHost;
import io.core9.plugin.server.request.Request;

import java.util.List;
import java.util.Map;

public interface AdminConfigRepository extends Core9Plugin {
	
	/**
	 * Return the configuration objects
	 * @param request
	 * @return
	 */
	List<Map<String,Object>> getConfigList(Request request, String configtype);
	
	/**
	 * Return the configuration objects
	 * @param vhost
	 * @param configtype
	 * @return
	 */
	List<Map<String,Object>> getConfigList(VirtualHost vhost, String configtype);
	
	/**
	 * Add a new configuration object
	 * @param request
	 * @param type
	 * @return 
	 */
	Map<String, Object> createConfig(Request request, String configtype);
	
	/**
	 * Add a new configuration object
	 * @param vhost
	 * @param configtype
	 * @return
	 */
	Map<String, Object> createConfig(VirtualHost vhost, String configtype, Map<String, Object> doc);

	/**
	 * Get an existing configuration item
	 * @param request
	 * @param type
	 * @param id
	 * @return
	 */
	Map<String, Object> readConfig(Request request, String id);
	
	/**
	 * Get an existing configuration item
	 * @param vhost
	 * @param id
	 * @return
	 */
	Map<String, Object> readConfig(VirtualHost vhost, String id);
	
	/**
	 * Save a configuration type
	 * @param request
	 * @param configtype
	 * @return
	 */
	Map<String, Object> updateConfig(Request request, String configtype, String id);
	
	/**
	 * Save a configuration type
	 * @param vhost
	 * @param configtype
	 * @param id
	 * @return
	 */
	Map<String, Object> updateConfig(VirtualHost vhost, String configtype, String id, Map<String, Object> doc);
	
	/**
	 * Remove an existing configuration item
	 * @param request
	 * @param type
	 * @param id
	 * @return
	 */
	Map<String, Object> deleteConfig(Request request, String type, String id);
	
	/**
	 * Remove an existing configuration item
	 * @param vhost
	 * @param type
	 * @param id
	 * @return
	 */
	Map<String, Object> deleteConfig(VirtualHost vhost, String type, String id);
	
	/**
	 * Get a list of the current config types
	 */
	List<String> getConfigTypes(VirtualHost vhost);

}
