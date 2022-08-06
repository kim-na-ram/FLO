package com.naram.flo.util

object Extensions {
    /**
     * millisecond 를 "minute:second" 형식으로 변환
     */
    fun Int.timeToStringMS(): String {
        val sec = (this / 1000) % 60
        val min = (this / 1000) / 60 % 60

        return "${String.format("%02d", min)}:${String.format("%02d", sec)}"
    }

    /**
     * "minute:second:millisecond" 를 millisecond 로 변환
     */
    fun String.timeToInt(): Int {
        this.split(":").let {
            val min = it[0].toInt() * 60 * 1000
            val sec = it[1].toInt() * 1000
            val milliSec = it[2].toInt()

            return min + sec + milliSec
        }
    }

    /**
     * millisecond 에서 백의 자리 이하 수를 절삭
     */
    fun Int.convertTime(): Int {
        return (this / 100) * 100
    }
}