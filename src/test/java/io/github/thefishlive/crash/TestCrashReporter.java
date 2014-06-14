package io.github.thefishlive.crash;

import io.github.thefishlive.installer.crash.CrashReporter;

import io.github.thefishlive.upload.UploadTargets;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestCrashReporter {

    @Test
    public void testGistReporter() throws IOException, ReflectiveOperationException {
        CrashReport report = CrashReporter.buildCrashReport("Test report", new RuntimeException("Test exception"));
        String url = report.upload(UploadTargets.GIST);
        System.out.println(url);
        assertNotNull(url);
    }
}
