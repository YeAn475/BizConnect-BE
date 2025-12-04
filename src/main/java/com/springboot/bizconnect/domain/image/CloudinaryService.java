package com.springboot.bizconnect.domain.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String upload(MultipartFile file, String folder) {
        try {
            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", folder
            );
            Map<String, Object> result = cloudinary.uploader().upload(file.getBytes(), options);
            return (String) result.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드에 실패했습니다.", e);
        }
    }

    public void delete(String imageUrl) {
        try {
            String publicId = extractPublicId(imageUrl);
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("이미지 삭제에 실패했습니다.", e);
        }
    }

    private String extractPublicId(String imageUrl) {
        // URL에서 public_id 추출
        // https://res.cloudinary.com/cloud-name/image/upload/v1234/folder/filename.jpg
        // → folder/filename
        String[] parts = imageUrl.split("/upload/");
        if (parts.length < 2) return "";
        String path = parts[1];
        // 버전 제거 (v1234/)
        if (path.startsWith("v")) {
            path = path.substring(path.indexOf("/") + 1);
        }
        // 확장자 제거
        return path.substring(0, path.lastIndexOf("."));
    }
}
