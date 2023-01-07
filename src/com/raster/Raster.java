package com.raster;

import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderException;
import com.raster.util.ApplicationAdapter;

public class Raster {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | RenderException e) {
            e.printStackTrace();
        }
    }

    public static void warning(String type, String arg) {
        System.out.println("[" + type + "] " + arg);
    }

    public static void warning(String arg) {
        warning("DeprecatedFeature", arg); // 'DeprecatedFeature' is a default warning type
    }
}