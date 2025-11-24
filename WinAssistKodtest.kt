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
	println(Antal)
	println(LagerRE)
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
	return if (Typ == 1) S.padStart(Antal) else S.padEnd(Antal)
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
