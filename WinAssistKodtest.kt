class KodTest {
    fun TestaInkopspriserBtnClick(LagerPoster: List<LagerPost>): Pair<Int,List<String>> {
        /* PSEUDO CODE from AktiveraSnittPris.pas function to be translated.
        Use DM.getLager() to get List, DM.getArtikel() to get articles.
            Antal is 0
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
