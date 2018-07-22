import junit.framework.TestCase;


public class FiveModelTest extends TestCase {

	// Test a normal Five Model board
	public void testFiveModel() {
		FiveModel testModel = new FiveModel(7,7);
		
	}
	
	// Test a board that technically doesn't exist
	public void testFiveModelNegative(){
		FiveModel testModel = new FiveModel(-5,-5);
	}
	
	// Test a normal board with a normal player
	public void testGetNextPlayer() {
		FiveModel testPlayer = new FiveModel(7,7);
		testPlayer.getNextPlayer();
	}

	// Test a Normal board with a player within the bounds
	public void testGetPlayerAt() {
		FiveModel testPlayer = new FiveModel (7,7);
		testPlayer.getPlayerAt(3,3);
	}
	
	// Test a Normal board with a player out of the bounds
	public void testGetPlayerAtInvalid() {
		FiveModel testPlayer = new FiveModel (7,7);
		testPlayer.getPlayerAt(8,8);
	}

	public void testReset() {
		fail("Not yet implemented");
	}

	// Test a move within the bounds of the board
	public void testMove() {
		FiveModel testMove = new FiveModel(7,7);
	}
	
	// Test a move out of bounds on the columns of the board 
	// This case maxCols will go out of bounds
	// yellow coverage, Loop isn't traversed fully
	public void testMoveInvalid() {
		FiveModel testMove = new FiveModel(12,7);
	}
	
	// Test another move out of bounds on the rows of the board
	// This case maxRows will go out of bounds
	// yellow coverage, Loop isn't traversed fully
	public void testMoveInvalid2(){
		FiveModel testMove = new FiveModel(7,15);
	}
	
	
	/* First attempt at checking column up and row right
	public void test_count5(){
		FiveModel testPlayer = new FiveModel (7,7);
		for(int i=0; i < 7; i++){
			for(int j=0; j < 7; j++){
				testPlayer.move(i,j);
				testPlayer.getGameStatus();
			}
		}
	}*/
	
	// Create a big enough board to test column up and column right
	// Test 5 within a column going up-way
	// Test 5 within a row going right-way
	public void test_count5ColumnRow(){
		FiveModel testPlay = new FiveModel (30,30);
		for(int i=1; i < 30; i++){
			for(int j=1; j < 30; j++){
				testPlay.move(i-1,j-1);
				testPlay.move(30-i, j);
				testPlay.getGameStatus();
			}
		}
	}
	
	// Create a board big enough to support getting
	// 5 in a row diagonally. Call game status to check.
	// This checks any diagonal way.
	// call gameStatus
	public void test_count5Diagonal(){
		FiveModel testDiagonal = new FiveModel (30,30);
		for(int i=0; i < 30; i++){
			for(int j=0; j < 30; j++){
				testDiagonal.move(i-1,j-1);
				testDiagonal.getGameStatus();
			}
		}
	}
	
	// Use the moves to "achieve 5 in a row". 
	// The idea was to test a match where
	// not getting 5 in a row resulted in a tie 
	// simply because the board was too small too even
	// achieve 5 in a row. 
	public void test_count5Tie(){
		FiveModel testTie = new FiveModel (2,2);
		for(int i=0; i < 2; i++){
			for(int j=0; j < 2; j++){
				testTie.move(i,j);
				testTie.getGameStatus();
			}
		}
	}
	
	
	//_count5 is private so test it through calling gameStatus 
	public void testGetGameStatus() {
		fail("Not yet implemented");
	}

}
