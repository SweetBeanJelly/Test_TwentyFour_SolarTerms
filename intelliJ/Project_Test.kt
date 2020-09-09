import org.w3c.dom.Element
import org.w3c.dom.Node
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.xml.parsers.DocumentBuilderFactory

val current: LocalDate = LocalDate.now()
val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd")
var today: String = current.format(formatter)

var name24Index1 =""
var name24Index2 =""

var date24 =""

var date24Index1 =""
var date24Index2 =""

fun main() {

    get24Divisions()

    println("현재 날짜 -> $current")

    Integer.parseInt(today)
    Integer.parseInt(date24Index1)
    Integer.parseInt(date24Index2)

    when {
        today < date24Index1 -> {
            println("다가오는 24절기는 $name24Index1 입니다.")
        }
        today == date24Index1 -> {
            println("오늘은 $name24Index1 입니다.")
        }
        today > date24Index1 && today < date24Index2 -> {
            println("다가오는 24절기는 $name24Index2 입니다.")
        }
        today == date24Index2 -> {
            println("오늘은 $name24Index2 입니다.")
        }
        else -> {
            print("이번 달 24절기는 없습니다.")
        }
    }

}

fun get24Divisions() {
    val year = "2020"
    val month = "09"
    val key = "85pxiQNHO6gsxSjBFQfzN5lxPOIub30SlkWNkEvKSFjX%2BBl0sCbOltv6etE002jZB5OQkf9LFYqVZgpr2%2FivQg%3D%3D"
    val url ="http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/get24DivisionsInfo?solYear=$year&solMonth=$month&ServiceKey=$key"

    var count = true // 1일과 가깝거나 31일과 가깝거나 확인

    val xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url) // xml 형태를 파싱하기 위한 코드

    val itemList = xml.getElementsByTagName("item") // 찾고자 하는 데이터가 어느 위치에 있는지

    for (i in 0 until itemList.length) {
        val n: Node = itemList.item(i)

        if (n.nodeType == Node.ELEMENT_NODE) {

            val element = n as Element
            val map = mutableMapOf<String, String>()

            for (j in 0 until element.attributes.length) {
                map.putIfAbsent(
                        element.attributes.item(j).nodeName,
                        element.attributes.item(j).nodeValue
                )
            }

            if(count) {
                name24Index1 = element.getElementsByTagName("dateName").item(0).textContent
                count = false
                date24 = element.getElementsByTagName("locdate").item(0).textContent
                date24Index1 = date24.substring(6,8)
            } else {
                name24Index2 = element.getElementsByTagName("dateName").item(0).textContent
                date24 = element.getElementsByTagName("locdate").item(0).textContent
                date24Index2 = date24.substring(6,8)
            }


        }
    }
}