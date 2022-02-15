package uz.example.focusstart.data.model


data class Data(
    var Date:String="",
    var PreviousDate:String="",
    var PreviousURL:String="",
    var Timestamp:String="",
    var Valute:Map<String,Currency> = mapOf()
)