package gamblersruin;

import java.util.ArrayList;

public class ValidRoundChk {
	private boolean validity;
	private String binaryStr;
	private String ABStr = "";
	private int binStrLen;
	private int binSum;
	private int currentA;
	private int permMoneyA;
	private int currentB;
	private int permMoneyB;
	private int totalMoney;
	private int tries;
	private int validTries = 0;
	private int invalidTries;
	private char format;
	private int[] binaryStrArray;
	private ArrayList<String> validBinary;
	private ArrayList<String> invalidBinary;
	
	public ValidRoundChk() {}
	
	public ValidRoundChk(int inTries, int inMoneyA) { //defaults to individual formatting
		validBinary = new ArrayList<String>();
		invalidBinary = new ArrayList<String>();
		format = 'i';
		tries = inTries;
		currentA = inMoneyA;
		permMoneyA = inMoneyA;
		permMoneyB = 1;
		binStrLen = Integer.toBinaryString((int)(Math.pow(2, tries - 1))).length();
	}
	
	public ValidRoundChk(char inFormat, int inTries, int inMoneyA) { //format grid with B = 1
		format = inFormat;
		tries = inTries;
		currentA = inMoneyA;
		permMoneyA = inMoneyA;
		permMoneyB = 1;
		binStrLen = Integer.toBinaryString((int)(Math.pow(2, tries - 1))).length();
	}
	
	public ValidRoundChk(char inFormat, int inTries, int inMoneyA, int inMoneyB) { //format grid with B > 0, currently unused
		format = inFormat;
		tries = inTries;
		currentA = inMoneyA;
		permMoneyA = inMoneyA;
		currentB = inMoneyB;
		permMoneyB = inMoneyB;
		binStrLen = Integer.toBinaryString((int)(Math.pow(2, tries - 1))).length();
	}
	
	public ArrayList<String> getValidBinary() {
		return validBinary;
	}
	
	public ArrayList<String> getInvalidBinary() {
		return invalidBinary;
	}
	
	public int getValidTries() {
		return validTries;
	}
	
	public int getInvalidTries() {
		return invalidTries;
	}
	
	public void checkValidRounds() { //looks for all valid tries for A to win
		for (int checkAllCases = (int)(Math.pow(2, tries - 1)); checkAllCases < Math.pow(2, tries); checkAllCases ++) {
			binSum = 0;
			currentA = permMoneyA;
			currentB = permMoneyB;
			totalMoney = permMoneyA + permMoneyB;
			validity = true;
			binaryStr = Integer.toBinaryString(checkAllCases);
			binaryStrArray = new int[binStrLen];
			for (int binToArray = 0; binToArray < binaryStr.length(); binToArray ++) {
				binaryStrArray[binToArray] = Character.getNumericValue(binaryStr.charAt(binToArray));
				binSum += Character.getNumericValue(binaryStr.charAt(binToArray));
			}

			if (((int) binSum) != ((binStrLen - permMoneyB) / 2)) {
				validity = false;
			} else {
				for (int checkCase = 0; checkCase < binStrLen; checkCase ++) {
					if (currentA == 0 || currentA == totalMoney) {
						validity = false;
						break;
					}
					if (binaryStrArray[checkCase] == 0) {
						currentA += 1;
					} else {
						currentA -= 1;
					}
				}
				if (currentA != totalMoney) {
					validity = false;
				}
			}
			
			if (format == 'i' && (((validity == true && validTries < 101) || (validity == false && invalidTries < 101)))) {
				ABStr = "";
				for (int convertToAB = 0; convertToAB < binaryStr.length(); convertToAB ++) {
					if (binaryStrArray[convertToAB] == 0) {
						ABStr += "A";
					} else ABStr += "B";
				}
			}
			
			if (validity == true) {
				validTries += 1;
				if (format == 'i' && validTries < 101)
					validBinary.add(ABStr);
			} else if (format == 'i') {
				invalidTries += 1;
				if (invalidTries < 101)
					invalidBinary.add(ABStr);
			}
		}
	}
}
