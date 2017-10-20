package com.coffee.common.json.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coffee.common.common.exp.SvcException;

/**
 * @author QM
 */
public final class JsonUtils {

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	private JsonUtils() {
	}

	/**
	 * Convert object (bean, list, array) to json string
	 * 
	 * @param obj
	 *            object
	 * @return String
	 * @throws SvcException
	 *             SvcException
	 */
	public static String obj2Json(final Object obj) throws SvcException {
		try {
			return OBJECT_MAPPER.writeValueAsString(obj);
		} catch (final JsonGenerationException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to json.");
		} catch (final JsonMappingException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to json.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to json.");
		}
	}

	/**
	 * Convert json string to bean
	 * 
	 * @param json
	 *            json
	 * @param clazz
	 *            class
	 * @return T
	 * @throws SvcException
	 *             SvcException
	 */
	public static <T> T json2Mo(final String json, final Class<T> clazz)
			throws SvcException {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (final JsonParseException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to bean.");
		} catch (final JsonMappingException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to bean.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to bean.");
		}
	}

	/**
	 * Convert json string to map
	 * 
	 * @param json
	 *            json
	 * @return Map
	 * @throws SvcException
	 *             SvcException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(final String json)
			throws SvcException {
		try {
			return OBJECT_MAPPER.readValue(json, Map.class);
		} catch (final JsonParseException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to map.");
		} catch (final JsonMappingException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to map.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to map.");
		}
	}

	/**
	 * Convert json array string to map of bean
	 * 
	 * @param json
	 *            json
	 * @param clazz
	 *            class
	 * @return List
	 * @throws SvcException
	 *             SvcException
	 */
	public static <T> Map<String, T> json2Map(final String json,
			final Class<T> clazz) throws SvcException {
		try {
			final Map<String, Map<String, Object>> map = OBJECT_MAPPER
					.readValue(json, new TypeReference<Map<String, T>>() {
					});
			//
			final Map<String, T> result = new HashMap<String, T>();
			for (final Entry<String, Map<String, Object>> entry : map
					.entrySet()) {
				result.put(entry.getKey(), map2Mo(entry.getValue(), clazz));
			}
			return result;
		} catch (final JsonParseException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to map.");
		} catch (final JsonMappingException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to map.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to map.");
		}
	}

	/**
	 * Convert json array string to list of bean
	 * 
	 * @param json
	 *            json
	 * @param clazz
	 *            class
	 * @return List
	 * @throws SvcException
	 *             SvcException
	 */
	public static <T> List<T> json2List(final String json, final Class<T> clazz)
			throws SvcException {
		try {
			final List<Map<String, Object>> list = OBJECT_MAPPER.readValue(
					json, new TypeReference<List<T>>() {
					});
			//
			final List<T> result = new ArrayList<T>();
			for (final Map<String, Object> map : list) {
				result.add(map2Mo(map, clazz));
			}
			return result;
		} catch (final JsonParseException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to list.");
		} catch (final JsonMappingException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to list.");
		} catch (final IOException e) {
			logger.error("", e);
			throw new SvcException("Failed to convert to list.");
		}
	}

	private static <T> T map2Mo(final Map<String, Object> map,
			final Class<T> clazz) {
		return OBJECT_MAPPER.convertValue(map, clazz);
	}
}
