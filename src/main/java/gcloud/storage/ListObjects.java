package gcloud.storage;

/**
 * Created by Omar.Rivera on 12/1/23.
 *
 * @author Omar.Rivera
 */

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class ListObjects {
    public static void listObjects(String projectId, String bucketName) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();


        Page<Bucket> blobs = storage.list();

        for (Bucket bucket : blobs.iterateAll()) {
            System.out.println(bucket.asBucketInfo());
        }
    }

    public static void main(String[] args) {

        // The ID of your GCP project
        String projectId = "eng-vizix-cloud";

        // The ID of your GCS bucket
        String bucketName = "eng-vizix-cloud-serverless-storage";
        listObjects(projectId, bucketName);
    }
}