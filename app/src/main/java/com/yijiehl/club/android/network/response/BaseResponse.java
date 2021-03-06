/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: BaseResponse.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/5.  <br/>
 */
package com.yijiehl.club.android.network.response;

import com.uuzz.android.util.net.response.AbstractResponse;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: BaseResponse <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/5 <br/>
 * @author 谌珂 <br/>
 */
public class BaseResponse extends AbstractResponse {
    /** 结果状态 */
    protected ResultMsg returnMsg;

    public ResultMsg getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(ResultMsg returnMsg) {
        this.returnMsg = returnMsg;
    }
}
