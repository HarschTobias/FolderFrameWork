import com.example.folderframework.OutputFileWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.TreeMap;

public class WriteOneOutputFileTest {
    private final Map<String, String> text_file_data = new TreeMap<>();

    {
        text_file_data.put("D:\\Root", "23 kB");
        text_file_data.put("D:\\Root \\Env", "2 kB");
        text_file_data.put("D:\\Root \\Env \\TestStubs", "21 kB");
        text_file_data.put("D:\\Root \\Project1", "5 kB");
        text_file_data.put("D:\\Root \\Project1 \\src", "57 kB");
        text_file_data.put("D:\\Root \\Project1 \\src \\com", "5 kB");
        text_file_data.put("D:\\Root \\UnitTests", "4 kB");
        text_file_data.put("D:\\Root \\UnitTests \\TestDesign", "712 kB");
    }

    @Test
    public void write_folder_information_to_writer_test() throws Exception {
        StringWriter string_writer = new StringWriter();
        BufferedWriter buffered_writer = new BufferedWriter(string_writer);
        new OutputFileWriter("Plugin FolderSize", text_file_data).write_folder_information_to_writer(buffered_writer);
        buffered_writer.flush();
        Assert.assertEquals(
                "Plugin FolderSize:" + System.lineSeparator() +
                        "D:\\Root                                                               :  23 kB" + System.lineSeparator() +
                        "D:\\Root \\Env                                                          :   2 kB" + System.lineSeparator() +
                        "D:\\Root \\Env \\TestStubs                                               :  21 kB" + System.lineSeparator() +
                        "D:\\Root \\Project1                                                     :   5 kB" + System.lineSeparator() +
                        "D:\\Root \\Project1 \\src                                                :  57 kB" + System.lineSeparator() +
                        "D:\\Root \\Project1 \\src \\com                                           :   5 kB" + System.lineSeparator() +
                        "D:\\Root \\UnitTests                                                    :   4 kB" + System.lineSeparator() +
                        "D:\\Root \\UnitTests \\TestDesign                                        : 712 kB", string_writer.toString());
    }
}




