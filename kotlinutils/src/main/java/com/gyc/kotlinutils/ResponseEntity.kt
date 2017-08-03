package com.gyc.kotlindemo.bean

import java.io.Serializable

/**
 * Created by Surface on 2016/10/31.
 */

data class ResponseEntity<T>(var isSucceed: Boolean, var msg: String?, var code: String?, var data: T?) : Serializable {

}
