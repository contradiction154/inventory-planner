package fk.retail.ip.requirement.internal.command.download;

import fk.retail.ip.requirement.internal.command.RequirementDataAggregator;
import fk.retail.ip.requirement.internal.entities.Requirement;
import fk.retail.ip.requirement.internal.repository.*;
import fk.retail.ip.requirement.model.RequirementDownloadLineItem;
import fk.retail.ip.zulu.client.ZuluClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by nidhigupta.m on 26/01/17.
 */

@Slf4j
public abstract class DownloadCommand extends RequirementDataAggregator {

    private final GenerateExcelCommand generateExcelCommand;

    public DownloadCommand(FsnBandRepository fsnBandRepository, WeeklySaleRepository weeklySaleRepository, GenerateExcelCommand generateExcelCommand, LastAppSupplierRepository lastAppSupplierRepository,
                           ProductInfoRepository productInfoRepository, ZuluClient zuluClient, RequirementRepository requirementRepository, WarehouseRepository warehouseRepository) {
        super(fsnBandRepository, weeklySaleRepository, lastAppSupplierRepository, productInfoRepository, zuluClient, requirementRepository, warehouseRepository);
        this.generateExcelCommand = generateExcelCommand;
    }

    public List<RequirementDownloadLineItem> execute(List<Requirement> requirements, boolean isLastAppSupplierRequired) {
        log.info("Download Request for {} number of requirements", requirements.size());
        if (requirements.isEmpty()) {
            log.info("No requirements found for download. Generating empty file");
            return new ArrayList<>();
        }

//        Set<String> fsns = requirements.stream().map(Requirement::getFsn).collect(Collectors.toSet());
        Set<String> requirementWhs = requirements.stream().map(Requirement::getWarehouse).collect(Collectors.toSet());

        List<RequirementDownloadLineItem> requirementDownloadLineItems = requirements.stream().map(RequirementDownloadLineItem::new).collect(toList());
        requirements.clear();
        Map<String, List<RequirementDownloadLineItem>> fsnToRequirement = requirementDownloadLineItems.stream().collect(Collectors.groupingBy(RequirementDownloadLineItem::getFsn));
//        Map<String, List<RequirementDownloadLineItem>> WhToRequirement = requirementDownloadLineItems.stream().collect(Collectors.groupingBy(RequirementDownloadLineItem::getWarehouse));
        Set<String> fsns = fsnToRequirement.keySet();
//        Set<String> requirementWhs = WhToRequirement.keySet();
        fetchProductData(fsnToRequirement);
        fetchFsnBandData(fsnToRequirement);
        fetchSalesBucketData(fsns, requirementDownloadLineItems);
        fetchWarehouseName(requirementWhs, requirementDownloadLineItems);
        fetchRequirementStateData(isLastAppSupplierRequired, fsns, requirementDownloadLineItems);
        return requirementDownloadLineItems;
    }

    protected abstract String getTemplateName(boolean isLastAppSupplierRequired);

    abstract void fetchRequirementStateData(boolean isLastAppSupplierRequired, Set<String> fsns, List<RequirementDownloadLineItem> requirementDownloadLineItems);

}
