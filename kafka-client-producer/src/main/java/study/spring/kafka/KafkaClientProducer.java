package study.spring.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaClientProducer {

  public static void main(String[] args) {
    Properties configs = new Properties();
    // 클러스터 주소
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, SimpleProducer.BOOTSTRAP_SERVERS);
    // 메시지 키, 값을 직렬화 하기 위한 클래스
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    // 프로듀서 생성
    KafkaProducer<String, String> producer = new KafkaProducer(configs);

    // 카프카 프로커로 데이터를 전송하기 위한 ProducerRecord 생성
    String messageValue = "testMessage1";
    ProducerRecord<String, String> record = new ProducerRecord<>(
        SimpleProducer.TOPIC_NAME,
        messageValue);

    // 전송 - 즉각 전송은 아니고 배치 전송
    producer.send(record, new ProducerCallback());
    SimpleProducer.logger.info("{}", record);
    // 브로커로 전송
    producer.flush();
    producer.close();
  }
}
