import com.raster.api.RenderException;
import com.raster.util.ApplicationAdapter;

public class Raster {
    public static void main(String[] args) {
        try {
            String applicationName = args[0];
            Class<ApplicationAdapter> appAdapter = (Class<ApplicationAdapter>) Class.forName("com.raster.registry." + applicationName);
            ApplicationAdapter adapter = appAdapter.newInstance();

            adapter.prepare();
            while (!adapter.context().shouldClose()) {
                adapter.context().update();
                adapter.event();
                adapter.render();
            }
            adapter.context().terminate();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | RenderException e) {
            e.printStackTrace();
        }
    }
}