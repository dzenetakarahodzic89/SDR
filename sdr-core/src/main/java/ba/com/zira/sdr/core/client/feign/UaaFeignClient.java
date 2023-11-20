package ba.com.zira.sdr.core.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import ba.com.zira.commons.message.response.PagedPayloadResponse;
import ba.com.zira.sdr.api.model.user.UserCodeDisplay;

@FeignClient(name = "${uaa.service.location:http://uaa}", decode404 = true)
public interface UaaFeignClient {

    public static String USER_ID = "User-ID= 'system'";
    public static String TRANSACTION_ID = "Transaction-ID='swagger-TID-1'";

    @GetMapping(value = "/user/getallusercodes", headers = { USER_ID, TRANSACTION_ID })
    PagedPayloadResponse<UserCodeDisplay> findAllUsers();
}