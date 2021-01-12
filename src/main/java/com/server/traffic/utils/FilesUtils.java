package com.server.traffic.utils;

import com.server.traffic.config.exception.FileStorageException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FilesUtils
 *
 * @author DatDV
 */
@Component
public class FilesUtils {

    @Value("${local.path.uploadfile}")
    private String root;

    @Value("${project.domain}")
    private String url;

    public String generateFileName(String fileNameClient) {
        String dateTimeNow = DateTimeUtils.getDateTimeNow("yyyyMMddHHmmssSSS");
        String[] splitString = fileNameClient.split("\\.");
        return StringUtils.substringBeforeLast(fileNameClient, ".") + "_" + dateTimeNow + "." + splitString[splitString.length - 1];
    }

    public String genFilePath(String urlFile) throws URISyntaxException {
        return this.getDomainName() + root + urlFile;
    }

    public String getDomainName() throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.toString();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public String save(MultipartFile file, String path, String fileName) {
        this.createFile();
        File fileMkdir = new File(System.getProperty("user.home") + root + path);

        if (!fileMkdir.exists()) {
            fileMkdir.mkdir();
        }

        Path rootCustomFileName = Paths.get(System.getProperty("user.home") + root + path);
        try {
            Files.copy(file.getInputStream(), rootCustomFileName.resolve(fileName));
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + path + ". Please try again!", e);
        }
        return path + fileName;
    }

    public File getFileFormDir(String pathFile) {
        String path = System.getProperty("user.home") + root + pathFile;
        return new File(path);
    }

    private void createFile() {
        File fileMkdirUsr = new File(System.getProperty("user.home") + "/usr");
        if (!fileMkdirUsr.exists()) {
            fileMkdirUsr.mkdir();
        }
        File fileMkdirVar = new File(System.getProperty("user.home") + "/usr/var");
        if (!fileMkdirVar.exists()) {
            fileMkdirVar.mkdir();
        }
    }
}
