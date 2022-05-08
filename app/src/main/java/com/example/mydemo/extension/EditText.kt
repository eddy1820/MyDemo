package com.example.mydemo.extension

import android.graphics.Typeface
import android.text.*
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.regex.Pattern

fun EditText.requestFocusIf(condition: Boolean) {
    if (condition) this.requestFocus() else this.clearFocus()
}

fun EditText.setLimitChinese() {
    val typeFilter = InputFilter { source, start, end, dest, dstart, dend ->
        val p = Pattern.compile("[\\u4e00-\\u9fa5]+")
        val m = p.matcher(source.toString())
        if (!m.matches()) "" else null
    }
    this.filters = arrayOf(typeFilter)
}

@Deprecated(
    "use doOnTextChanged",
    ReplaceWith("this.doOnTextChanged(callback)", "androidx.core.widget.doOnTextChanged")
)
fun EditText.addSimpleTextChangedListener(callback: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) =
    this.doOnTextChanged(callback)

// 擋前面輸入多個0
fun EditText.setNoZeroText(valStr: String, value: Double) {
    if (
        valStr.startsWith("0")
        && valStr.split(".")[0].length > 1
        && valStr != "0.0"
    ) setText((if (value > 0.0 || value < 1.0) value else 0.0).toString())
}

fun EditText.setNoZeroText(valStr: String, value: Int) {
    if (
        valStr.startsWith("0")
        && valStr.split(".")[0].length > 1
        && valStr != "0.0"
    ) setText((if (value > 0 || value < 1) value else 0).toString())
}

fun EditText.setNoZeroText(valStr: String, value: Long) {
    if (
        valStr.startsWith("0")
        && valStr.split(".")[0].length > 1
        && valStr != "0.0"
    ) setText((if (value > 0 || value < 1) value else 0).toString()).also { setSelection(this.text.length) }
}

fun EditText.setNoEditNoClick() {
    this.isFocusable = false
    this.isFocusableInTouchMode = false
    this.isEnabled = false
}

fun EditText.setBankCardMode(): Observable<String> {
    val textChangedCallback = PublishSubject.create<String>()

    //上次输入框中的内容
    var lastString: String = ""
    //光标的位置
    var selectPosition: Int = 0

    //追加字符
    val item = " "

    val filterArray = arrayOfNulls<InputFilter>(1)
    filterArray[0] = InputFilter.LengthFilter(24)
    this.filters = filterArray

    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            //获取输入框中的内容,不可以去空格
            val etContent = text.toString()
            if (TextUtils.isEmpty(etContent)) {
                textChangedCallback.onNext("")
                return
            }
            //重新拼接字符串
            val newContent = addSpaceByCredit(etContent)
            //保存本次字符串数据
            lastString = newContent

            //如果有改变，则重新填充
            //防止EditText无限setText()产生死循环
            if (newContent != etContent) {
                setText(newContent)
                try {
                    //保证光标的位置
                    setSelection(if (selectPosition > newContent.length) newContent.length else selectPosition)
                } catch (e: Exception) {
                    //刚好为限制字符的整数倍时添加空格后会出现越界的情况
                    //AppLogUtil.e("超过限制字符");
                }

            }
            //触发回调内容
            textChangedCallback.onNext(newContent.replace(" ", ""))
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //因为重新排序之后setText的存在
            //会导致输入框的内容从0开始输入，这里是为了避免这种情况产生一系列问题
            if (start == 0 && count > 1 && selectionStart == 0) {
                return
            }

            val textTrim = text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(textTrim)) {
                return
            }

            //如果 before >0 && count == 0,代表此次操作是删除操作
            if (before > 0 && count == 0) {
                selectPosition = start
                if (TextUtils.isEmpty(lastString)) {
                    return
                }
                //将上次的字符串去空格 和 改变之后的字符串去空格 进行比较
                //如果一致，代表本次操作删除的是空格
                if (textTrim == lastString.replace(item.toRegex(), "")) {
                    //帮助用户删除该删除的字符，而不是空格
                    val stringBuilder = StringBuilder(lastString)
                    stringBuilder.deleteCharAt(start - 1)
                    selectPosition = start - 1
                    setText(stringBuilder.toString())
                }
            } else {
                //此处代表是添加操作
                //当光标位于空格之前，添加字符时，需要让光标跳过空格，再按照之前的逻辑计算光标位置
                if ((start + count) % 5 == 0) {
                    selectPosition = start + count + 1
                } else {
                    selectPosition = start + count
                }
            }
        }

        /**
         * 每4位添加一个空格
         *
         * @param content
         * @return
         */
        fun addSpaceByCredit(content: String): String {
            var content = content
            if (TextUtils.isEmpty(content)) {
                return ""
            }
            content = content.replace(item.toRegex(), "")
            if (TextUtils.isEmpty(content)) {
                return ""
            }
            val newString = StringBuilder()
            for (i in 1..content.length) {
                if (i % 4 == 0 && i != content.length) {
                    newString.append(content[i - 1] + item)
                } else {
                    newString.append(content[i - 1])
                }
            }
            return newString.toString()
        }
    })



    return textChangedCallback
}