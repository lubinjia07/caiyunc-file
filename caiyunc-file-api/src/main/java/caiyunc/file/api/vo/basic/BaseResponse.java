package caiyunc.file.api.vo.basic;

import lombok.Data;

@Data
public class BaseResponse {
    protected int status;

    protected String msg = "";
    private String traceId;

    public BaseResponse() {
    }

    public BaseResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public void successResult() {
        this.status = 200;
        this.msg = "";
    }

    public void successResult(String msg) {
        this.status = 200;
        this.msg = msg;
    }

    public static BaseResponse success() {
        BaseResponse response = new BaseResponse();
        response.successResult();
        return response;
    }

    public static BaseResponse success(String msg) {
        BaseResponse response = new BaseResponse();
        response.successResult(msg);
        return response;
    }

    public void badRequestResult() {
        this.status = 400;
        this.msg = "";
    }

    public void badRequestResult(String msg) {
        this.status = 400;
        this.msg = msg;
    }

    public void noAuthorityResult() {
        this.status = 401;
        this.msg = "";
    }

    public void noAuthorityResult(String msg) {
        this.status = 401;
        this.msg = msg;
    }

    public void failureResult() {
        this.status = 500;
    }

    public void failureResult(String msg) {
        this.status = 500;
        this.msg = msg;
    }

    public void bizErrorResult() {
        this.status = 501;
    }

    public void bizErrorResult(String msg) {
        this.status = 501;
        this.msg = msg;
    }


}
