package utils;

import structure.Scalar;

import java.util.concurrent.atomic.AtomicInteger;

public class TestUtils
{
   private TestUtils() {};

   static public double [] getResult(Scalar[] input) {
      double [] result = new double[input.length];

      for(int i = 0; i < input.length; i++) {
         result[i] = input[i].getValue();
      }

      return result;
   }

   static public int depthFirstSearch(Scalar [] output) {
      AtomicInteger counter = new AtomicInteger(0);
      for (Scalar scalar : output) {
           depthFirstSearch(scalar, counter);
       }

      return counter.get();
   }

   static private void depthFirstSearch(Scalar scalar, AtomicInteger counter) {
      if(scalar.getParent() == null || scalar.getValue() == Double.MAX_VALUE) {
         return;
      }

      for(Scalar parent : scalar.getParent()) {
         depthFirstSearch(parent, counter);
      }

      counter.incrementAndGet();
   }
}
