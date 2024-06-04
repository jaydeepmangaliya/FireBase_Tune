package com.example.firebase_tune;

public class MusicFile {
    private String filePath;
    private String fileType;

    public MusicFile(String filePath, String fileType) {
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }
}
