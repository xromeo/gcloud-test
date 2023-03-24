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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;


public class PublishingAvroRecordExample {

    public static void main(String... args) throws Exception {
        // TODO(developer): Replace these variables before running the sample.
        String projectId =  "eng-vizix-cloud";
        // Use a topic created with an Avro schema.
        String topicId = "dev-stock-on-hand-populated-metadata";

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
        StockOnHandMetadata state = StockOnHandMetadata.newBuilder()
        .setRetailQTYMatch(false)
                .setRetailProductMateBundle(0)
                .setRetailProductUPCNumber("")
                .setSource("FTPBRIDGESOH")
                .setRetailSOHSKU("1731764106249")
                .setRetailProductImageURL("")
                .setRetailSequence(27372374L)
                .setRetailSOHQTYSR(0)
                .setRetailSOHDepositCode("")
                .setRetailSOHHoldArea(0)
                .setTsCoreIn(1674761975572L)
                .setRetailProductBrand("")
                .setRetailProductPrice("")
                .setId("")
                .setRetailProductLevel1Name("BELEZA")
                .setRetailSOHQTYSF(0)
                .setRetailProductRFIDTagged("")
                .setRetailProductLevel2Name("VULT")
                .setRetailProductLevel1("W")
                .setRetailProductLevel2("")
                .setRetailProductLevel3("WLD")
                .setTsMongoIn(0)
                .setRetailProductLevel4("WLDBB")
                .setRetailProductLevel5("WL")
                .setRetailSOHDateString("")
                .setRetailSOHStoreNumber("60")
                .setRetailProductManufacturer("")
                .setRetailProductUPCs("08443261062493")
                .setRetailSOHClearance(false)
                .setRetailProductStyle("BATOM LÃ\u008DQUIDO")
                .setId$1("0230126_60_1731764106249")
                .setRule("")
                .setRetailProductLevel3Name("")
                .setRetailQTYSF(1)
                .setRetailQTYSR(0)
                .setRetailSFMin(0)
                .setRetailProductDimension("WL")
                .setRetailSOHInTransitQTY(0)
                .setTsEdgeIn(1674761975035L)
                .setRetailProductUOM("")
                .setRetailProductLevel4Name("WLDB")
                .setRetailStoreNumber("60")
                .setRetailProductUPC("08443261062493")
                .setRetailSFMax(0)
                .setRetailProductSKU("1731764106249")
                //.setRetailProductSKU("")
                .setRetailSOHCurrency("")
                .setRetailProductExternalSKU("")
                .setRetailProductLevel5Name("26.99")
                .setRetailSOHReceiptID("")
                .setRetailProductVPN("")
                .setRetailSOHStoreInProcess(0)
                .setRetailSOHDate(1674761975035L)
                .setRetailSOHQTY(0)
                .setRetailSOHDepositName("")
                .setRetailProductSize("NA")
                .setRetailQTY(1)
                .setRetailSOHPrice("")
                .setRetailProductName("BATOM  WL VULT VULT BATOM LIQ MATE EN 00")
                .setRetailSKU("1731764106249")
                .setRetailProductColor("NA").build();

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
                    encoder = EncoderFactory.get().jsonEncoder(StockOnHandMetadata.getClassSchema(), byteStream);
                    break;

                default:
                    break block;
            }

            Map<String, String> attributes = new HashMap<>();
            attributes.put("tenant", "PERN");
            attributes.put("siteCode", "MEGA");
            attributes.put("totalRecords", "50");
            attributes.put("date", "2023-02-17");
            attributes.put("fileName", "IZOE_PREMISE_DESTINATION_20230214_431e73d3-c68d-46d2-8bbc-4e6be48c616b_soh.json");
            attributes.put("transactionId", "fc4b44b2-6ac3-413c-bbd5-b04585e859ae");
            attributes.put("lastMessage", "TRUE");
            attributes.put("firstMessage", "TRUE");



            // Encode the object and write it to the output stream.
            state.customEncode(encoder);
            encoder.flush();

            // Publish the encoded object as a Pub/Sub message.
            ByteString data = ByteString.copyFrom(byteStream.toByteArray());
            PubsubMessage message = PubsubMessage.newBuilder().setData(data).putAllAttributes(attributes).build();
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