package org.acme.timer;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;

public class MyProcessor implements Processor {
    
    private static final String TEST_UPLOAD_FILE = "src/main/resources/file/customerlist.csv";
    private static final java.io.File UPLOAD_FILE = new java.io.File(TEST_UPLOAD_FILE);

    @Override
    public void process(Exchange exchange) throws Exception {
       
        File fileMetadata = new File();
        fileMetadata.setTitle(UPLOAD_FILE.getName());
        FileContent mediaContent = new FileContent(null, UPLOAD_FILE);
        
        final Map<String, Object> headers = new HashMap<>();
        // parameter type is com.google.api.services.drive.model.File
        headers.put("CamelGoogleDrive.content", fileMetadata);
        headers.put("CamelGoogleDrive.mediaContent", mediaContent); 
        exchange.getIn().setHeaders(headers);
       
    }

}
