package caiyunc.file.api.enums.basic;

import lombok.Getter;

@Getter
public enum HttpStatusCodeEnums {

    PARAM_ERROR(400, "参数有误");
    //PARAM_ERROR(502, "系统内部错误");


    private Integer code;

    private String desc;


    HttpStatusCodeEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
