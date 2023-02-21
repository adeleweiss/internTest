run: spendPoints.class
		java spendPoints.java 5000 transactions.csv

spendPoints.class: spendPoints.java
		javac spendPoints.java 

clean:
	rm *.class
