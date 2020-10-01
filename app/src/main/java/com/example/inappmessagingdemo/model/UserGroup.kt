package com.example.inappmessagingdemo.model

import androidx.annotation.StringRes
import com.example.inappmessagingdemo.R

enum class UserGroup(@StringRes val labelRes: Int) {
    GROUP_A(R.string.user_group_a),
    GROUP_B(R.string.user_group_b),
    GROUP_C(R.string.user_group_c)
}