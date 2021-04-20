package threads

import threads.Storage.threadPool
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import kotlin.random.Random

object Storage {
    val threadPool = Executors.newSingleThreadExecutor()
}

fun main() {
    while (true) {

        println("Введите строку для подсчёта символов: ")
        val e = readLine()
        if (e != null) {
            if (e == "quit") {
                break
            } else {
                val ss = threadPool.submit(Counter(e))
                println(ss.get())
            }
        }

    }

    println("До свидания!")
    threadPool.shutdown()
}

class Counter(private val message: String) : Callable<String> {

    private fun countLetters(string: String): MutableMap<Char, Int> {
        val resultMap = mutableMapOf<Char, Int>()
        for (i in string) {
            Thread.sleep(Random.nextLong(2000L))
            if (resultMap.containsKey(i)) {
                var value = resultMap[i]!!
                value++
                resultMap[i] = value
            } else {
                resultMap[i] = 1
            }
        }
        return resultMap
    }

    private fun Map<*, *>.joinToString(): String {
        return this.map { (key, value) -> "\'$key\': $value" }.joinToString(",")
    }

    override fun call(): String {
        return countLetters(message).joinToString()
    }
}