package fk.retail.ip.requirement.internal.states;

import fk.retail.ip.requirement.internal.entities.Requirement;
import fk.retail.ip.requirement.model.RequirementDownloadLineItem;
import fk.retail.ip.requirement.model.RequirementUploadLineItem;
import fk.retail.ip.requirement.model.UploadOverrideResult;

import java.util.List;
import java.util.Map;

/**
 * Created by nidhigupta.m on 21/02/17.
 */

public interface RequirementState {
    List<RequirementDownloadLineItem> download(List<Requirement> requirements, boolean isLastAppSupplierRequired);
    UploadOverrideResult upload(List<Requirement> requirements, List<RequirementUploadLineItem> requirementUploadLineItems, String userID, String state, Map<Integer, Map<String, List<String>>> errorMap);
}
