package com.thbdesabase.orderservices.endpoint;

import com.thbdesabase.orderservices.common.BaseResponse;
import com.thbdesabase.orderservices.common.CommonCode;
import com.thbdesabase.orderservices.common.CommonMessage;
import com.thbdesabase.orderservices.dto.request.RequestHouseHoldDto;
import com.thbdesabase.orderservices.dto.response.ResponseOrderShoppingCartDto;
import com.thbdesabase.orderservices.service.IHouseHoldLeadService;
import com.thbdesabase.orderservices.service.IOrderService;
import com.thbdesabase.orderservices.statval.IApplicationConstant;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping(IApplicationConstant.ContextPath.USER)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserEndpont {

    private final IHouseHoldLeadService houseHoldLeadService;

    @PostMapping(IApplicationConstant.Path.User.ADD_USER)
    BaseResponse<ResponseOrderShoppingCartDto> addHouseHold(@RequestBody RequestHouseHoldDto request) throws ExecutionException, InterruptedException {
        try{

            if (Objects.nonNull(request)
                    && Objects.nonNull(request.getName())
                    && Objects.nonNull(request.getLivingStatus())
                    && Objects.nonNull(request.getEducationStatus())
                    && Objects.nonNull(request.getPassword())
                    && Objects.nonNull(request.getEmail())
                    && Objects.nonNull(request.getPhoneNumb())

            ){
                houseHoldLeadService.saveHouseHold(request);
                return new BaseResponse<>(CommonMessage.OK, CommonCode.SUCCESS);
            }

        } catch (ServiceException e) {
            return new BaseResponse<>(CommonMessage.ERROR,CommonCode.SERVER_ERROR);
        }
        return new BaseResponse<>(CommonMessage.NOT_SAVED,CommonCode.BAD_REQUEST);
    }


}
