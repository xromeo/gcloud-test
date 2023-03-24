package gcloud.pubsub;

/**
 * Created by Omar.Rivera on 11/1/23.
 *
 * @author Omar.Rivera
 */


import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.cloud.storage.Notification;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SubscribeAsyncExample {
    public static void main(String... args) throws Exception {
        // TODO(developer): Replace these variables before running the sample.
        String projectId =  "eng-vizix-cloud";
        String subscriptionId = "eng-vizix-cloud-serverless-storage";

        subscribeAsyncExample(projectId, subscriptionId);
        listPubSubNotifications(projectId, subscriptionId);
    }

    public static void subscribeAsyncExample(String projectId, String subscriptionId) {
        ProjectSubscriptionName subscriptionName =
                ProjectSubscriptionName.of(projectId, subscriptionId);

        // Instantiate an asynchronous message receiver.
        MessageReceiver receiver =
                (PubsubMessage message, AckReplyConsumer consumer) -> {
                    // Handle incoming message, then ack the received message.
                    System.out.println("Id: " + message.getMessageId());
                    System.out.println("Data: " + message.getData().toStringUtf8());
                    System.out.println("Attributes: " + message.getAttributesMap());

                    consumer.ack();
                };

        Subscriber subscriber = null;
        try {
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
            // Start the subscriber.
            subscriber.startAsync().awaitRunning();
            System.out.printf("Listening for messages on %s:\n", subscriptionName.toString());
            // Allow the subscriber to run for 30s unless an unrecoverable error occurs.
            subscriber.awaitTerminated(130, TimeUnit.SECONDS);
   //         subscriber.awaitTerminated();


        } catch (TimeoutException timeoutException) {
            // Shut down the subscriber after 30s. Stop receiving messages.
            subscriber.stopAsync();
        }
    }

    public static void listPubSubNotifications(String projectId, String bucketName) throws IOException {

        Storage storage = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .build()
                .getService();
        List<Notification> notificationList = storage.listNotifications(bucketName);
        for (Notification notification : notificationList) {
            System.out.println("Found notification " + notification.getTopic() + " for bucket " + bucketName);
        }
    }
}