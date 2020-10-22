package caiyunc.file.api.vo.basic;


import caiyunc.file.api.enums.basic.HttpStatusCodeEnums;
import lombok.Data;

@Data
public class ApiResponse<T> extends BaseResponse {
    private T data;

    public void successResult(T data) {
        super.status = 200;
        this.data = data;
    }

    public void successResult(T data, String msg) {
        super.status = 200;
        this.data = data;
        super.msg = msg;
    }


    //region 临时使用，后期整改优化

    public static <T> ApiResponse<T> ofSuccess(T t) {
        ApiResponse<T> response = new ApiResponse();
        response.setStatus(200);
        response.setData(t);
        return response;
    }


    public static <T> ApiResponse<T> ofParamError(String msg) {
        ApiResponse<T> response = new ApiResponse();
        response.badRequestResult(msg);
        return response;
    }

    public static <T> ApiResponse<T> ofParamError() {
        ApiResponse<T> response = new ApiResponse();
        response.badRequestResult(HttpStatusCodeEnums.PARAM_ERROR.getDesc());
        return response;
    }


    public static <T> ApiResponse<T> ofNoAuth() {
        ApiResponse<T> response = new ApiResponse();
        response.noAuthorityResult("请先登录");
        return response;
    }


    public static <T> ApiResponse<T> ofBizError(String msg) {
        ApiResponse<T> response = new ApiResponse();
        response.failureResult(msg);
        return response;
    }

    public static <T> ApiResponse<T> ofServerError() {
        ApiResponse<T> response = new ApiResponse();
        response.failureResult("系统繁忙");
        return response;
    }


    public static <T> ApiResponse<T> ofServerError(String msg) {
        ApiResponse<T> response = new ApiResponse();
        response.failureResult(msg);
        return response;
    }

    //endregion
}
