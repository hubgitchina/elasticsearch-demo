# spring-data-elasticsearch 使用说明
1.ElasticSearch安装部署

注意使用2.3版本，否则得设置内存等，同时高版本的 ES，目前稳定版的 SpringBoot 1.5.13 的 SpringData 并不支持。
此处是在docker容器中安装部署。

1)下载elasticsearch

docker pull elasticsearch :2.3

2)下载elasticsearch-head

docker pull mobz/elasticsearch-head:5

3)设置elasticsearch配置文件

此处直接在docker容器服务器根目录建立/elasticsearch/es.yml。es.yml内容如下：

#集群名称 所有节点要相同

cluster.name: "mangues_es"

#本节点名称

node.name: master

#作为master节点

node.master: true

#是否存储数据

node.data: true

#head插件设置

http.cors.enabled: true

http.cors.allow-origin: "*"

#设置可以访问的ip 这里全部设置通过

network.bind_host: 0.0.0.0

#设置节点 访问的地址 设置master所在机器的ip

network.publish_host: 192.168.105.81


4)启动elasticsearch

docker run -d --name es -p 9200:9200 -p 9300:9300 -v elasticsearch/es.yml:/usr/share/elasticsearch/config/elasticsearch.yml -v /elasticsearch/esdata1:/usr/share/elasticsearch/data elasticsearch:2.3

5)开启elasticsearch-head

docker run -p 9100:9100 mobz/elasticsearch-head:5

启动后，在浏览器打开地址 http://192.168.105.81:9100/ 即可查看elasticsearch运行状态信息。
 
2.ElasticSearch-IK中文分词器安装部署

1)进入elasticsearch容器

docker exec -it es /bin/bash

2)进入plugins目录

cd plugins

3)创建ik目录并进入

mkdir ik

cd ik

4)下载elasticsearch-analysis-ik插件

下载之前需要比对ik与elasticsearch的版本适配。 

此处使用elasticsearch2.3，则对应下载ik版本为1.9.5

wget https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v1.9.5/elasticsearch-analysis-ik-1.9.5.zip 

5)解压elasticsearch-analysis-ik安装包

unzip elasticsearch-analysis-ik-1.9.5.zip

6)删除elasticsearch-analysis-ik安装包

rm elasticsearch-analysis-ik-1.9.5.zip

然后重启elasticsearch服务即可。

3.ElasticSearch-IK中文分词说明

Ik具有两种分词模式，一种是最小粒度的ik_max_word ，另一种是最粗粒度的ik_smart 。

1)最小粒度模式ik_max_word

ik_max_word: 会将文本做最细粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,中华人民,中华,华人,人民共和国,人民,,共和国,共和,国,国歌”，会穷尽各种可能的组合。

2)最粗粒度模式ik_smart

ik_smart: 会做最粗粒度的拆分，比如会将“中华人民共和国国歌”拆分为“中华人民共和国,国歌”。
 
4.Spring-data-elasticsearch开发详解

Elasticsearch概念解释：

索引（index）等同于数据库名称

类型（type）等同于数据库表

文档（document）等同于表内一条数据

使用elasticsearch做全文检索，核心原则就是建库建表插入数据，再做相应的增删改查。

1)pom.xml依赖

在pom.xml加入spring-data-elasticsearch依赖

                <dependency>

			<groupId>org.springframework.boot</groupId>

			<artifactId>spring-boot-starter-data-elasticsearch</artifactId>

		</dependency>

2)application.properties配置

在application.properties文件中增加elasticsearch配置

#elasticsearch集群名称，默认是elasticsearch

spring.data.elasticsearch.cluster-name=mangues_es
 
#节点的地址 注意api模式下端口号是9300，千万不要写成9200

spring.data.elasticsearch.cluster-nodes=192.168.105.81:9300
 
#是否开启本地存储

spring.data.elasticsearch.repositories.enable=true
 
3)创建客户端连接

使用spring-data-elasticsearch进行操作之前，都需要先获取客户端连接，用完要关闭连接，释放资源。

创建连接：

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

关闭连接：

client.close();
 
4)创建索引

根据需要创建的索引名称，创建该索引。 

5)创建类型及类型结构

此处为方便动态创建类型和类型结构，使用json参数构建类型结构的方式来实现。

请求参数：

{

	"index":"buz_goods",

	"type":"goods",

	"fieldList":[

		{

			"fieldName":"goods_id",

			"type":"String",

			"index":"",

			"analyzer":"",

			"searchAnalyzer":""

		},

			{

			"fieldName":"goods_name",

			"type":"String",

			"index":"analyzed",

			"analyzer":"ik_max_word",

			"searchAnalyzer":"ik_max_word"

		},

			{

			"fieldName":"goods_desc",

			"type":"String",

			"index":"analyzed",

			"analyzer":"ik_max_word",

			"searchAnalyzer":"ik_max_word"

		},

			{

			"fieldName":"goods_price",

			"type":"Double",

			"index":"not_analyzed",

			"analyzer":"",

			"searchAnalyzer":""

		},

			{

			"fieldName":"goods_salenum",

			"type":"Long",

			"index":"not_analyzed",

			"analyzer":"",

			"searchAnalyzer":""

		},

			{

			"fieldName":"goods_shelftime",

			"type":"Date",

			"format":"yyyy-MM-dd HH:mm:ss",

			"index":"not_analyzed",

			"analyzer":"",

			"searchAnalyzer":""

		}

	]

}
 
index即在哪个索引下创建类型

type即创建类型的名称

fieldList为类型结构

fieldName为字段名称

type为字段类型

index为是否进行分词处理

analyzer为分词规则

searchAnalyzer为搜索时使用的分词规则
