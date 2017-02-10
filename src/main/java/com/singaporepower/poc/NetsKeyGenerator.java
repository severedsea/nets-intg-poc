package com.singaporepower.poc;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Nets configuration helper class
 *
 * Created by Hans Christian Ang
 */
@Component
public class NetsKeyGenerator {

    /**
     * Initializes NETS configuration dependencies
     */
    @PostConstruct
    public void init() throws IOException {
        generateMerchantPrivKeyFile();
        generateNetsPubKeyFile();
    }

    /**
     * Copies a file from the classpath to the filesystem
     * @param src - source file location from classpath
     * @return copied file
     * @throws IOException
     */
    public File copyFromClasspath(String src) throws IOException {
        // Get file from classpath
        ClassPathResource fileResource = new ClassPathResource(src);

        // Generate empty file to target location
        String target = "/tmp/" + fileResource.getFilename();
        File generated = new File(target);

        // Copy contents to generated file
        Files.copy(fileResource.getInputStream(), generated.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return generated;
    }

    private void generateMerchantPrivKeyFile() throws IOException {
        File generated = copyFromClasspath("keys/merchant-priv.pgp.asc");
        System.out.println("Generated merchant private key file to " + generated.getAbsolutePath());
    }

    private void generateNetsPubKeyFile() throws IOException {
        File generated = copyFromClasspath("keys/nets-pub.pgp.asc");
        System.out.println("Generated NETS public key file to " + generated.getAbsolutePath());
    }


}
