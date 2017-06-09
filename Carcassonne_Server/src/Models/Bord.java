package Models;

import Controllers.BordController;

import java.util.ArrayList;

public class Bord {

	ArrayList<Speler> alleSpelers;

	Speler spelerBeurt;

	Tile[][] alleTiles;

	Tile laatstGeplaatst;

	public Tile getLaatstGeplaatst() {
		return laatstGeplaatst;
	}
	public Bord(ArrayList<Speler> spelerList) {
		alleSpelers = spelerList;
		alleTiles = new Tile[100][100];
		spelerBeurt = alleSpelers.get(0);
	}

	public Speler getSpelerBeurt() {

		return spelerBeurt;
	}

	public void geefSpelerBeurt() {
		int beurt = alleSpelers.indexOf(spelerBeurt);
		if( beurt + 1 == alleSpelers.size()){
			beurt = 0;
			spelerBeurt = alleSpelers.get(beurt);
		} else {
			spelerBeurt = alleSpelers.get(beurt + 1);
		}
	}
	/**
	 * returnt of de speler aan de beurt is
	 * @param spelerNaam
	 * @return
	 */
	public boolean isSpelerBeurt(String spelerNaam) {
		System.out.println("Speler naam " + spelerNaam + " Speler beurt " + spelerBeurt.getNaam());
		return spelerBeurt.naam.equals(spelerNaam);
	}

	public Tile getTile(int x, int y){

		if(x < 0 || y < 0 || x > 100 || y > 100){
			return null;
		}
		return alleTiles[x][y];
	}

	public boolean plaatsKaart(int x, int y, Tile tile) {
		if(!checkKaartFit(x, y, tile)){
			System.out.println("Kaart past hier niet!");
			return false;
		}
		if(getTile(x,y) != null){
			System.out.println("Er ligt hier al een tile!");
			return false;
		}
		alleTiles[x][y] = tile;
		System.out.println("Kaart geplaatst");
		laatstGeplaatst = tile;
		geefSpelerBeurt();
		return true;
	}

	public void plaatsKaartCheat(int x, int y, Tile tile){
		alleTiles[x][y] = tile;
		laatstGeplaatst = tile;
		tile.plaats(x,y);
	}

	public boolean checkKaartFit(int x, int y, Tile tile) {

			Tile noord = getTile(x, y - 1);
			Tile oost = getTile(x + 1, y);
			Tile zuid = getTile(x , y + 1);
			Tile west = getTile(x - 1, y);
			if(noord==null && oost == null && zuid == null && west == null){
				System.out.println("Kaart is in de middle of nowhere geplaatst");
				//Tile is geplaatst op een plek met geen grenzende tiles
				return false;
			}

			if(noord != null){
				if(noord.zuidZijde.zijde != Zijde.ZijdeType.LEEG && noord.zuidZijde.zijde != tile.noordZijde.zijde){
					System.out.println("Noord klopt niet");
					return false;

				}
			}
			if(oost != null){
				if(oost.westZijde.zijde != Zijde.ZijdeType.LEEG && oost.westZijde.zijde != tile.oostZijde.zijde){
					System.out.println("oost klopt niet");
					return false;
				}
			}
			if(zuid != null){
				if(zuid.noordZijde.zijde != Zijde.ZijdeType.LEEG && zuid.noordZijde.zijde != tile.zuidZijde.zijde){
					System.out.println("zuid klopt niet");
					return false;
				}
			}
			if(west != null){
				if(west.oostZijde.zijde != Zijde.ZijdeType.LEEG && west.oostZijde.zijde != tile.westZijde.zijde){
					System.out.println("west klopt niet");
					return false;
				}
			}
			return true;



		}
}
