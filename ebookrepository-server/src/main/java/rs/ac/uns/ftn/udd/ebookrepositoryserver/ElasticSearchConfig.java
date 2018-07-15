package rs.ac.uns.ftn.udd.ebookrepositoryserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "rs.ac.uns.ftn.udd.ebookrepositoryserver.repository")
public class ElasticSearchConfig {

    @Value("${elasticsearch.host}")
    private String elasticSearchHost;

    @Value("${elasticsearch.port}")
    private int elasticSearchPort;

    @Value("${elasticsearch.clustername}")
    private String elasticSearchClusterName;

    @Bean
    public Client client() throws UnknownHostException {

        Settings esSettings = Settings.settingsBuilder()
                .put("cluster.name", elasticSearchClusterName)
                .build();

        return TransportClient.builder()
                .settings(esSettings)
                .build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticSearchHost), elasticSearchPort));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException  {
        return new ElasticsearchTemplate(client());
    }

}