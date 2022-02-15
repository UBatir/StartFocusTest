package uz.example.focusstart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currency(
    var ID:String="",
    var NumberCode:String="",
    var CharCode:String="",
    var Nominal:Int=0,
    var Name:String="",
    var Value:Double=0.0,
    var Previous:Double=0.0
):Parcelable
