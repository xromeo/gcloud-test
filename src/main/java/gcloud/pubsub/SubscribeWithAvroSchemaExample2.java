package gcloud.pubsub;

/**
 * Created by Omar.Rivera on 9/2/23.
 *
 * @author Omar.Rivera
 */


import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SubscribeWithAvroSchemaExample2 {

    public static void main(String... args) throws Exception {
        // TODO(developer): Replace these variables before running the sample.
        String projectId =  "eng-vizix-cloud";
        // Use an existing subscription.
        String subscriptionId = "dev-stock-on-hand-notification-inbound-to-gold";

        subscribeWithAvroSchemaExample(projectId, subscriptionId);
    }

    public static void subscribeWithAvroSchemaExample(String projectId, String subscriptionId) {

        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of(projectId, subscriptionId);

        // Prepare a reader for the encoded Avro records.
        SpecificDatumReader<StockOnHandNotification> reader = new SpecificDatumReader<>(StockOnHandNotification.getClassSchema());

        // Instantiate an asynchronous message receiver.
        MessageReceiver receiver =
                (PubsubMessage message, AckReplyConsumer consumer) -> {
                    ByteString data = message.getData();

                    // Get the schema encoding type.
                    String encoding = message.getAttributesMap().get("googclient_schemaencoding");

                    // Send the message data to a byte[] input stream.
                    InputStream inputStream = new ByteArrayInputStream(data.toByteArray());

                    Decoder decoder = null;

                    // Prepare an appropriate decoder for the message data in the input stream
                    // based on the schema encoding type.
                    block:
                    try {
                        switch (encoding) {
                            case "BINARY":
                                decoder = DecoderFactory.get().directBinaryDecoder(inputStream, /*reuse=*/ null);
                                System.out.println("Receiving a binary-encoded message:");
                                break;
                            case "JSON":
                                decoder = DecoderFactory.get().jsonDecoder(StockOnHandNotification.getClassSchema(), inputStream);
                                System.out.println("Receiving a JSON-encoded message:");
                                break;
                            default:
                                break block;
                        }

                        // Obtain an object of the generated Avro class using the decoder.
                        StockOnHandNotification sohMetadata = reader.read(null, decoder);
                        System.out.println(sohMetadata.getFileName() + " id:  " + sohMetadata.getId());

                    } catch (IOException e) {
                        System.err.println(e);
                    }

                    // Ack the message.
                    consumer.ack();
                };

        Subscriber subscriber = null;
        try {
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
            subscriber.startAsync().awaitRunning();
            System.out.printf("Listening for messages on %s:\n", subscriptionName.toString());
            subscriber.awaitTerminated(30, TimeUnit.SECONDS);
        } catch (TimeoutException timeoutException) {
            subscriber.stopAsync();
        }
    }
}