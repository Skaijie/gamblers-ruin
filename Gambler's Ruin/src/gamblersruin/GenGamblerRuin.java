package gamblersruin;
import java.util.ArrayList;
import java.util.Scanner;

public class GenGamblerRuin { // practically zero useful annotations they may come later

	public static void main(String[] args) {
		Scanner scanIn = new Scanner(System.in);
		int tries = 0, moneyA = 0, moneyB = 1, minVal;
		String format;
		String oddEven;
		ValidRoundChk vrc;
		boolean continueTest = true, gridRounds, gridMoneyA, gridMoneyB, indivRound, indivMoneyA, indivMoneyB;
		while (continueTest == true) {
			System.out.println("Print grid or print individual cases? (G or I - defaults to individual cases)");
			format = scanIn.next(); //get format: individual or grid
			if (format.toLowerCase().startsWith("g")) { //if format is grid
				String rowN;
				gridRounds = false;
				gridMoneyA = false;
				gridMoneyB = false;
				while (gridMoneyA == false) {
					System.out.println("Enter maximum amount of money that A has:");
					moneyA = scanIn.nextInt();
					if (moneyA < 1) {
						System.out.println("A has to be a positive integer.");
					} else gridMoneyA = true;
				}
				
				/* while (gridMoneyB == false) {
					System.out.println("Enter maximum amount of money that B has:");
					moneyB = scanIn.nextInt();
					if (moneyA < 1) {
						System.out.println("B has to be a positive integer.");
					} else gridMoneyB = true;
				} */
				minVal = Math.min(moneyA, moneyB);
				if (minVal % 2 == 0)
					oddEven = "even";
				else oddEven = "odd";
				while (gridRounds == false) {
					System.out.println("Enter number maximum number of rounds that can occur:");
					tries = scanIn.nextInt();
					if (tries % 2 != minVal % 2 || tries < minVal + 2) {
						System.out.println("Number of rounds has to be " + oddEven + " and at least " + (minVal + 2) + ".");
					} else gridRounds = true;
					if (tries > 23) {
						System.out.println("Tries above 23 will take > 30 seconds to complete.");
					}
				}
				
				String rowTemp = "";
				for (int row = 2; row <= moneyA; row ++) {
					rowN = row + " | " + moneyB/* + rowTemp*/;
					for (int column = moneyB + 2/*moneyB + (row * 2 - 2)*/; column <= tries; column += 2) {
						//System.out.println("Column: " + column + ", Row: " + row + " ");
						vrc = new ValidRoundChk('g', column, row, moneyB);
						vrc.checkValidRounds();
						int validTries = vrc.getValidTries();
						rowN += " | " + validTries;
						/*if (column == ((row * 2) - moneyB)) {
							//System.out.println("Equates to? " + (moneyB + (row * 2) - 1));
							rowTemp += " | " + validTries;
							//System.out.println("Temp Row: " + rowTemp);
						}*/
					}
					System.out.println(rowN);
				}
				System.out.println("Origin is money A = 2 and number of rounds = " + (minVal + 2) + ".");
			} else {
				indivRound = false;
				indivMoneyA = false;
				while (indivRound == false) {
					System.out.println("Enter number of rounds that must occur:");
					tries = scanIn.nextInt();
					if (tries % 2 == 0 || tries < 3) {
						System.out.println("Number of rounds has to be an odd number of at least 3.");
					} else indivRound = true;
				}
				while (indivMoneyA == false) {
					System.out.println("Enter amount of money that A has:");
					moneyA = scanIn.nextInt();
					if (moneyA < 1) {
						System.out.println("A has to be a positive integer.");
					} else indivMoneyA = true;
				}
				vrc = new ValidRoundChk(tries, moneyA);
				vrc.checkValidRounds();
				ArrayList<String> vrcValid = vrc.getValidBinary(), vrcInvalid = vrc.getInvalidBinary();
				int validTries = vrc.getValidTries(), invalidTries = vrc.getInvalidTries();
				
				if (validTries > 20 && validTries < 101) {
					System.out.println("The list of valid tries is longer than 20 (" + validTries + " valid tries). Do you want to print all valid cases?");
					String printAll = scanIn.next();
					if (printAll.toLowerCase().startsWith("y")) {
						System.out.println("Valid Tries:");
						for (int printValid = 0; printValid < validTries; printValid ++) {
							System.out.println(vrcValid.get(printValid));
						}
					} else System.out.println("Not printing valid tries.");
				} else if (validTries < 21) {
					for (int printValid = 0; printValid < vrcValid.size(); printValid ++) {
						System.out.println(vrcValid.get(printValid));
					}
				} else System.out.println("Number of valid tries exceeds 100 (" + validTries + " valid tries), list will not be printed.");
				System.out.println("Number of valid tries: " + validTries);
				
				if (invalidTries > 20 && invalidTries < 101) {
					System.out.println("The list of invalid tries is longer than 20 (" + invalidTries + " invalid tries). Do you want to print all invalid cases (excluding cases starting with A)?");
					String printAll = scanIn.next();
					if (printAll.toLowerCase().startsWith("y")) {
						System.out.println("Invalid Tries:");
						for (int printInvalid = 0; printInvalid < invalidTries; printInvalid ++) {
							System.out.println(vrcInvalid.get(printInvalid));
						}
					} else System.out.println("Not printing invalid tries.");
				} else if (invalidTries < 21) {
					for (int printInvalid = 0; printInvalid < invalidTries; printInvalid ++) {
						System.out.println(vrcInvalid.get(printInvalid));
					}
					System.out.println("Tries starting with \"A\" are not printed.");
				} else System.out.println("Number of invalid tries exceeds 100 (" + invalidTries + " invalid tries, excluding tries starting with \"A\"), list will not be printed.");
			}
			System.out.println("Start another round? Y/N");
			String startRound = scanIn.next();
			if (startRound.toLowerCase().startsWith("y")) {
				continueTest = true;
			} else {
				continueTest = false;
				scanIn.close();
			}
		}
	}
}