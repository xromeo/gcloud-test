package gcloud.storage;

/**
 * Created by Omar.Rivera on 11/1/23.
 *
 * @author Omar.Rivera
 */


import com.google.cloud.storage.*;

import java.io.IOException;
import java.nio.file.*;


import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class UploadObject {
    public static void uploadObject(
            String projectId, String bucketName, String objectName, String filePath) throws IOException {
        // The ID of your GCP project
        // String projectId = "eng-vizix-cloud";

        // The ID of your GCS bucket
        // String bucketName = "eng-vizix-cloud-serverless-storage";

        // The ID of your GCS object
        // String objectName = "your-object-name";

        Map<String, String> metadata = new HashMap<>();
        metadata.put("tenant", "PERN");
        metadata.put("siteCode", "MEGA");
        metadata.put("totalRecords", "50");
        metadata.put("date", "2");
        metadata.put("fileName", "IZOE_PREMISE_DESTINATION_20230214_431e73d3-c68d-46d2-8bbc-4e6be48c616b_soh.json");
        metadata.put("transactionId", "fc4b44b2-6ac3-413c-bbd5-b04585e859ae");

        // The path to your file to upload
        // String filePath = "path/to/your/file"

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setMetadata(metadata).build();

        // Optional: set a generation-match precondition to avoid potential race
        // conditions and data corruptions. The request returns a 412 error if the
        // preconditions are not met.
        Storage.BlobTargetOption precondition;
        if (storage.get(bucketName, objectName) == null) {
            // For a target object that does not yet exist, set the DoesNotExist precondition.
            // This will cause the request to fail if the object is created before the request runs.
            precondition = Storage.BlobTargetOption.doesNotExist();
        } else {
            // If the destination already exists in your bucket, instead set a generation-match
            // precondition. This will cause the request to fail if the existing object's generation
            // changes before the request runs.
            precondition =
                    Storage.BlobTargetOption.generationMatch(
                            storage.get(bucketName, objectName).getGeneration());
        }
        Blob blob = storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)), precondition);

        System.out.println("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
        System.out.println("BlobId: " + blobId + " Blob: " + blobInfo);

        //deleteFile("Omar.parquet");
    }

    public static void main(String[] args) throws IOException {
        // The ID of your GCP project
        String projectId = "eng-vizix-cloud";

        // The ID of your GCS bucket
        String bucketName = "eng-vizix-cloud-serverless-storage";

        // The ID of your GCS object
        String objectName = "AZOE_PREMISE_DESTINATION_20230214_431e73d3-c68d-46d2-8bbc-4e6be48c616b_soh.json";

        // The path to your file to upload
        //String filePath = "README.md";
        String filePath = "src/main/resources/ZOE_PREMISE_DESTINATION_20230214_431e73d3-c68d-46d2-8bbc-4e6be48c616b_soh.json";

        uploadObject(projectId, bucketName, "I" + objectName, filePath);
//        uploadObject(projectId, bucketName, "H" + objectName, filePath);
//        uploadObject(projectId, bucketName, "G" + objectName, filePath);
//        uploadObject(projectId, bucketName, "F" + objectName, filePath);
//        uploadObject(projectId, bucketName, "E" + objectName, filePath);
//        uploadObject(projectId, bucketName, "D" + objectName, filePath);
//        uploadObject(projectId, bucketName, "C" + objectName, filePath);
//        uploadObject(projectId, bucketName, "B" + objectName, filePath);
//        uploadObject(projectId, bucketName, "A" + objectName, filePath);


    }


    private static void deleteFile(String fileName){
        java.nio.file.Path path = Paths.get("/tmp", fileName);
        try {
            Files.deleteIfExists(path);
        }
        catch (NoSuchFileException e) {
            System.out.println("No such file/directory exists. FileName = " + fileName);
        }
        catch (IOException e) {
            System.out.println("Invalid permissions. FileName = " + fileName);
        }
        System.out.println("Deletion successful. FileName = " + fileName);
    }



}