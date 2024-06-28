package org.mf.langchain.DTO;

public class GenerateSpecsDTO {
    private SpecificationDTO specification;
    private MetadataInfoDTO metadataInfo;

    public GenerateSpecsDTO() {
    }
    public GenerateSpecsDTO(SpecificationDTO specification, MetadataInfoDTO metadataInfo) {
        this.specification = specification;
        this.metadataInfo = metadataInfo;
    }

    public SpecificationDTO getSpecification() {
        return specification;
    }

    public void setSpecification(SpecificationDTO specification) {
        this.specification = specification;
    }

    public MetadataInfoDTO getMetadataInfo() {
        return metadataInfo;
    }

    public void setMetadataInfo(MetadataInfoDTO metadataInfo) {
        this.metadataInfo = metadataInfo;
    }
}
