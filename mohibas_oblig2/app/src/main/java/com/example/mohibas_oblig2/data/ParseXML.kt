package com.example.mohibas_oblig2.data

import android.util.Xml
import io.ktor.utils.io.errors.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.InputStream
/*
private val ns: String? = null

class ParseXML {
    fun parse(inputStream: InputStream): List<StemmeXml> {
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            return lesFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException("Forventet start tag, men fikk ${parser.eventType}")
        }

        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
                else -> {} // ignorer andre tilfeller
            }
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun lesEntry(parser: XmlPullParser): StemmeXml {

        parser.require(XmlPullParser.START_TAG,ns,"parti")
        var id: Int? = null
        var stemmer: Int? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }

            when (parser.name) {
                "id" -> id = readAttribute(parser, parser.name).toIntOrNull()
                "votes" -> stemmer = readAttribute(parser, parser.name).toIntOrNull()
                else -> skip(parser)
            }
        }
        return StemmeXml(id, stemmer)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readAttribute(parser: XmlPullParser, tag: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)

        val value = readText(parser)

        parser.require(XmlPullParser.END_TAG, ns, tag)

        return value
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var resultat = ""

            if (parser.next() == XmlPullParser.TEXT) {
                resultat = parser.text
                parser.nextTag()
            }

        return resultat
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun lesFeed(parser: XmlPullParser): List<StemmeXml> {

        val parties = mutableListOf<StemmeXml>()
        parser.require(XmlPullParser.START_TAG, ns, "districtThree")

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "party") {
                parties.add(lesEntry(parser))
            } else {
                skip(parser)
            }
        }
        return parties
    }
}

 */


private val ns: String? = null
class ParseXML {
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<StemmeXml> {
        inputStream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }


    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<StemmeXml> {
        val resultat = mutableListOf<StemmeXml>()

        parser.require(XmlPullParser.START_TAG, ns, "districtThree")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag
            if (parser.name == "party") {
                resultat.add(readEntry(parser))
            } else {
                skip(parser)
            }
        }
        return resultat
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): StemmeXml {
        parser.require(XmlPullParser.START_TAG, ns, "party")
        var id : Int? = null
        var votes : Int? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "id" -> id = readAttribute(parser, parser.name).toIntOrNull()
                "votes" -> votes = readAttribute(parser, parser.name).toIntOrNull()

                else -> skip(parser)
            }
        }
        return StemmeXml(id, votes)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readAttribute(parser: XmlPullParser, tag : String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        val verdi = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return verdi
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}


