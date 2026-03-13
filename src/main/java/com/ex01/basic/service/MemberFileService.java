package com.ex01.basic.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.ex01.basic.exception.MemberNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MemberFileService {
    @Value("${s3.bucket}")
    private String bucket;
    @Autowired
    private AmazonS3 amazonS3;

    private static final String DIR = "uploads/";
    public String saveFile(MultipartFile multipartFile){
        String fileName = null;
        if( multipartFile == null || multipartFile.isEmpty() )
            fileName = "nan";
        else{
            //fileName : abcd124er34 - 탱담.jpg
            fileName = UUID.randomUUID().toString() + "-"
                    + multipartFile.getOriginalFilename();
            /*
            try{
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType( multipartFile.getContentType() );//파일 유형 s3알려줌
                metadata.setContentLength( multipartFile.getSize() );//파일 크기 s3알려줌
                amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

             */

            Path path = Paths.get(DIR + fileName );
            try {
                Files.createDirectories( path.getParent() );
                multipartFile.transferTo( path );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return fileName;
    }
    public byte[] getImage( String fileName ){
        /*
        byte[] imageBytes = {0};

        try {
            // S3에서 파일 다운로드
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucket, fileName));
            imageBytes = IOUtils.toByteArray(s3Object.getObjectContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageBytes;
         */

        Path filePath = Paths.get(DIR + fileName );
        if( !Files.exists(filePath) )
            throw new MemberNotFoundException("파일이 존재하지 않음");
        byte[] imageBytes = {0};
        try{
            imageBytes = Files.readAllBytes(filePath);
        }catch (IOException e){
            throw new RuntimeException();
        }
        return imageBytes;

    }
    public void deleteFile( String fileName ){
        Path path = Paths.get(DIR + fileName );
        try{
            Files.deleteIfExists(path);
            //amazonS3.deleteObject(bucket, fileName );
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}







