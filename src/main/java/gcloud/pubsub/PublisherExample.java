package gcloud.pubsub;

/**
 * Created by Omar.Rivera on 11/1/23.
 *
 * @author Omar.Rivera
 */


import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import io.smallrye.mutiny.infrastructure.Infrastructure;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PublisherExample {
    public static void main(String... args) throws Exception {
        // TODO(developer): Replace these variables before running the sample.
        String projectId =  "eng-vizix-cloud";
        String topicId = "eng-vizix-cloud-serverless-storage";

        publisherExample(projectId, topicId);
    }

    public static void publisherExample(String projectId, String topicId)
            throws IOException, ExecutionException, InterruptedException {
        TopicName topicName = TopicName.of(projectId, topicId);

        Publisher publisher = null;
        try {
            // Create a publisher instance with default settings bound to the topic
            publisher = Publisher.newBuilder(topicName).build();

            String message = "Hello Zoe!";
            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).setMessageId("lalalallalalalalalallaaaa").build();

            // Once published, returns a server-assigned message id (unique within the topic)
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            ApiFutures.addCallback(messageIdFuture, new ApiFutureCallback<String>() {
                @Override
                public void onFailure(Throwable throwable) {

                }

                @Override
                public void onSuccess(String s) {

                }
            }, Infrastructure.getDefaultExecutor());

            String messageId = messageIdFuture.get();
            System.out.println("Published message ID: " + messageId);
        } finally {
            if (publisher != null) {
                // When finished with the publisher, shutdown to free up resources.
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }
}
