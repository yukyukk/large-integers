// Daniel Lee 100517557

public class HugeInteger {
    boolean isPositive;
    Node head;
    Node tail;
    int length;

    public HugeInteger() {
        isPositive = true;
        head = null;
        tail = null;
        length = 0;
    }

    public HugeInteger(String number) {
        isPositive = true;
        head = null;
        tail = null;
        length = 0;
        int scanPosition = 0;

        scanPosition = flagIfNegative(number);
        scanPosition = trimLeadingZeroes(scanPosition, number);

        for (int i = number.length() - 1; i >= scanPosition; i--) {
            int digit = number.charAt(i) - '0';
            addNode(digit);
            length++;
        }
    }

    private int flagIfNegative(String number) {

        if (number.charAt(0) == '-') {
            isPositive = false;
            return 1;
        }
        return 0;
    }

    private int trimLeadingZeroes(int scanPos, String number) {

        while (number.charAt(scanPos) == '0') {
            scanPos++;
        }
        return scanPos;
    }

    private void addNode(int data) {
        if (head == null) {
            head = new Node(data);
            tail = head;
            return;
        }
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new Node(data);
        current.next.prev = current;
        tail = current.next;
    }

    public HugeInteger addPositive(HugeInteger num2) {
        Node thisNode = this.head;
        Node num2Node = num2.head;

        HugeInteger sumHuge = new HugeInteger();

        int carryOver = 0;
        int lengthCompareCode = compareLength(num2);
        int lengthOfLargerNode = this.length;

        if(lengthCompareCode == -1) {
            lengthOfLargerNode = num2.length;
        }

        for (int i = 0; i < lengthOfLargerNode; i++) {
            int thisData;
            int num2Data;

            if (thisNode == null) {
                thisData = 0;
            }
            else {
                thisData = thisNode.data;
            }
            if (num2Node == null) {
                num2Data = 0;
            }
            else {
                num2Data = num2Node.data;
            }

            int sumDigit = thisData + num2Data + carryOver;
            carryOver = 0;

            if (sumDigit >= 10) {
                sumDigit -= 10;
                carryOver = 1;
            }
            sumHuge.addNode(sumDigit);

            if (thisNode != null) {
                thisNode = thisNode.next;
            }
            if (num2Node != null) {
                num2Node = num2Node.next;
            }
        }
        if (carryOver == 1) {
            sumHuge.addLast(1);
        }
        return sumHuge;
        }

    public int compareTo(HugeInteger num2) {
        int compareCode = comparePolarityAndLength(num2);

        if (compareCode != 0) {
            return compareCode;
        }
        Node thisNode = this.tail;
        Node secondNode = num2.tail;

        while (thisNode != null && secondNode != null) {
            int thisData = thisNode.data;
            int num2Data = secondNode.data;

            thisNode = thisNode.prev;
            secondNode = secondNode.prev;

            if (thisData != num2Data) {
                compareCode = compareDigits(num2.isPositive, thisData, num2Data);
                return compareCode;
            }
        }
        return compareCode;
    }

    private int comparePolarityAndLength(HugeInteger num2) {

        if (this.isPositive && !num2.isPositive) {
            return 1;
        }
        else if (!this.isPositive && num2.isPositive) {
            return -1;
        }
        else if (!this.isPositive) {
            if (this.length > num2.length) {
                return -1;
            }
            else if (this.length < num2.length) {
                return 1;
            }
        }
        return compareLength(num2);
    }

    private int compareLength(HugeInteger num2) {
        if (this.length > num2.length) {
            return 1;
        }
        else if (this.length < num2.length) {
            return -1;
        }
        return 0;
    }

    private int compareDigits(boolean num2Pos, int thisData, int num2Data) {

        if (!this.isPositive && !num2Pos) {
            if (thisData > num2Data) {
                return -1;
            }
            else if (thisData < num2Data) {
                return 1;
            }
        }
        else {
            if (thisData > num2Data) {
                return 1;
            }
            else if (thisData < num2Data) {
                return -1;
            }
        }
        return 0;
    }

    public String toString() {
        StringBuilder outString = new StringBuilder();

        if (head == null) {
            return "0";
        }
        if (!isPositive) {
            outString.append("-");
        }
        Node thisNode = tail;

        while (thisNode != null) {
            outString.append(thisNode.data);
            thisNode = thisNode.prev;
        }
        return outString.toString();
    }

    public void concatenateDigit(int digit) {
        Node newNode = new Node(digit);
        Node oldHead = head;

        head = newNode;
        head.next = oldHead;

        if (head.next != null) {
            head.next.prev = head;
        }
        if (tail == null) {
            tail = newNode;
        }
    }

    public void addLast(int digit) {
        Node newNode = new Node(digit);
        Node oldTail = tail;

        tail = newNode;
        tail.prev = oldTail;

        if (tail.prev != null) {
            tail.prev.next = tail;
        }
        if (head == null) {
            head = newNode;
        }
    }
}

