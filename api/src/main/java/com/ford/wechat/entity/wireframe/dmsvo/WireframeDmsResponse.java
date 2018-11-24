package com.ford.wechat.entity.wireframe.dmsvo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lc on 2017/9/18.
 */
@Data
public class WireframeDmsResponse {

    /*状态码：0表示操作成功，其他均为错误或异常，不同业务的错误代码含义不通。 */
    private String status_code;

    /*状态描述：对状态码的详细文字描述。*/
    private String status_desc;

    /*返回内容：根据不同的业务，返回内容不同，格式为JASON格式字符串。*/
    private ArrayList<HashMap> content;
}
