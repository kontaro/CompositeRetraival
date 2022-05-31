public class Pair {

		int x1;
		int x2;
		
		public Pair(int x1, int x2) {
			super();
			this.x1 = x1;
			this.x2 = x2;
		}
		
		public boolean contiene(int num1, int num2) {
			if (x1 == num1) {
				if(x2 == num2) {
					return true;
				}
			}
			return false;
		}
		
		public boolean contiene(Pair par) {
			if (x1 == par.getX1()) {
				if(x2 == par.getX2()) {
					return true;
				}
			}
			return false;
		}
		public int getX1() {
			return x1;
		}
		public void setX1(int x) {
			this.x1 = x;
		}
		public int getX2() {
			return x2;
		}
		public void setX2(int x) {
			this.x2 = x;
		}
		
		
}
