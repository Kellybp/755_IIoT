import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class OPFileWriter {
    private final Logger mLogger = LoggerFactory.getLogger(OPFileWriter.class.getName());

    private KafkaConsumer<String, String> mConsumer;
    private static String previousRecord = "";

    OPFileWriter(String bootstrapServer, String groupId, String topic) {

        Properties props = consumerProps(bootstrapServer, groupId);
        mConsumer = new KafkaConsumer<>(props);
        mConsumer.subscribe(Collections.singletonList(topic));
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        String server = "127.0.0.1:9092";
        String groupId = "file_writer";
        String topic = "iiot_output";

        new OPFileWriter(server, groupId, topic).run();
    }

    public void run() {
        try {

            while (true) {
                ConsumerRecords<String, String> records = mConsumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    mLogger.info(" Value: " + record.value());
                    writeToFile(record.value());
                }
            }
        } catch (WakeupException | IOException e) {
            mLogger.info("Received shutdown signal!");
        } finally {
            mConsumer.close();
        }
    }

    private static void writeToFile(String data) throws IOException {

//        String str = "Hello";

        if (null != data && !data.isEmpty()) {

            String key = data.split(";")[0];
            if (null != key && !key.isEmpty() && !previousRecord.equals(key)) {

                FileWriter fw = new FileWriter("outputData.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(data);
                bw.newLine();
                bw.close();
                previousRecord = key;
            }
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
