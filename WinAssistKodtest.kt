const val alRIGHT = 0;
const val alLEFT = 1;

class KodTest {
    fun TestaInkopspriserBtnClick(LagerPoster: List<LagerPost>): Pair<Int,List<String>> {
	var Antal: integer = 0
	var OrderFields: string = 'mk'
    }

    fun getInkopsPris(brutto: Double, mk: String, rk: String, vg: String, pg: String): Double {
	return brutto
    }

    fun getInkopsPrisVNetto(brutto: Double, mk: String, rk: String, vg: String, pg: String): Double {
	return brutto / 2.0
    }

    fun L_(id: Int, text: String): String {
        return text
    }

    fun AddBlanks(S: string, Antal: int, Typ, integer): String {
	return if (Typ = 1) S.padStart(Antal) else S.padEnd(Antal)
    }
}

class DM {
    companion object {
	val lager1 = LagerPost("123VO", "lgrJA", "123", "VO")
	val lager2 = LagerPost("456VO", "lgrHEMTAGEN", "456", "VO")
	val artikel1 = Artikel("123VO", "123", "VO", "Artikel 1", "RAB1", "VGR1", "PG1", true, 123.45, 0.0, false, 0.0)
	val artikel2 = Artikel("456VO", "456", "VO", "Artikel 2", "RAB2", "VGR2", "PG2", true, 234.56, 0.0, false, 0.0)
	fun getLager(): List<LagerPost> {
	    return listOf(lager1, lager2)
	}

	fun getArtikel(artikelMk: String): Artikel? {
	    return when (artikelMk) {
		"123VO" -> artikel1
		"456VO" -> artikel2
		else -> null
	    }
	}
    }
}

data class LagerPost(
    val artikelMk: String,
    val lgrStatus: String,
    val artikelnr: String,
    val mk: String
)

data class Artikel(
    val artikelMk: String,
    val artikelnr: String,
    val mk: String,
    val benamning1: String,
    val rabattKod: String,
    val varuGrupp: String,
    val prodGrupp: String,
    val fastPris: Boolean,
    val pris: Double,
    val inkop: Double?,
    val useNetto: Boolean,
    val vNetto: Double
)
