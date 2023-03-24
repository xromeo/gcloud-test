package gcloud.storage;


import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroSchemaConverter;
import org.apache.parquet.column.ParquetProperties;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.ExampleParquetWriter;
import org.apache.parquet.hadoop.example.GroupReadSupport;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import org.apache.parquet.io.OutputFile;
import org.apache.parquet.io.PositionOutputStream;
import org.apache.parquet.schema.MessageType;

import java.io.IOException;

import static org.apache.parquet.hadoop.metadata.CompressionCodecName.SNAPPY;

/**
 * Created by Omar.Rivera on 29/1/23.
 *
 * @author Omar.Rivera
 */

public class ParquetDemo2 {

    public static void main(String... a) throws IOException {

        Configuration conf = new Configuration();
        Path root = new Path("target/tests/TestParquetWriter/");
        enforceEmptyDir(conf, root);

        Schema avroSchema = ReflectData.get().getSchema(StockOnHandMetadata.class);
        AvroSchemaConverter avroSchemaConverter = new AvroSchemaConverter();
        MessageType parquetSchema = avroSchemaConverter.convert(avroSchema);


        GroupWriteSupport.setSchema(parquetSchema, conf);
        SimpleGroupFactory f = new SimpleGroupFactory(parquetSchema);


        for (ParquetProperties.WriterVersion version : ParquetProperties.WriterVersion.values()) {
            Path file = new Path(root, version.name() + "_" + 10);
            ParquetWriter<Group> writer = ExampleParquetWriter.builder(new TestOutputFile(file, conf))
                    .withCompressionCodec(SNAPPY)

                    .withPageSize(1024)
                    .withDictionaryPageSize(512)
                    .enableDictionaryEncoding()
                    .withValidation(false)
                    .withWriterVersion(version)
                    .withConf(conf)
                    .build();
            for (int i = 0; i < 10; i++) {
                writer.write(
                        f.newGroup()
                                .append("id", "" + (i % 10))
                                .append("_id", "" + (i % 10))
                                .append("retailQuantityMatch", true)
                                .append("retailSohClearance", true)
                                .append("retailProductMateBundle", 1)
                                .append("source", "source" + (i % 10))
                                .append("retailSohSku", "retailSohSku" + (i % 10))
                                .append("retailProductImageUrl", "retailProductImageUrl" + (i % 10))
                                .append("retailSequence", 100L)
                                .append("tsCoreIn", 100L)
                                .append("tsMongoIn", 100L)
                                .append("tsEdgeIn", 100L)
                                .append("retailSohStoreInProcess", 100L)
                                .append("retailSohDate", 100L)
                                .append("rowNumber", 100L)
                                .append("retailSohQuantityStockRoom", i)
                                .append("retailSohQuantitySalesFloor", i)
                                .append("retailQuantitySalesFloor", i)
                                .append("retailQuantityStockRoom", i)
                                .append("retailSohHoldArea", i)
                                .append("retailSalesFloorMin", i)
                                .append("retailSalesFloorMax", i)
                                .append("retailSohInTransitQuantity", i)
                                .append("retailSohQuantity", i)
                                .append("retailQuantity", i)
                                .append("retailSohDepositCode", "retailSohDepositCode" + (i % 10))
                                .append("retailProductBrand", "retailProductBrand" + (i % 10))
                                .append("retailProductUpcNumber", "retailProductUpcNumber" + (i % 10))
                                .append("retailProductPrice", "retailProductPrice" + (i % 10))
                                .append("retailProductRfidTagged", "retailProductRfidTagged" + (i % 10))
                                .append("retailProductManufacturer", "retailProductManufacturer" + (i % 10))
                                .append("retailProductUpcs", "retailProductUpcs" + (i % 10))
                                .append("retailProductStyle", "retailProductStyle" + (i % 10))
                                .append("retailProductLevel1Name", "retailProductLevel1Name" + (i % 10))
                                .append("retailProductLevel2Name", "retailProductLevel2Name" + (i % 10))
                                .append("retailProductLevel1", "retailProductLevel1" + (i % 10))
                                .append("retailProductLevel2", "retailProductLevel2" + (i % 10))
                                .append("retailProductLevel3", "retailProductLevel3" + (i % 10))
                                .append("retailProductLevel4", "retailProductLevel4" + (i % 10))
                                .append("retailProductLevel5", "retailProductLevel5" + (i % 10))
                                .append("retailSohDateString", "retailSohDateString" + (i % 10))
                                .append("retailSohStoreNumber", "retailSohStoreNumber" + (i % 10))
                                .append("rule", "rule" + (i % 10))
                                .append("retailProductLevel3Name", "retailProductLevel3Name" + (i % 10))
                                .append("retailProductDimension", "retailProductDimension" + (i % 10))
                                .append("retailProductUom", "retailProductUom" + (i % 10))
                                .append("retailProductLevel4Name", "retailProductLevel4Name" + (i % 10))
                                .append("retailStoreNumber", "retailStoreNumber" + (i % 10))
                                .append("retailProductUpc", "retailProductUpc" + (i % 10))
                                .append("retailSohCurrency", "retailSohCurrency" + (i % 10))
                                .append("retailProductExternalSku", "retailProductExternalSku" + (i % 10))
                                .append("retailProductLevel5Name", "retailProductLevel5Name" + (i % 10))
                                .append("retailSohReceiptId", "retailSohReceiptId" + (i % 10))
                                .append("retailProductVpn", "retailProductVpn" + (i % 10))
                                .append("retailSohDepositName", "retailSohDepositName" + (i % 10))
                                .append("retailProductSize", "retailProductSize" + (i % 10))
                                .append("retailSohPrice", "retailSohPrice" + (i % 10))
                                .append("retailProductName", "retailProductName" + (i % 10))
                                .append("retailProductSku", "retailProductSku" + (i % 10))
                                .append("retailProductColor", "retailProductColor" + (i % 10))
                );
            }
            writer.close();


            ParquetReader<Group> reader = ParquetReader.builder(new GroupReadSupport(), file).withConf(conf).build();
            for (int i = 0; i < 10; i++) {
                Group group = reader.read();
                System.out.println(group);

//
//                assertEquals("test" + (i % 10), group.getBinary("binary_field", 0).toStringUsingUTF8());
//                assertEquals(32, group.getInteger("int32_field", 0));
//                assertEquals(64l, group.getLong("int64_field", 0));
//                assertEquals(true, group.getBoolean("boolean_field", 0));
//                assertEquals(1.0f, group.getFloat("float_field", 0), 0.001);
//                assertEquals(2.0d, group.getDouble("double_field", 0), 0.001);
//                assertEquals("foo", group.getBinary("flba_field", 0).toStringUsingUTF8());
//                assertEquals(Binary.fromConstantByteArray(new byte[12]),
//                        group.getInt96("int96_field", 0));
            }
            reader.close();

        }
    }


    /**
     * A test OutputFile implementation to validate the scenario of an OutputFile is implemented by an API client.
     */
    private static class TestOutputFile implements OutputFile {

        private final OutputFile outputFile;

        TestOutputFile(Path path, Configuration conf) throws IOException {
            outputFile = HadoopOutputFile.fromPath(path, conf);
        }

        @Override
        public PositionOutputStream create(long blockSizeHint) throws IOException {
            return outputFile.create(blockSizeHint);
        }

        @Override
        public PositionOutputStream createOrOverwrite(long blockSizeHint) throws IOException {
            return outputFile.createOrOverwrite(blockSizeHint);
        }

        @Override
        public boolean supportsBlockSize() {
            return outputFile.supportsBlockSize();
        }

        @Override
        public long defaultBlockSize() {
            return outputFile.defaultBlockSize();
        }

    }

    public static void enforceEmptyDir(Configuration conf, Path path) throws IOException {
        FileSystem fs = path.getFileSystem(conf);
        if (fs.exists(path)) {
            if (!fs.delete(path, true)) {
                throw new IOException("can not delete path " + path);
            }
        }
        if (!fs.mkdirs(path)) {
            throw new IOException("can not create path " + path);
        }
    }
}
