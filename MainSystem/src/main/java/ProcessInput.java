import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ProcessInput {
    private final Logger mLogger = LoggerFactory.getLogger(ProcessInput.class.getName());

    private KafkaConsumer<String, String> mConsumer;

    ProcessInput(String bootstrapServer, String groupId, String topic) {

        Properties props = consumerProps(bootstrapServer, groupId);
        mConsumer = new KafkaConsumer<>(props);
        mConsumer.subscribe(Collections.singletonList(topic));
    }

    public static void main(String[] args) {
        String server = "127.0.0.1:9092";
        String groupId = "main_application";
        String topic = "iiot_inputs_test";

        new ProcessInput(server, groupId, topic).run();
    }

    public void run() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = mConsumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    mLogger.info(" Value: " + record.value());
                    mLogger.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                    System.out.println(record.value());
                }
            }
        } catch (WakeupException e) {
            mLogger.info("Received shutdown signal!");
        } finally {
            mConsumer.close();
        }
    }

    private Properties consumerProps(String bootstrapServer, String groupId) {
        String deserializer = StringDeserializer.class.getName();
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, deserializer);
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return properties;
    }
}
