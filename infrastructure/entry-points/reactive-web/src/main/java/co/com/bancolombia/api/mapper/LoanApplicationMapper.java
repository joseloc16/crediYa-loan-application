package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.req.CreateLoanApplicationRequest;
import co.com.bancolombia.api.dto.res.LoanApplicationResponse;
import co.com.bancolombia.model.loanapplication.LoanApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    LoanApplication toDomain(CreateLoanApplicationRequest request);

    LoanApplicationResponse toResponse(LoanApplication loanEntity);

}
