package com.MCWorld.framework.base.utils;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class ManifestReader {
    private static final String CERT_RSA_NAME = "META-INF/CERT.RSA";
    private static final String CERT_SF_NAME = "META-INF/CERT.SF";

    public static Manifest readManifest(String inputFile) throws IOException {
        return new JarFile(new File(inputFile), true).getManifest();
    }

    public static Manifest readCertSF(String inputFile) throws IOException {
        JarFile inputJar = new JarFile(new File(inputFile), true);
        return new Manifest(inputJar.getInputStream(inputJar.getJarEntry(CERT_SF_NAME)));
    }
}
