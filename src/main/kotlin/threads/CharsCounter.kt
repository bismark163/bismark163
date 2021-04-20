package threads

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.random.Random

object Storage {
    val threadPool = Executors.newSingleThreadExecutor()
}

fun main() = runBlocking {

    while (true) {
        val defferedString = async {
            println("Введите строку для подсчёта символов: ")
            val result = readLine()
            result
        }

        if (defferedString.await()!! == "quit") {
            break
        }

        runBlocking {
            countSymbols(defferedString.await()!!)
        }
    }

    println("До свидания")
}

suspend fun countSymbols(string: String) {
    val resultMap = mutableMapOf<Char, Int>()
    for (i in string) {
        delay(Random.nextLong(2000L))
        if (resultMap.containsKey(i)) {
            var value = resultMap[i]!!
            value++
            resultMap[i] = value
        } else {
            resultMap[i] = 1
        }
    }
    println(resultMap.joinToString())
}

fun  Map<*, *>.joinToString(): String {
    return this.map { (key, value) -> "\'$key\': $value" }.joinToString(",")
}