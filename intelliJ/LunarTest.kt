import org.w3c.dom.Element
import org.w3c.dom.Node
import java.lang.Exception
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

fun main() {

    // 양력 날짜

    val calendar: Calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    var month = (calendar.get(Calendar.MONTH) + 1).toString()
    val today = calendar.get(Calendar.DATE)

    println("$year - $month - $today")

    // 음력 날짜

    var lunar_month = ""
    var lunar_today = ""
    var lunar_c_year = "" // 평년, 윤년
    var lunar_c_month ="" // 평달, 윤달

    //

    if (month.toInt() < 10) month = "0${month}"

    try {
        val key ="85pxiQNHO6gsxSjBFQfzN5lxPOIub30SlkWNkEvKSFjX%2BBl0sCbOltv6etE002jZB5OQkf9LFYqVZgpr2%2FivQg%3D%3D"
        val url ="http://apis.data.go.kr/B090041/openapi/service/LrsrCldInfoService/getLunCalInfo?solYear=$year&solMonth=$month&solDay=$today&ServiceKey=$key"

        val xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)
        val itemList = xml.getElementsByTagName("item")

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

                lunar_today = element.getElementsByTagName("lunDay").item(0).textContent
                lunar_c_month = element.getElementsByTagName("lunLeapmonth").item(0).textContent
                lunar_month = element.getElementsByTagName("lunMonth").item(0).textContent
                lunar_c_year = element.getElementsByTagName("solLeapyear").item(0).textContent
            }
        }
    }
    catch (e: Exception) { print("ERROR") }
    finally {

        when (lunar_c_month) {
            "평" -> lunar_c_month = "평달"
            "윤" -> lunar_c_month = "윤달"
        }
        when (lunar_c_year) {
            "평" -> lunar_c_year = "평년"
            "윤" -> lunar_c_year = "윤년"
        }

        println("음력 변환 -> $year - $lunar_month - $lunar_today , $lunar_c_year , $lunar_c_month")
    }
}