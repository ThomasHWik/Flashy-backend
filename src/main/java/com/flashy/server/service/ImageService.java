package com.flashy.server.service;

import com.azure.core.util.BinaryData;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.models.BlobItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Service
public class ImageService {

    @Autowired
    private BlobServiceClient blobServiceClient;

    @Value("${azure.storage.blob.container.name}")
    private String containerName;



    public void uploadBlob(String blobName, byte[] file) throws IOException {
        System.out.println("Uploading blob: " + blobName);
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("images");
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        blobClient.upload(new ByteArrayInputStream(file), file.length, true);
    }

    public BinaryData downloadBlob(String blobName) {
        try {
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("images");
            BlobClient blobClient = containerClient.getBlobClient(blobName);
            return blobClient.downloadContent();
        } catch (Exception e) {
            return null;
        }

    }

    public void deleteBlob(String blobName) {
        try {
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("images");
            containerClient.getBlobClient(blobName).delete();
        } catch (Exception e) {
            System.out.println("Error deleting blob: " + blobName);
        }

    }
}
