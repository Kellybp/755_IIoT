import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MessageProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String server = "127.0.0.1:9092";
        String topic = "iiot_inputs_test";

        MessageProducer producer = new MessageProducer(server);
        int waterPressure = 0;
        while (true) {
            waterPressure = getRandomNumber(10, 20);
            producer.put(topic, String.valueOf(waterPressure));
            Thread.sleep(2000);
        }


//        producer.close();

    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // Variables

    private final KafkaProducer<String, String> mProducer;
    private final Logger mLogger = LoggerFactory.getLogger(Producer.class);

    // Constructors

    MessageProducer(String bootstrapServer) {
        Properties props = producerProps(bootstrapServer);
        mProducer = new KafkaProducer<>(props);

        mLogger.info("Producer initialized");
    }

    // Public

    void put(String topic, String input) throws ExecutionException, InterruptedException {
        mLogger.info("Put input: " + input);

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, input);
        mProducer.send(record, (recordMetadata, e) -> {
            if (e != null) {
                mLogger.error("Error while producing", e);
                return;
            }

            mLogger.info("Received new meta. Topic: " + recordMetadata.topic()
                    + "; Partition: " + recordMetadata.partition()
                    + "; Offset: " + recordMetadata.offset()
                    + "; Timestamp: " + recordMetadata.timestamp());
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
