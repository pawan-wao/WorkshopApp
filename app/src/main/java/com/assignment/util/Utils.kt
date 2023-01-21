package com.assignment.util

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.assignment.R
import com.assignment.data.model.Workshop

class Utils {
    companion object {
        fun dialog(context: Context): Dialog {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.layout_custom_progress_dialog)
            return dialog
        }
        fun workShop(): ArrayList<Workshop> {
            val list = ArrayList<Workshop>()
            list.add(
                Workshop(
                    0,
                    "Android Development",
                    "https://cdn.pixabay.com/photo/2016/11/29/12/30/phone-1869510__480.jpg",
                    "01/12/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Web Development",
                    "https://cdn.pixabay.com/photo/2015/12/04/14/05/code-1076536__480.jpg",
                    "26/12/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Android Development",
                    "https://cdn.pixabay.com/photo/2020/06/08/19/48/corona-5275916__480.jpg",
                    "14/12/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Workshop on cyber security",
                    "https://cdn.pixabay.com/photo/2020/04/25/12/14/circle-5090539__480.jpg",
                    "14/12/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Digital Marketing",
                    "https://cdn.pixabay.com/photo/2017/01/14/10/56/people-1979261__480.jpg",
                    "01/01/2023"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Workshop on Java",
                    "https://cdn.pixabay.com/photo/2016/11/30/20/58/programming-1873854__480.png",
                    "30/11/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Workshop on Cpp",
                    "https://cdn.pixabay.com/photo/2015/09/05/20/02/coding-924920__480.jpg",
                    "29/11/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Game Development",
                    "https://cdn.pixabay.com/photo/2016/06/09/10/00/smartphone-1445489__480.jpg",
                    "23/12/2022"
                )
            )

            return list
        }
    }
}