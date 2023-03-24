package gcloud.storage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.nio.file.Paths;

public class DownloadObject {

    public static void downloadObject(
            String projectId, String bucketName, String objectName, String destFilePath) {
        // The ID of your GCP project
        // String projectId = "your-project-id";

        // The ID of your GCS bucket
        // String bucketName = "eng-vizix-cloud-serverless-storage";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        // The path to which the file should be downloaded
        // String destFilePath = "/local/path/to/file.txt";

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();

        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        System.out.println("blob = " + blob);
        System.out.println("blob.getMetadata() = " + blob.getMetadata());

        blob.downloadTo(Paths.get(destFilePath));
        System.out.println("Metadata: " + blob.getMetadata().get("soh"));

        System.out.println(
                "Downloaded object "
                        + objectName
                        + " from bucket name "
                        + bucketName
                        + " to "
                        + destFilePath);
    }

    public static void main(String[] args) {
        // The ID of your GCP project
        String projectId = "eng-vizix-cloud";

        // The ID of your GCS bucket
        String bucketName = "eng-vizix-cloud-serverless-storage";

        // The ID of your GCS object
        String objectName = "PERN_siteCode_MEGA_FileDate_2023-01-27_Chunk_1_Of_5_SystemDate_1674825840459.parquet";

        // The path to your file to upload
        //String filePath = "README.md";
        String destFilePath = "target/tests/TestParquetWriter/PERN_siteCode_MEGA_FileDate_2023-01-27_Chunk_1_Of_5_SystemDate_1674825840459.parquet";

        downloadObject(projectId, bucketName, objectName, destFilePath);

    }
}