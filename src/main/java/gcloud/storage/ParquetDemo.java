package gcloud.storage;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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
import org.apache.parquet.io.api.Binary;
import org.apache.parquet.schema.MessageType;

import java.io.IOException;

import static org.apache.parquet.hadoop.metadata.CompressionCodecName.SNAPPY;
import static org.apache.parquet.schema.MessageTypeParser.parseMessageType;

/**
 * Created by Omar.Rivera on 29/1/23.
 *
 * @author Omar.Rivera
 */

public class ParquetDemo {


    public static void main(String... a) throws IOException {

        Configuration conf = new Configuration();
        Path root = new Path("target/tests/TestParquetWriter/");
        enforceEmptyDir(conf, root);
        MessageType schema = parseMessageType(
                "message Stock_On_Hand_Metadata { "
                        + "required binary binary_field (UTF8); "
                        + "optional binary binary_field_optional (UTF8); "
                        + "required int32 int32_field; "
                        + "required int64 int64_field; "
                        + "required boolean boolean_field; "
                        + "required float float_field; "
                        + "required double double_field; "
                        + "required fixed_len_byte_array(3) flba_field; "
                        + "required int96 int96_field; "
                        + "} ");

        GroupWriteSupport.setSchema(schema, conf);
        SimpleGroupFactory f = new SimpleGroupFactory(schema);


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
                                .append("binary_field", "test" + (i % 10))
                                .append("binary_field_optional", "Zoe_" + (i % 10))
                                .append("int32_field", 32)
                                .append("int64_field", 64l)
                                .append("boolean_field", true)
                                .append("float_field", 1.0f)
                                .append("double_field", 2.0d)
                                .append("flba_field", "foo")
                                .append("int96_field", Binary.fromConstantByteArray(new byte[12])));
            }
            writer.close();


            ParquetReader<Group> reader = ParquetReader.builder(new GroupReadSupport(), file).withConf(conf).build();
            for (int i = 0; i < 10; i++) {
                Group group = reader.read();
                System.out.println(group);

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
