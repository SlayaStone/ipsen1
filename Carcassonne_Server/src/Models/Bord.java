package Models;

import commonFunctions.Point;

import java.util.ArrayList;

/**
 * Deze class regelt alles met betrekking tot het speelbord
 */
public class Bord {

	private ArrayList<Speler> alleSpelers;

	private Speler spelerBeurt;

	private Tile[][] alleTiles;

	private Tile laatstGeplaatst;

	private ArrayList<Tile> placedTiles;

	private boolean debug = false;

	private int gameBeurt = 1;

	private ArrayList<Point> verwijderHorigeDezeRonde;

	/**
	 * Deze functie geeft aan hoeveel rondes er al zijn geweest
	 *
	 * @return int met aantal beurten die reeds zijn geweest
	 */
	public int gameBeurt() {
		return gameBeurt;
	}

	/**
	 * Deze functie geeft aan welke tile er als laatst geplaatst is
	 *
	 * @return Het Tile object dat als laatste geplaatst is
	 */
	public Tile getLaatstGeplaatst() {
		return laatstGeplaatst;
	}

	/**
	 * Deze constructor zorgt ervoor dat het bord met alle plaatsen voor tiles wordt aangemaakt
	 *
	 * @param spelerList Geeft een ArrayList met alle spelers mee
	 */
	public Bord(ArrayList<Speler> spelerList) {
		verwijderHorigeDezeRonde = new ArrayList<>();
		alleSpelers = spelerList;
		alleTiles = new Tile[100][100];
		spelerBeurt = alleSpelers.get(0);
		setSpelerKleuren();
	}

	/**
	 * Overload constructor voor het laden van een game
	 *
	 * @param spelerList  Geeft een ArrayLists van alle spelers mee
	 * @param bordKaarten Geeft de kaarten van het bord mee
	 */
	public Bord(ArrayList<Speler> spelerList, Tile[][] bordKaarten) {
		verwijderHorigeDezeRonde = new ArrayList<>();
		alleSpelers = spelerList;
		alleTiles = bordKaarten;
		spelerBeurt = alleSpelers.get(0);
		setSpelerKleuren();
	}

	/**
	 * Controleert of kaart plaatsbaar is
	 * @param tile Specifieke tile die geplaatst moet worden
	 * @return true als plaatsbaar, false als niet plaatsbaar
	 */
	public boolean isKaartPlaatsbaar(Tile tile) {

		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {

				if (getTile(x, y - 1) == null && getTile(x + 1, y) == null && getTile(x, y + 1) == null && getTile(x - 1, y) == null) {
					continue;
				}


				System.out.println("RUNNING isKaartPlaatsbaar");

				if (checkKaartFit(x, y, tile)) {
					tile.resetRotation();
					return true;
				}
				tile.draaiKaart();
				if (checkKaartFit(x, y, tile)) {
					tile.resetRotation();
					return true;
				}
				tile.draaiKaart();
				if (checkKaartFit(x, y, tile)) {
					tile.resetRotation();
					return true;
				}

				tile.draaiKaart();
				if (checkKaartFit(x, y, tile)) {
					tile.resetRotation();
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * Verwijdert een horige indien punten behaalt.
	 * @param point Plek op de tile
	 */
	public void verwijderHorige(Point point) {
		verwijderHorigeDezeRonde.add(point);
		getTile(point.getX(), point.getY()).removeHorige();
		System.out.println("HORIGE " + point.getX() + " " + point.getY() + "zal verwijderd worden!");
	}


	private String[] spelerKleuren = {"horigeRood", "horigeBlauw", "horigeGroen", "horigeGeel", "horigePaars"};

	/**
	 * Geeft elke speler zijn eigen horige kleur.
	 */
	private void setSpelerKleuren() {
		System.out.println(" Goeie shit COLOR ");
		for (int i = 0; i < alleSpelers.size(); i++) {
			alleSpelers.get(i).setHorigeKleur(spelerKleuren[i]);
		}

	}

	/**
	 * Deze functie geeft aan welke speler aan de beurt is
	 *
	 * @return int met de locatie van de speler in de spelerlijst die aan de beurt is
	 */
	public Speler getSpelerBeurt() {

		return spelerBeurt;
	}

	/**
	 * Deze functie geeft de volgende speler de beurt
	 */
	public void geefSpelerBeurt() {

		spelerBeurt.setBeurt(false);

		int beurt = alleSpelers.indexOf(spelerBeurt);

		if (beurt + 1 == alleSpelers.size()) {
			beurt = 0;
			spelerBeurt = alleSpelers.get(beurt);
		} else {
			spelerBeurt = alleSpelers.get(beurt + 1);
		}

		spelerBeurt.setBeurt(true);
		System.out.println("Speler beurt gegeven aan: " + spelerBeurt.getNaam());
		gameBeurt++;
	}

	/**
	 * returnt of de speler aan de beurt is
	 *
	 * @param spelerNaam Geef de spelernaam mee in de vorm van een String
	 * @return True als de speler aan de beurt is, false als de speler niet aan de beurt is
	 */
	public boolean isSpelerBeurt(String spelerNaam) {
		return spelerBeurt.getNaam().equals(spelerNaam);
	}

	/**
	 * Deze functie geeft een kaart terug op de opgevraagde locatie in het speelbord
	 *
	 * @param x X co-ordinaat van de opgevraagde kaart
	 * @param y Y co-ordinaat van de opgevraagde kaart
	 * @return Tile de opgevraagde tile
	 */
	public Tile getTile(int x, int y) {

		if (x < 0 || y < 0 || x >= 100 || y >= 100) {
			return null;
		}
		return alleTiles[x][y];
	}

	/**
	 * Deze functie checkt of de kaart geplaatst kan worden op de meegegeven locatie, en zo ja plaatst hem daar
	 *
	 * @param x    De x co-ordinaat van de kaart
	 * @param y    De y co-ordinaat van de kaart
	 * @param tile De meegegeven tile
	 * @return True als de kaart past en is geplaatst, false als de kaart niet geplaatst kan/mag worden
	 */
	public boolean plaatsKaart(int x, int y, Tile tile) {
		if (!checkKaartFit(x, y, tile)) {
			System.out.println("Kaart past hier niet!");
			return false;
		}
		if (getTile(x, y) != null) {
			System.out.println("Er ligt hier al een tile!");
			return false;
		}
		alleTiles[x][y] = tile;
		System.out.println("Kaart geplaatst");
		laatstGeplaatst = tile;
		return true;
	}

	/**
	 * Plaats een kaart alle fitchecks ignored.
	 *
	 * @param x    geef de x co-ordinaat van de kaart mee
	 * @param y    geef de y co-ordinaat van de kaart mee
	 * @param tile geef de daadwerkelijke tile mee die geplaatst moet gaan worden
	 */
	public void plaatsKaartCheat(int x, int y, Tile tile) {
		alleTiles[x][y] = tile;
		laatstGeplaatst = tile;
		tile.plaats(x, y);
		System.out.println("=========================== CHEAT KAART GEPLAATST OP X 5 Y 5 ");
		System.out.println("Noord" + tile.getNoordZijde().getZijde().toString());
		System.out.println("Oost" + tile.getOostZijde().getZijde().toString());
		System.out.println("Zuid" + tile.getZuidZijde().getZijde().toString());
		System.out.println("West" + tile.getWestZijde().getZijde().toString());

	}

	/**
	 * Plaatst alle kaarten die geladen worden uit de json file op het bord.
	 * @param tiles
	 */
	public void plaatsLoadKaart(Tile[][] tiles) {
		placedTiles = new ArrayList<Tile>();
		for (int x = 0; 100 > x; x++) {
			for (int y = 0; 100 > y; y++) {
				if (tiles[x][y] != null) {
					System.out.println("Zien Tiles: " + alleTiles[x][y].getX());
					alleTiles[x][y] = tiles[x][y];
					laatstGeplaatst = tiles[x][y];
					tiles[x][y].plaats(tiles[x][y].getX(), tiles[x][y].getY());
					placedTiles.add(tiles[x][y]);
				}
			}
		}
	}

	/**
	 * Checkt of de kaart past op de plek waar de speler hem wil plaatsen
	 *
	 * @param x    coordinaat
	 * @param y    coordinaat
	 * @param tile de turntile
	 * @return returnt of de kaart past/niet
	 */
	public boolean checkKaartFit(int x, int y, Tile tile) {

		//Get alle omliggende tiles, als er geen tile op die plek ligt is de tile NULL
		Tile noord = getTile(x, y - 1);
		Tile oost = getTile(x + 1, y);
		Tile zuid = getTile(x, y + 1);
		Tile west = getTile(x - 1, y);

		//Als alle tiles NULL zijn is de kaart niet aan een aangrenzende tile geplaatst
		//Dit zou in principe nooit een probleem moeten zijn, maar deze check zit er voor
		//de zekerheid.
		if (noord == null && oost == null && zuid == null && west == null) {
			//Tile is geplaatst op een plek met geen grenzende tiles
			return false;
		}

		if (getTile(x, y) != null) {
			System.out.println("Er ligt hier al een tile!");
			return false;
		}

		//Als de tile niet null is en de ZUIDZIJDE van de NOORDtile overeenkomt met de NOORDZIJDE van de turntile is de
		//positie goed.
		if (noord != null) {
			if (noord.zuidZijde.zijde != Zijde.ZijdeType.LEEG && noord.zuidZijde.zijde != tile.noordZijde.zijde) {
				System.out.println("Noord klopt niet");
				return false;
			}
		}
		if (oost != null) {
			if (oost.westZijde.zijde != Zijde.ZijdeType.LEEG && oost.westZijde.zijde != tile.oostZijde.zijde) {
				System.out.println("oost klopt niet");
				return false;
			}
		}
		if (zuid != null) {
			if (zuid.noordZijde.zijde != Zijde.ZijdeType.LEEG && zuid.noordZijde.zijde != tile.zuidZijde.zijde) {
				System.out.println("zuid klopt niet");
				return false;
			}
		}
		if (west != null) {
			if (west.oostZijde.zijde != Zijde.ZijdeType.LEEG && west.oostZijde.zijde != tile.westZijde.zijde) {
				System.out.println("west klopt niet");
				return false;
			}
		}
		return true;
	}

	public int getSpelerHorige(String naam) {
		for (int i = 0; i < getAlleSpelers().size(); i++) {
			if (getAlleSpelers().get(i).getNaam().equals(naam)) {
				return getAlleSpelers().get(i).getBeschikbareHorigeInt();
			}
		}
		return 0;
	}


	/**
	 * Deze functie geeft een arraylist met alle spelers terug
	 *
	 * @return ArrayList met alle spelers
	 */
	public ArrayList<Speler> getAlleSpelers() {
		if (debug) {
			for (int i = 0; i < alleSpelers.size(); i++) {
				System.out.println("NIFFO- NAAM: " + alleSpelers.get(i).getNaam());
			}
		}
		return alleSpelers;
	}

	/**
	 * Return alle tiles in array als Tile
	 *
	 * @return ArrayList met alle tiles (als Tile)
	 */
	Tile[][] getAlleTiles() {
		return this.alleTiles;
	}

	public ArrayList<Point> getVerwijderHorigeDezeRonde() {
		return verwijderHorigeDezeRonde;
	}

	public ArrayList<Tile> getPlacedTiles() {
		return this.placedTiles;
	}
}
