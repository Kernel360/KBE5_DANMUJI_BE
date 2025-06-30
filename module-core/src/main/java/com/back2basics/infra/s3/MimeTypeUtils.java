package com.back2basics.infra.s3;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MimeTypeUtils {

    private static final Map<String, String> EXT_TO_MIME = new HashMap<>();

    static {

        // 이미지
        EXT_TO_MIME.put("png", "image/png");
        EXT_TO_MIME.put("jpg", "image/jpeg");
        EXT_TO_MIME.put("jpeg", "image/jpeg");
        EXT_TO_MIME.put("gif", "image/gif");
        EXT_TO_MIME.put("bmp", "image/bmp");
        EXT_TO_MIME.put("svg", "image/svg+xml");
        EXT_TO_MIME.put("webp", "image/webp");
        EXT_TO_MIME.put("ico", "image/x-icon");

        // 문서
        EXT_TO_MIME.put("pdf", "application/pdf");
        EXT_TO_MIME.put("txt", "text/plain");
        EXT_TO_MIME.put("csv", "text/csv");
        EXT_TO_MIME.put("doc", "application/msword");
        EXT_TO_MIME.put("docx",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        EXT_TO_MIME.put("xls", "application/vnd.ms-excel");
        EXT_TO_MIME.put("xlsx",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        EXT_TO_MIME.put("ppt", "application/vnd.ms-powerpoint");
        EXT_TO_MIME.put("pptx",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        EXT_TO_MIME.put("json", "application/json");
        EXT_TO_MIME.put("xml", "application/xml");

        // 압축
        EXT_TO_MIME.put("zip", "application/zip");
        EXT_TO_MIME.put("rar", "application/vnd.rar");
        EXT_TO_MIME.put("tar", "application/x-tar");
        EXT_TO_MIME.put("gz", "application/gzip");
        EXT_TO_MIME.put("7z", "application/x-7z-compressed");

        // 미디어
        EXT_TO_MIME.put("mp3", "audio/mpeg");
        EXT_TO_MIME.put("wav", "audio/wav");
        EXT_TO_MIME.put("ogg", "audio/ogg");
        EXT_TO_MIME.put("mp4", "video/mp4");
        EXT_TO_MIME.put("mov", "video/quicktime");
        EXT_TO_MIME.put("avi", "video/x-msvideo");
        EXT_TO_MIME.put("wmv", "video/x-ms-wmv");
        EXT_TO_MIME.put("webm", "video/webm");
    }

    public static String getMimeType(String filename, String fallback) {
        if (filename == null) {
            return fallback;
        }

        int idx = filename.lastIndexOf('.');
        if (idx == -1 || idx == filename.length() - 1) {
            return fallback;
        }

        String ext = filename.substring(idx + 1).toLowerCase();
        return EXT_TO_MIME.getOrDefault(ext,
            fallback != null ? fallback : "application/octet-stream");
    }
}
