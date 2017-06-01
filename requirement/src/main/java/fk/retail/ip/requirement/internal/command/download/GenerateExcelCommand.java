package fk.retail.ip.requirement.internal.command.download;

import fk.retail.ip.requirement.model.RequirementDownloadLineItem;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.StreamingOutput;
import java.util.List;

/**
 * Created by nidhigupta.m on 16/02/17.
 */
@Slf4j
public class GenerateExcelCommand {

    public StreamingOutput generateExcel(List<RequirementDownloadLineItem> requirementDownloadLineItems, String templateName) {
//        log.info("Generating excel for {} number of requirements",requirementDownloadLineItems.size());
//        SpreadSheetWriter spreadsheet = new SpreadSheetWriter();
//        ObjectMapper mapper = new ObjectMapper();
//        InputStream template = getClass().getResourceAsStream(templateName);
//        StreamingOutput output = (OutputStream out) -> {
//            try {
//                spreadsheet.populateTemplate(out, mapper.convertValue(requirementDownloadLineItems, new TypeReference<List<Map>>() {
//                }));
//            } catch (InvalidFormatException e) {
//                throw new WebApplicationException(e);
//            }
//        };
//        return output;
        return null;
    }
}
