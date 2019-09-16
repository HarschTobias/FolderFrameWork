import com.example.folderframework.FolderSizeClient;
import com.example.folderframework.RecursiveFolderWalker;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class RecursiveFolderWalkerTest {
    private final Map<Path, String> expected_data = new TreeMap<>();

    {
        expected_data.put(Paths.get("C:\\Root"), "0 kB");
        expected_data.put(Paths.get("C:\\Root\\Env"), "0 kB");
        expected_data.put(Paths.get("C:\\Root\\Env\\TestStubs"), "1.406 kB");
        expected_data.put(Paths.get("C:\\Root\\Project1"), "0 kB");
        expected_data.put(Paths.get("C:\\Root\\Project1\\src"), "0 kB");
        expected_data.put(Paths.get("C:\\Root\\Project1\\src\\com"), "0 kB");
        expected_data.put(Paths.get("C:\\Root\\UnitTests"), "0 kB");
        expected_data.put(Paths.get("C:\\Root\\UnitTests\\TestDesign"), "0 kB");
    }

    @Test
    public void recursive_folder_handler_test() {
        Path root_path = Paths.get("C:\\Root");
        Map<Path, String> result_data = new RecursiveFolderWalker(new FolderSizeClient()).get_formatted_path_folder_client_result_map(root_path);
        Assert.assertArrayEquals(expected_data.keySet().toArray(), result_data.keySet().toArray());
        Assert.assertArrayEquals(expected_data.values().toArray(), result_data.values().toArray());
    }
}
