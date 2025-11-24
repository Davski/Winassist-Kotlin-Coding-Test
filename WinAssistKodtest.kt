const val alRIGHT = 0;
const val alLEFT = 1;

fun main() {
    val lagerPoster = DM.getLager()
    val result = KodTest().TestaInkopspriserBtnClick(lagerPoster)
    //println(result)
}

class KodTest {
    fun TestaInkopspriserBtnClick(lagerPoster: List<LagerPost>): Pair<Int?,List<String>?> {
	var ArtMK: String = ""
	var ArtNr: String = ""
	var Typ: String = ""
	var Kod: String = ""
	var MK: String = ""
	var RK: String = ""
	var VG: String = ""
	var PG: String = ""
	var Benamning: String = ""
	var LgrStatus: String = ""

	// Im using these two for the cases where I need to use Brutto and Netto as Str since I'm not supposed to use IsFloatString()
	var BruttoStr = ""
	var NettoStr = ""

	var Brutto: Double = 0.0
	var Netto: Double? = 0.0
	var TaMed: Boolean
	var Antal = 0
	var gAktuellInkopsrabatt = 1;

	// This isn't actually used in the code but I'm leaving this here since the original Delphi code has this.
	var OrderFields = "mk"
	var LagerRE = mutableListOf<String>();
	LagerRE.clear()
	for (lagerPost in lagerPoster) {
	    ArtMK = lagerPost.artikelMk
	    LgrStatus = lagerPost.lgrStatus
	    TaMed = false

	    // Can make the following if-statement prettier by using enums or something similar
	    if (LgrStatus == "lgrJA" || LgrStatus == "lgrHEMTAGEN" || LgrStatus == "lgrUTGAENDE") {
		var artikel = DM.getArtikel(ArtMK)

		if (artikel != null) {
		    Antal++
		    ArtNr = artikel.artikelnr
		    MK = artikel.mk
		    Benamning = artikel.benamning1
		    RK = artikel.rabattKod
		    VG = artikel.varuGrupp
		    PG = artikel.prodGrupp

		    if (artikel.fastPris) {
			Typ = "F"
			Kod = "IS"
			Brutto = artikel.pris
			Netto = artikel.inkop

			// Maybe I should use NettoStr here? But artikel.inkop is by default a double so it makes no sense. I will leave this as null instead.
			TaMed = (Netto == null)
		    }

		    else if (artikel.useNetto) {
			Typ = "V"
			Brutto = artikel.vNetto
			Netto = getInkopsPrisVNetto(Brutto, MK, RK, VG, PG)
			TaMed = (RK.isEmpty()) || (Netto >= Brutto)

			if (RK.isEmpty()) {
			    Kod = "RS"
			}

			else {
			    Kod = "RV"
			}
		    }

		    else {
			Typ = "B"
			Brutto = artikel.pris
			Netto = getInkopsPris(Brutto, MK, RK, PG, VG)
			TaMed = (RK.isEmpty()) || (Netto >= Brutto)

			if (RK.isEmpty()) {
			    Kod = "RS"
			}

			else {
			    Kod = "RV"
			}
		    }
		}

		else {
		    ArtNr = lagerPost.artikelnr
		    MK = lagerPost.mk
		    Benamning = L_(199, "SAKNAS I ARTIKELREGISTER!!")
		    Typ = ""
		    Kod = ""
		    RK = ""

		    // In the Delphi code this is set to "" but I will assume they're double here and use BruttoStr and NettoStr for string inputs, so these can possibly be removed.
		    Brutto = 0.0
		    Netto = 0.0
		    TaMed = true
		}
	    }
	    if (TaMed) {

		// Converting double values to Str values for the data visualization into LagerRE
		NettoStr = Netto?.toString() ?: ""
		BruttoStr = Brutto.toString()

		Typ = AddBlanks(Typ, 4, alLEFT)
		Kod = AddBlanks(Kod, 4, alLEFT)
		ArtNr = AddBlanks(ArtNr, 18, alLEFT)
		MK = AddBlanks(MK, 5, alLEFT)
		RK = AddBlanks(RK, 5, alLEFT)
		Benamning = AddBlanks(Benamning, 30, alLEFT)
		NettoStr = AddBlanks(NettoStr, 10, alRIGHT)
		BruttoStr = AddBlanks(BruttoStr, 10, alRIGHT)
		LagerRE.add(Typ+Kod+ArtNr+MK+RK+Benamning+NettoStr+BruttoStr)
	    }

	}
	OrderFields = ""
	LagerRE.add("")
	LagerRE.add(L_(200, "Typer:"))
	LagerRE.add(L_(201, "B = Inköpspris beräknat på rabattkod mot bruttopris"))
	LagerRE.add(L_(202, "V = Inköpspris beräknat på rabattkod mot verkstadsnetto"))
	LagerRE.add(L_(203, "F = Fast inköpspris"))

	LagerRE.add("")
	LagerRE.add(L_(204, "Koder:"))
	LagerRE.add(L_(205, "IS = Inköpspris saknas"))
	LagerRE.add(L_(206, "RS = Rabattkod saknas"))
	LagerRE.add(L_(207, "RV = Rabattkod saknar värde"))
	Typ = AddBlanks("Typ", 4, alLEFT)
	Kod = AddBlanks("Kod", 4, alLEFT)
	ArtNr = AddBlanks(L_(37, "Artikelnr"), 18, alLEFT)
	MK = AddBlanks("MK", 5, alLEFT)
	RK = AddBlanks("RK", 5, alLEFT)
	Benamning = AddBlanks(L_(98, "Benämning"), 30, alLEFT)
	NettoStr = AddBlanks(L_(209, "Inköp"), 10, alRIGHT)
	BruttoStr = AddBlanks(L_(102, "Pris"), 10, alRIGHT)
	LagerRE.add(0, "--------------------------------------------------------------------------------------\n")
	LagerRE.add(0, Typ+Kod+ArtNr+MK+RK+Benamning+NettoStr+BruttoStr)
	// println(Antal)
	// println(LagerRE.joinToString("\n"))
	return Pair(Antal, LagerRE)
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

    fun AddBlanks(S: String, Antal: Int, Typ: Int): String {
	return if (Typ == 0) S.padStart(Antal) else S.padEnd(Antal)
    }
}

class DM {
    companion object {
	val lager1 = LagerPost("123VO", "lgrJA", "123", "VO")
	val lager2 = LagerPost("456VO", "lgrHEMTAGEN", "456", "VO")
	val lager3 = LagerPost("789VO", "lgrUTGAENDE", "789", "VO")
	val lager4 = LagerPost("000VO", "lgrJA", "000", "VO")
	val lager5 = LagerPost("111VO", "lgrHEMTAGEN", "111", "VO")
	val lager6 = LagerPost("333VO", "lgrUTGAENDE", "333", "VO")
	val lager7 = LagerPost("666VO", "lgrJA", "666", "VO")
	val lager8 = LagerPost("ABCVO", "lgrHEMTAGEN", "ABC", "VO")
	val lager9 = LagerPost("999VO", "lgrUTGAENDE", "999", "VO")
	val artikel1 = Artikel("123VO", "123", "VO", "Artikel 1", "RAB1", "VGR1", "PG1", true, 123.45, 0.0, false, 0.0)
	val artikel2 = Artikel("456VO", "456", "VO", "Artikel 2", "RAB2", "VGR2", "PG2", true, 234.56, 0.0, false, 0.0)
	val artikel3 = Artikel("789VO", "789", "VO", "Artikel 3", "RAB3", "VGR3", "PG3", true, 000.0, 1.0, false, 0.0)
	val artikel4 = Artikel("000VO", "000", "VO", "Artikel 4", "", "VGR4", "PG4", false, 100.0, 2.0, false, 0.0)
	val artikel5 = Artikel("111VO", "111", "VO", "Artikel 5", "RAB5", "VGR5", "PG5", false, 200.0, 3.0, true, 0.0)
	val artikel6 = Artikel("333VO", "333", "VO", "Artikel 6", "RAB6", "VGR6", "PG6", false, 1.0, 4.0, false, 0.0)
	val artikel7 = Artikel("666VO", "666", "VO", "Artikel 7", "RAB7", "VGR7", "PG7", true, 400.0, null, false, 0.0)
	val artikel8 = Artikel("777VO", "777", "VO", "Artikel 8", "RAB8", "VGR8", "PG8", true, 400.0, null, false, 0.0)
	val artikel9 = Artikel("999VO", "999", "VO", "Artikel 9", "", "VGR9", "PG9", false, 400.0, 0.0, true, 0.0)
	fun getLager(): List<LagerPost> {
	    return listOf(lager1, lager2, lager3, lager4, lager5, lager6, lager7, lager8, lager9)
	}

	fun getArtikel(artikelMk: String): Artikel? {
	    return when (artikelMk) {
		"123VO" -> artikel1
		"456VO" -> artikel2
		"789VO" -> artikel3
		"000VO" -> artikel4
		"111VO" -> artikel5
		"333VO" -> artikel6
		"666VO" -> artikel7
		"777VO" -> artikel8
		"999VO" -> artikel9
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
