package edu.by.diffprocessor;

/**
 * @author (Ivan Mazaliuk)
 */
public class Processor {

    private long limit;

    Processor(long limit) {
        this.limit = limit;
    }

    public void doProcess(SortedLimitedList<Double> mustBeEqualTo, SortedLimitedList<Double> expectedOutput) {
        // TODO: make "mustBeEqualTo" list equal to "expectedOutput".
        // 0. Processor will be created once and then will be used billion times.
        // 1. Use methods: AddFirst, AddLast, AddBefore, AddAfter, Remove to modify list.
        // 2. Do not change expectedOutput list.
        // 3. At any time number of elements in list could not exceed the "Limit".
        // 4. "Limit" will be passed into Processor's constructor. All "mustBeEqualTo" and "expectedOutput" lists will have the same "Limit" value.
        // 5. At any time list elements must be in non-descending order.
        // 6. Implementation must perform minimal possible number of actions (AddFirst, AddLast, AddBefore, AddAfter, Remove).
        // 7. Implementation must be fast and do not allocate excess memory.

        SortedLimitedList.Entry<Double> expectedCurrent = expectedOutput.getFirst();
        SortedLimitedList.Entry<Double> actualCurrent = mustBeEqualTo.getFirst();

        while(expectedCurrent != null && actualCurrent != null) {

            Double expectedValue = expectedCurrent.getValue();
            Double actualValue = actualCurrent.getValue();
            if(!expectedValue.equals(actualValue)) {

                if(expectedValue.compareTo(actualValue) > 0) {

                    SortedLimitedList.Entry<Double> temp = actualCurrent.getNext();
                    mustBeEqualTo.remove(actualCurrent);
                    actualCurrent = temp;
                } else {

                    if(mustBeEqualTo.getCount() >= limit) {

                        SortedLimitedList.Entry<Double> temp = actualCurrent.getPrevious();
                        mustBeEqualTo.remove(actualCurrent);
                        mustBeEqualTo.addAfter(temp, expectedValue);
                    } else {

                        mustBeEqualTo.addBefore(actualCurrent, expectedValue);
                        actualCurrent = actualCurrent.getPrevious();
                    }

                }
            } else {
                expectedCurrent = expectedCurrent.getNext();
                actualCurrent = actualCurrent.getNext();
            }
        }

        if(expectedCurrent == null) {
            while (actualCurrent != null) {

                SortedLimitedList.Entry<Double> temp = actualCurrent.getNext();
                mustBeEqualTo.remove(actualCurrent);
                actualCurrent = temp;
            }
        } else {
            while (expectedCurrent != null) {
                mustBeEqualTo.addLast(expectedCurrent.getValue());
                expectedCurrent = expectedCurrent.getNext();
            }
        }
    }
}
