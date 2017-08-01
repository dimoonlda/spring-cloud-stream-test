package pl.piomin.service.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;

public class ServiceInstanceUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceInstanceUtil.class);

	private ServiceInstanceUtil() {
		throw new IllegalAccessError("Utility class");
	}

	/**
	 * Generates instance ID with prefix.
	 * Instance id consists hash of host name and network interface's mac addresses.
	 * @param prefix prefix.
	 * @return generated instance ID.
	 */
	public static String generateInstanceId(String prefix) {
		String hostName = "unknown";
		try {
			InetAddress address = InetAddress.getLocalHost();
			hostName = address.getHostName();
		} catch (UnknownHostException e) {
			LOGGER.error("Unknown host name.", e);
		}
		StringBuilder macs = new StringBuilder(hostName);

		ArrayList<NetworkInterface> networkInterfaces = null;
		try {
			networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
		} catch (SocketException e) {
			LOGGER.error("Couldn't get list of network interfaces.", e);
		}

		if (networkInterfaces != null) {
			for (NetworkInterface networkInterface : networkInterfaces) {
				byte[] adr = null;
				try {
					adr = networkInterface.getHardwareAddress();
				} catch (SocketException e) {
					LOGGER.error("Couldn't get MAC address.", e);
				}
				if (adr == null || adr.length != 6)
					continue;
				String mac = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
						adr[0], adr[1], adr[2], adr[3], adr[4], adr[5]);
				macs.append(mac);
			}
		}

		String hash = DigestUtils.md5Hex(macs.toString());
		return prefix + "_" + hash;
	}
}
