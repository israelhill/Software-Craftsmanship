Israel Hill 
idh@case.edu
EECS 293
HW_4

Run the ProductTest.java file. This file contains JUnit tests for the package.

Part 3
The method testMake() will create objects based on the users input. If the serial number is invalid, it will throw an invalid serial number exception. If the user specifies a product that does not exist, it will throw an invalid product exception.

Part 4
The testExchangeBuilder and testRefundBuilder test if Refund and Exchange objects are immutatble. Changes to the builder after the instantiation of Refund/Exchange objects should reflect no changes in these objects.