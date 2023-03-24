package gcloud.storage;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Notification;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class GcsTestApplication {

    private static final String SERVICE_ACCOUNT_FILE_NAME = "service-account-eng-vizix-cloud.json";

    public static void main(String... args) throws IOException {
        String projectId = "eng-vizix-cloud";
        listBuckets(projectId);
        listPubSubNotifications(projectId, "eng-vizix-cloud-serverless-storage");
    }

    public static void listBuckets(String projectId) throws IOException {

        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_FILE_NAME));
        Storage storage = StorageOptions.newBuilder()
                .setProjectId(projectId).setCredentials(credentials)
                .build()
                .getService();
        Page<Bucket> buckets = storage.list();

        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.asBucketInfo());
        }
    }

    public static void listPubSubNotifications(String projectId, String bucketName) throws IOException {

        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_FILE_NAME));
        Storage storage = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
        List<Notification> notificationList = storage.listNotifications(bucketName);
        for (Notification notification : notificationList) {
            System.out.println("Found notification " + notification.getTopic() + " for bucket " + bucketName);
        }
    }


}
