package Models;

public class Horige {

	public enum Posities {

		NOORD(40,0),
		OOST(70,40),
		ZUID(70,40),
		WEST(0,70),
		MIDDEN(40,40);

		int x;
		int y;

		private Posities(int x, int y){
			this.x = x;
			this.y = y;
		}
		public int getX(){
			return x;
		}
		public int getY() {
			return y;
		}
	}
}
