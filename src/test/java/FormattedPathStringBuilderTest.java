
import com.example.folderframework.FormattedPathStringBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FormattedPathStringBuilderTest {

    @Test
    public void test_build_formatted_root() {
        Path source_path = Paths.get("C:\\Root");
        String formatted_root_path = new FormattedPathStringBuilder().build_formatted_path_from_target_path(source_path, source_path);
        Assert.assertEquals("C:\\Root", formatted_root_path);
    }

    @Test
    public void test_build_formatted_path_from_target_path() {
        Path source_path = Paths.get("C:\\Root");
        Path target_path = Paths.get("C:\\Root\\UnitTests\\TestDesign");
        String formatted_path = new FormattedPathStringBuilder().build_formatted_path_from_target_path(source_path, target_path);
        Assert.assertEquals("C:\\Root \\UnitTests \\TestDesign", formatted_path);

        target_path = Paths.get("C:\\Root\\Env");
        formatted_path = new FormattedPathStringBuilder().build_formatted_path_from_target_path(source_path, target_path);
        Assert.assertEquals("C:\\Root \\Env      ", formatted_path);
    }

    @Test
    public void test_build_formatted_path_from_irregular_source_path() {
        Path source_path = Paths.get("C:\\Root1");
        Path target_path = Paths.get("C:\\Root\\Env");
        String formatted_path = new FormattedPathStringBuilder().build_formatted_path_from_target_path(source_path, target_path);
        Assert.assertEquals("", formatted_path);
    }

    @Test
    public void test_build_formatted_path_from_irregular_target_path() {
        Path source_path = Paths.get("C:\\Root");
        Path target_path = Paths.get("C:\\Root1\\UnitTests\\TestDesign");
        String formatted_path = new FormattedPathStringBuilder().build_formatted_path_from_target_path(source_path, target_path);
        Assert.assertEquals("", formatted_path);
    }

    @Test
    public void test_build_formatted_path_from_empty() {
        Path source_path = Paths.get("C:\\Root\\Env");
        Path target_path = Paths.get("C:\\Root\\UnitTests\\TestDesign");
        String formatted_path = new FormattedPathStringBuilder().build_formatted_path_from_target_path(source_path, target_path);
        Assert.assertEquals("", formatted_path);
    }
}
