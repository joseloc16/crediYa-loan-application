package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.CreateLoanApplicationRequest;
import co.com.bancolombia.api.dto.LoanApplicationResponse;
import co.com.bancolombia.model.loanapplication.LoanApplication;
import co.com.bancolombia.usecase.createloanapplication.CreateLoanApplicationCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "statusCode", ignore = true)
    LoanApplication toDomain(CreateLoanApplicationCommand cmd);

    CreateLoanApplicationCommand toCommand(CreateLoanApplicationRequest request);

    @Mapping(target = "status", source = "statusCode")
    LoanApplicationResponse toResponse(LoanApplication loan);

}
