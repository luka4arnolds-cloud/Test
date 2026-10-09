import javax.tools.ToolProvider;
import java.nio.file.Path;
import java.net.URLClassLoader;

/** Compiles and executes the pure Java core without requiring a javac executable. */
public class CoreChecks {
    public static void main(String[] args) throws Exception {
        var compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) throw new IllegalStateException("JDK compiler required");
        var out = Path.of("/tmp/heat-drift-core-classes");
        java.nio.file.Files.createDirectories(out);
        int code = compiler.run(null, null, null, "-d", out.toString(),
            "src/core/Tuning.java", "src/core/DriftPhysics.java", "tests/PhysicsChecks.java");
        if (code != 0) throw new IllegalStateException("Compilation failed: " + code);
        try (var loader = new URLClassLoader(new java.net.URL[]{out.toUri().toURL()})) {
            loader.loadClass("PhysicsChecks").getMethod("main", String[].class).invoke(null, (Object)new String[0]);
        }
    }
}
