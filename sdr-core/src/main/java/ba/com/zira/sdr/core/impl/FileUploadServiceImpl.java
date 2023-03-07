package ba.com.zira.sdr.core.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ba.com.zira.commons.exception.ApiException;
import ba.com.zira.commons.message.request.EntityRequest;
import ba.com.zira.commons.model.response.ResponseCode;
import ba.com.zira.sdr.api.FileUploadService;
import ba.com.zira.sdr.api.model.audio.AudioUploadRequest;
import ba.com.zira.sdr.api.model.image.ImageUploadRequest;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${image.folder.location://opt//zira//cdn//vigor//img}")
    private String imagePath;

    @Value("${image.server.location:http://172.20.20.45:82//vigor//img}")
    private String imageServerUrl;

    @Value("${audio.folder.location://opt//zira//cdn//vigor//img}")
    private String audioPath;

    @Value("${audio.server.location:http://172.20.20.45:82//vigor//img}")
    private String audioServerUrl;

    @Override
    public Map<String, String> uploadImage(final EntityRequest<ImageUploadRequest> request) throws ApiException {
        Map<String, String> result = new HashMap<>();

        String url = imageServerUrl + "/" + request.getEntity().getImageName();
        String base64image = request.getEntity().getImageData().split(",")[1];
        byte[] decodedImage = Base64.getDecoder().decode(base64image.getBytes(StandardCharsets.UTF_8));
        var destinationFile = Paths.get(imagePath, request.getEntity().getImageName());
        String baseName = FilenameUtils.getBaseName(request.getEntity().getImageName());
        String extension = FilenameUtils.getExtension(request.getEntity().getImageName());
        try {
            Files.write(destinationFile, decodedImage);
            result.put("url", url);
            result.put("baseName", baseName);
            result.put("extension", extension);

            return result;
        } catch (IOException e) {
            throw ApiException.createFrom(request, ResponseCode.REQUEST_INVALID, e.getMessage());
        }
    }

    @Override
    public Map<String, String> uploadAudio(EntityRequest<AudioUploadRequest> request) throws ApiException {
        Map<String, String> result = new HashMap<>();

        String url = audioServerUrl + "/" + request.getEntity().getAudioName();
        String base64image = request.getEntity().getAudioData().split(",")[1];
        byte[] decodedAudio = Base64.getDecoder().decode(base64image.getBytes(StandardCharsets.UTF_8));
        var destinationFile = Paths.get(audioPath, request.getEntity().getAudioName());
        String baseName = FilenameUtils.getBaseName(request.getEntity().getAudioName());
        String extension = FilenameUtils.getExtension(request.getEntity().getAudioName());
        try {
            Files.write(destinationFile, decodedAudio);
            result.put("url", url);
            result.put("baseName", baseName);
            result.put("extension", extension);

            return result;
        } catch (IOException e) {
            throw ApiException.createFrom(request, ResponseCode.REQUEST_INVALID, e.getMessage());
        }
    }

}
