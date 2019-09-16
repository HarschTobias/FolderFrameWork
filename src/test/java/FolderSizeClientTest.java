import com.example.folderframework.FolderSizeClient;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class FolderSizeClientTest {
    static {
        Locale.setDefault(Locale.GERMANY);
    }

    @Test
    public void determine_size_of_files_test() {
        Path target_path = Paths.get("C:\\Root\\Env\\TestStubs");
        String file_size_string = new FolderSizeClient().call(target_path);
        Assert.assertEquals("1.406 kB", file_size_string);
    }

    @Test
    public void determine_size_of_empty_folder_test() {
        Path target_path = Paths.get("C:\\Root\\UnitTests\\TestDesign");
        String file_size_string = new FolderSizeClient().call(target_path);
        Assert.assertEquals("0 kB", file_size_string);
    }

    @Test
    public void determine_size_of_no_files_test() {
        Path target_path = Paths.get("C:\\Root");
        String file_size_string = new FolderSizeClient().call(target_path);
        Assert.assertEquals("0 kB", file_size_string);
    }
}
