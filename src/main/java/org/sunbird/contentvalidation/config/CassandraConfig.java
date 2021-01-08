package org.sunbird.contentvalidation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Value("${spring.data.cassandra.keyspace-name}")
	private String bodhiKeySpaceName;

	@Value("${spring.data.cassandra.contact-points}")
	private String bodhiContactPoints;

	@Value("${spring.data.cassandra.port}")
	private int bodhiPort;

	@Bean
	@Override
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(bodhiContactPoints);
		cluster.setPort(bodhiPort);
		cluster.setMetricsEnabled(false);
		return cluster;
	}

	@Override
	protected String getKeyspaceName() {
		return bodhiKeySpaceName;
	}
}
