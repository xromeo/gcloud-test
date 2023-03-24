package gcloud.storage;

import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.nio.charset.StandardCharsets;

public class DownloadObjectInMemory {

    public static void downloadObjectIntoMemory(
            String projectId, String bucketName, String objectName) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        byte[] content = storage.readAllBytes(bucketName, objectName);
        System.out.println(
                "The contents of "
                        + objectName
                        + " from bucket name "
                        + bucketName
                        + " are: "
                        + new String(content, StandardCharsets.UTF_8));


    }

    public static void main(String[] args) {
        // The ID of your GCP project
        String projectId = "eng-vizix-cloud";

        // The ID of your GCS bucket
        String bucketName = "eng-vizix-cloud-serverless-storage";

        // The ID of your GCS object
        String objectName = "dev-01-vizix-cloud/stock-management/your-object-name-oert.test";

        downloadObjectIntoMemory(projectId, bucketName, objectName);

    }
}