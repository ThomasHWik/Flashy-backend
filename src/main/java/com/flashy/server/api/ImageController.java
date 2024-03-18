package com.flashy.server.api;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.flashy.server.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;


@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService blobService;


    @GetMapping("/{blobName}")
    public ResponseEntity<byte[]> downloadBlob( @PathVariable String blobName) {
        BinaryData data = blobService.downloadBlob(blobName);
        if (data != null) {
            byte[] dataBytes = data.toBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(dataBytes.length);

            headers.setContentType(MediaType.valueOf("image/jpeg"));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(blobName).build());

            return new ResponseEntity<>(dataBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
/*
    @GetMapping("/{blobName}")
    public ResponseEntity<byte[]> downloadBlob( @PathVariable String blobName) {
        BinaryData data = blobService.downloadBlob(blobName);
        if (data != null) {
            byte[] dataBytes = data.toBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(dataBytes.length);

            headers.setContentType(MediaType.IMAGE_JPEG);
            // print out the whole parsed byte array to base64
            System.out.println(Base64.getEncoder().encodeToString(dataBytes));


            return new ResponseEntity<>(dataBytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
*/

}
