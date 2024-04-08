package br.com.valter.picpaysimplificado.notificacao.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class KafkaConfig {
    @Value("${app.kafka.notificacao.groupId}")
    private String groupId;


    @Bean
    @Primary
    public KafkaProperties kafkaProperties(){
        KafkaProperties props = new KafkaProperties();
        props.setBootstrapServers(List.of("localhost:9092"));
        props.getConsumer().setGroupId(groupId);
        props.getConsumer().setAutoOffsetReset("earliest");
        props.getProducer().setValueSerializer(org.apache.kafka.common.serialization.StringSerializer.class);
        props.getConsumer().setValueDeserializer(org.apache.kafka.common.serialization.StringDeserializer.class);
        props.getConsumer().setEnableAutoCommit(false);

        return props;
    }
}
