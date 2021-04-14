package threads

import threads.Storage.countingOver
import threads.Storage.result
import threads.Storage.timeToGo
import kotlin.random.Random

object Storage {
    @Volatile
    var timeToGo: Boolean = false

    @Volatile
    var result: String? = null

    @Volatile
    var countingOver: Boolean = true
}

fun main() {
    val thread1 = Thread(Input())
    val thread2 = Thread(Counter())
    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    println("До свидания!")

}

class Input: Runnable {
    override fun run() {
        while (!timeToGo) {
            if(countingOver) {
                println("Введите строку для подсчёта символов: ")
                val e =  readLine()
                if(e == "quit") {
                    timeToGo = true
                } else {
                    result = e
                    countingOver = false
                }
            }
        }
    }
}

class Counter: Runnable {
    private var previousResult: String? = null
    override fun run() {
        while (!timeToGo) {
            if (result != previousResult) {
                previousResult = result
                println(countLetters(result!!).joinToString())
                countingOver = true
            }
        }
    }

    private fun countLetters(string: String): MutableMap<Char, Int> {
        val resultMap = mutableMapOf<Char, Int>()
        for(i in string) {
            Thread.sleep(Random.nextLong(2000L))
            if(resultMap.containsKey(i)) {
                var value = resultMap[i]!!
                value++
                resultMap[i] = value
            } else {
                resultMap[i] = 1
            }
        }
        return resultMap
    }

    private fun Map<*,*>.joinToString(): String {
        return this.map { (key, value) -> "\'$key\': $value"}.joinToString(",")
    }
}