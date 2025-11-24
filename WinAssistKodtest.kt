const val alRIGHT = 0;
const val alLEFT = 1;

class KodTest {
    fun TestaInkopspriserBtnClick(LagerPoster: List<LagerPost>): Pair<Int,List<String>> {
        /* PSEUDO CODE from AktiveraSnittPris.pas function to be translated.
        Use DM.getLager() to get List, DM.getArtikel() to get articles.
            Antal is 0
	    OrderFields is 'mk'
	    For each LagerPost in LagerPoster:
                ArtMK is LagerPost.artikelMk
                LgrStatus is LagerPost.lgrStatus
                TaMed is false
                if LgrStatus is lgrJA or lgrHEMTAGEN or lgrUTGAENDE:
                    For each Artikel in Artiklar:
                        if DM.getArtikel(ArtMK) is not null:
                            Antal++
                            ArtNr is Artikel.artikelNr
                            MK is Artikel.mk
                            Benamning is Artikel.benamning1
                            RK is Artikel.rabattKod
                            VG is Artikel.varuGrupp
                            PG is Artikel.prodGrupp
                            if Artikel.fastPris is true:
                                Typ is 'F'
                                Kod is 'IS
                                Brutto is Artikel.pris
                                Netto is Artikel.inkop
                                TaMed is Netto equals null
                            else if Artikel.useNetto:
                                Typ is 'V'
                                Brutto is Artikel.vNetto
                                Netto is getInkopsPrisVNetto(Brutto, MK, RK, VG, PG)
                                TaMed is (RK equals '') or (Netto is geq Brutto)
                                if RK is '':
                                    Kod is 'RS'
                                else:
                                    Kod is 'RV'
                            else:
                                Typ is 'B'
                                Brutto is Artikel.pris
                                Netto is getInkopsPris(Brutto, MK, RK, PG, VG)
                                TaMed is (RK equals '') or (Netto is geq Brutto)
                                if RK is '':
                                    Kod is 'RS'
                                else:
                                    Kod is 'RV'
                        else:
                            ArtNr is LagerPost.artikelnr
                            MK is LagerPost.mk
                            Benamning is L_(199, 'SAKNAS I ARTIKELREGISTER!!')
                            Typ is ''
                            Kod is ''
                            RK is ''
                            Brutto is ''
                            Netto is ''
                            TaMed is True
		if TaMed:
		    Typ is AddBlanks(Typ, 4, alLEFT)
		    Kod is AddBlanks(Kod, 4, alLEFT)
		    ArtNr is AddBlanks(ArtNr, 18, alLEFT)
		    MK is AddBlanks(MK, 5, alLEFT)
		    RK is AddBlanks(RK, 5, alLEFT)
		    Benamning is AddBlanks(Benamning, 30, alLEFT)
		    Netto is AddBlanks(Netto, 10, alRIGHT)
		    Brutto is AddBlanks(Brutto, 10, alRIGHT)
		    LagerRE.Lines.Add(Typ+Kod+ArtNr+MK+RK+Benamning+Netto+Brutto)
	    OrderFields is ''
	  LagerRE.Add('')
	  LagerRE.Add(L_(200, 'Typer:'))
	  LagerRE.Add(L_(201, 'B = Inköpspris beräknat på rabattkod mot bruttopris'))
	  LagerRE.Add(L_(202, 'V = Inköpspris beräknat på rabattkod mot verkstadsnetto'))
	  LagerRE.Add(L_(203, 'F = Fast inköpspris'))

	  LagerRE.Add('')
	  LagerRE.Add(L_(204, 'Koder:'))
	  LagerRE.Add(L_(205, 'IS = Inköpspris saknas'))
	  LagerRE.Add(L_(206, 'RS = Rabattkod saknas'))
	  LagerRE.Add(L_(207, 'RV = Rabattkod saknar värde'))
	  Typ is AddBlanks('Typ', 4, alLEFT)
	  Kod is AddBlanks('Kod', 4, alLEFT)
	  ArtNr is AddBlanks(L_(37, 'Artikelnr'), 18, alLEFT)
	  MK is AddBlanks('MK', 5, alLEFT)
	  RK is AddBlanks('RK', 5, alLEFT)
	  Benamning is AddBlanks(L_(98, 'Benämning'), 30, alLEFT)
	  Netto is AddBlanks(L_(209, 'Inköp'), 10, alRIGHT)
	  Brutto is AddBlanks(L_(102, 'Pris'), 10, alRIGHT)
	  LagerRE.Add(0, '--------------------------------------------------------------------------------------')
	  LagerRE.Add(0, Typ+Kod+ArtNr+MK+RK+Benamning+Netto+Brutto)

         */

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
        return Typ;
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
