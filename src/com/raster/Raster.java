package com.raster;

import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderException;
import com.raster.util.ApplicationAdapter;

public class Raster {

    public static boolean mouseMoveEventWarningPrinted = false;

    public static void main(String[] args) {
        try {
            String applicationName = args[0];
            Class<ApplicationAdapter> appAdapter = (Class<ApplicationAdapter>) Class.forName("com.raster.registry." + applicationName);
            ApplicationAdapter adapter = appAdapter.newInstance();
            RenderContext.adapter = adapter;
            adapter.prepare();
            while (!adapter.context().shouldClose()) {
                adapter.context().update();
                adapter.event();
                adapter.render();
            }
            adapter.context().terminate();
        } catch (InstantiationException | IllegalAccessException | RenderException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Raster.warning("ApplicationNotFound", "application " + args[0] + " not found");
        }
    }

    public static void warning(String type, String arg) {
        System.out.println("[" + type + "] " + arg);
    }

    public static void warning(String arg) {
        warning("DeprecatedFeature", arg); // 'DeprecatedFeature' is a default warning type
    }
}