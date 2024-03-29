package org.jinn.zookeeper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ZkUtil {

	public static ZkConfig loadZkConfig() {
		Properties properties;
		try {
			properties = ZkUtil.getResourceAsProperties("config.properties",
					"GBK");
			final ZkConfig zkConfig = new ZkConfig();
			if (StringUtils.isNotBlank(properties.getProperty("zk.zkConnect"))) {
				zkConfig.zkConnect = properties.getProperty("zk.zkConnect");
			}

			if (StringUtils.isNotBlank(properties
					.getProperty("zk.zkSessionTimeoutMs"))) {
				zkConfig.zkSessionTimeoutMs = Integer.parseInt(properties
						.getProperty("zk.zkSessionTimeoutMs"));
			}

			if (StringUtils.isNotBlank(properties
					.getProperty("zk.zkConnectionTimeoutMs"))) {
				zkConfig.zkConnectionTimeoutMs = Integer.parseInt(properties
						.getProperty("zk.zkConnectionTimeoutMs"));
			}

			if (StringUtils.isNotBlank(properties
					.getProperty("zk.zkSyncTimeMs"))) {
				zkConfig.zkSyncTimeMs = Integer.parseInt(properties
						.getProperty("zk.zkSyncTimeMs"));
			}

			return zkConfig;
		} catch (final IOException e) {
//			 log.error("zk failure", e);
			return null;
		}
	}

	public static Properties getResourceAsProperties(String resource,
			String encoding) throws IOException {
		InputStream in = null;
		try {
			in = getResourceAsStream(resource);
		} catch (IOException e) {
			File file = new File(resource);
			if (!file.exists()) {
				throw e;
			}
			in = new FileInputStream(file);
		}

		Reader reader = new InputStreamReader(in, encoding);
		Properties props = new Properties();
		props.load(reader);
		in.close();
		reader.close();

		return props;

	}

	public static InputStream getResourceAsStream(String resource)
			throws IOException {
		InputStream in = null;
		ClassLoader loader = ZkUtil.class.getClassLoader();
		if (loader != null) {
			in = loader.getResourceAsStream(resource);
		}
		if (in == null) {
			in = ClassLoader.getSystemResourceAsStream(resource);
		}
		if (in == null) {
			throw new IOException("Could not find resource " + resource);
		}
		return in;
	}
}
