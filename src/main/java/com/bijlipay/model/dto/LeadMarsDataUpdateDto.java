package com.bijlipay.model.dto;


public class LeadMarsDataUpdateDto {
    private UpdateNewLeadDataDto updateNewLeadDataDto;
    private MarsOnboardInfoDto marsOnboardDto;
    private MarsDetailsDto marsDetailsDto;

    public UpdateNewLeadDataDto getUpdateNewLeadDataDto() {
        return updateNewLeadDataDto;
    }

    public void setUpdateNewLeadDataDto(UpdateNewLeadDataDto updateNewLeadDataDto) {
        this.updateNewLeadDataDto = updateNewLeadDataDto;
    }

    public MarsOnboardInfoDto getMarsOnboardDto() {
        return marsOnboardDto;
    }

    public void setMarsOnboardDto(MarsOnboardInfoDto marsOnboardDto) {
        this.marsOnboardDto = marsOnboardDto;
    }

    public MarsDetailsDto getMarsDetailsDto() {
        return marsDetailsDto;
    }

    public void setMarsDetailsDto(MarsDetailsDto marsDetailsDto) {
        this.marsDetailsDto = marsDetailsDto;
    }
}
