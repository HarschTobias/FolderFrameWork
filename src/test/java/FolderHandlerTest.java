import com.example.folderframework.FolderHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class FolderHandlerTest {
    private final String expected_data = "Plugin FolderSize:" + System.lineSeparator() +
            "C:\\Root                                                               :     0 kB" + System.lineSeparator() +
            "C:\\Root \\Env                                                          :     0 kB" + System.lineSeparator() +
            "C:\\Root \\Env       \\TestStubs                                         : 1.406 kB" + System.lineSeparator() +
            "C:\\Root \\Project1                                                     :     0 kB" + System.lineSeparator() +
            "C:\\Root \\Project1  \\src                                               :     0 kB" + System.lineSeparator() +
            "C:\\Root \\Project1  \\src \\com                                          :     0 kB" + System.lineSeparator() +
            "C:\\Root \\UnitTests                                                    :     0 kB" + System.lineSeparator() +
            "C:\\Root \\UnitTests \\TestDesign                                        :     0 kB";

    static {
        Locale.setDefault(Locale.GERMANY);
    }

    @Test
    public void write_to_writer_test() throws Exception {
        Path target_path = Paths.get("C:\\Root");
        StringWriter string_writer = new StringWriter();
        BufferedWriter buffered_writer = new BufferedWriter(string_writer);
        new FolderHandler(target_path).write_all_folder_information_to_writer(buffered_writer);
        buffered_writer.flush();
        Assert.assertEquals(expected_data, string_writer.toString());
    }

    @Test
    public void write_to_file_test() throws IOException {
        Path target_path = Paths.get("C:\\Root");
        Path out_path = Paths.get("C:\\Users\\Tobias Harsch\\IdeaProjects\\FolderFrameWork");
        new FolderHandler(target_path).write_all_folder_information_to_separate_files(out_path);

        byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\Tobias Harsch\\IdeaProjects\\FolderFrameWork\\Plugin FolderSize.txt"));
        Assert.assertArrayEquals(expected_data.getBytes(StandardCharsets.UTF_8), bytes);
    }

}
