package com.example.demoV.base;
import com.example.demoV.common.MessageCommon;
import org.springframework.http.ResponseEntity;
public class BaseResponse {
    protected ResponseEntity<?> getResponse(Object data) {
        return ResponseEntity.ok(genResponse(data));
    }
    private SuccessResponse genResponse(Object data) {
        SuccessResponse myResponse = new SuccessResponse();
        myResponse.setData(data);
        myResponse.setStatus(200);
        myResponse.setMessage(MessageCommon.SUCCESS);
        return myResponse;
    }
}
