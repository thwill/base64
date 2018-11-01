package de.twissow.base64;

import javax.enterprise.context.ApplicationScoped;
import java.util.Base64;

@ApplicationScoped
public class Base64EnDeCoder {

    private byte[] fileContent;

    private String encodedString;

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getEncodedString() {
        return encodedString;
    }

    public void setEncodedString(String encodedString) {
        this.encodedString = encodedString;
    }

    public String encode() {
        encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }

    public byte[] decode() {
        fileContent = Base64.getDecoder().decode(encodedString);
        return fileContent;
    }
}
