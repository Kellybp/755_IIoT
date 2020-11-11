import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String server = "127.0.0.1:9092";
        String outputTopic = "iiot_output";

        KafkaProducer producer = new KafkaProducer(server);
    }

    // Variables

    private final org.apache.kafka.clients.producer.KafkaProducer<String, String> mProducer;
    private final Logger mLogger = LoggerFactory.getLogger(Producer.class);

    // Constructors

    KafkaProducer(String bootstrapServer) {
        Properties props = producerProps(bootstrapServer);
        mProducer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);

        mLogger.info("Producer initialized");
    }

    // Public

    void put(String input) throws ExecutionException, InterruptedException {
        mLogger.info("Put input: " + input);
        String outputTopic = "iiot_output";

        ProducerRecord<String, String> record = new ProducerRecord<>(outputTopic, input);
        mProducer.send(record, (recordMetadata, e) -> {
            if (e != null) {
                mLogger.error("Error while producing", e);
                return;
            }

//            mLogger.info("Received new meta. Topic: " + recordMetadata.topic()
//                    + "; Partition: " + recordMetadata.partition()
//                    + "; Offset: " + recordMetadata.offset()
//                    + "; Timestamp: " + recordMetadata.timestamp());
        }).get();
    }

    void close() {
        mLogger.info("Closing producer's connection");
        mProducer.close();
    }

    // Private

    private Properties producerProps(String bootstrapServer) {
        String serializer = StringSerializer.class.getName();
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serializer);
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializer);

        return props;
    }
}
