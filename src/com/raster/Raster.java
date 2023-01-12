package com.raster;

import com.raster.api.render.RenderContext;
import com.raster.api.render.RenderException;
import com.raster.util.ApplicationAdapter;

public class Raster {

    public static boolean mouseMoveEventWarningPrinted = false;

    public static boolean glCapabilitiesCreated = false;

    public static boolean debug = false;

    public static boolean inEvent = false;

    public static void main(String[] args) {
        String applicationName = "GLTest";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-debug" -> {
                    Raster.debug = true;
                }
                case "-app" -> {
                    applicationName = args[i + 1];
                    i++;
                }
            }
        }

        try {
            Class<ApplicationAdapter> appAdapter = (Class<ApplicationAdapter>) Class.forName("com.raster.registry." + applicationName);
            ApplicationAdapter adapter = appAdapter.newInstance();
            RenderContext.adapter = adapter;
            adapter.prepare();
            while (!adapter.context().shouldClose()) {
                adapter.context().update();
                inEvent = true;
                adapter.event();
                inEvent = false;
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

    public static void checkCapabilities(String text) {
        if (!Raster.glCapabilitiesCreated) {
            throw new RenderException(text);
        }
    }

    public static void here() {
        if (!debug) return;
        System.out.println("HERE");
    }
}