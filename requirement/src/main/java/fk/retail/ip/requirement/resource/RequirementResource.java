package fk.retail.ip.requirement.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.common.io.FileBackedOutputStream;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import fk.retail.ip.requirement.internal.exception.InvalidRequirementStateException;
import fk.retail.ip.requirement.internal.exception.NoRequirementsSelectedException;
import fk.retail.ip.requirement.model.DownloadRequirementRequest;
import fk.retail.ip.requirement.model.RequirementUploadLineItem;
import fk.retail.ip.requirement.service.RequirementService;
import io.dropwizard.hibernate.UnitOfWork;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import net.minidev.json.JSONObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * Created by nidhigupta.m on 26/01/17.
 */
@Transactional
@Path("/v1/requirement")
public class RequirementResource {

    private final RequirementService requirementService;

    @Inject
    public RequirementResource(RequirementService requirementService) {
        this.requirementService = requirementService;
    }

    @POST
    @Path("/download")
    @Timed
    @UnitOfWork
    public Response download(DownloadRequirementRequest downloadRequirementRequest) {
        try {
            StreamingOutput stream = requirementService.downloadRequirement(downloadRequirementRequest);
            return Response.ok(stream)
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = projection.xlsx")
                    .build();
        } catch (InvalidRequirementStateException ise) {
            return Response.status(400).entity(ise.getMessage()).build();
        } catch (NoRequirementsSelectedException noreq) {
            return Response.status(400).entity(noreq.getMessage()).build();
        }
    }

    @POST
    @Path("/upload")
    @Timed
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadProjectionOverride(@FormDataParam("datafile") InputStream inputStream,
                                           @FormDataParam("state") String state)
            throws IOException, InvalidFormatException {

//        if (inputStream.available() > 0) {
//            System.out.println("stream is present");
//        } else{
//            return  " {\"status\" : \"success\"}";
//        }
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//        while((line = br.readLine()) != null) {
//            stringBuilder.append(line);
//        }
//        System.out.println(stringBuilder.toString());

//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        OutputStream outputStream = new FileOutputStream(file);
//        do {
//             bytesRead = inputStream.read(buffer);
//                outputStream.write(buffer, 0, bytesRead);
//        } while(bytesRead == 1024);

//        inputStream.read(buffer);

        //outputStream.write(buffer);
        System.out.println(state);
        try {
            //List<RequirementUploadLineItem> result = requirementService.uploadRequirement(new FileInputStream("/Users/agarwal.vaibhav/Desktop/test_proposed.xlsx"), fileDetails, state);
            List<RequirementUploadLineItem> result = requirementService.uploadRequirement(inputStream, state);
            JSONObject response = new JSONObject();
            if (result.isEmpty()) {
                response.put("status", "success");
                System.out.println("all were successful");
            } else {
                JSONObject responseBody = new JSONObject();
                List<JSONObject> responseList = new ArrayList<>();
                for(RequirementUploadLineItem row : result) {
                    responseBody.put("failureReason", row.getFailureReason());
                    responseBody.put("rowNumber", row.getRowNumber());
                    responseBody.put("warehouse", row.getWarehouse());
                    responseBody.put("fsn", row.getFsn());
                    responseList.add(responseBody);
                }
                response.put("status", "failed");
                response.put("response", responseList);

//                responseBody.put("rowNumber", result.get(0).getRowNumber());
//                responseBody.put("fsn", result.get(0).getFsn());
//                responseBody.put("warehouse", result.get(0).getWarehouse());
//                responseBody.put("failureReason", result.get(0).getFailureReason());
//                response.put("response", responseBody);
//                System.out.println(result.get(0).getFailureReason());
//                System.out.println(result.get(0).getFsn());
//                System.out.println(result.get(0).getRowNumber());
            }
            return Response.ok(response).build();

        } catch (InvalidRequirementStateException invalidStateException) {
            return Response.status(400).entity(invalidStateException.getMessage()).build();
        } catch (NoRequirementsSelectedException noRequirement) {
            return Response.status(400).entity(noRequirement.getMessage()).build();
        }

    }
}
