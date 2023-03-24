package gcloud.pubsub;

/**
 * Created by Omar.Rivera on 9/2/23.
 *
 * @author Omar.Rivera
 */


import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.cloud.pubsub.v1.TopicAdminClient;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.Encoding;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class PublishingAvroRecordExample2 {

    public static void main(String... args) throws Exception {
        // TODO(developer): Replace these variables before running the sample.
        String projectId =  "eng-vizix-cloud";
        // Use a topic created with an Avro schema.
        String topicId = "dev-stock-on-hand-notification";

        publishAvroRecordsExample(projectId, topicId);
//        publishAvroRecordsExample(projectId, topicId);
//        publishAvroRecordsExample(projectId, topicId);
//        publishAvroRecordsExample(projectId, topicId);
//        publishAvroRecordsExample(projectId, topicId);
    }

    public static void publishAvroRecordsExample(String projectId, String topicId)
            throws IOException, ExecutionException, InterruptedException {

        Encoding encoding = null;

        TopicName topicName = TopicName.of(projectId, topicId);

        // Get the topic encoding type.
        try (TopicAdminClient topicAdminClient = TopicAdminClient.create()) {
            encoding = topicAdminClient.getTopic(topicName).getSchemaSettings().getEncoding();
        }

        // Instantiate an avro-tools-generated class defined in `us-states.avsc`.
        StockOnHandNotification state = StockOnHandNotification.newBuilder()
                .setId("46c0d964-96da-4ae9-ae35-62183aa8480f")
                .setTenant("PERN")
                .setSiteCode("21")
                .setDate(1677598007000L)
                .setFileName("file_name_processed.json")
                .setSource("YTEM-SOH-SILVER")
                .setNumberElements(100)
                .setStatus(StatusType.FINISHED)
                .build();

        Publisher publisher = null;

        block:
        try {
            publisher = Publisher.newBuilder(topicName).build();

            // Prepare to serialize the object to the output stream.
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            Encoder encoder = null;

            // Prepare an appropriate encoder for publishing to the topic.
            switch (encoding) {
                case BINARY:
                    System.out.println("Preparing a BINARY encoder...");
                    encoder = EncoderFactory.get().directBinaryEncoder(byteStream, /*reuse=*/ null);
                    break;

                case JSON:
                    System.out.println("Preparing a JSON encoder...");
                    encoder = EncoderFactory.get().jsonEncoder(StockOnHandNotification.getClassSchema(), byteStream);
                    break;

                default:
                    break block;
            }

            // Encode the object and write it to the output stream.
            state.customEncode(encoder);
            encoder.flush();

            // Publish the encoded object as a Pub/Sub message.
            ByteString data = ByteString.copyFrom(byteStream.toByteArray());
            PubsubMessage message = PubsubMessage.newBuilder().setData(data).build();
            System.out.println("Publishing message: " + message);

            ApiFuture<String> future = publisher.publish(message);
            System.out.println("Published message ID: " + future.get());

        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }
}