package cn.com.ut.util;

import java.net.InetSocketAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class EsServerClient {

	public final static String HOST = "192.168.105.81";

	// http请求的端口是9200，客户端是9300
	public final static int PORT = 9300;

	public static Client getClient() {

		// 客户端设置
		Settings settings = Settings.builder().put("cluster.name", "mangues_es")
				.put("client.transport.sniff", "true").build();

		// 创建客户端
		Client client = TransportClient.builder().settings(settings).build().addTransportAddress(
				new InetSocketTransportAddress(new InetSocketAddress(HOST, PORT)));

		return client;
	}
}
